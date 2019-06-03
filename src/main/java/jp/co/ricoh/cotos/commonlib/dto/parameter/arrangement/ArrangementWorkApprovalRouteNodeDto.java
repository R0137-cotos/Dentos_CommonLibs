package jp.co.ricoh.cotos.commonlib.dto.parameter.arrangement;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.DtoBase;
import jp.co.ricoh.cotos.commonlib.entity.master.ApprovalRouteNodeMaster.ApproverDeriveMethodDiv;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ArrangementWorkApprovalRouteNodeDto extends DtoBase {

	/**
	 * 承認順
	 */
	@Min(0)
	@Max(999)
	@ApiModelProperty(value = "承認順", required = true, position = 3, allowableValues = "range[0,999]")
	private int approvalOrder;

	/**
	 * 承認者組織階層レベル
	 */
	@Min(0)
	@Max(9)
	@ApiModelProperty(value = "承認者組織階層レベル", required = false, position = 4, allowableValues = "range[0,9]")
	private Integer approverOrgLevel;

	/**
	 * 承認者MoM社員ID
	 */
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "承認者MoM社員ID", required = true, position = 5, allowableValues = "range[0,255]")
	private String approverEmpId;

	/**
	 * 承認者氏名
	 */
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "承認者氏名", required = true, position = 6, allowableValues = "range[0,255]")
	private String approverName;

	/**
	 * 承認者組織名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "承認者組織名", required = false, position = 7, allowableValues = "range[0,255]")
	private String approverOrgName;

	/**
	 * 代理承認者MoM社員ID
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "代理承認者MoM社員ID", required = false, position = 8, allowableValues = "range[0,255]")
	private String subApproverEmpId;

	/**
	 * 代理承認者氏名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "代理承認者氏名", required = false, position = 9, allowableValues = "range[0,255]")
	private String subApproverName;

	/**
	 * 代理承認者組織名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "代理承認者組織名", required = false, position = 10, allowableValues = "range[0,255]")
	private String subApproverOrgName;

	/**
	 * 承認者導出方式区分
	 */
	@ApiModelProperty(value = "承認者導出方式区分", required = false, allowableValues = "直属上司指定(\"1\"), 組織絶対階層指定(\"2\"), 組織直接指定(\"3\"), ユーザー直接指定(\"4\")", example = "1", position = 11)
	private ApproverDeriveMethodDiv approverDeriveMethodDiv;
}
