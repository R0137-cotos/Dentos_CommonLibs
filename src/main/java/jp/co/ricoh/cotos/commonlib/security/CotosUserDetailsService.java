package jp.co.ricoh.cotos.commonlib.security;

import java.util.Arrays;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.ActionDiv;
import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.AuthDiv;
import jp.co.ricoh.cotos.commonlib.logic.message.MessageUtil;
import jp.co.ricoh.cotos.commonlib.repository.master.SuperUserMasterRepository;
import jp.co.ricoh.cotos.commonlib.security.mom.MomAuthorityService;
import jp.co.ricoh.cotos.commonlib.security.mom.MomAuthorityService.AuthLevel;
import jp.co.ricoh.cotos.commonlib.util.ClaimsProperties;
import jp.co.ricoh.cotos.commonlib.util.JwtProperties;

@Component
public class CotosUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

	/** ロガー */
	private static final Log log = LogFactory.getLog(CotosUserDetailsService.class);

	@Autowired
	JwtProperties jwtProperties;

	@Autowired
	ClaimsProperties claimsProperties;

	@Autowired
	MomAuthorityService momAuthorityService;

	@Autowired
	MessageUtil messageUtil;

	@Autowired
	SuperUserMasterRepository superUserMasterRepository;

	@Override
	public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {

		// 認証用ヘッダーを取得
		String authenticationHeader = token.getPrincipal().toString();
		CotosAuthenticationDetails cotosAuthenticationDetails = null;

		try {
			cotosAuthenticationDetails = this.decodeAuthentication(authenticationHeader);
			if (cotosAuthenticationDetails == null || StringUtils.isAnyBlank(cotosAuthenticationDetails.getMomEmployeeId(), cotosAuthenticationDetails.getSingleUserId(), cotosAuthenticationDetails.getOrigin(), cotosAuthenticationDetails.getApplicationId())) {
				throw new UsernameNotFoundException("user not found");
			}
		} catch (Exception e) {
			throw new UsernameNotFoundException("failure verification", e);
		}

		// リクエスト元のOriginと、JWTのオリジンを比較
		String requestOrigin = token.getCredentials().toString();

		if (!StringUtils.isBlank(requestOrigin) && !requestOrigin.equals(cotosAuthenticationDetails.getOrigin())) {
			throw new UsernameNotFoundException("Origin Not Allowed");
		}

		return cotosAuthenticationDetails;
	}

	private CotosAuthenticationDetails decodeAuthentication(String authenticationHeader) throws Exception {

		// Bearer属性を取得
		if (authenticationHeader.startsWith("Bearer ")) {

			String jwtString = authenticationHeader.substring("Bearer ".length());

			Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecretKey());
			JWTVerifier verifier = JWT.require(algorithm).build();
			DecodedJWT jwt = verifier.verify(jwtString);

			// スーパーユーザーか判定
			boolean isSuperUser = superUserMasterRepository.existsByUserId(jwt.getClaim(claimsProperties.getMomEmpId()).asString());
			// シングルユーザーIDに紐づく権限情報を取得
			Map<ActionDiv, Map<AuthDiv, AuthLevel>> momAuthorities = momAuthorityService.searchAllMomAuthorities(jwt.getClaim(claimsProperties.getSingleUserId()).asString());

			// 一般ユーザーで、MoM権限ユーザーが取得できない場合はエラー
			if (!isSuperUser && momAuthorities == null) {
				log.error(messageUtil.createMessageInfo("NoMomAuthoritiesError", Arrays.asList(jwt.getClaim(claimsProperties.getSingleUserId()).asString()).toArray(new String[0])).getMsg());
				throw new Exception();
			}

			return new CotosAuthenticationDetails(jwt.getClaim(claimsProperties.getMomEmpId()).asString(), jwt.getClaim(claimsProperties.getSingleUserId()).asString(), jwt.getClaim(claimsProperties.getOrigin()).asString(), jwt.getClaim(claimsProperties.getApplicationId()).asString(), jwtString, isSuperUser, momAuthorities);
		}

		return null;
	}
}