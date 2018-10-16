package jp.co.ricoh.cotos.commonlib.security;

import org.apache.commons.lang3.StringUtils;
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

import jp.co.ricoh.cotos.commonlib.util.ClaimsProperties;
import jp.co.ricoh.cotos.commonlib.util.JwtProperties;

@Component
public class CotosUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

	@Autowired
	JwtProperties jwtProperties;

	@Autowired
	ClaimsProperties claimsProperties;

	@Override
	public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {

		// 認証用ヘッダーを取得
		String authenticationHeader = token.getPrincipal().toString();
		CotosAuthenticationDetails cotosAuthenticationDetails = null;

		try {
			cotosAuthenticationDetails = this.decodeAuthentication(authenticationHeader);
			if (cotosAuthenticationDetails == null || StringUtils.isAnyBlank(cotosAuthenticationDetails.getMomEmployeeId(), cotosAuthenticationDetails.getSingleUserId(), cotosAuthenticationDetails.getOrigin())) {
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

	private CotosAuthenticationDetails decodeAuthentication(String authenticationHeader) {

		// Bearer属性を取得
		if (authenticationHeader.startsWith("Bearer ")) {

			String jwtString = authenticationHeader.substring("Bearer ".length());

			Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecretKey());
			JWTVerifier verifier = JWT.require(algorithm).build();
			DecodedJWT jwt = verifier.verify(jwtString);
			jwt.getClaim(claimsProperties.getMomEmpId());
			jwt.getClaim(claimsProperties.getSingleUserId());
			jwt.getClaim(claimsProperties.getOrigin());

			return new CotosAuthenticationDetails(jwt.getClaim(claimsProperties.getMomEmpId()).asString(), jwt.getClaim(claimsProperties.getSingleUserId()).asString(), jwt.getClaim(claimsProperties.getOrigin()).asString(), jwtString);
		}

		return null;
	}
}
