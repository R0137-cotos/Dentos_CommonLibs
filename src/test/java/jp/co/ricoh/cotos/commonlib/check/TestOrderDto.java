package jp.co.ricoh.cotos.commonlib.check;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.databind.ObjectMapper;

import jp.co.ricoh.cotos.commonlib.DBConfig;
import jp.co.ricoh.cotos.commonlib.TestTools;
import jp.co.ricoh.cotos.commonlib.TestTools.ParameterErrorIds;
import jp.co.ricoh.cotos.commonlib.dto.parameter.contract.order.OrderBasicContentsDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.contract.order.OrderBasicInfoDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.contract.order.OrderBranchCustomerInfoDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.contract.order.OrderContractorInfoDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.contract.order.OrderDistributorInfoDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.contract.order.OrderListDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.contract.order.OrderProductGroupInfoDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.contract.order.OrderProductInfoDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.contract.order.OrderServiceInnerInfoDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.contract.order.OrderSetupInfoDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.contract.order.OrdererInfoDto;
import jp.co.ricoh.cotos.commonlib.entity.contract.order.OrderBasicInfo;
import jp.co.ricoh.cotos.commonlib.entity.contract.order.OrderBranchCustomerInfo;
import jp.co.ricoh.cotos.commonlib.entity.contract.order.OrderContractorInfo;
import jp.co.ricoh.cotos.commonlib.entity.contract.order.OrderDistributorInfo;
import jp.co.ricoh.cotos.commonlib.entity.contract.order.OrderProductGroupInfo;
import jp.co.ricoh.cotos.commonlib.entity.contract.order.OrderProductInfo;
import jp.co.ricoh.cotos.commonlib.entity.contract.order.OrderServiceInnerInfo;
import jp.co.ricoh.cotos.commonlib.entity.contract.order.OrderSetupInfo;
import jp.co.ricoh.cotos.commonlib.entity.contract.order.OrdererInfo;
import jp.co.ricoh.cotos.commonlib.repository.contract.order.OrderBasicInfoRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.order.OrderBranchCustomerInfoRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.order.OrderContractorInfoRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.order.OrderDistributorInfoRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.order.OrderProductGroupInfoRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.order.OrderProductInfoRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.order.OrderServiceInnerInfoRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.order.OrderSetupInfoRepository;
import jp.co.ricoh.cotos.commonlib.repository.contract.order.OrdererInfoRepository;
import jp.co.ricoh.cotos.commonlib.security.TestSecurityController;
import jp.co.ricoh.cotos.commonlib.security.bean.ParamterCheckResult;
import jp.co.ricoh.cotos.commonlib.util.HeadersProperties;

/**
 * パラメータチェック（契約ドメイン・注文）のテストクラス
 *
 * @author kentaro.kakuhana
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = "test.context.id = TestOrderDto")
public class TestOrderDto {

	private static final String STR_256 = "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345";
	private static final String STR_1001 = "01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
	private static final int INT_MINUS_1 = -1;
	private static final int INT_100000 = 100000;
	private static final BigDecimal DECIMAL_MINUS_001 = new BigDecimal("-0.01");
	private static final BigDecimal DECIMAL_0001 = new BigDecimal("0.001");

	static ConfigurableApplicationContext context;

	@Autowired
	HeadersProperties headersProperties;

	@Autowired
	OrderSetupInfoRepository orderSetupInfoRepository;

	@Autowired
	OrderServiceInnerInfoRepository orderServiceInnerInfoRepository;

	@Autowired
	OrderProductInfoRepository orderProductInfoRepository;

	@Autowired
	OrderProductGroupInfoRepository orderProductGroupInfoRepository;

	@Autowired
	OrdererInfoRepository ordererInfoRepository;

	@Autowired
	OrderDistributorInfoRepository orderDistributorInfoRepository;

	@Autowired
	OrderContractorInfoRepository orderContractorInfoRepository;

	@Autowired
	OrderBranchCustomerInfoRepository orderBranchCustomerInfoRepository;

	@Autowired
	OrderBasicInfoRepository orderBasicInfoRepository;

	@Autowired
	TestTools testTool;

	@Autowired
	ResourceLoader resourceLoader;

	@Autowired
	public void injectContext(ConfigurableApplicationContext injectContext) {
		context = injectContext;
		context.getBean(DBConfig.class).clearData();
		context.getBean(DBConfig.class).initTargetTestData("repository/attachedFile.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/order.sql");
	}

	@Autowired
	TestSecurityController testSecurityController;

	@LocalServerPort
	private int localServerPort;

	@Autowired
	ObjectMapper mapper;

	@AfterClass
	public static void stopAPServer() throws InterruptedException {
		if (null != context) {
			context.getBean(DBConfig.class).clearData();
			context.stop();
		}
	}

	@Test
	public void 外部IFのformatでコンバートできること() throws Exception {
		URL url = resourceLoader.getResource("classpath:order/orderIf.json").getURL();
		StringBuffer requestbodyBuffer = new StringBuffer();
		Reader reader = new InputStreamReader(url.openStream());
		BufferedReader bufferedReader = new BufferedReader(reader);
		bufferedReader.lines().forEach(line -> requestbodyBuffer.append(line + System.getProperty("line.separator")));
		String json = requestbodyBuffer.toString();
		OrderListDto result = mapper.readValue(json, OrderListDto.class);
		OrderBasicInfoDto target = result.getOrderList().get(0);
		Assert.assertNull(testTool.findNullProperties(target));
		Assert.assertNull(testTool.findNullProperties(target.getBasicContents()));
		Assert.assertNull(testTool.findNullProperties(target.getBranchCustomerInfo()));
		Assert.assertNull(testTool.findNullProperties(target.getContractorInfo()));
		Assert.assertNull(testTool.findNullProperties(target.getDistributorInfo()));
		Assert.assertNull(testTool.findNullProperties(target.getOrdererInfo()));
		Assert.assertNull(testTool.findNullProperties(target.getProductGroupInfo()));
		Assert.assertNull(testTool.findNullProperties(target.getProductInfo().get(0)));
		Assert.assertNull(testTool.findNullProperties(target.getProductInfo().get(1)));
		Assert.assertNull(testTool.findNullProperties(target.getSetupInfo()));
		Assert.assertNotNull(target.getServiceInnerInfo().getItem1());
		Assert.assertNotNull(target.getServiceInnerInfo().getItem2());
		Assert.assertNotNull(target.getServiceInnerInfo().getItem3());
		Assert.assertNotNull(target.getServiceInnerInfo().getItem4());
	}

	@Test
	public void OrderSetupInfoDtoのテスト() throws Exception {
		OrderSetupInfo entity = orderSetupInfoRepository.findById(401L).get();
		OrderSetupInfoDto testTarget = new OrderSetupInfoDto();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull）
		// なし

		// 異常系（@Size(max))
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setSetupCompanyName(STR_256);
		testTarget.setSetupNameKanji(STR_256);
		testTarget.setSetupNameKana(STR_256);
		testTarget.setSetupMailAddress(STR_256);
		testTarget.setSetupPostNumber(STR_256);
		testTarget.setSetupOfficeName(STR_256);
		testTarget.setSetupAddress(STR_256);
		testTarget.setSetupPhoneNumber(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 8);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));

		//dto-エンティティ整合性チェック※DTOクラスでは必須
		testTool.checkConsistency(OrderSetupInfo.class, OrderSetupInfoDto.class);
	}

	@Test
	public void OrderServiceInnerInfoDtoのテスト() throws Exception {
		OrderServiceInnerInfo entity = orderServiceInnerInfoRepository.findById(401L).get();
		OrderServiceInnerInfoDto testTarget = new OrderServiceInnerInfoDto();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull）
		// なし

		// 異常系（@Size(max))
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setItem1(STR_256);
		testTarget.setItem2(STR_256);
		testTarget.setItem3(STR_256);
		testTarget.setItem4(STR_256);
		testTarget.setItem5(STR_256);
		testTarget.setItem6(STR_256);
		testTarget.setItem7(STR_256);
		testTarget.setItem8(STR_256);
		testTarget.setItem9(STR_256);
		testTarget.setItem10(STR_256);
		testTarget.setItem11(STR_256);
		testTarget.setItem12(STR_256);
		testTarget.setItem13(STR_256);
		testTarget.setItem14(STR_256);
		testTarget.setItem15(STR_256);
		testTarget.setItem16(STR_256);
		testTarget.setItem17(STR_256);
		testTarget.setItem18(STR_256);
		testTarget.setItem19(STR_256);
		testTarget.setItem20(STR_256);
		testTarget.setItem21(STR_256);
		testTarget.setItem22(STR_256);
		testTarget.setItem23(STR_256);
		testTarget.setItem24(STR_256);
		testTarget.setItem25(STR_256);
		testTarget.setItem26(STR_256);
		testTarget.setItem27(STR_256);
		testTarget.setItem28(STR_256);
		testTarget.setItem29(STR_256);
		testTarget.setItem30(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 30);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));

		//dto-エンティティ整合性チェック※DTOクラスでは必須
		testTool.checkConsistency(OrderServiceInnerInfo.class, OrderServiceInnerInfoDto.class);
	}

	@Test
	public void OrderProductInfoDtoのテスト() throws Exception {
		OrderProductInfo entity = orderProductInfoRepository.findById(401L).get();
		OrderProductInfoDto testTarget = new OrderProductInfoDto();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull）
		// なし

		// 異常系（@Size(max))
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setProductCd(STR_256);
		testTarget.setFreePeriod(STR_256);
		testTarget.setProductName(STR_256);

		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 3);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));

		// 異常系（@Max ：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setQuantity(INT_100000);
		testTarget.setBeforeQuantity(INT_100000);
		testTarget.setDifferenceQuantity(INT_100000);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 3);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "変更前数量は最大値（99999）を超えています。"));

		// 異常系（@Min ：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setQuantity(INT_MINUS_1);
		testTarget.setBeforeQuantity(INT_MINUS_1);
		testTarget.setDifferenceQuantity(INT_MINUS_1); // マイナス登録可能に修正
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2); // differenceQuantityがマイナスでもエラーにならないことを確認
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00027));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "変更前数量は最小値（0）を下回っています。"));

		// 異常系（@DecimalMin：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setUnitPrice(DECIMAL_MINUS_001);
		testTarget.setAmountSummary(DECIMAL_MINUS_001);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00027));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "単価は最小値（0.00）を下回っています。"));

		// 異常系（@Digits：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setUnitPrice(DECIMAL_0001);
		testTarget.setAmountSummary(DECIMAL_0001);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00028));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "単価は小数点以下2桁を超えています。"));

		//dto-エンティティ整合性チェック※DTOクラスでは必須
		testTool.checkConsistency(OrderProductInfo.class, OrderProductInfoDto.class);

	}

	@Test
	public void OrderProductGroupInfoDtoのテスト() throws Exception {
		OrderProductGroupInfo entity = orderProductGroupInfoRepository.findById(401L).get();
		OrderProductGroupInfoDto testTarget = new OrderProductGroupInfoDto();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull）
		// なし

		// 異常系（@Size(max))
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setProductGroupCd(STR_256);
		testTarget.setProductGroupName(STR_256);

		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));

		//dto-エンティティ整合性チェック※DTOクラスでは必須
		testTool.checkConsistency(OrderProductGroupInfo.class, OrderProductGroupInfoDto.class);
	}

	@Test
	public void OrdererInfoDtoのテスト() throws Exception {
		OrdererInfo entity = ordererInfoRepository.findById(401L).get();
		OrdererInfoDto testTarget = new OrdererInfoDto();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull）
		// なし

		// 異常系（@Size(max))
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setOrdererCompanyName(STR_256);
		testTarget.setOrdererNameKanji(STR_256);
		testTarget.setOrdererNameKana(STR_256);
		testTarget.setOrdererMailAddress(STR_256);
		testTarget.setOrdererPostNumber(STR_256);
		testTarget.setOrdererOfficeName(STR_256);
		testTarget.setOrdererAddress(STR_256);
		testTarget.setOrdererPhoneNumber(STR_256);

		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 8);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));

		//dto-エンティティ整合性チェック※DTOクラスでは必須
		testTool.checkConsistency(OrdererInfo.class, OrdererInfoDto.class);
	}

	@Test
	public void OrderDistributorInfoDtoのテスト() throws Exception {
		OrderDistributorInfo entity = orderDistributorInfoRepository.findById(401L).get();
		OrderDistributorInfoDto testTarget = new OrderDistributorInfoDto();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull）
		// なし

		// 異常系（@Size(max))
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setDistributorCd(STR_256);
		testTarget.setDistributorName(STR_256);
		testTarget.setOeDeliveryCd(STR_256);
		testTarget.setDistributorCustomerType(STR_256);
		testTarget.setDistributorEmployeeName(STR_256);
		testTarget.setDistributorEmployeeMailAddress(STR_256);
		testTarget.setRingsCustomerCd(STR_256);
		testTarget.setDistributorPostNumber(STR_256);
		testTarget.setDistributorAddress(STR_256);

		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 9);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));

		//dto-エンティティ整合性チェック※DTOクラスでは必須
		testTool.checkConsistency(OrderDistributorInfo.class, OrderDistributorInfoDto.class);
	}

	@Test
	public void OrderContractorInfoDtoのテスト() throws Exception {
		OrderContractorInfo entity = orderContractorInfoRepository.findById(401L).get();
		OrderContractorInfoDto testTarget = new OrderContractorInfoDto();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull）
		// なし

		// 異常系（@Size(max))
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setCompanyId(STR_256);
		testTarget.setKjbId(STR_256);
		testTarget.setNetricohAccount(STR_256);
		testTarget.setCustomerCd(STR_256);
		testTarget.setContractorCompanyName(STR_256);
		testTarget.setContractorNameKanji(STR_256);
		testTarget.setContractorNameKana(STR_256);
		testTarget.setContractorMailAddress(STR_256);
		testTarget.setContractorPostNumber(STR_256);
		testTarget.setContractorOfficeName(STR_256);
		testTarget.setContractorAddress(STR_256);
		testTarget.setContractorPhoneNumber(STR_256);
		testTarget.setAuthorityForNetricoh(STR_256);

		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 13);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));

		//dto-エンティティ整合性チェック※DTOクラスでは必須
		testTool.checkConsistency(OrderContractorInfo.class, OrderContractorInfoDto.class);
	}

	@Test
	public void OrderBranchCustomerInfoDtoのテスト() throws Exception {
		OrderBranchCustomerInfo entity = orderBranchCustomerInfoRepository.findById(401L).get();
		OrderBranchCustomerInfoDto testTarget = new OrderBranchCustomerInfoDto();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull）
		// なし

		// 異常系（@Size(max))
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setBranchCustomerCd(STR_256);
		testTarget.setBranchCustomerName(STR_256);
		testTarget.setOfficeCd(STR_256);
		testTarget.setOfficeName(STR_256);
		testTarget.setEmployeeCd(STR_256);
		testTarget.setEmployeeName(STR_256);
		testTarget.setEmployeePhoneNumber(STR_256);
		testTarget.setEmployeeMailAddress(STR_256);

		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 8);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));

		//dto-エンティティ整合性チェック※DTOクラスでは必須
		testTool.checkConsistency(OrderBranchCustomerInfo.class, OrderBranchCustomerInfoDto.class);
	}

	@Test
	public void OrderBasicContentsDto() throws Exception {
		OrderBasicInfo entity = orderBasicInfoRepository.findById(4L).get();
		OrderBasicContentsDto testTarget = new OrderBasicContentsDto();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull）
		// なし

		// 異常系（@Size(max))
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setOrdererNumber(STR_256);
		testTarget.setRjManageNumber(STR_256);
		testTarget.setMakerManageNumber(STR_256);
		testTarget.setCancelReason1(STR_256);
		testTarget.setCancelReason2(STR_1001);
		testTarget.setImprovementPoint(STR_1001);

		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 6);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));

		// 異常系（@DecimalMin：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setInitialTotalAmount(DECIMAL_MINUS_001);
		testTarget.setMonthlyTotalAmount(DECIMAL_MINUS_001);
		testTarget.setYearlyTotalAmount(DECIMAL_MINUS_001);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 3);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00027));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "初期費合計は最小値（0.00）を下回っています。"));

		// 異常系（@Digits：）
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setInitialTotalAmount(DECIMAL_0001);
		testTarget.setMonthlyTotalAmount(DECIMAL_0001);
		testTarget.setYearlyTotalAmount(DECIMAL_0001);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 3);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00028));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "初期費合計は小数点以下2桁を超えています。"));

		//dto-エンティティ整合性チェック※DTOクラスでは必須
		testTool.checkConsistency(OrderBasicInfo.class, OrderBasicContentsDto.class);

	}

	@Test
	public void OrderListDtoのテスト() throws Exception {

		// 子DTOのチェックが走っているかどうかの確認

		// トップレベルDTO
		OrderListDto testTopTarget = new OrderListDto();

		// 親
		OrderBasicInfo entity = orderBasicInfoRepository.findById(4L).get();
		OrderBasicInfoDto testTarget = new OrderBasicInfoDto();
		BeanUtils.copyProperties(entity, testTarget);

		// 子
		OrderSetupInfoDto testOrderSetupInfoTarget = new OrderSetupInfoDto();
		BeanUtils.copyProperties(entity.getOrderSetupInfo(), testOrderSetupInfoTarget);
		testTarget.setSetupInfo(testOrderSetupInfoTarget);

		OrderServiceInnerInfoDto testOrderServiceInnerInfoTarget = new OrderServiceInnerInfoDto();
		BeanUtils.copyProperties(entity.getOrderServiceInnerInfo(), testOrderServiceInnerInfoTarget);
		testTarget.setServiceInnerInfo(testOrderServiceInnerInfoTarget);

		OrderProductInfoDto testOrderProductInfoTarget = new OrderProductInfoDto();
		BeanUtils.copyProperties(entity.getOrderProductInfoList().get(0), testOrderProductInfoTarget);
		List<OrderProductInfoDto> testOrderProductInfoTargetList = new ArrayList<>();
		testOrderProductInfoTargetList.add(testOrderProductInfoTarget);
		testTarget.setProductInfo(testOrderProductInfoTargetList);

		OrderProductGroupInfoDto testOrderProductGroupInfoTarget = new OrderProductGroupInfoDto();
		BeanUtils.copyProperties(entity.getOrderProductGroupInfo(), testOrderProductGroupInfoTarget);
		testTarget.setProductGroupInfo(testOrderProductGroupInfoTarget);

		OrdererInfoDto testOrdererInfoTarget = new OrdererInfoDto();
		BeanUtils.copyProperties(entity.getOrdererInfo(), testOrdererInfoTarget);
		testTarget.setOrdererInfo(testOrdererInfoTarget);

		OrderDistributorInfoDto testOrderDistributorInfoTarget = new OrderDistributorInfoDto();
		BeanUtils.copyProperties(entity.getOrderDistributorInfo(), testOrderDistributorInfoTarget);
		testTarget.setDistributorInfo(testOrderDistributorInfoTarget);

		OrderContractorInfoDto testOrderContractorInfoTarget = new OrderContractorInfoDto();
		BeanUtils.copyProperties(entity.getOrderContractorInfo(), testOrderContractorInfoTarget);
		testTarget.setContractorInfo(testOrderContractorInfoTarget);

		OrderBranchCustomerInfoDto testOrderBranchCustomerInfoTarget = new OrderBranchCustomerInfoDto();
		BeanUtils.copyProperties(entity.getOrderBranchCustomerInfo(), testOrderBranchCustomerInfoTarget);
		testTarget.setBranchCustomerInfo(testOrderBranchCustomerInfoTarget);

		OrderBasicContentsDto testOrderBasicContentsDtoTarget = new OrderBasicContentsDto();
		BeanUtils.copyProperties(entity, testOrderBasicContentsDtoTarget);
		testTarget.setBasicContents(testOrderBasicContentsDtoTarget);

		// 正常系
		testTopTarget.setOrderList(new ArrayList<>());
		testTopTarget.getOrderList().add(testTarget);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTopTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@Size(max))
		testTarget.getBasicContents().setOrdererNumber(STR_256);
		testTarget.getSetupInfo().setSetupCompanyName(STR_256);
		testTarget.getServiceInnerInfo().setItem1(STR_256);
		testTarget.getProductInfo().get(0).setFreePeriod(STR_256);
		;
		testTarget.getProductGroupInfo().setProductGroupCd(STR_256);
		;
		testTarget.getOrdererInfo().setOrdererCompanyName(STR_256);
		;
		testTarget.getDistributorInfo().setDistributorEmployeeName(STR_256);
		;
		testTarget.getContractorInfo().setContractorCompanyName(STR_256);
		;
		testTarget.getBranchCustomerInfo().setEmployeeName(STR_256);
		;

		result = testSecurityController.callParameterCheck(testTopTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 9);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
	}

}
