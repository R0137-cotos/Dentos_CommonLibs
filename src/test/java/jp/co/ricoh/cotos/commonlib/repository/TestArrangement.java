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
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWork;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWorkApprovalRoute;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWorkApprovalRouteNode;
import jp.co.ricoh.cotos.commonlib.repository.arrangement.ArrangementPicWorkerEmpRepository;
import jp.co.ricoh.cotos.commonlib.repository.arrangement.ArrangementRepository;
import jp.co.ricoh.cotos.commonlib.repository.arrangement.ArrangementWorkApprovalResultRepository;
import jp.co.ricoh.cotos.commonlib.repository.arrangement.ArrangementWorkApprovalRouteNodeRepository;
import jp.co.ricoh.cotos.commonlib.repository.arrangement.ArrangementWorkApprovalRouteRepository;
import jp.co.ricoh.cotos.commonlib.repository.arrangement.ArrangementWorkAttachedFileRepository;
import jp.co.ricoh.cotos.commonlib.repository.arrangement.ArrangementWorkCheckResultRepository;
import jp.co.ricoh.cotos.commonlib.repository.arrangement.ArrangementWorkErrorLogRepository;
import jp.co.ricoh.cotos.commonlib.repository.arrangement.ArrangementWorkOperationLogRepository;
import jp.co.ricoh.cotos.commonlib.repository.arrangement.ArrangementWorkRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestArrangement {

	/** 手配 */
	@Autowired
	ArrangementRepository arrangementRepository;

	/** 手配業務 */
	@Autowired
	ArrangementWorkRepository arrangementWorkRepository;

	/** 手配業務チェック結果 */
	@Autowired
	ArrangementWorkCheckResultRepository arrangementWorkCheckResultRepository;

	/** 手配業務承認ルート */
	@Autowired
	ArrangementWorkApprovalRouteRepository arrangementWorkApporovalRouteRepository;

	/** 手配業務承認ルートノード */
	@Autowired
	ArrangementWorkApprovalRouteNodeRepository arrangmentWorkApprovalRouteNodeRepository;

	/** 手配業務承認実績 */
	@Autowired
	ArrangementWorkApprovalResultRepository arrangementWorkApprovalResultRepository;

	/** 手配業務操作履歴 */
	@Autowired
	ArrangementWorkOperationLogRepository arrangementWorkOperationLogRepository;

	/** 手配業務添付ファイル */
	@Autowired
	ArrangementWorkAttachedFileRepository arrangmentWorkAttachedFileRepository;

	/** 担当作業者社員 */
	@Autowired
	ArrangementPicWorkerEmpRepository arrangementPicWorkerEmpRepository;

	/** 手配業務エラー履歴 */
	@Autowired
	ArrangementWorkErrorLogRepository arrangementWorkErrorLogRepository;

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
	public void 全てのカラムがNullではないことを確認_手配() {
		全てのカラムがNullではないことを確認_共通(arrangementRepository, 4L, 5L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_手配業務() {
		全てのカラムがNullではないことを確認_共通(arrangementWorkRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_手配業務チェック結果() {
		全てのカラムがNullではないことを確認_共通(arrangementWorkCheckResultRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_手配業務承認ルート() {
		全てのカラムがNullではないことを確認_共通(arrangementWorkApporovalRouteRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_手配業務承認ルートノード() {
		全てのカラムがNullではないことを確認_共通(arrangmentWorkApprovalRouteNodeRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_手配業務承認実績() {
		全てのカラムがNullではないことを確認_共通(arrangementWorkApprovalResultRepository, 402L, 502L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_手配業務操作履歴() {
		全てのカラムがNullではないことを確認_共通(arrangementWorkOperationLogRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_手配業務添付ファイル() {
		全てのカラムがNullではないことを確認_共通(arrangmentWorkAttachedFileRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_担当作業者社員() {
		全てのカラムがNullではないことを確認_共通(arrangementPicWorkerEmpRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_手配業務エラー履歴() {
		全てのカラムがNullではないことを確認_共通(arrangementWorkErrorLogRepository, 401L, 501L);
	}

	@Test
	public void 手配承認ルート条件取得確認() {
		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/attachedFile.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/arrangement.sql");

		ArrangementWorkApprovalRoute found = arrangementWorkApporovalRouteRepository.findByArrangementWorkId(401L);
		Assert.assertNotNull(found);
	}

	@Test
	public void 手配承認ルートノード条件取得確認() {
		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/attachedFile.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/arrangement.sql");

		ArrangementWorkApprovalRouteNode found = arrangmentWorkApprovalRouteNodeRepository.findByArrangementWorkApprovalRouteIdAndApprovalOrderAndApproverEmpId(401L, 1, "00808347");
		Assert.assertNotNull(found);
	}

	@Test
	public void ArrangementWorkRepositoryの条件テスト() throws Exception {
		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/attachedFile.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/arrangement.sql");

		List<String> appId = Arrays.asList("electric");
		ArrangementWork found = arrangementWorkRepository.findByIdAndAppIdNotIn(401L, appId);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTools.assertColumnsNotNull(found);

		appId = Arrays.asList("cotos_dev");
		found = arrangementWorkRepository.findByIdAndAppIdIn(401L, appId);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTools.assertColumnsNotNull(found);
	}

	@Transactional
	private <T extends EntityBase, ID extends Serializable> void 全てのカラムがNullではないことを確認_共通(CrudRepository<T, ID> repository, @SuppressWarnings("unchecked") ID... ids) {
		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/attachedFile.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/arrangement.sql");

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
