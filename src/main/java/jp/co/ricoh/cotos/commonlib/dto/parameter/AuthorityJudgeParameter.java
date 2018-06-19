package jp.co.ricoh.cotos.commonlib.dto.parameter;

import java.util.List;

import jp.co.ricoh.cotos.commonlib.entity.master.EmployeeMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.KjbMaster;
import lombok.Data;

@Data
public class AuthorityJudgeParameter {

	/** アクター用社員マスター */
	private EmployeeMaster actorEmployeeMaster;
	
	/** 社員マスター */
	private List<EmployeeMaster> employeeMasterList;
	
	/** 承認依頼者社員マスター */
	private EmployeeMaster requesterEmployeeMaster;
	
	/** 企事部マスター */
	private KjbMaster kjbMaster;
}
