package jp.co.ricoh.cotos.commonlib.entity.contract;

import javax.persistence.PrePersist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.db.DBUtil;

@Component
public class ContractListener {
	private static final String ID_PREFIX_CONT = "CC";
	private static final String ID_PREFIX_IMMUTABLE = "CIC";

	private static DBUtil dbUtil;

	@Autowired
	public void setDBUtil(DBUtil dbUtil) {
		ContractListener.dbUtil = dbUtil;
	}

	/**
	 * 契約番号・恒久契約識別番号を付与する。
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
			contract.setContractNumber(ID_PREFIX_CONT + String.format("%05d", sequenceContract));
		}

		/**
		 * 恒久契約識別番号
		 */
		if (null == contract.getImmutableContIdentNumber()) {
			long sequenceImmutable = dbUtil.loadSingleFromSQLFile("sql/nextImmutableContIdentNumberSequence.sql", GeneratedNumber.class).getGeneratedNumber();
			contract.setImmutableContIdentNumber(ID_PREFIX_IMMUTABLE + String.format("%04d", sequenceImmutable));
		}
	}
}
