package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.ServiceCategory;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * メールテンプレートマスタ
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "mail_template_master")
public class MailTemplateMaster extends EntityBaseMaster {

	/**
	 * 処理カテゴリ
	 */
	public enum ProcessCategory {

		承認依頼("1"), 承認依頼取消("2"), 承認依頼差戻("3"), 承認("4"), 作業依頼("5"), 作業完了("6"), キャンセル手続き("7"), キャンセル手続き中止("8"), 解約手続き(
				"9"), 解約手続き中止("10"), 問い合わせ("11");

		private final String text;

		private ProcessCategory(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static ProcessCategory fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst()
					.orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mail_template_master_seq")
	@SequenceGenerator(name = "mail_template_master_seq", sequenceName = "mail_template_master_seq", allocationSize = 1)
	@ApiModelProperty(value = "メールテンプレートID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * サービスカテゴリ
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "サービスカテゴリ", required = true, position = 2)
	private ServiceCategory serviceCategory;

	/**
	 * 処理カテゴリ
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "処理カテゴリ", required = true, position = 3)
	private ProcessCategory processCategory;

	/**
	 * 商品グループマスタID
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "商品グループマスタID", required = true, position = 4, allowableValues = "range[0,9999999999999999999]")
	private long productGrpMasterId;

	/**
	 * メール件名
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "メール件名", required = true, position = 5, allowableValues = "range[0,255]")
	private String mailSubject;

	/**
	 * メール本文
	 */
	@Column(nullable = false)
	@Lob
	@ApiModelProperty(value = "メール本文", required = true, position = 6)
	private String mailBody;

}
