package jp.co.ricoh.cotos.commonlib.entity.communication;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.ServiceCategory;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 問い合わせを表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "contact")
public class Contact extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_seq")
	@SequenceGenerator(name = "contact_seq", sequenceName = "contact_seq", allocationSize = 1)
	@ApiModelProperty(value = "問い合わせID (作成時不要)", required = true, position = 1, allowableValues = "range[0,9223372036854775807]", readOnly = true)
	private long id;

	/**
	 * 見積ID
	 */
	@Min(0)
	@Column(nullable = false)
	@ApiModelProperty(value = "見積ID", required = true, position = 2, allowableValues = "range[0,9223372036854775807]")
	private long estimationId;

	/**
	 * 親問い合わせ
	 */
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "parent_id", referencedColumnName = "id")
	@ApiModelProperty(value = "親問い合わせ", required = false, position = 3)
	private Contact parent;

	/**
	 * 子問い合わせリスト
	 */
	@Valid
	@OneToMany(mappedBy = "parent")
	@ApiModelProperty(value = "子問い合わせリスト", required = false, position = 4)
	private List<Contact> children;

	/**
	 * 送信者MoM社員ID
	 */
	@Column(nullable = false)
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "送信者MoM社員ID", required = true, position = 5, allowableValues = "range[0,255]")
	private String contactFromEmpId;

	/**
	 * サービスカテゴリ
	 */
	@ApiModelProperty(value = "サービスカテゴリ", required = false, allowableValues = "見積(\"1\"), 契約(\"2\"), 手配(\"3\")", example = "1", position = 6)
	private ServiceCategory serviceCategory;

	/**
	 * タイトル
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "タイトル", required = false, position = 7, allowableValues = "range[0,255]")
	private String title;

	/**
	 * 内容
	 */
	@ApiModelProperty(value = "内容", required = false, position = 8)
	@Lob
	private String content;

	/**
	 * 送信日時
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "送信日時 (作成時不要)", required = true, position = 9, readOnly = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date sendAt;

	/**
	 * 送信者氏名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "送信者氏名", required = false, position = 10, allowableValues = "range[0,255]")
	private String contactFromEmpName;

	/**
	 * 宛先
	 */
	@Valid
	@OneToMany(mappedBy = "contact")
	@NotNull
	@ApiModelProperty(value = "宛先", required = true, position = 11)
	private List<ContactTo> contactToList;

	/**
	 * アプリケーションID
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "アプリケーションID", required = false, position = 12, allowableValues = "range[0,255]")
	private String appId;

	@PrePersist
	public void prePersist() {
		super.prePersist();
		this.sendAt = super.getCreatedAt();
	}
}
