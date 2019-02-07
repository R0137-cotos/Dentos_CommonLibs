package jp.co.ricoh.cotos.commonlib;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Assert;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import jp.co.ricoh.cotos.commonlib.exception.ErrorInfo;
import jp.co.ricoh.cotos.commonlib.security.bean.ParamterCheckResult;

@Component
public class TestTools {

	/**
	 * パラメータチェックで使用するエラーID
	 *
	 * @author hideto.yamanaka
	 *
	 */
	public enum ParameterErrorIds {
		/** NotNull、NotEmpty：EntityCheckNotNullError（{0}が設定されていません。） */
		ROT00013,
		/** Size：EntityCheckStringSizeError（{0}は最大文字数（{1}）を超えています。） */
		ROT00014,
		/** Max：EntityCheckNumberMaxError（{0}は最大値（{1}）を超えています。） */
		ROT00015,
		/** Min：EntityCheckNumberMinError（{0}は最小値（{1}）を下回っています。） */
		ROT00027,
		/** Digits：EntityCheckNumberDigitsError（{0}は小数点以下{1}桁を超えています。）*/
		ROT00028;
	}

	public <T> String findNullProperties(T entity) throws Exception {
		Map<String, String> entityMap = BeanUtils.describe(entity);
		Optional<String> propertyName = BeanUtils.describe(entity).keySet().stream().filter(key -> entityMap.get(key) == null).findFirst();

		return propertyName.isPresent() ? propertyName.get() : null;
	}

	/**
	 * エンティティクラスのフィールドの設定値に null が含まれるか
	 *
	 * @param entity
	 * @throws Exception
	 */
	public void assertColumnsNotNull(EntityBaseMaster entity) throws Exception {
		Assert.assertTrue(hasNullColumn(entity) == false);
	}

	/**
	 * エンティティクラスのフィールドの設定値に null が含まれるか
	 *
	 * @param entity
	 * @throws Exception
	 */

	public void assertColumnsNotNull(EntityBase entity) throws Exception {
		Assert.assertTrue(hasNullColumn(entity) == false);
	}

	/**
	 * エンティティクラスのフィールドの設定値に null が含まれるかどうかを判定する
	 * ただしフィールドの型がリスト、エンティティクラスだった場合、判定処理をスキップする
	 *
	 * @param entity
	 *            エンティティ
	 * @return boolean 判定結果（true：フィールドの設定値が null の項目を含む false：フィールドの設定値はすべて null
	 *         以外である）
	 * @throws Exception
	 */
	public boolean hasNullColumn(EntityBaseMaster entity) throws Exception {
		for (Field field : entity.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			if (field.getType() == List.class)
				continue;
			if (field.getType() == EntityBaseMaster.class)
				continue;
			if (field.get(entity) == null)
				return true;
		}
		return false;
	}

	public boolean hasNullColumn(EntityBase entity) throws Exception {
		for (Field field : entity.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			if (field.getType() == MultipartFile.class)
				continue;
			if (field.get(entity) == null)
				return true;
		}
		return false;

	}

	/**
	 * 入力チェックエラーが発生しなことを確認するアサーション
	 *
	 * @param result
	 *            Entity のパラメータチェックの実行結果
	 */
	public void assertValidationOk(ParamterCheckResult result) {
		Assert.assertTrue(result == null || result.getErrorInfoList() == null || result.getErrorInfoList().size() == 0);
	}

	/**
	 * 入力チェックエラーが発生するを確認するアサーション
	 *
	 * @param result
	 *            Entity のパラメータチェックの実行結果
	 */
	public void assertValidationNg(ParamterCheckResult result) {
		Assert.assertTrue(result != null && result.getErrorInfoList() != null && result.getErrorInfoList().size() > 0);
	}

	/**
	 * ErrorInfo のエラー ID が全て指定したものと同じであるかどうかを判定する
	 *
	 * @param errorInfoList
	 *            ErrorInfo のリスト
	 * @param paramterErrorId
	 *            エラーID
	 * @return boolean 判定結果（true：全て一致 false：不一致あり）
	 */
	public boolean errorIdMatchesAll(List<ErrorInfo> errorInfoList, ParameterErrorIds paramterErrorId) {
		return (errorInfoList.size() == (int) errorInfoList.stream().filter(info -> info != null && paramterErrorId.toString().equals(info.getErrorId())).count());
	}

	/**
	 * ErrorInfo のエラー ID が全て指定したものと同じ物が一つだけ存在するかどうかを判定する
	 *
	 * @param errorInfoList
	 *            ErrorInfo のリスト
	 * @param paramterErrorId
	 *            エラーID
	 * @return boolean 判定結果（true：全て一致 false：不一致あり）
	 */
	public boolean errorIdMatchesOne(List<ErrorInfo> errorInfoList, ParameterErrorIds paramterErrorId) {
		return (1 == (int) errorInfoList.stream().filter(info -> info != null && paramterErrorId.toString().equals(info.getErrorId())).count());
	}

	/**
	 * ErrorInfo のエラー メッセージ が指定したものと同じ物が一つだけ存在するかどうかを判定する
	 *
	 * @param errorInfoList
	 *            ErrorInfo のリスト
	 * @param paramterErrorMessage
	 *            エラーメッセージ
	 * @return boolean 判定結果（true：一つ一致 false：一致無しまたは二つ以上一致）
	 */
	public boolean errorMessageMatchesOne(List<ErrorInfo> errorInfoList, String paramterErrorMessage) {
		return (1 == (int) errorInfoList.stream().filter(info -> info != null && paramterErrorMessage.toString().equals(info.getErrorMessage())).count());
	}

}