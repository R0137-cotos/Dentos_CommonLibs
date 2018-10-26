package jp.co.ricoh.cotos.commonlib.entity.arrangement;

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
public class ArrangementWorkOperationLogListener {

	private static MvEmployeeMasterRepository mvEmployeeMasterRepository;

	@Autowired
	public void setMvEmployeeMasterRepository(MvEmployeeMasterRepository mvEmployeeMasterRepository) {
		ArrangementWorkOperationLogListener.mvEmployeeMasterRepository = mvEmployeeMasterRepository;
	}

	@Autowired
	CheckUtil checkUtil;

	/**
	 * 社員マスタ情報を手配業務操作履歴トランザクションに紐づけます。
	 *
	 * @param arrangementWorkOperationLog
	 */
	@PrePersist
	@Transactional
	public void appendsEmployeeFields(ArrangementWorkOperationLog arrangementWorkOperationLog) {
		MvEmployeeMaster employeeMaster = mvEmployeeMasterRepository.findByMomEmployeeId(arrangementWorkOperationLog.getOperatorEmpId());

		if (employeeMaster == null) {
			String[] regexList = { "操作者MoM社員ID" };
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "MasterDoesNotExistEmployeeMaster", regexList));
		}

		arrangementWorkOperationLog.setOperatorName(employeeMaster.getJobname1() + employeeMaster.getJobname2());
		arrangementWorkOperationLog.setOperatorOrgName(employeeMaster.getOrgName());
	}

}
