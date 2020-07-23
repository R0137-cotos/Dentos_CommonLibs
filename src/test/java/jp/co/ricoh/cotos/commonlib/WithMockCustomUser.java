package jp.co.ricoh.cotos.commonlib;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.springframework.security.test.context.support.WithSecurityContext;

import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.ActionDiv;
import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.AuthDiv;
import jp.co.ricoh.cotos.commonlib.security.mom.MomAuthorityService.AuthLevel;

@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithMockCustomUserSecurityContextFactory.class)
public @interface WithMockCustomUser {

	String momEmployeeId() default "mid";

	String singleUserId() default "sid";

	String origin() default "cotos.ricoh.co.jp";

	String applicationId() default "cotos_dev";

	boolean isSuperUser() default false;

	boolean isDummyUser() default false;

	String jwt() default "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJvcmlnaW4iOiJjb3Rvcy5yaWNvaC5jby5qcCIsInNpbmdsZVVzZXJJZCI6InNpZCIsIm1vbUVtcElkIjoibWlkIiwiZXhwIjoyNTM0MDIyNjgzOTksImFwcGxpY2F0aW9uSWQiOiJjb3Rvc19kZXYifQ.qJBFsMJFZcLdF7jWwEafZSOQfmL1EqPVDcRuz6WvsCI";

	ActionDiv actionDiv() default ActionDiv.なし;
	
	AuthDiv authDiv() default AuthDiv.なし;
	
	AuthLevel authLevel() default AuthLevel.不可;
}
