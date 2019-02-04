package jp.co.ricoh.cotos.commonlib.dto.parameter.estimation;

import java.math.BigDecimal;

import javax.persistence.OneToOne;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.DetailStatus;
import lombok.Data;

@Data
public class EstimationDetailDto {

	/**
	 * ID
	 */
	@ApiModelProperty(value = "ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * version
	 */
	@ApiModelProperty(value = "version", required = true, position = 2, allowableValues = "range[0,9999999999999999999]")
	private long version;

	/**
	 * 状態
	 */
	@ApiModelProperty(value = "状態", required = true, allowableValues = "NOUPDATE(\"1\"), ADD(\"2\"), DELETE(\"3\")", example = "1", position = 3)
	private DetailStatus state;

	/**
	 * 数量
	 */
	@ApiModelProperty(value = "数量", required = true, position = 4, allowableValues = "range[0,99999]")
	private int quantity;

	/**
	 * 見積金額
	 */
	@ApiModelProperty(value = "見積金額", required = false, position = 5, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal estimationAmountSummary;

	/**
	 * 摘要
	 */
	@ApiModelProperty(value = "摘要", required = false, position = 6, allowableValues = "range[0,255]")
	private String detailAbstract;

	/**
	 * 拡張項目
	 */
	@ApiModelProperty(value = "拡張項目", required = false, position = 7)
	private String extendsParameter;

	@OneToOne(mappedBy = "estimationDetail")
	@ApiModelProperty(value = "品種(見積用)", required = true, position = 8)
	private ItemEstimationDto itemEstimation;
}
