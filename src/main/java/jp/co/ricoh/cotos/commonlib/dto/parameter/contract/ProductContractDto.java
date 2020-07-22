package jp.co.ricoh.cotos.commonlib.dto.parameter.contract;

import javax.persistence.Lob;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.DtoBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ProductContractDto extends DtoBase {

	/**
	 * 商品マスタID
	 */
	@Min(0)
	@ApiModelProperty(value = "商品マスタID", required = true, position = 3, allowableValues = "range[0,9223372036854775807]")
	private long productMasterId;

	/**
	 * 商品名
	 */
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "商品名", required = true, position = 4, allowableValues = "range[0,255]")
	private String productContractName;

	/**
	 * 代表品種マスタID
	 */
	@Min(0)
	@ApiModelProperty(value = "代表品種マスタID", required = false, position = 5, allowableValues = "range[0,9223372036854775807]")
	private Long repItemMasterId;

	/**
	 * サービス識別番号
	 */
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "サービス識別番号", required = true, position = 6, allowableValues = "range[0,255]")
	private String serviceIdentNumber;

	/**
	 * 拡張項目
	 */
	@ApiModelProperty(value = "拡張項目", required = false, position = 7)
	@Lob
	private String extendsParameter;
	
	/**
	 * 拡張項目繰返
	 */
	@ApiModelProperty(value = "拡張項目繰返", required = false, position = 8)
	@Lob
	private String extendsParameterIterance;
}
