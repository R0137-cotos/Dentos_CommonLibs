package jp.co.ricoh.cotos.commonlib.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

public class PreAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
		
		// リクエストのヘッダー情報から認証情報を取得
		String authenticationHeader = request.getHeader("Authentication");
		return authenticationHeader != null ? authenticationHeader : "";
	}

	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		// 付帯情報は無し
		return "";
	}

}
