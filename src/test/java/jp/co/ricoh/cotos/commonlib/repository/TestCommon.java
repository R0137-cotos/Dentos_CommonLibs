package jp.co.ricoh.cotos.commonlib.repository;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.ricoh.cotos.commonlib.DBConfig;
import jp.co.ricoh.cotos.commonlib.TestTools;
import jp.co.ricoh.cotos.commonlib.entity.common.AttachedFile;
import jp.co.ricoh.cotos.commonlib.entity.common.MailSendHistory;
import jp.co.ricoh.cotos.commonlib.entity.common.VMailAddressList;
import jp.co.ricoh.cotos.commonlib.repository.common.AttachedFileRepository;
import jp.co.ricoh.cotos.commonlib.repository.common.MailSendHistoryRepository;
import jp.co.ricoh.cotos.commonlib.repository.common.VMailAddressListRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestCommon {

	/** 添付ファイル */
	@Autowired
	AttachedFileRepository attachedFileRepository;

	/** メール送信履歴 */
	@Autowired
	MailSendHistoryRepository mailSendHistoryRepository;
	
	/**
	 * メールアドレス一覧
	 */
	@Autowired
	VMailAddressListRepository vMailAddressListRepository;

	@Autowired
	TestTools testTool;

	static ConfigurableApplicationContext context;

	@Autowired
	public void injectContext(ConfigurableApplicationContext injectContext) {
		context = injectContext;
		context.getBean(DBConfig.class).clearData();
		context.getBean(DBConfig.class).initTargetTestData("repository/attachedFile.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/mailTemplateMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/mailControlMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/master/mailConvertValueMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/mailSendHistory.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/estimation_all.sql");
	}

	@AfterClass
	public static void stopAPServer() throws InterruptedException {
		if (null != context) {
			context.getBean(DBConfig.class).clearData();
			context.stop();
		}
	}

	@Test
	public void AttachedFileRepositoryのテスト() throws Exception {

		AttachedFile found = attachedFileRepository.findOne(1L);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);
	}

	@Test
	public void MailSendHistoryRepositoryのテスト() throws Exception {

		MailSendHistory found = mailSendHistoryRepository.findOne(1L);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);
	}
	
	@Test
	public void VMailAddressListRepositoryのテスト() throws Exception {

		VMailAddressList found = vMailAddressListRepository.findOne(1L);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);
		
		List<String> foundList = vMailAddressListRepository.findByDomainAndTableAndTranId("1","1",4);
		
		// Entity が null ではないことを確認
		Assert.assertNotNull(foundList);

	}
}
