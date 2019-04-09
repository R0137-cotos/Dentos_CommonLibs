package jp.co.ricoh.cotos.commonlib.dto.parameter.contract;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.DtoBase;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.DetailStatus;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.InitialAccountSalesStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ContractDetailDto extends DtoBase {

	/**
	 * 状態
	 */
	@NotNull
	@ApiModelProperty(value = "状態", required = true, allowableValues = "NONE(\"1\"), ADD(\"2\"), DELETE(\"3\")", example = "", position = 3)
	private DetailStatus state;

	/**
	 * 数量
	 */
	@Min(0)
	@Max(99999)
	@ApiModelProperty(value = "数量", required = true, position = 4, allowableValues = "range[0,99999]")
	private int quantity;

	/**
	 * 単価
	 */
	@NotNull
	@DecimalMin("0.00")
	@Digits(integer = 19, fraction = 2)
	@ApiModelProperty(value = "単価", required = true, position = 5, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal unitPrice;

	/**
	 * 金額
	 */
	@NotNull
	@DecimalMin("0.00")
	@Digits(integer = 19, fraction = 2)
	@ApiModelProperty(value = "金額", required = true, position = 6, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal amountSummary;

	/**
	 * 摘要
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "摘要", required = false, position = 7, allowableValues = "range[0,255]")
	private String detailAbstract;

	/**
	 * 拡張項目
	 */
	@ApiModelProperty(value = "拡張項目", required = false, position = 8)
	@Lob
	private String extendsParameter;

	/**
	 * イニシャル売上計上処理状態
	 */
	@ApiModelProperty(value = "イニシャル売上計上処理状態", required = false, position = 9)
	private InitialAccountSalesStatus initialAccountSalesStatus;

	/**
	 * イニシャル売上計上処理日
	 */
	@ApiModelProperty(value = "イニシャル売上計上処理日", required = false, position = 10)
	@Temporal(TemporalType.DATE)
	private Date initialAccountSalesDate;

	@Valid
	@NotNull
	@OneToOne(mappedBy = "contractDetail")
	@ApiModelProperty(value = "品種(契約用)", required = true, position = 11)
	private ItemContractDto itemContract;
}
