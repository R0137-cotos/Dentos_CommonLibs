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
import jp.co.ricoh.cotos.commonlib.entity.EnumType.ApprovalTargetType;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.ServiceCategory;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.WorkflowType;
import jp.co.ricoh.cotos.commonlib.entity.communication.Communication;
import jp.co.ricoh.cotos.commonlib.entity.communication.Contact;
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
		全てのカラムがNullではないことを確認_共通(communicationRepository, 1L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_コミュニケーション履歴() {
		全てのカラムがNullではないことを確認_共通(communicationHistoryRepository, 1L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_問い合わせ() {
		全てのカラムがNullではないことを確認_共通(contactRepository, 5L);
	}

	@Test
	public void CommunicationRepositoryの条件テスト() {
		context.getBean(DBConfig.class).initTargetTestData("repository/communication.sql");
		List<String> appId = Arrays.asList("electric");
		List<Communication> list = communicationRepository.findByProcessCategoryAndLoginUserMomEmployeeIdAndAppIdNotIn("1", "dummy_request_to_id_1", appId);
		Assert.assertNotEquals(0, list.size());
		appId = Arrays.asList("cotos_dev");
		list = communicationRepository.findByProcessCategoryAndLoginUserMomEmployeeIdAndAppIdIn("1", "dummy_request_to_id_1", appId);
		Assert.assertNotEquals(0, list.size());
		list = communicationRepository.findByProcessCategoryAndLoginUserMomEmployeeId("1", "dummy_request_to_id_1");
		Assert.assertEquals(1, list.size());
		list = communicationRepository.findByTargetDocKeyAndWorkflowTypeAndApprovalTargetTypeAndServiceCategory("4", WorkflowType.承認フロー, ApprovalTargetType.新規, ServiceCategory.見積);
		Assert.assertEquals(1, list.size());
	}

	@Test
	public void ContactRepositoryの条件テスト() {
		context.getBean(DBConfig.class).initTargetTestData("repository/communication.sql");
		List<String> appId = Arrays.asList("electric");
		List<Contact> list = contactRepository.findByEstimationIdAndServiceCategoryAndParentIdIsNullAndAppIdNotInOrderByIdDesc(4L, "1", appId);
		Assert.assertNotEquals(0, list.size());
		appId = Arrays.asList("cotos_dev");
		list = contactRepository.findByEstimationIdAndServiceCategoryAndParentIdIsNullAndAppIdInOrderByIdDesc(4L, "1", appId);
		Assert.assertNotEquals(0, list.size());
		list = contactRepository.findByEstimationIdAndServiceCategoryAndParentIdIsNullOrderByIdDesc(4L, "1");
		Assert.assertEquals(1, list.size());
	}

	@Test
	public void 全てのカラムがNullではないことを確認_問い合わせ宛先() {
		全てのカラムがNullではないことを確認_共通(contactToRepository, 1L);
	}

	@Test
	public void 親がいない場合には親がnullになることを確認_問い合わせ() {
		context.getBean(DBConfig.class).initTargetTestData("repository/communication.sql");
		Contact child = contactRepository.findOne(4L);
		Assert.assertNull(child.getParent());
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
