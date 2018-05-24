package jp.co.ricoh.cotos.commonlib.common.check;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import jp.co.ricoh.cotos.commonlib.common.entity.Communication;
import jp.co.ricoh.cotos.commonlib.common.entity.Communication.CommunicationCategory;
import jp.co.ricoh.cotos.commonlib.common.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.common.exception.ErrorInfo;

/**
 * コミュニケーション機能別チェック管理クラス
 */
@Component
public class FunctionCheckCommunication {

	@Autowired
	CheckUtil checkUtil;
	@Autowired
	DBFoundCheck dBFoundCheck;

	/**
	 * コミュニケーション情報作成チェック処理
	 * 
	 * @param communication
	 *            コミュニケーション情報
	 * @param result
	 *            Entityチェック結果
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkCommunicationCreate(Communication communication, BindingResult result) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// Entityチェック
		checkCommunicationEntity(errorInfoList, communication, result);
	}

	/**
	 * コミュニケーション情報更新チェック処理
	 * 
	 * @param communication
	 *            コミュニケーション情報
	 * @param result
	 *            Entityチェック結果
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkCommunicationUpdate(Communication communication, BindingResult result) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// Entityチェック
		checkCommunicationEntity(errorInfoList, communication, result);
	}

	/**
	 * TOP画面処理中タスク一覧取得チェック処理
	 * 
	 * @param momEmployeeId
	 *            MoM社員ID
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkCommunicationFindProcessingTaskList(String momEmployeeId) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// MoM社員存在チェック
		checkFoundEmployeeMaster(errorInfoList, momEmployeeId);
	}

	/**
	 * コミュニケーション情報更新メール送信チェック処理
	 * 
	 * @param communication
	 *            コミュニケーション情報
	 * @param result
	 *            Entityチェック結果
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkCommunicationUpdateAndSendMail(Communication communication, BindingResult result) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// Entityチェック
		checkCommunicationEntity(errorInfoList, communication, result);
		// 伝達者MoM社員ID存在チェック
		checkFoundEmployeeMaster(errorInfoList, communication.getRequestFrom());
		// 被伝達者MoM社員ID存在チェック
		checkFoundEmployeeMaster(errorInfoList, communication.getRequestTo());
	}

	/**
	 * 再承認依頼時コミュニケーション情報ステータス更新チェック処理
	 * 
	 * @param targetDocKey
	 *            対象文書キー
	 * @param communicationCategory
	 *            コミュニケーションカテゴリー
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkCommunicationUpdateStatusReApprovalRequest(String targetDocKey, String communicationCategory) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		if (StringUtils.isBlank(targetDocKey)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "ArgumentNullErrorTargetDocKey", "ArgumentNullErrorTargetDocKeyMsg");
			throw new ErrorCheckException(errorInfoList);
		}

		if (StringUtils.isBlank(communicationCategory)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "ArgumentNullErrorCommunicationCategory", "ArgumentNullErrorCommunicationCategoryMsg");
			throw new ErrorCheckException(errorInfoList);
		}

		try {
			CommunicationCategory.fromValue(communicationCategory);
		} catch (IllegalArgumentException ill) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "DoesNotExistCommunicationCategory", "DoesNotExistCommunicationCategoryMsg");
			throw new ErrorCheckException(errorInfoList);
		}
	}

	/**
	 * コミュニケーション情報チェック
	 * 
	 * @param errorInfoList
	 *            エラーリスト
	 * @param result
	 *            Entityチェック結果
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	private void checkCommunicationEntity(List<ErrorInfo> errorInfoList, Communication communication, BindingResult result) throws ErrorCheckException {
		if (null == communication) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "ArgumentNullErrorCommunication", "ArgumentNullErrorCommunicationMsg");
			throw new ErrorCheckException(errorInfoList);
		}

		// Entityチェック
		checkUtil.checkEntity(result);
	}

	/**
	 * 社員マスタ存在チェック
	 * 
	 * @param errorInfoList
	 *            エラーリスト
	 * @param momEmployeeId
	 *            MoM社員ID
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	private void checkFoundEmployeeMaster(List<ErrorInfo> errorInfoList, String momEmployeeId) throws ErrorCheckException {
		if (StringUtils.isBlank(momEmployeeId)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "ArgumentNullErrorMomEmployeeId", "ArgumentNullErrorMomEmployeeIdMsg");
			throw new ErrorCheckException(errorInfoList);
		}

		if (null == dBFoundCheck.existsFoundEmployeeMaster(momEmployeeId)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "MasterDoesNotExistEmployeeMaster", "MasterDoesNotExistEmployeeMasterMsg");
			throw new ErrorCheckException(errorInfoList);
		}
	}
}
