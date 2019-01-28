package jp.co.ricoh.cotos.commonlib.dto.parameter;

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

	/** 企事部マスター */
	private VKjbMaster vKjbMaster;
}
