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
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractApprovalRoute;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractApprovalRouteNode;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractAddedEditorEmpRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractApprovalResultRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractApprovalRouteNodeRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractApprovalRouteRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractAttachedFileRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractCheckResultRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractDetailRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractOperationLogRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractPicSaEmpRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.CustomerContractRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.DealerContractRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ItemContractRepository;
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
	ProductContractRepository productContractRepository;

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
	public void 全てのカラムがNullではないことを確認_商品_契約用() {

		全てのカラムがNullではないことを確認_共通(productContractRepository, 401L, 501L);
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

}
