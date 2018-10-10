package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.ServiceCategory;
import lombok.Data;

@Entity
@Data
@Table(name = "mail_template_master")
public class MailTemplateMaster {

	/**
	 * 処理カテゴリ
	 */
	public enum ProcessCategory {

		承認依頼("1"), 承認依頼取消("2"), 承認依頼差戻("3"), 承認("4"), 作業依頼("5"), 作業完了("6"), キャンセル手続き("7"), キャンセル手続き中止("8"), 解約手続き("9"), 解約手続き中止("10"), 問い合わせ("11");

		private final String text;

		private ProcessCategory(final String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return this.text;
		}

		public static ProcessCategory fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	@Id
	@ApiModelProperty(value = "メールテンプレートID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * サービスカテゴリ
	 */
	@ApiModelProperty(value = "サービスカテゴリ", required = true, position = 2)
	private ServiceCategory serviceCategory;

	/**
	 * 処理カテゴリ
	 */
	@ApiModelProperty(value = "処理カテゴリ", required = true, position = 3)
	private ProcessCategory processCategory;

	/**
	 * 商品グループマスタID
	 */
	@ApiModelProperty(value = "商品グループマスタID", required = true, position = 4, allowableValues = "range[0,9999999999999999999]")
	private long productGrpMasterId;

	/**
	 * メール件名
	 */
	@ApiModelProperty(value = "メール件名", required = true, position = 5, allowableValues = "range[0,255]")
	private String mailSubject;

	/**
	 * メール本文
	 */
	@ApiModelProperty(value = "メール本文", required = true, position = 6)
	private String mailBody;

}
