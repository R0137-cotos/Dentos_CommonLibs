package jp.co.ricoh.cotos.commonlib.logic.check;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.dto.parameter.common.AuthorityJudgeParameter;
import jp.co.ricoh.cotos.commonlib.entity.master.MvEmployeeMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.AccessType;
import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.ActionDiv;
import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.AuthDiv;
import jp.co.ricoh.cotos.commonlib.repository.master.MvEmployeeMasterRepository;
import jp.co.ricoh.cotos.commonlib.security.mom.MomAuthorityService;

/**
 * 仕様チェック管理クラス
 */
@Component
public class BusinessCheck {

	@Autowired
	MvEmployeeMasterRepository mvEmployeeMasterRepository;

	@Autowired
	MomAuthorityService momAuthorityService;

	/**
	 * 代理承認者に承認権限があるか確認
	 *
	 * @param approvalRequesterMomEmployeeId
	 *            承認依頼者MoM社員ID
	 * @param subApproverMomEmployeeId
	 *            代理承認者MoM社員ID
	 * @return チェック結果
	 * @throws Exception
	 */
	public boolean existsSubApproverEmployeeAuthority(String approvalRequesterMomEmployeeId, String subApproverMomEmployeeId) throws Exception {
		MvEmployeeMaster requester = mvEmployeeMasterRepository.findByMomEmployeeId(approvalRequesterMomEmployeeId);
		MvEmployeeMaster approver = mvEmployeeMasterRepository.findByMomEmployeeId(subApproverMomEmployeeId);
		if (approvalRequesterMomEmployeeId.equals(subApproverMomEmployeeId)) {
			return false;
		}

		if (null != approver && !existsAcceptAuthority(requester, approver)) {
			return false;
		}

		return true;
	}

	/**
	 * 権限区分を元に承認権限があるか確認
	 *
	 * @param requester
	 *            承認者情報
	 * @param approver
	 *            承認者情報
	 * @return チェック結果
	 * @throws Exception
	 */
	private boolean existsAcceptAuthority(MvEmployeeMaster requester, MvEmployeeMaster approver) throws Exception {
		AuthorityJudgeParameter authParam = new AuthorityJudgeParameter();
		authParam.setRequesterMvEmployeeMaster(requester);
		authParam.setActorMvEmployeeMaster(approver);

		return momAuthorityService.hasAuthority(authParam, ActionDiv.更新, AuthDiv.見積_契約_手配, AccessType.承認);
	}

	/**
	 * 代理承認者と承認依頼者が同一人物でないことを確認
	 *
	 * @param approvalRequesterMomEmployeeId
	 *            承認依頼者MoM社員ID
	 * @param subApproverMomEmployeeId
	 *            代理承認者MoM社員ID
	 * @return チェック結果
	 */
	public boolean confirmApprovalRequesterAndApprovarDiff(String approvalRequesterMomEmployeeId, String subApproverMomEmployeeId) {
		if (approvalRequesterMomEmployeeId.equals(subApproverMomEmployeeId)) {
			return false;
		}
		return true;
	}
}
