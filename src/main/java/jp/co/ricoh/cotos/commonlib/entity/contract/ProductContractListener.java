package jp.co.ricoh.cotos.commonlib.entity.contract;

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
		productContract.setServiceIdentNumber(ID_PREFIX + String.format("%05d", sequence));
	}

}
