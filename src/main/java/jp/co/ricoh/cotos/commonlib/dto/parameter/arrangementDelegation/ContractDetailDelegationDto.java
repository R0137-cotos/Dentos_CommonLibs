package jp.co.ricoh.cotos.commonlib.dto.parameter.arrangementDelegation;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ContractDetailDelegationDto {

	/**
	 * 契約明細ID
	 */
	@NotNull
	@Min(0)
	@ApiModelProperty(value = "契約明細ID", required = true, position = 1, allowableValues = "range[0,9223372036854775807]")
	private long id;

	/**
	 * 品種(契約用)
	 */
	@Valid
	@NotNull
	@ApiModelProperty(value = "品種(契約用)", required = true, position = 2)
	private ItemContractDelegationDto itemContract;

	/**
	 * レートプランチャージ情報
	 */
	@Valid
	@NotNull
	@ApiModelProperty(value = "レートプランチャージ情報", required = true, position = 3)
	private RatePlanChargeInfoDto ratePlanChargeInfo;

}
