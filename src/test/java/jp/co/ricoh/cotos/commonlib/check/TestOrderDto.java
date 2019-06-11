package jp.co.ricoh.cotos.commonlib.check;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestOrderDto {

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

	@AfterClass
	public static void stopAPServer() throws InterruptedException {
		if (null != context) {
			context.getBean(DBConfig.class).clearData();
			context.stop();
		}
	}

	@Test
	public void OrderSetupInfoDtoのテスト() throws Exception {
		OrderSetupInfo entity = orderSetupInfoRepository.findOne(401L);
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
	}

	@Test
	public void OrderServiceInnerInfoDtoのテスト() throws Exception {
		OrderServiceInnerInfo entity = orderServiceInnerInfoRepository.findOne(401L);
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
	}

	@Test
	public void OrderProductInfoDtoのテスト() throws Exception {
		OrderProductInfo entity = orderProductInfoRepository.findOne(401L);
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
	}

	@Test
	public void OrderProductGroupInfoDtoのテスト() throws Exception {
		OrderProductGroupInfo entity = orderProductGroupInfoRepository.findOne(401L);
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
	}

	@Test
	public void OrdererInfoDtoのテスト() throws Exception {
		OrdererInfo entity = ordererInfoRepository.findOne(401L);
		OrdererInfoDto testTarget = new OrdererInfoDto();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull）
		//なし

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
	}

	@Test
	public void OrderDistributorInfoDtoのテスト() throws Exception {
		OrderDistributorInfo entity = orderDistributorInfoRepository.findOne(401L);
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
	}

	@Test
	public void OrderContractorInfoDtoのテスト() throws Exception {
		OrderContractorInfo entity = orderContractorInfoRepository.findOne(401L);
		OrderContractorInfoDto testTarget = new OrderContractorInfoDto();
		BeanUtils.copyProperties(entity, testTarget);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull）
		// なし

		// 異常系（@Size(max))
		BeanUtils.copyProperties(entity, testTarget);
		testTarget.setCompanyid(STR_256);
		testTarget.setKjbid(STR_256);
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
	}

	@Test
	public void OrderBranchCustomerInfoDtoのテスト() throws Exception {
		OrderBranchCustomerInfo entity = orderBranchCustomerInfoRepository.findOne(401L);
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
	}
	
	@Test
	public void OrderBasicInfoDtoのテスト() throws Exception {
		OrderBasicInfo entity = orderBasicInfoRepository.findOne(4L);
		OrderBasicInfoDto testTarget = new OrderBasicInfoDto();
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

		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 3);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
	}
	
	@Test
	public void OrderListDtoのテスト() throws Exception {
		
		//子DTOのチェックが走っているかどうかの確認
		
		//トップレベルDTO
		OrderListDto testTopTarget = new OrderListDto();
		
		//親
		OrderBasicInfo entity = orderBasicInfoRepository.findOne(4L);
		OrderBasicInfoDto testTarget = new OrderBasicInfoDto();
		BeanUtils.copyProperties(entity, testTarget);
		
		//子
		OrderSetupInfoDto testOrderSetupInfoTarget = new OrderSetupInfoDto();
		BeanUtils.copyProperties(entity.getOrderSetupInfo(), testOrderSetupInfoTarget);
		testTarget.setOrderSetupInfoDto(testOrderSetupInfoTarget);
		
		OrderServiceInnerInfoDto testOrderServiceInnerInfoTarget = new OrderServiceInnerInfoDto();
		BeanUtils.copyProperties(entity.getOrderServiceInnerInfo(), testOrderServiceInnerInfoTarget);
		testTarget.setOrderServiceInnerInfoDto(testOrderServiceInnerInfoTarget);
		
		OrderProductInfoDto testOrderProductInfoTarget = new OrderProductInfoDto();
		BeanUtils.copyProperties(entity.getOrderProductInfoList().get(0), testOrderProductInfoTarget);
		List<OrderProductInfoDto> testOrderProductInfoTargetList = new ArrayList<>();
		testOrderProductInfoTargetList.add(testOrderProductInfoTarget);
		testTarget.setOrderProductInfoDtoList(testOrderProductInfoTargetList);
		
		OrderProductGroupInfoDto testOrderProductGroupInfoTarget = new OrderProductGroupInfoDto();
		BeanUtils.copyProperties(entity.getOrderProductGroupInfo(), testOrderProductGroupInfoTarget);
		testTarget.setOrderProductGroupInfoDto(testOrderProductGroupInfoTarget);
		
		OrdererInfoDto testOrdererInfoTarget = new OrdererInfoDto();
		BeanUtils.copyProperties(entity.getOrdererInfo(), testOrdererInfoTarget);
		testTarget.setOrdererInfoDto(testOrdererInfoTarget);
		
		OrderDistributorInfoDto testOrderDistributorInfoTarget = new OrderDistributorInfoDto();
		BeanUtils.copyProperties(entity.getOrderDistributorInfo(), testOrderDistributorInfoTarget);
		testTarget.setOrderDistributorInfoDto(testOrderDistributorInfoTarget);
		
		OrderContractorInfoDto testOrderContractorInfoTarget = new OrderContractorInfoDto();
		BeanUtils.copyProperties(entity.getOrderContractorInfo(), testOrderContractorInfoTarget);
		testTarget.setOrderContractorInfoDto(testOrderContractorInfoTarget);
		
		OrderBranchCustomerInfoDto testOrderBranchCustomerInfoTarget = new OrderBranchCustomerInfoDto();
		BeanUtils.copyProperties(entity.getOrderBranchCustomerInfo(), testOrderBranchCustomerInfoTarget);
		testTarget.setOrderBranchCustomerInfoDto(testOrderBranchCustomerInfoTarget);
		
		// 正常系
		testTopTarget.setOrderList(new ArrayList<>());
		testTopTarget.getOrderList().add(testTarget);
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTopTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);
		
		// 異常系（@Size(max))
		testTarget.setOrdererNumber(STR_256);
		testTarget.getOrderSetupInfoDto().setSetupCompanyName(STR_256);
		testTarget.getOrderServiceInnerInfoDto().setItem1(STR_256);
		testTarget.getOrderProductInfoDtoList().get(0).setFreePeriod(STR_256);;
		testTarget.getOrderProductGroupInfoDto().setProductGroupCd(STR_256);;
		testTarget.getOrdererInfoDto().setOrdererCompanyName(STR_256);;
		testTarget.getOrderDistributorInfoDto().setDistributorEmployeeName(STR_256);;
		testTarget.getOrderContractorInfoDto().setContractorCompanyName(STR_256);;
		testTarget.getOrderBranchCustomerInfoDto().setEmployeeName(STR_256);;
		
		result = testSecurityController.callParameterCheck(testTopTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 9);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
	}

}
