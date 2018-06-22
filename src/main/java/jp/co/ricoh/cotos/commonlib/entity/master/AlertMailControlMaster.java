package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Data;

@Entity
@Data
@Table(name = "alert_mail_control_master")
public class AlertMailControlMaster {

	public enum ContractUpdateDiv {

		手動更新, 自動更新, 全て;

		@JsonValue
		public String toValue() {
			return this.name();
		}

		@JsonCreator
		public static Enum<ContractUpdateDiv> fromValue(String name) {
			return Arrays.stream(values()).filter(v -> v.name() == name).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(name)));
		}
	}

	public enum DestinationDiv {

		お客様担当者, 担当営業, 販売店担当者, 直接指定;

		@JsonValue
		public String toValue() {
			return this.name();
		}

		@JsonCreator
		public static Enum<DestinationDiv> fromValue(String name) {
			return Arrays.stream(values()).filter(v -> v.name() == name).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(name)));
		}
	}

	public enum AutoDeliveryFailHandlingDiv {

		別宛先へ送信する, 別宛先へ送信しない;

		@JsonValue
		public String toValue() {
			return this.name();
		}

		@JsonCreator
		public static Enum<AutoDeliveryFailHandlingDiv> fromValue(String name) {
			return Arrays.stream(values()).filter(v -> v.name() == name).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(name)));
		}
	}

	public enum AutoDeliveryFailDestinationDiv {

		担当営業, 販売店担当者, 直接指定;

		@JsonValue
		public String toValue() {
			return this.name();
		}

		@JsonCreator
		public static Enum<AutoDeliveryFailDestinationDiv> fromValue(String name) {
			return Arrays.stream(values()).filter(v -> v.name() == name).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(name)));
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "alert_mail_control_master_seq")
	@SequenceGenerator(name = "alert_mail_control_master_seq", sequenceName = "alert_mail_control_master_seq", allocationSize = 1)
	private long id;

	/**
	 * 商品マスタ
	 */
	@ManyToOne
	private Product product;

	/**
	 * 通知判定未実施フラグ
	 */
	@Column(length = 1)
	private boolean alertDetermineNotOperationFlg;

	/**
	 * 起算日判定項目
	 */
	private String initialDateDetermineColumn;

	/**
	 * 通知日差分
	 */
	private Integer alertDateDelta;

	/**
	 * 通知時間
	 */
	private Integer alertTime;

	/**
	 * メールテンプレートマスタ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "mailTemplateMasterId")
	private MailTemplateMaster mailTemplateMaster;

	/**
	 * 契約更新方式区分
	 */
	@Enumerated(EnumType.STRING)
	private ContractUpdateDiv contractUpdateDiv;

	/**
	 * 送信先区分(To)
	 */
	@Enumerated(EnumType.STRING)
	private DestinationDiv destinationDivSendTo;

	/**
	 * 送信先区分(CC)
	 */
	@Enumerated(EnumType.STRING)
	private DestinationDiv destinationDivSendCc;

	/**
	 * 自動配信不可時対応区分
	 */
	@Enumerated(EnumType.STRING)
	private AutoDeliveryFailHandlingDiv autoDeliveryFailHandlingDiv;

	/**
	 * 自動配信不可時送信先区分(To)
	 */
	@Enumerated(EnumType.STRING)
	private AutoDeliveryFailDestinationDiv autoDeliveryFailDestinationDivSendTo;

	/**
	 * 自動配信不可時送信先区分(CC)
	 */
	@Enumerated(EnumType.STRING)
	private AutoDeliveryFailDestinationDiv autoDeliveryFailDestinationDivSendCc;

	/**
	 * 自動配信不可時通知日差分
	 */
	private Integer autoDeliveryFailAlertDateDelta;

	/**
	 * 再送要否フラグ
	 */
	@Column(length = 1)
	private boolean reForwardingDeltaFlg;
}
