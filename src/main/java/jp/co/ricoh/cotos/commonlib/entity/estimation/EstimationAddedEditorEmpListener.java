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
public class EstimationAddedEditorEmpListener {

	private static MvEmployeeMasterRepository mvEmployeeMasterRepository;
	private static CheckUtil checkUtil;
	private static DummyUserMasterRepository dummyUserMasterRepository;

	@Autowired
	public void setMvEmployeeMasterRepository(MvEmployeeMasterRepository mvEmployeeMasterRepository) {
		EstimationAddedEditorEmpListener.mvEmployeeMasterRepository = mvEmployeeMasterRepository;
	}

	@Autowired
	public void setCheckUtil(CheckUtil checkUtil) {
		EstimationAddedEditorEmpListener.checkUtil = checkUtil;
	}

	@Autowired
	public void setDummyUserMasterRepository(DummyUserMasterRepository dummyUserMasterRepository) {
		EstimationAddedEditorEmpListener.dummyUserMasterRepository = dummyUserMasterRepository;
	}

	/**
	 * 社員マスタ情報を見積追加編集者トランザクションに紐づけます。
	 *
	 * @param entity
	 */
	@PrePersist
	@Transactional
	public void appendsEmployeeFields(EstimationAddedEditorEmp estimationAddedEditorEmp) {
		if (dummyUserMasterRepository.existsByUserId(estimationAddedEditorEmp.getMomEmployeeId())) {
			DummyUserMaster dummyUserMaster = dummyUserMasterRepository.findByUserId(estimationAddedEditorEmp.getMomEmployeeId());
			estimationAddedEditorEmp.setEmployeeName(dummyUserMaster.getEmpName());
			estimationAddedEditorEmp.setAddress(dummyUserMaster.getAddress());
			return;
		}

		// RJ社員マスタから情報取得済みであれば再設定しない
		if (estimationAddedEditorEmp.isAcquiredInfo()) {
			return;
		}

		MvEmployeeMaster employeeMaster = mvEmployeeMasterRepository.findByMomEmployeeId(estimationAddedEditorEmp.getMomEmployeeId());

		if (employeeMaster == null) {
			String[] regexList = { "見積追加編集者社員" };
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "MasterDoesNotExistEmployeeMaster", regexList));
		}

		BeanUtils.copyProperties(employeeMaster, estimationAddedEditorEmp, "orgName", "salesCompanyName", "postNumber", "orgPhoneNumber", "salesDepartmentName", "phoneNumber", "faxNumber", "mailAddress");

		if (StringUtils.isBlank(estimationAddedEditorEmp.getOrgName())) {
			estimationAddedEditorEmp.setOrgName(employeeMaster.getOrgName());
		}
		if (StringUtils.isBlank(estimationAddedEditorEmp.getSalesCompanyName())) {
			estimationAddedEditorEmp.setSalesCompanyName(employeeMaster.getHanshSeiskNm());
		}
		if (StringUtils.isBlank(estimationAddedEditorEmp.getPostNumber())) {
			estimationAddedEditorEmp.setPostNumber(employeeMaster.getPostNumber());
		}
		if (StringUtils.isBlank(estimationAddedEditorEmp.getOrgPhoneNumber())) {
			estimationAddedEditorEmp.setOrgPhoneNumber(employeeMaster.getOrgPhoneNumber());
		}
		if (StringUtils.isBlank(estimationAddedEditorEmp.getSalesDepartmentName())) {
			estimationAddedEditorEmp.setSalesDepartmentName(employeeMaster.getSalesDepartmentName());
		}
		if (StringUtils.isBlank(estimationAddedEditorEmp.getPhoneNumber())) {
			estimationAddedEditorEmp.setPhoneNumber(employeeMaster.getPhoneNumber());
		}
		if (StringUtils.isBlank(estimationAddedEditorEmp.getFaxNumber())) {
			estimationAddedEditorEmp.setFaxNumber(employeeMaster.getFaxNumber());
		}
		if (StringUtils.isBlank(estimationAddedEditorEmp.getMailAddress())) {
			estimationAddedEditorEmp.setMailAddress(employeeMaster.getMailAddress());
		}
		if (StringUtils.isBlank(estimationAddedEditorEmp.getEmployeeName())) {
			estimationAddedEditorEmp.setEmployeeName(employeeMaster.getJobname1() + employeeMaster.getJobname2());
		}
		if (StringUtils.isBlank(estimationAddedEditorEmp.getAddress())) {
			estimationAddedEditorEmp.setAddress(convertJoinedAddress(employeeMaster));
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
