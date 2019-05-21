package jp.co.ricoh.cotos.commonlib.dto.parameter.contract;

import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.DtoBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ContractPicIntSsOrgDto extends DtoBase{
	
	/**
	 * 科所名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "科所名", required = false, position = 3, allowableValues = "range[0,255]")
	private String serviceOrgName;
}
