package jp.co.ricoh.cotos.commonlib.logic.check;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementDetailMakeInfo;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWork;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWork.ArrangementWorkStatus;
import jp.co.ricoh.cotos.commonlib.entity.master.ArrangementMaster;
import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.exception.ErrorInfo;
import jp.co.ricoh.cotos.commonlib.logic.check.CheckUtil.EmpMode;
import jp.co.ricoh.cotos.commonlib.repository.arrangement.ArrangementWorkRepository;

/**
 * 手配機能別チェック管理クラス
 */
@Component
public class FunctionCheckArrangement {

	@Autowired
	CheckUtil checkUtil;
	@Autowired
	DBFoundCheck dBFoundCheck;
	@Autowired
	BusinessCheck businessCheck;
	@Autowired
	ArrangementWorkRepository arrangementWorkRepository;

	/**
	 * 手配情報作成チェック処理1回目
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
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "ArgumentNullErrorContractId");
			throw new ErrorCheckException(errorInfoList);
		}

		if (!dBFoundCheck.existsContract(contractId)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "EntityDoesNotExistContract");
			throw new ErrorCheckException(errorInfoList);
		}
		// 操作者MoM社員存在チェック
		existsMomEmployeeId(errorInfoList, operatorId, EmpMode.操作者);
	}

	/**
	 * 手配情報作成チェック処理2回目
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
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "NotFoundErrorSummaryArrangementMasterList");
			throw new ErrorCheckException(errorInfoList);
		}
		// 手配業務に紐づく手配明細存在チェック
		if (CollectionUtils.isEmpty(arrangementDetailMakeInfoList)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "NotFoundErrorSummaryArrangementDetailMakeInfoList");
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
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "ArgumentNullErrorArrangementId");
			throw new ErrorCheckException(errorInfoList);
		}

		if (!dBFoundCheck.existsArrangement(arrangementId)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "EntityDoesNotExistArrangement");
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
		existsArrangementWorkId(errorInfoList, arrangementWorkId);
	}

	/**
	 * 手配業務受付チェック処理
	 * 
	 * @param arrangementWorkList
	 *            手配業務リスト
	 * @param operatorId
	 *            操作者MoM社員ID
	 * @param result
	 *            Entityチェック結果
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkArrangementAcceptWork(List<ArrangementWork> arrangementWorkList, String operatorId, BindingResult result) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 手配業務情報存在チェック
		existsArrangementWorkList(errorInfoList, arrangementWorkList);
		// 操作者MoM社員存在チェック
		existsMomEmployeeId(errorInfoList, operatorId, EmpMode.操作者);
		// Entityチェック
		checkUtil.checkEntity(result);
		// 手配業務ステータスチェック
		for (ArrangementWork arrangementWork : arrangementWorkList) {
			if (businessCheck.existsArrangementWorkStatusMatch(arrangementWork.getArrangementWorkStatus(), ArrangementWorkStatus.受付済み) || businessCheck.existsArrangementWorkStatusMatch(arrangementWork.getArrangementWorkStatus(), ArrangementWorkStatus.対応済み)) {
				errorInfoList = checkUtil.addErrorInfo(errorInfoList, "WrongErrorArrangementWorkStatus", new String[] { ArrangementWorkStatus.受付済み.name() + "または" + ArrangementWorkStatus.対応済み.name() });
				throw new ErrorCheckException(errorInfoList);
			}
		}
	}

	/**
	 * 手配担当者設定チェック処理
	 * 
	 * @param arrangementWorkList
	 *            手配業務リスト
	 * @param employeeId
	 *            MoM社員ID
	 * @param result
	 *            Entityチェック結果
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkArrangementAssignWorker(List<ArrangementWork> arrangementWorkList, String employeeId, BindingResult result) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 手配業務情報存在チェック
		existsArrangementWorkList(errorInfoList, arrangementWorkList);
		// 操作者MoM社員存在チェック
		existsMomEmployeeId(errorInfoList, employeeId, EmpMode.パラメータ);
		// Entityチェック
		checkUtil.checkEntity(result);
	}

	/**
	 * 手配不受理チェック処理
	 * 
	 * @param arrangementWorkList
	 *            手配業務リスト
	 * @param operatorId
	 *            操作者MoM社員ID
	 * @param result
	 *            Entityチェック結果
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkArrangementRefuseWork(List<ArrangementWork> arrangementWorkList, String operatorId, BindingResult result) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 手配業務情報存在チェック
		existsArrangementWorkList(errorInfoList, arrangementWorkList);
		// 操作者MoM社員存在チェック
		existsMomEmployeeId(errorInfoList, operatorId, EmpMode.操作者);
		// Entityチェック
		checkUtil.checkEntity(result);
		// 手配業務ステータスチェック
		for (ArrangementWork arrangementWork : arrangementWorkList) {
			if (!businessCheck.existsArrangementWorkStatusMatch(arrangementWork.getArrangementWorkStatus(), ArrangementWorkStatus.受付済み)) {
				errorInfoList = checkUtil.addErrorInfo(errorInfoList, "WrongNotErrorArrangementWorkStatus", new String[] { ArrangementWorkStatus.受付済み.name() });
				throw new ErrorCheckException(errorInfoList);
			}
		}
	}

	/**
	 * 手配完了取消チェック処理
	 * 
	 * @param arrangementWork
	 *            手配業務情報
	 * @param operatorId
	 *            操作者MoM社員ID
	 * @param result
	 *            Entityチェック結果
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkArrangementCancelCompleteWork(ArrangementWork arrangementWork, String operatorId, BindingResult result) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 手配業務情報存在チェック
		existsArrangementWork(errorInfoList, arrangementWork);
		// 操作者MoM社員存在チェック
		existsMomEmployeeId(errorInfoList, operatorId, EmpMode.操作者);
		// Entityチェック
		checkUtil.checkEntity(result);
		// 手配業務ステータスチェック
		if (!businessCheck.existsArrangementWorkStatusMatch(arrangementWork.getArrangementWorkStatus(), ArrangementWorkStatus.対応済み)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "WrongNotErrorArrangementWorkStatus", new String[] { ArrangementWorkStatus.対応済み.name() });
			throw new ErrorCheckException(errorInfoList);
		}
	}

	/**
	 * 手配完了チェック処理
	 * 
	 * @param arrangementWork
	 *            手配業務情報
	 * @param operatorId
	 *            操作者MoM社員ID
	 * @param result
	 *            Entityチェック結果
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkArrangementCompleteWork(ArrangementWork arrangementWork, String operatorId, BindingResult result) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 手配業務情報存在チェック
		existsArrangementWork(errorInfoList, arrangementWork);
		// 操作者MoM社員存在チェック
		existsMomEmployeeId(errorInfoList, operatorId, EmpMode.操作者);
		// Entityチェック
		checkUtil.checkEntity(result);
		// 手配業務ステータスチェック
		if (!businessCheck.existsArrangementWorkStatusMatch(arrangementWork.getArrangementWorkStatus(), ArrangementWorkStatus.受付済み)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "WrongNotErrorArrangementWorkStatus", new String[] { ArrangementWorkStatus.受付済み.name() });
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
	private void existsArrangementWorkList(List<ErrorInfo> errorInfoList, List<ArrangementWork> arrangementWorkList) throws ErrorCheckException {
		// 手配業務リストNullチェック
		if (CollectionUtils.isEmpty(arrangementWorkList)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "ArgumentNullErrorArrangementWorkList");
			throw new ErrorCheckException(errorInfoList);
		}
		// 手配業務リスト項目Nullチェック
		if (arrangementWorkList.contains(null)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "LackingParameterErrorArrangementWorkList");
			throw new ErrorCheckException(errorInfoList);
		}
		// 手配業務ID存在チェック
		for (ArrangementWork arrangementWork : arrangementWorkList) {
			existsArrangementWork(errorInfoList, arrangementWork);
		}
	}

	/**
	 * 手配業務情報存在チェック
	 * 
	 * @param errorInfoList
	 *            エラーリスト
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	private void existsArrangementWork(List<ErrorInfo> errorInfoList, ArrangementWork arrangementWork) throws ErrorCheckException {
		if (null == arrangementWork) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "ArgumentNullErrorArrangementWork");
			throw new ErrorCheckException(errorInfoList);
		}

		if (!dBFoundCheck.existsArrangementWork(arrangementWork.getId())) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "EntityDoesNotExistArrangementWork");
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
	 * @return チェック結果
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	private void existsArrangementWorkId(List<ErrorInfo> errorInfoList, Long arrangementWorkId) throws ErrorCheckException {
		if (null == arrangementWorkId) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "ArgumentNullErrorArrangementWorkId");
			throw new ErrorCheckException(errorInfoList);
		}

		if (!dBFoundCheck.existsArrangementWork(arrangementWorkId)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "EntityDoesNotExistArrangementWork");
			throw new ErrorCheckException(errorInfoList);
		}
	}

	/**
	 * 社員マスタ存在チェック
	 * 
	 * @param errorInfoList
	 *            エラーリスト
	 * @param momEmployeeId
	 *            MoM社員ID
	 * @param empMode
	 *            社員モード
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	private void existsMomEmployeeId(List<ErrorInfo> errorInfoList, String momEmployeeId, EmpMode empMode) throws ErrorCheckException {
		if (StringUtils.isBlank(momEmployeeId)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "ArgumentNullErrorMomEmployeeId", new String[] { empMode.name() });
			throw new ErrorCheckException(errorInfoList);
		}

		if (!dBFoundCheck.existsEmployeeMaster(momEmployeeId)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "MasterDoesNotExistEmployeeMaster", new String[] { empMode.name() });
			throw new ErrorCheckException(errorInfoList);
		}
	}
}
