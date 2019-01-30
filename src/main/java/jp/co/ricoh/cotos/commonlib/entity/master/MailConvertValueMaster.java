package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 通知メール変換値マスタを表すEntity
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "mail_convert_value_master")
public class MailConvertValueMaster extends EntityBaseMaster {

	public enum SubjectVodyType {

		件名("0"), 本文("1");

		private final String text;

		private SubjectVodyType(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static SubjectVodyType fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}

	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mail_convert_value_master_seq")
	@SequenceGenerator(name = "mail_convert_value_master_seq", sequenceName = "mail_convert_value_master_seq", allocationSize = 1)
	@ApiModelProperty(value = "通知メール変換値マスタID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
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
	 * 件名/本文区分
	 */
	@ApiModelProperty(value = "件名/本文区分", required = false, allowableValues = "件名(\"0\"), 本文(\"1\")", example = "1", position = 3)
	private SubjectVodyType subjectBodyType;

	/**
	 * 置換変数番号
	 */
	@Max(99)
	@ApiModelProperty(value = "置換変数番号", required = false, position = 4)
	private Integer replaceVariableNumber;

	/**
	 * 置換値エンティティ名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "置換値エンティティ名", required = false, position = 5, allowableValues = "range[0,255]")
	private String replaceEntityName;

	/**
	 * 置換値フィールド名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "置換値フィールド名", required = false, position = 6, allowableValues = "range[0,255]")
	private String replaceFieldName;
}
