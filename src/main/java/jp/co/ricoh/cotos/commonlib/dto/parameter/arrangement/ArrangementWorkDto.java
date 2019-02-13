package jp.co.ricoh.cotos.commonlib.dto.parameter.arrangement;

import java.util.List;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.DtoBase;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWork.WorkflowStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ArrangementWorkDto extends DtoBase {

	/**
	 * 手配
	 */
	@NotNull
	@Valid
	@ManyToOne(optional = false)
	@JoinColumn(name = "arrangement_id", referencedColumnName = "id")
	@ApiModelProperty(value = "手配", required = true, position = 3)
	private ArrangementDto arrangement;

	/**
	 * 手配業務タイプマスタID
	 */
	@ApiModelProperty(value = "手配業務タイプマスタID", required = true, position = 4, allowableValues = "range[0,9999999999999999999]")
	private long arrangementWorkTypeMasterId;

	/**
	 * ワークフロー状態
	 */
	@NotNull
	@ApiModelProperty(value = "ワークフロー状態", required = true, allowableValues = "受付待ち(\"1\"), 作業中(\"2\"), 作業完了報告(\"3\"), 承認依頼中(\"4\"), 作業完了(\"5\")", example = "1", position = 5)
	private WorkflowStatus workflowStatus;

	/**
	 * メモ
	 */
	@Size(max = 4000)
	@ApiModelProperty(value = "メモ", required = false, position = 6, allowableValues = "range[0,4000]")
	private String memo;

	/**
	 * 保留フラグ
	 */
	@Min(0)
	@Max(9)
	@ApiModelProperty(value = "保留フラグ", required = true, position = 7, allowableValues = "range[0,9]")
	private int holdingFlg;

	/**
	 * 手配業務承認ルート
	 */
	@Valid
	@OneToOne(mappedBy = "arrangementWork")
	@ApiModelProperty(value = "手配業務承認ルート", required = false, position = 8)
	private ArrangementWorkApprovalRouteDto arrangementWorkApprovalRoute;

	/**
	 * 担当作業者社員
	 */
	@Valid
	@OneToOne(mappedBy = "arrangementWork")
	@ApiModelProperty(value = "担当作業者社員", required = false, position = 9)
	private ArrangementPicWorkerEmpDto arrangementPicWorkerEmp;

	/**
	 * 手配業務添付ファイル
	 */
	@Valid
	@OneToMany(mappedBy = "arrangementWork")
	@ApiModelProperty(value = "手配業務添付ファイル", required = false, position = 10)
	private List<ArrangementWorkAttachedFileDto> workAttachedFileList;

	/**
	 * 手配業務チェック結果
	 */
	@Valid
	@OneToMany(mappedBy = "arrangementWork")
	@ApiModelProperty(value = "手配業務チェック結果", required = false, position = 11)
	private List<ArrangementWorkCheckResultDto> arrangementWorkCheckResultList;
}
