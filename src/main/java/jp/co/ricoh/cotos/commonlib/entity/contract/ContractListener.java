package jp.co.ricoh.cotos.commonlib.entity.contract;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

import javax.persistence.PrePersist;
import javax.transaction.Transactional;

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
	@Transactional
	public void appendsContractNumber(Contract contract) {
		
		/**
		 * 契約番号
		 */
		if (null != contract.getContractNumber()) {
			return;
		}
		long sequenceContract = dbUtil.loadSingleFromSQLFile("sql/nextContractNumberSequence.sql", GeneratedNumber.class).getGeneratedNumber();
		long todayLongContract = Long.parseLong(new SimpleDateFormat("yyyyMMdd").format(new Date()) + "00000");
		while (todayLongContract > sequenceContract) {
			String sql = dbUtil.loadSQLFromClasspath("sql/updateContractNumberVal.1.sql");
			String replaceSQLDirectlyBecauseIncrementedValueForOracleNamedParametersFailWithORA_01722Error = sql.replace(":incrementValue", String.valueOf(todayLongContract - sequenceContract));
			dbUtil.executeWithSQLFile(replaceSQLDirectlyBecauseIncrementedValueForOracleNamedParametersFailWithORA_01722Error, Collections.emptyMap());
			dbUtil.loadFromSQLFile("sql/updateContractNumberVal.2.sql", GeneratedNumber.class);
			dbUtil.execute("sql/updateContractNumberVal.3.sql", Collections.emptyMap());
			sequenceContract = dbUtil.loadSingleFromSQLFile("sql/nextContractNumberSequence.sql", GeneratedNumber.class).getGeneratedNumber();
		}
		contract.setContractNumber(ID_PREFIX_CONT + sequenceContract);

		/**
		 * 恒久契約識別番号
		 */
		if (null != contract.getImmutableContIdentNumber()) {
			return;
		}
		long sequenceImmutable = dbUtil.loadSingleFromSQLFile("sql/nextImmutableContIdentNumberSequence.sql", GeneratedNumber.class).getGeneratedNumber();
		long todayLongImmutable = Long.parseLong(new SimpleDateFormat("yyyyMMdd").format(new Date()) + "00000");
		while (todayLongImmutable > sequenceImmutable) {
			String sql = dbUtil.loadSQLFromClasspath("sql/updateImmutableContIdentNumberVal.1.sql");
			String replaceSQLDirectlyBecauseIncrementedValueForOracleNamedParametersFailWithORA_01722Error = sql.replace(":incrementValue", String.valueOf(todayLongImmutable - sequenceImmutable));
			dbUtil.executeWithSQLFile(replaceSQLDirectlyBecauseIncrementedValueForOracleNamedParametersFailWithORA_01722Error, Collections.emptyMap());
			dbUtil.loadFromSQLFile("sql/updateImmutableContIdentNumberVal.2.sql", GeneratedNumber.class);
			dbUtil.execute("sql/updateImmutableContIdentNumberVal.3.sql", Collections.emptyMap());
			sequenceImmutable = dbUtil.loadSingleFromSQLFile("sql/nextImmutableContIdentNumberSequence.sql", GeneratedNumber.class).getGeneratedNumber();
		}
		contract.setImmutableContIdentNumber(ID_PREFIX_IMMUTABLE + sequenceImmutable);
	}

}
