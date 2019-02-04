package jp.co.ricoh.cotos.commonlib.dto.parameter.estimation;

import java.util.List;

import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.Max;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.DtoBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class EstimationApprovalRouteDto extends DtoBase {

	/**
	 * 特価承認対象フラグ
	 */
	@Max(9)
	@ApiModelProperty(value = "特価承認対象フラグ", required = true, position = 3, allowableValues = "range[0,9]")
	private int specialPriceApprovalFlg;

	/**
	 * 見積承認ルートノード
	 */
	@OneToMany(mappedBy = "estimationApprovalRoute")
	@OrderBy("approvalOrder ASC")
	@ApiModelProperty(value = "見積承認ルートノード", required = true, position = 4)
	private List<EstimationApprovalRouteNodeDto> estimationApprovalRouteNodeList;
}
