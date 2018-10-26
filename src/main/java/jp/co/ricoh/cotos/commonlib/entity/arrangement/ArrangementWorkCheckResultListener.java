package jp.co.ricoh.cotos.commonlib.entity.arrangement;

import java.util.ArrayList;

import javax.persistence.PreUpdate;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.entity.master.MvEmployeeMaster;
import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.exception.ErrorInfo;
import jp.co.ricoh.cotos.commonlib.logic.check.CheckUtil;
import jp.co.ricoh.cotos.commonlib.repository.master.MvEmployeeMasterRepository;

@Component
public class ArrangementWorkCheckResultListener {

	private static MvEmployeeMasterRepository mvEmployeeMasterRepository;

	@Autowired
	public void setMvEmployeeMasterRepository(MvEmployeeMasterRepository mvEmployeeMasterRepository) {
		ArrangementWorkCheckResultListener.mvEmployeeMasterRepository = mvEmployeeMasterRepository;
	}

	@Autowired
	CheckUtil checkUtil;

	/**
	 * 社員マスタ情報を手配業務チェック結果トランザクションに紐づけます。
	 *
	 * @param arrangementWorkCheckResult
	 */
	@PreUpdate
	@Transactional
	public void appendsEmployeeFields(ArrangementWorkCheckResult arrangementWorkCheckResult) {
		MvEmployeeMaster employeeMaster = mvEmployeeMasterRepository.findByMomEmployeeId(arrangementWorkCheckResult.getCheckedUserId());

		if (employeeMaster == null) {
			String[] regexList = { "操作者MoM社員ID" };
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "MasterDoesNotExistEmployeeMaster", regexList));
		}

		arrangementWorkCheckResult.setCheckedUserName(employeeMaster.getJobname1() + employeeMaster.getJobname2());
		arrangementWorkCheckResult.setCheckedOrgName(employeeMaster.getOrgName());
	}

}
