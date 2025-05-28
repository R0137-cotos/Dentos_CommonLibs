package jp.co.ricoh.cotos.commonlib;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import jakarta.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.ricoh.cotos.commonlib.db.DBUtil;
import lombok.val;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDBUtil {

	@Autowired
	DBUtil dbUtil;

	static ConfigurableApplicationContext context;

	@Autowired
	public void injectContext(ConfigurableApplicationContext injectContext) {
		context = injectContext;
	}

	private static boolean isH2() {
		return "org.h2.Driver".equals(context.getEnvironment().getProperty("spring.datasource.driverClassName"));
	}
	
	@Test
	@Transactional
	public void DBUtilでSQLを発行できる() {
		
		// h2以外ならスルー
		if (!isH2()) {
			return;
		}
		
		IntStream.range(0, 10).forEach((i) -> {
			long beforeCount = dbUtil.loadFromSQLFile("sql/testLoadAll.sql", TestData.class).size();
			long beforeCountWithCountSql = dbUtil.loadCountFromSQLFile("sql/testLoadAll.sql", Collections.emptyMap());
			Assert.assertEquals("listで取ったものと、Countで取ったものと件数が一致する。", beforeCount, beforeCountWithCountSql);

			Map<String, Object> testParam = new HashMap<>();

			long time = System.currentTimeMillis();
			val moji = "どうでしょう?" + time;
			val likeSearchString = "test" + time;
			testParam.put("moji", moji);
			testParam.put("likeSearchString", likeSearchString);

			dbUtil.execute("sql/testInsert.sql", testParam);
			Assert.assertEquals("インサート後にデータが増えている。", beforeCount + 1, dbUtil.loadFromSQLFile("sql/testLoadAll.sql", TestData.class).size());
			Assert.assertEquals("文字が入ってる。", moji, dbUtil.loadSingleFromSQLFile("sql/testLoadMaxId.sql", TestData.class).getMoji());
		});
	}

	@Test
	@Transactional
	public void Like句のエスケープが正常に実施されることを確認() {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}
		
		Map<String, Object> testDataUnderscore = new HashMap<>();
		testDataUnderscore.put("moji", "半角アンダースコア");
		testDataUnderscore.put("likeSearchString", "ai_eo");
		dbUtil.execute("sql/testInsert.sql", testDataUnderscore);

		Map<String, Object> testDataUnderscoreMulti = new HashMap<>();
		testDataUnderscoreMulti.put("moji", "全角アンダースコア");
		testDataUnderscoreMulti.put("likeSearchString", "ai＿eo");
		dbUtil.execute("sql/testInsert.sql", testDataUnderscoreMulti);

		Map<String, Object> testDataPercent = new HashMap<>();
		testDataPercent.put("moji", "半角パーセント");
		testDataPercent.put("likeSearchString", "ai%eo");
		dbUtil.execute("sql/testInsert.sql", testDataPercent);

		Map<String, Object> testDataPercentMulti = new HashMap<>();
		testDataPercentMulti.put("moji", "全角パーセント");
		testDataPercentMulti.put("likeSearchString", "ai％eo");
		dbUtil.execute("sql/testInsert.sql", testDataPercentMulti);

		Map<String, Object> testDataEscape = new HashMap<>();
		testDataEscape.put("moji", "エスケープ文字");
		testDataEscape.put("likeSearchString", "ai\\eo");
		dbUtil.execute("sql/testInsert.sql", testDataEscape);

		// 半角アンダースコア
		Assert.assertEquals("Like検索件数が1件であること", 1, dbUtil.loadFromSQLFile("sql/testLoadLikeSearch.sql", TestData.class, Collections.singletonMap("likeSearchString", "i_e")).size());
		Assert.assertEquals("Like検索できること", "半角アンダースコア", dbUtil.loadFromSQLFile("sql/testLoadLikeSearch.sql", TestData.class, Collections.singletonMap("likeSearchString", "i_e")).get(0).getMoji());

		// 全角アンダースコア
		Assert.assertEquals("Like検索件数が1件であること", 1, dbUtil.loadFromSQLFile("sql/testLoadLikeSearch.sql", TestData.class, Collections.singletonMap("likeSearchString", "i＿e")).size());
		Assert.assertEquals("Like検索できること", "全角アンダースコア", dbUtil.loadFromSQLFile("sql/testLoadLikeSearch.sql", TestData.class, Collections.singletonMap("likeSearchString", "i＿e")).get(0).getMoji());

		// 半角パーセント
		Assert.assertEquals("Like検索件数が1件であること", 1, dbUtil.loadFromSQLFile("sql/testLoadLikeSearch.sql", TestData.class, Collections.singletonMap("likeSearchString", "i%e")).size());
		Assert.assertEquals("Like検索できること", "半角パーセント", dbUtil.loadFromSQLFile("sql/testLoadLikeSearch.sql", TestData.class, Collections.singletonMap("likeSearchString", "i%e")).get(0).getMoji());

		// 全角パーセント
		Assert.assertEquals("Like検索件数が1件であること", 1, dbUtil.loadFromSQLFile("sql/testLoadLikeSearch.sql", TestData.class, Collections.singletonMap("likeSearchString", "i％e")).size());
		Assert.assertEquals("Like検索できること", "全角パーセント", dbUtil.loadFromSQLFile("sql/testLoadLikeSearch.sql", TestData.class, Collections.singletonMap("likeSearchString", "i％e")).get(0).getMoji());

		// エスケープ文字で検索できることの確認
		Assert.assertEquals("Like検索件数が1件であること", 1, dbUtil.loadFromSQLFile("sql/testLoadLikeSearch.sql", TestData.class, Collections.singletonMap("likeSearchString", "\\")).size());
		Assert.assertEquals("Like検索できること", "エスケープ文字", dbUtil.loadFromSQLFile("sql/testLoadLikeSearch.sql", TestData.class, Collections.singletonMap("likeSearchString", "\\")).get(0).getMoji());
	}
}
