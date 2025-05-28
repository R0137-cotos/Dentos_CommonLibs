package jp.co.ricoh.cotos.commonlib.security;

import java.util.function.Supplier;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * テスト用投票クラス
 */
@Component
public class TestVoter implements AuthorizationManager<RequestAuthorizationContext> {

	@Override
	public AuthorizationDecision check(Supplier<Authentication> auth, RequestAuthorizationContext context) {

		Authentication authentication = auth.get();

		if (authentication == null) {
			return new AuthorizationDecision(false);
		}

		boolean hasBody = Boolean.valueOf(context.getRequest().getParameter("hasBody"));
		boolean isSuccess = Boolean.valueOf(context.getRequest().getParameter("isSuccess"));

		if (hasBody) {
			try {
				// リクエストBODYから情報を取得
				ObjectMapper om = new ObjectMapper();
				om.readValue(context.getRequest().getInputStream(), TestEntity.class);
			} catch (Exception e) {
				throw new RuntimeException();
			}
		}

		return new AuthorizationDecision(isSuccess);
	}
}
