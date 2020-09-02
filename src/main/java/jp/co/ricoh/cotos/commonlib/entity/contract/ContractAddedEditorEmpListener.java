package jp.co.ricoh.cotos.commonlib.entity.contract;

import java.util.ArrayList;

import javax.persistence.PrePersist;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.entity.master.MvEmployeeMaster;
import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.exception.ErrorInfo;
import jp.co.ricoh.cotos.commonlib.logic.check.CheckUtil;
import jp.co.ricoh.cotos.commonlib.repository.master.DummyUserMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.MvEmployeeMasterRepository;

@Component
public class ContractAddedEditorEmpListener {
	private static MvEmployeeMasterRepository mvEmployeeMasterRepository;
	private static CheckUtil checkUtil;
	private static DummyUserMasterRepository dummyUserMasterRepository;

	@Autowired
	public void setMvEmployeeMasterRepository(MvEmployeeMasterRepository mvEmployeeMasterRepository) {
		ContractAddedEditorEmpListener.mvEmployeeMasterRepository = mvEmployeeMasterRepository;
	}

	@Autowired
	public void setCheckUtil(CheckUtil checkUtil) {
		ContractAddedEditorEmpListener.checkUtil = checkUtil;
	}

	@Autowired
	public void setDummyUserMasterRepository(DummyUserMasterRepository dummyUserMasterRepository) {
		ContractAddedEditorEmpListener.dummyUserMasterRepository = dummyUserMasterRepository;
	}

	@PrePersist
	@Transactional
	public void appendsEmployeeFields(ContractAddedEditorEmp contractAddedEditorEmp) {
		//ダミーユーザーであるかどうかのチェック
		if (dummyUserMasterRepository.existsByUserId(contractAddedEditorEmp.getMomEmployeeId())) {
			return;
		}

		// RJ社員マスタから情報取得済みであれば再設定しない
		if (contractAddedEditorEmp.isAcquiredInfo()) {
			return;
		}

		MvEmployeeMaster employeeMaster = mvEmployeeMasterRepository.findByMomEmployeeId(contractAddedEditorEmp.getMomEmployeeId());

		if (employeeMaster == null) {
			String[] regexList = { "契約追加編集者社員" };
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "MasterDoesNotExistEmployeeMaster", regexList));
		}

		BeanUtils.copyProperties(employeeMaster, contractAddedEditorEmp, "orgName", "salesCompanyName", "postNumber", "orgPhoneNumber", "salesDepartmentName", "phoneNumber", "faxNumber", "mailAddress");

		if (StringUtils.isBlank(contractAddedEditorEmp.getOrgName())) {
			contractAddedEditorEmp.setOrgName(employeeMaster.getOrgName());
		}
		if (StringUtils.isBlank(contractAddedEditorEmp.getSalesCompanyName())) {
			contractAddedEditorEmp.setSalesCompanyName(employeeMaster.getHanshSeiskNm());
		}
		if (StringUtils.isBlank(contractAddedEditorEmp.getPostNumber())) {
			contractAddedEditorEmp.setPostNumber(employeeMaster.getPostNumber());
		}
		if (StringUtils.isBlank(contractAddedEditorEmp.getOrgPhoneNumber())) {
			contractAddedEditorEmp.setOrgPhoneNumber(employeeMaster.getOrgPhoneNumber());
		}
		if (StringUtils.isBlank(contractAddedEditorEmp.getSalesDepartmentName())) {
			contractAddedEditorEmp.setSalesDepartmentName(employeeMaster.getSalesDepartmentName());
		}
		if (StringUtils.isBlank(contractAddedEditorEmp.getPhoneNumber())) {
			contractAddedEditorEmp.setPhoneNumber(employeeMaster.getPhoneNumber());
		}
		if (StringUtils.isBlank(contractAddedEditorEmp.getFaxNumber())) {
			contractAddedEditorEmp.setFaxNumber(employeeMaster.getFaxNumber());
		}
		if (StringUtils.isBlank(contractAddedEditorEmp.getMailAddress())) {
			contractAddedEditorEmp.setMailAddress(employeeMaster.getMailAddress());
		}
		if (StringUtils.isBlank(contractAddedEditorEmp.getEmployeeName())) {
			contractAddedEditorEmp.setEmployeeName(employeeMaster.getJobname1() + employeeMaster.getJobname2());
		}
		if (StringUtils.isBlank(contractAddedEditorEmp.getAddress())) {
			contractAddedEditorEmp.setAddress(convertJoinedAddress(employeeMaster));
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
