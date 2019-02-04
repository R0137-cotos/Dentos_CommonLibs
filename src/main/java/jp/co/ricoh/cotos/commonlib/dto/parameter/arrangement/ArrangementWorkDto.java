package jp.co.ricoh.cotos.commonlib.dto.parameter.arrangement;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.DtoBase;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWork.WorkflowStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ArrangementWorkDto extends DtoBase{

	/**
	 * 手配業務タイプマスタID
	 */
	@ApiModelProperty(value = "手配業務タイプマスタID", required = true, position = 3, allowableValues = "range[0,9999999999999999999]")
	private long arrangementWorkTypeMasterId;

	/**
	 * ワークフロー状態
	 */
	@NotNull
	@ApiModelProperty(value = "ワークフロー状態", required = true, allowableValues = "受付待ち(\"1\"), 作業中(\"2\"), 作業完了報告(\"3\"), 承認依頼中(\"4\"), 作業完了(\"5\")", example = "1", position = 4)
	private WorkflowStatus workflowStatus;

	/**
	 * メモ
	 */
	@Size(max = 4000)
	@ApiModelProperty(value = "メモ", required = false, position = 5, allowableValues = "range[0,4000]")
	private String memo;

	/**
	 * 保留フラグ
	 */
	@Max(9)
	@ApiModelProperty(value = "保留フラグ", required = true, position = 6, allowableValues = "range[0,9]")
	private int holdingFlg;
}
