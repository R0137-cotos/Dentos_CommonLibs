package jp.co.ricoh.cotos.commonlib.common.check;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.common.entity.ArrangementDetailMakeInfo;
import jp.co.ricoh.cotos.commonlib.common.entity.ArrangementWork;
import jp.co.ricoh.cotos.commonlib.common.entity.ArrangementWork.ArrangementWorkStatus;
import jp.co.ricoh.cotos.commonlib.common.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.common.exception.ErrorInfo;
import jp.co.ricoh.cotos.commonlib.common.master.ArrangementMaster;

/**
 * 手配機能別チェック管理クラス
 */
@Component
public class FunctionCheckArrangement {

	@Autowired
	CheckUtil checkMessageUtil;
	@Autowired
	DBFoundCheck dBFoundCheck;
	@Autowired
	BusinessCheck businessCheck;

	/**
	 * 手配情報作成チェック処理第1弾
	 * 
	 * @param contractId
	 *            契約ID
	 * @param operatorId
	 *            操作者MoM社員ID
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkArrangementCreateFirst(Long contractId, String operatorId) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		if (null == contractId) {
			errorInfoList = checkMessageUtil.addErrorInfo(errorInfoList, "ArgumentNullErrorContractId", "ArgumentNullErrorContractIdMsg");
			throw new ErrorCheckException(errorInfoList);
		}

		if (null == dBFoundCheck.existsFoundContract(contractId)) {
			errorInfoList = checkMessageUtil.addErrorInfo(errorInfoList, "EntityDoesNotExistContract", "EntityDoesNotExistContractMsg");
			throw new ErrorCheckException(errorInfoList);
		}
		// 操作者MoM社員存在チェック
		checkFoundEmployeeMaster(errorInfoList, operatorId);
	}

	/**
	 * 手配情報作成チェック処理第2弾
	 * 
	 * @param summaryArrangementMasterList
	 *            手配業務単位にサマリーした手配マスタ
	 * @param arrangementDetailMakeInfoList
	 *            手配業務に紐づく手配明細
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkArrangementCreateSecond(List<ArrangementMaster> summaryArrangementMasterList, List<ArrangementDetailMakeInfo> arrangementDetailMakeInfoList) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 手配業務単位にサマリーした手配マスタ存在チェック
		if (CollectionUtils.isEmpty(summaryArrangementMasterList)) {
			errorInfoList = checkMessageUtil.addErrorInfo(errorInfoList, "NotFoundErrorSummaryArrangementMasterList", "NotFoundErrorSummaryArrangementMasterListMsg");
			throw new ErrorCheckException(errorInfoList);
		}
		// 手配業務に紐づく手配明細存在チェック
		if (CollectionUtils.isEmpty(arrangementDetailMakeInfoList)) {
			errorInfoList = checkMessageUtil.addErrorInfo(errorInfoList, "NotFoundErrorSummaryArrangementDetailMakeInfoList", "NotFoundErrorSummaryArrangementDetailMakeInfoListMsg");
			throw new ErrorCheckException(errorInfoList);
		}
	}

	/**
	 * 手配情報取得チェック処理
	 * 
	 * @param arrangementId
	 *            手配ID
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkArrangementFind(Long arrangementId) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		if (null == arrangementId) {
			errorInfoList = checkMessageUtil.addErrorInfo(errorInfoList, "ArgumentNullErrorArrangementId", "ArgumentNullErrorArrangementIdMsg");
			throw new ErrorCheckException(errorInfoList);
		}

		if (null == dBFoundCheck.existsFoundArrangement(arrangementId)) {
			errorInfoList = checkMessageUtil.addErrorInfo(errorInfoList, "EntityDoesNotExistArrangement", "EntityDoesNotExistArrangementMsg");
			throw new ErrorCheckException(errorInfoList);
		}
	}

	/**
	 * 手配業務情報取得チェック処理
	 * 
	 * @param arrangementWorkId
	 *            手配業務ID
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkArrangementWorkFind(Long arrangementWorkId) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 手配業務情報存在チェック
		checkFoundArrangementWorkId(errorInfoList, arrangementWorkId);
	}

	/**
	 * 手配業務受付チェック処理第1弾
	 * 
	 * @param arrangementWorkIdList
	 *            手配業務IDリスト
	 * @param operatorId
	 *            操作者MoM社員ID
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkArrangementAcceptWorkFirst(List<Long> arrangementWorkIdList, String operatorId) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 手配業務情報存在チェック
		checkFoundArrangementWork(errorInfoList, arrangementWorkIdList);
		// 操作者MoM社員存在チェック
		checkFoundEmployeeMaster(errorInfoList, operatorId);
	}

	/**
	 * 手配業務受付チェック処理第2弾
	 * 
	 * @param arrangementWork
	 *            手配業務
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkArrangementAcceptWorkSecond(ArrangementWork arrangementWork) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 手配業務ステータスチェック
		if (businessCheck.existsArrangementWorkStatusMatch(arrangementWork.getArrangementWorkStatus(), ArrangementWorkStatus.受付済み) || businessCheck.existsArrangementWorkStatusMatch(arrangementWork.getArrangementWorkStatus(), ArrangementWorkStatus.対応済み)) {
			errorInfoList = checkMessageUtil.addErrorInfo(errorInfoList, "WrongErrorArrangementWorkStatus", "WrongErrorArrangementWorkStatusMsg", new String[] { ArrangementWorkStatus.受付済み.name() + "または" + ArrangementWorkStatus.対応済み.name() });
			throw new ErrorCheckException(errorInfoList);
		}
	}

	/**
	 * 手配担当者設定チェック処理
	 * 
	 * @param arrangementWorkIdList
	 *            手配業務IDリスト
	 * @param employeeId
	 *            MoM社員ID
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkArrangementAssignWorker(List<Long> arrangementWorkIdList, String employeeId) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 手配業務情報存在チェック
		checkFoundArrangementWork(errorInfoList, arrangementWorkIdList);
		// MoM社員存在チェック
		checkFoundEmployeeMaster(errorInfoList, employeeId);
	}

	/**
	 * 手配不受理チェック処理第1弾
	 * 
	 * @param arrangementWorkIdList
	 *            手配業務IDリスト
	 * @param operatorId
	 *            操作者MoM社員ID
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkArrangementRefuseWorkFirst(List<Long> arrangementWorkIdList, String operatorId) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 手配業務情報存在チェック
		checkFoundArrangementWork(errorInfoList, arrangementWorkIdList);
		// 操作者MoM社員存在チェック
		checkFoundEmployeeMaster(errorInfoList, operatorId);
	}

	/**
	 * 手配不受理チェック処理第2弾
	 * 
	 * @param arrangementWork
	 *            手配業務
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkArrangementRefuseWorkSecond(ArrangementWork arrangementWork) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 手配業務ステータスチェック
		if (!businessCheck.existsArrangementWorkStatusMatch(arrangementWork.getArrangementWorkStatus(), ArrangementWorkStatus.受付済み)) {
			errorInfoList = checkMessageUtil.addErrorInfo(errorInfoList, "WrongNotErrorArrangementWorkStatus", "WrongNotErrorArrangementWorkStatusMsg", new String[] { ArrangementWorkStatus.受付済み.name() });
			throw new ErrorCheckException(errorInfoList);
		}
	}

	/**
	 * 手配完了取消チェック処理
	 * 
	 * @param arrangementWorkId
	 *            手配業務ID
	 * @param operatorId
	 *            操作者MoM社員ID
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkArrangementCancelCompleteWork(Long arrangementWorkId, String operatorId) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 手配業務情報存在チェック
		ArrangementWork arrangementWork = checkFoundArrangementWorkId(errorInfoList, arrangementWorkId);
		// 操作者MoM社員存在チェック
		checkFoundEmployeeMaster(errorInfoList, operatorId);
		// 手配業務ステータスチェック
		if (!businessCheck.existsArrangementWorkStatusMatch(arrangementWork.getArrangementWorkStatus(), ArrangementWorkStatus.対応済み)) {
			errorInfoList = checkMessageUtil.addErrorInfo(errorInfoList, "WrongNotErrorArrangementWorkStatus", "WrongNotErrorArrangementWorkStatusMsg", new String[] { ArrangementWorkStatus.対応済み.name() });
			throw new ErrorCheckException(errorInfoList);
		}
	}

	/**
	 * 手配完了チェック処理
	 * 
	 * @param arrangementWorkId
	 *            手配業務ID
	 * @param operatorId
	 *            操作者MoM社員ID
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkArrangementCompleteWork(Long arrangementWorkId, String operatorId) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 手配業務情報存在チェック
		ArrangementWork arrangementWork = checkFoundArrangementWorkId(errorInfoList, arrangementWorkId);
		// 操作者MoM社員存在チェック
		checkFoundEmployeeMaster(errorInfoList, operatorId);
		// 手配業務ステータスチェック
		if (!businessCheck.existsArrangementWorkStatusMatch(arrangementWork.getArrangementWorkStatus(), ArrangementWorkStatus.受付済み)) {
			errorInfoList = checkMessageUtil.addErrorInfo(errorInfoList, "WrongNotErrorArrangementWorkStatus", "WrongNotErrorArrangementWorkStatusMsg", new String[] { ArrangementWorkStatus.受付済み.name() });
			throw new ErrorCheckException(errorInfoList);
		}
	}

	/**
	 * 手配業務情報存在チェック
	 * 
	 * @param errorInfoList
	 *            エラーリスト
	 * @param arrangementWorkIdList
	 *            手配業務IDリスト
	 * @return 契約情報
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	private void checkFoundArrangementWork(List<ErrorInfo> errorInfoList, List<Long> arrangementWorkIdList) throws ErrorCheckException {
		// 手配業務IDリストNullチェック
		if (CollectionUtils.isEmpty(arrangementWorkIdList)) {
			errorInfoList = checkMessageUtil.addErrorInfo(errorInfoList, "ArgumentNullErrorArrangementWorkIdList", "ArgumentNullErrorArrangementWorkIdListMsg");
			throw new ErrorCheckException(errorInfoList);
		}
		// 手配業務IDリスト項目Nullチェック
		if (arrangementWorkIdList.contains(null)) {
			errorInfoList = checkMessageUtil.addErrorInfo(errorInfoList, "LackingParameterErrorArrangementWorkIdList", "LackingParameterErrorArrangementWorkIdListMsg");
			throw new ErrorCheckException(errorInfoList);
		}
		// 手配業務ID存在チェック
		if (!businessCheck.existsArrangementWork(arrangementWorkIdList)) {
			errorInfoList = checkMessageUtil.addErrorInfo(errorInfoList, "EntityDoesNotExistArrangementWork", "EntityDoesNotExistArrangementWorkMsg");
			throw new ErrorCheckException(errorInfoList);
		}
	}

	/**
	 * 手配業務情報存在チェック
	 * 
	 * @param errorInfoList
	 *            エラーリスト
	 * @param arrangementWorkId
	 *            手配業務ID
	 * @return 契約情報
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	private ArrangementWork checkFoundArrangementWorkId(List<ErrorInfo> errorInfoList, Long arrangementWorkId) throws ErrorCheckException {
		if (null == arrangementWorkId) {
			errorInfoList = checkMessageUtil.addErrorInfo(errorInfoList, "ArgumentNullErrorArrangementWorkId", "ArgumentNullErrorArrangementWorkIdMsg");
			throw new ErrorCheckException(errorInfoList);
		}

		ArrangementWork arrangementWork = dBFoundCheck.existsFoundArrangementWork(arrangementWorkId);
		if (null == dBFoundCheck.existsFoundArrangementWork(arrangementWorkId)) {
			errorInfoList = checkMessageUtil.addErrorInfo(errorInfoList, "EntityDoesNotExistArrangementWork", "EntityDoesNotExistArrangementWorkMsg");
			throw new ErrorCheckException(errorInfoList);
		}

		return arrangementWork;
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
			errorInfoList = checkMessageUtil.addErrorInfo(errorInfoList, "ArgumentNullErrorMomEmployeeId", "ArgumentNullErrorMomEmployeeIdMsg");
			throw new ErrorCheckException(errorInfoList);
		}

		if (null == dBFoundCheck.existsFoundEmployeeMaster(momEmployeeId)) {
			errorInfoList = checkMessageUtil.addErrorInfo(errorInfoList, "MasterDoesNotExistEmployeeMaster", "MasterDoesNotExistEmployeeMasterMsg");
			throw new ErrorCheckException(errorInfoList);
		}
	}
}
