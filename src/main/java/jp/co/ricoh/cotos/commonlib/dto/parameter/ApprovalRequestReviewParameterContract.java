package jp.co.ricoh.cotos.commonlib.dto.parameter;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.contract.Contract;
import lombok.Data;

@Data
public class ApprovalRequestReviewParameterContract {

	/** 契約情報 */
	@ApiModelProperty(value = "契約情報", required = true, position = 1)
	private Contract contract;

	/** コメント */
	@ApiModelProperty(value = "コメント", required = false, position = 2, allowableValues = "range[0,255]")
	private String comment;
}
