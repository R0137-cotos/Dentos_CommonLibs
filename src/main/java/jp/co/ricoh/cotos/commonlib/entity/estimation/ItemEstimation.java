package jp.co.ricoh.cotos.commonlib.entity.estimation;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.master.ItemMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.ItemMaster.CostType;
import jp.co.ricoh.cotos.commonlib.entity.master.ItemMaster.ItemType;
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
	 * 商品マスタ
	 */
	@ApiModelProperty(value = "商品マスタ", required = false, position = 3, allowableValues = "range[0,9999999999999999999]")
	private long productMasterId;

	/**
	 * 品種名
	 */
	@ApiModelProperty(value = "品種名", required = false, position = 4, allowableValues = "range[0,255]")
	private String itemEstimationName;

	/**
	 * リコー品種コード
	 */
	@ApiModelProperty(value = "リコー品種コード", required = false, position = 5, allowableValues = "range[0,255]")
	private String ricohItemCode;

	/**
	 * 品種区分
	 */
	@ApiModelProperty(value = "品種区分", required = false, position = 6)
	private ItemType itemType;

	/**
	 * 費用種別
	 */
	@ApiModelProperty(value = "費用種別", required = false, position = 7)
	private CostType costType;
	
	/**
	 * 仕切価格
	 */
	@ApiModelProperty(value = "仕切価格", required = false, position = 8, allowableValues = "range[0.00,9999999999999999999.99]")
	@Pattern(regexp = "9999999999999999999.99")
	private BigDecimal partitionPrice;
	
	/**
	 * 積上げ可能期間（開始日）
	 */
	@ApiModelProperty(value = "積上げ可能期間（開始日）", required = true, position = 9, readOnly = true)
	private Date effectiveFrom;
	
	/**
	 * 積上げ可能期間（終了日）
	 */
	@ApiModelProperty(value = "積上げ可能期間（終了日）", required = true, position = 10, readOnly = true)
	private Date effectiveTo;
	
	/**
	 * 見積明細
	 */
	@OneToOne(optional = false)
	@JoinColumn(name = "estimation_detail_id", referencedColumnName = "id")
	@ApiModelProperty(value = "見積明細", required = true, position = 11)
	private EstimationDetail estimationDetail;

}
