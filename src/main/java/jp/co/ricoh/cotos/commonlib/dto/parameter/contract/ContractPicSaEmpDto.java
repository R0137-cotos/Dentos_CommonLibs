package jp.co.ricoh.cotos.commonlib.dto.parameter.contract;

import javax.persistence.Lob;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.EmployeeAbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ContractPicSaEmpDto extends EmployeeAbstractDto {

	/**
	 * 拡張項目
	 */
	@ApiModelProperty(value = "拡張項目", required = false, position = 3)
	@Lob
	private String extendsParameter;
}
