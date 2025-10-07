package jp.co.ricoh.cotos.commonlib.check;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.ricoh.cotos.commonlib.logic.check.CheckUtil;

/**
 * フォーマット変換確認メソッドのテストクラス
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTryParse {

	@Autowired
	CheckUtil checkUtil;

	static ConfigurableApplicationContext context;

	@Autowired
	public void injectContext(ConfigurableApplicationContext injectContext) {
		context = injectContext;
	}

	@AfterClass
	public static void stopAPServer() throws InterruptedException {
		if (null != context) {
			context.stop();
		}
	}

	@Test
	public void 変換可能であること_日付() {
		Assert.assertTrue("変換可能な日付であること", checkUtil.tryParseDate("2019/06/01", "yyyy/MM/dd"));
		Assert.assertTrue("変換可能な日付であること", checkUtil.tryParseDate("20190601", "yyyyMMdd"));
	}

	@Test
	public void 変換可能であること_日時() {
		Assert.assertTrue("変換可能な日付であること", checkUtil.tryParseDate("2019/06/01 12:34:56", "yyyy/MM/dd"));
		Assert.assertTrue("変換可能な日付であること", checkUtil.tryParseDate("20190601123456", "yyyyMMddhhmmss"));
	}

	@Test
	public void 変換可能なこと_NULL() {
		Assert.assertTrue("変換可能であること（処理がスルーされること）", checkUtil.tryParseDate(null, "yyyy/MM/dd"));
	}

	@Test
	public void 変換可能でないこと_日付() {
		Assert.assertFalse("変換可能な日付でないこと", checkUtil.tryParseDate("error", "yyyy/MM/dd"));
		Assert.assertFalse("変換可能な日付でないこと", checkUtil.tryParseDate("error", "yyyyMMdd"));
	}

	@Test
	public void 変換可能でないこと_日時() {
		Assert.assertFalse("変換可能な日付でないこと", checkUtil.tryParseDate("error", "yyyy/MM/dd"));
		Assert.assertFalse("変換可能な日付でないこと", checkUtil.tryParseDate("error", "yyyyMMddhhmmss"));
	}
}
