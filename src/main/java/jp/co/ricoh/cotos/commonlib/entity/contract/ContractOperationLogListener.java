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
import jp.co.ricoh.cotos.commonlib.util.BatchMomInfoProperties;

@Component
public class ContractOperationLogListener {

	private static MvEmployeeMasterRepository mvEmployeeMasterRepository;

	@Autowired
	public void setMvEmployeeMasterRepository(MvEmployeeMasterRepository mvEmployeeMasterRepository) {
		ContractOperationLogListener.mvEmployeeMasterRepository = mvEmployeeMasterRepository;
	}

	@Autowired
	CheckUtil checkUtil;
	
	@Autowired
	BatchMomInfoProperties batchProperty;

	/**
	 * 社員マスタ情報を契約操作履歴トランザクションに紐づけます。
	 * バッチユーザーの場合は固定値を設定します。
	 *
	 * @param contractOperationLog
	 */
	@PrePersist
	@Transactional
	public void appendsEmployeeFields(ContractOperationLog contractOperationLog) {

		if (batchProperty.getMomEmpId().equals(contractOperationLog.getOperatorEmpId())) {
			contractOperationLog.setOperatorName("dummy_batch_operator");
			contractOperationLog.setOperatorOrgName("dummy_batch_operator_org");
			return;
		}
		MvEmployeeMaster employeeMaster = mvEmployeeMasterRepository.findByMomEmployeeId(contractOperationLog.getOperatorEmpId());
		if (employeeMaster == null) {
			String[] regexList = { "操作者MoM社員ID" };
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "MasterDoesNotExistEmployeeMaster", regexList));
		}

		contractOperationLog.setOperatorName(employeeMaster.getJobname1() + employeeMaster.getJobname2());
		contractOperationLog.setOperatorOrgName(employeeMaster.getOrgName());
	}

}
