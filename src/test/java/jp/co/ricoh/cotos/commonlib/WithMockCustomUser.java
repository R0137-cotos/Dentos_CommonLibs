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
	
	String applicationId() default "cotos_dev";
	
	String jwt() default "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJvcmlnaW4iOiJjb3Rvcy5yaWNvaC5jby5qcCIsInNpbmdsZVVzZXJJZCI6InNpZCIsIm1vbUVtcElkIjoibWlkIiwiYXBwbGljYXRpb25JZCI6ImNvdG9zX2RldiIsImV4cCI6MjUzNDAyMjY2ODM5OX0.9llyM40te3KdgDCpuUfZKnGAj6YuLe3X_1xET_8i4FA";
}
