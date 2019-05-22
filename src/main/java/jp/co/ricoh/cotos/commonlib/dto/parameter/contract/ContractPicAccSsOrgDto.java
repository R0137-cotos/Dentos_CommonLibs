package jp.co.ricoh.cotos.commonlib.dto.parameter.contract;

import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.DtoBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ContractPicAccSsOrgDto extends DtoBase{
	
	/**
	 * 課所名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "課所名", required = false, position = 3, allowableValues = "range[0,255]")
	private String serviceOrgName;
}
