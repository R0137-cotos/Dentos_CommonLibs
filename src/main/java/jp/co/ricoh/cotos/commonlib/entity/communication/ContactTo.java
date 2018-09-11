package jp.co.ricoh.cotos.commonlib.entity.communication;

import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "contact_to")
public class ContactTo extends EntityBase {
	public enum SendType {

		TO, CC;

		@JsonValue
		public String toValue() {
			return this.name();
		}

		@JsonCreator
		public static Enum<SendType> fromValue(String name) {
			return Arrays.stream(values()).filter(v -> v.name() == name).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(name)));
		}
	}

	@Id
	@ApiModelProperty(value = "宛先ID", required = true, position = 1, allowableValues = "range[0,99999999999999999999999999999]")
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
	@ApiModelProperty(value = "宛先メールアドレス", required = false, position = 5, allowableValues = "range[0,255]")
	private String contactToEmail;
}
