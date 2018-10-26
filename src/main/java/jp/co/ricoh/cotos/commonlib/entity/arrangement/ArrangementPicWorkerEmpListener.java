package jp.co.ricoh.cotos.commonlib.entity.arrangement;

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
public class ArrangementPicWorkerEmpListener {

	private static MvEmployeeMasterRepository mvEmployeeMasterRepository;

	@Autowired
	public void setMvEmployeeMasterRepository(MvEmployeeMasterRepository mvEmployeeMasterRepository) {
		ArrangementPicWorkerEmpListener.mvEmployeeMasterRepository = mvEmployeeMasterRepository;
	}

	@Autowired
	CheckUtil checkUtil;

	/**
	 * 社員マスタ情報を担当作業者社員トランザクションに紐づけます。
	 *
	 * @param arrangementPicWorkerEmp
	 */
	@PrePersist
	@Transactional
	public void appendsEmployeeFields(ArrangementPicWorkerEmp arrangementPicWorkerEmp) {
		MvEmployeeMaster employeeMaster = mvEmployeeMasterRepository.findByMomEmployeeId(arrangementPicWorkerEmp.getMvEmployeeMaster().getMomEmployeeId());

		if (employeeMaster == null) {
			String[] regexList = { "担当作業者社員" };
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "MasterDoesNotExistEmployeeMaster", regexList));
		}

		BeanUtils.copyProperties(employeeMaster, arrangementPicWorkerEmp);
		arrangementPicWorkerEmp.setEmployeeName(employeeMaster.getJobname1() + employeeMaster.getJobname2());
		arrangementPicWorkerEmp.setAddress(convertJoinedAddress(employeeMaster));
		arrangementPicWorkerEmp.setMvEmployeeMaster(employeeMaster);
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
