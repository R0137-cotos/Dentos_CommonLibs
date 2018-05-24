package jp.co.ricoh.cotos.commonlib.common.result;

import jp.co.ricoh.cotos.commonlib.common.master.ApprovalRouteMaster;
import lombok.Data;

@Data
public class ApprovalRouteMasterResult {

	/**
	 * 条件式判定結果
	 */
	private RouteFormulaResult routeFormulaResult;

	/**
	 * 承認ルート
	 */
	private ApprovalRouteMaster approvalRouteMaster;
}
