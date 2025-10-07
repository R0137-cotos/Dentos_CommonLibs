package jp.co.ricoh.cotos.commonlib.dto.parameter.arrangement;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.DtoBase;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.Arrangement.WorkflowStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ArrangementDto extends DtoBase {

	/**
	 * 契約ID
	 */
	@ApiModelProperty(value = "契約ID", required = true, position = 3, allowableValues = "range[0,9999999999999999999]")
	private long contractId;

	/**
	 * 解約フラグ
	 */
	@Min(0)
	@Max(9L)
	@ApiModelProperty(value = "解約フラグ", required = true, position = 4, allowableValues = "range[0,9]")
	private int disengagementFlg;

	/**
	 * ワークフロー状態
	 */
	@NotNull
	@ApiModelProperty(value = "ワークフロー状態", required = true, allowableValues = "手配中(\"1\"), 手配完了(\"2\")", example = "1", position = 5)
	private WorkflowStatus workflowStatus;
}
