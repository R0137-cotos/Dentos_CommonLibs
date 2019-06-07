package jp.co.ricoh.cotos.commonlib.dto.parameter.contract.order;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.DtoBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 注文商品グループ情報DTO
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class OrderProductGroupInfoDto extends DtoBase {

	/**
	 * 商品グループコード
	 */
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "商品グループコード", required = false, position = 2, allowableValues = "range[0,]")
	private String productGroupCd;

	/**
	 * 商品グループ名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "商品グループ名", required = false, position = 3, allowableValues = "range[0,]")
	private String productGroupName;

}
