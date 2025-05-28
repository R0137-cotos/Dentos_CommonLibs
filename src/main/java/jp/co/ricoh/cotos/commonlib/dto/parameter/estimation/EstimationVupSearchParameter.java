package jp.co.ricoh.cotos.commonlib.dto.parameter.estimation;

import java.util.List;

import jakarta.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EstimationVupSearchParameter {

	/**
	 * 見積番号＋見積番号枝番(ハイフン繋ぎ)
	 */
	@NotNull
	@ApiModelProperty(value = "見積番号＋見積番号枝番(ハイフン繋ぎ)", required = true, position = 1)
	private List<String> estimationNumberList;
}
