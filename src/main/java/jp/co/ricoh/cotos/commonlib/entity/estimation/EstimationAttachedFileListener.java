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
public class EstimationAttachedFileListener {

	private static MvEmployeeMasterRepository mvEmployeeMasterRepository;

	@Autowired
	public void setMvEmployeeMasterRepository(MvEmployeeMasterRepository mvEmployeeMasterRepository) {
		EstimationAttachedFileListener.mvEmployeeMasterRepository = mvEmployeeMasterRepository;
	}

	@Autowired
	CheckUtil checkUtil;

	/**
	 * 社員マスタ情報を見積添付ファイルトランザクションに紐づけます。
	 *
	 * @param estimationAttachedFile
	 */
	@PrePersist
	@Transactional
	public void appendsEmployeeFields(EstimationAttachedFile estimationAttachedFile) {
		MvEmployeeMaster employeeMaster = mvEmployeeMasterRepository.findByMomEmployeeId(estimationAttachedFile.getAttachedEmpId());

		if (employeeMaster == null) {
			String[] regexList = { "添付者MoM社員ID" };
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "MasterDoesNotExistEmployeeMaster", regexList));
		}

		estimationAttachedFile.setAttachedEmpName(employeeMaster.getJobname1() + employeeMaster.getJobname2());
		estimationAttachedFile.setAttachedOrgName(employeeMaster.getOrgName());
	}

}
