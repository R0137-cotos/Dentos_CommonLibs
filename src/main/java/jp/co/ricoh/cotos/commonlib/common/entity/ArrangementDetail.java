package jp.co.ricoh.cotos.commonlib.common.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.common.master.ArrangementMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 手配情報の明細を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "arrangement_detail")
public class ArrangementDetail extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "arrangement_detail_seq")
	@SequenceGenerator(name = "arrangement_detail_seq", sequenceName = "arrangement_detail_seq", allocationSize = 1)
	@ApiModelProperty(value = "手配明細ID", required = true, position = 1)
	private long id;

	/**
	 * 契約明細ID
	 */
	@ApiModelProperty(value = "契約明細ID", required = false, position = 2)
	private long contractDetailId;

	/**
	 * 品種ID
	 */
	@ApiModelProperty(value = "品種ID", required = false, position = 3)
	private long itemId;

	/**
	 * 品種名
	 */
	@ApiModelProperty(value = "品種名", required = false, position = 4, allowableValues = "range[0,255]")
	private String itemName;

	/**
	 * リコー品種コード
	 */
	@ApiModelProperty(value = "リコー品種コード", required = false, position = 5, allowableValues = "range[0,255]")
	private String ricohItemCode;

	/**
	 * 手配
	 */
	@ManyToOne(optional = false)
	@JsonIgnore
	@ApiModelProperty(value = "手配", required = false, position = 6)
	private Arrangement arrangement;

	/**
	 * 手配業務
	 */
	@ManyToOne(optional = false)
	@JsonIgnore
	@ApiModelProperty(value = "手配業務", required = false, position = 7)
	private ArrangementWork arrangementWork;

	/**
	 * 手配マスタ
	 */
	@ManyToOne(optional = false)
	@ApiModelProperty(value = "手配マスタ", required = false, position = 8)
	private ArrangementMaster arrangementMaster;
}