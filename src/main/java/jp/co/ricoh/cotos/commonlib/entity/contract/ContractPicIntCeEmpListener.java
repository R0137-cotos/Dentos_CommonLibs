package jp.co.ricoh.cotos.commonlib.entity.contract;

import java.util.ArrayList;

import jakarta.persistence.PrePersist;
import jakarta.transaction.Transactional;

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
public class ContractPicIntCeEmpListener {

	private static MvEmployeeMasterRepository mvEmployeeMasterRepository;
	private static CheckUtil checkUtil;
	private static DummyUserMasterRepository dummyUserMasterRepository;

	@Autowired
	public void setMvEmployeeMasterRepository(MvEmployeeMasterRepository mvEmployeeMasterRepository) {
		ContractPicIntCeEmpListener.mvEmployeeMasterRepository = mvEmployeeMasterRepository;
	}

	@Autowired
	public void setCheckUtil(CheckUtil checkUtil) {
		ContractPicIntCeEmpListener.checkUtil = checkUtil;
	}

	@Autowired
	public void setDummyUserMasterRepository(DummyUserMasterRepository dummyUserMasterRepository) {
		ContractPicIntCeEmpListener.dummyUserMasterRepository = dummyUserMasterRepository;
	}

	/**
	 * 社員マスタ情報を契約導入担当CE社員トランザクションに紐づけます。
	 *
	 * @param estimationPicSaEmp
	 */
	@PrePersist
	@Transactional
	public void appendsEmployeeFields(ContractPicIntCeEmp contractPicIntCeEmp) {
		if (dummyUserMasterRepository.existsByUserId(contractPicIntCeEmp.getMomEmployeeId())) {
			DummyUserMaster dummyUserMaster = dummyUserMasterRepository.findByUserId(contractPicIntCeEmp.getMomEmployeeId());
			contractPicIntCeEmp.setEmployeeName(dummyUserMaster.getEmpName());
			contractPicIntCeEmp.setAddress(dummyUserMaster.getAddress());
			return;
		}

		MvEmployeeMaster employeeMaster = mvEmployeeMasterRepository.findByMomEmployeeId(contractPicIntCeEmp.getMomEmployeeId());

		if (employeeMaster == null) {
			String[] regexList = { "契約導入担当CE社員" };
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "MasterDoesNotExistEmployeeMaster", regexList));
		}

		BeanUtils.copyProperties(employeeMaster, contractPicIntCeEmp, "orgName", "salesCompanyName", "postNumber", "orgPhoneNumber", "salesDepartmentName", "phoneNumber", "faxNumber", "mailAddress");

		if (StringUtils.isBlank(contractPicIntCeEmp.getOrgName())) {
			contractPicIntCeEmp.setOrgName(employeeMaster.getOrgName());
		}
		if (StringUtils.isBlank(contractPicIntCeEmp.getSalesCompanyName())) {
			contractPicIntCeEmp.setSalesCompanyName(employeeMaster.getHanshSeiskNm());
		}
		if (StringUtils.isBlank(contractPicIntCeEmp.getPostNumber())) {
			contractPicIntCeEmp.setPostNumber(employeeMaster.getPostNumber());
		}
		if (StringUtils.isBlank(contractPicIntCeEmp.getOrgPhoneNumber())) {
			contractPicIntCeEmp.setOrgPhoneNumber(employeeMaster.getOrgPhoneNumber());
		}
		if (StringUtils.isBlank(contractPicIntCeEmp.getSalesDepartmentName())) {
			contractPicIntCeEmp.setSalesDepartmentName(employeeMaster.getSalesDepartmentName());
		}
		if (StringUtils.isBlank(contractPicIntCeEmp.getPhoneNumber())) {
			contractPicIntCeEmp.setPhoneNumber(employeeMaster.getPhoneNumber());
		}
		if (StringUtils.isBlank(contractPicIntCeEmp.getFaxNumber())) {
			contractPicIntCeEmp.setFaxNumber(employeeMaster.getFaxNumber());
		}
		if (StringUtils.isBlank(contractPicIntCeEmp.getMailAddress())) {
			contractPicIntCeEmp.setMailAddress(employeeMaster.getMailAddress());
		}
		if (StringUtils.isBlank(contractPicIntCeEmp.getEmployeeName())) {
			contractPicIntCeEmp.setEmployeeName(employeeMaster.getJobname1() + employeeMaster.getJobname2());
		}
		if (StringUtils.isBlank(contractPicIntCeEmp.getAddress())) {
			contractPicIntCeEmp.setAddress(convertJoinedAddress(employeeMaster));
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
