package jp.co.ricoh.cotos.commonlib.repository;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
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
import jp.co.ricoh.cotos.commonlib.entity.contract.Contract;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractApprovalRoute;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractApprovalRouteNode;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractAddedEditorEmpRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractApprovalResultRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractApprovalRouteNodeRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractApprovalRouteRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractAttachedFileHistoryRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractAttachedFileRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractCheckResultRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractDetailRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractEquipmentRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractInstallationLocationRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractOperationLogRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractPicAccCeEmpRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractPicAccSsOrgRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractPicIntCeEmpRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractPicIntSsOrgRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractPicMntCeEmpRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractPicMntSsOrgRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractPicSaEmpRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.CustomerContractRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.DealerContractRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ItemContractRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ItemDetailContractRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ManagedEstimationDetailRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ProductContractRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestContract {

	@Autowired
	ContractRepository contractRepository;

	@Autowired
	ContractAddedEditorEmpRepository contractAddedEditorEmpRepository;

	@Autowired
	ContractApprovalResultRepository contractApprovalResultRepository;

	@Autowired
	ContractApprovalRouteRepository contractApprovalRouteRepository;

	@Autowired
	ContractApprovalRouteNodeRepository contractApprovalRouteNodeRepository;

	@Autowired
	ContractAttachedFileRepository contractAttachedFileRepository;

	@Autowired
	ContractCheckResultRepository contractCheckResultRepository;

	@Autowired
	ContractDetailRepository contractDetailRepository;

	@Autowired
	ContractEquipmentRepository contractEquipmentRepository;

	@Autowired
	ContractOperationLogRepository contractOperationLogRepository;

	@Autowired
	ContractPicSaEmpRepository contractPicSaEmpRepository;

	@Autowired
	CustomerContractRepository customerContractRepository;

	@Autowired
	DealerContractRepository dealerContractRepository;

	@Autowired
	ItemContractRepository itemContractRepository;

	@Autowired
	ItemDetailContractRepository itemDetailContractRepository;

	@Autowired
	ProductContractRepository productContractRepository;

	@Autowired
	ContractPicMntCeEmpRepository contractPicMntCeEmpRepository;

	@Autowired
	ContractPicIntCeEmpRepository contractPicIntCeEmpRepository;

	@Autowired
	ContractPicAccCeEmpRepository contractPicAccCeEmpRepository;

	@Autowired
	ContractPicMntSsOrgRepository contractPicMntSsOrgRepository;

	@Autowired
	ContractPicAccSsOrgRepository contractPicAccSsOrgRepository;

	@Autowired
	ContractPicIntSsOrgRepository contractPicIntSsOrgRepository;

	@Autowired
	ContractInstallationLocationRepository contractInstallationLocationRepository;

	@Autowired
	ContractAttachedFileHistoryRepository contractAttachedFileHistoryRepository;

	@Autowired
	ManagedEstimationDetailRepository managedEstimationDetailRepository;

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
	public void 全てのカラムがNullではないことを確認_契約() {
		全てのカラムがNullではないことを確認_共通(contractRepository, 4L, 5L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_契約追加編集者社員() {
		全てのカラムがNullではないことを確認_共通(contractAddedEditorEmpRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_契約承認実績() {
		全てのカラムがNullではないことを確認_共通(contractApprovalResultRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_契約承認ルート() {
		全てのカラムがNullではないことを確認_共通(contractApprovalRouteRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_契約承認ルートノード() {
		全てのカラムがNullではないことを確認_共通(contractApprovalRouteNodeRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_契約添付ファイル() {
		全てのカラムがNullではないことを確認_共通(contractAttachedFileRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_契約チェック結果() {
		全てのカラムがNullではないことを確認_共通(contractCheckResultRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_契約明細() {
		全てのカラムがNullではないことを確認_共通(contractDetailRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_契約機種() {
		全てのカラムがNullではないことを確認_共通(contractEquipmentRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_契約操作履歴() {
		全てのカラムがNullではないことを確認_共通(contractOperationLogRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_契約担当SA社員() {
		全てのカラムがNullではないことを確認_共通(contractPicSaEmpRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_顧客_契約用() {
		全てのカラムがNullではないことを確認_共通(customerContractRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_販売店_契約用() {
		全てのカラムがNullではないことを確認_共通(dealerContractRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_品種_契約用() {
		全てのカラムがNullではないことを確認_共通(itemContractRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_品種明細_契約用() {
		全てのカラムがNullではないことを確認_共通(itemDetailContractRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_商品_契約用() {
		全てのカラムがNullではないことを確認_共通(productContractRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_契約担当CE社員() {
		全てのカラムがNullではないことを確認_共通(contractPicMntCeEmpRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_契約導入担当CE社員() {
		全てのカラムがNullではないことを確認_共通(contractPicIntCeEmpRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_契約受付担当CE社員() {
		全てのカラムがNullではないことを確認_共通(contractPicAccCeEmpRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_契約保守担当SS組織() {
		全てのカラムがNullではないことを確認_共通(contractPicMntSsOrgRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_契約導入担当SS組織() {
		全てのカラムがNullではないことを確認_共通(contractPicIntSsOrgRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_設置先_契約用() {
		全てのカラムがNullではないことを確認_共通(contractInstallationLocationRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_契約受付担当SS組織() {
		全てのカラムがNullではないことを確認_共通(contractPicAccSsOrgRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_契約添付ファイル履歴() {
		全てのカラムがNullではないことを確認_共通(contractAttachedFileHistoryRepository, 401L, 501L);
	}

	@Test
	public void 全てのカラムがNullではないことを確認_見積明細管理() {
		全てのカラムがNullではないことを確認_共通(managedEstimationDetailRepository, 401L, 501L);
	}

	@Test
	public void 契約承認ルート条件取得確認() {
		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/attachedFile.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/contract.sql");

		ContractApprovalRoute found = contractApprovalRouteRepository.findByContractIdAndTargetLifecycleStatus(4L, "5");
		Assert.assertNotNull(found);
	}

	@Test
	public void 契約承認ルートノード条件取得確認() {
		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/attachedFile.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/contract.sql");

		ContractApprovalRouteNode found = contractApprovalRouteNodeRepository.findByContractApprovalRouteIdAndApprovalOrderAndApproverEmpId(401L, 1, "00808347");
		Assert.assertNotNull(found);
	}

	@Test
	public void 契約条件取得確認() throws Exception {
		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/attachedFile.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/contract.sql");

		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		Date date = format.parse("2019/12/01 01:23:45");
		List<Contract> list = contractRepository.findByContractTypeAndChangePreferredDate(date);
		Assert.assertTrue(list.size() != 0);
	}

	@Test
	public void ContractRepositoryの条件テスト() throws Exception {
		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/attachedFile.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/contract.sql");

		List<String> appId = Arrays.asList("electric");
		Contract found = contractRepository.findByIdAndAppIdNotIn(4L, appId);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTools.assertColumnsNotNull(found);

		appId = Arrays.asList("cotos_dev");
		found = contractRepository.findByIdAndAppIdIn(4L, appId);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTools.assertColumnsNotNull(found);
	}

	@Transactional
	private <T extends EntityBase, ID extends Serializable> void 全てのカラムがNullではないことを確認_共通(CrudRepository<T, ID> repository, @SuppressWarnings("unchecked") ID... ids) {
		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/attachedFile.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/contract.sql");

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

	@Test
	public void ContractRepository_findByProductGrpMasterIdのテスト() {
		// テストデータ登録
		context.getBean(DBConfig.class).initTargetTestData("repository/master/productGrpMaster.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/contract.sql");
		List<Long> productGrpMasterIdList = Arrays.asList(7L, 8L, 9L);
		List<Contract> foundList = contractRepository.findByProductGrpMasterId(productGrpMasterIdList);
		// Entity の各項目の値が null ではないことを確認
		Assert.assertNotNull(foundList);
		// データが取得できていることを確認
		Assert.assertTrue(foundList.size() > 0);
	}
}
