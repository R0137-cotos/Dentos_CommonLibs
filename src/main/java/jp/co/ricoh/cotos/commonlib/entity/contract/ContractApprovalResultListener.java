package jp.co.ricoh.cotos.commonlib.entity.contract;

import java.util.ArrayList;

import javax.persistence.PrePersist;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.entity.master.MvEmployeeMaster;
import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.exception.ErrorInfo;
import jp.co.ricoh.cotos.commonlib.logic.check.CheckUtil;
import jp.co.ricoh.cotos.commonlib.repository.master.MvEmployeeMasterRepository;

@Component
public class ContractApprovalResultListener {

	private static MvEmployeeMasterRepository mvEmployeeMasterRepository;

	@Autowired
	public void setMvEmployeeMasterRepository(MvEmployeeMasterRepository mvEmployeeMasterRepository) {
		ContractApprovalResultListener.mvEmployeeMasterRepository = mvEmployeeMasterRepository;
	}

	@Autowired
	CheckUtil checkUtil;

	/**
	 * 社員マスタ情報を契約承認実績トランザクションに紐づけます。
	 *
	 * @param contractApprovalResult
	 */
	@PrePersist
	@Transactional
	public void appendsEmployeeFields(ContractApprovalResult contractApprovalResult) {
		MvEmployeeMaster employeeMaster = mvEmployeeMasterRepository.findByMomEmployeeId(contractApprovalResult.getActualEmpId());

		if (employeeMaster == null) {
			String[] regexList = { "操作者MoM社員ID" };
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "MasterDoesNotExistEmployeeMaster", regexList));
		}

		contractApprovalResult.setActualUserName(employeeMaster.getJobname1() + employeeMaster.getJobname2());
		contractApprovalResult.setActualOrgName(employeeMaster.getOrgName());
	}
}
