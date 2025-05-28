package jp.co.ricoh.cotos.commonlib.security.complement;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import jakarta.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.util.FieldUtils;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.util.ReflectionUtils;
import jp.co.ricoh.cotos.commonlib.security.CotosAuthenticationDetails;
import jp.co.ricoh.cotos.commonlib.security.filter.CotosDisclosable;
import jp.co.ricoh.cotos.commonlib.security.mom.MomAuthorityService.AuthLevel;

/**
 * MoM権限によるフィルタリングされた項目をDB情報で補完するクラス
 */
@Component
public class CotosFilteredParamsComplementer {

	/** COTOSのエンティティ/DTOか判定するためのパッケージ */
	private static String ENTITY_EXISTS_PACKAGE = "jp.co.ricoh.cotos.commonlib";

	private static ConfigurableApplicationContext context;

	@Autowired
	public void injectContext(ConfigurableApplicationContext injectContext) {
		context = injectContext;
	}

	@Autowired
	EntityManager entityManager;

	/**
	 * パラメーターの補完処理
	 */
	public Object completmentFilteredParam(Object param) {

		// Cotosのエンティティ/DTOか確認
		if (param.getClass().getPackage() != null && param.getClass().getPackage().getName().startsWith(ENTITY_EXISTS_PACKAGE)) {

			// 補完対象のクラスか確認
			CotosComplementTarget cotosComplementTarget = param.getClass().getDeclaredAnnotation(CotosComplementTarget.class);

			try {
				// 補完対象のクラスでない場合は、対象のクラス内から補完対象クラスを検索する
				if (cotosComplementTarget == null) {
					this.findComplementTarget(param);
				} else {
					// 補完元の情報
					Object srcEntity = this.find(cotosComplementTarget, FieldUtils.getFieldValue(param, "id"));

					// 再帰的な補完処理
					this.complement(param, srcEntity);
				}
			} catch (IllegalAccessException neverOccured) {
				throw new RuntimeException(neverOccured);
			}
		}

		return param;
	}

	/**
	 * パラメーターのクラスから、補完対象オブジェクトを検索し、 補完処理を実施する。
	 */
	private void findComplementTarget(Object target) throws IllegalAccessException {

		// INPUTクラスに定義されたフィールドでループ
		for (Field field : ReflectionUtils.getDeclaredFields(target.getClass())) {

			Object fieldValue = FieldUtils.getFieldValue(target, field.getName());

			// nullならスルー
			if (fieldValue == null) {
				continue;
			}

			// プリミティブ型は補完対象オブジェクトとなり得ないため、次のループ処理へ
			if (field.getType().isPrimitive()) {
				continue;
			}

			// COTOSのエンティティ/DTOを参照している場合は、再帰的な補完処理を実施する
			if (field.getType().getPackage().getName().startsWith(ENTITY_EXISTS_PACKAGE)) {

				// COTOS用の列挙型は再帰的な補完処理が不要なため、次のループ処理へ
				if (field.getType().isEnum()) {
					continue;
				}

				// 補完対象クラスか確認
				CotosComplementTarget cotosComplementTarget = field.getType().getDeclaredAnnotation(CotosComplementTarget.class);

				// 補完対象のクラスでない場合は、対象のクラス内から補完対象クラスを検索する
				if (cotosComplementTarget == null) {
					this.findComplementTarget(fieldValue);
				} else {
					// 補完元情報
					Object srcEntity = this.find(cotosComplementTarget, FieldUtils.getFieldValue(fieldValue, "id"));

					// 再帰的な補完処理
					this.complement(fieldValue, srcEntity);
				}
			}

			// List型の場合は、ジェネリクス指定の型を取得
			if (field.getType().equals(List.class)) {

				// Listのジェネリクスの型を取得
				ParameterizedType listType = (ParameterizedType) field.getGenericType();
				Class<?> genericsClass = (Class<?>) listType.getActualTypeArguments()[0];

				// 補完対象のクラスか確認
				CotosComplementTarget cotosComplementTarget = genericsClass.getDeclaredAnnotation(CotosComplementTarget.class);

				@SuppressWarnings("unchecked")
				List<Object> targetChildList = (List<Object>) fieldValue;

				for (Object targetChild : targetChildList) {

					// 補完対象のクラスでない場合は、対象のクラス内から補完対象クラスを検索する
					if (cotosComplementTarget == null) {
						this.findComplementTarget(targetChild);
					} else {
						// 補完元情報
						Object srcEntity = this.find(cotosComplementTarget, FieldUtils.getFieldValue(targetChild, "id"));

						// 再帰的な補完処理
						this.complement(targetChild, srcEntity);
					}
				}
			}
		}
	}

	/**
	 * エンティティの値・アノテーション情報を元に、 MoM権限によりフィルタリングされた情報を補完する
	 * 
	 * @param target
	 *            補完対象
	 * @param src
	 *            補完元情報
	 */
	private void complement(Object target, Object srcEntity) throws IllegalAccessException {

		// ログインユーザーの認証情報を取得
		CotosAuthenticationDetails userInfo = (CotosAuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// INPUTクラスに定義されたフィールドでループ
		for (Field field : ReflectionUtils.getDeclaredFields(target.getClass())) {

			// エンティティのフィールド情報を取得
			Field entityField = FieldUtils.getField(srcEntity.getClass(), field.getName());

			// エンティティのフィールドにJsonIgnoreが指定されている場合
			if (entityField.getAnnotation(JsonIgnore.class) != null) {
				continue;
			}

			// エンティティのフィールドからフィルタリング用アノテーションを取得
			CotosDisclosable cotosDisclosable = entityField.getDeclaredAnnotation(CotosDisclosable.class);

			// フィルタリング用アノテーションが指定されている場合
			if (cotosDisclosable != null) {

				// フィルタリング用アノテーションに対応するユーザーのMoM権限を取得
				AuthLevel userAuthLevel = userInfo.getMomAuthorities().get(cotosDisclosable.momActionDiv()).get(cotosDisclosable.momAuthDiv());

				// アノテーションに指定されたMoM権限レベル以上であれば、補完しない
				boolean disclosable = Integer.compare(Integer.valueOf(userAuthLevel.toValue()), Integer.valueOf(cotosDisclosable.momAuthLevel().toValue())) >= 0;

				if (!disclosable) {
					FieldUtils.setProtectedFieldValue(field.getName(), target, FieldUtils.getFieldValue(srcEntity, field.getName()));
				}

				continue;
			}

			// プリミティブ型は再帰的な補完処理が不要なため、次のループ処理へ
			if (field.getType().isPrimitive()) {
				continue;
			}

			// COTOSのエンティティ/DTOを参照している場合は、再帰的な補完処理を実施する
			if (field.getType().getPackage().getName().startsWith(ENTITY_EXISTS_PACKAGE)) {

				// COTOS用の列挙型は再帰的な補完処理が不要なため、次のループ処理へ
				if (field.getType().isEnum()) {
					continue;
				}

				// エンティティが存在する場合のみ、補完処理を実施する
				if (FieldUtils.getFieldValue(srcEntity, entityField.getName()) != null) {
					// 再帰的な補完処理
					this.complement(FieldUtils.getFieldValue(target, field.getName()), FieldUtils.getFieldValue(srcEntity, entityField.getName()));
				}
				continue;
			}

			// COTOSのエンティティ/DTOのListの場合は、要素に対して再帰的な補完処理を実施する
			if (field.getType().equals(List.class)) {

				@SuppressWarnings("unchecked")
				List<Object> targetChildList = (List<Object>) FieldUtils.getFieldValue(target, field.getName());
				@SuppressWarnings("unchecked")
				List<Object> childEntityList = (List<Object>) FieldUtils.getFieldValue(srcEntity, field.getName());

				for (Object targetChild : targetChildList) {

					// 補完対象の子要素のIDを取得
					Object targetChildId = FieldUtils.getFieldValue(targetChild, "id");

					for (Object childEntity : childEntityList) {

						// 補完元の子要素のIDを取得
						Object childEntityId = FieldUtils.getFieldValue(childEntity, "id");

						// 補完対象、補完元のIDが一致する場合は、再帰的な補完処理を実施する。
						if (targetChildId.equals(childEntityId)) {
							this.complement(targetChild, childEntity);
						}
					}
				}

				continue;
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object find(CotosComplementTarget cotosComplementTarget, Object id) {
		// コンテキストからRepositoryを取得
		CrudRepository repository = (CrudRepository) context.getBean(cotosComplementTarget.repository());
		Object found = repository.findById((Serializable) id);

		// 永続化解除
		entityManager.clear();

		return found;
	}
}
