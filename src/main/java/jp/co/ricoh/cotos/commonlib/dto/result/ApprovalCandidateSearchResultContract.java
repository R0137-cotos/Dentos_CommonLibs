package jp.co.ricoh.cotos.commonlib.dto.result;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractApprovalRoute;
import lombok.Data;

/**
 * 契約承認者特定用APIの実施結果用パラメーター
 */

@Data
public class ApprovalCandidateSearchResultContract {

	/**
	 * 承認ルートマスタ
	 */
	@ApiModelProperty(value = "承認ルートマスタ情報", required = true, position = 1)
	private ApprovalRouteMasterDto approvalRouteMaster;

	/**
	 * 条件式判定結果
	 */
	@ApiModelProperty(value = "条件式判定結果", required = true, position = 2)
	private RouteFormulaResult routeFormulaResult;

	/**
	 * 契約承認ルート
	 */
	@ApiModelProperty(value = "契約承認ルート情報", required = true, position = 3)
	private ContractApprovalRoute contractApprovalRoute;
}
