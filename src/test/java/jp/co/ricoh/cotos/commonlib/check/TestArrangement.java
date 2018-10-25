package jp.co.ricoh.cotos.commonlib.check;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.AfterClass;
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
import jp.co.ricoh.cotos.commonlib.entity.arrangement.Arrangement;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementPicWorkerEmp;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWork;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWorkApprovalResult;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWorkApprovalRoute;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWorkApprovalRouteNode;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWorkAttachedFile;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWorkCheckResult;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWorkOperationLog;
import jp.co.ricoh.cotos.commonlib.repository.arrangement.ArrangementPicWorkerEmpRepository;
import jp.co.ricoh.cotos.commonlib.repository.arrangement.ArrangementRepository;
import jp.co.ricoh.cotos.commonlib.repository.arrangement.ArrangementWorkApprovalResultRepository;
import jp.co.ricoh.cotos.commonlib.repository.arrangement.ArrangementWorkApprovalRouteNodeRepository;
import jp.co.ricoh.cotos.commonlib.repository.arrangement.ArrangementWorkApprovalRouteRepository;
import jp.co.ricoh.cotos.commonlib.repository.arrangement.ArrangementWorkAttachedFileRepository;
import jp.co.ricoh.cotos.commonlib.repository.arrangement.ArrangementWorkCheckResultRepository;
import jp.co.ricoh.cotos.commonlib.repository.arrangement.ArrangementWorkOperationLogRepository;
import jp.co.ricoh.cotos.commonlib.repository.arrangement.ArrangementWorkRepository;
import jp.co.ricoh.cotos.commonlib.security.TestSecurityController;
import jp.co.ricoh.cotos.commonlib.security.bean.ParamterCheckResult;
import jp.co.ricoh.cotos.commonlib.util.HeadersProperties;

/**
 * パラメータチェック（手配ドメイン）のテストクラス
 *
 * @author kentaro.kakuhana
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestArrangement {

	private static final String STR_256 = "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345";
	private static final String STR_1025 = "01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234";

	static ConfigurableApplicationContext context;

	@Autowired
	HeadersProperties headersProperties;

	@Autowired
	ArrangementPicWorkerEmpRepository arrangementPicWorkerEmpRepository;

	@Autowired
	ArrangementRepository arrangementRepository;

	@Autowired
	ArrangementWorkApprovalResultRepository arrangementWorkApprovalResultRepository;

	@Autowired
	ArrangementWorkApprovalRouteNodeRepository arrangementWorkApprovalRouteNodeRepository;

	@Autowired
	ArrangementWorkApprovalRouteRepository arrangementWorkApprovalRouteRepository;

	@Autowired
	ArrangementWorkAttachedFileRepository arrangementWorkAttachedFileRepository;

	@Autowired
	ArrangementWorkCheckResultRepository arrangementWorkCheckResultRepository;

	@Autowired
	ArrangementWorkOperationLogRepository arrangementWorkOperationLogRepository;

	@Autowired
	ArrangementWorkRepository arrangementWorkRepository;

	@Autowired
	TestTools testTool;

	@Autowired
	public void injectContext(ConfigurableApplicationContext injectContext) {
		context = injectContext;
		context.getBean(DBConfig.class).clearData();
		context.getBean(DBConfig.class).initTargetTestData("repository/contract.sql");
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
	public void ArrangementPicWorkerEmpのテスト() throws Exception {
		ArrangementPicWorkerEmp entity = arrangementPicWorkerEmpRepository.findOne(null);
		ArrangementPicWorkerEmp testTarget = new ArrangementPicWorkerEmp();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		// 異常系（@NotEmptyの空文字列チェック：）
		// 異常系（@Size(max) ：）
		// 異常系（@Max ：）
		// 異常系（@DecimalMax：）
	}

	@Test
	public void Arrangementのテスト() throws Exception {
		Arrangement entity = arrangementRepository.findOne(null);
		Arrangement testTarget = new Arrangement();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		// 異常系（@NotEmptyの空文字列チェック：）
		// 異常系（@Size(max) ：）
		// 異常系（@Max ：）
		// 異常系（@DecimalMax：）
	}

	@Test
	public void ArrangementWorkApprovalResultのテスト() throws Exception {
		ArrangementWorkApprovalResult entity = arrangementWorkApprovalResultRepository.findOne(null);
		ArrangementWorkApprovalResult testTarget = new ArrangementWorkApprovalResult();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		// 異常系（@NotEmptyの空文字列チェック：）
		// 異常系（@Size(max) ：）
		// 異常系（@Max ：）
		// 異常系（@DecimalMax：）
	}

	@Test
	public void ArrangementWorkApprovalRouteNodeのテスト() throws Exception {
		ArrangementWorkApprovalRouteNode entity = arrangementWorkApprovalRouteNodeRepository.findOne(null);
		ArrangementWorkApprovalRouteNode testTarget = new ArrangementWorkApprovalRouteNode();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		// 異常系（@NotEmptyの空文字列チェック：）
		// 異常系（@Size(max) ：）
		// 異常系（@Max ：）
		// 異常系（@DecimalMax：）
	}

	@Test
	public void ArrangementWorkApprovalRouteのテスト() throws Exception {
		ArrangementWorkApprovalRoute entity = arrangementWorkApprovalRouteRepository.findOne(null);
		ArrangementWorkApprovalRoute testTarget = new ArrangementWorkApprovalRoute();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		// 異常系（@NotEmptyの空文字列チェック：）
		// 異常系（@Size(max) ：）
		// 異常系（@Max ：）
		// 異常系（@DecimalMax：）
	}

	@Test
	public void ArrangementWorkAttachedFileのテスト() throws Exception {
		ArrangementWorkAttachedFile entity = arrangementWorkAttachedFileRepository.findOne(null);
		ArrangementWorkAttachedFile testTarget = new ArrangementWorkAttachedFile();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		// 異常系（@NotEmptyの空文字列チェック：）
		// 異常系（@Size(max) ：）
		// 異常系（@Max ：）
		// 異常系（@DecimalMax：）
	}

	@Test
	public void ArrangementWorkCheckResultのテスト() throws Exception {
		ArrangementWorkCheckResult entity = arrangementWorkCheckResultRepository.findOne(null);
		ArrangementWorkCheckResult testTarget = new ArrangementWorkCheckResult();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		// 異常系（@NotEmptyの空文字列チェック：）
		// 異常系（@Size(max) ：）
		// 異常系（@Max ：）
		// 異常系（@DecimalMax：）
	}

	@Test
	public void ArrangementWorkOperationLogのテスト() throws Exception {
		ArrangementWorkOperationLog entity = arrangementWorkOperationLogRepository.findOne(null);
		ArrangementWorkOperationLog testTarget = new ArrangementWorkOperationLog();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		// 異常系（@NotEmptyの空文字列チェック：）
		// 異常系（@Size(max) ：）
		// 異常系（@Max ：）
		// 異常系（@DecimalMax：）
	}

	@Test
	public void ArrangementWorkのテスト() throws Exception {
		ArrangementWork entity = arrangementWorkRepository.findOne(null);
		ArrangementWork testTarget = new ArrangementWork();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		// 異常系（@NotEmptyの空文字列チェック：）
		// 異常系（@Size(max) ：）
		// 異常系（@Max ：）
		// 異常系（@DecimalMax：）
	}

}
