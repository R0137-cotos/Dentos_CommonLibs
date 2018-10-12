package jp.co.ricoh.cotos.commonlib;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.commons.lang3.reflect.FieldUtils;
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
import jp.co.ricoh.cotos.commonlib.entity.master.ProductCompMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.ProductGrpMaster;
import jp.co.ricoh.cotos.commonlib.repository.master.ProductCompMasterRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.ProductGrpMasterRepository;

/**
 * Repository（マスタドメイン）のテストクラス
 *
 * @author hideto.yamanaka
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestRepositoriesMaster {

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
	public void ProductCompMasterRepositoryのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/productCompMaster.sql");

		// リポジトリ作成
		Long id = 1L;
		ProductCompMasterRepository repository = context.getBean(ProductCompMasterRepository.class);
		ProductCompMaster found = repository.findOne(id);

		Assert.assertNotNull(found);

		// TODO 各項目の値が null ではないことを確認

		Assert.assertTrue(true);
	}

	@Test
	@WithMockCustomUser
	@Transactional
	public void ProductGrpMasterRepositoryのテスト() throws Exception {

		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/productGrpMaster.sql");

		// リポジトリ作成
		Long id = 1L;
		ProductGrpMasterRepository repository = context.getBean(ProductGrpMasterRepository.class);
		ProductGrpMaster found = repository.findOne(id);

		Assert.assertNotNull(found);

		// TODO 各項目の値が null ではないことを確認

		Assert.assertTrue(true);
	}

	/**
	 * TODO EntityのデータをMapにする
	 */
	public static Map<String, Object> createEntityValueMap(Object entity) {
		Map<String, Object> retMap = new HashMap<>();

		FieldUtils.getAllFieldsList(entity.getClass()).stream().filter(putField -> !putField.getName().startsWith("$")).forEach(field -> {
			try {
				retMap.put(field.getName(), field.get(entity));
			} catch (IllegalAccessException e) {
				retMap.put(field.getName(), null);
			}
		});

		return retMap;
	}

	public static void main(String[] args) {
		Map<String, Object> mapData = createEntityValueMap(new ProductCompMaster());
		mapData = mapData;
	}

}
