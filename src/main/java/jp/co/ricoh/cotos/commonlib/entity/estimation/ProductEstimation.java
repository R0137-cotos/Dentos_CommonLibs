package jp.co.ricoh.cotos.commonlib.entity.estimation;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.master.ItemMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.ItemMaster.CostType;
import jp.co.ricoh.cotos.commonlib.entity.master.ItemMaster.ItemType;
import jp.co.ricoh.cotos.commonlib.entity.master.ProductMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品を表すEntity
 */

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "product_estimation")
public class ProductEstimation extends EntityBase {

	@Id
	@ApiModelProperty(value = "ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 商品マスタ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "product_master_id", referencedColumnName = "id")
	@ApiModelProperty(value = "商品マスタ", required = true, position = 2)
	private ProductMaster productMaster;
	
	/**
	 * 商品名
	 */
	@ApiModelProperty(value = "商品名", required = true, position = 3, allowableValues = "range[0,255]")
	private String productEstimationName;
	
	/**
	 * 代表品種マスタID
	 */
	@ApiModelProperty(value = "代表品種マスタID", required = true, position = 4, allowableValues = "range[0,9999999999999999999]")
	private long repItemMasterId;
	
	/**
	 * 積上げ可能期間（開始日）
	 */
	@ApiModelProperty(value = "積上げ可能期間（開始日）", required = true, position = 5, readOnly = true)
	private Date effectiveFrom;
	
	/**
	 * 積上げ可能期間（終了日）
	 */
	@ApiModelProperty(value = "積上げ可能期間（終了日）", required = true, position = 6, readOnly = true)
	private Date effectiveTo;
	
	/**
	 * サービス識別番号
	 */
	@ApiModelProperty(value = "サービス識別番号", required = false, position = 7, allowableValues = "range[0,255]")
	private String serviceIdentNumber;
	
	/**
	 * 見積
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "estimation_id", referencedColumnName = "id")
	@ApiModelProperty(value = "見積", required = true, position = 8)
	private Estimation estimation;
	
	/**
	 * 拡張項目
	 */
	@ApiModelProperty(value = "拡張項目", required = false, position = 9)
	private String extendsParameter;
	
}
