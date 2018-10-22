package jp.co.ricoh.cotos.commonlib.repository;

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
import jp.co.ricoh.cotos.commonlib.entity.estimation.CustomerEstimation;
import jp.co.ricoh.cotos.commonlib.entity.estimation.DealerEstimation;
import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationAddedEditorEmp;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationApprovalResult;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationApprovalRoute;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationApprovalRouteNode;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationAttachedFile;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationCheckResult;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationDetail;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationPicSaEmp;
import jp.co.ricoh.cotos.commonlib.entity.estimation.ItemEstimation;
import jp.co.ricoh.cotos.commonlib.entity.estimation.OperationLog;
import jp.co.ricoh.cotos.commonlib.entity.estimation.ProductEstimation;
import jp.co.ricoh.cotos.commonlib.repository.estimation.AttachedFileRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.CustomerEstimationRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.DealerEstimationRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.EstimationAddedEditorEmpRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.EstimationApprovalResultRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.EstimationApprovalRouteNodeRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.EstimationApprovalRouteRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.EstimationCheckResultRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.EstimationDetailRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.EstimationPicSaEmpRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.EstimationRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.ItemEstimationRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.OperationLogRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.ProductEstimationRepository;

/**
 * Repository（見積ドメイン）のテストクラス
 *
 * @author kentaro.kakuhana
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestEstimation {

	static ConfigurableApplicationContext context;

	@Autowired
	TestTools testTool;

	@Autowired
	AttachedFileRepository attachedFileRepository;

	@Autowired
	OperationLogRepository operationLogRepository;

	@Autowired
	EstimationAddedEditorEmpRepository estimationAddedEditorEmpRepository;

	@Autowired
	DealerEstimationRepository dealerEstimationRepository;

	@Autowired
	EstimationCheckResultRepository estimationCheckResultRepository;

	@Autowired
	EstimationDetailRepository estimationDetailRepository;

	@Autowired
	ProductEstimationRepository productEstimationRepository;

	@Autowired
	EstimationApprovalResultRepository estimationApprovalResultRepository;

	@Autowired
	EstimationApprovalRouteNodeRepository estimationApprovalRouteNodeRepository;

	@Autowired
	CustomerEstimationRepository customerEstimationRepository;

	@Autowired
	EstimationPicSaEmpRepository estimationPicSaEmpRepository;

	@Autowired
	EstimationApprovalRouteRepository estimationApprovalRouteRepository;

	@Autowired
	ItemEstimationRepository itemEstimationRepository;

	@Autowired
	EstimationRepository estimationRepository;

	@Autowired
	public void injectContext(ConfigurableApplicationContext injectContext) {
		context = injectContext;
		context.getBean(DBConfig.class).clearData();
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/estimation_all.sql");

	}

	@AfterClass
	public static void stopAPServer() throws InterruptedException {
		if (null != context) {
			context.getBean(DBConfig.class).clearData();
			context.stop();
		}
	}

	@Test
	public void AttachedFileRepositoryのテスト() throws Exception {

		EstimationAttachedFile found = attachedFileRepository.findOne(401L);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);
	}

	@Test
	public void OperationLogRepositoryのテスト() throws Exception {

		OperationLog found = operationLogRepository.findOne(401L);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);
	}

	@Test
	public void EstimationAddedEditorEmpRepositoryのテスト() throws Exception {

		EstimationAddedEditorEmp found = estimationAddedEditorEmpRepository.findOne(401L);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);
	}

	@Test
	public void DealerEstimationRepositoryのテスト() throws Exception {

		DealerEstimation found = dealerEstimationRepository.findOne(402L);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

	}

	@Test
	public void EstimationCheckResultRepositoryのテスト() throws Exception {

		EstimationCheckResult found = estimationCheckResultRepository.findOne(401L);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

	}

	@Test
	public void EstimationDetailRepositoryのテスト() throws Exception {

		EstimationDetail found = estimationDetailRepository.findOne(401L);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

	}

	@Test
	public void ProductEstimationRepositoryのテスト() throws Exception {

		ProductEstimation found = productEstimationRepository.findOne(401L);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

	}

	@Test
	public void EstimationApprovalResultRepositoryのテスト() throws Exception {

		EstimationApprovalResult found = estimationApprovalResultRepository.findOne(401L);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

	}

	@Test
	public void EstimationApprovalRouteNodeRepositoryのテスト() throws Exception {

		EstimationApprovalRouteNode found = estimationApprovalRouteNodeRepository.findOne(401L);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

	}

	@Test
	public void CustomerEstimationRepositoryのテスト() throws Exception {

		CustomerEstimation found = customerEstimationRepository.findOne(401L);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

	}

	@Test
	public void EstimationPicSaEmpRepositoryのテスト() throws Exception {

		EstimationPicSaEmp found = estimationPicSaEmpRepository.findOne(401L);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

	}

	@Test
	public void EstimationApprovalRouteRepositoryのテスト() throws Exception {

		EstimationApprovalRoute found = estimationApprovalRouteRepository.findOne(401L);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

	}

	@Test
	public void ItemEstimationRepositoryのテスト() throws Exception {

		ItemEstimation found = itemEstimationRepository.findOne(401L);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

	}

	@Test
	public void EstimationRepositoryのテスト() throws Exception {

		Estimation found = estimationRepository.findOne(4L);

		// Entity が null ではないことを確認
		Assert.assertNotNull(found);

		// Entity の各項目の値が null ではないことを確認
		testTool.assertColumnsNotNull(found);

	}

}
