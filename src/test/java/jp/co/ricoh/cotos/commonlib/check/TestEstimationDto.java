package jp.co.ricoh.cotos.commonlib.check;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.ricoh.cotos.commonlib.DBConfig;
import jp.co.ricoh.cotos.commonlib.TestTools;
import jp.co.ricoh.cotos.commonlib.TestTools.ParameterErrorIds;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.AttachedFileDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.estimation.CustomerEstimationDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.estimation.DealerEstimationDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.estimation.EstimationAddedEditorEmpDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.estimation.EstimationApprovalRouteDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.estimation.EstimationApprovalRouteNodeDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.estimation.EstimationAttachedFileDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.estimation.EstimationCancelParameter;
import jp.co.ricoh.cotos.commonlib.dto.parameter.estimation.EstimationCheckResultDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.estimation.EstimationDetailDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.estimation.EstimationDetailRegisterParameter;
import jp.co.ricoh.cotos.commonlib.dto.parameter.estimation.EstimationDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.estimation.EstimationPicSaEmpDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.estimation.EstimationRegisterParameter;
import jp.co.ricoh.cotos.commonlib.dto.parameter.estimation.ItemEstimationDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.estimation.ProductEstimationDto;
import jp.co.ricoh.cotos.commonlib.entity.estimation.CustomerEstimation;
import jp.co.ricoh.cotos.commonlib.entity.estimation.DealerEstimation;
import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationAddedEditorEmp;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationApprovalRoute;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationApprovalRouteNode;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationAttachedFile;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationCheckResult;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationDetail;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationPicSaEmp;
import jp.co.ricoh.cotos.commonlib.entity.estimation.ItemEstimation;
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
public class TestEstimationDto {

	private static final String STR_256 = "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345";
	private static final String STR_1001 = "01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
	private static final int INT_MINUS_1 = -1;
	private static final Long LONG_MINUS_1 = -1L;
	private static final int INT_10 = 10;
	private static final int INT_100 = 100;
	private static final int INT_1000 = 1000;
	private static final int INT_100000 = 100000;
	private static final BigDecimal DECIMAL_MINUS_001 = new BigDecimal("-0.01");
	private static final BigDecimal DECIMAL_0001 = new BigDecimal("0.001");

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
	public void CustomerEstimationDtoのテスト() throws Exception {
		CustomerEstimation entity = customerEstimationRepository.findOne(401L);
		CustomerEstimationDto testTarget = new CustomerEstimationDto();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setMomKjbSystemId(null);
		testTarget.setMomCustId(null);
		testTarget.setCompanyId(null);
		testTarget.setOfficeId(null);
		testTarget.setDepartmentDiv(null);
		testTarget.setCustomerName(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 6);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "MoM企事部システム連携IDが設定されていません。"));

		// 異常系（@Size(max))
		BeanUtils.copyProperties(entity, testTarget);
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
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "住所は最大文字数（1000）を超えています。"));
	}

	@Test
	public void DealerEstimationDtoのテスト() throws Exception {
		DealerEstimation entity = dealerEstimationRepository.findOne(401L);
		DealerEstimationDto testTarget = new DealerEstimationDto();

		// 正常系
		BeanUtils.copyProperties(entity, testTarget);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setDealerFlowOrder(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "販売店商流順が設定されていません。"));

		// 異常系（@Size(max) ：）
		BeanUtils.copyProperties(entity, testTarget);
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
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "住所は最大文字数（1000）を超えています。"));
	}

	@Test
	public void EstimationDtoのテスト() throws Exception {
		Estimation entity = estimationRepository.findOne(4L);
		EstimationDto dto = new EstimationDto();
		EstimationDto testTarget = new EstimationDto();

		BeanUtils.copyProperties(entity, dto);

		// 見積明細
		EstimationDetailDto detail = new EstimationDetailDto();
		BeanUtils.copyProperties(entity.getEstimationDetailList().get(0), detail);
		ItemEstimationDto item = new ItemEstimationDto();
		BeanUtils.copyProperties(entity.getEstimationDetailList().get(0).getItemEstimation(), item);
		detail.setItemEstimation(item);
		dto.setEstimationDetailList(Arrays.asList(detail));

		// 見積担当SA社員
		EstimationPicSaEmpDto sa = new EstimationPicSaEmpDto();
		BeanUtils.copyProperties(entity.getEstimationPicSaEmp(), sa);
		dto.setEstimationPicSaEmp(sa);

		// 顧客（見積用）
		CustomerEstimationDto customer = new CustomerEstimationDto();
		BeanUtils.copyProperties(entity.getCustomerEstimation(), customer);
		dto.setCustomerEstimation(customer);

		// 販売店（見積用）
		DealerEstimationDto dealer = new DealerEstimationDto();
		BeanUtils.copyProperties(entity.getDealerEstimationList().get(0), dealer);
		dto.setDealerEstimationList(Arrays.asList(dealer));

		// 見積追加編集者社員
		EstimationAddedEditorEmpDto added = new EstimationAddedEditorEmpDto();
		BeanUtils.copyProperties(entity.getEstimationAddedEditorEmpList().get(0), added);
		dto.setEstimationAddedEditorEmpList(Arrays.asList(added));

		// 見積添付ファイル
		EstimationAttachedFileDto esAttached = new EstimationAttachedFileDto();
		BeanUtils.copyProperties(entity.getEstimationAttachedFileList().get(0), esAttached);
		AttachedFileDto attached = new AttachedFileDto();
		BeanUtils.copyProperties(entity.getEstimationAttachedFileList().get(0).getAttachedFile(), attached);
		esAttached.setAttachedFile(attached);
		dto.setEstimationAttachedFileList(Arrays.asList(esAttached));

		// 商品（見積用）
		ProductEstimationDto product = new ProductEstimationDto();
		BeanUtils.copyProperties(entity.getProductEstimationList().get(0), product);
		dto.setProductEstimationList(Arrays.asList(product));

		// 見積承認ルート
		EstimationApprovalRouteDto route = new EstimationApprovalRouteDto();
		BeanUtils.copyProperties(entity.getEstimationApprovalRoute(), route);
		dto.setEstimationApprovalRoute(route);

		// 見積チェック結果
		EstimationCheckResultDto check = new EstimationCheckResultDto();
		BeanUtils.copyProperties(entity.getEstimationCheckResultList().get(0), check);
		dto.setEstimationCheckResultList(Arrays.asList(check));

		// 正常系
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.setEstimationApprovalRoute(null);
		testTarget.setEstimationAttachedFileList(null);
		testTarget.setEstimationAddedEditorEmpList(null);
		testTarget.setDealerEstimationList(null);
		testTarget.setEstimationCheckResultList(null);
		testTarget.setEstimationDetailList(null);
		testTarget.setProductEstimationList(null);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNullの null チェック：）
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.setLifecycleStatus(null);
		testTarget.setEstimationType(null);
		testTarget.setWorkflowStatus(null);
		testTarget.setEstimationNumber(null);
		testTarget.setEstimationPicSaEmp(null);
		testTarget.setCustomerEstimation(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 6);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "見積担当SA社員が設定されていません。"));

		// 異常系（@Size(max) ：）
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.setImmutableContIdentNumber(STR_256);
		testTarget.setCaseNumber(STR_256);
		testTarget.setCaseTitle(STR_256);
		testTarget.setEstimationNumber(STR_256);
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
		testTarget.setIssueTaxCodeValue(STR_256);
		testTarget.setEstimationWorkId(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 30);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "見積発行元FAX番号は最大文字数（255）を超えています。"));

		// 異常系（@Max ：）
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.setEstimationBranchNumber(INT_100);
		testTarget.setOriginContractBranchNumber(INT_100);
		testTarget.setVupLinkageFlg(INT_10);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 3);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "変更元契約番号枝番は最大値（99）を超えています。"));

		// 異常系（@Min ：）
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.setProductGrpMasterId(INT_MINUS_1);
		testTarget.setEstimationBranchNumber(INT_MINUS_1);
		testTarget.setOriginContractBranchNumber(INT_MINUS_1);
		testTarget.setOriginContractId((long) INT_MINUS_1);
		testTarget.setVupLinkageFlg(INT_MINUS_1);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 5);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00027));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "商品グループマスタIDは最小値（0）を下回っています。"));

		// 異常系（@DecimalMin ：）
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.setCompetitionAmount(DECIMAL_MINUS_001);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00027));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "競合先基本料金は最小値（0.00）を下回っています。"));

		// 異常系（@Digits ：）
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.setCompetitionAmount(DECIMAL_0001);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00028));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "競合先基本料金は小数点以下2桁を超えています。"));

		// 異常系（@Valid：）
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.getEstimationDetailList().get(0).setDetailAbstract(STR_256);
		testTarget.getEstimationPicSaEmp().setPostNumber(STR_256);
		testTarget.getCustomerEstimation().setCustomerName(STR_256);
		testTarget.getDealerEstimationList().get(0).setDealerName(STR_256);
		testTarget.getEstimationAddedEditorEmpList().get(0).setOrgName(STR_256);
		testTarget.getEstimationAttachedFileList().get(0).setAttachedOrgName(STR_256);
		testTarget.getProductEstimationList().get(0).setProductEstimationName(STR_256);
		testTarget.getEstimationApprovalRoute().setApprovalRequesterName(STR_256);
		testTarget.getEstimationCheckResultList().get(0).setCheckedUserName(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 9);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "商品名は最大文字数（255）を超えています。"));
	}

	@Test
	public void EstimationAddedEditorEmpDtoのテスト() throws Exception {
		EstimationAddedEditorEmp entity = estimationAddedEditorEmpRepository.findOne(401L);
		EstimationAddedEditorEmpDto testTarget = new EstimationAddedEditorEmpDto();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		BeanUtils.copyProperties(entity, testTarget);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setMomEmployeeId(null);
		testTarget.setEmployeeName(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "MoM社員IDが設定されていません。"));

		// 異常系（@Size(max) ：）
		BeanUtils.copyProperties(entity, testTarget);
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
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 12);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "MoM社員IDは最大文字数（255）を超えています。"));

		// 異常系（@Min ：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setOrgHierarchyLevel(INT_MINUS_1);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00027));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "所属組織階層レベルは最小値（0）を下回っています。"));

		// 異常系（@Max ：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setOrgHierarchyLevel(INT_10);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "所属組織階層レベルは最大値（9）を超えています。"));
	}

	@Test
	public void EstimationApprovalRouteDtoのテスト() throws Exception {
		EstimationApprovalRoute entity = estimationApprovalRouteRepository.findOne(401L);
		EstimationApprovalRouteDto dto = new EstimationApprovalRouteDto();
		EstimationApprovalRouteDto testTarget = new EstimationApprovalRouteDto();

		BeanUtils.copyProperties(entity, dto);
		dto.setEstimationApprovalRouteNodeList(new ArrayList<EstimationApprovalRouteNodeDto>());
		entity.getEstimationApprovalRouteNodeList().forEach(s -> {
			EstimationApprovalRouteNodeDto node = new EstimationApprovalRouteNodeDto();
			BeanUtils.copyProperties(s, node);
			dto.getEstimationApprovalRouteNodeList().add(node);
		});

		// 正常系
		BeanUtils.copyProperties(dto, testTarget);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.setEstimationApprovalRouteNodeList(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "見積承認ルートノードが設定されていません。"));

		// 異常系（@Size(max) ：）
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.setApprovalRequesterEmpId(STR_256);
		testTarget.setApprovalRequesterName(STR_256);
		testTarget.setApprovalRequesterOrgName(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 3);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "承認依頼者組織名は最大文字数（255）を超えています。"));

		// 異常系（@Max ：）
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.setSpecialPriceApprovalFlg(INT_10);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "特価承認対象フラグは最大値（9）を超えています。"));

		// 異常系（@Min ：）
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.setSpecialPriceApprovalFlg(INT_MINUS_1);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00027));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "特価承認対象フラグは最小値（0）を下回っています。"));

		// 異常系（@Valid：）
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.getEstimationApprovalRouteNodeList().get(0).setApproverName(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "承認者氏名は最大文字数（255）を超えています。"));
	}

	@Test
	public void EstimationApprovalRouteNodeDtoのテスト() throws Exception {
		EstimationApprovalRouteNode entity = estimationApprovalRouteNodeRepository.findOne(401L);
		EstimationApprovalRouteNodeDto testTarget = new EstimationApprovalRouteNodeDto();

		// 正常系
		BeanUtils.copyProperties(entity, testTarget);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setApproverEmpId(null);
		testTarget.setApproverName(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "承認者氏名が設定されていません。"));

		// 異常系（@Size(max) ：）
		BeanUtils.copyProperties(entity, testTarget);
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
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setApprovalOrder(INT_1000);
		testTarget.setApproverOrgLevel(INT_10);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "承認順は最大値（999）を超えています。"));

		// 異常系（@Min ：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setApprovalOrder(INT_MINUS_1);
		testTarget.setApproverOrgLevel(INT_MINUS_1);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00027));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "承認者組織階層レベルは最小値（0）を下回っています。"));
	}

	@Test
	public void EstimationAttachedFileDtoのテスト() throws Exception {

		EstimationAttachedFile entity = estimationAttachedFileRepository.findOne(401L);
		EstimationAttachedFileDto dto = new EstimationAttachedFileDto();
		EstimationAttachedFileDto testTarget = new EstimationAttachedFileDto();

		BeanUtils.copyProperties(entity, dto);
		AttachedFileDto attachedFile = new AttachedFileDto();
		BeanUtils.copyProperties(entity.getAttachedFile(), attachedFile);
		dto.setAttachedFile(attachedFile);

		// 正常系
		BeanUtils.copyProperties(dto, testTarget);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.setFileName(null);
		testTarget.setAttachedFile(null);
		testTarget.setAttachedEmpId(null);
		testTarget.setAttachedEmpName(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 4);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "添付者氏名が設定されていません。"));

		// 異常系（@Size(max)
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.setFileName(STR_256);
		testTarget.setFileKind(STR_256);
		testTarget.setAttachedComment(STR_1001);
		testTarget.setAttachedEmpId(STR_256);
		testTarget.setAttachedEmpName(STR_256);
		testTarget.setAttachedOrgName(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 6);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "添付者MoM社員IDは最大文字数（255）を超えています。"));
	}

	@Test
	public void EstimationCheckResultDtoのテスト() throws Exception {
		EstimationCheckResult entity = estimationCheckResultRepository.findOne(401L);
		EstimationCheckResultDto testTarget = new EstimationCheckResultDto();

		// 正常系
		BeanUtils.copyProperties(entity, testTarget);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setTargetLifecycleStatus(null);
		testTarget.setCheckMatterCode(null);
		testTarget.setCheckMatterText(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 3);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "チェック事項文面が設定されていません。"));

		// 異常系（@Size(max) ：）
		BeanUtils.copyProperties(entity, testTarget);
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
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setDisplayOrder(INT_1000);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "表示順は最大値（999）を超えています。"));

		// 異常系（@Min ：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setDisplayOrder(INT_MINUS_1);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00027));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "表示順は最小値（0）を下回っています。"));
	}

	@Test
	public void EstimationDetailDtoのテスト() throws Exception {
		EstimationDetail entity = estimationDetailRepository.findOne(401L);
		EstimationDetailDto dto = new EstimationDetailDto();
		EstimationDetailDto testTarget = new EstimationDetailDto();
		BeanUtils.copyProperties(entity, dto);

		ItemEstimationDto itemEstimation = new ItemEstimationDto();
		BeanUtils.copyProperties(entity.getItemEstimation(), itemEstimation);
		dto.setItemEstimation(itemEstimation);

		// 正常系
		BeanUtils.copyProperties(dto, testTarget);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull：）
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.setState(null);
		testTarget.setEstimationAmountSummary(null);
		testTarget.setEstimationUnitPrice(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 3);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "状態が設定されていません。"));

		// 異常系（@Size(max) ：）
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.setDetailAbstract(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "摘要は最大文字数（255）を超えています。"));

		// 異常系（@Max ：）
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.setQuantity(INT_100000);
		testTarget.setBeforeQuantity(INT_100000);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "数量は最大値（99999）を超えています。"));

		// 異常系（@Min ：）
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.setQuantity(INT_MINUS_1);
		testTarget.setBeforeQuantity(INT_MINUS_1);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00027));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "数量は最小値（0）を下回っています。"));

		// 異常系（@DecimalMin：）
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.setEstimationAmountSummary(DECIMAL_MINUS_001);
		testTarget.setEstimationUnitPrice(DECIMAL_MINUS_001);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00027));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "見積金額は最小値（0.00）を下回っています。"));

		// 異常系（@Digits：）
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.setEstimationAmountSummary(DECIMAL_0001);
		testTarget.setEstimationUnitPrice(DECIMAL_0001);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00028));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "見積金額は小数点以下2桁を超えています。"));

		// 異常系（@Valid：）
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.getItemEstimation().setRicohItemCode(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "リコー品種コードは最大文字数（255）を超えています。"));
	}

	@Test
	public void EstimationPicSaEmpDtoのテスト() throws Exception {
		EstimationPicSaEmp entity = estimationPicSaEmpRepository.findOne(401L);
		EstimationPicSaEmpDto testTarget = new EstimationPicSaEmpDto();

		// 正常系
		BeanUtils.copyProperties(entity, testTarget);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setMomEmployeeId(null);
		testTarget.setEmployeeName(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "MoM社員IDが設定されていません。"));

		// 異常系（@Size(max) ：）
		BeanUtils.copyProperties(entity, testTarget);
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
		testTarget.setMomKjbId(STR_256);
		testTarget.setSalesCompanyNameKana(STR_256);
		testTarget.setCompanyRepresentativeName(STR_256);
		testTarget.setCompanyRepresentativeNameKana(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 17);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "MoM社員IDは最大文字数（255）を超えています。"));

		// 異常系（@Min ：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setOrgHierarchyLevel(INT_MINUS_1);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00027));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "所属組織階層レベルは最小値（0）を下回っています。"));

		// 異常系（@Max ：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setOrgHierarchyLevel(INT_10);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "所属組織階層レベルは最大値（9）を超えています。"));
	}

	@Test
	public void ItemEstimationDtoのテスト() throws Exception {
		ItemEstimation entity = itemEstimationRepository.findOne(401L);
		ItemEstimationDto testTarget = new ItemEstimationDto();

		// 正常系
		BeanUtils.copyProperties(entity, testTarget);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setItemType(null);
		testTarget.setCostType(null);
		testTarget.setPartitionPrice(null);
		testTarget.setItemEstimationName(null);
		testTarget.setRicohItemCode(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 5);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "品種名が設定されていません。"));

		// 異常系（@Size(max) ：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setItemEstimationName(STR_256);
		testTarget.setRicohItemCode(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "リコー品種コードは最大文字数（255）を超えています。"));

		// 異常系（@Min ：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setItemMasterId(INT_MINUS_1);
		testTarget.setProductMasterId(INT_MINUS_1);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00027));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "品種マスタIDは最小値（0）を下回っています。"));

		// 異常系（@DecimalMin：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setPartitionPrice(DECIMAL_MINUS_001);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00027));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "仕切価格は最小値（0.00）を下回っています。"));

		// 異常系（@Digits：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setPartitionPrice(DECIMAL_0001);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00028));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "仕切価格は小数点以下2桁を超えています。"));

	}

	@Test
	public void ProductEstimationDtoのテスト() throws Exception {
		ProductEstimation entity = productEstimationRepository.findOne(401L);
		ProductEstimationDto testTarget = new ProductEstimationDto();

		// 正常系
		BeanUtils.copyProperties(entity, testTarget);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setProductEstimationName(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "商品名が設定されていません。"));

		// 異常系（@Size(max) ：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setProductEstimationName(STR_256);
		testTarget.setServiceIdentNumber(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "サービス識別番号は最大文字数（255）を超えています。"));

		// 異常系（@Min ：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setProductMasterId(INT_MINUS_1);
		testTarget.setRepItemMasterId(INT_MINUS_1);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00027));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "代表品種マスタIDは最小値（0）を下回っています。"));
	}

	@Test
	public void EstimationRegisterParameterのテスト() throws Exception {
		EstimationRegisterParameter dto = new EstimationRegisterParameter();
		dto.setEstimationId(11L);
		dto.setProductGroupMasterId(11L);
		dto.setExtendsParameter("dummy_extends_param");
		dto.setCreatedUser("dummy_user");
		dto.setEstimationDetailRegisterParameterList(new ArrayList<EstimationDetailRegisterParameter>());

		EstimationDetailRegisterParameter detailParam = new EstimationDetailRegisterParameter();
		detailParam.setStatus("dummy_status");
		detailParam.setRicohItemCode("dummy_item_code");
		detailParam.setQuantity(11);
		detailParam.setAmountSummary(new BigDecimal("11.11"));
		detailParam.setExtendsParameter("dummy_extends_param");
		dto.getEstimationDetailRegisterParameterList().add(detailParam);

		EstimationRegisterParameter testTarget = new EstimationRegisterParameter();

		// 正常系
		BeanUtils.copyProperties(dto, testTarget);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull：）
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.setExtendsParameter(null);
		testTarget.setCreatedUser(null);
		testTarget.setEstimationDetailRegisterParameterList(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 3);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "登録者が設定されていません。"));

		// 異常系（@Size(max) ：）
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.setCreatedUser(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "登録者は最大文字数（255）を超えています。"));

		// 異常系（@Min ：）
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.setEstimationId(INT_MINUS_1);
		testTarget.setProductGroupMasterId(INT_MINUS_1);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00027));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "商品グループマスタIDは最小値（0）を下回っています。"));

		// 異常系（@Valid：）
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.getEstimationDetailRegisterParameterList().get(0).setRicohItemCode(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "リコー品種コードは最大文字数（255）を超えています。"));
	}

	@Test
	public void EstimationDetailRegisterParameterのテスト() throws Exception {
		EstimationDetailRegisterParameter dto = new EstimationDetailRegisterParameter();
		dto.setStatus("dummy_status");
		dto.setRicohItemCode("dummy_item_code");
		dto.setQuantity(11);
		dto.setAmountSummary(new BigDecimal("11.11"));
		dto.setExtendsParameter("dummy_extends_param");

		EstimationDetailRegisterParameter testTarget = new EstimationDetailRegisterParameter();

		// 正常系
		BeanUtils.copyProperties(dto, testTarget);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull：）
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.setStatus(null);
		testTarget.setRicohItemCode(null);
		testTarget.setAmountSummary(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 3);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "ステータスが設定されていません。"));

		// 異常系（@Size(max) ：）
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.setStatus(STR_256);
		testTarget.setRicohItemCode(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "ステータスは最大文字数（255）を超えています。"));

		// 異常系（@Min ：）
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.setQuantity(INT_MINUS_1);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00027));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "数量は最小値（0）を下回っています。"));

		// 異常系（@DecimalMin：）
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.setAmountSummary(DECIMAL_MINUS_001);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00027));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "金額は最小値（0.00）を下回っています。"));

		// 異常系（@Digits：）
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.setAmountSummary(DECIMAL_0001);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00028));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "金額は小数点以下2桁を超えています。"));
	}

	@Test
	public void EstimationCancelParameterのテスト() throws Exception {
		EstimationCancelParameter dto = new EstimationCancelParameter();
		dto.setReEstimationId(11L);
		dto.setContractId(11L);

		EstimationCancelParameter testTarget = new EstimationCancelParameter();

		// 正常系
		BeanUtils.copyProperties(dto, testTarget);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@Min ：）
		BeanUtils.copyProperties(dto, testTarget);
		testTarget.setReEstimationId(LONG_MINUS_1);
		testTarget.setContractId(LONG_MINUS_1);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00027));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "再見積IDは最小値（0）を下回っています。"));
	}
}
