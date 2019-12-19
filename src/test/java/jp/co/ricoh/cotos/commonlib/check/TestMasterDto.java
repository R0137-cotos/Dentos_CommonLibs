package jp.co.ricoh.cotos.commonlib.check;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.ricoh.cotos.commonlib.TestTools;
import jp.co.ricoh.cotos.commonlib.dto.parameter.master.JsonSchemaMasterParameter;
import jp.co.ricoh.cotos.commonlib.security.TestSecurityController;
import jp.co.ricoh.cotos.commonlib.security.bean.ParamterCheckResult;
import jp.co.ricoh.cotos.commonlib.util.HeadersProperties;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestMasterDto {

	private static final String STR_256 = "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345";

	@Autowired
	HeadersProperties headersProperties;

	@Autowired
	TestSecurityController testSecurityController;

	@Autowired
	TestTools testTool;

	@LocalServerPort
	private int localServerPort;

	@Test
	public void JsonSchemaMasterParameterのテスト() {

		JsonSchemaMasterParameter testTarget = new JsonSchemaMasterParameter();

		// 正常系
		testTarget.setProductMasterId(1L);
		testTarget.setEstimationType("1");
		testTarget.setContractType("2");
		testTarget.setLifecycleStatus("1");

		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系
		testTarget.setEstimationType(STR_256);
		testTarget.setContractType(STR_256);
		testTarget.setLifecycleStatus(STR_256);

		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 3);
	}
}