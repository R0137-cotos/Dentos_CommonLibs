package jp.co.ricoh.cotos.commonlib.common.master;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import jp.co.ricoh.cotos.commonlib.common.entity.Communication.CommunicationCategory;
import jp.co.ricoh.cotos.commonlib.common.entity.Communication.ProcessCategory;
import lombok.Data;

@Entity
@Data
@Table(name = "mail_template_master")
public class MailTemplateMaster {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mail_template_master_seq")
	@SequenceGenerator(name = "mail_template_master_seq", sequenceName = "mail_template_master_seq", allocationSize = 1)
	private long id;

	/**
	 * メール件名
	 */
	private String mailSubject;

	/**
	 * メール本文
	 */
	@Lob
	@Basic(fetch = FetchType.EAGER)
	private String mailBody;

	/**
	 * コミュニケーションカテゴリー
	 */
	@Enumerated(EnumType.STRING)
	private CommunicationCategory communicationCategory;

	/**
	 * 処理カテゴリー
	 */
	@Enumerated(EnumType.STRING)
	private ProcessCategory processCategory;

	/**
	 * 送信元メールアドレス
	 */
	private String sendFromMailAddress;
}
