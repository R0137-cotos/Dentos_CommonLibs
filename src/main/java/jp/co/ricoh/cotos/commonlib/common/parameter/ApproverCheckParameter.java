package jp.co.ricoh.cotos.commonlib.common.parameter;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class ApproverCheckParameter {

	/**
	 * SUID
	 */
	@ApiParam(value = "SUID", required = false)
	private String singleUserId;
}