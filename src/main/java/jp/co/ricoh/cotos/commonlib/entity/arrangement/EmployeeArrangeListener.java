package jp.co.ricoh.cotos.commonlib.entity.arrangement;

import javax.persistence.PrePersist;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.entity.master.EmployeeMaster;
import jp.co.ricoh.cotos.commonlib.repository.master.EmployeeMasterRepository;

@Component
public class EmployeeArrangeListener {
	private static EmployeeMasterRepository employeeMasterRepository;

	@Autowired
	public void setEmployeeMasterRepository(EmployeeMasterRepository employeeMasterRepository) {
		EmployeeArrangeListener.employeeMasterRepository = employeeMasterRepository;
	}

	/**
	 * 社員マスタ情報を社員トランザクションに紐づけます。
	 *
	 * @param entity
	 */
	@PrePersist
	@Transactional
	public void appendsEmployeeFields(EmployeeArrange employeeArrange) {
		EmployeeMaster employeeMaster = employeeMasterRepository.findByMomEmployeeId(employeeArrange.getEmployeeMaster().getMomEmployeeId());

		if (employeeMaster == null) {
			throw new IllegalArgumentException("Target Not Found By MomEmployeeId : EmployeeMaster" + "MomEmployeeId:" + employeeArrange.getEmployeeMaster().getMomEmployeeId());
		}

		BeanUtils.copyProperties(employeeMaster, employeeArrange);
		employeeArrange.setEmployeeName(employeeMaster.getJobname1() + employeeMaster.getJobname2());
		employeeArrange.setAddress(convertJoinedAddress(employeeMaster));
		employeeArrange.setEmployeeMaster(employeeMaster);
	}

	private String convertJoinedAddress(EmployeeMaster master) {

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
