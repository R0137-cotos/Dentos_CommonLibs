package jp.co.ricoh.cotos.commonlib;

import java.util.List;

import javax.persistence.EntityManager;
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

import jp.co.ricoh.cotos.commonlib.db.DBUtil;
import jp.co.ricoh.cotos.commonlib.entity.estimation.ItemEstimation;
import jp.co.ricoh.cotos.commonlib.repository.estimation.ItemEstimationRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestEstimationRepositories {

	@Autowired
	DBUtil dbUtil;

	@Autowired
	EntityManager entityManager;

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

	@Test
	@WithMockCustomUser
	@Transactional
	public void ItemEstimationRepositoryのテスト() throws Exception {

		// テストデータ登録
		//context.getBean(DBConfig.class).initTargetTestData("repository/authPatternMaster.sql");

		// リポジトリ作成
		ItemEstimationRepository repository = context.getBean(ItemEstimationRepository.class);
		ItemEstimation found = repository.findOne(1L);
		Assert.assertNotNull(found);

		// 永続化コンテキストから除外
		entityManager.detach(found);

		long newId = 2L;

		// 新規データ登録
		found.setId(newId);
		repository.save(found);

		// 登録データを検索
		ItemEstimation created = repository.findOne(newId);
		Assert.assertEquals(newId, created.getId());
	}


}
