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

import io.swagger.annotations.ApiModelProperty;
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
	@ApiModelProperty(value = "メール送信履歴ID", required = true, position = 1)
	private long id;

	@ManyToOne
	@JoinColumn(name = "alertMailControlMasterId")
	@ApiModelProperty(value = "通知メール制御マスタ", required = false, position = 2)
	private AlertMailControlMaster alertMailControlMaster;

	@ManyToOne(optional = false)
	@JoinColumn(name = "mailTemplateMasterId")
	@ApiModelProperty(value = "メールテンプレートマスタ", required = false, position = 3)
	private MailTemplateMaster mailTemplateMaster;

	private String sendFromMailAddress;

	private String[] toFromMailAddress;

	private String[] ccFromMailAddress;

	private String mailSubject;

	@Lob
	@Basic(fetch = FetchType.EAGER)
	private String mailBody;

	@Lob
	@Basic(fetch = FetchType.EAGER)
	private String attachedFile;

	@Column(length = 1)
	private boolean errorFlg;

	@Enumerated(EnumType.STRING)
	private ErrorContent errorContent;

	@Column(length = 1)
	private boolean reForwardingFlg;
}
