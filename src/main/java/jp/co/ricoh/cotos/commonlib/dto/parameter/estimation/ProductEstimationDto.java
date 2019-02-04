package jp.co.ricoh.cotos.commonlib.dto.parameter.estimation;

import javax.persistence.Lob;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.DtoBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ProductEstimationDto extends DtoBase {

	/**
	 * 商品マスタID
	 */
	@ApiModelProperty(value = "商品マスタID", required = true, position = 3, allowableValues = "range[0,9999999999999999999]")
	private long productMasterId;

	/**
	 * 代表品種マスタID
	 */
	@ApiModelProperty(value = "代表品種マスタID", required = true, position = 4, allowableValues = "range[0,9999999999999999999]")
	private long repItemMasterId;

	/**
	 * 拡張項目
	 */
	@ApiModelProperty(value = "拡張項目", required = false, position = 5)
	@Lob
	private String extendsParameter;
}
