package jp.co.ricoh.cotos.commonlib.entity.estimation;

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
public class EstimationApprovalResultListener {

	private static MvEmployeeMasterRepository mvEmployeeMasterRepository;
	private static CheckUtil checkUtil;

	@Autowired
	public void setMvEmployeeMasterRepository(MvEmployeeMasterRepository mvEmployeeMasterRepository) {
		EstimationApprovalResultListener.mvEmployeeMasterRepository = mvEmployeeMasterRepository;
	}

	@Autowired
	public void setCheckUtil(CheckUtil checkUtil) {
		EstimationApprovalResultListener.checkUtil = checkUtil;
	}

	/**
	 * 社員マスタ情報を見積承認実績トランザクションに紐づけます。
	 *
	 * @param estimationApprovalResult
	 */
	@PrePersist
	@Transactional
	public void appendsEmployeeFields(EstimationApprovalResult estimationApprovalResult) {
		MvEmployeeMaster employeeMaster = mvEmployeeMasterRepository.findByMomEmployeeId(estimationApprovalResult.getActualEmpId());

		if (employeeMaster == null) {
			String[] regexList = { "操作者MoM社員ID" };
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "MasterDoesNotExistEmployeeMaster", regexList));
		}

		estimationApprovalResult.setActualUserName(employeeMaster.getJobname1() + employeeMaster.getJobname2());
		estimationApprovalResult.setActualOrgName(employeeMaster.getOrgName());
	}
}
