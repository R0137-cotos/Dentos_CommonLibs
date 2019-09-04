package jp.co.ricoh.cotos.commonlib.dto.parameter.arrangement;

import java.util.List;

import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.DtoBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ArrangementWorkApprovalRouteDto extends DtoBase {

	/**
	 * 承認依頼者MoM社員ID
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "承認依頼者MoM社員ID", required = true, position = 3, allowableValues = "range[0,255]")
	private String approvalRequesterEmpId;

	/**
	 * 承認依頼者氏名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "承認依頼者氏名", required = true, position = 4, allowableValues = "range[0,255]")
	private String approvalRequesterName;

	/**
	 * 承認依頼者組織名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "承認依頼者組織名", required = false, position = 5, allowableValues = "range[0,255]")
	private String approvalRequesterOrgName;

	/**
	 * 承認ルートマスタID
	 */
	@Min(0)
	@ApiModelProperty(value = "承認ルートマスタID", required = false, position = 6, allowableValues = "range[0,9223372036854775807]")
	private Long approvalRouteMasterId;

	/**
	 * 手配業務承認ルートノード
	 */
	@NotNull
	@Valid
	@OneToMany(mappedBy = "arrangementWorkApprovalRoute")
	@ApiModelProperty(value = "手配業務承認ルートノード", required = true, position = 7)
	private List<ArrangementWorkApprovalRouteNodeDto> arrangementWorkApprovalRouteNodeList;
}
