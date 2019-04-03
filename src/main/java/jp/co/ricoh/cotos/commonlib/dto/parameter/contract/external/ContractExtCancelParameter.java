package jp.co.ricoh.cotos.commonlib.dto.parameter.contract.external;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ContractExtCancelParameter {

	/**
	 * web受注注文番号
	 */
	@Size(max = 255)
	@NotNull
	@ApiModelProperty(value = "web受注注文番号", required = true, position = 1, allowableValues = "range[0,255]")
	private String webOrderNumber;

	/**
	 * RJ管理番号
	 */
	@Size(max = 255)
	@NotNull
	@ApiModelProperty(value = "RJ管理番号", required = true, position = 2, allowableValues = "range[0,255]")
	private String rjManageNumber;
}
