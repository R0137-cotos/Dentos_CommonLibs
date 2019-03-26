package jp.co.ricoh.cotos.commonlib.security.filter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.ActionDiv;
import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.AuthDiv;
import jp.co.ricoh.cotos.commonlib.security.mom.MomAuthorityService.AuthLevel;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface CotosDisclosable {
	ActionDiv momActionDiv();

	AuthDiv momAuthInfoId();

	AuthLevel momAuthLevel();
}