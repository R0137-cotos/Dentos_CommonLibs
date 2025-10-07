package jp.co.ricoh.cotos.commonlib.security;

import java.io.IOException;
import java.util.Arrays;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jp.co.ricoh.cotos.commonlib.logic.message.MessageUtil;

@Component
public class AccessLogOutputFilter extends OncePerRequestFilter {

	private static final Log log = LogFactory.getLog(AccessLogOutputFilter.class);

	@Autowired
	MessageUtil messageUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		
		// 認証情報を取得
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		// 認証情報からログに必要な情報を取得
		String momEmployeeId = authentication == null ? null : ((CotosAuthenticationDetails)authentication.getPrincipal()).getMomEmployeeId();
		String singleUserId = authentication == null ? null : ((CotosAuthenticationDetails)authentication.getPrincipal()).getSingleUserId();
		String origin = StringUtils.isBlank(request.getHeader("origin")) ? null : request.getHeader("origin");
		
		// アクセス： start
		log.info(messageUtil.createMessageInfo("AccessLogStartInfo", Arrays.asList(request.getMethod(), request.getRequestURL().toString(), singleUserId, momEmployeeId, origin).toArray(new String[0])).getMsg());
		try {
			filterChain.doFilter(request, response);
		} finally {
			// アクセス： end
			log.info(messageUtil.createMessageInfo("AccessLogEndInfo", Arrays.asList(request.getMethod(), request.getRequestURL().toString(), (HttpStatus.OK.value() == response.getStatus() || HttpStatus.CREATED.value() == response.getStatus()) ? "success" : "fail", singleUserId, momEmployeeId, String.valueOf(response.getStatus())).toArray(new String[0])).getMsg());
		}
	}
}
