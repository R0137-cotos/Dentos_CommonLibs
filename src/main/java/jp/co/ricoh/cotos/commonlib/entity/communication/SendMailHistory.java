package jp.co.ricoh.cotos.commonlib.entity.communication;

import java.util.Arrays;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.master.AlertMailControlMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.MailTemplateMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "send_mail_history")
public class SendMailHistory extends EntityBase {

	public enum ErrorContent {

		通信エラー;

		@JsonValue
		public String toValue() {
			return this.name();
		}

		@JsonCreator
		public static Enum<ErrorContent> fromValue(String name) {
			return Arrays.stream(values()).filter(v -> v.name() == name).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(name)));
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "send_mail_history_seq")
	@SequenceGenerator(name = "send_mail_history_seq", sequenceName = "send_mail_history_seq", allocationSize = 1)
	private long id;

	/**
	 * 通知メール制御マスタ
	 */
	@ManyToOne
	@JoinColumn(name = "alertMailControlMasterId")
	private AlertMailControlMaster alertMailControlMaster;

	/**
	 * メールテンプレートマスタ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "mailTemplateMasterId")
	private MailTemplateMaster mailTemplateMaster;

	/**
	 * 送信元メールアドレス
	 */
	private String sendFromMailAddress;

	/**
	 * 送信先メールアドレス(To)
	 */
	private String[] toMailAddress;

	/**
	 * 送信先メールアドレス(Cc)
	 */
	private String[] ccMailAddress;

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
	 * 添付ファイル
	 */
	@Lob
	@Basic(fetch = FetchType.EAGER)
	private String attachedFile;

	/**
	 * エラーフラグ
	 */
	@Column(length = 1)
	private boolean errorFlg;

	/**
	 * エラー内容
	 */
	@Enumerated(EnumType.STRING)
	private ErrorContent errorContent;

	/**
	 * 再送フラグ
	 */
	@Column(length = 1)
	private boolean reForwardingFlg;
}
