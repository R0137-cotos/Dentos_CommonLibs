package jp.co.ricoh.cotos.commonlib.dto.parameter.contract.order;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.DtoBase;
import jp.co.ricoh.cotos.commonlib.entity.contract.order.OrderProductInfo.ChargeRule;
import jp.co.ricoh.cotos.commonlib.entity.contract.order.OrderProductInfo.ProvideMethod;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 注文商品情報DTO
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class OrderProductInfoDto extends DtoBase {

	/**
	 * 商品コード（RICOH品種コード）
	 */
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "商品コード（RICOH品種コード）", required = false, position = 2, allowableValues = "range[0,]")
	private String productCd;

	/**
	 * 課金制約ルール
	 */
	@ApiModelProperty(value = "課金制約ルール", required = false, position = 3, allowableValues = "range[0,]")
	private ChargeRule chargeRule;

	/**
	 * 無料期間
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "無料期間", required = false, position = 4, allowableValues = "range[0,]")
	private String freePeriod;

	/**
	 * 商品名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "商品名", required = false, position = 5, allowableValues = "range[0,]")
	private String productName;

	/**
	 * 提供方法
	 */
	@ApiModelProperty(value = "提供方法", required = false, position = 6, allowableValues = "range[0,]")
	private ProvideMethod provideMethod;

	/**
	 * 変更後数量
	 */
	@NotNull
	@ApiModelProperty(value = "変更後数量", required = false, position = 7)
	private Integer quantity;

	/**
	 * 変更前数量
	 */
	@ApiModelProperty(value = "変更前数量", required = false, position = 8)
	private Integer beforeQuantity;

	/**
	 * 差分
	 */
	@ApiModelProperty(value = "差分", required = false, position = 9)
	private Integer differenceQuantity;

	/**
	 * 売価単価
	 */
	@NotNull
	@ApiModelProperty(value = "売価単価", required = false, position = 10)
	private BigDecimal unitPrice;

	/**
	 * 売価合計
	 */
	@ApiModelProperty(value = "売価合計", required = false, position = 11)
	private BigDecimal amountSummary;

}
