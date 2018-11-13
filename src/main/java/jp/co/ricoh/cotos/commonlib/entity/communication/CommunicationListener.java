package jp.co.ricoh.cotos.commonlib.entity.communication;

import java.util.ArrayList;

import javax.persistence.PrePersist;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.entity.master.MvEmployeeMaster;
import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.exception.ErrorInfo;
import jp.co.ricoh.cotos.commonlib.logic.check.CheckUtil;
import jp.co.ricoh.cotos.commonlib.repository.master.MvEmployeeMasterRepository;

@Component
public class CommunicationListener {

	private static MvEmployeeMasterRepository mvEmployeeMasterRepository;

	@Autowired
	public void setMvEmployeeMasterRepository(MvEmployeeMasterRepository mvEmployeeMasterRepository) {
		CommunicationListener.mvEmployeeMasterRepository = mvEmployeeMasterRepository;
	}

	@Autowired
	CheckUtil checkUtil;

	/**
	 * 社員マスタ情報をコミュニケーショントランザクションに焼き付けます。
	 *
	 * @param communication
	 */
	@PrePersist
	@Transactional
	public void appendsEmployeeFields(Communication communication) {
		MvEmployeeMaster emRequestOrigin = mvEmployeeMasterRepository.findByMomEmployeeId(communication.getRequestOriginId());
		if (emRequestOrigin == null) {
			String[] regexList = { "依頼者MoM社員ID" };
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "MasterDoesNotExistEmployeeMaster", regexList));
		}
		communication.setRequestOriginName(emRequestOrigin.getJobname1() + emRequestOrigin.getJobname2());

		MvEmployeeMaster emRequestFrom = mvEmployeeMasterRepository.findByMomEmployeeId(communication.getRequestFromId());
		if (emRequestFrom == null) {
			String[] regexList = { "伝達者MoM社員ID" };
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "MasterDoesNotExistEmployeeMaster", regexList));
		}
		communication.setRequestFromName(emRequestFrom.getJobname1() + emRequestFrom.getJobname2());

		MvEmployeeMaster emRequestTo = mvEmployeeMasterRepository.findByMomEmployeeId(communication.getRequestToId());
		if (emRequestTo == null) {
			String[] regexList = { "被伝達者MoM社員ID" };
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "MasterDoesNotExistEmployeeMaster", regexList));
		}
		communication.setRequestToName(emRequestTo.getJobname1() + emRequestTo.getJobname2());

		if (!StringUtils.isEmpty(communication.getRequestToCandidateId())) {
			MvEmployeeMaster emRequestToCandidate = mvEmployeeMasterRepository.findByMomEmployeeId(communication.getRequestToCandidateId());
			if (emRequestToCandidate == null) {
				String[] regexList = { "被伝達者候補MoM社員ID" };
				throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "MasterDoesNotExistEmployeeMaster", regexList));
			}
			communication.setRequestToCandidateName(emRequestToCandidate.getJobname1() + emRequestToCandidate.getJobname2());
		}

	}
}
