package jp.co.ricoh.cotos.commonlib.dto.parameter.arrangementDelegation;

import jakarta.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ErrorInfoDto {

	/**
	 * 対象API(エラー発生個所システム)
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "対象API(エラー発生個所システム)", required = false, position = 1, allowableValues = "range[0,255]")
	private String targetApi;

	/**
	 * エラー内容
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "エラー内容", required = false, position = 2, allowableValues = "range[0,255]")
	private String message;

}
