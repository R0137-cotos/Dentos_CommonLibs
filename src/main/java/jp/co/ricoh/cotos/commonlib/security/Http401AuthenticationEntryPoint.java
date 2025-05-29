package jp.co.ricoh.cotos.commonlib.security;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * 401を送信する。
 * responseHeaderに"WWW-Authenticate"を追加し、指定の文字列を設定する。
 *
 * @NOTE Spring 1.x系のHttp401AuthenticationEntryPointを参考にカスタム実装
 */
public class Http401AuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final String headerValue;

	public Http401AuthenticationEntryPoint(String headerValue) {
		this.headerValue = headerValue;
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		response.setHeader("WWW-Authenticate", this.headerValue);
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
				authException.getMessage());
	}

}