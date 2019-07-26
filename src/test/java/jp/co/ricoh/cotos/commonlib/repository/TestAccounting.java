package jp.co.ricoh.cotos.commonlib.repository;

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
import jp.co.ricoh.cotos.commonlib.entity.accounting.Accounting;
import jp.co.ricoh.cotos.commonlib.entity.accounting.Wjcmj301KiykSikyuCtsWk;
import jp.co.ricoh.cotos.commonlib.entity.accounting.Wjcmj302SikyuMisiCtsWk;
import jp.co.ricoh.cotos.commonlib.entity.accounting.Wjcmj303GnkHrkeCtsWk;
import jp.co.ricoh.cotos.commonlib.repository.accounting.AccountingRepository;
import jp.co.ricoh.cotos.commonlib.repository.accounting.Wjcmj301KiykSikyuCtsWkRepository;
import jp.co.ricoh.cotos.commonlib.repository.accounting.Wjcmj302SikyuMisiCtsWkRepository;
import jp.co.ricoh.cotos.commonlib.repository.accounting.Wjcmj303GnkHrkeCtsWkRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestAccounting {

	static ConfigurableApplicationContext context;

	@Autowired
	TestTools testTool;

	@Autowired
	AccountingRepository accountingRespository;

	@Autowired
	Wjcmj301KiykSikyuCtsWkRepository wjcmj301KiykSikyuCtsWkRepository;

	@Autowired
	Wjcmj302SikyuMisiCtsWkRepository wjcmj302SikyuMisiCtsWkRepository;

	@Autowired
	Wjcmj303GnkHrkeCtsWkRepository wjcmj303GnkHrkeCtsWkRepository;

	@Autowired
	public void injectContext(ConfigurableApplicationContext injectContext) {
		context = injectContext;
		context.getBean(DBConfig.class).clearData();
		context.getBean(DBConfig.class).initTargetTestData("repository/accounting/accounting.sql");
	}

	@AfterClass
	public static void stopAPServer() throws InterruptedException {
		if (null != context) {
			context.getBean(DBConfig.class).clearData();
			context.stop();
		}
	}

	@Test
	public void AccountingRepositoryのテスト() throws Exception {

		Accounting found = accountingRespository.findOne(1L);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

	}

	@Test
	public void Wjcmj301KiykSikyuCtsWkRepositoryのテスト() throws Exception {

		context.getBean(DBConfig.class).initTargetTestData("repository/accounting/wjcmj301KiykSikyuCtsWk.sql");

		Wjcmj301KiykSikyuCtsWk found = wjcmj301KiykSikyuCtsWkRepository.findOne("00011878000001000000");

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);
	}

	@Test
	public void Wjcmj302SikyuMisiCtsWkRepositoryのテスト() throws Exception {

		context.getBean(DBConfig.class).initTargetTestData("repository/accounting/wjcmj302SikyuMisiCtsWk.sql");

		Wjcmj302SikyuMisiCtsWk found = wjcmj302SikyuMisiCtsWkRepository.findOne("00011878000001000000");

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);
	}

	@Test
	public void Wjcmj303GnkHrkeCtsWkRepositoryのテスト() throws Exception {

		context.getBean(DBConfig.class).initTargetTestData("repository/accounting/wjcmj303GnkHrkeCtsWk.sql");

		Wjcmj303GnkHrkeCtsWk found = wjcmj303GnkHrkeCtsWkRepository.findOne("00011878000001001000");

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);
	}

}
