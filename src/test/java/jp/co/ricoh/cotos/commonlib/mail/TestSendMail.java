package jp.co.ricoh.cotos.commonlib.mail;

import java.io.File;
import java.util.HashMap;
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
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.ricoh.cotos.commonlib.db.DBUtil;
import jp.co.ricoh.cotos.commonlib.entity.communication.SendMailHistory;
import jp.co.ricoh.cotos.commonlib.entity.master.MailTemplateMaster.CommunicationCategory;
import jp.co.ricoh.cotos.commonlib.entity.master.MailTemplateMaster.ServiceCategory;
import jp.co.ricoh.cotos.commonlib.logic.mail.SendMail;
import jp.co.ricoh.cotos.commonlib.repository.communication.SendMailHistoryRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSendMail {

	@Autowired
	DBUtil dbUtil;
	@Autowired
	SendMail sendMail;

	static ConfigurableApplicationContext context;

	@Autowired
	public void injectContext(ConfigurableApplicationContext injectContext) {
		context = injectContext;
	}

	@AfterClass
	public static void stopAPServer() throws InterruptedException {
		if (null != context) {
			context.getBean(DBConfig.class).clearData();
			context.stop();
		}
	}

	private static boolean isH2() {
		return "org.h2.Driver".equals(context.getEnvironment().getProperty("spring.datasource.driverClassName"));
	}

	@Test
	@Transactional
	public void メール送信コミュニケーション呼び出し() throws MessagingException {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		テストデータ作成();

		List<String> emailToList = 送信先TOメールアドレスリスト作成();
		List<String> emailCcList = 送信先CCメールアドレスリスト作成();
		List<String> mailSubjectRepalceValueList = メール件名置換リスト作成();
		List<String> mailTextRepalceValueList = メール本文置換リスト作成();
		sendMail.findMailTemplateMasterAndSendMail(ServiceCategory.見積, CommunicationCategory.承認依頼, emailToList, emailCcList, mailSubjectRepalceValueList, mailTextRepalceValueList, null);

		メール送信履歴確認(false);
	}

	@Test
	@Transactional
	public void メール送信API呼び出し() throws MessagingException {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		テストデータ作成();

		List<String> emailToList = 送信先TOメールアドレスリスト作成();
		List<String> emailCcList = 送信先CCメールアドレスリスト作成();
		List<String> mailSubjectRepalceValueList = メール件名置換リスト作成();
		List<String> mailTextRepalceValueList = メール本文置換リスト作成();
		sendMail.findMailTemplateMasterAndSendMail(2L, emailToList, emailCcList, mailSubjectRepalceValueList, mailTextRepalceValueList, null);

		メール送信履歴確認(true);
	}

	@Test
	@Transactional
	public void メール送信添付ファイル() throws MessagingException {

		// h2以外ならスルー
		if (!isH2()) {
			return;
		}

		テストデータ作成();

		List<String> emailToList = 送信先TOメールアドレスリスト作成();
		List<String> emailCcList = 送信先CCメールアドレスリスト作成();
		List<String> mailSubjectRepalceValueList = メール件名置換リスト作成();
		List<String> mailTextRepalceValueList = メール本文置換リスト作成();
		String path = new File(".").getAbsoluteFile().getParent();
		String uploadFile = path + "/src/test/resources/dummyFile/10130102146_201712.zip";
		sendMail.findMailTemplateMasterAndSendMail(3L, emailToList, emailCcList, mailSubjectRepalceValueList, mailTextRepalceValueList, uploadFile);

		メール送信履歴確認添付ファイルあり();
	}

	private void テストデータ作成() {
		dbUtil.execute("sql/mail/testProductInsert.sql", new HashMap<>());
		dbUtil.execute("sql/mail/testMailTemplateMasterInset.sql", new HashMap<>());
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

	/**
	 * メール送信履歴確認
	 * 
	 * @param isApiExce
	 *            API実行かどうか
	 */
	private void メール送信履歴確認(boolean isApiExce) {
		SendMailHistoryRepository sendMailHistoryRepository = context.getBean(SendMailHistoryRepository.class);
		SendMailHistory sendMailHistory = sendMailHistoryRepository.findOne(!isApiExce ? 1L : 2L);
		Assert.assertEquals("メールテンプレートマスタIDが設定されていること", !isApiExce ? 1L : 2L, sendMailHistory.getMailTemplateMaster().getId());
		Assert.assertEquals("送信元メールアドレスが設定されていること", "oshirase_shindenryoku@example.com", sendMailHistory.getSendFromMailAddress());
		Assert.assertEquals("送信先TOメールアドレスが設定されていること", "send_mail_to1@softcomm.co.jp", sendMailHistory.getToFromMailAddress()[0]);
		Assert.assertEquals("送信先TOメールアドレスが設定されていること", "send_mail_to2@softcomm.co.jp", sendMailHistory.getToFromMailAddress()[1]);
		Assert.assertEquals("送信先CCメールアドレスが設定されていること", "send_mail_cc1@softcomm.co.jp", sendMailHistory.getCcFromMailAddress()[0]);
		Assert.assertEquals("送信先CCメールアドレスが設定されていること", "send_mail_cc2@softcomm.co.jp", sendMailHistory.getCcFromMailAddress()[1]);
		Assert.assertEquals("件名が設定されていること", !isApiExce ? "【test_subject1】見積承認依頼メール" : "【test_subject1】見積承認完了メール", sendMailHistory.getMailSubject());
		Assert.assertNotNull("本文が設定されていること", sendMailHistory.getMailBody());
		Assert.assertEquals("エラーフラグに「0」が設定されていること", false, sendMailHistory.isErrorFlg());
		Assert.assertNull("エラー内容が設定されていないこと", sendMailHistory.getErrorContent());
		Assert.assertEquals("再送フラグに「0」が設定されていること", false, sendMailHistory.isReForwardingFlg());
	}

	/**
	 * メール送信履歴確認添付ファイルあり
	 */
	private void メール送信履歴確認添付ファイルあり() {
		SendMailHistoryRepository sendMailHistoryRepository = context.getBean(SendMailHistoryRepository.class);
		SendMailHistory sendMailHistory = sendMailHistoryRepository.findOne(3L);
		Assert.assertEquals("メールテンプレートマスタIDが設定されていること", 3L, sendMailHistory.getMailTemplateMaster().getId());
		Assert.assertEquals("送信元メールアドレスが設定されていること", "oshirase_shindenryoku@example.com", sendMailHistory.getSendFromMailAddress());
		Assert.assertEquals("送信先TOメールアドレスが設定されていること", "send_mail_to1@softcomm.co.jp", sendMailHistory.getToFromMailAddress()[0]);
		Assert.assertEquals("送信先TOメールアドレスが設定されていること", "send_mail_to2@softcomm.co.jp", sendMailHistory.getToFromMailAddress()[1]);
		Assert.assertEquals("送信先CCメールアドレスが設定されていること", "send_mail_cc1@softcomm.co.jp", sendMailHistory.getCcFromMailAddress()[0]);
		Assert.assertEquals("送信先CCメールアドレスが設定されていること", "send_mail_cc2@softcomm.co.jp", sendMailHistory.getCcFromMailAddress()[1]);
		Assert.assertEquals("件名が設定されていること", "【test_subject1】見積承認依頼差戻メール", sendMailHistory.getMailSubject());
		Assert.assertNotNull("本文が設定されていること", sendMailHistory.getMailBody());
		Assert.assertEquals("エラーフラグに「0」が設定されていること", false, sendMailHistory.isErrorFlg());
		Assert.assertNull("エラー内容が設定されていないこと", sendMailHistory.getErrorContent());
		Assert.assertEquals("再送フラグに「0」が設定されていること", false, sendMailHistory.isReForwardingFlg());
		Assert.assertNotNull("添付ファイルが設定されていること", sendMailHistory.getAttachedFile());
	}
}
