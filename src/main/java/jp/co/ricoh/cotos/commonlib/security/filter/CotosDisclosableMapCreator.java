package jp.co.ricoh.cotos.commonlib.security.filter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jp.co.ricoh.cotos.commonlib.security.CotosAuthenticationDetails;
import jp.co.ricoh.cotos.commonlib.security.mom.MomAuthorityService.AuthLevel;

/**
 * UIでトランザクション情報の項目表示/非表示を制御するための情報を作成するクラス
 */
@Component
public class CotosDisclosableMapCreator {

	/** COTOSのエンティティ/DTOか判定するためのパッケージ */
	private static String ENTITY_EXISTS_PACKAGE = "jp.co.ricoh.cotos.commonlib";

	/**
	 * 指定されたクラスの権限情報を作成する
	 */
	public Map<String, Object> create(Class<?> clazz) {

		// ログインユーザーの認証情報を取得
		CotosAuthenticationDetails userInfo = (CotosAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// 権限情報マップ
		Map<String, Object> disclosableMap = new HashMap<>();

		// スーパークラスの権限情報マップを取得
		if (!clazz.getSuperclass().equals(Object.class)) {
			Map<String, Object> superClassMap = this.create(clazz.getSuperclass());
			disclosableMap.putAll(superClassMap);
		}

		for (Field field : clazz.getDeclaredFields()) {

			// trueなら表示可能
			boolean disclosable = true;

			// アノテーション取得
			Annotation ignoreAnnotation = field.getAnnotation(JsonIgnore.class);

			// JsonIgnoreが指定されている場合、項目を追加しない
			if (ignoreAnnotation != null)
				continue;

			// アノテーション取得
			CotosDisclosable cotosDiscosableAnnotation = field.getAnnotation(CotosDisclosable.class);

			// CotosDisclosableが指定されている場合、権限チェックを行う
			if (cotosDiscosableAnnotation != null) {
				// アノテーションに対応するユーザーのMoM権限を取得
				AuthLevel userAuthLevel = userInfo.getMomAuthorities().get(cotosDiscosableAnnotation.momActionDiv()).get(cotosDiscosableAnnotation.momAuthInfoId());

				// アノテーションに指定されたMoM権限レベル以上であれば、表示可能
				disclosable = Integer.compare(Integer.valueOf(userAuthLevel.toValue()), Integer.valueOf(cotosDiscosableAnnotation.momAuthLevel().toValue())) >= 0;
			}

			// フィールドの型により、処理変更
			if (field.getType().isPrimitive() || field.getType().isEnum()) {
				disclosableMap.put(field.getName(), disclosable);
			} else {
				// COTOSのエンティティを参照している場合は、参照しているクラスの権限Mapを作成
				if (field.getType().getPackage().getName().startsWith(ENTITY_EXISTS_PACKAGE)) {
					Map<String, Object> entityMap = this.create(field.getType());
					disclosableMap.put(field.getName(), entityMap);
					continue;
				}

				// List型の場合は、ジェネリクス指定の型を取得
				if (field.getType().equals(List.class)) {
					ParameterizedType listType = (ParameterizedType) field.getGenericType();
					Class<?> listClass = (Class<?>) listType.getActualTypeArguments()[0];

					// COTOSのエンティティの場合、対象のエンティティの権限情報を作成
					if (listClass.getPackage().getName().startsWith(ENTITY_EXISTS_PACKAGE)) {
						Map<String, Object> entityMap = this.create(listClass);
						disclosableMap.put(field.getName(), entityMap);
					} else {
						disclosableMap.put(field.getName(), disclosable);
					}

					continue;
				}

				// 上記クラス以外
				disclosableMap.put(field.getName(), disclosable);
			}
		}

		return disclosableMap;
	}
}
