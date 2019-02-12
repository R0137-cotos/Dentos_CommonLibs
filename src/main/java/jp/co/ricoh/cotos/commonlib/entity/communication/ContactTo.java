package jp.co.ricoh.cotos.commonlib.entity.communication;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

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
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static SendType fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_to_seq")
	@SequenceGenerator(name = "contact_to_seq", sequenceName = "contact_to_seq", allocationSize = 1)
	@ApiModelProperty(value = "宛先ID (作成時不要)", required = true, position = 1, allowableValues = "range[0,9999999999999999999]", readOnly = true)
	private long id;

	/**
	 * 問い合わせ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "contact_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "問い合わせ", required = true, position = 2)
	private Contact contact;

	/**
	 * 送信タイプ
	 */
	@ApiModelProperty(value = "送信タイプ", required = false, allowableValues = "TO(\"1\"), CC(\"2\")", example = "1", position = 3)
	private SendType sendType;

	/**
	 * 宛先MoM社員ID
	 */
	@Column(nullable = false)
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "宛先MoM社員ID", required = true, position = 4, allowableValues = "range[0,255]")
	private String contactToEmpId;

	/**
	 * 宛先メールアドレス
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "宛先メールアドレス", required = false, position = 5, allowableValues = "range[0,255]")
	private String contactToEmail;

	/**
	 * 宛先氏名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "宛先氏名", required = false, position = 6, allowableValues = "range[0,255]")
	private String contactToEmpName;
}
