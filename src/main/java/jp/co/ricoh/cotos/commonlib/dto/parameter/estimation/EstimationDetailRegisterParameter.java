package jp.co.ricoh.cotos.commonlib.dto.parameter.estimation;

import java.math.BigDecimal;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class EstimationDetailRegisterParameter {

	/**
	 * ステータス
	 */
	@NotNull
	@Size(max = 255)
	@ApiParam(value = "ステータス", allowableValues = "range[0,255]", required = true)
	private String status;

	/**
	 * 品種コード
	 */
	@NotNull
	@Size(max = 255)
	@ApiParam(value = "品種コード", allowableValues = "range[0,255]", required = true)
	private String ricohItemCode;
	
	/**
	 * 品種名
	 */
	@Size(max = 255)
	@ApiParam(value = "品種名", allowableValues = "range[0,255]", required = true)
	private String ricohItemName;

	/**
	 * 変更前数量
	 */
	@Max(99999)
	@Min(0)
	@ApiModelProperty(value = "変更前数量", allowableValues = "range[0,99999]", required = false)
	private Integer beforeQuantity;

	/**
	 * 数量
	 */
	@Min(0)
	@Max(99999)
	@ApiParam(value = "数量", allowableValues = "range[0,99999]", required = true)
	private int quantity;

	/**
	 * 見積単価
	 */
	@ApiParam(value = "見積単価", allowableValues = "range[0.00,9999999999999999999.99]", required = true)
	private BigDecimal unitPrice;

	/**
	 * 見積金額
	 */
	@NotNull
	@DecimalMin("0.00")
	@Digits(integer = 19, fraction = 2)
	@ApiParam(value = "見積金額", allowableValues = "range[0.00,9999999999999999999.99]", required = true)
	private BigDecimal amountSummary;

	/**
	 * R原価
	 */
	@DecimalMin("0.00")
	@Digits(integer = 19, fraction = 2)
	@ApiParam(value = "R原価", allowableValues = "range[0.00,9999999999999999999.99]", required = true)
	private BigDecimal rCost;

	/**
	 * ＲＪ仕入価格
	 */
	@DecimalMin("0.00")
	@Digits(integer = 19, fraction = 2)
	@ApiParam(value = "ＲＪ仕入価格", allowableValues = "range[0.00,9999999999999999999.99]", required = true)
	private BigDecimal rjPurchasePrice;

	/**
	 * ＲＪ仕切価格
	 */
	@DecimalMin("0.00")
	@Digits(integer = 19, fraction = 2)
	@ApiParam(value = "ＲＪ仕切価格", allowableValues = "range[0.00,9999999999999999999.99]", required = true)
	private BigDecimal rjDividingPrice;

	/**
	 * 母店売価(接点店仕切)
	 */
	@DecimalMin("0.00")
	@Digits(integer = 19, fraction = 2)
	@ApiParam(value = "母店売価(接点店仕切)", allowableValues = "range[0.00,9999999999999999999.99]", required = true)
	private BigDecimal motherStorePrice;

	/**
	 * 拡張項目
	 */
	@ApiParam(value = "拡張項目", required = false)
	@Lob
	private String extendsParameter;

}
