package jp.co.ricoh.cotos.commonlib.security;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import jp.co.ricoh.cotos.commonlib.util.HeadersProperties;

public class PreAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

	@Autowired
	HeadersProperties headersProperties;

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {

		// リクエストのヘッダー情報から認証情報を取得
		String authenticationHeader = request.getHeader(headersProperties.getAuthorization());
		return authenticationHeader != null ? authenticationHeader : "";
	}

	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		// 付帯情報としてOriginを取得
		String origin = request.getHeader("origin");
		return origin != null ? origin : "";
	}

}
