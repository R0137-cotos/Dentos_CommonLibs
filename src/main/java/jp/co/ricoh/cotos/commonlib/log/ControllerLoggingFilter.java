package jp.co.ricoh.cotos.commonlib.log;

import java.io.IOException;
import java.util.Collections;
import java.util.stream.Collectors;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class ControllerLoggingFilter implements Filter {
	/** ロガー */
	private static final Log log = LogFactory.getLog(ControllerLoggingInterceptor.class);

	@Autowired
	ObjectMapper mapper;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// リクエストヘッダーのログ出力
		HttpServletRequest httpReq = ((HttpServletRequest) request);
		log.info(String.format("\tRequest Header: %s", Collections.list(httpReq.getHeaderNames()).stream().collect(Collectors.toMap(k -> k, v -> httpReq.getHeader(v)))));

		// APIの実行
		chain.doFilter(request, response);

		// レスポンスヘッダーの出力
		HttpServletResponse httpRes = ((HttpServletResponse) response);
		log.info(String.format("\tResponse Header: %s", httpRes.getHeaderNames().stream().distinct().collect(Collectors.toMap(k -> k, v -> httpRes.getHeader(v)))));
	}

	@Override
	public void destroy() {
	}

}
