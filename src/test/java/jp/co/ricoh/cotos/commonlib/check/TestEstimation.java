package jp.co.ricoh.cotos.commonlib.check;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.ricoh.cotos.commonlib.DBConfig;
import jp.co.ricoh.cotos.commonlib.TestTools;
import jp.co.ricoh.cotos.commonlib.TestTools.ParameterErrorIds;
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
import jp.co.ricoh.cotos.commonlib.security.TestSecurityController;
import jp.co.ricoh.cotos.commonlib.security.bean.ParamterCheckResult;
import jp.co.ricoh.cotos.commonlib.util.HeadersProperties;

/**
 * パラメータチェック（見積ドメイン）のテストクラス
 *
 * @author kentaro.kakuhana
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestEstimation {

	private static final String STR_256 = "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345";
	private static final String STR_1025 = "01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234";

	static ConfigurableApplicationContext context;

	@Autowired
	HeadersProperties headersProperties;

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
	TestTools testTool;

	@Autowired
	public void injectContext(ConfigurableApplicationContext injectContext) {
		context = injectContext;
		context.getBean(DBConfig.class).clearData();
		context.getBean(DBConfig.class).initTargetTestData("repository/estimation/estimation_all.sql");
	}

	@Autowired
	TestSecurityController testSecurityController;

	@LocalServerPort
	private int localServerPort;

	@AfterClass
	public static void stopAPServer() throws InterruptedException {
		if (null != context) {
			context.getBean(DBConfig.class).clearData();
			context.stop();
		}
	}

	@Test
	public void CustomerEstimationのテスト() throws Exception {
		CustomerEstimation entity = customerEstimationRepository.findOne(null);
		CustomerEstimation testTarget = new CustomerEstimation();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		// 異常系（@NotEmptyの空文字列チェック：）
		// 異常系（@Size(max) ：）
		// 異常系（@Max       ：）
		// 異常系（@DecimalMax：）
	}

	@Test
	public void DealerEstimationのテスト() throws Exception {
		DealerEstimation entity = dealerEstimationRepository.findOne(null);
		DealerEstimation testTarget = new DealerEstimation();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		// 異常系（@NotEmptyの空文字列チェック：）
		// 異常系（@Size(max) ：）
		// 異常系（@Max       ：）
		// 異常系（@DecimalMax：）

	}

	@Test
	public void Estimationのテスト() throws Exception {
		Estimation entity = estimationRepository.findOne(null);
		Estimation testTarget = new Estimation();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		// 異常系（@NotEmptyの空文字列チェック：）
		// 異常系（@Size(max) ：）
		// 異常系（@Max       ：）
		// 異常系（@DecimalMax：）

	}

	@Test
	public void EstimationAddedEditorEmpのテスト() throws Exception {
		EstimationAddedEditorEmp entity = estimationAddedEditorEmpRepository.findOne(null);
		EstimationAddedEditorEmp testTarget = new EstimationAddedEditorEmp();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		// 異常系（@NotEmptyの空文字列チェック：）
		// 異常系（@Size(max) ：）
		// 異常系（@Max       ：）
		// 異常系（@DecimalMax：）

	}

	@Test
	public void EstimationApprovalResultのテスト() throws Exception {
		EstimationApprovalResult entity = estimationApprovalResultRepository.findOne(null);
		EstimationApprovalResult testTarget = new EstimationApprovalResult();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		// 異常系（@NotEmptyの空文字列チェック：）
		// 異常系（@Size(max) ：）
		// 異常系（@Max       ：）
		// 異常系（@DecimalMax：）

	}

	@Test
	public void EstimationApprovalRouteのテスト() throws Exception {
		EstimationApprovalRoute entity = estimationApprovalRouteRepository.findOne(null);
		EstimationApprovalRoute testTarget = new EstimationApprovalRoute();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		// 異常系（@NotEmptyの空文字列チェック：）
		// 異常系（@Size(max) ：）
		// 異常系（@Max       ：）
		// 異常系（@DecimalMax：）

	}

	@Test
	public void EstimationApprovalRouteNodeのテスト() throws Exception {
		EstimationApprovalRouteNode entity = estimationApprovalRouteNodeRepository.findOne(null);
		EstimationApprovalRouteNode testTarget = new EstimationApprovalRouteNode();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		// 異常系（@NotEmptyの空文字列チェック：）
		// 異常系（@Size(max) ：）
		// 異常系（@Max       ：）
		// 異常系（@DecimalMax：）

	}

	@Test
	public void EstimationAttachedFileのテスト() throws Exception {

		EstimationAttachedFile entity = attachedFileRepository.findOne(401L);
		EstimationAttachedFile testTarget = new EstimationAttachedFile();
		BeanUtils.copyProperties(testTarget, entity);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：id、attachedAt、attachedFilePath　※idはプリミティブ型で試験実施できない）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setAttachedAt(null);
		testTarget.setAttachedFilePath(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@NotEmptyの空文字列チェック：attachedFilePath）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setAttachedFilePath("");
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@Size(max) ：fileKind　attachedFilePath　attachedEmpId　attachedEmpName）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setFileKind(STR_256);
		testTarget.setAttachedFilePath(STR_1025);
		testTarget.setAttachedEmpId(STR_256);
		testTarget.setAttachedEmpName(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 4);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));

		// 異常系（@Max       ：id　※ long 型の最大値と同じ。試験実施できない）
		// 異常系（@DecimalMax：なし）
	}

	@Test
	public void EstimationCheckResultのテスト() throws Exception {
		EstimationCheckResult entity = estimationCheckResultRepository.findOne(null);
		EstimationCheckResult testTarget = new EstimationCheckResult();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		// 異常系（@NotEmptyの空文字列チェック：）
		// 異常系（@Size(max) ：）
		// 異常系（@Max       ：）
		// 異常系（@DecimalMax：）

	}

	@Test
	public void EstimationDetailのテスト() throws Exception {
		EstimationDetail entity = estimationDetailRepository.findOne(null);
		EstimationDetail testTarget = new EstimationDetail();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		// 異常系（@NotEmptyの空文字列チェック：）
		// 異常系（@Size(max) ：）
		// 異常系（@Max       ：）
		// 異常系（@DecimalMax：）

	}

	@Test
	public void EstimationPicSaEmpのテスト() throws Exception {
		EstimationPicSaEmp entity = estimationPicSaEmpRepository.findOne(null);
		EstimationPicSaEmp testTarget = new EstimationPicSaEmp();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		// 異常系（@NotEmptyの空文字列チェック：）
		// 異常系（@Size(max) ：）
		// 異常系（@Max       ：）
		// 異常系（@DecimalMax：）

	}

	@Test
	public void ItemEstimationのテスト() throws Exception {
		ItemEstimation entity = itemEstimationRepository.findOne(null);
		ItemEstimation testTarget = new ItemEstimation();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		// 異常系（@NotEmptyの空文字列チェック：）
		// 異常系（@Size(max) ：）
		// 異常系（@Max       ：）
		// 異常系（@DecimalMax：）

	}

	@Test
	public void OperationLogのテスト() throws Exception {
		OperationLog entity = operationLogRepository.findOne(null);
		OperationLog testTarget = new OperationLog();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		// 異常系（@NotEmptyの空文字列チェック：）
		// 異常系（@Size(max) ：）
		// 異常系（@Max       ：）
		// 異常系（@DecimalMax：）

	}

	@Test
	public void ProductEstimationのテスト() throws Exception {
		ProductEstimation entity = productEstimationRepository.findOne(null);
		ProductEstimation testTarget = new ProductEstimation();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		// 異常系（@NotEmptyの空文字列チェック：）
		// 異常系（@Size(max) ：）
		// 異常系（@Max       ：）
		// 異常系（@DecimalMax：）
	}

}
