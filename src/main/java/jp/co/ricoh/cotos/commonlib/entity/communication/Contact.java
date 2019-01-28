package jp.co.ricoh.cotos.commonlib.entity.communication;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

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
	@ApiModelProperty(value = "問い合わせID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 見積ID
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "見積ID", required = true, position = 2, allowableValues = "range[0,9999999999999999999]")
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
	@OneToMany(mappedBy = "parent")
	@ApiModelProperty(value = "子問い合わせリスト", required = false, position = 3)
	private List<Contact> children;

	/**
	 * 送信者MoM社員ID
	 */
	@Column(nullable = false)
	@NotEmpty
	@Size(max = 255)
	@ApiModelProperty(value = "送信者MoM社員ID", required = true, position = 4, allowableValues = "range[0,255]")
	private String contactFromEmpId;

	/**
	 * サービスカテゴリ
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "サービスカテゴリ", required = false, allowableValues = "見積(\"1\"), 契約(\"2\"), 手配(\"3\")", example = "1", position = 5)
	private ServiceCategory serviceCategory;

	/**
	 * タイトル
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "タイトル", required = false, position = 6, allowableValues = "range[0,255]")
	private String title;

	/**
	 * 内容
	 */
	@ApiModelProperty(value = "内容", required = false, position = 7)
	@Lob
	private String content;

	/**
	 * 送信日時
	 */
	@Column(nullable = false)
	@NotNull
	@ApiModelProperty(value = "送信日時", required = true, position = 8, readOnly = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date sendAt;

	/**
	 * 宛先
	 */
	@OneToMany(mappedBy = "contact")
	@NotNull
	@ApiModelProperty(value = "宛先", required = true, position = 9)
	private List<ContactTo> contactToList;

	@PrePersist
	public void prePersist() {
		super.prePersist();
		this.sendAt = super.getCreatedAt();
	}
}
