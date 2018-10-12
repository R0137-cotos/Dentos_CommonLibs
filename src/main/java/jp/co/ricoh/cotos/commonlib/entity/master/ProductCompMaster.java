package jp.co.ricoh.cotos.commonlib.entity.master;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品構成マスタ
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "product_comp_master")
public class ProductCompMaster extends EntityBaseMaster {

	/**
	 * 商品構成マスタID
	 */
	@Id
	@ApiModelProperty(value = "商品構成マスタID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 商品グループマスタ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "product_grp_master_id")	// TODO ER図では外部キーの物理名が不明
	@ApiModelProperty(value = "商品グループマスタ", required = false, position = 3)
	private  ProductGrpMaster productGrpMaster;

	/**
	 * 商品グマスタ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "product_master_id")	// TODO ER図では外部キーの物理名が不明
	@ApiModelProperty(value = "商品グマスタ", required = false, position = 5)
	private  ProductMaster productMaster;

}
