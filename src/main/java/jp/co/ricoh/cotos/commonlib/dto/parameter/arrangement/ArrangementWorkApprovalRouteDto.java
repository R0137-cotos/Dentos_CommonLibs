package jp.co.ricoh.cotos.commonlib.dto.parameter.arrangement;

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
}
