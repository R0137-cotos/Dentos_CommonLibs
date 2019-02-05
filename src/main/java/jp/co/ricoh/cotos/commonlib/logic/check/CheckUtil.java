package jp.co.ricoh.cotos.commonlib.logic.check;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonValue;

import jp.co.ricoh.cotos.commonlib.dto.result.MessageInfo;
import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.exception.ErrorInfo;
import jp.co.ricoh.cotos.commonlib.logic.message.MessageUtil;

/**
 * チェック共通クラス
 */
@Component
public class CheckUtil {

	@Autowired
	MessageUtil messageUtil;

	/**
	 * 社員モード(パラメータ,操作者)
	 */
	public enum EmpMode {

		パラメータ, 操作者;

		@JsonValue
		public String toValue() {
			return this.name();
		}
	}

	/**
	 * Entityチェック
	 *
	 * @param result
	 *            Entityチェック結果
	 * @param ignoreFields
	 *            Entityチェック対象外項目名配列(未指定可)
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkEntity(BindingResult result, String... ignoreFields) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();
		if (result.hasErrors()) {
			for (FieldError fieldError : result.getFieldErrors()) {

				if (Arrays.asList(ignoreFields).contains(fieldError.getField())) {
					continue;
				}

				String fieldNm = messageUtil.convertSingleValue(fieldError.getField());
				String errCode = fieldError.getCode();
				String max = null;
				String min = null;
				if (fieldError.getArguments() != null && fieldError.getArguments().length > 0) {
					if ("Max".equals(errCode) || "Size".equals(errCode)) {
						max = fieldError.getArguments()[1].toString();
					}
					if ("Min".equals(errCode)) {
						min = fieldError.getArguments()[1].toString();
					}
					if ("DecimalMax".equals(errCode)) {
						max = fieldError.getArguments()[2].toString();
					}
					if ("DecimalMin".equals(errCode)) {
						min = fieldError.getArguments()[2].toString();
					}
				}

				String errKey = null;
				String[] regexList = null;
				// 必須チェック
				if ("NotNull".equals(errCode) || "NotEmpty".equals(errCode)) {
					errKey = "EntityCheckNotNullError";
					regexList = new String[] { fieldNm };
				}
				// 文字列桁数チェック
				if ("Size".equals(errCode)) {
					errKey = "EntityCheckStringSizeError";
					regexList = new String[] { fieldNm, max };
				}
				// 数値桁数チェック
				if ("Max".equals(errCode) || "DecimalMax".equals(errCode)) {
					errKey = "EntityCheckNumberMaxError";
					regexList = new String[] { fieldNm, max };
				}
				// 数値桁数チェック
				if ("Min".equals(errCode) || "DecimalMin".equals(errCode)) {
					errKey = "EntityCheckNumberMaxError";
					regexList = new String[] { fieldNm, min };
				}
				MessageInfo messageInfo = messageUtil.createMessageInfo(errKey, regexList);
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorId(messageInfo.getId());
				errorInfo.setErrorMessage(messageInfo.getMsg());
				errorInfoList.add(errorInfo);
			}

			if (!errorInfoList.isEmpty()) {
				throw new ErrorCheckException(errorInfoList);
			}
		}
	}

	/**
	 * リストサイズチェック
	 *
	 * @param list
	 *            チェック対象リスト
	 * @return boolean チェック結果（true：正常 false：異常）
	 */
	public boolean checkListSize(List<T> list) {
		if (list == null || list.size() == 0) {
			BindingResult errors = new BeanPropertyBindingResult(list, "list");
			checkEntity(errors);
			return false;
		}
		for (T element : list) {
			if (element == null) {
				BindingResult errors = new BeanPropertyBindingResult(element, "entity");
				checkEntity(errors);
				return false;
			}
		}
		return true;
	}

	/**
	 * エラー情報追加
	 *
	 * @param errorInfoList
	 *            エラーリスト
	 * @param messageKey
	 *            メッセージキー
	 * @return エラーリスト
	 */
	public List<ErrorInfo> addErrorInfo(List<ErrorInfo> errorInfoList, String messageKey) {
		errorInfoList.add(createErrorInfo(messageKey));
		return errorInfoList;
	}

	/**
	 * エラー情報追加
	 *
	 * @param errorInfoList
	 *            エラーリスト
	 * @param messageKey
	 *            メッセージキー
	 * @param regexList
	 *            メッセージ引数
	 * @return エラーリスト
	 */
	public List<ErrorInfo> addErrorInfo(List<ErrorInfo> errorInfoList, String messageKey, String[] regexList) {
		errorInfoList.add(createErrorInfo(messageKey, regexList));
		return errorInfoList;
	}

	/**
	 * エラー情報追加(項目用)
	 *
	 * @param errorInfoList
	 *            エラーリスト
	 * @param columnNm
	 *            カラム名
	 * @param errorType
	 *            エラータイプ
	 * @return エラーリスト
	 */
	public List<ErrorInfo> addErrorInfoColumnCheck(List<ErrorInfo> errorInfoList, String columnNm, String errorType) {
		errorInfoList.add(createErrorInfoColumnCheck(columnNm, errorType));
		return errorInfoList;
	}

	/**
	 * エラー情報生成
	 *
	 * @param messageKey
	 *            メッセージキー
	 * @return エラーリスト
	 */
	private ErrorInfo createErrorInfo(String messageKey) {

		MessageInfo messageInfo = messageUtil.createMessageInfo(messageKey);

		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorId(messageInfo.getId());
		errorInfo.setErrorMessage(messageInfo.getMsg());
		return errorInfo;
	}

	/**
	 * エラー情報生成
	 *
	 * @param messageKey
	 *            メッセージキー
	 * @param regexList
	 *            メッセージ引数
	 * @return エラーリスト
	 */
	private ErrorInfo createErrorInfo(String messageKey, String[] regexList) {

		MessageInfo messageInfo = messageUtil.createMessageInfo(messageKey, regexList);

		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorId(messageInfo.getId());
		errorInfo.setErrorMessage(messageInfo.getMsg());
		return errorInfo;
	}

	/**
	 * エラー情報生成(項目用)
	 *
	 * @param columnNm
	 *            カラム名
	 * @param messageKey
	 *            メッセージキー
	 * @return エラーリスト
	 */
	private ErrorInfo createErrorInfoColumnCheck(String columnNm, String messageKey) {

		MessageInfo messageInfo = messageUtil.createMessageInfo(messageKey);

		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorId(messageInfo.getId());
		errorInfo.setErrorMessage(messageUtil.convertSingleValue(columnNm) + messageInfo.getMsg());
		return errorInfo;
	}
}