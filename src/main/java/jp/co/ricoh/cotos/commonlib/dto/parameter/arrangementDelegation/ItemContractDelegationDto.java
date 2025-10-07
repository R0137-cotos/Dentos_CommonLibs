package jp.co.ricoh.cotos.commonlib.dto.parameter.arrangementDelegation;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ItemContractDelegationDto {

	/**
	 * ID
	 */
	@NotNull
	@Min(0)
	@ApiModelProperty(value = "ID", required = true, position = 1, allowableValues = "range[0,9223372036854775807]")
	private long id;

	/**
	 * 品種マスタID
	 */
	@NotNull
	@Min(0)
	@ApiModelProperty(value = "品種マスタID", required = true, position = 2, allowableValues = "range[0,9223372036854775807]")
	private long itemMasterId;

	/**
	 * 商品マスタID
	 */
	@NotNull
	@Min(0)
	@ApiModelProperty(value = "商品マスタID", required = true, position = 3, allowableValues = "range[0,9223372036854775807]")
	private long productMasterId;

	/**
	 * 品種名
	 */
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "品種名", required = true, position = 4, allowableValues = "range[0,255]")
	private String itemContractName;

	/**
	 * リコー品種コード
	 */
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "リコー品種コード", required = true, position = 5, allowableValues = "range[0,255]")
	private String ricohItemCode;

}
