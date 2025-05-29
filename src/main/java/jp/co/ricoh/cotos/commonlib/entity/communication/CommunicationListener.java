package jp.co.ricoh.cotos.commonlib.entity.communication;

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
public class CommunicationListener {

	private static MvEmployeeMasterRepository mvEmployeeMasterRepository;
	private static CheckUtil checkUtil;
	private static DummyUserMasterRepository dummyUserMasterRepository;

	@Autowired
	public void setMvEmployeeMasterRepository(MvEmployeeMasterRepository mvEmployeeMasterRepository) {
		CommunicationListener.mvEmployeeMasterRepository = mvEmployeeMasterRepository;
	}

	@Autowired
	public void setCheckUtil(CheckUtil checkUtil) {
		CommunicationListener.checkUtil = checkUtil;
	}

	@Autowired
	public void setDummyUserMasterRepository(DummyUserMasterRepository dummyUserMasterRepository) {
		CommunicationListener.dummyUserMasterRepository = dummyUserMasterRepository;
	}

	/**
	 * 社員マスタ情報をコミュニケーショントランザクションに焼き付けます。
	 *
	 * @param communication
	 */
	@PrePersist
	@Transactional
	public void appendsEmployeeFields(Communication communication) {
		if (dummyUserMasterRepository.existsByUserId(communication.getRequestOriginId())) {
			DummyUserMaster dummyUserMaster = dummyUserMasterRepository.findByUserId(communication.getRequestOriginId());
			communication.setRequestOriginName(dummyUserMaster.getEmpName());
		} else {

			MvEmployeeMaster emRequestOrigin = mvEmployeeMasterRepository.findByMomEmployeeId(communication.getRequestOriginId());
			if (emRequestOrigin == null) {
				String[] regexList = { "依頼者MoM社員ID" };
				throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "MasterDoesNotExistEmployeeMaster", regexList));
			}
			communication.setRequestOriginName(emRequestOrigin.getJobname1() + emRequestOrigin.getJobname2());
		}

		if (dummyUserMasterRepository.existsByUserId(communication.getRequestFromId())) {
			DummyUserMaster dummyUserMaster = dummyUserMasterRepository.findByUserId(communication.getRequestFromId());
			communication.setRequestFromName(dummyUserMaster.getEmpName());
		} else {
			MvEmployeeMaster emRequestFrom = mvEmployeeMasterRepository.findByMomEmployeeId(communication.getRequestFromId());
			if (emRequestFrom == null) {
				String[] regexList = { "伝達者MoM社員ID" };
				throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "MasterDoesNotExistEmployeeMaster", regexList));
			}
			communication.setRequestFromName(emRequestFrom.getJobname1() + emRequestFrom.getJobname2());
		}

		if (dummyUserMasterRepository.existsByUserId(communication.getRequestToId())) {
			DummyUserMaster dummyUserMaster = dummyUserMasterRepository.findByUserId(communication.getRequestOriginId());
			communication.setRequestToName(dummyUserMaster.getEmpName());
		} else {
			MvEmployeeMaster emRequestTo = mvEmployeeMasterRepository.findByMomEmployeeId(communication.getRequestToId());
			if (emRequestTo == null) {
				String[] regexList = { "被伝達者MoM社員ID" };
				throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "MasterDoesNotExistEmployeeMaster", regexList));
			}
			communication.setRequestToName(emRequestTo.getJobname1() + emRequestTo.getJobname2());
		}

		if (!StringUtils.isEmpty(communication.getRequestToCandidateId())) {

			if (dummyUserMasterRepository.existsByUserId(communication.getRequestToCandidateId())) {
				DummyUserMaster dummyUserMaster = dummyUserMasterRepository.findByUserId(communication.getRequestToCandidateId());
				communication.setRequestToCandidateName(dummyUserMaster.getEmpName());
			} else {
				MvEmployeeMaster emRequestToCandidate = mvEmployeeMasterRepository.findByMomEmployeeId(communication.getRequestToCandidateId());
				if (emRequestToCandidate == null) {
					String[] regexList = { "被伝達者候補MoM社員ID" };
					throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "MasterDoesNotExistEmployeeMaster", regexList));
				}
				communication.setRequestToCandidateName(emRequestToCandidate.getJobname1() + emRequestToCandidate.getJobname2());
			}
		}

	}
}
