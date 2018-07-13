package jp.co.ricoh.cotos.commonlib.fileupdownload;

import java.util.Arrays;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.db.DBUtil;

@Component
public class DBConfigFileUpDownload {
	@Autowired
	EntityManager em;
	@Autowired
	DBUtil dbUtil;

	@Transactional
	public void clearData() {
		Arrays.asList(dbUtil.loadSQLFromClasspath("sql/fileupdownload/deleteAttachedFile.sql").split(";")).stream().forEach(sql -> em.createNativeQuery(sql).executeUpdate());
	}
}
