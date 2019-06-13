package jp.co.ricoh.cotos.commonlib.util;

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
import jp.co.ricoh.cotos.commonlib.repository.contract.ContractRepository;
import jp.co.ricoh.cotos.commonlib.security.bean.ParamterCheckResult;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestCheckEntityWithEntityName {
	private static final int INT_100 = 100;

	static ConfigurableApplicationContext context;

	@Autowired
	HeadersProperties headersProperties;

	@Autowired
	TestTools testTool;

	@Autowired
	ContractRepository contractRepository;

	@Autowired
	public void injectContext(ConfigurableApplicationContext injectContext) {
		context = injectContext;
		context.getBean(DBConfig.class).clearData();
		context.getBean(DBConfig.class).initTargetTestData("repository/attachedFile.sql");
		context.getBean(DBConfig.class).initTargetTestData("repository/contract.sql");
	}

	@Autowired
	TestUtilController testUtilController;

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
	public void エラーなし() throws Exception {
		Contract entity = contractRepository.findOne(4L);
		Contract testTarget = new Contract();

		BeanUtils.copyProperties(testTarget, entity);
		ParamterCheckResult result = testUtilController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

	}

	@Test
	public void 契約種別() throws Exception {
		Contract entity = contractRepository.findOne(4L);
		Contract testTarget = new Contract();

		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setContractType(null);
		ParamterCheckResult result = testUtilController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "契約種別が設定されていません。"));

	}

	@Test
	public void 見積番号枝番() throws Exception {
		Contract entity = contractRepository.findOne(4L);
		Contract testTarget = new Contract();

		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setOriginContractBranchNumber(INT_100);
		testTarget.setEstimationBranchNumber(INT_100);
		ParamterCheckResult result = testUtilController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "見積番号枝番は最大値（99）を超えています。"));
	}

	@Test
	public void 契約明細_状態() throws Exception {
		Contract entity = contractRepository.findOne(4L);
		Contract testTarget = new Contract();

		BeanUtils.copyProperties(testTarget, entity);
		testTarget.getContractDetailList().get(0).setState(null);
		ParamterCheckResult result = testUtilController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "契約明細の状態が設定されていません。"));
	}

	@Test
	public void 契約担当SA社員_MoM社員ID() throws Exception {
		Contract entity = contractRepository.findOne(4L);
		Contract testTarget = new Contract();

		BeanUtils.copyProperties(testTarget, entity);
		testTarget.getContractPicSaEmp().setMomEmployeeId(null);
		ParamterCheckResult result = testUtilController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "契約担当SA社員のMoM社員IDが設定されていません。"));
	}

	@Test
	public void 品種_品種区分() throws Exception {
		Contract entity = contractRepository.findOne(4L);
		Contract testTarget = new Contract();

		BeanUtils.copyProperties(testTarget, entity);
		testTarget.getContractDetailList().get(0).getItemContract().setItemType(null);

		ParamterCheckResult result = testUtilController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "品種(契約用)の品種区分が設定されていません。"));
	}

	@Test
	public void 契約機種() throws Exception {
		Contract entity = contractRepository.findOne(4L);
		Contract testTarget = new Contract();

		BeanUtils.copyProperties(testTarget, entity);
		testTarget.getContractEquipmentList().get(0).setEquipmentCode(null);

		ParamterCheckResult result = testUtilController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "契約機種の機種コードが設定されていません。"));
	}

}
