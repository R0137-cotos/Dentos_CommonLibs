package jp.co.ricoh.cotos.commonlib.logic.check;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.exception.ErrorInfo;

/**
 * チェック共通クラス
 */
@Component
public class CheckUtil {

	@Autowired
	MessageSource messageSource;

	/**
	 * Entityチェック
	 * 
	 * @param result
	 *            Entityチェック結果
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkEntity(BindingResult result) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();
		if (result.hasErrors()) {
			result.getAllErrors().stream().forEach(error -> {
				int index = error.getDefaultMessage().indexOf(":");
				String errorId = error.getDefaultMessage().substring(0, index);
				String errorMessage = error.getDefaultMessage().substring(index + 1);
				ErrorInfo errorInfo = new ErrorInfo();
				errorInfo.setErrorId(errorId);
				errorInfo.setErrorMessage(errorMessage);
				errorInfoList.add(errorInfo);
			});
			if (!errorInfoList.isEmpty()) {
				throw new ErrorCheckException(errorInfoList);
			}
		}
	}

	/**
	 * エラー情報追加
	 * 
	 * @param errorInfoList
	 *            エラーリスト
	 * @param errorId
	 *            エラーID
	 * @param errorMessageId
	 *            エラーメッセージID
	 * @return エラーリスト
	 */
	public List<ErrorInfo> addErrorInfo(List<ErrorInfo> errorInfoList, String errorId, String errorMessageId) {
		errorInfoList.add(createErrorInfo(errorId, errorMessageId));
		return errorInfoList;
	}

	/**
	 * エラー情報追加
	 * 
	 * @param errorInfoList
	 *            エラーリスト
	 * @param errorId
	 *            エラーID
	 * @param errorMessageId
	 *            エラーメッセージID
	 * @param regexList
	 *            メッセージ引数
	 * @return エラーリスト
	 */
	public List<ErrorInfo> addErrorInfo(List<ErrorInfo> errorInfoList, String errorId, String errorMessageId, String[] regexList) {
		errorInfoList.add(createErrorInfo(errorId, errorMessageId, regexList));
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
	 * @param errorId
	 *            エラーID
	 * @param errorMessageId
	 *            エラーメッセージID
	 * @return エラーリスト
	 */
	private ErrorInfo createErrorInfo(String errorId, String errorMessageId) {
		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorId(convertMessage(errorId));
		errorInfo.setErrorMessage(convertMessage(errorMessageId));
		return errorInfo;
	}

	/**
	 * エラー情報生成
	 * 
	 * @param errorId
	 *            エラーID
	 * @param errorMessageId
	 *            エラーメッセージID
	 * @param regexList
	 *            メッセージ引数
	 * @return エラーリスト
	 */
	private ErrorInfo createErrorInfo(String errorId, String errorMessageId, String[] regexList) {
		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorId(convertMessage(errorId));
		errorInfo.setErrorMessage(convertMessage(errorMessageId, regexList));
		return errorInfo;
	}

	/**
	 * エラー情報生成(項目用)
	 * 
	 * @param columnNm
	 *            カラム名
	 * @param errorType
	 *            エラータイプ
	 * @return エラーリスト
	 */
	private ErrorInfo createErrorInfoColumnCheck(String columnNm, String errorType) {
		ErrorInfo errorInfo = new ErrorInfo();
		errorInfo.setErrorId(convertMessage(errorType) + " " + columnNm);
		errorInfo.setErrorMessage(convertMessage(columnNm) + convertMessage(errorType + "Msg"));
		return errorInfo;
	}

	/**
	 * メッセージ生成
	 * 
	 * @param id
	 *            ID
	 * @return メッセージ
	 */
	private String convertMessage(String id) {
		return messageSource.getMessage(id, null, Locale.JAPANESE);
	}

	/**
	 * メッセージ生成
	 * 
	 * @param id
	 *            ID
	 * @param regexList
	 *            メッセージ引数
	 * @return メッセージ
	 */
	private String convertMessage(String id, String[] regexList) {
		return messageSource.getMessage(id, regexList, Locale.JAPANESE);
	}
}
