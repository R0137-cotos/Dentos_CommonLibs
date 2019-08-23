package jp.co.ricoh.cotos.commonlib.dto.parameter.contract.detail;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.validation.constraints.Min;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品（契約用）を表す契約一覧情報詳細取得API用DTO
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class ProductContractForFindAllDetailsDto extends EntityBase {

	@Min(0)
	@ApiModelProperty(value = "契約ID(作成時不要)", required = true, position = 1, allowableValues = "range[0,9223372036854775807]", readOnly = true)
	private long id;

	/**
	 * 商品マスタID
	 */
	@Min(0)
	@Column(nullable = false)
	@ApiModelProperty(value = "商品マスタID", required = true, position = 2, allowableValues = "range[0,9223372036854775807]")
	private long productMasterId;

	/**
	 * 商品名
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "商品名(作成時不要)", required = true, position = 3, allowableValues = "range[0,255]", readOnly = true)
	private String productContractName;

	/**
	 * 代表品種マスタID
	 */
	@Min(0)
	@ApiModelProperty(value = "代表品種マスタID", required = false, position = 4, allowableValues = "range[0,9223372036854775807]")
	private Long repItemMasterId;

	/**
	 * サービス識別番号
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "サービス識別番号(作成時不要)", required = true, position = 5, allowableValues = "range[0,255]", readOnly = true)
	private String serviceIdentNumber;

	/**
	 * Bplats連携対象外
	 */
	@ApiModelProperty(value = "Bplats連携対象外", required = false, position = 6, readOnly = true)
	private Boolean extendBplatsLink;

	/**
	 * 拡張項目
	 */
	@ApiModelProperty(value = "拡張項目", required = false, position = 8)
	@Lob
	private String extendsParameter;

}
