package jp.co.ricoh.cotos.commonlib.logic.mail;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import jp.co.ricoh.cotos.commonlib.entity.EnumType.ProcessCategory;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.ServiceCategory;
import jp.co.ricoh.cotos.commonlib.entity.master.MailTemplateMaster;
import jp.co.ricoh.cotos.commonlib.repository.master.MailTemplateMasterRepository;
import jp.co.ricoh.cotos.commonlib.util.AppProperties;

@Component
public class CommonSendMail {

	@Autowired
	MailTemplateMasterRepository mailTemplateMasterRepository;

	@Autowired
	JavaMailSender javaMailSender;

	@Autowired
	AppProperties appProperties;

	/**
	 * メールテンプレートマスタ特定&メール送信処理
	 * 
	 * @param emailToList
	 *            Toメールアドレス
	 * @param emailCcList
	 *            CCメールアドレスリスト
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
	public void findMailTemplateMasterAndSendMail(ServiceCategory serviceCategory, ProcessCategory processCategory, List<String> emailToList, List<String> emailCcList, List<String> mailSubjectRepalceValueList, List<String> mailTextRepalceValueList, String uploadFile) throws MessagingException {
		MailTemplateMaster mailTemplateMaster = mailTemplateMasterRepository.findByServiceCategoryAndProcessCategory(serviceCategory.toString(), processCategory.toString());
		sendMail(emailToList, emailCcList, mailTemplateMaster, mailSubjectRepalceValueList, mailTextRepalceValueList, uploadFile);
	}

	/**
	 * メールテンプレートマスタ特定&メール送信処理
	 * 
	 * @param mailTemplateMasterId
	 *            メールテンプレートマスタID
	 * @param emailToList
	 *            Toメールアドレス
	 * @param emailCcList
	 *            CCメールアドレスリスト
	 * @param mailSubjectRepalceValueList
	 *            メール件名置換リスト(最大5個まで)
	 * @param mailTextRepalceValueList
	 *            メール本文置換リスト(最大10個まで)
	 * @param uploadFile
	 *            添付ファイル
	 * @throws MessagingException
	 */
	public void findMailTemplateMasterAndSendMail(long mailTemplateMasterId, List<String> emailToList, List<String> emailCcList, List<String> mailSubjectRepalceValueList, List<String> mailTextRepalceValueList, String uploadFile) throws MessagingException {
		MailTemplateMaster mailTemplateMaster = mailTemplateMasterRepository.findOne(mailTemplateMasterId);
		sendMail(emailToList, emailCcList, mailTemplateMaster, mailSubjectRepalceValueList, mailTextRepalceValueList, uploadFile);
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
	 * @throws MessagingException
	 */
	@Async
	private void sendMail(List<String> emailToList, List<String> emailCcList, MailTemplateMaster mailTemplateMaster, List<String> mailSubjectRepalceValueList, List<String> mailTextRepalceValueList, String uploadFile) throws MessagingException {
		MimeMessage attachedMsg = javaMailSender.createMimeMessage();
		attachedMsg.setHeader("Content-Transfer-Encoding", "base64");
		MimeMessageHelper attachedHelper = new MimeMessageHelper(attachedMsg, true, StandardCharsets.UTF_8.name());

		Writer writerMailSubject = createMailSubject(mailTemplateMaster, mailSubjectRepalceValueList);
		Writer writerMailText = createMailText(mailTemplateMaster, mailTextRepalceValueList);

		String[] toEmail = (String[]) emailToList.toArray(new String[0]);
		String[] ccEmail = (String[]) emailCcList.toArray(new String[0]);
		attachedHelper.setTo(toEmail);
		attachedHelper.setFrom(appProperties.getMailProperties().getFromMailAddress());
		attachedHelper.setCc(ccEmail);
		attachedHelper.setSubject(writerMailSubject.toString());
		attachedHelper.setText(writerMailText.toString());

		if (null != uploadFile) {
			FileSystemResource res = new FileSystemResource(uploadFile);
			attachedHelper.addAttachment(res.getFilename(), res);
		}
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
