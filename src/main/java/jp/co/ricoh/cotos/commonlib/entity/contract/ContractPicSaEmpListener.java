package jp.co.ricoh.cotos.commonlib.entity.contract;

import java.util.ArrayList;

import javax.persistence.PrePersist;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.entity.master.MvEmployeeMaster;
import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.exception.ErrorInfo;
import jp.co.ricoh.cotos.commonlib.logic.check.CheckUtil;
import jp.co.ricoh.cotos.commonlib.repository.master.DummyUserMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.MvEmployeeMasterRepository;

@Component
public class ContractPicSaEmpListener {
	private static MvEmployeeMasterRepository mvEmployeeMasterRepository;
	private static CheckUtil checkUtil;
	private static DummyUserMasterRepository dummyUserMasterRepository;

	@Autowired
	public void setMvEmployeeMasterRepository(MvEmployeeMasterRepository mvEmployeeMasterRepository) {
		ContractPicSaEmpListener.mvEmployeeMasterRepository = mvEmployeeMasterRepository;
	}

	@Autowired
	public void setCheckUtil(CheckUtil checkUtil) {
		ContractPicSaEmpListener.checkUtil = checkUtil;
	}

	@Autowired
	public void setDummyUserMasterRepository(DummyUserMasterRepository dummyUserMasterRepository) {
		ContractPicSaEmpListener.dummyUserMasterRepository = dummyUserMasterRepository;
	}

	@PrePersist
	@Transactional
	public void appendsEmployeeFields(ContractPicSaEmp contractPicSaEmp) {
		//ダミーユーザーであるかどうかのチェック
		if (dummyUserMasterRepository.existsByUserId(contractPicSaEmp.getMomEmployeeId())) {
			return;
		}

		// RJ社員マスタから情報取得済みであれば再設定しない
		if (contractPicSaEmp.isAcquiredInfo()) {
			return;
		}

		MvEmployeeMaster employeeMaster = mvEmployeeMasterRepository.findByMomEmployeeId(contractPicSaEmp.getMomEmployeeId());

		if (employeeMaster == null) {
			String[] regexList = { "契約担当SA社員" };
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "MasterDoesNotExistEmployeeMaster", regexList));
		}

	}

}
