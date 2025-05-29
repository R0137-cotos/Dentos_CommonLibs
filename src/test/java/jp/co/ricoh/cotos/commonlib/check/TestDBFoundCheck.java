package jp.co.ricoh.cotos.commonlib.check;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.ricoh.cotos.commonlib.logic.check.DBFoundCheck;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestDBFoundCheck {

	@Autowired
	DBFoundCheck dBFoundCheck;

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
	public void MoM社員マスタ存在チェック() {
		Assert.assertTrue("MoM社員情報が存在すること", dBFoundCheck.existsEmployeeMaster("00229692"));
		Assert.assertFalse("MoM社員情報が存在しないこと", dBFoundCheck.existsEmployeeMaster("test"));
	}
}
