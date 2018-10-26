package jp.co.ricoh.cotos.commonlib.check;

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
import jp.co.ricoh.cotos.commonlib.entity.communication.Communication;
import jp.co.ricoh.cotos.commonlib.entity.communication.CommunicationHistory;
import jp.co.ricoh.cotos.commonlib.entity.communication.Contact;
import jp.co.ricoh.cotos.commonlib.entity.communication.ContactTo;
import jp.co.ricoh.cotos.commonlib.repository.communication.CommunicationHistoryRepository;
import jp.co.ricoh.cotos.commonlib.repository.communication.CommunicationRepository;
import jp.co.ricoh.cotos.commonlib.repository.communication.ContactRepository;
import jp.co.ricoh.cotos.commonlib.repository.communication.ContactToRepository;
import jp.co.ricoh.cotos.commonlib.security.TestSecurityController;
import jp.co.ricoh.cotos.commonlib.security.bean.ParamterCheckResult;
import jp.co.ricoh.cotos.commonlib.util.HeadersProperties;

/**
 * パラメータチェック（手配ドメイン）のテストクラス
 *
 * @author kentaro.kakuhana
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestCommunication {

	private static final String STR_256 = "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345";
	private static final String STR_1025 = "01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234";

	static ConfigurableApplicationContext context;

	@Autowired
	HeadersProperties headersProperties;

	@Autowired
	CommunicationHistoryRepository communicationHistoryRepository;

	@Autowired
	CommunicationRepository communicationRepository;

	@Autowired
	ContactRepository contactRepository;

	@Autowired
	ContactToRepository contactToRepository;

	@Autowired
	TestTools testTool;

	@Autowired
	public void injectContext(ConfigurableApplicationContext injectContext) {
		context = injectContext;
		context.getBean(DBConfig.class).clearData();
		context.getBean(DBConfig.class).initTargetTestData("repository/communication.sql");
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
	public void CommunicationHistoryのテスト() throws Exception {
		CommunicationHistory entity = communicationHistoryRepository.findOne(1L);
		CommunicationHistory testTarget = new CommunicationHistory();
		BeanUtils.copyProperties(testTarget, entity);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：communicatedAt serviceCategory
		// processCategory targetDocKey workflowType approvalTargetType targetDocUrl
		// requestOriginId requestFromId requestToId targetDocNumber customerName
		// productName title）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setCommunicatedAt(null);
		testTarget.setServiceCategory(null);
		testTarget.setProcessCategory(null);
		testTarget.setTargetDocKey(null);
		testTarget.setWorkflowType(null);
		testTarget.setApprovalTargetType(null);
		testTarget.setTargetDocUrl(null);
		testTarget.setRequestOriginId(null);
		testTarget.setRequestFromId(null);
		testTarget.setRequestToId(null);
		testTarget.setTargetDocNumber(null);
		testTarget.setCustomerName(null);
		testTarget.setProductName(null);
		testTarget.setTitle(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 14);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@NotEmptyの空文字列チェック：targetDocKey targetDocUrl requestOriginId
		// requestFromId requestToId targetDocNumber customerName productName title）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setTargetDocKey("");
		testTarget.setTargetDocUrl("");
		testTarget.setRequestOriginId("");
		testTarget.setRequestFromId("");
		testTarget.setRequestToId("");
		testTarget.setTargetDocNumber("");
		testTarget.setCustomerName("");
		testTarget.setProductName("");
		testTarget.setTitle("");
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 9);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@Size(max) ：targetDocKey targetDocUrl requestOriginId requestFromId
		// requestToId requestToCandidateId targetDocNumber
		// customerName productName title communicationComment）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setTargetDocKey(STR_256);
		testTarget.setTargetDocUrl(STR_1025);
		testTarget.setRequestOriginId(STR_256);
		testTarget.setRequestFromId(STR_256);
		testTarget.setRequestToId(STR_256);
		testTarget.setRequestToCandidateId(STR_256);
		testTarget.setTargetDocNumber(STR_256);
		testTarget.setCustomerName(STR_256);
		testTarget.setProductName(STR_256);
		testTarget.setTitle(STR_256);
		testTarget.setCommunicationComment(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 11);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));

		// 異常系（@Max ： ）

		// 異常系（@DecimalMax：）
	}

	@Test
	public void Communicationのテスト() throws Exception {
		Communication entity = communicationRepository.findOne(1L);
		Communication testTarget = new Communication();
		BeanUtils.copyProperties(testTarget, entity);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：communicatedAt serviceCategory
		// processCategory targetDocKey workflowType approvalTargetType targetDocUrl
		// requestOriginId requestFromId requestToId targetDocNumber customerName
		// productName title）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setCommunicatedAt(null);
		testTarget.setServiceCategory(null);
		testTarget.setProcessCategory(null);
		testTarget.setTargetDocKey(null);
		testTarget.setWorkflowType(null);
		testTarget.setApprovalTargetType(null);
		testTarget.setTargetDocUrl(null);
		testTarget.setRequestOriginId(null);
		testTarget.setRequestFromId(null);
		testTarget.setRequestToId(null);
		testTarget.setTargetDocNumber(null);
		testTarget.setCustomerName(null);
		testTarget.setProductName(null);
		testTarget.setTitle(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 14);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@NotEmptyの空文字列チェック：targetDocKey targetDocUrl requestOriginId
		// requestFromId requestToId targetDocNumber customerName productName title）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setTargetDocKey("");
		testTarget.setTargetDocUrl("");
		testTarget.setRequestOriginId("");
		testTarget.setRequestFromId("");
		testTarget.setRequestToId("");
		testTarget.setTargetDocNumber("");
		testTarget.setCustomerName("");
		testTarget.setProductName("");
		testTarget.setTitle("");
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 9);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@Size(max) ：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setTargetDocKey(STR_256);
		testTarget.setTargetDocUrl(STR_1025);
		testTarget.setRequestOriginId(STR_256);
		testTarget.setRequestFromId(STR_256);
		testTarget.setRequestToId(STR_256);
		testTarget.setRequestToCandidateId(STR_256);
		testTarget.setTargetDocNumber(STR_256);
		testTarget.setCustomerName(STR_256);
		testTarget.setProductName(STR_256);
		testTarget.setTitle(STR_256);
		testTarget.setCommunicationComment(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 11);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));

		// 異常系（@Max ： targetDocBranchNumber）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setTargetDocBranchNumber(100);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));

		// 異常系（@DecimalMax：）
	}

	@Test
	public void Contactのテスト() throws Exception {
		Contact entity = contactRepository.findOne(1L);
		Contact testTarget = new Contact();
		BeanUtils.copyProperties(testTarget, entity);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：sendAt contactToList contactFromEmpId）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setSendAt(null);
		testTarget.setContactToList(null);
		testTarget.setContactFromEmpId(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 3);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@NotEmptyの空文字列チェック：contactFromEmpId）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setContactFromEmpId("");
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@Size(max) ：contactFromEmpId title）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setContactFromEmpId(STR_256);
		testTarget.setTitle(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));

		// 異常系（@Max ：）
		// 異常系（@DecimalMax：）
	}

	@Test
	public void ContactToのテスト() throws Exception {
		ContactTo entity = contactToRepository.findOne(1L);
		ContactTo testTarget = new ContactTo();
		BeanUtils.copyProperties(testTarget, entity);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNull、@NotEmptyの null チェック：contactToEmpId）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setContactToEmpId(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@NotEmptyの空文字列チェック：contactToEmpId）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setContactToEmpId("");
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));

		// 異常系（@Size(max) ：contactToEmpId contactToEmail）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setContactToEmpId(STR_256);
		testTarget.setContactToEmail(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));

		// 異常系（@Max ：）
		// 異常系（@DecimalMax：）
	}

}
