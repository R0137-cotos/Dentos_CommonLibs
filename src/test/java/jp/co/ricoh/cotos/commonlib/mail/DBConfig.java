package jp.co.ricoh.cotos.commonlib.mail;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.db.DBUtil;

@Component
public class DBConfig {
	@Autowired
	EntityManager em;
	@Autowired
	DBUtil dbUtil;

	@Transactional
	public void clearData() {
		Arrays.asList(dbUtil.loadSQLFromClasspath("sql/mail/deleteSendMailHistory.sql").split(";")).stream().forEach(sql -> em.createNativeQuery(sql).executeUpdate());
	}
}
