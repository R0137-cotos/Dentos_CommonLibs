package jp.co.ricoh.cotos.commonlib.dto.parameter.contract.external;

import java.util.Date;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.contract.Contract.LifecycleStatus;
import jp.co.ricoh.cotos.commonlib.entity.contract.Contract.WorkflowStatus;
import lombok.Data;

@Data
public class ContractExtCancelDto {

	/**
	 * 契約ID
	 */
	@Min(0)
	@ApiModelProperty(value = "契約ID", required = false, position = 1, allowableValues = "range[0,9223372036854775807]")
	private long contractId;

	/**
	 * RJ管理番号
	 */
	@Size(max = 255)
	@NotNull
	@ApiModelProperty(value = "RJ管理番号", required = true, position = 2, allowableValues = "range[0,255]")
	private String rjManageNumber;

	/**
	 * ライフサイクル状態
	 */
	@NotNull
	@ApiModelProperty(value = "ライフサイクル状態", required = true, allowableValues = "作成中(\"1\"), 作成完了(\"2\"), キャンセル手続き中(\"3\"), 破棄(\"4\"), 予定日待ち(\"5\"), 締結中(\"6\"), 解約手続き中(\"7\"), 解約予定日待ち(\"8\"), 解約(\"9\"), 旧契約(\"10\")", example = "1", position = 3)
	private LifecycleStatus lifecycleStatus;

	/**
	 * ワークフロー状態
	 */
	@NotNull
	@ApiModelProperty(value = "ワークフロー状態", required = true, allowableValues = "作成中(\"1\"), 承認依頼中(\"2\"), 承認済(\"3\"), 業務依頼中(\"4\"), 業務処理完了(\"5\"), キャンセル申請中(\"6\"), 売上可能(\"7\"), 解約申請中(\"8\")", example = "1", position = 4)
	private WorkflowStatus workflowStatus;

	/**
	 * 解約予定日
	 */
	@ApiModelProperty(value = "解約予定日", required = false, position = 5)
	@Temporal(TemporalType.DATE)
	private Date cancelScheduledDate;

	/**
	 * 解約理由
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "解約理由", required = false, position = 6, allowableValues = "range[0,255]")
	private String cancelReason;

	/**
	 * その他解約理由
	 */
	@Size(max = 1000)
	@ApiModelProperty(value = "その他解約理由", required = false, position = 7, allowableValues = "range[0,1000]")
	private String cancelReasonEtc;

	/**
	 * 解約注文番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "解約注文番号", required = false, position = 8, allowableValues = "range[0,255]")
	private String cancelOrderNo;

}
