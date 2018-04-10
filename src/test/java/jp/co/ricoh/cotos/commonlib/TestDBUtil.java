package jp.co.ricoh.cotos.commonlib;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
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

			Map<String, Object> testParam = new HashMap<>();

			val moji = "どうでしょう?" + System.currentTimeMillis();
			val likeSearchString = "aiueo" + System.currentTimeMillis();
			testParam.put("moji", moji);
			testParam.put("likeSearchString", likeSearchString);

			dbUtil.execute("sql/testInsert.sql", testParam);
			Assert.assertEquals("インサート後にデータが増えている。", beforeCount + 1, dbUtil.loadFromSQLFile("sql/testLoadAll.sql", TestData.class).size());
			Assert.assertEquals("文字が入ってる。", moji, dbUtil.loadSingleFromSQLFile("sql/testLoadMaxId.sql", TestData.class).getMoji());

			Assert.assertNull("Like検索できないこと", dbUtil.loadSingleFromSQLFile("sql/testLoadLikeSearch.sql", TestData.class, Collections.singletonMap("likeSearchString", "a%")));
			Assert.assertTrue("Like検索できること", dbUtil.loadFromSQLFile("sql/testLoadLikeSearch.sql", TestData.class, Collections.singletonMap("likeSearchString", "iue")).size() > 0);
		});
	}
}
