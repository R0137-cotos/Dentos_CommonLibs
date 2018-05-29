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
	private static final String ID_PREFIX = "CA";

	private static DBUtil dbUtil;

	@Autowired
	public void setDBUtil(DBUtil dbUtil) {
		ContractListener.dbUtil = dbUtil;
	}

	/**
	 * 契約番号を付与する。
	 * 
	 * @param contract
	 */
	@PrePersist
	@Transactional
	public void appendsContractNumber(Contract contract) {
		if (null != contract.getContractNumber()) {
			return;
		}
		long sequence = dbUtil.loadSingleFromSQLFile("sql/nextContractNumberSequence.sql", GeneratedNumber.class).getGeneratedNumber();
		long todayLong = Long.parseLong(new SimpleDateFormat("yyyyMMdd").format(new Date()) + "00000");
		while (todayLong > sequence) {
			String sql = dbUtil.loadSQLFromClasspath("sql/updateContractNumberVal.1.sql");
			String replaceSQLDirectlyBecauseIncrementedValueForOracleNamedParametersFailWithORA_01722Error = sql.replace(":incrementValue", String.valueOf(todayLong - sequence));
			dbUtil.executeWithSQLFile(replaceSQLDirectlyBecauseIncrementedValueForOracleNamedParametersFailWithORA_01722Error, Collections.emptyMap());
			dbUtil.loadFromSQLFile("sql/updateContractNumberVal.2.sql", GeneratedNumber.class);
			dbUtil.execute("sql/updateContractNumberVal.3.sql", Collections.emptyMap());
			sequence = dbUtil.loadSingleFromSQLFile("sql/nextContractNumberSequence.sql", GeneratedNumber.class).getGeneratedNumber();
		}
		contract.setContractNumber(ID_PREFIX + sequence + "-01");
	}

}
