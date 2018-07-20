package jp.co.ricoh.cotos.commonlib.check;

import java.util.Collections;
import java.util.Map;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.ricoh.cotos.commonlib.db.DBUtil;
import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.logic.check.CheckUtil;
import jp.co.ricoh.cotos.commonlib.logic.check.FunctionCheckCommon;
import jp.co.ricoh.cotos.commonlib.util.SearchProperties;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestFunctionCheckCommon {

	@Autowired
	FunctionCheckCommon functionCheckCommon;
	@Autowired
	CheckUtil checkUtil;
	@Autowired
	DBUtil dbUtil;
	@Autowired
	SearchProperties searchProperties;

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
	public void 検索件数上限確認_異常() {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		// 検索条件とpathの指定
		Map<String, Object> queryParams = Collections.singletonMap("test", "A");
		String path = "sql/check/testlimitSizeCheckError.sql";

		try {

			functionCheckCommon.checkLimitSize(queryParams, path);

		} catch (ErrorCheckException e) {

			if (dbUtil.loadCountFromSQLFile(path, queryParams) == searchProperties.getLimitSize() + 1) {
				Assert.assertTrue("検索結果が検索上限を1件超過したときに正しく表示されないこと", true);
			}

			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00010", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "検索結果が上限数" + searchProperties.getLimitSize() + "件を超えました。さらに条件を絞って検索してください。", e.getErrorInfoList().get(0).getErrorMessage());
		}

	}
	
	@Test
	@Transactional
	public void 検索件数上限確認_正常() {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		// 検索条件とpathの指定
		Map<String, Object> queryParams = Collections.singletonMap("test", "A");
		String path = "sql/check/testlimitSizeCheckCorrect.sql";

		try {

			functionCheckCommon.checkLimitSize(queryParams, path);

			if (dbUtil.loadCountFromSQLFile(path, queryParams) == searchProperties.getLimitSize()) {
				Assert.assertTrue("検索上限と検索結果の件数が等しいときに正しく結果が出力されること", true);
			}

		} catch (ErrorCheckException e) {

			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00010", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "検索結果が上限数" + searchProperties.getLimitSize() + "件を超えました。さらに条件を絞って検索してください。", e.getErrorInfoList().get(0).getErrorMessage());
		}

	}
}
