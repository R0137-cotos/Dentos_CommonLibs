package jp.co.ricoh.cotos.commonlib.entity.communication;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	@ApiModelProperty(value = "問い合わせID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 見積ID
	 */
	@ApiModelProperty(value = "見積ID", required = true, position = 2, allowableValues = "range[0,9999999999999999999]")
	private String estimationId;

	/**
	 * 親問い合わせ
	 */
	@OneToOne
	@JoinColumn(name = "parent_id", referencedColumnName = "id")
	@ApiModelProperty(value = "親問い合わせ", required = true, position = 3) // TODO ほんとに必須？
	private Contact parent;

	/**
	 * 送信者MoM社員ID
	 */
	@ApiModelProperty(value = "送信者MoM社員ID", required = true, position = 4, allowableValues = "range[0,255]")
	private String contactFromEmpId;

	/**
	 * サービスカテゴリ
	 */
	@ApiModelProperty(value = "サービスカテゴリ", required = false, position = 5)
	private ServiceCategory serviceCategory;

	/**
	 * タイトル
	 */
	@ApiModelProperty(value = "タイトル", required = false, position = 6, allowableValues = "range[0,255]")
	private String titile;

	/**
	 * 内容
	 */
	@ApiModelProperty(value = "内容", required = false, position = 7)
	@Lob
	private String content;

	/**
	 * 送信日時
	 */
	@ApiModelProperty(value = "送信日時", required = true, position = 8, readOnly = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date sendAt;

	/**
	 * 宛先
	 */
	@OneToMany(mappedBy = "contact")
	@ApiModelProperty(value = "宛先", required = true, position = 9)
	private List<ContactTo> contactToList;

	@PrePersist
	public void prePersist() {
		this.sendAt = new Date();
	}
}
