package jp.co.ricoh.cotos.commonlib.entity.master;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_comp_master_seq")
	@SequenceGenerator(name = "product_comp_master_seq", sequenceName = "product_comp_master_seq", allocationSize = 1)
	@ApiModelProperty(value = "商品構成マスタID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 商品グループマスタ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "product_grp_master_id", referencedColumnName = "id")
	@ApiModelProperty(value = "商品グループマスタ", required = true, position = 2)
	private ProductGrpMaster productGrpMaster;

	/**
	 * 商品マスタ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "product_master_id", referencedColumnName = "id")
	@ApiModelProperty(value = "商品マスタ", required = true, position = 3)
	private ProductMaster productMaster;

}
