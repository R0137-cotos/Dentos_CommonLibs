package jp.co.ricoh.cotos.commonlib.dto.parameter.arrangement;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.DtoBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ArrangementWorkApprovalRouteNodeDto extends DtoBase {

	/**
	 * 手配業務承認ルート
	 */
	@JsonIgnore
	@ApiModelProperty(value = "手配業務承認ルート", required = true, position = 3)
	private ArrangementWorkApprovalRouteDto arrangementWorkApprovalRoute;

	/**
	 * 承認順
	 */
	@Max(999L)
	@ApiModelProperty(value = "承認順", required = true, position = 4, allowableValues = "range[0,999]")
	private long approvalOrder;

	/**
	 * 承認者組織階層レベル
	 */
	@Max(9)
	@ApiModelProperty(value = "承認者組織階層レベル", required = false, position = 5, allowableValues = "range[0,9]")
	private Integer approverOrgLevel;

	/**
	 * 承認者MoM社員ID
	 */
	@NotEmpty
	@Size(max = 255)
	@ApiModelProperty(value = "承認者MoM社員ID", required = true, position = 6, allowableValues = "range[0,255]")
	private String approverEmpId;

	/**
	 * 承認者氏名
	 */
	@NotEmpty
	@Size(max = 255)
	@ApiModelProperty(value = "承認者氏名", required = true, position = 7, allowableValues = "range[0,255]")
	private String approverName;

	/**
	 * 承認者組織名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "承認者組織名", required = false, position = 8, allowableValues = "range[0,255]")
	private String approverOrgName;

	/**
	 * 代理承認者MoM社員ID
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "代理承認者MoM社員ID", required = false, position = 9, allowableValues = "range[0,255]")
	private String subApproverEmpId;

	/**
	 * 代理承認者氏名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "代理承認者氏名", required = false, position = 10, allowableValues = "range[0,255]")
	private String subApproverName;

	/**
	 * 代理承認者組織名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "代理承認者組織名", required = false, position = 11, allowableValues = "range[0,255]")
	private String subApproverOrgName;
}
