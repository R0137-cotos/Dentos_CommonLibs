package jp.co.ricoh.cotos.commonlib.dto.parameter.estimation;

import javax.persistence.Lob;
import javax.persistence.Version;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

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
	@NotEmpty
	@Size(max = 255)
	@ApiModelProperty(value = "商品名", required = true, position = 3, allowableValues = "range[0,255]")
	private String productEstimationName;

	/**
	 * 代表品種マスタID
	 */
	@ApiModelProperty(value = "代表品種マスタID", required = true, position = 4, allowableValues = "range[0,9999999999999999999]")
	@Lob
	private long repItemMasterId;

	/**
	 * 拡張項目
	 */
	@ApiModelProperty(value = "拡張項目", required = false, position = 5)
	private String extendsParameter;
}
