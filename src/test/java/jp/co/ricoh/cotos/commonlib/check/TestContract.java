package jp.co.ricoh.cotos.commonlib.check;

import java.util.List;

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
import jp.co.ricoh.cotos.commonlib.entity.contract.Contract;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractAddedEditorEmp;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractApprovalResult;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractApprovalRoute;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractApprovalRouteNode;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractAttachedFile;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractCheckResult;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractDetail;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractOperationLog;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractPicSaEmp;
import jp.co.ricoh.cotos.commonlib.entity.contract.CustomerContract;
import jp.co.ricoh.cotos.commonlib.entity.contract.DealerContract;
import jp.co.ricoh.cotos.commonlib.entity.contract.ItemContract;
import jp.co.ricoh.cotos.commonlib.entity.contract.ProductContract;
import jp.co.ricoh.cotos.commonlib.exception.ErrorInfo;
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
import jp.co.ricoh.cotos.commonlib.security.TestSecurityController;
import jp.co.ricoh.cotos.commonlib.util.HeadersProperties;

/**
 * パラメータチェック（契約ドメイン）のテストクラス
 *
 * @author kentaro.kakuhana
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestContract {

	private static final String STR_256 = "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345";
	private static final String STR_1025 = "01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234";

	static ConfigurableApplicationContext context;

	@Autowired
	HeadersProperties headersProperties;

	@Autowired
	ContractAddedEditorEmpRepository contractAddedEditorEmpRepository;

	@Autowired
	ContractApprovalResultRepository contractApprovalResultRepository;

	@Autowired
	ContractApprovalRouteNodeRepository contractApprovalRouteNodeRepository;

	@Autowired
	ContractApprovalRouteRepository contractApprovalRouteRepository;

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
	ContractRepository contractRepository;

	@Autowired
	CustomerContractRepository customerContractRepository;

	@Autowired
	DealerContractRepository dealerContractRepository;

	@Autowired
	ItemContractRepository itemContractRepository;

	@Autowired
	ProductContractRepository productContractRepository;

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
	public void ContractAddedEditorEmpのテスト() throws Exception {
		ContractAddedEditorEmp entity = contractAddedEditorEmpRepository.findOne(null);
		ContractAddedEditorEmp testTarget = new ContractAddedEditorEmp();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		List<ErrorInfo> result = testTool.executeEntityValidation(testTarget, testSecurityController, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		// 異常系（@NotEmptyの空文字列チェック：）
		// 異常系（@Size(max) ：）
		// 異常系（@Max ：）
		// 異常系（@DecimalMax：）
	}

	@Test
	public void ContractApprovalResultのテスト() throws Exception {
		ContractApprovalResult entity = contractApprovalResultRepository.findOne(null);
		ContractApprovalResult testTarget = new ContractApprovalResult();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		List<ErrorInfo> result = testTool.executeEntityValidation(testTarget, testSecurityController, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		// 異常系（@NotEmptyの空文字列チェック：）
		// 異常系（@Size(max) ：）
		// 異常系（@Max ：）
		// 異常系（@DecimalMax：）
	}

	@Test
	public void ContractApprovalRouteNodeのテスト() throws Exception {
		ContractApprovalRouteNode entity = contractApprovalRouteNodeRepository.findOne(null);
		ContractApprovalRouteNode testTarget = new ContractApprovalRouteNode();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		List<ErrorInfo> result = testTool.executeEntityValidation(testTarget, testSecurityController, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		// 異常系（@NotEmptyの空文字列チェック：）
		// 異常系（@Size(max) ：）
		// 異常系（@Max ：）
		// 異常系（@DecimalMax：）
	}

	@Test
	public void ContractApprovalRouteのテスト() throws Exception {
		ContractApprovalRoute entity = contractApprovalRouteRepository.findOne(null);
		ContractApprovalRoute testTarget = new ContractApprovalRoute();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		List<ErrorInfo> result = testTool.executeEntityValidation(testTarget, testSecurityController, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		// 異常系（@NotEmptyの空文字列チェック：）
		// 異常系（@Size(max) ：）
		// 異常系（@Max ：）
		// 異常系（@DecimalMax：）
	}

	@Test
	public void ContractAttachedFileのテスト() throws Exception {
		ContractAttachedFile entity = contractAttachedFileRepository.findOne(null);
		ContractAttachedFile testTarget = new ContractAttachedFile();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		List<ErrorInfo> result = testTool.executeEntityValidation(testTarget, testSecurityController, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		// 異常系（@NotEmptyの空文字列チェック：）
		// 異常系（@Size(max) ：）
		// 異常系（@Max ：）
		// 異常系（@DecimalMax：）
	}

	@Test
	public void ContractCheckResultのテスト() throws Exception {
		ContractCheckResult entity = contractCheckResultRepository.findOne(null);
		ContractCheckResult testTarget = new ContractCheckResult();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		List<ErrorInfo> result = testTool.executeEntityValidation(testTarget, testSecurityController, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		// 異常系（@NotEmptyの空文字列チェック：）
		// 異常系（@Size(max) ：）
		// 異常系（@Max ：）
		// 異常系（@DecimalMax：）
	}

	@Test
	public void ContractDetailのテスト() throws Exception {
		ContractDetail entity = contractDetailRepository.findOne(null);
		ContractDetail testTarget = new ContractDetail();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		List<ErrorInfo> result = testTool.executeEntityValidation(testTarget, testSecurityController, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		// 異常系（@NotEmptyの空文字列チェック：）
		// 異常系（@Size(max) ：）
		// 異常系（@Max ：）
		// 異常系（@DecimalMax：）
	}

	@Test
	public void ContractOperationLogのテスト() throws Exception {
		ContractOperationLog entity = contractOperationLogRepository.findOne(null);
		ContractOperationLog testTarget = new ContractOperationLog();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		List<ErrorInfo> result = testTool.executeEntityValidation(testTarget, testSecurityController, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		// 異常系（@NotEmptyの空文字列チェック：）
		// 異常系（@Size(max) ：）
		// 異常系（@Max ：）
		// 異常系（@DecimalMax：）
	}

	@Test
	public void ContractPicSaEmpのテスト() throws Exception {
		ContractPicSaEmp entity = contractPicSaEmpRepository.findOne(null);
		ContractPicSaEmp testTarget = new ContractPicSaEmp();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		List<ErrorInfo> result = testTool.executeEntityValidation(testTarget, testSecurityController, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		// 異常系（@NotEmptyの空文字列チェック：）
		// 異常系（@Size(max) ：）
		// 異常系（@Max ：）
		// 異常系（@DecimalMax：）
	}

	@Test
	public void Contractのテスト() throws Exception {
		Contract entity = contractRepository.findOne(null);
		Contract testTarget = new Contract();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		List<ErrorInfo> result = testTool.executeEntityValidation(testTarget, testSecurityController, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		// 異常系（@NotEmptyの空文字列チェック：）
		// 異常系（@Size(max) ：）
		// 異常系（@Max ：）
		// 異常系（@DecimalMax：）
	}

	@Test
	public void CustomerContractのテスト() throws Exception {
		CustomerContract entity = customerContractRepository.findOne(null);
		CustomerContract testTarget = new CustomerContract();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		List<ErrorInfo> result = testTool.executeEntityValidation(testTarget, testSecurityController, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		// 異常系（@NotEmptyの空文字列チェック：）
		// 異常系（@Size(max) ：）
		// 異常系（@Max ：）
		// 異常系（@DecimalMax：）
	}

	@Test
	public void DealerContractのテスト() throws Exception {
		DealerContract entity = dealerContractRepository.findOne(null);
		DealerContract testTarget = new DealerContract();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		List<ErrorInfo> result = testTool.executeEntityValidation(testTarget, testSecurityController, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		// 異常系（@NotEmptyの空文字列チェック：）
		// 異常系（@Size(max) ：）
		// 異常系（@Max ：）
		// 異常系（@DecimalMax：）
	}

	@Test
	public void ItemContractのテスト() throws Exception {
		ItemContract entity = itemContractRepository.findOne(null);
		ItemContract testTarget = new ItemContract();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		List<ErrorInfo> result = testTool.executeEntityValidation(testTarget, testSecurityController, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		// 異常系（@NotEmptyの空文字列チェック：）
		// 異常系（@Size(max) ：）
		// 異常系（@Max ：）
		// 異常系（@DecimalMax：）
	}

	@Test
	public void ProductContractのテスト() throws Exception {
		ProductContract entity = productContractRepository.findOne(null);
		ProductContract testTarget = new ProductContract();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		List<ErrorInfo> result = testTool.executeEntityValidation(testTarget, testSecurityController, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		// 異常系（@NotEmptyの空文字列チェック：）
		// 異常系（@Size(max) ：）
		// 異常系（@Max ：）
		// 異常系（@DecimalMax：）
	}

}
