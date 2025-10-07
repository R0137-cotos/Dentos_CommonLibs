package jp.co.ricoh.cotos.commonlib.dto.parameter.contract.order;

import jakarta.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 注文商品グループ情報DTO
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class OrderProductGroupInfoDto {

	/**
	 * 商品グループコード
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "商品グループコード", required = true, position = 2, allowableValues = "range[0,255]")
	private String productGroupCd;

	/**
	 * 商品グループ名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "商品グループ名", required = false, position = 3, allowableValues = "range[0,255]")
	private String productGroupName;

}
