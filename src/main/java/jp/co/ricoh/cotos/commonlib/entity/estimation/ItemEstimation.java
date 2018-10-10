package jp.co.ricoh.cotos.commonlib.entity.estimation;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.master.ItemMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 品種を表すEntity
 */

@Entity
@EqualsAndHashCode(callSuper = true)
@EntityListeners(ItemEstimationListener.class)
@Data
@Table(name = "item_estimation")
public class ItemEstimation extends EntityBase {

	@Id
	@ApiModelProperty(value = "ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 品種マスタ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "item_master_id", referencedColumnName = "id")
	@ApiModelProperty(value = "品種マスタ", required = true, position = 2)
	private ItemMaster itemMaster;

	/**
	 * 品種名
	 */
	@ApiModelProperty(value = "品種名", required = false, position = 3, allowableValues = "range[0,255]")
	private String name;

	/**
	 * リコー品種コード
	 */
	@ApiModelProperty(value = "リコー品種コード", required = false, position = 4, allowableValues = "range[0,255]")
	private String ricohItemCode;

	/**
	 * 品種区分
	 */
	@ApiModelProperty(value = "品種区分", required = false, position = 5, allowableValues = "range[0,255]")
	private String itemType;

	/**
	 * 費用種別
	 */
	@ApiModelProperty(value = "費用種別", required = false, position = 6, allowableValues = "range[0,255]")
	private String costType;

	/**
	 * 見積明細
	 */
	@OneToOne(optional = false)
	@JoinColumn(name = "estimation_detail_id", referencedColumnName = "id")
	@ApiModelProperty(value = "見積明細", required = true, position = 5)
	private EstimationDetail estimationDetail;

}
