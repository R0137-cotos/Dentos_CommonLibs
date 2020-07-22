package jp.co.ricoh.cotos.commonlib.entity.common;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.master.MailControlMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * メール送信履歴情報を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "mail_send_history")
public class MailSendHistory extends EntityBase {

	public enum MailSendType {

		未送信("0"), 完了("1"), エラー("2");

		private final String text;

		private MailSendType(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static MailSendType fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}

	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mail_send_history_seq")
	@SequenceGenerator(name = "mail_send_history_seq", sequenceName = "mail_send_history_seq", allocationSize = 1)
	@ApiModelProperty(value = "メール送信履歴ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 通知メール制御マスタ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "mail_control_master_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "通知メール制御マスタ", required = true, position = 2)
	private MailControlMaster mailControlMaster;

	/**
	 * 対象データID
	 */
	@ApiModelProperty(value = "対象データID", required = false, position = 3)
	private Long targetDataId;

	/**
	 * 宛先To
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "宛先To", required = false, position = 4, allowableValues = "range[0,255]")
	private String contactMailTo;

	/**
	 * 宛先Cc
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "宛先Cc", required = false, position = 5, allowableValues = "range[0,255]")
	private String contactMailCc;

	/**
	 * 宛先Bcc
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "宛先Bcc", required = false, position = 6, allowableValues = "range[0,255]")
	private String contactMailBcc;

	/**
	 * メール送信区分
	 */
	@ApiModelProperty(value = "メール送信区分", required = false, allowableValues = "未送信(\"0\"), 完了(\"1\"), エラー(\"2\")", example = "1", position = 7)
	private MailSendType mailSendType;

	/**
	 * 送信日時
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "実施日時(作成時不要)", required = false, position = 8, readOnly = true)
	private Date sendedAt;

	@PrePersist
	public void prePersist() {
		super.prePersist();
		this.sendedAt = super.getCreatedAt();
	}
}
