package jp.co.ricoh.cotos.commonlib.dto.parameter.common;

import java.util.List;

import jp.co.ricoh.cotos.commonlib.entity.master.MvEmployeeMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.VKjbMaster;
import lombok.Data;

@Data
public class AuthorityJudgeParameter {

	/** アクター用社員マスター */
	private MvEmployeeMaster actorMvEmployeeMaster;

	/** 社員マスター */
	private List<MvEmployeeMaster> mvEmployeeMasterList;

	/** 承認依頼者社員マスター */
	private MvEmployeeMaster requesterMvEmployeeMaster;

	/** 承認者社員マスター */
	private List<MvEmployeeMaster> approverMvEmployeeMasterList;

	/** 次回承認者社員マスター */
	private MvEmployeeMaster nextApproverMvEmployeeMaster;

	/** 次回代理承認者社員マスター */
	private MvEmployeeMaster nextSubApproverMvEmployeeMaster;

	/** 前回承認者社員マスター */
	private MvEmployeeMaster prevApproverMvEmployeeMaster;

	/** 前回代理承認者社員マスター */
	private MvEmployeeMaster prevSubApproverMvEmployeeMaster;

	/** 承認者直接指定フラグ */
	private boolean isManualApprover;

	/** 自己承認フラグ */
	private boolean isSelfApprover = false;

	/** 企事部マスター */
	private VKjbMaster vKjbMaster;
}
