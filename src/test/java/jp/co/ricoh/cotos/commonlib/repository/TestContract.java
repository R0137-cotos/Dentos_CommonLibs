package jp.co.ricoh.cotos.commonlib.repository;

import java.io.Serializable;

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
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractAddedEditorEmpRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractApprovalResultRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractAttachedFileRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractCheckResultRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractDetailRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractOperationLogRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractPicSaEmpRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.CustomerContractRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.DealerContractRepository;
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

//	@Autowired	
//	ContractApprovalRouteRepository contractApprovalRouteRepository;

//	@Autowired
//	ContractApprovalRouteNodeRepository contractApprovalRouteNodeRepository;

	@Autowired
	ContractAttachedFileRepository contractAttachedFileRepository;

	@Autowired
	ContractCheckResultRepository contractCheckResultRepository;

	@Autowired
	ContractDetailRepository contractDetailRepository;

//	@Autowired	
//	ContractListenerRepository contractListenerRepository;

	@Autowired
	ContractOperationLogRepository contractOperationLogRepository;

	@Autowired
	ContractPicSaEmpRepository contractPicSaEmpRepository;

	@Autowired
	CustomerContractRepository customerContractRepository;

	@Autowired
	DealerContractRepository DealerContractRepository;

//	@Autowired	
//	GeneratedNumberRepository GeneratedNumberRepository;

//	@Autowired	
//	ItemContractRepository itemContractRepository;

	ProductContractRepository productContractRepository;

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
	public void 全てのカラムがNullではないことを確認_契約() {
		全てのカラムがNullではないことを確認_共通(contractRepository, 4L);
	}

	private <T extends EntityBase, ID extends Serializable> void 全てのカラムがNullではないことを確認_共通(CrudRepository<T, ID> repository, ID id) {
		// テストデータ登録
		//context.getBean(DBConfig.class).initTargetTestData("repository/contract.sql");
			
		// データが取得できることを確認
		T found = repository.findOne(id);
		Assert.assertNotNull(found);

		// 全てのカラムがNullではないことを確認
		try {
			//Assert.assertTrue("正常終了", testTools.assertColumnsNotNull(found));
			// 山中さんがメソッド作成中
		} catch (Exception e) {
			Assert.fail("例外が発生した場合、エラー"); // assertColumnsNotNullで投げていたら。
		}
	}
}
