package jp.co.ricoh.cotos.commonlib.dto.parameter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ApprovalRequestReviewParameterContract {

	/** ID */
	@ApiModelProperty(value = "契約ID", required = true, position = 1)
	private long id;

	/** コメント */
	@ApiModelProperty(value = "コメント", required = false, position = 2, allowableValues = "range[0,255]")
	private String comment;
}
