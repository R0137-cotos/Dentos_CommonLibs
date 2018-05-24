package jp.co.ricoh.cotos.commonlib.common.check;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

import jp.co.ricoh.cotos.commonlib.common.entity.Estimation;
import jp.co.ricoh.cotos.commonlib.common.entity.EstimationApprovalRouteNode;
import jp.co.ricoh.cotos.commonlib.common.entity.Estimation.Status;
import jp.co.ricoh.cotos.commonlib.common.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.common.exception.ErrorInfo;
import jp.co.ricoh.cotos.commonlib.common.master.KjbMaster;

/**
 * 見積機能別チェック管理クラス
 */
@Component
public class FunctionCheckEstimation {

	@Autowired
	CheckUtil checkUtil;
	@Autowired
	DBFoundCheck dBFoundCheck;
	@Autowired
	BusinessCheck businessCheck;

	/**
	 * 見積情報取得チェック処理
	 * 
	 * @param estimationId
	 *            見積ID
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkEstimationFind(Long estimationId) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 見積情報存在チェック
		checkFoundEstimationId(errorInfoList, estimationId);
	}

	/**
	 * 見積情報更新チェック処理
	 * 
	 * @param estimation
	 *            見積情報
	 * @param operatorId
	 *            操作者MoM社員ID
	 * @param result
	 *            Entityチェック結果
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkEstimationUpdate(Estimation estimation, String operatorId, BindingResult result) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 見積情報存在チェック
		checkFoundEstimation(errorInfoList, estimation);
		// 操作者MoM社員存在チェック
		checkFoundEmployeeMaster(errorInfoList, operatorId);
		// Entityチェック
		checkUtil.checkEntity(result);
	}

	/**
	 * 見積情報帳票出力チェック処理
	 * 
	 * @param estimationId
	 *            見積ID
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkEstimationOutPutReport(Long estimationId) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 見積情報存在チェック
		checkFoundEstimationId(errorInfoList, estimationId);
	}

	/**
	 * 見積情報承認ルート取得チェック処理
	 * 
	 * @param estimationId
	 *            見積ID
	 * @param momEmployeeId
	 *            MoM社員ID
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkEstimationFindApprover(Long estimationId, String momEmployeeId) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 見積情報存在チェック
		checkFoundEstimationId(errorInfoList, estimationId);
		// 操作者MoM社員存在チェック
		checkFoundEmployeeMaster(errorInfoList, momEmployeeId);
	}

	/**
	 * 見積情報代理承認者設定チェック処理
	 * 
	 * @param estimationApprovalRouteNode
	 *            見積承認ルートノード
	 * @param operatorId
	 *            操作者MoM社員ID
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkEstimationRegisterSubApproverEmployee(EstimationApprovalRouteNode estimationApprovalRouteNode, String operatorId) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		if (null == estimationApprovalRouteNode) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "ArgumentNullErrorEstimationApprovalRouteNode", "ArgumentNullErrorEstimationApprovalRouteNodeMsg");
			throw new ErrorCheckException(errorInfoList);
		}

		if (null == dBFoundCheck.existsFoundEstimationApprovalRouteNode(estimationApprovalRouteNode.getId())) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "EntityDoesNotExistEstimationApprovalRouteNode", "EntityDoesNotExistEstimationApprovalRouteNodeMsg");
			throw new ErrorCheckException(errorInfoList);
		}

		// 操作者MoM社員存在チェック
		checkFoundEmployeeMaster(errorInfoList, operatorId);
		// 承認者と代理承認者が重複してないか確認
		if (!businessCheck.existsEstimationApprovalRouteApproverDuplication(estimationApprovalRouteNode)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "DuplicationErrorEstimationSubApproverEmployee", "DuplicationErrorEstimationSubApproverEmployeeMsg");
			throw new ErrorCheckException(errorInfoList);
		}
	}

	/**
	 * 見積情報承認依頼チェック処理第1弾
	 * 
	 * @param estimation
	 *            見積情報
	 * @param operatorId
	 *            操作者MoM社員ID
	 * @param result
	 *            Entityチェック結果
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkEstimationApprovalRequestFirst(Estimation estimation, String operatorId, BindingResult result) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 見積情報存在チェック
		checkFoundEstimation(errorInfoList, estimation);
		// 操作者MoM社員存在チェック
		checkFoundEmployeeMaster(errorInfoList, operatorId);
		// Entityチェック
		checkUtil.checkEntity(result);
		// 見積ステータスチェック
		if (!businessCheck.existsEstimationStatusMatch(estimation.getStatus(), Status.作成中)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "WrongNotErrorEstimationStatus", "WrongNotErrorEstimationStatusMsg", new String[] { Status.作成中.name() });
			throw new ErrorCheckException(errorInfoList);
		}
	}

	/**
	 * 見積情報承認依頼チェック処理第2弾
	 * 
	 * @param estimationApprovalRouteNodeList
	 *            見積承認ルートノード
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
	public void checkEstimationApprovalRequestSecond(List<EstimationApprovalRouteNode> estimationApprovalRouteNodeList, List<EstimationApprovalRouteNode> approvalRouteNodeMasterList, String approvalRequesterMomEmployeeId, String momServiceUrl, String relatedId, String dbUrl, String dbUser, String dbPassword) throws Exception {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 承認者と代理承認者が重複してないか確認
		if (!businessCheck.existsEstimationApprovalRouteApproverDuplication(estimationApprovalRouteNodeList)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "DuplicationErrorEstimationSubApproverEmployee", "DuplicationErrorEstimationSubApproverEmployeeMsg");
			throw new ErrorCheckException(errorInfoList);
		}
		// 見積承認ルートが承認ルートマスタと一致しているか確認
		if (!businessCheck.existsEstimationApprovalRouteNodeMasterEqual(estimationApprovalRouteNodeList, approvalRouteNodeMasterList)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "NotEqualErrorEstimationApprovalRouteNodeMaster", "NotEqualErrorEstimationApprovalRouteNodeMasterMsg");
			throw new ErrorCheckException(errorInfoList);
		}
		// 代理承認者の組織階層が承認者より上位か確認
		if (!businessCheck.existsSubApproverOrgHierarchyLevelSuperiorEstimation(estimationApprovalRouteNodeList)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "LevelSuperiorErrorEstimationSubApproverOrgHierarchy", "LevelSuperiorErrorEstimationSubApproverOrgHierarchyMsg");
			throw new ErrorCheckException(errorInfoList);
		}
		// 代理承認者に承認権限があるか確認
		if (!businessCheck.existsSubApproverEmployeeAuthorityEstimation(approvalRequesterMomEmployeeId, estimationApprovalRouteNodeList, momServiceUrl, relatedId, dbUrl, dbUser, dbPassword)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "ApprovalAuthorityErrorEstimationSubApprover", "ApprovalAuthorityErrorEstimationSubApproverMsg");
			throw new ErrorCheckException(errorInfoList);
		}
	}

	/**
	 * 見積情報承認依頼差戻チェック処理
	 * 
	 * @param estimationId
	 *            見積ID
	 * @param operatorId
	 *            操作者MoM社員ID
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkEstimationApprovalRemand(Long estimationId, String operatorId) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 見積情報存在チェック
		Estimation estimation = checkFoundEstimationId(errorInfoList, estimationId);
		// 操作者MoM社員存在チェック
		checkFoundEmployeeMaster(errorInfoList, operatorId);
		// 見積ステータスチェック
		if (!businessCheck.existsEstimationStatusMatch(estimation.getStatus(), Status.承認依頼中)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "WrongNotErrorEstimationStatus", "WrongNotErrorEstimationStatusMsg", new String[] { Status.承認依頼中.name() });
			throw new ErrorCheckException(errorInfoList);
		}
		// 見積承認ルートNullチェック
		if (null == estimation.getEstimationApprovalRoute()) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "EntityDoesNotExistEstimationApprovalRoute", "EntityDoesNotExistEstimationApprovalRouteMsg");
			throw new ErrorCheckException(errorInfoList);
		}
	}

	/**
	 * 見積情報承認チェック処理
	 * 
	 * @param estimationId
	 *            見積ID
	 * @param operatorId
	 *            操作者MoM社員ID
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkEstimationApproval(Long estimationId, String operatorId) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 見積情報存在チェック
		Estimation estimation = checkFoundEstimationId(errorInfoList, estimationId);
		// 操作者MoM社員存在チェック
		checkFoundEmployeeMaster(errorInfoList, operatorId);
		// 見積ステータスチェック
		if (!businessCheck.existsEstimationStatusMatch(estimation.getStatus(), Status.承認依頼中)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "WrongNotErrorEstimationStatus", "WrongNotErrorEstimationStatusMsg", new String[] { Status.承認依頼中.name() });
			throw new ErrorCheckException(errorInfoList);
		}
		// 見積承認ルートNullチェック
		if (null == estimation.getEstimationApprovalRoute()) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "EntityDoesNotExistEstimationApprovalRoute", "EntityDoesNotExistEstimationApprovalRouteMsg");
			throw new ErrorCheckException(errorInfoList);
		}
	}

	/**
	 * 見積情報最終承認チェック処理
	 * 
	 * @param estimationId
	 *            見積ID
	 * @param operatorId
	 *            操作者MoM社員ID
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkEstimationLastApproval(Long estimationId, String operatorId) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 見積情報存在チェック
		Estimation estimation = checkFoundEstimationId(errorInfoList, estimationId);
		// 操作者MoM社員存在チェック
		checkFoundEmployeeMaster(errorInfoList, operatorId);
		// 見積ステータスチェック
		if (!businessCheck.existsEstimationStatusMatch(estimation.getStatus(), Status.承認済み)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "WrongNotErrorEstimationStatus", "WrongNotErrorEstimationStatusMsg", new String[] { Status.承認済み.name() });
			throw new ErrorCheckException(errorInfoList);
		}
	}

	/**
	 * 見積情報受注チェック処理
	 * 
	 * @param estimationId
	 *            見積ID
	 * @param operatorId
	 *            操作者MoM社員ID
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkEstimationAccept(Long estimationId, String operatorId) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 見積情報存在チェック
		Estimation estimation = checkFoundEstimationId(errorInfoList, estimationId);
		// 操作者MoM社員存在チェック
		checkFoundEmployeeMaster(errorInfoList, operatorId);
		// 見積ステータスチェック
		if (!businessCheck.existsEstimationStatusMatch(estimation.getStatus(), Status.承認済み)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "WrongNotErrorEstimationStatus", "WrongNotErrorEstimationStatusMsg", new String[] { Status.承認済み.name() });
			throw new ErrorCheckException(errorInfoList);
		}
	}

	/**
	 * 見積情報失注チェック処理
	 * 
	 * @param estimationId
	 *            見積ID
	 * @param operatorId
	 *            操作者MoM社員ID
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public void checkEstimationCancel(Long estimationId, String operatorId) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 見積情報存在チェック
		Estimation estimation = checkFoundEstimationId(errorInfoList, estimationId);
		// 操作者MoM社員存在チェック
		checkFoundEmployeeMaster(errorInfoList, operatorId);
		// 見積ステータスチェック
		if (businessCheck.existsEstimationStatusMatch(estimation.getStatus(), Status.受注)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "WrongErrorEstimationStatus", "WrongErrorEstimationStatusMsg", new String[] { Status.受注.name() });
			throw new ErrorCheckException(errorInfoList);
		}
	}

	/**
	 * 企事部情報取得チェック処理
	 * 
	 * @param mclMomKjbId
	 *            企事部ID
	 * @return 企事部情報
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	public KjbMaster checkEstimationFindKjbInfo(String mclMomKjbId) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		if (null == mclMomKjbId) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "ArgumentNullErrorMclMomKjbId", "ArgumentNullErrorMclMomKjbIdMsg");
			throw new ErrorCheckException(errorInfoList);
		}

		KjbMaster kjbMaster = dBFoundCheck.existsFoundKjbMaster(mclMomKjbId);
		if (null == kjbMaster) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "MasterDoesNotExistKjbMaster", "MasterDoesNotExistKjbMasterMsg");
			throw new ErrorCheckException(errorInfoList);
		}

		return kjbMaster;
	}

	/**
	 * 見積情報存在チェック
	 * 
	 * @param errorInfoList
	 *            エラーリスト
	 * @param estimation
	 *            見積情報
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	private void checkFoundEstimation(List<ErrorInfo> errorInfoList, Estimation estimation) throws ErrorCheckException {
		if (null == estimation) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "ArgumentNullErrorEstimation", "ArgumentNullErrorEstimationMsg");
			throw new ErrorCheckException(errorInfoList);
		}

		if (null == dBFoundCheck.existsFoundEstimation(estimation.getId())) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "EntityDoesNotExistEstimation", "EntityDoesNotExistEstimationMsg");
			throw new ErrorCheckException(errorInfoList);
		}
	}

	/**
	 * 見積情報存在チェック
	 * 
	 * @param errorInfoList
	 *            エラーリスト
	 * @param estimationId
	 *            見積ID
	 * @return 見積情報
	 * @throws ErrorCheckException
	 *             エラーチェックException
	 */
	private Estimation checkFoundEstimationId(List<ErrorInfo> errorInfoList, Long estimationId) throws ErrorCheckException {
		if (null == estimationId) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "ArgumentNullErrorEstimationId", "ArgumentNullErrorEstimationIdMsg");
			throw new ErrorCheckException(errorInfoList);
		}

		Estimation estimation = dBFoundCheck.existsFoundEstimation(estimationId);
		if (null == dBFoundCheck.existsFoundEstimation(estimationId)) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "EntityDoesNotExistEstimation", "EntityDoesNotExistEstimationMsg");
			throw new ErrorCheckException(errorInfoList);
		}

		return estimation;
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
