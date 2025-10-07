package jp.co.ricoh.cotos.commonlib.dto.parameter.contract;

import jakarta.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.DealerAbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class DealerContractDto extends DealerAbstractDto {

	/**
	 * 販売店コード
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "販売店コード", required = false, position = 3, allowableValues = "range[0,255]")
	private String distributorCd;

	/**
	 * OE届け先コード
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "OE届け先コード", required = false, position = 4, allowableValues = "range[0,255]")
	private String oeDeliveryCd;

	/**
	 * 担当営業メールアドレス
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "担当営業メールアドレス", required = false, position = 5, allowableValues = "range[0,255]")
	private String distributorEmployeeMailAddress;

	/**
	 * Rings得意先コード
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "Rings得意先コード", required = false, position = 6, allowableValues = "range[0,255]")
	private String ringsCustomerCd;

	/**
	 * 取引先コード（手数料用）
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "取引先コード（手数料用）", required = false, position = 7, allowableValues = "range[0,255]")
	private String distributorRtcCd;

}
