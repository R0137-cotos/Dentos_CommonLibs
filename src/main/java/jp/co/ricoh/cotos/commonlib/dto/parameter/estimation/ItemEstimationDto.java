package jp.co.ricoh.cotos.commonlib.dto.parameter.estimation;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Version;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.master.ItemMaster.CostType;
import jp.co.ricoh.cotos.commonlib.entity.master.ItemMaster.ItemType;
import lombok.Data;

@Data
public class ItemEstimationDto {

	/**
	 * ID
	 */
	@ApiModelProperty(value = "ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * version
	 */
	@Version
	@ApiModelProperty(value = "version", required = true, position = 2, allowableValues = "range[0,9999999999999999999]")
	private long version;

	/**
	 * 品種マスタID
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "品種マスタID", required = true, position = 3, allowableValues = "range[0,9999999999999999999]")
	private long itemMasterId;

	/**
	 * 商品マスタ
	 */
	@ApiModelProperty(value = "商品マスタ", required = true, position = 4, allowableValues = "range[0,9999999999999999999]")
	private long productMasterId;

	/**
	 * 品種名
	 */
	@NotEmpty
	@Size(max = 255)
	@ApiModelProperty(value = "品種名", required = true, position = 5, allowableValues = "range[0,255]")
	private String itemEstimationName;

	/**
	 * リコー品種コード
	 */
	@NotEmpty
	@Size(max = 255)
	@ApiModelProperty(value = "リコー品種コード", required = true, position = 6, allowableValues = "range[0,255]")
	private String ricohItemCode;

	/**
	 * 品種区分
	 */
	@NotNull
	@ApiModelProperty(value = "品種区分", required = true, allowableValues = "なし(\"0\"), 基本(\"1\"), オプション(\"2\")", example = "1", position = 7)
	private ItemType itemType;

	/**
	 * 費用種別
	 */
	@NotNull
	@ApiModelProperty(value = "費用種別", required = true, allowableValues = "初期費(\"1\"), 月額(\"2\"), 年額(\"3\")", example = "1", position = 8)
	private CostType costType;

	/**
	 * 仕切価格
	 */
	@NotNull
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "仕切価格", required = true, position = 9, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal partitionPrice;
}
