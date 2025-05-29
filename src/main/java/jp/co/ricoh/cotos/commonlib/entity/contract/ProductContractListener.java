package jp.co.ricoh.cotos.commonlib.entity.contract;

import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.persistence.PrePersist;

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
	public void appendsServiceIdentNumber(ProductContract productContract) {
		if (null != productContract.getServiceIdentNumber()) {
			return;
		}
		long sequence = dbUtil.loadSingleFromSQLFile("sql/nextServiceIdentNumberSequence.sql", GeneratedNumber.class).getGeneratedNumber();
		productContract.setServiceIdentNumber(ID_PREFIX + new SimpleDateFormat("yyyyMMdd").format(new Date()) + String.format("%05d", sequence));
	}

}
