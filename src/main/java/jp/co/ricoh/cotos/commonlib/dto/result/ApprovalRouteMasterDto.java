package jp.co.ricoh.cotos.commonlib.dto.result;

import lombok.Data;

@Data
public class ApprovalRouteMasterDto {

	/**
	 * 承認ルートID
	 */
	private long approvalRouteId;

	/**
	 * 承認ルート名
	 */
	private String approvalRouteName;

	/**
	 * 説明
	 */
	private String description;
}
