package jp.co.ricoh.cotos.commonlib.check;

import java.util.ArrayList;
import java.util.List;

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
import jp.co.ricoh.cotos.commonlib.dto.parameter.arrangementDelegation.ArrangementResultDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.arrangementDelegation.ArrangementResultInfoDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.arrangementDelegation.ContractDetailDelegationDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.arrangementDelegation.ContractInfoDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.arrangementDelegation.ErrorInfoDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.arrangementDelegation.ItemContractDelegationDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.arrangementDelegation.ProductContractDelegationDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.arrangementDelegation.RatePlanChargeInfoDto;
import jp.co.ricoh.cotos.commonlib.security.TestSecurityController;
import jp.co.ricoh.cotos.commonlib.security.bean.ParamterCheckResult;
import jp.co.ricoh.cotos.commonlib.util.HeadersProperties;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestArrangementResultDto {

	private static final String STR_256 = "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345";
	private static final int INT_MINUS_1 = -1;

	static ConfigurableApplicationContext context;

	@Autowired
	HeadersProperties headersProperties;

	@Autowired
	TestTools testTool;

	@Autowired
	public void injectContext(ConfigurableApplicationContext injectContext) {
		context = injectContext;
		context.getBean(DBConfig.class).clearData();
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
	public void ArrangementResultDtoのテスト() {

		//正常系
		ArrangementResultDto arrangementResultDto = 正常系データ作成();
		ParamterCheckResult result = testSecurityController.callParameterCheck(arrangementResultDto, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		//異常系_オブジェクトNull
		arrangementResultDto.setArrangementResultInfo(null);
		arrangementResultDto.setContractInfo(null);
		result = testSecurityController.callParameterCheck(arrangementResultDto, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);

		//異常系_子エンティティ配下Null
		arrangementResultDto = 異常系Nullデータ作成();
		result = testSecurityController.callParameterCheck(arrangementResultDto, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 8);

		//異常系_文字列最大値
		arrangementResultDto = 異常系入力値不正データ作成();
		result = testSecurityController.callParameterCheck(arrangementResultDto, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 22);
	}

	private ArrangementResultDto 正常系データ作成() {
		ArrangementResultInfoDto arrangementResultInfoDto = new ArrangementResultInfoDto();
		arrangementResultInfoDto.setResultCode("resultCode");
		ErrorInfoDto errorInfoDto = new ErrorInfoDto();
		errorInfoDto.setTargetApi("targetApi");
		errorInfoDto.setMessage("message");
		arrangementResultInfoDto.setErrorInfo(errorInfoDto);

		ContractInfoDto contractInfoDto = new ContractInfoDto();
		contractInfoDto.setContractNumber("contractNumber");
		contractInfoDto.setContractBranchNumber("contractBranchNumber");
		List<ContractDetailDelegationDto> contractDetailDelegationDtoList = new ArrayList<>();
		ContractDetailDelegationDto contractDetailDelegationDto = new ContractDetailDelegationDto();
		contractDetailDelegationDto.setId(1);
		ItemContractDelegationDto itemContractDelegationDto = new ItemContractDelegationDto();
		itemContractDelegationDto.setId(1);
		itemContractDelegationDto.setItemMasterId(1);
		itemContractDelegationDto.setProductMasterId(1);
		itemContractDelegationDto.setItemContractName("itemContractName");
		itemContractDelegationDto.setRicohItemCode("ricohItemName");
		contractDetailDelegationDto.setItemContract(itemContractDelegationDto);
		RatePlanChargeInfoDto ratePlanChargeInfoDto = new RatePlanChargeInfoDto();
		ratePlanChargeInfoDto.setProductId("productId");
		ratePlanChargeInfoDto.setProductRatePlanId("productRatePlanId");
		ratePlanChargeInfoDto.setProductRatePlanChargeId("productRatePlanChargeId");
		ratePlanChargeInfoDto.setRatePlanChargeId("ratePlanChargeId");
		contractDetailDelegationDto.setRatePlanChargeInfo(ratePlanChargeInfoDto);
		contractDetailDelegationDtoList.add(contractDetailDelegationDto);
		contractInfoDto.setContractDetailList(contractDetailDelegationDtoList);
		List<ProductContractDelegationDto> productContractDelegationDtoList = new ArrayList<>();
		ProductContractDelegationDto productContractDelegationDto = new ProductContractDelegationDto();
		productContractDelegationDto.setId(1);
		productContractDelegationDto.setProductMasterId(1);
		productContractDelegationDto.setProductContractName("productContractName");
		productContractDelegationDto.setTenantId("tenantId");
		productContractDelegationDto.setZuoraAccountId("zuoraAccountId");
		productContractDelegationDto.setUserId("userId");
		productContractDelegationDto.setSubscriptionNumber("subscriptionNumber");
		productContractDelegationDtoList.add(productContractDelegationDto);
		contractInfoDto.setProductContractList(productContractDelegationDtoList);

		ArrangementResultDto arrangementResultDto = new ArrangementResultDto();
		arrangementResultDto.setArrangementResultInfo(arrangementResultInfoDto);
		arrangementResultDto.setContractInfo(contractInfoDto);

		return arrangementResultDto;
	}

	private ArrangementResultDto 異常系Nullデータ作成() {
		ArrangementResultInfoDto arrangementResultInfoDto = new ArrangementResultInfoDto();
		arrangementResultInfoDto.setResultCode(null);

		ContractInfoDto contractInfoDto = new ContractInfoDto();
		contractInfoDto.setContractNumber(null);
		contractInfoDto.setContractBranchNumber(null);
		List<ContractDetailDelegationDto> contractDetailDelegationDtoList = new ArrayList<>();
		ContractDetailDelegationDto contractDetailDelegationDto = new ContractDetailDelegationDto();
		contractDetailDelegationDto.setId(1);
		ItemContractDelegationDto itemContractDelegationDto = new ItemContractDelegationDto();
		itemContractDelegationDto.setId(1);
		itemContractDelegationDto.setItemMasterId(1);
		itemContractDelegationDto.setProductMasterId(1);
		itemContractDelegationDto.setItemContractName(null);
		itemContractDelegationDto.setRicohItemCode(null);
		contractDetailDelegationDto.setItemContract(itemContractDelegationDto);
		RatePlanChargeInfoDto ratePlanChargeInfoDto = new RatePlanChargeInfoDto();
		ratePlanChargeInfoDto.setProductRatePlanChargeId(null);
		contractDetailDelegationDto.setRatePlanChargeInfo(ratePlanChargeInfoDto);
		contractDetailDelegationDtoList.add(contractDetailDelegationDto);
		contractInfoDto.setContractDetailList(contractDetailDelegationDtoList);
		List<ProductContractDelegationDto> productContractDelegationDtoList = new ArrayList<>();
		ProductContractDelegationDto productContractDelegationDto = new ProductContractDelegationDto();
		productContractDelegationDto.setId(1);
		productContractDelegationDto.setProductMasterId(1);
		productContractDelegationDto.setProductContractName(null);
		productContractDelegationDto.setTenantId(null);
		productContractDelegationDtoList.add(productContractDelegationDto);
		contractInfoDto.setProductContractList(productContractDelegationDtoList);

		ArrangementResultDto arrangementResultDto = new ArrangementResultDto();
		arrangementResultDto.setArrangementResultInfo(arrangementResultInfoDto);
		arrangementResultDto.setContractInfo(contractInfoDto);

		return arrangementResultDto;
	}

	private ArrangementResultDto 異常系入力値不正データ作成() {
		ArrangementResultInfoDto arrangementResultInfoDto = new ArrangementResultInfoDto();
		arrangementResultInfoDto.setResultCode(STR_256);
		ErrorInfoDto errorInfoDto = new ErrorInfoDto();
		errorInfoDto.setTargetApi(STR_256);
		errorInfoDto.setMessage(STR_256);
		arrangementResultInfoDto.setErrorInfo(errorInfoDto);

		ContractInfoDto contractInfoDto = new ContractInfoDto();
		contractInfoDto.setContractNumber(STR_256);
		contractInfoDto.setContractBranchNumber(STR_256);
		List<ContractDetailDelegationDto> contractDetailDelegationDtoList = new ArrayList<>();
		ContractDetailDelegationDto contractDetailDelegationDto = new ContractDetailDelegationDto();
		contractDetailDelegationDto.setId(INT_MINUS_1);
		ItemContractDelegationDto itemContractDelegationDto = new ItemContractDelegationDto();
		itemContractDelegationDto.setId(INT_MINUS_1);
		itemContractDelegationDto.setItemMasterId(INT_MINUS_1);
		itemContractDelegationDto.setProductMasterId(INT_MINUS_1);
		itemContractDelegationDto.setItemContractName(STR_256);
		itemContractDelegationDto.setRicohItemCode(STR_256);
		contractDetailDelegationDto.setItemContract(itemContractDelegationDto);
		RatePlanChargeInfoDto ratePlanChargeInfoDto = new RatePlanChargeInfoDto();
		ratePlanChargeInfoDto.setProductId(STR_256);
		ratePlanChargeInfoDto.setProductRatePlanId(STR_256);
		ratePlanChargeInfoDto.setProductRatePlanChargeId(STR_256);
		ratePlanChargeInfoDto.setRatePlanChargeId(STR_256);
		contractDetailDelegationDto.setRatePlanChargeInfo(ratePlanChargeInfoDto);
		contractDetailDelegationDtoList.add(contractDetailDelegationDto);
		contractInfoDto.setContractDetailList(contractDetailDelegationDtoList);
		List<ProductContractDelegationDto> productContractDelegationDtoList = new ArrayList<>();
		ProductContractDelegationDto productContractDelegationDto = new ProductContractDelegationDto();
		productContractDelegationDto.setId(INT_MINUS_1);
		productContractDelegationDto.setProductMasterId(INT_MINUS_1);
		productContractDelegationDto.setProductContractName(STR_256);
		productContractDelegationDto.setTenantId(STR_256);
		productContractDelegationDto.setZuoraAccountId(STR_256);
		productContractDelegationDto.setUserId(STR_256);
		productContractDelegationDto.setSubscriptionNumber(STR_256);
		productContractDelegationDtoList.add(productContractDelegationDto);
		contractInfoDto.setProductContractList(productContractDelegationDtoList);

		ArrangementResultDto arrangementResultDto = new ArrangementResultDto();
		arrangementResultDto.setArrangementResultInfo(arrangementResultInfoDto);
		arrangementResultDto.setContractInfo(contractInfoDto);

		return arrangementResultDto;
	}

}
