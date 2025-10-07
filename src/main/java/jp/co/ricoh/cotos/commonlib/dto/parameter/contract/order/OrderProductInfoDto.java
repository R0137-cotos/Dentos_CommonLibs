package jp.co.ricoh.cotos.commonlib.dto.parameter.contract.order;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.contract.order.OrderProductInfo.ChargeRule;
import jp.co.ricoh.cotos.commonlib.entity.contract.order.OrderProductInfo.ProvideMethod;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 注文商品情報DTO
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class OrderProductInfoDto {

	/**
	 * 商品コード（RICOH品種コード）
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "商品コード（RICOH品種コード）", required = true, position = 2, allowableValues = "range[0,255]")
	private String productCd;

	/**
	 * 課金制約ルール
	 */
	@ApiModelProperty(value = "課金制約ルール", required = false, position = 3, allowableValues = "有料(\"0\"), 初月無料(\"1\"), 無料期間指定(\"2\")")
	private ChargeRule chargeRule;

	/**
	 * 無料期間
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "無料期間", required = false, position = 4, allowableValues = "range[0,255]")
	private String freePeriod;

	/**
	 * 商品名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "商品名", required = false, position = 5, allowableValues = "range[0,255]")
	private String productName;

	/**
	 * 提供方法
	 */
	@ApiModelProperty(value = "提供方法", required = false, position = 6, allowableValues = "初期(\"1\"), 月額(\"2\"), 年額(\"3\")")
	private ProvideMethod provideMethod;

	/**
	 * 変更後数量
	 */
	@Min(0)
	@Max(99999)
	@ApiModelProperty(value = "変更後数量", required = false, position = 7, allowableValues = "range[0,99999]")
	private Integer quantity;

	/**
	 * 変更前数量
	 */
	@Min(0)
	@Max(99999)
	@ApiModelProperty(value = "変更前数量", required = true, position = 8, allowableValues = "range[0,99999]")
	private Integer beforeQuantity;

	/**
	 * 差分
	 */
	@Max(99999)
	@ApiModelProperty(value = "差分", required = false, position = 9, allowableValues = "range[0,99999]")
	private Integer differenceQuantity;

	/**
	 * 売価単価
	 */
	@DecimalMin("0.00")
	@Digits(integer = 19, fraction = 2)
	@ApiModelProperty(value = "売価単価", required = true, position = 10, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal unitPrice;

	/**
	 * 売価合計
	 */
	@DecimalMin("0.00")
	@Digits(integer = 19, fraction = 2)
	@ApiModelProperty(value = "売価合計", required = false, position = 11, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal amountSummary;

}
