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

import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.logic.check.CheckUtil;
import jp.co.ricoh.cotos.commonlib.logic.check.CommonCheck;
import jp.co.ricoh.cotos.commonlib.util.SearchPropaties;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCommonCheck {

	@Autowired
	CommonCheck commonCheck;
	@Autowired
	CheckUtil checkUtil;
	@Autowired
	SearchPropaties searchPropaties;

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
	public void 検索件数上限確認() {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		// application.ymlより取得
		int limitSize = searchPropaties.getLimitSize();

		try {
			// 検索条件とpathの指定
			Map<String, Object> queryPrams = Collections.singletonMap("test", "A");
			String path = "sql/check/testlimitSizeCheck.sql";

			commonCheck.LimitSizeCheck(queryPrams, path);

			Assert.fail("正常終了してしまった");
		} catch (ErrorCheckException e) {

			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00010", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "検索結果が" + limitSize + "件以上です。さらに条件を絞って検索してください。", e.getErrorInfoList().get(0).getErrorMessage());
		}

	}
}
