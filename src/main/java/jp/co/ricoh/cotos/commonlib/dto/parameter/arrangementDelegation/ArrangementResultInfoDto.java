package jp.co.ricoh.cotos.commonlib.dto.parameter.arrangementDelegation;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ArrangementResultInfoDto {

	/**
	 * 手配結果コード
	 */
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "手配結果コード", required = true, position = 1, allowableValues = "range[0,255]")
	private String resultCode;

	/**
	 * エラー情報
	 */
	@Valid
	@ApiModelProperty(value = "エラー情報", required = false, position = 2)
	private ErrorInfoDto errorInfo;

}
