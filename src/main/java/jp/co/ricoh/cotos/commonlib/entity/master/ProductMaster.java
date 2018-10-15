package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品マスタ
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "product_master")
public class ProductMaster extends EntityBaseMaster {

	@Id
 	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_master_seq")
 	@SequenceGenerator(name = "product_master_seq", sequenceName = "product_master_seq", allocationSize = 1)
	@ApiModelProperty(value = "商品マスタID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 商品名
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "商品名", required = true, position = 2, allowableValues = "range[0,255]")
	private String productName;

	/**
	 * 代表品種マスタID
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "代表品種マスタID", required = true, position = 3, allowableValues = "range[0,9999999999999999999]")
	private long repItemMasterId;

	/**
	 * 商品構成マスタ
	 */
	@OneToMany(mappedBy = "productMaster")
	@ApiModelProperty(value = "商品構成マスタ", required = false, position = 4)
	private List<ProductCompMaster> productCompMasterList;

	/**
	 * 品種マスタ
	 */
	@OneToMany(mappedBy = "productMaster")
	@ApiModelProperty(value = "品種マスタ", required = false, position = 5)
	private List<ItemMaster> itemMasterList;

	/**
	 * 見積チェックリスト構成マスタ
	 */
	@OneToMany(mappedBy = "productMaster")
	@ApiModelProperty(value = "チェックリスト構成マスタ", required = false, position = 6)
	private List<EstimationChecklistCompMaster> estimationChecklistCompMasterList;

	/**
	 * 契約チェックリスト構成マスタ
	 */
	@OneToMany(mappedBy = "productMaster")
	@ApiModelProperty(value = "チェックリスト構成マスタ", required = false, position = 7)
	private List<ContractChecklistCompMaster> contractChecklistCompMasterList;

}
