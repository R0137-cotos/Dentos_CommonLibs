package jp.co.ricoh.cotos.commonlib.entity.estimation;

import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.persistence.PrePersist;

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
	 * 見積番号を付与する。
	 *
	 * @param entity
	 */
	@PrePersist
	public void appendsEstimationNumber(Estimation entity) {
		if (null != entity.getEstimationNumber()) {
			return;
		}
		long sequence = dbUtil.loadSingleFromSQLFile("sql/nextEstimationNumberSequence.sql", GeneratedNumber.class).getGeneratedNumber();
		entity.setEstimationNumber(ID_PREFIX + new SimpleDateFormat("yyyyMMdd").format(new Date()) + String.format("%05d", sequence));
	}

}
