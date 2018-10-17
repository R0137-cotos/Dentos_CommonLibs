package jp.co.ricoh.cotos.commonlib.entity.estimation;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;

import javax.persistence.PrePersist;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.db.DBUtil;

@Component
public class EstimationListener {
	private static final String ID_PREFIX = "CE";

	private static DBUtil dbUtil;

	@Autowired
	public void setDBUtil(DBUtil dbUtil) {
		EstimationListener.dbUtil = dbUtil;
	}

	/**
	 * 見積番号を付与する。 しかしながら、同時に２回走るとおかしくなる（翌日の数字となる）ため、バッチでやったほうが安全かもしれない。
	 * 
	 * @param entity
	 */
	@PrePersist
	@Transactional
	public void appendsEstimationNumber(Estimation entity) {
		if (null != entity.getEstimationNumber()) {
			return;
		}
		long sequence = dbUtil.loadSingleFromSQLFile("sql/nextEstimationNumberSequence.sql", GeneratedNumber.class).getGeneratedNumber();
		long todayLong = Long.parseLong(new SimpleDateFormat("yyyyMMdd").format(new Date()) + "00000");
		while (todayLong > sequence) {
			String sql = dbUtil.loadSQLFromClasspath("sql/updateEstimationNumberVal.1.sql");
			String replaceSQLDirectlyBecauseIncrementedValueForOracleNamedParametersFailWithORA_01722Error = sql.replace(":incrementValue", String.valueOf(todayLong - sequence));
			dbUtil.executeWithSQLFile(replaceSQLDirectlyBecauseIncrementedValueForOracleNamedParametersFailWithORA_01722Error, Collections.emptyMap());
			dbUtil.loadFromSQLFile("sql/updateEstimationNumberVal.2.sql", GeneratedNumber.class);
			dbUtil.execute("sql/updateEstimationNumberVal.3.sql", Collections.emptyMap());
			sequence = dbUtil.loadSingleFromSQLFile("sql/nextEstimationNumberSequence.sql", GeneratedNumber.class).getGeneratedNumber();
		}
		entity.setEstimationNumber(ID_PREFIX + sequence + "-01");
	}

}
