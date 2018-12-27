package jp.co.ricoh.cotos.commonlib.mail;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.mail.MessagingException;
import javax.transaction.Transactional;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.ricoh.cotos.commonlib.DBConfig;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.ProcessCategory;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.ServiceCategory;
import jp.co.ricoh.cotos.commonlib.logic.mail.CommonSendMail;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestSendMail {

	@Autowired
	CommonSendMail commonSendMail;

	static ConfigurableApplicationContext context;

	@Autowired
	public void injectContext(ConfigurableApplicationContext injectContext) {
		context = injectContext;
		context.getBean(DBConfig.class).clearData();
	}

	@AfterClass
	public static void stopAPServer() throws InterruptedException {
		if (null != context) {
			context.getBean(DBConfig.class).clearData();
			context.stop();
		}
	}

	@Test
	public void メール送信_商品グループマスタID未指定() throws MessagingException {
		テストデータ作成();

		List<String> emailToList = 送信先TOメールアドレスリスト作成();
		List<String> emailCcList = 送信先CCメールアドレスリスト作成();
		List<String> mailSubjectRepalceValueList = メール件名置換リスト作成();
		List<String> mailTextRepalceValueList = メール本文置換リスト作成();
		try {
			commonSendMail.findMailTemplateMasterAndSendMail(ServiceCategory.見積, ProcessCategory.承認依頼.toString(), null, emailToList, emailCcList, mailSubjectRepalceValueList, mailTextRepalceValueList, null);
		} catch (Exception e) {
			Assert.fail("異常終了");
		}
	}

	@Test
	public void メール送信_商品グループマスタID指定() throws MessagingException {
		テストデータ作成();

		List<String> emailToList = 送信先TOメールアドレスリスト作成();
		List<String> emailCcList = 送信先CCメールアドレスリスト作成();
		List<String> mailSubjectRepalceValueList = メール件名置換リスト作成();
		List<String> mailTextRepalceValueList = メール本文置換リスト作成();
		try {
			commonSendMail.findMailTemplateMasterAndSendMail(ServiceCategory.見積, ProcessCategory.承認依頼.toString(), 1L, emailToList, emailCcList, mailSubjectRepalceValueList, mailTextRepalceValueList, null);
		} catch (Exception e) {
			Assert.fail("異常終了");
		}
	}

	@Test
	public void メール送信API呼び出し() throws MessagingException {
		テストデータ作成();

		List<String> emailToList = 送信先TOメールアドレスリスト作成();
		List<String> emailCcList = 送信先CCメールアドレスリスト作成();
		List<String> mailSubjectRepalceValueList = メール件名置換リスト作成();
		List<String> mailTextRepalceValueList = メール本文置換リスト作成();
		try {
			commonSendMail.findMailTemplateMasterAndSendMail(2L, emailToList, emailCcList, mailSubjectRepalceValueList, mailTextRepalceValueList, null);
		} catch (Exception e) {
			Assert.fail("異常終了");
		}
	}

	@Test
	@Transactional
	public void メール送信添付ファイル() throws MessagingException {
		テストデータ作成();

		List<String> emailToList = 送信先TOメールアドレスリスト作成();
		List<String> emailCcList = 送信先CCメールアドレスリスト作成();
		List<String> mailSubjectRepalceValueList = メール件名置換リスト作成();
		List<String> mailTextRepalceValueList = メール本文置換リスト作成();
		String path = new File(".").getAbsoluteFile().getParent();
		String uploadFile = path + "/src/test/resources/dummyFile/10130102146_201712.zip";
		try {
			commonSendMail.findMailTemplateMasterAndSendMail(3L, emailToList, emailCcList, mailSubjectRepalceValueList, mailTextRepalceValueList, uploadFile);
		} catch (Exception e) {
			Assert.fail("異常終了");
		}
	}

	@Test
	public void メール送信置換リストNull値あり() throws MessagingException {
		テストデータ作成();

		List<String> emailToList = 送信先TOメールアドレスリスト作成();
		List<String> emailCcList = 送信先CCメールアドレスリスト作成();
		List<String> mailSubjectRepalceValueList = メール件名置換リスト作成Null値あり();
		List<String> mailTextRepalceValueList = メール本文置換リスト作成Null値あり();
		try {
			commonSendMail.findMailTemplateMasterAndSendMail(ServiceCategory.見積, ProcessCategory.承認依頼.toString(), 0L, emailToList, emailCcList, mailSubjectRepalceValueList, mailTextRepalceValueList, null);
		} catch (Exception e) {
			Assert.fail("異常終了");
		}
	}

	@Test
	public void メール送信環境依存文字あり() throws MessagingException {
		テストデータ作成();

		List<String> emailToList = 送信先TOメールアドレスリスト作成();
		List<String> emailCcList = 送信先CCメールアドレスリスト作成();
		List<String> mailSubjectRepalceValueList = メール件名置換リスト作成();
		List<String> mailTextRepalceValueList = メール本文置換リスト作成();
		try {
			commonSendMail.findMailTemplateMasterAndSendMail(10L, emailToList, emailCcList, mailSubjectRepalceValueList, mailTextRepalceValueList, null);
		} catch (Exception e) {
			Assert.fail("異常終了");
		}
	}

	private void テストデータ作成() {
		context.getBean(DBConfig.class).initTargetTestData("sql/mail/testProductGrpMasterInsert.sql");
		context.getBean(DBConfig.class).initTargetTestData("sql/mail/testMailTemplateMasterInset.sql");
	}

	private List<String> 送信先TOメールアドレスリスト作成() {
		return IntStream.rangeClosed(1, 2).mapToObj(i -> "send_mail_to" + i + "@softcomm.co.jp").collect(Collectors.toList());
	}

	private List<String> 送信先CCメールアドレスリスト作成() {
		return IntStream.rangeClosed(1, 2).mapToObj(i -> "send_mail_cc" + i + "@softcomm.co.jp").collect(Collectors.toList());
	}

	private List<String> メール件名置換リスト作成() {
		return IntStream.rangeClosed(1, 6).mapToObj(i -> "test_subject" + i).collect(Collectors.toList());
	}

	private List<String> メール本文置換リスト作成() {
		return IntStream.rangeClosed(1, 11).mapToObj(i -> "test_text" + i).collect(Collectors.toList());
	}

	private List<String> メール件名置換リスト作成Null値あり() {
		List<String> list = IntStream.rangeClosed(2, 3).mapToObj(i -> "test_subject" + i).collect(Collectors.toList());
		list.add(0, null);
		return list;
	}

	private List<String> メール本文置換リスト作成Null値あり() {
		List<String> list = IntStream.rangeClosed(2, 3).mapToObj(i -> "test_text" + i).collect(Collectors.toList());
		list.add(0, null);
		return list;
	}
}
