package jp.co.ricoh.cotos.commonlib.dto.parameter.estimation;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class EstimationCancelParameter {

	/**
	 * 再見積ID
	 */
	@ApiParam(value = "再見積ID", allowableValues = "range[0,9999999999999999999]", required = false)
	private Long reEstimationId;

	/**
	 * 契約ID
	 */
	@ApiParam(value = "契約ID", allowableValues = "range[0,9999999999999999999]", required = false)
	private Long contractId;
}
