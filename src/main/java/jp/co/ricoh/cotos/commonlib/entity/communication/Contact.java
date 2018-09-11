package jp.co.ricoh.cotos.commonlib.entity.communication;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
@Table(name = "contact")
public class Contact extends EntityBase {
	public enum DomainType {

		見積, 契約, 手配;

		@JsonValue
		public String toValue() {
			return this.name();
		}

		@JsonCreator
		public static Enum<DomainType> fromValue(String name) {
			return Arrays.stream(values()).filter(v -> v.name() == name).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(name)));
		}
	}

	@Id
	@ApiModelProperty(value = "問い合わせID", required = true, position = 1, allowableValues = "range[0,99999999999999999999999999999]")
	private long id;

	/**
	 * 見積番号
	 */
	@ApiModelProperty(value = "見積番号", required = true, position = 2, allowableValues = "range[0,255]")
	private String estimateNumber;

	/**
	 * 親問い合わせ
	 */
	@OneToOne
	@JoinColumn(name = "parent_id", referencedColumnName = "id")
	@ApiModelProperty(value = "親問い合わせ", required = false, position = 3)
	private Contact parent;

	/**
	 * 送信者MoM社員ID
	 */
	@ApiModelProperty(value = "送信者MoM社員ID", required = true, position = 4, allowableValues = "range[0,255]")
	private String contactFromEmpId;

	/**
	 * 宛先ID
	 */
	@OneToMany(mappedBy = "contact")
	@ApiModelProperty(value = "宛先ID", required = true, position = 5)
	private List<ContactTo> contactTo;

	/**
	 * ドメイン
	 */
	@ApiModelProperty(value = "ドメイン", required = false, position = 6)
	private DomainType domain;

	/**
	 * タイトル
	 */
	@ApiModelProperty(value = "タイトル", required = false, position = 7, allowableValues = "range[0,255]")
	private String titile;

	/**
	 * 内容
	 */
	@ApiModelProperty(value = "内容", required = false, position = 8)
	private String content;

	/**
	 * 送信日時
	 */
	@ApiModelProperty(value = "送信日時", required = true, position = 9)
	private int sendAt;

	/**
	 * 子問い合わせ
	 */
	@OneToOne()
	@ApiModelProperty(value = "子問い合わせ", required = false, position = 10)
	private Contact child;
}
