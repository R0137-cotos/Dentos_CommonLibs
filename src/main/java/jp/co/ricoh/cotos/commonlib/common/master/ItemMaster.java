package jp.co.ricoh.cotos.commonlib.common.master;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 品種を表すEntity
 */

@Entity
@Data
@Table(name = "item_master")
public class ItemMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_master_seq")
	@SequenceGenerator(name = "item_master_seq", sequenceName = "item_master_seq", allocationSize = 1)
	@ApiModelProperty(value = "品種マスタID", required = true, position = 1)
	private long id;

	/**
	 * 品種名
	 */
	@ApiModelProperty(value = "品種名", required = false, position = 2, allowableValues = "range[0,255]")
	private String name;

	/**
	 * 品種コード
	 */
	@ApiModelProperty(value = "品種コード", required = false, position = 3, allowableValues = "range[0,255]")
	private String itemCode;

	@ManyToOne
	@ApiModelProperty(value = "商品マスタ", required = false, position = 4)
	private Product product;

	// 以下、COTOSエンティティー共通項目
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "登録日時", required = false)
	private Date createdAt;
	@ApiModelProperty(value = "登録者", required = false, allowableValues = "range[0,255]")
	private String createdUser;
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "更新日時", required = false)
	private Date updatedAt;
	@ApiModelProperty(value = "更新者", required = false, allowableValues = "range[0,255]")
	private String updatedUser;
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "削除日時", required = false)
	private Date deletedAt;
	@ApiModelProperty(value = "削除者", required = false, allowableValues = "range[0,255]")
	private String deletedUser;

	@PrePersist
	public void prePersist() {
		this.createdAt = new Date();
	}

	@PreUpdate
	public void preUpdate() {
		this.updatedAt = new Date();
	}
}
