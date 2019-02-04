package jp.co.ricoh.cotos.commonlib.dto.parameter.arrangement;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.DtoBase;
import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation.WorkflowStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ArrangementDto extends DtoBase {

	/**
	 * 契約ID
	 */
	@ApiModelProperty(value = "契約ID", required = true, position = 2, allowableValues = "range[0,9999999999999999999]")
	private long contractId;

	/**
	 * 解約フラグ
	 */
	@Max(9L)
	@ApiModelProperty(value = "解約フラグ", required = true, position = 3, allowableValues = "range[0,9]")
	private int disengagementFlg;

	/**
	 * ワークフロー状態
	 */
	@NotNull
	@ApiModelProperty(value = "ワークフロー状態", required = true, allowableValues = "手配中(\"1\"), 手配完了(\"2\")", example = "1", position = 4)
	private WorkflowStatus workflowStatus;
}
