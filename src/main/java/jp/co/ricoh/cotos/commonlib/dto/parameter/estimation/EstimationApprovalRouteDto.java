package jp.co.ricoh.cotos.commonlib.dto.parameter.estimation;

import java.util.List;

import javax.persistence.OneToMany;
import javax.persistence.OrderBy;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EstimationApprovalRouteDto {

	/**
	 * ID
	 */
	@ApiModelProperty(value = "ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * version
	 */
	@ApiModelProperty(value = "version", required = true, position = 2, allowableValues = "range[0,9999999999999999999]")
	private long version;

	/**
	 * 見積承認ルートノード
	 */
	@OneToMany(mappedBy = "estimationApprovalRoute")
	@OrderBy("approvalOrder ASC")
	@ApiModelProperty(value = "見積承認ルートノード", required = true, position = 3)
	private List<EstimationApprovalRouteNodeDto> estimationApprovalRouteNodeList;
}
