package jp.co.ricoh.cotos.commonlib.security.filter;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;

import jp.co.ricoh.cotos.commonlib.security.CotosAuthenticationDetails;
import jp.co.ricoh.cotos.commonlib.security.mom.MomAuthorityService.AuthLevel;

@Component
public class CotosDisclosableFilter extends SimpleBeanPropertyFilter {

	/** フィルター名 */
	public static final String FILTER_NAME = "cotosDisclosableFilter";

	@Override
	public void serializeAsField(Object pojo, JsonGenerator jgen, SerializerProvider provider, PropertyWriter writer) throws Exception {

		// ログインユーザーの認証情報を取得
		CotosAuthenticationDetails userInfo = (CotosAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// スーパーユーザーならシリアライズ処理を実施
		if (userInfo.isSuperUser()) {
			super.serializeAsField(pojo, jgen, provider, writer);
		} else {
			// シリアライズ対象のクラスを取得
			Class<?> clazz = jgen.getOutputContext().getCurrentValue().getClass();

			// シリアライズ対象のフィールドを特定し、フィルタリング対象のアノテーションを特定
			CotosDisclosable targetFilterAnnotation = clazz.getDeclaredField(writer.getName()).getAnnotation(CotosDisclosable.class);

			// フィルタリング対象のアノテーションが存在しなければ、シリアライズ処理を実施
			if (targetFilterAnnotation == null) {
				super.serializeAsField(pojo, jgen, provider, writer);
			} else {
				// アノテーションに対応するユーザーのMoM権限を取得
				AuthLevel userAuthLevel = userInfo.getMomAuthorities().get(targetFilterAnnotation.momActionDiv()).get(targetFilterAnnotation.momAuthDiv());

				// アノテーションに指定されたMoM権限レベル以上であれば、シリアライズ処理を実施
				if (Integer.compare(Integer.valueOf(userAuthLevel.toValue()), Integer.valueOf(targetFilterAnnotation.momAuthLevel().toValue())) >= 0) {
					super.serializeAsField(pojo, jgen, provider, writer);
				}
			}
		}
	}
}