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
import jp.co.ricoh.cotos.commonlib.entity.master.AuthPatternMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.DispUrlAuthMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.JwtSysAuthMaster;
import jp.co.ricoh.cotos.commonlib.repository.master.AuthPatternMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.DispUrlAuthMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.JwtSysAuthMasterRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestRepositories {

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
	public void AuthPatternMasterRepositoryのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/authPatternMaster.sql");

		// リポジトリ作成
		AuthPatternMasterRepository repository = context.getBean(AuthPatternMasterRepository.class);
		AuthPatternMaster found = repository.findOne(1L);
		Assert.assertNotNull(found);

		// 永続化コンテキストから除外
		entityManager.detach(found);

		long newId = 2L;

		// 新規データ登録
		found.setAuthPatternId(newId);
		repository.save(found);

		// 登録データを検索
		AuthPatternMaster created = repository.findOne(newId);
		Assert.assertEquals(newId, created.getAuthPatternId());
	}

	@Test
	@WithMockCustomUser
	@Transactional
	public void JwtSysAuthMasterRepositoryのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/jwtSysAuthMaster.sql");

		// リポジトリ作成
		JwtSysAuthMasterRepository repository = context.getBean(JwtSysAuthMasterRepository.class);
		JwtSysAuthMaster found = repository.findBySystemIdAndPassword("cotos_test", "cotosmightyoubehappy");
		Assert.assertNotNull(found);

		// 永続化コンテキストから除外
		entityManager.detach(found);

		String newId = "test";

		// 新規データ登録
		found.setSystemId(newId);
		repository.save(found);

		// 登録データを検索
		JwtSysAuthMaster created = repository.findOne(newId);
		Assert.assertEquals(newId, created.getSystemId());
	}

	@Test
	@WithMockCustomUser
	@Transactional
	public void DispUrlAuthMasterRepositoryのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/dispUrlAuthMaster.sql");

		// リポジトリ作成
		DispUrlAuthMasterRepository repository = context.getBean(DispUrlAuthMasterRepository.class);
		List<DispUrlAuthMaster> foundList = repository.findByIdSystemDomainOrderByIdUrlPatternAscIdActionIdAsc("cotos.ricoh.co.jp");
		Assert.assertNotNull(foundList);

		String newId = "TEST_ACTION_003";

		// 登録するデータを永続化コンテキストから除外
		DispUrlAuthMaster found = foundList.get(0);
		entityManager.detach(found);

		// 新規データ登録
		found.getId().setActionId(newId);
		repository.save(found);

		// 登録データを検索
		DispUrlAuthMaster created = repository.findOne(found.getId());
		Assert.assertEquals(newId, created.getId().getActionId());
	}
	
	@Test
	@WithMockCustomUser
	@Transactional
	public void testtest() throws Exception {
		
	}
	
}
