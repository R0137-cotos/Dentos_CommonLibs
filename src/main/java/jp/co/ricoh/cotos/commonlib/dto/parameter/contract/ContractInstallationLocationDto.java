package jp.co.ricoh.cotos.commonlib.dto.parameter.contract;

import jakarta.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.CustomerAbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ContractInstallationLocationDto extends CustomerAbstractDto {

	/**
	 * MoM非連携_企業代表者名（カナ）
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_企業代表者名（カナ）", required = false, position = 3, allowableValues = "range[0,255]")
	private String companyRepresentativeNameKana;

	/**
	 * MoM非連携_郵便番号(手入力)
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_郵便番号(手入力)", required = false, position = 72, allowableValues = "range[0,255]")
	private String inputPostNumber;

	/**
	 * MoM非連携_住所(手入力)
	 */
	@Size(max = 1000)
	@ApiModelProperty(value = "MoM非連携_住所(手入力)", required = false, position = 73, allowableValues = "range[0,1000]")
	private String inputAddress;
}
