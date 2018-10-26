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
	private static final String STR_1001 = "01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
	private static final String STR_1025 = "01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234";
	private static final int INT_10 = 10;
	private static final int INT_100 = 100;
	private static final int INT_1000 = 1000;
	private static final int INT_100000 = 100000;
	private static final BigDecimal DECIMAL_10000000000000000000 = new BigDecimal("10000000000000000000.00");

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
		CustomerEstimation entity = customerEstimationRepository.findOne(401L);
		CustomerEstimation testTarget = new CustomerEstimation();
		BeanUtils.copyProperties(testTarget, entity);

		// 正常系
		testTarget.setVKjbMaster(null);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：momCustId companyId officeId departmentDiv
		// customerName phoneNumber）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setVKjbMaster(null);

		testTarget.setMomCustId(null);
		testTarget.setCompanyId(null);
		testTarget.setOfficeId(null);
		testTarget.setDepartmentDiv(null);
		testTarget.setCustomerName(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		//
		Assert.assertTrue(result.getErrorInfoList().size() == 5);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@NotEmptyの空文字列チェック：momCustId companyId officeId departmentDiv
		// customerName phoneNumber）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setVKjbMaster(null);

		testTarget.setMomCustId("");
		testTarget.setCompanyId("");
		testTarget.setOfficeId("");
		testTarget.setCustomerName("");
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 4);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@Size(max) ：momCustId companyId officeId customerName companyName
		// officeName departmentName postNumber address faxNumber）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setVKjbMaster(null);

		testTarget.setMomCustId(STR_256);
		testTarget.setCompanyId(STR_256);
		testTarget.setOfficeId(STR_256);
		testTarget.setCustomerName(STR_256);
		testTarget.setCompanyName(STR_256);
		testTarget.setOfficeName(STR_256);
		testTarget.setDepartmentName(STR_256);
		testTarget.setPostNumber(STR_256);
		testTarget.setAddress(STR_1001);
		testTarget.setPhoneNumber(STR_256);
		testTarget.setFaxNumber(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 11);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));

	}

	@Test
	public void DealerEstimationのテスト() throws Exception {
		DealerEstimation entity = dealerEstimationRepository.findOne(401L);
		DealerEstimation testTarget = new DealerEstimation();

		// 正常系
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setVKjbMaster(null);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setVKjbMaster(null);

		testTarget.setDealerFlowOrder(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@Size(max) ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setVKjbMaster(null);

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
	public void Estimationのテスト() throws Exception {
		Estimation entity = estimationRepository.findOne(4L);
		Estimation testTarget = new Estimation();

		// 正常系
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setCustomerEstimation(null);
		testTarget.setOperationLogList(null);
		testTarget.setEstimationAttachedFileList(null);
		testTarget.setEstimationAddedEditorEmpList(null);
		testTarget.setDealerEstimationList(null);
		testTarget.setEstimationCheckResultList(null);
		testTarget.setEstimationDetailList(null);
		testTarget.setProductEstimationList(null);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setEstimationPicSaEmp(null);
		testTarget.setCustomerEstimation(null);
		testTarget.setOperationLogList(null);
		testTarget.setEstimationAttachedFileList(null);
		testTarget.setEstimationAddedEditorEmpList(null);
		testTarget.setDealerEstimationList(null);
		testTarget.setEstimationCheckResultList(null);
		testTarget.setEstimationDetailList(null);
		testTarget.setProductEstimationList(null);

		testTarget.setLifecycleStatus(null);
		testTarget.setEstimationType(null);
		testTarget.setWorkflowStatus(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 4);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@Size(max) ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setCustomerEstimation(null);
		testTarget.setOperationLogList(null);
		testTarget.setEstimationAttachedFileList(null);
		testTarget.setEstimationAddedEditorEmpList(null);
		testTarget.setDealerEstimationList(null);
		testTarget.setEstimationCheckResultList(null);
		testTarget.setEstimationDetailList(null);
		testTarget.setProductEstimationList(null);

		testTarget.setImmutableContIdentNumber(STR_256);
		testTarget.setCaseNumber(STR_256);
		testTarget.setCaseTitle(STR_256);
		testTarget.setEstimationNumber(STR_256);
		testTarget.setEstimationTitle(STR_256);
		testTarget.setOriginContractNumber(STR_256);
		testTarget.setCommercialFlowDiv(STR_256);
		testTarget.setIssueFormat(STR_256);
		testTarget.setIssueEstimationTitle(STR_256);
		testTarget.setIssueCustomerCorpName(STR_256);
		testTarget.setCoverCompanyName(STR_256);
		testTarget.setCoverTitle(STR_256);
		testTarget.setCoverEstimationSubject(STR_256);
		testTarget.setCoverPaymentTerms(STR_256);
		testTarget.setCoverRemarks(STR_256);
		testTarget.setPublishCompany(STR_256);
		testTarget.setPublishDepartment(STR_256);
		testTarget.setPublishPostNumber(STR_256);
		testTarget.setPublishAddress(STR_1001);
		testTarget.setPublishFax(STR_256);
		testTarget.setPublishEmployee(STR_256);
		testTarget.setSpPriceApplyReason(STR_256);
		testTarget.setSpPriceApplyReasonText(STR_256);
		testTarget.setMainCompetitorName(STR_256);
		testTarget.setCompetitionInfo(STR_256);
		testTarget.setCompetitionContractDiv(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 26);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));

		// 異常系（@Max ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setCustomerEstimation(null);
		testTarget.setOperationLogList(null);
		testTarget.setEstimationAttachedFileList(null);
		testTarget.setEstimationAddedEditorEmpList(null);
		testTarget.setDealerEstimationList(null);
		testTarget.setEstimationCheckResultList(null);
		testTarget.setEstimationDetailList(null);
		testTarget.setProductEstimationList(null);

		testTarget.setEstimationBranchNumber(INT_100);
		testTarget.setOriginContractBranchNumber(INT_100);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));

	}

	@Test
	public void EstimationAddedEditorEmpのテスト() throws Exception {
		EstimationAddedEditorEmp entity = estimationAddedEditorEmpRepository.findOne(401L);
		EstimationAddedEditorEmp testTarget = new EstimationAddedEditorEmp();
		BeanUtils.copyProperties(testTarget, entity);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setMvEmployeeMaster(null);

		testTarget.setEmployeeName(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@NotEmptyの空文字列チェック：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setMvEmployeeMaster(null);

		testTarget.setEmployeeName("");
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@Size(max) ：）
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

		// 異常系（@Max ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setOrgHierarchyLevel(INT_10);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));

	}

	@Test
	public void EstimationApprovalResultのテスト() throws Exception {
		EstimationApprovalResult entity = estimationApprovalResultRepository.findOne(401L);
		EstimationApprovalResult testTarget = new EstimationApprovalResult();
		BeanUtils.copyProperties(testTarget, entity);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setProcessedAt(null);
		testTarget.setApprovalProcessCategory(null);
		testTarget.setActualEmpId(null);
		testTarget.setActualUserName(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 4);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@NotEmptyの空文字列チェック：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setActualEmpId("");
		testTarget.setActualUserName("");
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@Size(max) ：）
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
	public void EstimationApprovalRouteのテスト() throws Exception {
		EstimationApprovalRoute entity = estimationApprovalRouteRepository.findOne(401L);
		EstimationApprovalRoute testTarget = new EstimationApprovalRoute();

		// 正常系
		BeanUtils.copyProperties(testTarget, entity);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setEstimationApprovalRouteNodeList(null);
		testTarget.setApprovalRequesterEmpId(null);
		testTarget.setApprovalRequesterName(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 3);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@NotEmptyの空文字列チェック：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setApprovalRequesterEmpId("");
		testTarget.setApprovalRequesterName("");
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@Size(max) ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setApprovalRequesterEmpId(STR_256);
		testTarget.setApprovalRequesterName(STR_256);
		testTarget.setApprovalRequesterOrgName(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 3);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));

		// 異常系（@Max ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setSpecialPriceApprovalFlg(INT_10);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));

	}

	@Test
	public void EstimationApprovalRouteNodeのテスト() throws Exception {
		EstimationApprovalRouteNode entity = estimationApprovalRouteNodeRepository.findOne(401L);
		EstimationApprovalRouteNode testTarget = new EstimationApprovalRouteNode();

		// 正常系
		BeanUtils.copyProperties(testTarget, entity);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setApproverEmpId(null);
		testTarget.setApproverName(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		// 異常系（@NotEmptyの空文字列チェック：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setApproverEmpId("");
		testTarget.setApproverName("");
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		// 異常系（@Size(max) ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setApproverEmpId(STR_256);
		testTarget.setApproverName(STR_256);
		testTarget.setApproverOrgName(STR_256);
		testTarget.setSubApproverEmpId(STR_256);
		testTarget.setSubApproverName(STR_256);
		testTarget.setSubApproverOrgName(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 6);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		// 異常系（@Max ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setApprovalOrder(INT_1000);
		testTarget.setApproverOrgLevel(INT_10);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));
	}

	@Test
	public void EstimationAttachedFileのテスト() throws Exception {

		EstimationAttachedFile entity = attachedFileRepository.findOne(401L);
		EstimationAttachedFile testTarget = new EstimationAttachedFile();
		BeanUtils.copyProperties(testTarget, entity);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：id、attachedAt、attachedFilePath
		// ※idはプリミティブ型で試験実施できない）
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

		// 異常系（@Size(max) ：fileKind attachedFilePath attachedEmpId attachedEmpName）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setFileKind(STR_256);
		testTarget.setAttachedFilePath(STR_1025);
		testTarget.setAttachedEmpId(STR_256);
		testTarget.setAttachedEmpName(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 4);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));

	}

	@Test
	public void EstimationCheckResultのテスト() throws Exception {
		EstimationCheckResult entity = estimationCheckResultRepository.findOne(401L);
		EstimationCheckResult testTarget = new EstimationCheckResult();

		// 正常系
		BeanUtils.copyProperties(testTarget, entity);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setTargetLifecycleStatus(null);
		testTarget.setCheckMatterCode(null);
		testTarget.setCheckMatterText(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 3);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@NotEmptyの空文字列チェック：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setCheckMatterCode("");
		testTarget.setCheckMatterText("");
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@Size(max) ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setCheckMatterCode(STR_256);
		testTarget.setCheckMatterText(STR_256);
		testTarget.setCheckedUserId(STR_256);
		testTarget.setCheckedUserName(STR_256);
		testTarget.setCheckedOrgName(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 5);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));

		// 異常系（@Max ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setDisplayOrder(INT_1000);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));

	}

	@Test
	public void EstimationDetailのテスト() throws Exception {
		EstimationDetail entity = estimationDetailRepository.findOne(401L);
		EstimationDetail testTarget = new EstimationDetail();
		BeanUtils.copyProperties(testTarget, entity);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setState(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		// 異常系（@Size(max) ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setDetailAbstract(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		// 異常系（@Max ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setQuantity(INT_100000);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));
		// 異常系（@DecimalMax：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setAmountSummary(DECIMAL_10000000000000000000);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));
	}

	@Test
	public void EstimationPicSaEmpのテスト() throws Exception {
		EstimationPicSaEmp entity = estimationPicSaEmpRepository.findOne(401L);
		EstimationPicSaEmp testTarget = new EstimationPicSaEmp();

		// 正常系
		BeanUtils.copyProperties(testTarget, entity);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setMvEmployeeMaster(null);

		testTarget.setEmployeeName(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		// 異常系（@NotEmptyの空文字列チェック：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setMvEmployeeMaster(null);

		testTarget.setEmployeeName("");
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		// 異常系（@Size(max) ：）
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
		// 異常系（@Max ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setOrgHierarchyLevel(INT_10);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));
	}

	@Test
	public void ItemEstimationのテスト() throws Exception {
		ItemEstimation entity = itemEstimationRepository.findOne(401L);
		ItemEstimation testTarget = new ItemEstimation();

		// 正常系
		BeanUtils.copyProperties(testTarget, entity);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setItemType(null);
		testTarget.setCostType(null);
		testTarget.setPartitionPrice(null);
		testTarget.setEffectiveFrom(null);
		testTarget.setItemEstimationName(null);
		testTarget.setRicohItemCode(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 6);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		// 異常系（@NotEmptyの空文字列チェック：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setItemEstimationName("");
		testTarget.setRicohItemCode("");
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		// 異常系（@Size(max) ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setItemEstimationName(STR_256);
		testTarget.setRicohItemCode(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		// 異常系（@DecimalMax：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setPartitionPrice(DECIMAL_10000000000000000000);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));

	}

	@Test
	public void OperationLogのテスト() throws Exception {
		OperationLog entity = operationLogRepository.findOne(401L);
		OperationLog testTarget = new OperationLog();

		// 正常系
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setEstimation(null);

		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);
		// 異常系（@NotNull、@NotEmptyの null チェック：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setOperatedAt(null);
		testTarget.setOperatorEmpId(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		// 異常系（@NotEmptyの空文字列チェック：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setOperatorEmpId("");
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		// 異常系（@Size(max) ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setOperatorName(STR_256);
		testTarget.setOperatorOrgName(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
	}

	@Test
	public void ProductEstimationのテスト() throws Exception {
		ProductEstimation entity = productEstimationRepository.findOne(401L);
		ProductEstimation testTarget = new ProductEstimation();

		// 正常系
		BeanUtils.copyProperties(testTarget, entity);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);
		// 異常系（@NotNull、@NotEmptyの null チェック：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setProductEstimationName(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		// 異常系（@Size(max) ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setProductEstimationName(STR_256);
		testTarget.setServiceIdentNumber(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
	}

}
