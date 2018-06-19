package jp.co.ricoh.cotos.commonlib.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class MultipleReadEnableFilter extends GenericFilterBean {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// リクエストのInputStreamを何度も使用できるようにラップする
		HttpServletRequest wrappedRequest = new BufferedServletRequestWrapper((HttpServletRequest) request);
		chain.doFilter(wrappedRequest, response);
	}
}
