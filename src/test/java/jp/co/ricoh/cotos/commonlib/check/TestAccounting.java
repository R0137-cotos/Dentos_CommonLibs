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
import jp.co.ricoh.cotos.commonlib.entity.accounting.Accounting;
import jp.co.ricoh.cotos.commonlib.repository.accounting.AccountingRepository;
import jp.co.ricoh.cotos.commonlib.security.TestSecurityController;
import jp.co.ricoh.cotos.commonlib.security.bean.ParamterCheckResult;
import jp.co.ricoh.cotos.commonlib.util.HeadersProperties;

/**
 * パラメータチェック（課金計上ドメイン）のテストクラス
 *
 * @author kentaro.kakuhana
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestAccounting {

	private static final String STR_256 = "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345";

	static ConfigurableApplicationContext context;

	@Autowired
	HeadersProperties headersProperties;

	@Autowired
	AccountingRepository accountingRespository;

	@Autowired
	TestTools testTool;

	@Autowired
	public void injectContext(ConfigurableApplicationContext injectContext) {
		context = injectContext;
		context.getBean(DBConfig.class).clearData();
		context.getBean(DBConfig.class).initTargetTestData("repository/accounting/accounting.sql");
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
	public void Accountingのテスト() throws Exception {

		Accounting entity = accountingRespository.findOne(1L);
		Accounting testTarget = new Accounting();
		BeanUtils.copyProperties(testTarget, entity);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@Size(max))
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setRjManageNumber(STR_256);
		testTarget.setWebOrderNo(STR_256);
		testTarget.setProductTypeCd(STR_256);
		testTarget.setProductTypeName(STR_256);
		testTarget.setFfmCompanyCd(STR_256);
		testTarget.setFfmDataPtn(STR_256);
		testTarget.setFfmAccountType(STR_256);
		testTarget.setFfmDataType(STR_256);
		testTarget.setFfmRedBlackType(STR_256);
		testTarget.setFfmMatchingKey(STR_256);
		testTarget.setFfmNspKey(STR_256);
		testTarget.setFfmProjectNo(STR_256);
		testTarget.setFfmContractDocNo(STR_256);
		testTarget.setFfmContractNo(STR_256);
		testTarget.setFfmContractDetailNo(STR_256);
		testTarget.setFfmBillingDetailNo(STR_256);
		testTarget.setFfmInqNo(STR_256);
		testTarget.setFfmInqDetailNo(STR_256);
		testTarget.setFfmArrProjectNo(STR_256);
		testTarget.setFfmArrInqNo(STR_256);
		testTarget.setFfmCancelReason(STR_256);
		testTarget.setFfmOrgContractCd(STR_256);
		testTarget.setFfmOrgContractDetailNo(STR_256);
		testTarget.setFfmContractDate(STR_256);
		testTarget.setFfmContractPeriodStart(STR_256);
		testTarget.setFfmContractPeriodEnd(STR_256);
		testTarget.setFfmContractSscd(STR_256);
		testTarget.setFfmContractSspiccd(STR_256);
		testTarget.setFfmTrnsLocationCd(STR_256);
		testTarget.setFfmTrnsPicCd(STR_256);
		testTarget.setFfmMntLeaseNo(STR_256);
		testTarget.setFfmProdactCd(STR_256);
		testTarget.setFfmModelId(STR_256);
		testTarget.setFfmSerialId(STR_256);
		testTarget.setFfmQuotationProdactName(STR_256);
		testTarget.setFfmCostProdactName(STR_256);
		testTarget.setFfmPurchaseType(STR_256);
		testTarget.setFfmPurchaseDiscntType(STR_256);
		testTarget.setFfmPurchaseClassType(STR_256);
		testTarget.setFfmPurchaseDate(STR_256);
		testTarget.setFfmNonRItemCd(STR_256);
		testTarget.setFfmSupplierCd(STR_256);
		testTarget.setFfmDeptAssortType(STR_256);
		testTarget.setFfmPurchaseLocationCd(STR_256);
		testTarget.setFfmPurchaseRespClientCd(STR_256);
		testTarget.setFfmRdStrctInventoryCd(STR_256);
		testTarget.setFfmDealsNo(STR_256);
		testTarget.setFfmSupplierBillingNo(STR_256);
		testTarget.setFfmPaymentProdactName(STR_256);
		testTarget.setFfmSalesType(STR_256);
		testTarget.setFfmSalesDiscountType(STR_256);
		testTarget.setFfmSalesTradeDate(STR_256);
		testTarget.setFfmClientCd(STR_256);
		testTarget.setFfmSalesLocationType(STR_256);
		testTarget.setFfmSalesLocationCd(STR_256);
		testTarget.setFfmSalesEmpType(STR_256);
		testTarget.setFfmSalesEmpCd(STR_256);
		testTarget.setFfmDiscntNo(STR_256);
		testTarget.setFfmSlipNo(STR_256);
		testTarget.setFfmContractType(STR_256);
		testTarget.setFfmDistType(STR_256);
		testTarget.setFfmUserSalesTaxType(STR_256);
		testTarget.setFfmUserSalesTaxRate(STR_256);
		testTarget.setFfmRjSalesTaxType(STR_256);
		testTarget.setFfmRjSalesTaxRate(STR_256);
		testTarget.setFfmRjPurchaseTaxType(STR_256);
		testTarget.setFfmRjPurchaseTaxRate(STR_256);
		testTarget.setFfmShopSalesTaxType(STR_256);
		testTarget.setFfmShopSalesTaxRate(STR_256);
		testTarget.setFfmRCostTaxType(STR_256);
		testTarget.setFfmRCostTaxRate(STR_256);
		testTarget.setFfmCommissionTaxType(STR_256);
		testTarget.setFfmCommissionTaxRate(STR_256);
		testTarget.setFfmBillDetailCd(STR_256);
		testTarget.setFfmBillOutputFlg(STR_256);
		testTarget.setFfmBillOutputPtn(STR_256);
		testTarget.setFfmBillOutputFmt(STR_256);
		testTarget.setFfmBillOutputSystem(STR_256);
		testTarget.setFfmProdactPtnNo(STR_256);
		testTarget.setFfmProdactNameForBill(STR_256);
		testTarget.setFfmMessageForBiz(STR_256);
		testTarget.setFfmRemarkForBill(STR_256);
		testTarget.setFfmRBillingPeriodStart(STR_256);
		testTarget.setFfmRBillingPeriodEnd(STR_256);
		testTarget.setFfmBillingYm(STR_256);
		testTarget.setFfmCounter(STR_256);
		testTarget.setFfmOutputComment1(STR_256);
		testTarget.setFfmOutputComment2(STR_256);
		testTarget.setFfmInstalltionName(STR_256);
		testTarget.setFfmInstalltionDptName(STR_256);
		testTarget.setFfmRingsDstCd(STR_256);
		testTarget.setFfmOeDstCd(STR_256);
		testTarget.setFfmDstType(STR_256);
		testTarget.setFfmDstName1(STR_256);
		testTarget.setFfmDstName2(STR_256);
		testTarget.setFfmDstClientName(STR_256);
		testTarget.setFfmDstAddr1(STR_256);
		testTarget.setFfmDstAddr2(STR_256);
		testTarget.setFfmDstAddr3(STR_256);
		testTarget.setFfmDstZipCd(STR_256);
		testTarget.setFfmDstTel(STR_256);
		testTarget.setFfmDstFax(STR_256);
		testTarget.setFfmDstNameKana(STR_256);
		testTarget.setFfmClientCdSec(STR_256);
		testTarget.setFfmDstCdSec(STR_256);
		testTarget.setFfmQuotationCd(STR_256);
		testTarget.setFfmQuotationDetailCd(STR_256);
		testTarget.setFfmMainQuotationDetailCd(STR_256);
		testTarget.setChgBillingText(STR_256);
		testTarget.setChgRCompanyCode1st(STR_256);
		testTarget.setChgRCompanyName1st(STR_256);
		testTarget.setChgShopId1st(STR_256);
		testTarget.setChgShopName1st(STR_256);
		testTarget.setChgShopText1st(STR_256);
		testTarget.setChgRCompanyCode2st(STR_256);
		testTarget.setChgRCompanyName2st(STR_256);
		testTarget.setChgShopId2st(STR_256);
		testTarget.setChgShopName2st(STR_256);
		testTarget.setChgShopText2st(STR_256);
		testTarget.setCubicFmtType(STR_256);
		testTarget.setCubicAccountingCd(STR_256);
		testTarget.setCubicLcType(STR_256);
		testTarget.setCubicSystemCd(STR_256);
		testTarget.setCubicCompanyCd(STR_256);
		testTarget.setCubicVoucherDate(STR_256);
		testTarget.setCubicVoucherDetailDate(STR_256);
		testTarget.setCubicAccountDeptCd(STR_256);
		testTarget.setCubicProductAxis(STR_256);
		testTarget.setCubicSalesAxis(STR_256);
		testTarget.setCubicFinancialIdentifier(STR_256);
		testTarget.setCubicProductTypeCd(STR_256);
		testTarget.setCubicInDecReason(STR_256);
		testTarget.setCubicEnvAccountCd(STR_256);
		testTarget.setCubicProjectCd(STR_256);
		testTarget.setCubicCurrencyCd(STR_256);
		testTarget.setCubicCurrencyConvType(STR_256);
		testTarget.setCubicText(STR_256);
		testTarget.setCubicTextDetail(STR_256);
		testTarget.setCubicCoSegment(STR_256);
		testTarget.setCubicCoSegment1(STR_256);
		testTarget.setCubicCoSegment2(STR_256);
		testTarget.setCubicDeleteKey(STR_256);
		testTarget.setCubicOperatorCd(STR_256);
		testTarget.setCubicGreenBuyCd(STR_256);
		testTarget.setCubicProjectNo(STR_256);
		testTarget.setCubicDfNo(STR_256);
		testTarget.setCubicBudgetNo(STR_256);
		testTarget.setCubicClientCd(STR_256);
		testTarget.setCubicCoMgtSegment(STR_256);
		testTarget.setCubicBillDstSiteCd(STR_256);
		testTarget.setCubicDomesticForeignType(STR_256);
		testTarget.setCubicRecoveryReqName(STR_256);
		testTarget.setCubicRecoveryMethodName(STR_256);
		testTarget.setCubicBillingTypeName(STR_256);
		testTarget.setCubicBillDetailTypeCode(STR_256);
		testTarget.setCubicDiscountName(STR_256);
		testTarget.setCubicBillOutputType(STR_256);
		testTarget.setCubicBillingNo(STR_256);
		testTarget.setCubicDocumentaryBillNo(STR_256);
		testTarget.setCubicPcKeyNo(STR_256);
		testTarget.setCubicBillingOutputNo(STR_256);
		testTarget.setCubicReceivedOrderNo(STR_256);
		testTarget.setCubicOrderNo(STR_256);
		testTarget.setCubicBeforeManageNo(STR_256);
		testTarget.setCubicAddTerm(STR_256);
		testTarget.setCubicContractNo(STR_256);
		testTarget.setCubicProdactName(STR_256);
		testTarget.setCubicMatchingKey(STR_256);
		testTarget.setCubicGeneralTransferData(STR_256);
		testTarget.setCubicOrgSlipNoForRed(STR_256);
		testTarget.setCubicOrgSlipDetailNoForRed(STR_256);
		testTarget.setCubicDstSiteCd(STR_256);
		testTarget.setCubicItem1(STR_256);
		testTarget.setCubicItem2(STR_256);
		testTarget.setCubicItem3(STR_256);

		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 175);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));

		// 異常系（@Max)
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setFfmFlg(10);
		testTarget.setCubicFlg(10);
		testTarget.setFfmTotalBillingCount(100);
		testTarget.setFfmUserSalesCnt(100000);
		testTarget.setFfmRjSalesCnt(100000);
		testTarget.setFfmRjPurchaseCnt(100000);
		testTarget.setFfmShopSalesCnt(100000);
		testTarget.setFfmRCostCnt(100000);
		testTarget.setFfmCommissionCnt(100000);
		testTarget.setFfmThisBillingCnt(100000);
		testTarget.setFfmForcedFlg(10);
		testTarget.setCubicCount(100000);

		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 12);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));

		// 異常系（@DecimalMax）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setFfmContractPrice(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setFfmPriceBeforeInvoice(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setFfmTaxPriceBeforeInvoice(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setFfmRevenueCostprice(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setFfmTrnsPrice(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setFfmUserSalesPrice(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setFfmUserSalesPriceInTax(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setFfmUserSalesAmt(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setFfmUserSalesAmtInTax(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setFfmUserSalesTaxPrice(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setFfmRjSalesPrice(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setFfmRjSalesPriceInTax(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setFfmRjSalesAmt(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setFfmRjSalesAmtInTax(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setFfmRjSalesTaxPrice(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setFfmRjPurchasePrice(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setFfmRjPurchasePriceInTax(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setFfmRjPurchaseAmt(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setFfmRjPurchaseAmtInTax(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setFfmRjPurchaseTaxPrice(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setFfmShopSalesPrice(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setFfmShopSalesPriceInTax(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setFfmShopSalesAmt(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setFfmShopSalesAmtInTax(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setFfmShopSalesTaxPrice(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setFfmRCostPrice(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setFfmRCostPriceInTax(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setFfmRCostAmt(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setFfmRCostAmtInTax(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setFfmRCostTaxPrice(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setFfmCommissionPrice(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setFfmCommissionPriceInTax(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setFfmCommissionAmt(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setFfmCommissionAmtInTax(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setFfmCommissionTaxPrice(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setFfmInterestExpensePrice(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setFfmInterestIncomePrice(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setCubicAmount(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setCubicAmountForForeign(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setCubicCurrencyConvRate(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setCubicSalesPriceNoTax(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setCubicSalesPriceForeign(BigDecimal.valueOf(10000000000000000000.99));
		testTarget.setCubicBeforeCancelAmt(BigDecimal.valueOf(10000000000000000000.99));

		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 43);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));

	}

}
