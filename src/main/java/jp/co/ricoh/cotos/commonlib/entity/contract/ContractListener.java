package jp.co.ricoh.cotos.commonlib.entity.contract;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import jakarta.persistence.PrePersist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.db.DBUtil;
import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.exception.ErrorInfo;
import jp.co.ricoh.cotos.commonlib.logic.check.CheckUtil;
import jp.co.ricoh.cotos.commonlib.repository.master.MvTJmci101MasterRepository;

@Component
public class ContractListener {
	private static final String ID_PREFIX_CONT = "CC";
	private static final String ID_PREFIX_IMMUTABLE = "CIC";

	private static MvTJmci101MasterRepository mvTJmci101MasterRepository;

	private static CheckUtil checkUtil;

	private static DBUtil dbUtil;

	@Autowired
	public void setDBUtil(DBUtil dbUtil) {
		ContractListener.dbUtil = dbUtil;
	}

	@Autowired
	public void setMvTJmci101MasterRepository(MvTJmci101MasterRepository mvTJmci101MasterRepository) {
		ContractListener.mvTJmci101MasterRepository = mvTJmci101MasterRepository;
	}

	@Autowired
	public void setCheckUtil(CheckUtil checkUtil) {
		ContractListener.checkUtil = checkUtil;
	}

	/**
	 * 契約番号・恒久契約識別番号・RJ管理番号を付与する。得意先コードについて、MoM請求売上先サイト情報マスタ上の存在チェックを行う。
	 *
	 * @param contract
	 */
	@PrePersist
	public void appendsContractNumber(Contract contract) {
		/**
		 * 契約番号
		 */
		if (null == contract.getContractNumber()) {
			long sequenceContract = dbUtil.loadSingleFromSQLFile("sql/nextContractNumberSequence.sql", GeneratedNumber.class).getGeneratedNumber();
			contract.setContractNumber(ID_PREFIX_CONT + new SimpleDateFormat("yyyyMMdd").format(new Date()) + String.format("%05d", sequenceContract));
		}

		/**
		 * 恒久契約識別番号
		 */
		if (null == contract.getImmutableContIdentNumber()) {
			long sequenceImmutable = dbUtil.loadSingleFromSQLFile("sql/nextImmutableContIdentNumberSequence.sql", GeneratedNumber.class).getGeneratedNumber();
			contract.setImmutableContIdentNumber(ID_PREFIX_IMMUTABLE + new SimpleDateFormat("yyyyMMdd").format(new Date()) + String.format("%04d", sequenceImmutable));
		}

		/**
		 * RJ管理番号
		 */
		if (null == contract.getRjManageNumber()) {
			contract.setRjManageNumber(contract.getImmutableContIdentNumber());
		}

		/**
		 * 得意先コード
		 */
		if (null != contract.getBillingCustomerSpCode() && null == mvTJmci101MasterRepository.findByOriginalSystemCode(contract.getBillingCustomerSpCode())) {
			String[] regexList = { "得意先コード" };
			throw new ErrorCheckException(checkUtil.addErrorInfo(new ArrayList<ErrorInfo>(), "MasterDoesNotExistMvTJmci101Master", regexList));
		}
	}
}
