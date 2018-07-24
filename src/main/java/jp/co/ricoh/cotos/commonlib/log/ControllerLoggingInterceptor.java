package jp.co.ricoh.cotos.commonlib.log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jp.co.ricoh.cotos.commonlib.logic.message.MessageUtil;
import jp.co.ricoh.cotos.commonlib.security.CotosAuthenticationDetails;

@Aspect
@Component
public class ControllerLoggingInterceptor {

	/** ロガー */
	private static final Log log = LogFactory.getLog(ControllerLoggingInterceptor.class);

	@Autowired
	MessageUtil messageUtil;

	@Around("execution(* jp.co.ricoh.cotos.*.controller.*Controller.*(..))")
	public Object traceController(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

		// 認証情報を取得
		CotosAuthenticationDetails userInfo = (CotosAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// 現在のリクエストを取得
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");

		Object ret = null;

		try {
			log.info(messageUtil.createMessageInfo("PerformLogInfo", Arrays.asList(request.getMethod(), request.getRequestURL().toString(), "start", userInfo.getSingleUserId(), userInfo.getMomEmployeeId(), formatter.format(LocalDateTime.now())).toArray(new String[0])).getMsg());

			ret = proceedingJoinPoint.proceed();
			return ret;
		} finally {
			log.info(messageUtil.createMessageInfo("PerformLogInfo", Arrays.asList(request.getMethod(), request.getRequestURL().toString(), "end", userInfo.getSingleUserId(), userInfo.getMomEmployeeId(), formatter.format(LocalDateTime.now())).toArray(new String[0])).getMsg());
		}
	}
}
