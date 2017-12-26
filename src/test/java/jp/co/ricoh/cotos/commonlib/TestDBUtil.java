package jp.co.ricoh.cotos.commonlib;

import java.util.Collections;
import java.util.stream.IntStream;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.ricoh.cotos.commonlib.util.DBUtil;
import lombok.val;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDBUtil {

	@Autowired
	DBUtil dbUtil;

	@Test
	@Transactional
	public void DBUtilでSQLを発行できる() {
		IntStream.range(0, 10).forEach((i) -> {
			long beforeCount = dbUtil.loadFromSQLFile("sql/testLoadAll.sql", TestData.class).size();
			long beforeCountWithCountSql = dbUtil.loadCountFromSQLFile("sql/testCount.sql", Collections.emptyMap());
			Assert.assertEquals("listで取ったものと、Countで取ったものと件数が一致する。", beforeCount, beforeCountWithCountSql);
			val moji = "どうでしょう?" + System.currentTimeMillis();
			dbUtil.execute("sql/testInsert.sql", Collections.singletonMap("moji", moji));
			Assert.assertEquals("インサート後にデータが増えている。", beforeCount + 1, dbUtil.loadFromSQLFile("sql/testLoadAll.sql", TestData.class).size());
			Assert.assertEquals("文字が入ってる。", moji, dbUtil.loadSingleFromSQLFile("sql/testLoadMaxId.sql", TestData.class).getMoji());
		});
	}
}
