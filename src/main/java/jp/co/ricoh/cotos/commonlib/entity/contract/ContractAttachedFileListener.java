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
public class ContractAttachedFileListener {

	private static MvEmployeeMasterRepository mvEmployeeMasterRepository;
	private static CheckUtil checkUtil;

	@Autowired
	public void setMvEmployeeMasterRepository(MvEmployeeMasterRepository mvEmployeeMasterRepository) {
		ContractAttachedFileListener.mvEmployeeMasterRepository = mvEmployeeMasterRepository;
	}

	@Autowired
	public void setCheckUtil(CheckUtil checkUtil) {
		ContractAttachedFileListener.checkUtil = checkUtil;
	}

	/**
	 * 社員マスタ情報を契約添付ファイルトランザクションに紐づけます。
	 *
	 * @param contractAttachedFile
	 */
	@PrePersist
	@Transactional
	public void appendsEmployeeFields(ContractAttachedFile contractAttachedFile) {
		MvEmployeeMaster employeeMaster = mvEmployeeMasterRepository.findByMomEmployeeId(contractAttachedFile.getAttachedEmpId());

		if (employeeMaster == null) {
			String[] regexList = { "添付者MoM社員ID" };
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "MasterDoesNotExistEmployeeMaster", regexList));
		}

		contractAttachedFile.setAttachedEmpName(employeeMaster.getJobname1() + employeeMaster.getJobname2());
		contractAttachedFile.setAttachedOrgName(employeeMaster.getOrgName());
	}

}
