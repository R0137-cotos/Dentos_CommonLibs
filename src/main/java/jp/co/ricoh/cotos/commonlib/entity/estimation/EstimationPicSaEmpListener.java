package jp.co.ricoh.cotos.commonlib.entity.estimation;

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
public class EstimationPicSaEmpListener {

	private static MvEmployeeMasterRepository mvEmployeeMasterRepository;
	private static CheckUtil checkUtil;
	private static DummyUserMasterRepository dummyUserMasterRepository;

	@Autowired
	public void setMvEmployeeMasterRepository(MvEmployeeMasterRepository mvEmployeeMasterRepository) {
		EstimationPicSaEmpListener.mvEmployeeMasterRepository = mvEmployeeMasterRepository;
	}

	@Autowired
	public void setCheckUtil(CheckUtil checkUtil) {
		EstimationPicSaEmpListener.checkUtil = checkUtil;
	}

	@Autowired
	public void setDummyUserMasterRepository(DummyUserMasterRepository dummyUserMasterRepository) {
		EstimationPicSaEmpListener.dummyUserMasterRepository = dummyUserMasterRepository;
	}

	/**
	 * 社員マスタ情報を見積担当SA社員トランザクションに紐づけます。
	 *
	 * @param estimationPicSaEmp
	 */
	@PrePersist
	@Transactional
	public void appendsEmployeeFields(EstimationPicSaEmp estimationPicSaEmp) {
		if (dummyUserMasterRepository.existsByUserId(estimationPicSaEmp.getMomEmployeeId())) {
			DummyUserMaster dummyUserMaster = dummyUserMasterRepository.findByUserId(estimationPicSaEmp.getMomEmployeeId());
			estimationPicSaEmp.setEmployeeName(dummyUserMaster.getEmpName());
			estimationPicSaEmp.setAddress(dummyUserMaster.getAddress());
			return;
		}

		// RJ社員マスタから情報取得済みであれば再設定しない
		if (estimationPicSaEmp.isAcquiredInfo()) {
			return;
		}

		MvEmployeeMaster employeeMaster = mvEmployeeMasterRepository.findByMomEmployeeId(estimationPicSaEmp.getMomEmployeeId());

		if (employeeMaster == null) {
			String[] regexList = { "見積担当SA社員" };
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "MasterDoesNotExistEmployeeMaster", regexList));
		}

		BeanUtils.copyProperties(employeeMaster, estimationPicSaEmp, "orgName", "salesCompanyName", "postNumber", "orgPhoneNumber", "salesDepartmentName", "phoneNumber", "faxNumber", "mailAddress");

		if (StringUtils.isBlank(estimationPicSaEmp.getOrgName())) {
			estimationPicSaEmp.setOrgName(employeeMaster.getOrgName());
		}
		if (StringUtils.isBlank(estimationPicSaEmp.getSalesCompanyName())) {
			estimationPicSaEmp.setSalesCompanyName(employeeMaster.getHanshSeiskNm());
		}
		if (StringUtils.isBlank(estimationPicSaEmp.getPostNumber())) {
			estimationPicSaEmp.setPostNumber(employeeMaster.getPostNumber());
		}
		if (StringUtils.isBlank(estimationPicSaEmp.getOrgPhoneNumber())) {
			estimationPicSaEmp.setOrgPhoneNumber(employeeMaster.getOrgPhoneNumber());
		}
		if (StringUtils.isBlank(estimationPicSaEmp.getSalesDepartmentName())) {
			estimationPicSaEmp.setSalesDepartmentName(employeeMaster.getSalesDepartmentName());
		}
		if (StringUtils.isBlank(estimationPicSaEmp.getPhoneNumber())) {
			estimationPicSaEmp.setPhoneNumber(employeeMaster.getPhoneNumber());
		}
		if (StringUtils.isBlank(estimationPicSaEmp.getFaxNumber())) {
			estimationPicSaEmp.setFaxNumber(employeeMaster.getFaxNumber());
		}
		if (StringUtils.isBlank(estimationPicSaEmp.getMailAddress())) {
			estimationPicSaEmp.setMailAddress(employeeMaster.getMailAddress());
		}
		if (StringUtils.isBlank(estimationPicSaEmp.getEmployeeName())) {
			estimationPicSaEmp.setEmployeeName(employeeMaster.getJobname1() + employeeMaster.getJobname2());
		}
		if (StringUtils.isBlank(estimationPicSaEmp.getAddress())) {
			estimationPicSaEmp.setAddress(convertJoinedAddress(employeeMaster));
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
