package jp.co.ricoh.cotos.commonlib.dto.parameter.estimation.external;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EstimationNumberDto {

	/**
	 * 見積番号
	 */
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "見積番号", required = true, position = 1, allowableValues = "range[0,255]")
	private String estimationNumber;

	/**
	 * 見積番号枝番
	 */
	@NotNull
	@Size(max = 99)
	@ApiModelProperty(value = "見積番号枝番", required = true, position = 2, allowableValues = "range[0,99]")
	private int estimationBranchNumber;
}
