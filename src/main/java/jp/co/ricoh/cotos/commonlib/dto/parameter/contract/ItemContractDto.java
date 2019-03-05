package jp.co.ricoh.cotos.commonlib.dto.parameter.contract;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.DtoBase;
import jp.co.ricoh.cotos.commonlib.entity.master.ItemMaster.CostType;
import jp.co.ricoh.cotos.commonlib.entity.master.ItemMaster.ItemType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ItemContractDto extends DtoBase {

	/**
	 * 品種マスタID
	 */
	@Min(0)
	@ApiModelProperty(value = "品種マスタID", required = true, position = 3, allowableValues = "range[0,9223372036854775807]")
	private long itemMasterId;

	/**
	 * 商品マスタID
	 */
	@Min(0)
	@ApiModelProperty(value = "商品マスタID", required = true, position = 4, allowableValues = "range[0,9223372036854775807]")
	private long productMasterId;

	/**
	 * 品種名
	 */
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "品種名", required = true, position = 5, allowableValues = "range[0,255]")
	private String itemContractName;

	/**
	 * リコー品種コード
	 */
	@NotNull
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
	@ApiModelProperty(value = "費用種別", required = true, allowableValues = "初期費(\"1\"), 月額_定額(\"2\"), 年額(\"3\"), 月額_従量(\"4\")", example = "1", position = 8)
	private CostType costType;

	/**
	 * 仕切価格
	 */
	@NotNull
	@DecimalMin("0.00")
	@Digits(integer = 19, fraction = 2)
	@ApiModelProperty(value = "仕切価格", required = true, position = 9, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal partitionPrice;

	/**
	 * 仕入取引先コード
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "仕入取引先コード", required = false, position = 10, allowableValues = "range[0,255]")
	private String bpCd;

	/**
	 * Ｒ原価
	 */
	@DecimalMin("0.00")
	@Digits(integer = 19, fraction = 2)
	@ApiModelProperty(value = "Ｒ原価", required = false, position = 11, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal rCost;

	/**
	 * ＲＪ仕入価格
	 */
	@DecimalMin("0.00")
	@Digits(integer = 19, fraction = 2)
	@ApiModelProperty(value = "ＲＪ仕入価格", required = false, position = 12, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal rjPurchasePrice;

	/**
	 * ＲＪ仕切価格
	 */
	@DecimalMin("0.00")
	@Digits(integer = 19, fraction = 2)
	@ApiModelProperty(value = "ＲＪ仕切価格", required = false, position = 13, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal rjDividingPrice;

	/**
	 * 母店売価(接点店仕切)
	 */
	@DecimalMin("0.00")
	@Digits(integer = 19, fraction = 2)
	@ApiModelProperty(value = "母店売価(接点店仕切)", required = false, position = 14, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal motherStorePrice;

	/**
	 * 消費税区分
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "消費税区分", required = false, position = 15, allowableValues = "range[0,255]")
	private String taxFlag;
}
