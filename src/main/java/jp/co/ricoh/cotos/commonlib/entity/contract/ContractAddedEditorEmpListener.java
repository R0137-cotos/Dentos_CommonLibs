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
import jp.co.ricoh.cotos.commonlib.repository.master.MvEmployeeMasterRepository;

@Component
public class ContractAddedEditorEmpListener {

	private static MvEmployeeMasterRepository mvEmployeeMasterRepository;

	@Autowired
	public void setMvEmployeeMasterRepository(MvEmployeeMasterRepository mvEmployeeMasterRepository) {
		ContractAddedEditorEmpListener.mvEmployeeMasterRepository = mvEmployeeMasterRepository;
	}

	@Autowired
	CheckUtil checkUtil;

	/**
	 * 社員マスタ情報を契約追加編集者トランザクションに紐づけます。
	 *
	 * @param entity
	 */
	@PrePersist
	@Transactional
	public void appendsEmployeeFields(ContractAddedEditorEmp contractAddedEditorEmp) {
		MvEmployeeMaster employeeMaster = mvEmployeeMasterRepository.findByMomEmployeeId(contractAddedEditorEmp.getMomEmployeeId());

		if (employeeMaster == null) {
			String[] regexList = { "契約追加編集者社員" };
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "MasterDoesNotExistEmployeeMaster", regexList));
		}

		BeanUtils.copyProperties(employeeMaster, contractAddedEditorEmp);
		contractAddedEditorEmp.setEmployeeName(employeeMaster.getJobname1() + employeeMaster.getJobname2());
		contractAddedEditorEmp.setAddress(convertJoinedAddress(employeeMaster));
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
