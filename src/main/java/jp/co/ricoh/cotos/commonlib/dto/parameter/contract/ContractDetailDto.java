package jp.co.ricoh.cotos.commonlib.dto.parameter.contract;

import java.math.BigDecimal;

import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.DtoBase;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.DetailStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ContractDetailDto extends DtoBase {

	/**
	 * 状態
	 */
	@NotNull
	@ApiModelProperty(value = "状態", required = true, allowableValues = "NOUPDATE(\"1\"), ADD(\"2\"), DELETE(\"3\")", example = "1", position = 3)
	private DetailStatus state;

	/**
	 * 数量
	 */
	@Max(99999)
	@ApiModelProperty(value = "数量", required = true, position = 4, allowableValues = "range[0,99999]")
	private int quantity;

	/**
	 * 見積金額
	 */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "見積金額", required = false, position = 5, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal estimationAmountSummary;

	/**
	 * 摘要
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "摘要", required = false, position = 6, allowableValues = "range[0,255]")
	private String detailAbstract;

	/**
	 * 拡張項目
	 */
	@Lob
	@ApiModelProperty(value = "拡張項目", required = false, position = 7)
	private String extendsParameter;

	@OneToOne(mappedBy = "contractDetail")
	@ApiModelProperty(value = "品種(契約用)", required = true, position = 8)
	private ItemContractDto itemContract;
}
