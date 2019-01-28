package jp.co.ricoh.cotos.commonlib.dto.result;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWorkApprovalRoute;
import lombok.Data;

/**
 * 手配承認者特定用APIの実施結果用パラメーター
 */
@Data
public class ApprovalSearchResultArrangement {

	/**
	 * 条件式判定結果
	 */
	@ApiModelProperty(value = "条件式判定結果", required = true, position = 1)
	private RouteFormulaResult routeFormulaResult;

	/**
	 * 手配承認ルート
	 */
	@ApiModelProperty(value = "手配承認ルート情報", required = true, position = 2)
	private ArrangementWorkApprovalRoute arrangementWorkApprovalRoute;
}