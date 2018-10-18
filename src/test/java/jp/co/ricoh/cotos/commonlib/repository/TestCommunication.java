package jp.co.ricoh.cotos.commonlib.repository;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.ricoh.cotos.commonlib.DBConfig;
import jp.co.ricoh.cotos.commonlib.TestTools;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.repository.communication.CommunicationHistoryRepository;
import jp.co.ricoh.cotos.commonlib.repository.communication.CommunicationRepository;
import jp.co.ricoh.cotos.commonlib.repository.communication.ContactRepository;
import jp.co.ricoh.cotos.commonlib.repository.communication.ContactToRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestCommunication {

	@Autowired
	CommunicationRepository communicationRepository;

	@Autowired
	CommunicationHistoryRepository communicationHistoryRepository;

	@Autowired
	ContactRepository contactRepository;

	@Autowired
	ContactToRepository contactToRepository;

	@Autowired
	TestTools testTools;

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
	public void 全てのカラムがNullではないことを確認_コミュニケーション() {
		全てのカラムがNullではないことを確認_共通(communicationRepository, 1L, 4L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_コミュニケーション履歴() {
		全てのカラムがNullではないことを確認_共通(communicationHistoryRepository, 1L, 2L, 3L, 4L, 5L, 6L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_問い合わせ() {
		全てのカラムがNullではないことを確認_共通(contactRepository, 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_問い合わせ宛先() {
		全てのカラムがNullではないことを確認_共通(contactToRepository, 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L);
	}

	@Transactional
	private <T extends EntityBase, ID extends Serializable> void 全てのカラムがNullではないことを確認_共通(CrudRepository<T, ID> repository, @SuppressWarnings("unchecked") ID... ids) {
		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/communication.sql");

		List<ID> idList = Arrays.asList(ids);

		idList.stream().forEach(id -> {
			// データが取得できることを確認
			T found = repository.findOne(id);

			Assert.assertNotNull(found);
			// 全てのカラムがNullではないことを確認
			try {
				testTools.assertColumnsNotNull(found);
			} catch (Exception e1) {
				Assert.fail("例外が発生した場合、エラー");
			}
		});
	}
}
