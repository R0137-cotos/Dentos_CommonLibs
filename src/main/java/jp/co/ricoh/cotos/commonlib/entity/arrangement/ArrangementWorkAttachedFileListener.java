package jp.co.ricoh.cotos.commonlib.entity.arrangement;

import java.util.ArrayList;

import jakarta.persistence.PrePersist;
import jakarta.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.entity.master.DummyUserMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.MvEmployeeMaster;
import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.exception.ErrorInfo;
import jp.co.ricoh.cotos.commonlib.logic.check.CheckUtil;
import jp.co.ricoh.cotos.commonlib.repository.master.DummyUserMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.MvEmployeeMasterRepository;

@Component
public class ArrangementWorkAttachedFileListener {

	private static MvEmployeeMasterRepository mvEmployeeMasterRepository;
	private static CheckUtil checkUtil;
	private static DummyUserMasterRepository dummyUserMasterRepository;

	@Autowired
	public void setMvEmployeeMasterRepository(MvEmployeeMasterRepository mvEmployeeMasterRepository) {
		ArrangementWorkAttachedFileListener.mvEmployeeMasterRepository = mvEmployeeMasterRepository;
	}

	@Autowired
	public void setCheckUtil(CheckUtil checkUtil) {
		ArrangementWorkAttachedFileListener.checkUtil = checkUtil;
	}

	@Autowired
	public void setDummyUserMasterRepository(DummyUserMasterRepository dummyUserMasterRepository) {
		ArrangementWorkAttachedFileListener.dummyUserMasterRepository = dummyUserMasterRepository;
	}

	/**
	 * 社員マスタ情報を手配業務添付ファイルトランザクションに紐づけます。
	 *
	 * @param arrangementWorkAttachedFile
	 */
	@PrePersist
	@Transactional
	public void appendsEmployeeFields(ArrangementWorkAttachedFile arrangementWorkAttachedFile) {
		if (dummyUserMasterRepository.existsByUserId(arrangementWorkAttachedFile.getAttachedEmpId())) {
			DummyUserMaster dummyUserMaster = dummyUserMasterRepository.findByUserId(arrangementWorkAttachedFile.getAttachedEmpId());
			arrangementWorkAttachedFile.setAttachedEmpName(dummyUserMaster.getEmpName());
			arrangementWorkAttachedFile.setAttachedOrgName(dummyUserMaster.getOrgName());
			return;
		}

		MvEmployeeMaster employeeMaster = mvEmployeeMasterRepository.findByMomEmployeeId(arrangementWorkAttachedFile.getAttachedEmpId());

		if (employeeMaster == null) {
			String[] regexList = { "添付者MoM社員ID" };
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "MasterDoesNotExistEmployeeMaster", regexList));
		}

		if (StringUtils.isBlank(arrangementWorkAttachedFile.getAttachedEmpName())) {
			arrangementWorkAttachedFile.setAttachedEmpName(employeeMaster.getJobname1() + employeeMaster.getJobname2());
		}
		if (StringUtils.isBlank(arrangementWorkAttachedFile.getAttachedOrgName())) {
			arrangementWorkAttachedFile.setAttachedOrgName(employeeMaster.getOrgName());
		}
	}

}
