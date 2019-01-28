package jp.co.ricoh.cotos.commonlib.check;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.ricoh.cotos.commonlib.logic.check.BusinessCheck;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestBusinessCheck {

	@Autowired
	BusinessCheck businessCheck;

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
	public void 代理承認者の承認権限チェック() throws Exception {
		Assert.assertTrue("「配下(30)」代理承認者の承認権限があること", businessCheck.existsSubApproverEmployeeAuthority("00220552", "00229692"));
		Assert.assertTrue("「自社(50)」代理承認者の承認権限があること", businessCheck.existsSubApproverEmployeeAuthority("00220552", "00229692"));
		Assert.assertTrue("「地域(70)」代理承認者の承認権限があること", businessCheck.existsSubApproverEmployeeAuthority("00220552", "00220552"));
		Assert.assertFalse("「自社(50)」代理承認者の承認権限がないこと", businessCheck.existsSubApproverEmployeeAuthority("00229692", "00623100"));
		Assert.assertFalse("「地域(70)」代理承認者の承認権限がないこと", businessCheck.existsSubApproverEmployeeAuthority("00229692", "00623100"));
	}
}
