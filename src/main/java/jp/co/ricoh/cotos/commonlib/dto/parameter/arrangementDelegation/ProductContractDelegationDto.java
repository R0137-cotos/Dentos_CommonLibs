package jp.co.ricoh.cotos.commonlib.dto.parameter.arrangementDelegation;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductContractDelegationDto {

	/**
	 * ID
	 */
	@NotNull
	@Min(0)
	@ApiModelProperty(value = "ID", required = true, position = 1, allowableValues = "range[0,9223372036854775807]")
	private long id;

	/**
	 * 商品マスタID
	 */
	@NotNull
	@Min(0)
	@ApiModelProperty(value = "商品マスタID", required = true, position = 2, allowableValues = "range[0,9223372036854775807]")
	private long productMasterId;

	/**
	 * 商品名
	 */
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "商品名", required = true, position = 3, allowableValues = "range[0,255]")
	private String productContractName;

	/**
	 * テナントID
	 */
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "テナントID", required = true, position = 4, allowableValues = "range[0,255]")
	private String tenantId;

	/**
	 * ZuoraアカウントId
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "ZuoraアカウントId", required = false, position = 5, allowableValues = "range[0,255]")
	private String zuoraAccountId;

	/**
	 * ユーザID
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "ユーザID", required = false, position = 6, allowableValues = "range[0,255]")
	private String userId;

	/**
	 * サブスクリプション番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "サブスクリプション番号", required = false, position = 7, allowableValues = "range[0,255]")
	private String subscriptionNumber;

}
