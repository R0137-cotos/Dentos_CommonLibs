package jp.co.ricoh.cotos.commonlib.logic.check;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import jp.co.ricoh.cotos.commonlib.entity.contract.Contract;
import jp.co.ricoh.cotos.commonlib.entity.contract.Contract.ContractStatus;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractApprovalRouteNode;
import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation;
import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation.Status;
import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.exception.ErrorInfo;
import jp.co.ricoh.cotos.commonlib.logic.check.CheckUtil.EmpMode;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.EstimationRepository;

/**
 * 契約機能別チェック管理クラス
 */
@Component
public class FunctionCheckContract {

	@Autowired
	CheckUtil checkUtil;
	@Autowired
	DBFoundCheck dBFoundCheck;
	@Autowired
	BusinessCheck businessCheck;
	@Autowired
	EstimationRepository estimationRepository;
	@Autowired
	ContractRepository contractRepository;

	/**
	 * 契約情報作成チェック処理
	 * 
	 * @param estimationId
	 *            見積ID
	 * @param operatorId
	 *            操作者MoM社員ID
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkContractCreate(Long estimationId, String operatorId) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		if (null == estimationId) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "ArgumentNullErrorEstimationId", "ArgumentNullErrorEstimationIdMsg");
			throw new ErrorCheckException(errorInfoList);
		}

		if (!dBFoundCheck.existsEstimation(estimationId)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "EntityDoesNotExistEstimation", "EntityDoesNotExistEstimationMsg");
			throw new ErrorCheckException(errorInfoList);
		}

		// 操作者MoM社員存在チェック
		existsMomEmployeeId(errorInfoList, operatorId, EmpMode.操作者);
		// 見積ステータスチェック
		Estimation estimation = estimationRepository.findOne(estimationId);
		if (!businessCheck.existsEstimationStatusMatch(estimation.getStatus(), Status.受注)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "WrongNotErrorEstimationStatus", "WrongNotErrorEstimationStatusMsg", new String[] { Status.受注.name() });
			throw new ErrorCheckException(errorInfoList);
		}
	}

	/**
	 * 契約情報取得チェック処理
	 * 
	 * @param contractId
	 *            契約ID
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkContractFind(Long contractId) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 契約情報存在チェック
		existsContractId(errorInfoList, contractId);
	}

	/**
	 * 契約情報更新チェック処理
	 * 
	 * @param contract
	 *            契約情報
	 * @param operatorId
	 *            操作者MoM社員ID
	 * @param result
	 *            Entityチェック結果
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkContractUpdate(Contract contract, String operatorId, BindingResult result) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 契約情報存在チェック
		existsContract(errorInfoList, contract);
		// 操作者MoM社員存在チェック
		existsMomEmployeeId(errorInfoList, operatorId, EmpMode.操作者);
		// Entityチェック
		checkUtil.checkEntity(result);
		// 解約予定日非必須チェック
		if (null != contract.getCancelScheduledDate()) {
			errorInfoList = checkUtil.addErrorInfoColumnCheck(errorInfoList, "CancelScheduledDate", "EmptyError");
			throw new ErrorCheckException(errorInfoList);
		}
	}

	/**
	 * 契約情報承認者取得チェック処理
	 * 
	 * @param contractId
	 *            契約ID
	 * @param momEmployeeId
	 *            MoM社員ID
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkContractFindApprover(Long contractId, String momEmployeeId) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 契約情報存在チェック
		existsContractId(errorInfoList, contractId);
		// 操作者MoM社員存在チェック
		existsMomEmployeeId(errorInfoList, momEmployeeId, EmpMode.パラメータ);
	}

	/**
	 * 契約情報代理承認者設定チェック処理
	 * 
	 * @param contractApprovalRouteNode
	 *            契約承認ルートノード
	 * @param operatorId
	 *            操作者MoM社員ID
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkContractRegisterSubApproverEmployee(ContractApprovalRouteNode contractApprovalRouteNode, String operatorId) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		if (null == contractApprovalRouteNode) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "ArgumentNullErrorContractApprovalRouteNode", "ArgumentNullErrorContractApprovalRouteNodeMsg");
			throw new ErrorCheckException(errorInfoList);
		}

		if (!dBFoundCheck.existsContractApprovalRouteNode(contractApprovalRouteNode.getId())) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "EntityDoesNotExistContractApprovalRouteNode", "EntityDoesNotExistContractApprovalRouteNodeMsg");
			throw new ErrorCheckException(errorInfoList);
		}

		// 操作者MoM社員存在チェック
		existsMomEmployeeId(errorInfoList, operatorId, EmpMode.操作者);
		// 承認者と代理承認者が重複してないか確認
		if (!businessCheck.existsContractApprovalRouteApproverDuplication(contractApprovalRouteNode)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "DuplicationErrorContractSubApproverEmployee", "DuplicationErrorContractSubApproverEmployeeMsg");
			throw new ErrorCheckException(errorInfoList);
		}
	}

	/**
	 * 契約情報承認依頼チェック処理1回目
	 * 
	 * @param contract
	 *            契約情報
	 * @param operatorId
	 *            操作者MoM社員ID
	 * @param result
	 *            Entityチェック結果
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkContractApprovalRequestFirst(Contract contract, String operatorId, BindingResult result) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 契約情報存在チェック
		existsContract(errorInfoList, contract);
		// 操作者MoM社員存在チェック
		existsMomEmployeeId(errorInfoList, operatorId, EmpMode.操作者);
		// Entityチェック
		checkUtil.checkEntity(result);
		// 契約ステータスチェック
		if (!businessCheck.existsContractStatusMatch(contract.getContractStatus(), ContractStatus.作成中)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "WrongNotErrorContractStatus", "WrongNotErrorContractStatusMsg", new String[] { ContractStatus.作成中.name() });
			throw new ErrorCheckException(errorInfoList);
		}
		// 契約承認ルートNullチェック
		if (null == contract.getContractApprovalRoute()) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "EntityDoesNotExistContractApprovalRoute", "EntityDoesNotExistContractApprovalRouteMsg");
			throw new ErrorCheckException(errorInfoList);
		}
	}

	/**
	 * 契約情報承認依頼チェック処理2回目
	 * 
	 * @param contractApprovalRouteNodeList
	 *            契約承認ルートノード
	 * @param approvalRouteNodeMasterList
	 *            マスタ承認ルートノード
	 * @param approvalRequesterMomEmployeeId
	 *            承認依頼者MoM社員ID
	 * @param momServiceUrl
	 *            MoMサービスURL
	 * @param relatedId
	 *            接続ID
	 * @param dbUrl
	 *            DB_URL
	 * @param dbUser
	 *            DB_USER
	 * @param dbPassword
	 *            DB_PASSWORD
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkContractApprovalRequestSecond(List<ContractApprovalRouteNode> contractApprovalRouteNodeList, List<ContractApprovalRouteNode> approvalRouteNodeMasterList, String approvalRequesterMomEmployeeId, String momServiceUrl, String relatedId, String dbUrl, String dbUser, String dbPassword) throws Exception {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 承認者と代理承認者が重複してないか確認
		if (!businessCheck.existsContractApprovalRouteApproverDuplication(contractApprovalRouteNodeList)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "DuplicationErrorContractSubApproverEmployee", "DuplicationErrorContractSubApproverEmployeeMsg");
			throw new ErrorCheckException(errorInfoList);
		}
		// 契約承認ルートが承認ルートマスタと一致しているか確認
		if (!businessCheck.existsContractApprovalRouteNodeMasterEqual(contractApprovalRouteNodeList, approvalRouteNodeMasterList)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "NotEqualErrorContractApprovalRouteNodeMaster", "NotEqualErrorContractApprovalRouteNodeMasterMsg");
			throw new ErrorCheckException(errorInfoList);
		}
		// 代理承認者の組織階層が承認者より上位か確認
		if (!businessCheck.existsSubApproverOrgHierarchyLevelSuperiorContract(contractApprovalRouteNodeList)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "LevelSuperiorErrorContractSubApproverOrgHierarchy", "LevelSuperiorErrorContractSubApproverOrgHierarchyMsg");
			throw new ErrorCheckException(errorInfoList);
		}
		// 代理承認者に承認権限があるか確認
		if (!businessCheck.existsSubApproverEmployeeAuthorityContract(approvalRequesterMomEmployeeId, contractApprovalRouteNodeList, momServiceUrl, relatedId, dbUrl, dbUser, dbPassword)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "ApprovalAuthorityErrorContractSubApprover", "ApprovalAuthorityErrorContractSubApproverMsg");
			throw new ErrorCheckException(errorInfoList);
		}
	}

	/**
	 * 契約情報承認差戻チェック処理
	 * 
	 * @param contractId
	 *            契約ID
	 * @param operatorId
	 *            操作者MoM社員ID
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkContractApprovalRemand(Long contractId, String operatorId) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 契約情報存在チェック
		existsContractId(errorInfoList, contractId);
		// 操作者MoM社員存在チェック
		existsMomEmployeeId(errorInfoList, operatorId, EmpMode.操作者);
		// 契約ステータスチェック
		Contract contract = contractRepository.findOne(contractId);
		if (!businessCheck.existsContractStatusMatch(contract.getContractStatus(), ContractStatus.承認依頼中)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "WrongNotErrorContractStatus", "WrongNotErrorContractStatusMsg", new String[] { ContractStatus.承認依頼中.name() });
			throw new ErrorCheckException(errorInfoList);
		}
		// 契約承認ルートNullチェック
		if (null == contract.getContractApprovalRoute()) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "EntityDoesNotExistContractApprovalRoute", "EntityDoesNotExistContractApprovalRouteMsg");
			throw new ErrorCheckException(errorInfoList);
		}
	}

	/**
	 * 契約情報承認チェック処理
	 * 
	 * @param contractId
	 *            契約ID
	 * @param operatorId
	 *            操作者MoM社員ID
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkContractApproval(Long contractId, String operatorId) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 契約情報存在チェック
		existsContractId(errorInfoList, contractId);
		// 操作者MoM社員存在チェック
		existsMomEmployeeId(errorInfoList, operatorId, EmpMode.操作者);
		// 契約ステータスチェック
		Contract contract = contractRepository.findOne(contractId);
		if (!businessCheck.existsContractStatusMatch(contract.getContractStatus(), ContractStatus.承認依頼中)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "WrongNotErrorContractStatus", "WrongNotErrorContractStatusMsg", new String[] { ContractStatus.承認依頼中.name() });
			throw new ErrorCheckException(errorInfoList);
		}
		// 契約承認ルートNullチェック
		if (null == contract.getContractApprovalRoute()) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "EntityDoesNotExistContractApprovalRoute", "EntityDoesNotExistContractApprovalRouteMsg");
			throw new ErrorCheckException(errorInfoList);
		}
	}

	/**
	 * 契約情報解約手続きチェック処理
	 * 
	 * @param contract
	 *            契約情報
	 * @param operatorId
	 *            操作者MoM社員ID
	 * @param result
	 *            Entityチェック結果
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkContractCancellation(Contract contract, String operatorId, BindingResult result) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 契約情報存在チェック
		existsContract(errorInfoList, contract);
		// 操作者MoM社員存在チェック
		existsMomEmployeeId(errorInfoList, operatorId, EmpMode.操作者);
		// Entityチェック
		checkUtil.checkEntity(result);
		// 契約ステータスチェック
		if (!businessCheck.existsContractStatusMatch(contract.getContractStatus(), ContractStatus.承認済み)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "WrongNotErrorContractStatus", "WrongNotErrorContractStatusMsg", new String[] { ContractStatus.承認済み.name() });
			throw new ErrorCheckException(errorInfoList);
		}
		// 契約日必須チェック
		if (null == contract.getContractDate()) {
			errorInfoList = checkUtil.addErrorInfoColumnCheck(errorInfoList, "ContractDate", "NotEmptyError");
			throw new ErrorCheckException(errorInfoList);
		}
		// 解約予定日必須チェック
		if (null == contract.getCancelScheduledDate()) {
			errorInfoList = checkUtil.addErrorInfoColumnCheck(errorInfoList, "CancelScheduledDate", "NotEmptyError");
			throw new ErrorCheckException(errorInfoList);
		}
		// 契約日>解約予定日の日付逆転チェック
		if (contract.getContractDate().after(contract.getCancelScheduledDate())) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "DateReverseError", "DateReverseErrorMsg", new String[] { "解約予定日", "契約日" });
			throw new ErrorCheckException(errorInfoList);
		}
	}

	/**
	 * 契約情報情報変更チェック処理
	 * 
	 * @param contract
	 *            契約情報
	 * @param operatorId
	 *            操作者MoM社員ID
	 * @param result
	 *            Entityチェック結果
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkContractUpdateAfter(Contract contract, String operatorId, BindingResult result) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 契約情報存在チェック
		existsContract(errorInfoList, contract);
		// 操作者MoM社員存在チェック
		existsMomEmployeeId(errorInfoList, operatorId, EmpMode.操作者);
		// Entityチェック
		checkUtil.checkEntity(result);
		// 契約ステータスチェック
		if (!businessCheck.existsContractStatusMatch(contract.getContractStatus(), ContractStatus.承認済み)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "WrongNotErrorContractStatus", "WrongNotErrorContractStatusMsg", new String[] { ContractStatus.承認済み.name() });
			throw new ErrorCheckException(errorInfoList);
		}
		// 解約予定日非必須チェック
		if (null != contract.getCancelScheduledDate()) {
			errorInfoList = checkUtil.addErrorInfoColumnCheck(errorInfoList, "CancelScheduledDate", "EmptyError");
			throw new ErrorCheckException(errorInfoList);
		}
	}

	/**
	 * 契約情報プラン変更チェック処理
	 * 
	 * @param contract
	 *            契約情報
	 * @param operatorId
	 *            操作者MoM社員ID
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkContractPlanChange(Long contractId, String operatorId) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 契約情報存在チェック
		existsContractId(errorInfoList, contractId);
		// 操作者MoM社員存在チェック
		existsMomEmployeeId(errorInfoList, operatorId, EmpMode.操作者);
		// 契約ステータスチェック
		Contract contract = contractRepository.findOne(contractId);
		if (!businessCheck.existsContractStatusMatch(contract.getContractStatus(), ContractStatus.承認済み)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "WrongNotErrorContractStatus", "WrongNotErrorContractStatusMsg", new String[] { ContractStatus.承認済み.name() });
			throw new ErrorCheckException(errorInfoList);
		}
		// プラン変更実施済の契約情報かどうか確認
		if (!businessCheck.existsConditionMatchEstimation(contract.getContractNumber())) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "ConditionMatchErrorEstimation", "ConditionMatchErrorEstimationMsg", new String[] { ContractStatus.承認済み.name() });
			throw new ErrorCheckException(errorInfoList);
		}
	}

	/**
	 * 得意先情報取得チェック処理
	 * 
	 * @param originalSystemCode
	 *            得意先コード
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkContractFindBillingCustomerInfo(String originalSystemCode) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		if (null == originalSystemCode) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "ArgumentNullErrorOriginalSystemCode", "ArgumentNullErrorOriginalSystemCodeMsg");
			throw new ErrorCheckException(errorInfoList);
		}

		if (!dBFoundCheck.existsBillingCustomerMaster(originalSystemCode)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "MasterDoesNotExistBillingCustomerMaster", "MasterDoesNotExistBillingCustomerMasterMsg");
			throw new ErrorCheckException(errorInfoList);
		}
	}

	/**
	 * 契約情報存在チェック
	 * 
	 * @param errorInfoList
	 *            エラーリスト
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	private void existsContract(List<ErrorInfo> errorInfoList, Contract contract) throws ErrorCheckException {
		if (null == contract) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "ArgumentNullErrorContract", "ArgumentNullErrorContractMsg");
			throw new ErrorCheckException(errorInfoList);
		}

		if (!dBFoundCheck.existsContract(contract.getId())) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "EntityDoesNotExistContract", "EntityDoesNotExistContractMsg");
			throw new ErrorCheckException(errorInfoList);
		}
	}

	/**
	 * 契約情報存在チェック
	 * 
	 * @param errorInfoList
	 *            エラーリスト
	 * @param contractId
	 *            契約ID
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	private void existsContractId(List<ErrorInfo> errorInfoList, Long contractId) throws ErrorCheckException {
		if (null == contractId) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "ArgumentNullErrorContractId", "ArgumentNullErrorContractIdMsg");
			throw new ErrorCheckException(errorInfoList);
		}

		if (!dBFoundCheck.existsContract(contractId)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "EntityDoesNotExistContract", "EntityDoesNotExistContractMsg");
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
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "ArgumentNullErrorMomEmployeeId", "ArgumentNullErrorMomEmployeeIdMsg", new String[] { empMode.name() });
			throw new ErrorCheckException(errorInfoList);
		}

		if (!dBFoundCheck.existsEmployeeMaster(momEmployeeId)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "MasterDoesNotExistEmployeeMaster", "MasterDoesNotExistEmployeeMasterMsg", new String[] { empMode.name() });
			throw new ErrorCheckException(errorInfoList);
		}
	}
}
