package jp.co.ricoh.cotos.commonlib;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithSecurityContext;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {

	String momEmployeeId() default "mid";

	String singleUserId() default "sid";

	String origin() default "cotos.ricoh.co.jp";
	
	String jwt() default "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJvcmlnaW4iOiJjb3Rvcy5yaWNvaC5jby5qcCIsInNpbmdsZVVzZXJJZCI6InNpZCIsIm1vbUVtcElkIjoibWlkIiwiZXhwIjoyNTM0MDIyNjgzOTl9.Apmi4uDwtiscf9WgVIh5Rx1DjoZX2eS7H2YlAGayOsQ";
}
