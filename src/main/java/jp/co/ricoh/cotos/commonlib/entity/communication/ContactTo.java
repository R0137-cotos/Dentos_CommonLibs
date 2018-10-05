package jp.co.ricoh.cotos.commonlib.entity.communication;

import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 問い合わせ宛先を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "contact_to")
public class ContactTo extends EntityBase {

	public enum SendType {

		TO("1"), CC("2");

		private final String text;

		private SendType(final String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return this.text;
		}

		public static SendType fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	@Id
	@ApiModelProperty(value = "宛先ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 問い合わせ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "contact_id", referencedColumnName = "id")
	@ApiModelProperty(value = "問い合わせ", required = true, position = 2)
	private Contact contact;

	/**
	 * 送信タイプ
	 */
	@ApiModelProperty(value = "送信タイプ", required = false, position = 3)
	private SendType sendType;

	/**
	 * 宛先MoM社員ID
	 */
	@ApiModelProperty(value = "宛先MoM社員ID", required = true, position = 4, allowableValues = "range[0,255]")
	private String contactToEmpId;

	/**
	 * 宛先メールアドレス
	 */
	@ApiModelProperty(value = "宛先メールアドレス", required = false, position = 5, allowableValues = "range[0,255]", readOnly = true)
	private String contactToEmail;
}
