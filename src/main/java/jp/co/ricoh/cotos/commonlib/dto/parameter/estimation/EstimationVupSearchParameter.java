package jp.co.ricoh.cotos.commonlib.dto.parameter.estimation;

import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EstimationVupSearchParameter {

	/**
	 * 見積番号
	 */
	@NotNull
	@ApiModelProperty(value = "見積番号", required = true, position = 1)
	private List<String> estimationNumberList;

	/**
	 * 見積番号枝番
	 */
	@NotNull
	@ApiModelProperty(value = "見積番号枝番", required = true, position = 2)
	private List<Integer> estimationBranchNumberList;
}
