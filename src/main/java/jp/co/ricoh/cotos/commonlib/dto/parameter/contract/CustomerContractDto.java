package jp.co.ricoh.cotos.commonlib.dto.parameter.contract;

import jakarta.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.CustomerAbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class CustomerContractDto extends CustomerAbstractDto {

	/**
	 * NetRicoh会員ID
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "NetRicoh会員ID", required = false, position = 4, allowableValues = "range[0,255]")
	private String netricohAccount;

	/**
	 * 設置先名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "設置先名", required = false, position = 5, allowableValues = "range[0,255]")
	private String setupCorpNm;

	/**
	 * 設置先郵便番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "設置先郵便番号", required = false, position = 6, allowableValues = "range[0,255]")
	private String setupPostCd;

	/**
	 * 設置先住所
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "設置先住所", required = false, position = 7, allowableValues = "range[0,255]")
	private String setupAddr;

	/**
	 * 設置先電話番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "設置先電話番号", required = false, position = 8, allowableValues = "range[0,255]")
	private String setupTel;
}
