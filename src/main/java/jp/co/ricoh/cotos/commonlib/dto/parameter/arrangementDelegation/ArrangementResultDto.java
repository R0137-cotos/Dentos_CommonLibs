package jp.co.ricoh.cotos.commonlib.dto.parameter.arrangementDelegation;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * 手配結果登録APIリクエストパラメータDTO
 *
 */

@Data
public class ArrangementResultDto {

	/**
	 * 手配結果情報
	 */
	@Valid
	@NotNull
	@ApiModelProperty(value = "手配結果情報", required = true, position = 1)
	private ArrangementResultInfoDto arrangementResultInfo;

	/**
	 * 契約情報
	 */
	@Valid
	@NotNull
	@ApiModelProperty(value = "契約情報", required = true, position = 2)
	private ContractInfoDto contructInfo;

}
