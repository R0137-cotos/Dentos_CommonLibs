package jp.co.ricoh.cotos.commonlib.entity.master;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 品種を表すEntity
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "item_master")
public class ItemMaster extends EntityBaseMaster {

	@Id
	@ApiModelProperty(value = "品種マスタID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 商品マスタ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "product_id", referencedColumnName = "id")
	@ApiModelProperty(value = "商品マスタ", required = true, position = 2)
	private ProductMaster productMaster;

	/**
	 * 品種名
	 */
	@ApiModelProperty(value = "品種名", required = true, position = 3, allowableValues = "range[0,255]")
	private String name;

	/**
	 * リコー品種コード
	 */
	@ApiModelProperty(value = "リコー品種コード", required = true, position = 4, allowableValues = "range[0,255]")
	private String ricohItemCode;

	/**
	 * 商品区分
	 */
	@ApiModelProperty(value = "商品区分", required = true, position = 5, allowableValues = "range[0,255]")
	private String productType;

	/**
	 * 費用種別
	 */
	@ApiModelProperty(value = "費用種別", required = true, position = 6, allowableValues = "range[0,255]")
	private String costType;

	/**
	 * 仕切価格
	 */
	@ApiModelProperty(value = "仕切価格", required = true, position = 7, allowableValues = "range[0.00,9999999999999999999.99]")
	@Pattern(regexp = "9999999999999999999.99")
	private BigDecimal partitionPrice;

	/**
	 * 積上げ可能期間（開始日）
	 */
	@ApiModelProperty(value = "積上げ可能期間（開始日）", required = true, position = 8, allowableValues = "range[0,19]")
	@Pattern(regexp = "YYYY-MM-DD HH:mm:ss")
	private String effectiveFrom;

	/**
	 * 積上げ可能期間（終了日）
	 */
	@ApiModelProperty(value = "積上げ可能期間（終了日）", required = true, position = 9, allowableValues = "range[0,19]")
	@Pattern(regexp = "YYYY-MM-DD HH:mm:ss")
	private String effectiveTo;

	/**
	 * 計上分解構成マスタ
	 */
	@OneToMany(mappedBy = "itemMaster")
	@ApiModelProperty(value = "計上分解構成マスタ", required = false, position = 10)
	private List<RecordDecomposeCompMaster> recordDecomposeCompMasterList;

	/**
	 * 手配業務構成マスタ
	 */
	@OneToMany(mappedBy = "itemMaster")
	@ApiModelProperty(value = "手配業務構成マスタ", required = false, position = 11)
	private List<ArrangementWorkCompMaster> arrangementWorkCompMasterList;

}
