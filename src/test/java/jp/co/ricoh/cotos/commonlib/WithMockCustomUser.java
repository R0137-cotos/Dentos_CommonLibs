package jp.co.ricoh.cotos.commonlib;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {

	String momEmployeeId() default "mid";

	String singleUserId() default "sid";

	String applicationKey() default "";
}
