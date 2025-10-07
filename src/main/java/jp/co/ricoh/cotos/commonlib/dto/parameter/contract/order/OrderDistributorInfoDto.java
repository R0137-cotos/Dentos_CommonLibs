package jp.co.ricoh.cotos.commonlib.dto.parameter.contract.order;

import jakarta.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 注文販売店情報DTO
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class OrderDistributorInfoDto {

	/**
	 * 販売店コード
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "販売店コード", required = false, position = 2, allowableValues = "range[0,255]")
	private String distributorCd;

	/**
	 * 販売店名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "販売店名", required = false, position = 3, allowableValues = "range[0,255]")
	private String distributorName;

	/**
	 * OE届け先コード
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "OE届け先コード", required = false, position = 4, allowableValues = "range[0,255]")
	private String oeDeliveryCd;

	/**
	 * 販売店区分
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "販売店区分", required = false, position = 5, allowableValues = "range[0,255]")
	private String distributorCustomerType;

	/**
	 * 販売店担当営業
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "販売店担当営業", required = false, position = 6, allowableValues = "range[0,255]")
	private String distributorEmployeeName;

	/**
	 * 販売店担当営業メールアドレス
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "販売店担当営業メールアドレス", required = false, position = 7, allowableValues = "range[0,255]")
	private String distributorEmployeeMailAddress;

	/**
	 * Rings得意先コード
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "Rings得意先コード", required = false, position = 8, allowableValues = "range[0,255]")
	private String ringsCustomerCd;

	/**
	 * 販売店郵便番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "販売店郵便番号", required = false, position = 9, allowableValues = "range[0,255]")
	private String distributorPostNumber;

	/**
	 * 販売店住所
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "販売店住所", required = false, position = 10, allowableValues = "range[0,255]")
	private String distributorAddress;

}
