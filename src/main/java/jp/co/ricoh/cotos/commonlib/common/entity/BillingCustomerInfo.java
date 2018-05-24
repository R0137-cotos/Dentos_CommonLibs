package jp.co.ricoh.cotos.commonlib.common.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BillingCustomerInfo {

	@ApiModelProperty(value = "得意先コード", required = true, position = 1, allowableValues = "range[0,60]")
	private String originalSystemCode;

	@ApiModelProperty(value = "得意先名称", required = true, position = 2, allowableValues = "range[0,1326")
	private String customerName;
}
