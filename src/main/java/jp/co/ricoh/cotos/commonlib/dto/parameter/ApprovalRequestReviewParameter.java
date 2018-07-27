package jp.co.ricoh.cotos.commonlib.dto.parameter;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation;
import lombok.Data;

@Data
public class ApprovalRequestReviewParameter {

	/** 見積情報 */
	@ApiModelProperty(value = "見積情報", required = false, position = 1)
	private Estimation estimation;

	/** コメント */
	@ApiModelProperty(value = "コメント", required = false, position = 2, allowableValues = "range[0,255]")
	private String comment;
}
