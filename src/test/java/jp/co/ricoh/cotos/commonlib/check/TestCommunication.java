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
	private static final String STR_1001 = "01234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890";
	private static final int INT_MINUS_1 = -1;
	private static final int INT_100 = 100;

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

		// 異常系（@NotNullの null チェック：serviceCategory
		// processCategory targetDocKey workflowType approvalTargetType targetDocUrl
		// requestOriginId requestFromId requestToId targetDocNumber customerName）
		BeanUtils.copyProperties(testTarget, entity);
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
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 11);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "サービスカテゴリが設定されていません。"));

		// 異常系（@Size(max) ：targetDocKey targetDocUrl requestOriginId requestFromId
		// requestToId requestToCandidateId targetDocNumber
		// customerName productName title communicationComment）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setTargetDocKey(STR_256);
		testTarget.setTargetDocUrl(STR_1001);
		testTarget.setRequestOriginId(STR_256);
		testTarget.setRequestFromId(STR_256);
		testTarget.setRequestToId(STR_256);
		testTarget.setRequestToCandidateId(STR_256);
		testTarget.setTargetDocNumber(STR_256);
		testTarget.setCustomerName(STR_256);
		testTarget.setProductGrpName(STR_256);
		testTarget.setTitle(STR_256);
		testTarget.setCommunicationComment(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 11);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "対象文書キーは最大文字数（255）を超えています。"));

		// 異常系（@Max ：targetDocBranchNumber）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setTargetDocBranchNumber(INT_100);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "対象文書番号枝番は最大値（99）を超えています。"));

		// 異常系（@Min ：targetDocBranchNumber）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setTargetDocBranchNumber(INT_MINUS_1);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00027));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "対象文書番号枝番は最小値（0）を下回っています。"));

	}

	@Test
	public void Communicationのテスト() throws Exception {
		Communication entity = communicationRepository.findOne(1L);
		Communication testTarget = new Communication();
		BeanUtils.copyProperties(testTarget, entity);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNullの null チェック：serviceCategory
		// processCategory targetDocKey workflowType approvalTargetType targetDocUrl
		// requestOriginId requestFromId requestToId targetDocNumber customerName）
		BeanUtils.copyProperties(testTarget, entity);
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
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 11);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "サービスカテゴリが設定されていません。"));

		// 異常系（@Size(max) ：targetDocKey targetDocUrl requestOriginId requestFromId
		// requestToId requestToCandidateId targetDocNumber
		// customerName productName title communicationComment）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setTargetDocKey(STR_256);
		testTarget.setTargetDocUrl(STR_1001);
		testTarget.setRequestOriginId(STR_256);
		testTarget.setRequestFromId(STR_256);
		testTarget.setRequestToId(STR_256);
		testTarget.setRequestToCandidateId(STR_256);
		testTarget.setTargetDocNumber(STR_256);
		testTarget.setCustomerName(STR_256);
		testTarget.setProductGrpName(STR_256);
		testTarget.setTitle(STR_256);
		testTarget.setCommunicationComment(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 11);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "対象文書キーは最大文字数（255）を超えています。"));

		// 異常系（@Max ：targetDocBranchNumber）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setTargetDocBranchNumber(INT_100);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00015));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "対象文書番号枝番は最大値（99）を超えています。"));

		// 異常系（@Min ：targetDocBranchNumber）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setTargetDocBranchNumber(INT_MINUS_1);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00027));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "対象文書番号枝番は最小値（0）を下回っています。"));

	}

	@Test
	public void Contactのテスト() throws Exception {
		Contact entity = contactRepository.findOne(1L);
		Contact testTarget = new Contact();
		BeanUtils.copyProperties(testTarget, entity);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNullの null チェック：contactToList contactFromEmpId）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setContactToList(null);
		testTarget.setContactFromEmpId(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "送信者MoM社員IDが設定されていません。"));

		// 異常系（@Size(max) ：contactFromEmpId title contactFromEmpName）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setContactFromEmpId(STR_256);
		testTarget.setTitle(STR_256);
		testTarget.setContactFromEmpName(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 3);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "送信者MoM社員IDは最大文字数（255）を超えています。"));

		// 異常系（@Min ：estimationId）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setEstimationId(INT_MINUS_1);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00027));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "見積IDは最小値（0）を下回っています。"));

		// 異常系（@Valid ：問い合わせ宛先）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.getContactToList().get(0).setContactToEmpId(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "宛先MoM社員IDが設定されていません。"));
	}

	@Test
	public void ContactToのテスト() throws Exception {
		ContactTo entity = contactToRepository.findOne(1L);
		ContactTo testTarget = new ContactTo();
		BeanUtils.copyProperties(testTarget, entity);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNullの null チェック：contactToEmpId）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setContactToEmpId(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "宛先MoM社員IDが設定されていません。"));

		// 異常系（@Size(max) ：contactToEmpId contactToEmail）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setContactToEmpId(STR_256);
		testTarget.setContactToEmail(STR_256);
		testTarget.setContactToEmpName(STR_256);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 3);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00014));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "宛先MoM社員IDは最大文字数（255）を超えています。"));

	}

}
