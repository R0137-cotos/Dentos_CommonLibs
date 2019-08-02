package jp.co.ricoh.cotos.commonlib.check;

import java.util.ArrayList;
import java.util.List;

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
import jp.co.ricoh.cotos.commonlib.dto.parameter.communication.CommunicationRegisterParameter;
import jp.co.ricoh.cotos.commonlib.dto.parameter.communication.ContactDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.communication.ContactRegisterParameter;
import jp.co.ricoh.cotos.commonlib.dto.parameter.communication.ContactToDto;
import jp.co.ricoh.cotos.commonlib.entity.communication.Communication;
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
 * TestContactDtoのテストクラス
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestCommunicationDto {

	private static final String STR_256 = "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345";
	private static final int INT_MINUS_1 = -1;

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
	public void ContactDtoのテスト() throws Exception {
		Contact entity = contactRepository.findOne(1L);
		ContactDto testTarget = new ContactDto();
		BeanUtils.copyProperties(testTarget, entity);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNullの null チェック：contactToList contactFromEmpId）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setContactToList(null);
		testTarget.setContactFromEmpId(null);
		testTarget.setTitle(null);
		testTarget.setContent(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 4);
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
		ContactToDto contactToDto = new ContactToDto();
		BeanUtils.copyProperties(contactToDto, entity.getContactToList().get(0));
		testTarget.getContactToList().set(0, contactToDto);
		testTarget.getContactToList().get(0).setContactToEmpId(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 1);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "宛先MoM社員IDが設定されていません。"));
	}

	@Test
	public void ContactToDtoのテスト() throws Exception {
		ContactTo entity = contactToRepository.findOne(1L);
		ContactToDto testTarget = new ContactToDto();
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

	@Test
	public void CommunicationRegisterParameterのテスト() throws Exception {
		Communication entity = communicationRepository.findOne(1L);
		CommunicationRegisterParameter dto = new CommunicationRegisterParameter();
		dto.setCommunication(entity);
		List<String> dummy_list = new ArrayList<String>();
		dto.setMomEmpList(dummy_list);
		dto.setMailSubjectRepalceValueList(dummy_list);
		dto.setMailTextRepalceValueList(dummy_list);

		CommunicationRegisterParameter testTarget = new CommunicationRegisterParameter();
		BeanUtils.copyProperties(testTarget, dto);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNullの null チェック：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setCommunication(null);
		testTarget.setMailSubjectRepalceValueList(null);
		testTarget.setMailTextRepalceValueList(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 3);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "コミュニケーションエンティティが設定されていません。"));

	}

	@Test
	public void ContactRegisterParameterのテスト() throws Exception {
		Contact entity = contactRepository.findOne(1L);
		ContactRegisterParameter dto = new ContactRegisterParameter();
		ContactDto contactDto = new ContactDto();
		BeanUtils.copyProperties(contactDto, entity);
		dto.setContact(contactDto);
		dto.setParentContact(contactDto);
		List<String> dummy_list = new ArrayList<String>();
		dto.setMailSubjectRepalceValueList(dummy_list);
		dto.setMailTextRepalceValueList(dummy_list);

		ContactRegisterParameter testTarget = new ContactRegisterParameter();
		BeanUtils.copyProperties(testTarget, dto);

		// 正常系
		ParamterCheckResult result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		testTool.assertValidationOk(result);

		// 異常系（@NotNullの null チェック：）
		BeanUtils.copyProperties(testTarget, entity);
		testTarget.setMailSubjectRepalceValueList(null);
		testTarget.setMailTextRepalceValueList(null);
		result = testSecurityController.callParameterCheck(testTarget, headersProperties, localServerPort);
		Assert.assertTrue(result.getErrorInfoList().size() == 2);
		Assert.assertTrue(testTool.errorIdMatchesAll(result.getErrorInfoList(), ParameterErrorIds.ROT00013));
		Assert.assertTrue(testTool.errorMessageMatchesOne(result.getErrorInfoList(), "メール本文置換リストが設定されていません。"));

	}
}
