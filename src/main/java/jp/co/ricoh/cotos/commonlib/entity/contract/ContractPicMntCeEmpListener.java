package jp.co.ricoh.cotos.commonlib.entity.contract;

import java.util.ArrayList;

import javax.persistence.PrePersist;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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
public class ContractPicMntCeEmpListener {

	private static MvEmployeeMasterRepository mvEmployeeMasterRepository;
	private static CheckUtil checkUtil;
	private static DummyUserMasterRepository dummyUserMasterRepository;

	@Autowired
	public void setMvEmployeeMasterRepository(MvEmployeeMasterRepository mvEmployeeMasterRepository) {
		ContractPicMntCeEmpListener.mvEmployeeMasterRepository = mvEmployeeMasterRepository;
	}

	@Autowired
	public void setCheckUtil(CheckUtil checkUtil) {
		ContractPicMntCeEmpListener.checkUtil = checkUtil;
	}

	@Autowired
	public void setDummyUserMasterRepository(DummyUserMasterRepository dummyUserMasterRepository) {
		ContractPicMntCeEmpListener.dummyUserMasterRepository = dummyUserMasterRepository;
	}

	/**
	 * 社員マスタ情報を契約担当CE社員トランザクションに紐づけます。
	 *
	 * @param estimationPicSaEmp
	 */
	@PrePersist
	@Transactional
	public void appendsEmployeeFields(ContractPicMntCeEmp contractPicMntCeEmp) {
		if (dummyUserMasterRepository.existsByUserId(contractPicMntCeEmp.getMomEmployeeId())) {
			DummyUserMaster dummyUserMaster = dummyUserMasterRepository.findByUserId(contractPicMntCeEmp.getMomEmployeeId());
			contractPicMntCeEmp.setEmployeeName(dummyUserMaster.getEmpName());
			contractPicMntCeEmp.setAddress(dummyUserMaster.getAddress());
			return;
		}

		MvEmployeeMaster employeeMaster = mvEmployeeMasterRepository.findByMomEmployeeId(contractPicMntCeEmp.getMomEmployeeId());

		if (employeeMaster == null) {
			String[] regexList = { "契約担当CE社員" };
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "MasterDoesNotExistEmployeeMaster", regexList));
		}

		BeanUtils.copyProperties(employeeMaster, contractPicMntCeEmp, "orgName", "salesCompanyName", "postNumber", "orgPhoneNumber", "salesDepartmentName", "phoneNumber", "faxNumber", "mailAddress");

		if (StringUtils.isBlank(contractPicMntCeEmp.getOrgName())) {
			contractPicMntCeEmp.setOrgName(employeeMaster.getOrgName());
		}
		if (StringUtils.isBlank(contractPicMntCeEmp.getSalesCompanyName())) {
			contractPicMntCeEmp.setSalesCompanyName(employeeMaster.getHanshSeiskNm());
		}
		if (StringUtils.isBlank(contractPicMntCeEmp.getPostNumber())) {
			contractPicMntCeEmp.setPostNumber(employeeMaster.getPostNumber());
		}
		if (StringUtils.isBlank(contractPicMntCeEmp.getOrgPhoneNumber())) {
			contractPicMntCeEmp.setOrgPhoneNumber(employeeMaster.getOrgPhoneNumber());
		}
		if (StringUtils.isBlank(contractPicMntCeEmp.getSalesDepartmentName())) {
			contractPicMntCeEmp.setSalesDepartmentName(employeeMaster.getSalesDepartmentName());
		}
		if (StringUtils.isBlank(contractPicMntCeEmp.getPhoneNumber())) {
			contractPicMntCeEmp.setPhoneNumber(employeeMaster.getPhoneNumber());
		}
		if (StringUtils.isBlank(contractPicMntCeEmp.getFaxNumber())) {
			contractPicMntCeEmp.setFaxNumber(employeeMaster.getFaxNumber());
		}
		if (StringUtils.isBlank(contractPicMntCeEmp.getMailAddress())) {
			contractPicMntCeEmp.setMailAddress(employeeMaster.getMailAddress());
		}
		if (StringUtils.isBlank(contractPicMntCeEmp.getEmployeeName())) {
			contractPicMntCeEmp.setEmployeeName(employeeMaster.getJobname1() + employeeMaster.getJobname2());
		}
		if (StringUtils.isBlank(contractPicMntCeEmp.getAddress())) {
			contractPicMntCeEmp.setAddress(convertJoinedAddress(employeeMaster));
		}
	}

	private String convertJoinedAddress(MvEmployeeMaster master) {

		StringBuilder sb = new StringBuilder();

		sb.append(StringUtils.defaultIfEmpty(master.getTdhknNmKnji(), StringUtils.EMPTY));
		sb.append(StringUtils.defaultIfEmpty(master.getSkugnchosnKnji(), StringUtils.EMPTY));
		sb.append(StringUtils.defaultIfEmpty(master.getOwaTusyoKnji(), StringUtils.EMPTY));
		sb.append(StringUtils.defaultIfEmpty(master.getKowChomeKnji(), StringUtils.EMPTY));
		sb.append(StringUtils.defaultIfEmpty(master.getStreet(), StringUtils.EMPTY));
		sb.append(StringUtils.defaultIfEmpty(master.getBuilding(), StringUtils.EMPTY));

		return sb.toString();
	}
}
