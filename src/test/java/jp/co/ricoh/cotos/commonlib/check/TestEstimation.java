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
import jp.co.ricoh.cotos.commonlib.repository.estimation.CustomerEstimationRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.DealerEstimationRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.EstimationAddedEditorEmpRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.EstimationApprovalResultRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.EstimationApprovalRouteNodeRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.EstimationApprovalRouteRepository;
import jp.co.ricoh.cotos.commonlib.repository.estimation.EstimationAttachedFileRepository;
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
	private static final int INT_MINUS_1 = -1;
	private static final int INT_10 = 10;
	private static final int INT_100 = 100;
	private static final int INT_1000 = 1000;
	private static final int INT_100000 = 100000;
	private static final BigDecimal DECIMAL_MINUS_001 = new BigDecimal("-0.01");
	private static final BigDecimal DECIMAL_0001 = new BigDecimal("0.001");
	private static final BigDecimal DECIMAL_10000000000000000000 = new BigDecimal("10000000000000000000.00");

	static ConfigurableApplicationContext context;

	@Autowired
	HeadersProperties headersProperties;

	@Autowired
	EstimationAttachedFileRepository estimationAttachedFileRepository;

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
		context.getBean(DBConfig.class).initTargetTestData("repository/attachedFile.sql");
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
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNullの null チェック）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setMomKjbSystemId(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "MoM企事部システム連携IDが設定されていません。"));

		// 異常系（@Size(max)）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setMomKjbSystemId(STR_256);
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
		testTarget.setPicName(STR_256);
		testTarget.setPicNameKana(STR_256);
		testTarget.setPicDeptName(STR_256);
		testTarget.setPicPhoneNumber(STR_256);
		testTarget.setPicFaxNumber(STR_256);
		testTarget.setPicMailAddress(STR_256);
		testTarget.setCompanyRepresentativeNameKana(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 21);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "MoM企事部システム連携IDは最大文字数（255）を超えています。"));

	}

	@Test
	public void DealerEstimationのテスト() throws Exception {
		DealerEstimation entity = dealerEstimationRepository.findOne(401L);
		DealerEstimation testTarget = new DealerEstimation();

		// 正常系
		BeanUtils.copyProperties(testTarget, entity);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNullの null チェック：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setDealerFlowOrder(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "販売店商流順が設定されていません。"));

		// 異常系（@Size(max) ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setMomKjbSystemId(STR_256);
		testTarget.setDealerName(STR_256);
		testTarget.setPostNumber(STR_256);
		testTarget.setAddress(STR_1001);
		testTarget.setOrgPhoneNumber(STR_256);
		testTarget.setPicName(STR_256);
		testTarget.setPicDeptName(STR_256);
		testTarget.setPicPhoneNumber(STR_256);
		testTarget.setPicFaxNumber(STR_256);
		testTarget.setPicMailAddress(STR_256);
		testTarget.setPicNameKana(STR_256);
		testTarget.setDealerNameKana(STR_256);
		testTarget.setCompanyRepresentativeName(STR_256);
		testTarget.setCompanyRepresentativeNameKana(STR_256);
		testTarget.setMomCustId(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 15);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "MoM非連携_担当者メールアドレスは最大文字数（255）を超えています。"));

	}

	@Test
	public void Estimationのテスト() throws Exception {
		Estimation entity = estimationRepository.findOne(4L);
		Estimation testTarget = new Estimation();

		// 正常系
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setEstimationAttachedFileList(null);
		testTarget.setEstimationAddedEditorEmpList(null);
		testTarget.setDealerEstimationList(null);
		testTarget.setEstimationDetailList(null);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@Size(max) ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setCaseNumber(STR_256);
		testTarget.setCaseTitle(STR_256);
		testTarget.setEstimationTitle(STR_256);
		testTarget.setEstimatedSystemDiv(STR_256);
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
		testTarget.setPublishTel(STR_256);
		testTarget.setPublishFax(STR_256);
		testTarget.setPublishEmployee(STR_256);
		testTarget.setSpPriceApplyReason(STR_256);
		testTarget.setSpPriceApplyReasonText(STR_256);
		testTarget.setMainCompetitorName(STR_256);
		testTarget.setCompetitionInfo(STR_256);
		testTarget.setCompetitionContractDiv(STR_256);
		testTarget.setAppId(STR_256);
		testTarget.setIssueTaxCodeValue(STR_256);
		testTarget.setEstimationWorkId(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 29);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "見積発行元FAX番号は最大文字数（255）を超えています。"));

		// 異常系（@Max ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setOriginContractBranchNumber(INT_100);
		testTarget.setVupLinkageFlg(INT_10);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "変更元文書番号枝番は最大値（99）を超えています。"));

		// 異常系（@Min ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setProductGrpMasterId(INT_MINUS_1);
		testTarget.setOriginContractBranchNumber(INT_MINUS_1);
		testTarget.setOriginContractId((long) INT_MINUS_1);
		testTarget.setVupLinkageFlg(INT_MINUS_1);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 4);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00027));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "商品グループマスタIDは最小値（0）を下回っています。"));

		// 異常系（@DecimalMin ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setCompetitionAmount(DECIMAL_MINUS_001);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00027));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "競合先基本料金は最小値（0.00）を下回っています。"));

		// 異常系（@Digits ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setCompetitionAmount(DECIMAL_0001);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00028));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "競合先基本料金は小数点以下2桁を超えています。"));

		// 異常系（@Valid ：顧客（見積用））
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.getCustomerEstimation().setMomKjbSystemId(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "MoM企事部システム連携IDが設定されていません。"));

		// 異常系（@Valid：見積添付ファイル）
		entity = estimationRepository.findOne(4L);
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.getEstimationAttachedFileList().get(0).setFileName(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "ファイル名が設定されていません。"));

		// 異常系（@Valid：見積担当SA社員）
		entity = estimationRepository.findOne(4L);
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.getEstimationPicSaEmp().setMomEmployeeId(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "MoM社員IDが設定されていません。"));

		// 異常系（@Valid ：見積明細）
		entity = estimationRepository.findOne(4L);
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.getEstimationDetailList().get(0).setState(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "状態が設定されていません。"));

		// 異常系（@Valid ：販売店（見積用））
		entity = estimationRepository.findOne(4L);
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.getDealerEstimationList().get(0).setPicMailAddress(STR_256);
		;
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "MoM非連携_担当者メールアドレスは最大文字数（255）を超えています。"));

		// 異常系（@Valid ：見積追加編集者社員）
		entity = estimationRepository.findOne(4L);
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.getEstimationAddedEditorEmpList().get(0).setMomEmployeeId(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "MoM社員IDが設定されていません。"));

	}

	@Test
	public void EstimationAddedEditorEmpのテスト() throws Exception {
		EstimationAddedEditorEmp entity = estimationAddedEditorEmpRepository.findOne(401L);
		EstimationAddedEditorEmp testTarget = new EstimationAddedEditorEmp();
		BeanUtils.copyProperties(testTarget, entity);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNullの null チェック：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setMomEmployeeId(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "MoM社員IDが設定されていません。"));

		// 異常系（@Size(max) ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setMomEmployeeId(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "MoM社員IDは最大文字数（255）を超えています。"));

	}

	@Test
	public void EstimationApprovalResultのテスト() throws Exception {
		EstimationApprovalResult entity = estimationApprovalResultRepository.findOne(401L);
		EstimationApprovalResult testTarget = new EstimationApprovalResult();
		BeanUtils.copyProperties(testTarget, entity);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNullの null チェック：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setApprovalProcessCategory(null);
		testTarget.setActualEmpId(null);
		testTarget.setActualUserName(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 3);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "処理実施者氏名が設定されていません。"));

		// 異常系（@Size(max) ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setActualEmpId(STR_256);
		testTarget.setActualUserName(STR_256);
		testTarget.setActualOrgName(STR_256);
		testTarget.setRequestComment(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 4);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "コメントは最大文字数（255）を超えています。"));

	}

	@Test
	public void EstimationApprovalRouteのテスト() throws Exception {
		EstimationApprovalRoute entity = estimationApprovalRouteRepository.findOne(401L);
		EstimationApprovalRoute testTarget = new EstimationApprovalRoute();

		// 正常系
		BeanUtils.copyProperties(testTarget, entity);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@Size(max) ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setApprovalRequesterEmpId(STR_256);
		testTarget.setApprovalRequesterName(STR_256);
		testTarget.setApprovalRequesterOrgName(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 3);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "承認依頼者組織名は最大文字数（255）を超えています。"));

		// 異常系（@Max ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setSpecialPriceApprovalFlg(INT_10);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "特価承認対象フラグは最大値（9）を超えています。"));

		// 異常系（@Min ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setSpecialPriceApprovalFlg(INT_MINUS_1);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00027));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "特価承認対象フラグは最小値（0）を下回っています。"));

	}

	@Test
	public void EstimationApprovalRouteNodeのテスト() throws Exception {
		EstimationApprovalRouteNode entity = estimationApprovalRouteNodeRepository.findOne(401L);
		EstimationApprovalRouteNode testTarget = new EstimationApprovalRouteNode();

		// 正常系
		BeanUtils.copyProperties(testTarget, entity);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNullの null チェック：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setApproverEmpId(null);
		testTarget.setApproverName(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "承認者氏名が設定されていません。"));

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
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "代理承認者組織名は最大文字数（255）を超えています。"));

		// 異常系（@Max ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setApprovalOrder(INT_1000);
		testTarget.setApproverOrgLevel(INT_10);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "承認順は最大値（999）を超えています。"));

		// 異常系（@Min ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setApprovalOrder(INT_MINUS_1);
		testTarget.setApproverOrgLevel(INT_MINUS_1);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00027));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "承認者組織階層レベルは最小値（0）を下回っています。"));
	}

	@Test
	public void EstimationAttachedFileのテスト() throws Exception {

		EstimationAttachedFile entity = estimationAttachedFileRepository.findOne(401L);
		EstimationAttachedFile testTarget = new EstimationAttachedFile();
		BeanUtils.copyProperties(testTarget, entity);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNullの null チェック：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setFileName(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "ファイル名が設定されていません。"));

		// 異常系（@Size(max) ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setFileName(STR_256);
		testTarget.setFileKind(STR_256);
		testTarget.setAttachedComment(STR_1001);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 3);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "コメントは最大文字数（1000）を超えています。"));

		// 異常系（@Valid ：添付ファイル）
		entity = estimationAttachedFileRepository.findOne(401L);
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.getAttachedFile().setFilePhysicsName(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "物理ファイル名が設定されていません。"));

	}

	@Test
	public void EstimationCheckResultのテスト() throws Exception {
		EstimationCheckResult entity = estimationCheckResultRepository.findOne(401L);
		EstimationCheckResult testTarget = new EstimationCheckResult();

		// 正常系
		BeanUtils.copyProperties(testTarget, entity);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNullの null チェック：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setTargetLifecycleStatus(null);
		testTarget.setCheckMatterCode(null);
		testTarget.setCheckMatterText(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 3);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "チェック事項文面が設定されていません。"));

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
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "チェック事項文面は最大文字数（255）を超えています。"));

		// 異常系（@Max ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setDisplayOrder(INT_1000);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "表示順は最大値（999）を超えています。"));

		// 異常系（@Min ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setDisplayOrder(INT_MINUS_1);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00027));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "表示順は最小値（0）を下回っています。"));

	}

	@Test
	public void EstimationDetailのテスト() throws Exception {
		EstimationDetail entity = estimationDetailRepository.findOne(401L);
		EstimationDetail testTarget = new EstimationDetail();
		BeanUtils.copyProperties(testTarget, entity);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNullの null チェック：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setState(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "状態が設定されていません。"));

		// 異常系（@Size(max) ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setDetailAbstract(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "摘要は最大文字数（255）を超えています。"));

		// 異常系（@Max ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setQuantity(INT_100000);
		testTarget.setBeforeQuantity(INT_100000);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "数量は最大値（99999）を超えています。"));

		// 異常系（@Min ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setQuantity(INT_MINUS_1);
		testTarget.setBeforeQuantity(INT_MINUS_1);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00027));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "数量は最小値（0）を下回っています。"));

		// 異常系（@DecimalMax：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setEstimationAmountSummary(DECIMAL_10000000000000000000);
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

		// 異常系（@NotNullの null チェック：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setMomEmployeeId(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "MoM社員IDが設定されていません。"));

		// 異常系（@Size(max) ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setMomEmployeeId(STR_256);
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
		testTarget.setMomKjbSystemId(STR_256);
		testTarget.setMomCustId(STR_256);
		testTarget.setSalesCompanyNameKana(STR_256);
		testTarget.setCompanyRepresentativeName(STR_256);
		testTarget.setCompanyRepresentativeNameKana(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 17);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "MoM社員IDは最大文字数（255）を超えています。"));
	}

	@Test
	public void ItemEstimationのテスト() throws Exception {
		ItemEstimation entity = itemEstimationRepository.findOne(401L);
		ItemEstimation testTarget = new ItemEstimation();

		// 正常系
		BeanUtils.copyProperties(testTarget, entity);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNullの null チェック：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setItemType(null);
		testTarget.setCostType(null);
		testTarget.setPartitionPrice(null);
		testTarget.setItemEstimationName(null);
		testTarget.setRicohItemCode(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 5);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "品種名が設定されていません。"));

		// 異常系（@Min ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setItemMasterId(INT_MINUS_1);
		testTarget.setProductMasterId(INT_MINUS_1);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00027));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "商品マスタIDは最小値（0）を下回っています。"));

		// 異常系（@DecimalMin：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setPartitionPrice(DECIMAL_MINUS_001);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00027));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "仕切価格は最小値（0.00）を下回っています。"));

		// 異常系（@Digits：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setPartitionPrice(DECIMAL_0001);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00028));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "仕切価格は小数点以下2桁を超えています。"));

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
		// 異常系（@NotNullのnull チェック：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setOperatorEmpId(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "操作者MoM社員IDが設定されていません。"));

		// 異常系（@Size(max) ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setOperatorName(STR_256);
		testTarget.setOperatorOrgName(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "操作者組織名は最大文字数（255）を超えています。"));
	}

	@Test
	public void ProductEstimationのテスト() throws Exception {
		ProductEstimation entity = productEstimationRepository.findOne(401L);
		ProductEstimation testTarget = new ProductEstimation();

		// 正常系
		BeanUtils.copyProperties(testTarget, entity);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNullの null チェック：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setProductEstimationName(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "商品名が設定されていません。"));

		// 異常系（@Size(max) ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setProductEstimationName(STR_256);
		testTarget.setServiceIdentNumber(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "商品名は最大文字数（255）を超えています。"));

		// 異常系（@Size(max) ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setProductMasterId(INT_MINUS_1);
		testTarget.setRepItemMasterId(new Long(INT_MINUS_1));
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00027));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "商品マスタIDは最小値（0）を下回っています。"));
	}

}
