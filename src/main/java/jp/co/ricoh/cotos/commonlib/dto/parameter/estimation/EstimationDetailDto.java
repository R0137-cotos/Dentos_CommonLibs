package jp.co.ricoh.cotos.commonlib.dto.parameter.estimation;

import java.math.BigDecimal;

import jakarta.persistence.Lob;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.DtoBase;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.DetailStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class EstimationDetailDto extends DtoBase {
	/**
	 * 状態
	 */
	@NotNull
	@ApiModelProperty(value = "状態", required = true, allowableValues = "NOUPDATE(\"1\"), ADD(\"2\"), DELETE(\"3\"), UPDATE(\"4\")", example = "1", position = 3)
	private DetailStatus state;

	/**
	 * 変更前数量
	 */
	@Max(99999)
	@Min(0)
	@ApiModelProperty(value = "変更前数量", required = false, position = 4, allowableValues = "range[0,99999]")
	private Integer beforeQuantity;

	/**
	 * 数量
	 */
	@Min(0)
	@Max(99999)
	@ApiModelProperty(value = "数量", required = true, position = 5, allowableValues = "range[0,99999]")
	private int quantity;

	/**
	 * 見積単価
	 */
	@NotNull
	@DecimalMin("0.00")
	@Digits(integer = 19, fraction = 2)
	@ApiModelProperty(value = "見積単価", required = true, position = 6, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal estimationUnitPrice;

	/**
	 * 見積金額
	 */
	@NotNull
	@DecimalMin("0.00")
	@Digits(integer = 19, fraction = 2)
	@ApiModelProperty(value = "見積金額", required = true, position = 7, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal estimationAmountSummary;

	/**
	 * 摘要
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "摘要", required = false, position = 8, allowableValues = "range[0,255]")
	private String detailAbstract;

	/**
	 * 拡張項目
	 */
	@Lob
	@ApiModelProperty(value = "拡張項目", required = false, position = 9)
	private String extendsParameter;

	/**
	 * 品種(見積用)
	 */
	@Valid
	@NotNull
	@ApiModelProperty(value = "品種(見積用)", required = true, position = 10)
	private ItemEstimationDto itemEstimation;
}
