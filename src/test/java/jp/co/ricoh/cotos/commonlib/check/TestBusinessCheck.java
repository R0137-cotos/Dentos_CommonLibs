package jp.co.ricoh.cotos.commonlib.check;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.ricoh.cotos.commonlib.WithMockCustomUser;
import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.ActionDiv;
import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.AuthDiv;
import jp.co.ricoh.cotos.commonlib.logic.check.BusinessCheck;
import jp.co.ricoh.cotos.commonlib.security.mom.MomAuthorityService.AuthLevel;

@RunWith(SpringRunner.class)
@SpringBootTest
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
	@WithMockCustomUser(actionDiv = ActionDiv.更新, authDiv = AuthDiv.見積_契約_手配, authLevel = AuthLevel.配下)
	public void 代理承認者の承認権限チェック_配下() throws Exception {

		Assert.assertTrue("「配下(30)」代理承認者の承認権限があること", businessCheck.existsSubApproverEmployeeAuthority("00230148", "00231268"));
	}

	@Test
	@WithMockCustomUser(actionDiv = ActionDiv.更新, authDiv = AuthDiv.見積_契約_手配, authLevel = AuthLevel.自社)
	public void 代理承認者の承認権限チェック_自社() throws Exception {

		Assert.assertTrue("「自社(50)」代理承認者の承認権限があること", businessCheck.existsSubApproverEmployeeAuthority("00229746", "00231268"));
		Assert.assertFalse("「自社(50)」代理承認者の承認権限がないこと", businessCheck.existsSubApproverEmployeeAuthority("00229746", "00469821"));
	}

	@Test
	@WithMockCustomUser(actionDiv = ActionDiv.更新, authDiv = AuthDiv.見積_契約_手配, authLevel = AuthLevel.地域)
	public void 代理承認者の承認権限チェック_地域() throws Exception {

		Assert.assertTrue("「地域(70)」代理承認者の承認権限があること", businessCheck.existsSubApproverEmployeeAuthority("00469821", "00673662"));
		Assert.assertFalse("「地域(70)」代理承認者の承認権限がないこと", businessCheck.existsSubApproverEmployeeAuthority("00469821", "00231268"));
	}

	@Test
	@WithMockCustomUser(actionDiv = ActionDiv.更新, authDiv = AuthDiv.見積_契約_手配, authLevel = AuthLevel.配下)
	public void 代理承認者の承認権限チェック_承認依頼者代理承認者チェックNG() throws Exception {

		Assert.assertFalse("代理承認者の承認権限がないこと", businessCheck.existsSubApproverEmployeeAuthority("00230148", "00230148"));
	}

	@Test
	@WithMockCustomUser(actionDiv = ActionDiv.更新, authDiv = AuthDiv.見積_契約_手配, authLevel = AuthLevel.配下)
	public void 代理承認者と承認依頼者が同一人物でないことを確認() throws Exception {

		Assert.assertTrue("別人であること", businessCheck.existsSubApproverEmployeeAuthority("00230148", "00231268"));
		Assert.assertFalse("同一人物であること", businessCheck.existsSubApproverEmployeeAuthority("00230148", "00230148"));
	}
}
