package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import lombok.Data;

@Entity
@Data
@Table(name = "mail_template_master")
public class MailTemplateMaster extends EntityBaseMaster {

	public enum ServiceCategory {

		見積, 契約, 手配;

		@JsonValue
		public String toValue() {
			return this.name();
		}

		@JsonCreator
		public static Enum<ServiceCategory> fromValue(String name) {
			return Arrays.stream(values()).filter(v -> v.name() == name).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(name)));
		}
	}

	public enum ProcessCategory {

		承認依頼, 承認依頼差戻, 承認, 作業依頼, 作業依頼差戻, 作業完了;

		@JsonValue
		public String toValue() {
			return this.name();
		}

		@JsonCreator
		public static Enum<ProcessCategory> fromValue(String name) {
			return Arrays.stream(values()).filter(v -> v.name() == name).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(name)));
		}
	}

	@Id
	@ApiModelProperty(value = "メールテンプレートID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 商品マスタ
	 */
	@ApiModelProperty(value = "商品マスタID", required = true, position = 2, allowableValues = "range[0,9999999999999999999]")
	private long productMasterId;

	/**
	 * サービスカテゴリ
	 */
	@ApiModelProperty(value = "サービスカテゴリ", required = true, position = 3)
	private ServiceCategory serviceCategory;

	/**
	 * 処理カテゴリ
	 */
	@ApiModelProperty(value = "処理カテゴリ", required = true, position = 4)
	private ProcessCategory processCategory;

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
