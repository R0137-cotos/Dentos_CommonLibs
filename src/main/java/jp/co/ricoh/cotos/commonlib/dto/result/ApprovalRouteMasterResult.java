package jp.co.ricoh.cotos.commonlib.dto.result;

import jp.co.ricoh.cotos.commonlib.entity.master.ApprovalRouteMaster;
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
