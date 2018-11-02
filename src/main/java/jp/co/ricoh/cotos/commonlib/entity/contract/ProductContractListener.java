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
public class ProductContractListener {
	private static final String ID_PREFIX = "CS";

	private static DBUtil dbUtil;

	@Autowired
	public void setDBUtil(DBUtil dbUtil) {
		ProductContractListener.dbUtil = dbUtil;
	}

	/**
	 * サービス識別番号を付与する。
	 * 
	 * @param productContract
	 */
	@PrePersist
	@Transactional
	public void appendsServiceIdentNumber(ProductContract productContract) {
		if (null != productContract.getServiceIdentNumber()) {
			return;
		}
		long sequence = dbUtil.loadSingleFromSQLFile("sql/nextServiceIdentNumberSequence.sql", GeneratedNumber.class).getGeneratedNumber();
		long todayLong = Long.parseLong(new SimpleDateFormat("yyyyMMdd").format(new Date()) + "00000");
		while (todayLong > sequence) {
			String sql = dbUtil.loadSQLFromClasspath("sql/updateServiceIdentNumberVal.1.sql");
			String replaceSQLDirectlyBecauseIncrementedValueForOracleNamedParametersFailWithORA_01722Error = sql.replace(":incrementValue", String.valueOf(todayLong - sequence));
			dbUtil.executeWithSQLFile(replaceSQLDirectlyBecauseIncrementedValueForOracleNamedParametersFailWithORA_01722Error, Collections.emptyMap());
			dbUtil.loadFromSQLFile("sql/updateServiceIdentNumberVal.2.sql", GeneratedNumber.class);
			dbUtil.execute("sql/updateServiceIdentNumberVal.3.sql", Collections.emptyMap());
			sequence = dbUtil.loadSingleFromSQLFile("sql/nextServiceIdentNumberSequence.sql", GeneratedNumber.class).getGeneratedNumber();
		}
		productContract.setServiceIdentNumber(ID_PREFIX + sequence);
	}

}
