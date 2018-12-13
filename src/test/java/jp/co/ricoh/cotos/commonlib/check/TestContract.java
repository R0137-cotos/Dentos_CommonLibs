package jp.co.ricoh.cotos.commonlib.check;

import java.math.BigDecimal;

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
import jp.co.ricoh.cotos.commonlib.security.bean.ParamterCheckResult;
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
	private static final String STR_1001 = "01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";

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
		context.getBean(DBConfig.class).initTargetTestData("repository/attachedFile.sql");
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
		ContractAddedEditorEmp entity = contractAddedEditorEmpRepository.findOne(401L);
		ContractAddedEditorEmp testTarget = new ContractAddedEditorEmp();

		// 正常系
		BeanUtils.copyProperties(testTarget, entity);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：momEmployeeId employeeName）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setMomEmployeeId(null);
		testTarget.setEmployeeName(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@NotEmptyの空文字列チェック：momEmployeeId employeeName）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setMomEmployeeId("");
		testTarget.setEmployeeName("");
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@Size(max) ：momOrgId orgName salesCompanyName orgPhoneNumber employeeName
		// salesDepartmentName postNumber phoneNumber faxNumber mailAddress）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setMomOrgId(STR_256);
		testTarget.setOrgName(STR_256);
		testTarget.setSalesCompanyName(STR_256);
		testTarget.setOrgPhoneNumber(STR_256);
		testTarget.setEmployeeName(STR_256);
		testTarget.setSalesDepartmentName(STR_256);
		testTarget.setPostNumber(STR_256);
		testTarget.setPhoneNumber(STR_256);
		testTarget.setFaxNumber(STR_256);
		testTarget.setMailAddress(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 10);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));

		// 異常系（@Max ：orgHierarchyLevel ）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setOrgHierarchyLevel(10);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));

	}

	@Test
	public void ContractApprovalResultのテスト() throws Exception {
		ContractApprovalResult entity = contractApprovalResultRepository.findOne(401L);
		ContractApprovalResult testTarget = new ContractApprovalResult();
		BeanUtils.copyProperties(testTarget, entity);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：processedAt approvalProcessCategory
		// actualEmpId actualUserName）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setProcessedAt(null);
		testTarget.setApprovalProcessCategory(null);
		testTarget.setActualEmpId(null);
		testTarget.setActualUserName(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 4);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@NotEmptyの空文字列チェック：actualEmpId actualUserName）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setActualEmpId("");
		testTarget.setActualUserName("");
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@Size(max) ：actualEmpId actualUserName actualOrgName requestComment）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setActualEmpId(STR_256);
		testTarget.setActualUserName(STR_256);
		testTarget.setActualOrgName(STR_256);
		testTarget.setRequestComment(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 4);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));

	}

	@Test
	public void ContractApprovalRouteNodeのテスト() throws Exception {
		ContractApprovalRouteNode entity = contractApprovalRouteNodeRepository.findOne(401L);
		ContractApprovalRouteNode testTarget = new ContractApprovalRouteNode();
		BeanUtils.copyProperties(testTarget, entity);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：approverEmpId approverName）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setApproverEmpId(null);
		testTarget.setApproverName(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@NotEmptyの空文字列チェック：approverEmpId approverName）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setApproverEmpId("");
		testTarget.setApproverName("");
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@Size(max) ：approverEmpId approverName approverOrgName subApproverName
		// subApproverOrgName）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setApproverEmpId(STR_256);
		testTarget.setApproverName(STR_256);
		testTarget.setApproverOrgName(STR_256);
		testTarget.setSubApproverName(STR_256);
		testTarget.setSubApproverOrgName(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 5);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));

		// 異常系（@Max ：approvalOrder approverOrgLevel）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setApprovalOrder(1000);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));

	}

	@Test
	public void ContractApprovalRouteのテスト() throws Exception {
		ContractApprovalRoute entity = contractApprovalRouteRepository.findOne(401L);
		ContractApprovalRoute testTarget = new ContractApprovalRoute();
		BeanUtils.copyProperties(testTarget, entity);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：contractApprovalRouteNodeList
		// targetLifecycleStatus）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setTargetLifecycleStatus(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@Size(max) ：approvalRequesterEmpId approvalRequesterName
		// approvalRequesterOrgName）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setApprovalRequesterEmpId(STR_256);
		testTarget.setApprovalRequesterName(STR_256);
		testTarget.setApprovalRequesterOrgName(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 3);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));

		// 異常系（@Max ：specialPriceApprovalFlg）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setSpecialPriceApprovalFlg(10);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));

	}

	@Test
	public void ContractAttachedFileのテスト() throws Exception {
		ContractAttachedFile entity = contractAttachedFileRepository.findOne(401L);
		ContractAttachedFile testTarget = new ContractAttachedFile();
		BeanUtils.copyProperties(testTarget, entity);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：attachedAt attachedFilePath attachedEmpId
		// attachedEmpName）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setFileName(null);
		testTarget.setAttachedFile(null);
		testTarget.setAttachedAt(null);
		testTarget.setAttachedEmpId(null);
		testTarget.setAttachedEmpName(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 5);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@NotEmptyの空文字列チェック：fileName attachedEmpId attachedEmpName）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setFileName("");
		testTarget.setAttachedEmpId("");
		testTarget.setAttachedEmpName("");
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 3);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@Size(max) ：fileKind attachedFilePath attachedComment attachedEmpId
		// attachedEmpName attachedOrgName）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setFileName(STR_256);
		testTarget.setFileKind(STR_256);
		testTarget.setAttachedComment(STR_1001);
		testTarget.setAttachedEmpId(STR_256);
		testTarget.setAttachedEmpName(STR_256);
		testTarget.setAttachedOrgName(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 6);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));

	}

	@Test
	public void ContractCheckResultのテスト() throws Exception {
		ContractCheckResult entity = contractCheckResultRepository.findOne(401L);
		ContractCheckResult testTarget = new ContractCheckResult();
		BeanUtils.copyProperties(testTarget, entity);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：targetLifecycleStatus checkMatterCode
		// checkMatterText）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setTargetLifecycleStatus(null);
		testTarget.setCheckMatterCode(null);
		testTarget.setCheckMatterText(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 3);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@NotEmptyの空文字列チェック：checkMatterCode checkMatterText）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setCheckMatterCode("");
		testTarget.setCheckMatterText("");
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@Size(max) ：checkMatterCode checkMatterText checkedUserName
		// checkedOrgName checkedAt）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setCheckMatterCode(STR_256);
		testTarget.setCheckMatterText(STR_256);
		testTarget.setCheckedUserName(STR_256);
		testTarget.setCheckedOrgName(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 4);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));

		// 異常系（@Max ：displayOrder）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setDisplayOrder(1000);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));

	}

	@Test
	public void ContractDetailのテスト() throws Exception {
		ContractDetail entity = contractDetailRepository.findOne(401L);
		ContractDetail testTarget = new ContractDetail();
		BeanUtils.copyProperties(testTarget, entity);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：amountSummary state）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setAmountSummary(null);
		testTarget.setState(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@Size(max) ：detailAbstract）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setDetailAbstract(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));

		// 異常系（@Max ：quantity）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setQuantity(100000);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));

		// 異常系（@DecimalMax：amountSummary）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setAmountSummary(BigDecimal.valueOf(10000000000000000000.99));
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));

	}

	@Test
	public void ContractOperationLogのテスト() throws Exception {
		ContractOperationLog entity = contractOperationLogRepository.findOne(401L);
		ContractOperationLog testTarget = new ContractOperationLog();
		BeanUtils.copyProperties(testTarget, entity);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：operatedAt operation operatorEmpId
		// operatorName）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setOperatedAt(null);
		testTarget.setOperation(null);
		testTarget.setOperatorEmpId(null);
		testTarget.setOperatorName(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 4);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@NotEmptyの空文字列チェック：operatorEmpId operatorName）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setOperatorEmpId("");
		testTarget.setOperatorName("");
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@Size(max) ：operatorEmpId operatorName operatorOrgName）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setOperatorEmpId(STR_256);
		testTarget.setOperatorName(STR_256);
		testTarget.setOperatorOrgName(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 3);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));

	}

	@Test
	public void ContractPicSaEmpのテスト() throws Exception {
		ContractPicSaEmp entity = contractPicSaEmpRepository.findOne(401L);
		ContractPicSaEmp testTarget = new ContractPicSaEmp();
		BeanUtils.copyProperties(testTarget, entity);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：momEmployeeId employeeName）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setMomEmployeeId(null);
		testTarget.setEmployeeName(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@NotEmptyの空文字列チェック：momEmployeeId employeeName）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setMomEmployeeId("");
		testTarget.setEmployeeName("");
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@Size(max) ：momOrgId orgName salesCompanyName orgPhoneNumber employeeName
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setMomOrgId(STR_256);
		testTarget.setOrgName(STR_256);
		testTarget.setSalesCompanyName(STR_256);
		testTarget.setOrgPhoneNumber(STR_256);
		testTarget.setEmployeeName(STR_256);
		testTarget.setSalesDepartmentName(STR_256);
		testTarget.setPostNumber(STR_256);
		testTarget.setAddress(STR_1001);
		testTarget.setPhoneNumber(STR_256);
		testTarget.setFaxNumber(STR_256);
		testTarget.setMailAddress(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 11);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));

		// 異常系（@Max ：orgHierarchyLevel）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setOrgHierarchyLevel(10);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));

	}

	@Test
	public void Contractのテスト() throws Exception {
		Contract entity = contractRepository.findOne(4L);
		Contract testTarget = new Contract();
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setContractAddedEditorEmpList(null);
		testTarget.setDealerContractList(null);
		testTarget.setCustomerContract(null);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：contractDetailList contractPicSaEmp
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setContractPicSaEmp(null);
		testTarget.setContractAddedEditorEmpList(null);
		testTarget.setDealerContractList(null);
		testTarget.setCustomerContract(null);

		testTarget.setContractDetailList(null);
		testTarget.setContractOperationLogList(null);
		testTarget.setProductContractList(null);
		testTarget.setLifecycleStatus(null);
		testTarget.setWorkflowStatus(null);
		testTarget.setContractNumber(null);
		testTarget.setEstimationNumber(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 3);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@NotEmptyの空文字列チェック：contractNumber estimationNumber）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setContractPicSaEmp(null);
		testTarget.setContractAddedEditorEmpList(null);
		testTarget.setDealerContractList(null);
		testTarget.setCustomerContract(null);

		testTarget.setContractNumber("");
		testTarget.setEstimationNumber("");
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@Size(max) ：immutableContIdentNumber caseNumber caseTitle contractNumber
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setContractAddedEditorEmpList(null);
		testTarget.setDealerContractList(null);
		testTarget.setCustomerContract(null);
		testTarget.setImmutableContIdentNumber(STR_256);
		testTarget.setCaseNumber(STR_256);
		testTarget.setCaseTitle(STR_256);
		testTarget.setContractNumber(STR_256);
		testTarget.setOriginContractNumber(STR_256);
		testTarget.setEstimationNumber(STR_256);
		testTarget.setEstimationTitle(STR_256);
		testTarget.setCommercialFlowDiv(STR_256);
		testTarget.setIssueFormat(STR_256);
		testTarget.setBillingCustomerSpCode(STR_256);
		testTarget.setBillingCustomerSpName(STR_256);
		testTarget.setPaymentTerms(STR_256);
		testTarget.setPaymentMethod(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 13);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));

		// 異常系（@Max ：contractBranchNumber accountSalesFlg estimationBranchNumber）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setContractAddedEditorEmpList(null);
		testTarget.setDealerContractList(null);
		testTarget.setCustomerContract(null);

		testTarget.setContractBranchNumber(100);
		testTarget.setAccountSalesFlg(10);
		testTarget.setEstimationBranchNumber(100);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 3);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));

	}

	@Test
	public void CustomerContractのテスト() throws Exception {
		CustomerContract entity = customerContractRepository.findOne(401L);
		CustomerContract testTarget = new CustomerContract();

		// 正常系
		BeanUtils.copyProperties(testTarget, entity);

		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：momKjbSystemId momCustId companyId officeId
		// departmentDiv customerName）
		BeanUtils.copyProperties(testTarget, entity);

		testTarget.setMomCustId(null);
		testTarget.setCompanyId(null);
		testTarget.setOfficeId(null);
		testTarget.setDepartmentDiv(null);
		testTarget.setCustomerName(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 5);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@NotEmptyの空文字列チェック：momCustId companyId officeId customerName）
		BeanUtils.copyProperties(testTarget, entity);

		testTarget.setMomCustId("");
		testTarget.setCompanyId("");
		testTarget.setOfficeId("");
		testTarget.setCustomerName("");
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 4);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@Size(max) ：momCustId companyId officeId customerName companyName
		// companyNameKana officeName departmentName postNumber address phoneNumber
		// faxNumber companyRepresentativeName companyRepresentativeNameKana picName
		// picNameKana picDeptName picPhoneNumber picFaxNumber picMailAddress）
		BeanUtils.copyProperties(testTarget, entity);

		testTarget.setMomCustId(STR_256);
		testTarget.setCompanyId(STR_256);
		testTarget.setOfficeId(STR_256);
		testTarget.setCustomerName(STR_256);
		testTarget.setCompanyName(STR_256);
		testTarget.setCompanyNameKana(STR_256);
		testTarget.setOfficeName(STR_256);
		testTarget.setDepartmentName(STR_256);
		testTarget.setPostNumber(STR_256);
		testTarget.setAddress(STR_1001);
		testTarget.setPhoneNumber(STR_256);
		testTarget.setFaxNumber(STR_256);
		testTarget.setCompanyRepresentativeName(STR_256);
		testTarget.setCompanyRepresentativeNameKana(STR_256);
		testTarget.setPicName(STR_256);
		testTarget.setPicNameKana(STR_256);
		testTarget.setPicDeptName(STR_256);
		testTarget.setPicPhoneNumber(STR_256);
		testTarget.setPicFaxNumber(STR_256);
		testTarget.setPicMailAddress(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 20);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));

	}

	@Test
	public void DealerContractのテスト() throws Exception {
		DealerContract entity = dealerContractRepository.findOne(401L);
		DealerContract testTarget = new DealerContract();

		// 正常系
		BeanUtils.copyProperties(testTarget, entity);

		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：dealerFlowOrder）
		BeanUtils.copyProperties(testTarget, entity);

		testTarget.setDealerFlowOrder(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@Size(max) ：dealerName postNumber address orgPhoneNumber picName
		// picDeptName picPhoneNumber picFaxNumber）
		BeanUtils.copyProperties(testTarget, entity);

		testTarget.setDealerName(STR_256);
		testTarget.setPostNumber(STR_256);
		testTarget.setAddress(STR_1001);
		testTarget.setOrgPhoneNumber(STR_256);
		testTarget.setPicName(STR_256);
		testTarget.setPicDeptName(STR_256);
		testTarget.setPicPhoneNumber(STR_256);
		testTarget.setPicFaxNumber(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 8);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));

	}

	@Test
	public void ItemContractのテスト() throws Exception {
		ItemContract entity = itemContractRepository.findOne(401L);
		ItemContract testTarget = new ItemContract();
		BeanUtils.copyProperties(testTarget, entity);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：partitionPrice effectiveFrom effectiveTo
		// itemContractName ricohItemCode itemType costType)
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setPartitionPrice(null);
		testTarget.setItemContractName(null);
		testTarget.setRicohItemCode(null);
		testTarget.setItemType(null);
		testTarget.setCostType(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 5);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@NotEmptyの空文字列チェック：itemContractName）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setItemContractName("");
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@Size(max) ：itemContractName ricohItemCode ）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setItemContractName(STR_256);
		testTarget.setRicohItemCode(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));

		// 異常系（@DecimalMax：partitionPrice）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setPartitionPrice(BigDecimal.valueOf(10000000000000000000.99));
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));

	}

	@Test
	public void ProductContractのテスト() throws Exception {
		ProductContract entity = productContractRepository.findOne(401L);
		ProductContract testTarget = new ProductContract();
		BeanUtils.copyProperties(testTarget, entity);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：effectiveFrom effectiveTo
		// productContractName serviceIdentNumber）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setProductContractName(null);
		testTarget.setServiceIdentNumber(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@NotEmptyの空文字列チェック：productContractName serviceIdentNumber）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setProductContractName("");
		testTarget.setServiceIdentNumber("");
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@Size(max) ：productContractName serviceIdentNumber）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setProductContractName(STR_256);
		testTarget.setServiceIdentNumber(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));

	}

}
