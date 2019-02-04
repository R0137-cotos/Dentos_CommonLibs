package jp.co.ricoh.cotos.commonlib.dto.parameter.estimation;

import java.util.List;

import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Version;
import javax.validation.constraints.Max;

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
	@Version
	@ApiModelProperty(value = "version", required = true, position = 2, allowableValues = "range[0,9999999999999999999]")
	private long version;

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
