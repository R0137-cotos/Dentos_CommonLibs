package jp.co.ricoh.cotos.commonlib.security;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class MultipleReadEnableFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		// リクエストのInputStreamを何度も使用できるようにラップする
		HttpServletRequest wrappedRequest = new BufferedServletRequestWrapper((HttpServletRequest) request);
		filterChain.doFilter(wrappedRequest, response);
	}
}
