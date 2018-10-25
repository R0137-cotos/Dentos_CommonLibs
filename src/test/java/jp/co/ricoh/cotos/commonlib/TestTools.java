package jp.co.ricoh.cotos.commonlib;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Assert;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import jp.co.ricoh.cotos.commonlib.exception.ErrorInfo;
import jp.co.ricoh.cotos.commonlib.security.bean.ParamterCheckResult;

@Component
public class TestTools {

	/**
	 * パラメータチェックで使用するエラーID
	 * @author hideto.yamanaka
	 *
	 */
	public enum ParameterErrorIds {
		/** NotNull、NotEmpty：EntityCheckNotNullError（{0}が設定されていません。） */
		ROT00013,
		/** Size：EntityCheckStringSizeError（{0}は最大文字数（{1}）を超えています。） */
		ROT00014,
		/** Max：EntityCheckNumberMaxError（{0}は最大値（{1}）を超えています。） */
		ROT00015;
	}

	public <T> String findNullProperties(T entity) throws Exception {
		Map<String, String> entityMap = BeanUtils.describe(entity);
		Optional<String> propertyName = BeanUtils.describe(entity).keySet().stream()
				.filter(key -> entityMap.get(key) == null).findFirst();

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

			if (field.get(entity) == null)
				return true;
		}
		return false;

	}

	//	/**
	//	 * 指定した Entity クラスの Validation を実行する
	//	 * @param entity
	//	 *            エンティティ
	//	 * @param testSecurityController
	//	 *            TestSecurityController
	//	 * @param headersProperties
	//	 *            HeadersProperties
	//	 * @param localServerPort
	//	 *            ポート番号
	//	 * @return List<ErrorInfo> Validation の実行結果（正常：null　異常：ErrorInfoのリスト）
	//	 */
	//	public List<ErrorInfo> executeEntityValidation(Object entity, TestSecurityController testSecurityController,
	//			HeadersProperties headersProperties, int localServerPort) {
	//
	//		BindingResult bindingResult = null;
	//		final String WITHIN_PERIOD_JWT = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJvcmlnaW4iOiJjb3Rvcy5yaWNvaC5jby5qcCIsInNpbmdsZVVzZXJJZCI6InNpZCIsIm1vbUVtcElkIjoibWlkIiwiZXhwIjoyNTM0MDIyNjgzOTl9.Apmi4uDwtiscf9WgVIh5Rx1DjoZX2eS7H2YlAGayOsQ";
	//		RestTemplate rest = initRest(WITHIN_PERIOD_JWT, headersProperties);
	//		rest.postForEntity(loadTopURL(localServerPort) + "test/api/xxx?isSuccess=true&hasBody=false", entity,
	//				ErrorInfo[].class);
	//
	//		return testSecurityController.callEntityValidation(entity, bindingResult);
	//	}

	//	private RestTemplate initRest(final String header, final HeadersProperties headersProperties) {
	//		RestTemplate rest = new RestTemplate();
	//		if (null != header) {
	//			rest.setInterceptors(
	//					Stream.concat(rest.getInterceptors().stream(), Arrays.asList(new ClientHttpRequestInterceptor() {
	//						@Override
	//						public ClientHttpResponse intercept(HttpRequest request, byte[] body,
	//								ClientHttpRequestExecution execution) throws IOException {
	//							System.out.println("initRest Start");
	//							System.out.println(request.getURI());
	//							System.out.println(request.getMethod());
	//							request.getHeaders().add(headersProperties.getAuthorization(), "Bearer " + header);
	//							System.out.println(request.getHeaders());
	//							System.out.println("initRest End");
	//							return execution.execute(request, body);
	//						}
	//					}).stream()).collect(Collectors.toList()));
	//		}
	//		return rest;
	//	}

	//	private String loadTopURL(int localServerPort) {
	//		return "http://localhost:" + localServerPort + "/";
	//	}

	/**
	 * 入力チェックエラーが発生しなことを確認するアサーション
	 * @param result Entity のパラメータチェックの実行結果
	 */
	public void assertValidationOk(ParamterCheckResult result) {
		Assert.assertTrue(result == null || result.getErrorInfoList() == null || result.getErrorInfoList().size() == 0);
	}

	/**
	 * 入力チェックエラーが発生するを確認するアサーション
	 * @param result Entity のパラメータチェックの実行結果
	 */
	public void assertValidationNg(ParamterCheckResult result) {
		Assert.assertTrue(result != null && result.getErrorInfoList() != null && result.getErrorInfoList().size() > 0);
	}

	/**
	 * ErrorInfo のエラー ID が全て指定したものと同じであるかどうかを判定する
	 * @param errorInfoList    ErrorInfo のリスト
	 * @param paramterErrorId エラーID
	 * @return boolean 判定結果（true：全て一致　false：不一致あり）
	 */
	public boolean errorIdMatchesAll(List<ErrorInfo> errorInfoList, ParameterErrorIds paramterErrorId) {
		return (errorInfoList.size() == (int )errorInfoList.stream().filter( info -> info != null && paramterErrorId.toString().equals(info.getErrorId())).count());
	}

}
