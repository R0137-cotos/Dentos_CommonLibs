package jp.co.ricoh.cotos.commonlib.security;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "cotos.auth")
public class CotosUserDetailsService implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {

	@Setter
	@Getter
	private Map<String, String> authentications;

	@Override
	public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken token) throws UsernameNotFoundException {

		String authenticationHeader = token.getPrincipal().toString();
		CotosAuthenticationDetails cotosAuthenticationDetails;

		try {
			cotosAuthenticationDetails = this.decodeAuthAuthenticated(authenticationHeader);
			if (cotosAuthenticationDetails == null) {
				throw new UsernameNotFoundException("user not found");
			}
		} catch (UnsupportedEncodingException e) {
			throw new UsernameNotFoundException("failure url decoding", e);
		}

		return cotosAuthenticationDetails;
	}

	private CotosAuthenticationDetails decodeAuthAuthenticated(String authenticationHeader) throws UnsupportedEncodingException {

		// authentications属性から
		if (null != authenticationHeader && authenticationHeader.startsWith("Bearer ")) {

			// Authentications属性からユーザー情報を取得
			String[] split = URLDecoder.decode(authenticationHeader.substring("Bearer ".length()), "UTF-8").split(":", -1);

			// 要素数、必須項目の確認
			if (3 == split.length && !StringUtils.isEmpty(split[0]) && !StringUtils.isEmpty(split[1]) && !StringUtils.isEmpty(split[2])) {

				// アプリケーションの実行キーが存在するか確認
				if (null != authentications && authentications.containsKey(split[2])) {

					return new CotosAuthenticationDetails(split[1], split[0], authentications.get(split[2]));
				}
			}
		}

		return null;
	}
}
