package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.ServiceCategory;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 通知メール制御マスタを表すEntity
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "mail_control_master")
public class MailControlMaster extends EntityBaseMaster {

	public enum NotificationTimingType {

		対象日イコール("0"), 対象日以降("1");

		private final String text;

		private NotificationTimingType(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static NotificationTimingType fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}

	}

	public enum ContactReferenceType {

		直接アドレス("0"), MoM社員ID("1");

		private final String text;

		private ContactReferenceType(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static ContactReferenceType fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mail_control_master_seq")
	@SequenceGenerator(name = "mail_control_master_seq", sequenceName = "mail_control_master_seq", allocationSize = 1)
	@ApiModelProperty(value = "通知メール制御マスタID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 対象ドメイン
	 */
	@Column(nullable = false)
	@NotNull
	@ApiModelProperty(value = "対象ドメイン", required = true, allowableValues = "見積(\"1\"), 契約(\"2\"), 手配(\"3\")", example = "1", position = 2)
	private ServiceCategory serviceCategory;

	/**
	 * 対象トランザクションテーブル名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "対象トランザクションテーブル名", required = false, position = 3, allowableValues = "range[0,255]")
	private String targetTransactionTableName;

	/**
	 * 対象カラム名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "対象カラム名", required = false, position = 4, allowableValues = "range[0,255]")
	private String targetColumnName;

	/**
	 * 通知日差分
	 */
	@Max(9)
	@ApiModelProperty(value = "通知日差分", required = false, position = 5)
	private Integer notificationDateDifference;

	/**
	 * 通知日タイミング区分
	 */
	@ApiModelProperty(value = "通知日タイミング区分", required = false, allowableValues = "直接アドレス(\"0\"), MoM社員ID(\"1\")", example = "1", position = 6)
	private NotificationTimingType notificationTimingType;

	/**
	 * 宛先テーブル区分（To）
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "宛先テーブル名区分（To）", required = false, position = 7, allowableValues = "range[0,255]")
	private String contactTableTypeTo;

	/**
	 * 宛先テーブル区分（Cc）
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "宛先テーブル区分（Cc）", required = false, position = 8, allowableValues = "range[0,255]")
	private String contactTableTypeCc;

	/**
	 * 宛先テーブル区分（Bcc）
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "宛先テーブル区分（Bcc）", required = false, position = 9, allowableValues = "range[0,255]")
	private String contactTableTypeBcc;

	/**
	 * メールテンプレートマスタ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "mail_template_master_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "メールテンプレートマスタ", required = true, position = 10)
	private MailTemplateMaster mailTemplateMaster;

	/**
	 * 条件式追加フラグ
	 */
	@ApiModelProperty(value = "条件式追加フラグ", required = false, position = 11, allowableValues = "range[0,9]")
	private Long extendsQueryFlg;

	/**
	 * 追加条件式
	 */
	@ApiModelProperty(value = "追加条件式", required = false, position = 12)
	private String extendsQuery;

	/**
	 * メール変換値マスタ
	 */
	@OneToMany(mappedBy = "mailControlMaster")
	@ApiModelProperty(value = "メール変換値マスタ", required = false, position = 13)
	private List<MailConvertValueMaster> MailConvertValueMasterList;

	/**
	 *  通知メール対象商材マスタ
	 */
	@OneToMany(mappedBy = "mailControlMaster")
	@ApiModelProperty(value = "通知メール対象商材マスタ", required = false, position = 14)
	private List<MailProductMaster> mailProductMasterList;
}
