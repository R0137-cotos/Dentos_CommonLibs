package jp.co.ricoh.cotos.commonlib.common.parameter;

import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * 契約を検索するためのキー項目クラスを表します。
 */

@Data
public class ApprovalSearchParameterContract {

	/**
	 * 契約ID
	 */
	@ApiParam(value = "契約ID", required = true)
	private Long contractId;

	/**
	 * MoM社員ID（承認依頼者）
	 */
	@ApiParam(value = "社員ID", required = true)
	private String employeeId;

	/**
	 * 承認ルート登録フラグ
	 */
	@ApiParam(value = "承認ルート登録フラグ", required = true, allowableValues = "true, false")
	private boolean approvalRouteRegisterFlg = false;
}
