package jp.co.ricoh.cotos.commonlib.repository.contract;

import javax.transaction.Transactional;

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
import jp.co.ricoh.cotos.commonlib.entity.contract.Contract;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestContract {

	@Autowired
	ContractRepository contractRepository;

	@Autowired
	TestTools testTools;

	// -- start ここからコピペ テスト前後のデータクリア -- //
	static ConfigurableApplicationContext context;

	@Autowired
	public void injectContext(ConfigurableApplicationContext injectContext) {
		context = injectContext;
		context.getBean(DBConfig.class).clearData();
	}

	@AfterClass
	public static void stopAPServer() throws InterruptedException {
		if (null != context) {
			context.getBean(DBConfig.class).clearData();
			context.stop();
		}
	}
	// -- start ここまでコピペ テスト前後のデータクリア -- //

	@Test
	@Transactional
	public void 全てのカラムがNullではないことを確認() {
		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/contract.sql"); // マクロから生成してここに置く。

		// データが取得できることを確認
		Contract found = contractRepository.findOne(4L);
		Assert.assertNotNull(found);

		// 全てのカラムがNullではないことを確認
		try {
			// Assert.assertEquals("正常終了", testTools.assertColumnsNotNull(found)); 山中さんがメソッド作成中
		} catch (Exception e) {
			Assert.fail("例外が発生した場合、エラー"); // assertColumnsNotNullで投げていたら。
		}
	}
}
