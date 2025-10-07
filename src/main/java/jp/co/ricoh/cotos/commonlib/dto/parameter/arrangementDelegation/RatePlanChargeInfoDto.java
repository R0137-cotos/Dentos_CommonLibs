package jp.co.ricoh.cotos.commonlib.dto.parameter.arrangementDelegation;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RatePlanChargeInfoDto {

	/**
	 * プロダクトID
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "プロダクトID", required = false, position = 1, allowableValues = "range[0,255]")
	private String productId;

	/**
	 * 料金プランID
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "料金プランID", required = false, position = 2, allowableValues = "range[0,255]")
	private String productRatePlanId;

	/**
	 * 料金プランチャージID
	 */
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "料金プランチャージID", required = true, position = 3, allowableValues = "range[0,255]")
	private String productRatePlanChargeId;

	/**
	 * レートプランチャージID
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "レートプランチャージID", required = false, position = 4, allowableValues = "range[0,255]")
	private String ratePlanChargeId;

}
