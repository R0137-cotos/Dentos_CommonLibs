package jp.co.ricoh.cotos.commonlib.dto.parameter.contract;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.DtoBase;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.InitialRunningDiv;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ItemDetailContractDto extends DtoBase {

	/**
	 * 原価
	 */
	@Digits(integer = 19, fraction = 2)
	@DecimalMin("0.00")
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "原価", required = false, position = 3, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal price;

	/**
	 * 振替先課所コード
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "振替先課所コード", required = false, position = 4, allowableValues = "range[0,255]")
	private String transToServiceOrgCode;

	/**
	 * 振替先課所名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "振替先課所名", required = false, position = 5, allowableValues = "range[0,255]")
	private String transToServiceOrgName;

	/**
	 * イニシャル/ランニング区分
	 */
	@ApiModelProperty(value = "イニシャル/ランニング区分", required = false, position = 6, allowableValues = "イニシャル(\"1\"), ランニング(\"2\"), 期間売(\"3\")")
	private InitialRunningDiv initialRunningDiv;
}
