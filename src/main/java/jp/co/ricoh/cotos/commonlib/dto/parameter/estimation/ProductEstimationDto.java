package jp.co.ricoh.cotos.commonlib.dto.parameter.estimation;

import javax.persistence.Lob;
import javax.persistence.Version;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProductEstimationDto {

	/**
	 * ID
	 */
	@ApiModelProperty(value = "ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * version
	 */
	@Version
	@ApiModelProperty(value = "version", required = true, position = 2, allowableValues = "range[0,9999999999999999999]")
	private long version;

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
