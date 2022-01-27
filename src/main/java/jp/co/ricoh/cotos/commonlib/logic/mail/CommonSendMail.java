package jp.co.ricoh.cotos.commonlib.logic.mail;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.sun.mail.smtp.SMTPMessage;

import jp.co.ricoh.cotos.commonlib.entity.EnumType.ServiceCategory;
import jp.co.ricoh.cotos.commonlib.entity.master.MailTemplateMaster;
import jp.co.ricoh.cotos.commonlib.repository.master.MailTemplateMasterRepository;
import jp.co.ricoh.cotos.commonlib.util.AppProperties;

@Component
public class CommonSendMail {

	@Autowired
	MailTemplateMasterRepository mailTemplateMasterRepository;

	@Autowired(required = false)
	JavaMailSender javaMailSender;

	@Autowired
	AppProperties appProperties;

	/**
	 * メールテンプレートマスタ特定&メール送信処理
	 *
	 * <pre>
	 * 【処理内容】
	 * ・引数のサービスカテゴリー(見積、契約、手配etc)と処理カテゴリー(承認、承認依頼、承認依頼差戻etc)と商品グループマスタIDを元にメールテンプレートマスタTBL(MAIL_TEMPLATE_MASTER)からメールテンプレートマスタ情報を取得　　詳細は以下を参照
	 *  条件：
	 *    サービスカテゴリー(MAIL_TEMPLATE_MASTER.SERVICE_CATEGORY)=引数のサービスカテゴリー
	 *    処理カテゴリー(MAIL_TEMPLATE_MASTER.PROCESS_CATEGORY)=引数の処理カテゴリー
	 *    商品グループマスタID
	 * ・引数のメール件名置換リストとメールテンプレートマスタTBL.メール件名(MAIL_TEMPLATE_MASTER.MAIL_SUBJECT)を元にメール件名作成
	 *  例：
	 *    メールテンプレートマスタTBL.メール件名(MAIL_TEMPLATE_MASTER.MAIL_SUBJECT)
	 *     【{{replaceValue1}}】見積承認依頼メール {{replaceValue2}}
	 *    メール件名置換リスト
	 *     テスト1,テスト2,テスト3
	 *    各値が上記の場合、以下が生成されるメール件名
	 *     【テスト1】見積承認依頼メール テスト2
	 * ・引数のメール本文置換リストとメールテンプレートマスタTBL.メール本文(MAIL_TEMPLATE_MASTER.MAIL_BODY)を元にメール本文作成
	 *  ※設定できる引数の数がメール件名と異なるだけで、文字列生成方法はメール件名と同一
	 * ・引数のToメールアドレスリストとCCメールアドレスリストと上記で作成したメール件名やメール本文を使用してメール送信
	 * ・送信元メールアドレスは、メールテンプレートマスタTBL.送信元メールアドレス(MAIL_TEMPLATE_MASTER.SEND_FROM_MAIL_ADDRESS)から取得
	 * ・メールは文字コードをUTF-8で作成しており、ファイル添付も可能
	 * </pre>
	 *
	 * @param emailToList
	 *            Toメールアドレスリスト(複数設定可能)
	 * @param emailCcList
	 *            CCメールアドレスリスト(複数設定可能)
	 * @param serviceCategory
	 *            サービスカテゴリー
	 * @param processCategory
	 *            処理カテゴリー
	 * @param mailSubjectRepalceValueList
	 *            メール件名置換リスト(最大5個まで)
	 * @param mailTextRepalceValueList
	 *            メール本文置換リスト(最大10個まで)
	 * @param uploadFile
	 *            添付ファイル
	 * @throws MessagingException
	 */
	public void findMailTemplateMasterAndSendMail(ServiceCategory serviceCategory, String processCategory, Long productGrpMasterId, List<String> emailToList, List<String> emailCcList, List<String> emailBccList, List<String> mailSubjectRepalceValueList, List<String> mailTextRepalceValueList, String uploadFile) throws MessagingException {
		MailTemplateMaster mailTemplateMaster = mailTemplateMasterRepository.findByServiceCategoryAndProcessCategoryAndProductGrpMasterId(serviceCategory.toString(), processCategory, productGrpMasterId != null ? productGrpMasterId : 0L);
		sendMail(emailToList, emailCcList, emailBccList, mailTemplateMaster, mailSubjectRepalceValueList, mailTextRepalceValueList, uploadFile, null);
	}

	/**
	 * メールテンプレートマスタ特定&メール送信処理&MIMEヘッダー
	 *
	 * @see #findMailTemplateMasterAndSendMail(ServiceCategory, String, Long, List, List, List, List, List, String)
	 *
	 * @param emailToList
	 *            Toメールアドレスリスト(複数設定可能)
	 * @param emailCcList
	 *            CCメールアドレスリスト(複数設定可能)
	 * @param serviceCategory
	 *            サービスカテゴリー
	 * @param processCategory
	 *            処理カテゴリー
	 * @param mailSubjectRepalceValueList
	 *            メール件名置換リスト(最大5個まで)
	 * @param mailTextRepalceValueList
	 *            メール本文置換リスト(最大10個まで)
	 * @param uploadFile
	 *            添付ファイル
	 * @param mimeHeaderMap
	 *            MIMEヘッダーマップ
	 * @throws MessagingException
	 */
	public void findMailTemplateMasterAndSendMail(ServiceCategory serviceCategory, String processCategory, Long productGrpMasterId, List<String> emailToList, List<String> emailCcList, List<String> emailBccList, List<String> mailSubjectRepalceValueList, List<String> mailTextRepalceValueList, String uploadFile, Map<String, String> mimeHeaderMap) throws MessagingException {
		MailTemplateMaster mailTemplateMaster = mailTemplateMasterRepository.findByServiceCategoryAndProcessCategoryAndProductGrpMasterId(serviceCategory.toString(), processCategory, productGrpMasterId != null ? productGrpMasterId : 0L);
		sendMail(emailToList, emailCcList, emailBccList, mailTemplateMaster, mailSubjectRepalceValueList, mailTextRepalceValueList, uploadFile, mimeHeaderMap);
	}

	/**
	 * メールテンプレートマスタ特定&メール送信処理
	 *
	 * <pre>
	 * 【処理内容】
	 * ・引数のメールテンプレートマスタIDを元にメールテンプレートマスタTBL(MAIL_TEMPLATE_MASTER)からメールテンプレートマスタ情報を取得
	 *  条件：
	 *    メールテンプレートマスタID(MAIL_TEMPLATE_MASTER.ID)=引数のメールテンプレートマスタID
	 * ・引数のメール件名置換リストとメールテンプレートマスタTBL.メール件名(MAIL_TEMPLATE_MASTER.MAIL_SUBJECT)を元にメール件名作成
	 *  例：
	 *    メールテンプレートマスタTBL.メール件名(MAIL_TEMPLATE_MASTER.MAIL_SUBJECT)
	 *     【{{replaceValue1}}】見積承認依頼メール {{replaceValue2}}
	 *    メール件名置換リスト
	 *     テスト1,テスト2,テスト3
	 *    各値が上記の場合、以下が生成されるメール件名
	 *     【テスト1】見積承認依頼メール テスト2
	 * ・引数のメール本文置換リストとメールテンプレートマスタTBL.メール本文(MAIL_TEMPLATE_MASTER.MAIL_BODY)を元にメール本文作成
	 *  ※設定できる引数の数がメール件名と異なるだけで、文字列生成方法はメール件名と同一
	 * ・引数のToメールアドレスリストとCCメールアドレスリストと上記で作成したメール件名やメール本文を使用してメール送信
	 * ・送信元メールアドレスは、メールテンプレートマスタTBL.送信元メールアドレス(MAIL_TEMPLATE_MASTER.SEND_FROM_MAIL_ADDRESS)から取得
	 * ・メールは文字コードをUTF-8で作成しており、ファイル添付も可能
	 * </pre>
	 *
	 * @param mailTemplateMasterId
	 *            メールテンプレートマスタID
	 * @param emailToList
	 *            Toメールアドレスリスト(複数設定可能)
	 * @param emailCcList
	 *            CCメールアドレスリスト(複数設定可能)
	 * @param mailSubjectRepalceValueList
	 *            メール件名置換リスト(最大5個まで)
	 * @param mailTextRepalceValueList
	 *            メール本文置換リスト(最大10個まで)
	 * @param uploadFile
	 *            添付ファイル
	 * @throws MessagingException
	 */
	public void findMailTemplateMasterAndSendMail(long mailTemplateMasterId, List<String> emailToList, List<String> emailCcList, List<String> emailBccList, List<String> mailSubjectRepalceValueList, List<String> mailTextRepalceValueList, String uploadFile) throws MessagingException {
		MailTemplateMaster mailTemplateMaster = mailTemplateMasterRepository.findOne(mailTemplateMasterId);
		sendMail(emailToList, emailCcList, emailBccList, mailTemplateMaster, mailSubjectRepalceValueList, mailTextRepalceValueList, uploadFile, null);
	}

	/**
	 * メールテンプレートマスタ特定&メール送信処理&MIMEヘッダー
	 *
	 * @see #findMailTemplateMasterAndSendMail(long, List, List, List, List, List, String)
	 *
	 * @param mailTemplateMasterId
	 *            メールテンプレートマスタID
	 * @param emailToList
	 *            Toメールアドレスリスト(複数設定可能)
	 * @param emailCcList
	 *            CCメールアドレスリスト(複数設定可能)
	 * @param mailSubjectRepalceValueList
	 *            メール件名置換リスト(最大5個まで)
	 * @param mailTextRepalceValueList
	 *            メール本文置換リスト(最大10個まで)
	 * @param uploadFile
	 *            添付ファイル
	 * @param mimeHeaderMap
	 *            MIMEヘッダーマップ
	 * @throws MessagingException
	 */
	public void findMailTemplateMasterAndSendMail(long mailTemplateMasterId, List<String> emailToList, List<String> emailCcList, List<String> emailBccList, List<String> mailSubjectRepalceValueList, List<String> mailTextRepalceValueList, String uploadFile, Map<String, String> mimeHeaderMap) throws MessagingException {
		MailTemplateMaster mailTemplateMaster = mailTemplateMasterRepository.findOne(mailTemplateMasterId);
		sendMail(emailToList, emailCcList, emailBccList, mailTemplateMaster, mailSubjectRepalceValueList, mailTextRepalceValueList, uploadFile, mimeHeaderMap);
	}

	/**
	 * メールテンプレートマスタ特定&メール送信処理
	 *
	 * <pre>
	 * 【処理内容】
	 * ・引数のメールテンプレートマスタIDを元にメールテンプレートマスタTBL(MAIL_TEMPLATE_MASTER)からメールテンプレートマスタ情報を取得
	 *  条件：
	 *    メールテンプレートマスタID(MAIL_TEMPLATE_MASTER.ID)=引数のメールテンプレートマスタID
	 * ・引数のメール件名置換リストとメールテンプレートマスタTBL.メール件名(MAIL_TEMPLATE_MASTER.MAIL_SUBJECT)を元にメール件名作成
	 *  例：
	 *    メールテンプレートマスタTBL.メール件名(MAIL_TEMPLATE_MASTER.MAIL_SUBJECT)
	 *     【{{replaceValue1}}】見積承認依頼メール {{replaceValue2}}
	 *    メール件名置換リスト
	 *     テスト1,テスト2,テスト3
	 *    各値が上記の場合、以下が生成されるメール件名
	 *     【テスト1】見積承認依頼メール テスト2
	 * ・引数のメール本文置換リストとメールテンプレートマスタTBL.メール本文(MAIL_TEMPLATE_MASTER.MAIL_BODY)を元にメール本文作成
	 *  ※設定できる引数の数がメール件名と異なるだけで、文字列生成方法はメール件名と同一
	 * ・引数のToメールアドレスリストとCCメールアドレスリストと上記で作成したメール件名やメール本文を使用してメール送信
	 * ・送信元メールアドレスは、メールテンプレートマスタTBL.送信元メールアドレス(MAIL_TEMPLATE_MASTER.SEND_FROM_MAIL_ADDRESS)から取得
	 * ・メールは文字コードをUTF-8で作成しており、複数のファイル添付も可能
	 * </pre>
	 *
	 * @param mailTemplateMasterId
	 *            メールテンプレートマスタID
	 * @param emailToList
	 *            Toメールアドレスリスト(複数設定可能)
	 * @param emailCcList
	 *            CCメールアドレスリスト(複数設定可能)
	 * @param mailSubjectRepalceValueList
	 *            メール件名置換リスト(最大5個まで)
	 * @param mailTextRepalceValueList
	 *            メール本文置換リスト(最大10個まで)
	 * @param uploadFileList
	 *            添付ファイル(複数)
	 * @throws MessagingException
	 */
	public void findMailTemplateMasterAndSendMailAndAttachedFiles(long mailTemplateMasterId, List<String> emailToList, List<String> emailCcList, List<String> emailBccList, List<String> mailSubjectRepalceValueList, List<String> mailTextRepalceValueList, List<String> uploadFileList) throws MessagingException {
		MailTemplateMaster mailTemplateMaster = mailTemplateMasterRepository.findOne(mailTemplateMasterId);
		sendMail(emailToList, emailCcList, emailBccList, mailTemplateMaster, mailSubjectRepalceValueList, mailTextRepalceValueList, uploadFileList, null);
	}

	/**
	 * メールテンプレートマスタ特定&メール送信処理&MIMEヘッダー
	 *
	 * @see #findMailTemplateMasterAndSendMailAndAttachedFiles(long, List, List, List, List, List, List)
	 *
	 * @param mailTemplateMasterId
	 *            メールテンプレートマスタID
	 * @param emailToList
	 *            Toメールアドレスリスト(複数設定可能)
	 * @param emailCcList
	 *            CCメールアドレスリスト(複数設定可能)
	 * @param mailSubjectRepalceValueList
	 *            メール件名置換リスト(最大5個まで)
	 * @param mailTextRepalceValueList
	 *            メール本文置換リスト(最大10個まで)
	 * @param uploadFileList
	 *            添付ファイル(複数)
	 * @param mimeHeaderMap
	 *            MIMEヘッダーマップ
	 * @throws MessagingException
	 */
	public void findMailTemplateMasterAndSendMailAndAttachedFiles(long mailTemplateMasterId, List<String> emailToList, List<String> emailCcList, List<String> emailBccList, List<String> mailSubjectRepalceValueList, List<String> mailTextRepalceValueList, List<String> uploadFileList, Map<String, String> mimeHeaderMap) throws MessagingException {
		MailTemplateMaster mailTemplateMaster = mailTemplateMasterRepository.findOne(mailTemplateMasterId);
		sendMail(emailToList, emailCcList, emailBccList, mailTemplateMaster, mailSubjectRepalceValueList, mailTextRepalceValueList, uploadFileList, mimeHeaderMap);
	}

	/**
	 * メール送信処理_添付ファイルあり
	 *
	 * @param emailTo
	 *            Toメールアドレス
	 * @param emailCcList
	 *            CCメールアドレスリスト
	 * @param mailTemplateMaster
	 *            メールテンプレートマスタ
	 * @param mailSubjectRepalceValueList
	 *            メール件名置換リスト(最大5個まで)
	 * @param mailTextRepalceValueList
	 *            メール本文置換リスト(最大10個まで)
	 * @param uploadFile
	 *            添付ファイル
	 * @param mimeHeaderMap
	 *            MIMEヘッダーマップ
	 * @throws MessagingException
	 */
	@Async
	private void sendMail(List<String> emailToList, List<String> emailCcList, List<String> emailBccList, MailTemplateMaster mailTemplateMaster, List<String> mailSubjectRepalceValueList, List<String> mailTextRepalceValueList, String uploadFile, Map<String, String> mimeHeaderMap) throws MessagingException {
		MimeMessage attachedMsg = javaMailSender.createMimeMessage();
		attachedMsg.setHeader("Content-Transfer-Encoding", "base64");
		MimeMessageHelper attachedHelper = new MimeMessageHelper(attachedMsg, true, StandardCharsets.UTF_8.name());

		Writer writerMailSubject = createMailSubject(mailTemplateMaster, mailSubjectRepalceValueList);
		Writer writerMailText = createMailText(mailTemplateMaster, mailTextRepalceValueList);

		String[] toEmail = (String[]) emailToList.toArray(new String[0]);
		String[] ccEmail = (String[]) emailCcList.toArray(new String[0]);
		String[] bccEmail = (String[]) emailBccList.toArray(new String[0]);
		attachedHelper.setTo(toEmail);
		attachedHelper.setFrom(appProperties.getMailProperties().getFromMailAddress());
		attachedHelper.setCc(ccEmail);
		attachedHelper.setBcc(bccEmail);
		String subject = writerMailSubject.toString().replace("&#10;", "\r\n");
		attachedHelper.setSubject(subject);
		String text = writerMailText.toString().replace("&#10;", "\r\n");
		attachedHelper.setText(text);

		if (null != uploadFile) {
			FileSystemResource res = new FileSystemResource(uploadFile);
			attachedHelper.addAttachment(res.getFilename(), res);
		}
		SMTPMessage SMTPMessage = new SMTPMessage(attachedMsg);
		SMTPMessage.setEnvelopeFrom(Optional.ofNullable(mailTemplateMaster.getEnvelopeFrom()).orElse(appProperties.getMailProperties().getEnvelopeFromMailAddress()));
		// MIMEヘッダー追加
		if (MapUtils.isNotEmpty(mimeHeaderMap)) {
			for (Entry<String, String> entry : mimeHeaderMap.entrySet()) {
				if (!StringUtils.isEmpty(entry.getValue())) {
					SMTPMessage.setHeader(entry.getKey(), entry.getValue());
				}
			}
		}
		javaMailSender.send(SMTPMessage);
	}

	/**
	* メール送信処理_複数添付ファイルあり
	*
	* @param emailTo
	*            Toメールアドレス
	* @param emailCcList
	*            CCメールアドレスリスト
	* @param mailTemplateMaster
	*            メールテンプレートマスタ
	* @param mailSubjectRepalceValueList
	*            メール件名置換リスト(最大5個まで)
	* @param mailTextRepalceValueList
	*            メール本文置換リスト(最大10個まで)
	* @param uploadFileList
	*            添付ファイル(複数)
	* @param mimeHeaderMap
	*            MIMEヘッダーマップ
	* @throws MessagingException
	*/
	@Async
	private void sendMail(List<String> emailToList, List<String> emailCcList, List<String> emailBccList, MailTemplateMaster mailTemplateMaster, List<String> mailSubjectRepalceValueList, List<String> mailTextRepalceValueList, List<String> uploadFileList, Map<String, String> mimeHeaderMap) throws MessagingException {
		MimeMessage attachedMsg = javaMailSender.createMimeMessage();
		attachedMsg.setHeader("Content-Transfer-Encoding", "base64");
		MimeMessageHelper attachedHelper = new MimeMessageHelper(attachedMsg, true, StandardCharsets.UTF_8.name());

		Writer writerMailSubject = createMailSubject(mailTemplateMaster, mailSubjectRepalceValueList);
		Writer writerMailText = createMailText(mailTemplateMaster, mailTextRepalceValueList);

		String[] toEmail = (String[]) emailToList.toArray(new String[0]);
		String[] ccEmail = (String[]) emailCcList.toArray(new String[0]);
		String[] bccEmail = (String[]) emailBccList.toArray(new String[0]);
		attachedHelper.setTo(toEmail);
		attachedHelper.setFrom(appProperties.getMailProperties().getFromMailAddress());
		attachedHelper.setCc(ccEmail);
		attachedHelper.setBcc(bccEmail);
		String subject = writerMailSubject.toString().replace("&#10;", "\r\n");
		attachedHelper.setSubject(subject);
		String text = writerMailText.toString().replace("&#10;", "\r\n");
		attachedHelper.setText(text);

		if (null != uploadFileList) {
			for (String uploadFile : uploadFileList) {
				FileSystemResource res = new FileSystemResource(uploadFile);
				attachedHelper.addAttachment(res.getFilename(), res);
			}
		}

		SMTPMessage SMTPMessage = new SMTPMessage(attachedMsg);
		SMTPMessage.setEnvelopeFrom(Optional.ofNullable(mailTemplateMaster.getEnvelopeFrom()).orElse(appProperties.getMailProperties().getEnvelopeFromMailAddress()));
		// MIMEヘッダー追加
		if (MapUtils.isNotEmpty(mimeHeaderMap)) {
			for (Entry<String, String> entry : mimeHeaderMap.entrySet()) {
				if (!StringUtils.isEmpty(entry.getValue())) {
					SMTPMessage.setHeader(entry.getKey(), entry.getValue());
				}
			}
		}
		javaMailSender.send(SMTPMessage);
	}

	/**
	 * メール件名作成
	 *
	 * @param mailTemplateMaster
	 *            エラーテンプレートマスタ
	 * @param mailSubjectRepalceValueList
	 *            メール件名置換リスト(最大5個まで)
	 * @return Writer
	 */
	private Writer createMailSubject(MailTemplateMaster mailTemplateMaster, List<String> mailSubjectRepalceValueList) {
		Writer writer = new StringWriter();

		String stringMailSubject = mailTemplateMaster.getMailSubject();
		MustacheFactory mustacheFactory = new DefaultMustacheFactory();
		Mustache mustacheMailSubject = mustacheFactory.compile(new StringReader(stringMailSubject), stringMailSubject);
		MailSubject mailSubject = new MailSubject(mailSubjectRepalceValueList);
		mustacheMailSubject.execute(writer, mailSubject);

		return writer;
	}

	/**
	 * メール本文作成
	 *
	 * @param mailTemplateMaster
	 *            エラーテンプレートマスタ
	 * @param mailTextRepalceValueList
	 *            メール本文置換リスト(最大10個まで)
	 * @return Writer
	 */
	private Writer createMailText(MailTemplateMaster mailTemplateMaster, List<String> mailTextRepalceValueList) {
		Writer writer = new StringWriter();

		String stringMailText = mailTemplateMaster.getMailBody();
		MustacheFactory mustacheFactory = new DefaultMustacheFactory();
		Mustache mustacheMailText = mustacheFactory.compile(new StringReader(stringMailText), stringMailText);
		MailText mailText = new MailText(mailTextRepalceValueList);
		mustacheMailText.execute(writer, mailText);

		return writer;
	}
}
