package jp.co.ricoh.cotos.commonlib.dto.parameter.estimation;

import jakarta.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.EmployeeAbstractDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class EstimationPicSaEmpDto extends EmployeeAbstractDto {

	/**
	 * MoM非連携_MoM企事部システム連携ID
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_MoM企事部システム連携ID", required = false, position = 1, allowableValues = "range[0,255]")
	private String momKjbSystemId;

	/**
	 * MoM非連携_MoM企事部ID
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_MoM企事部ID", required = false, position = 2, allowableValues = "range[0,255]")
	private String momCustId;

	/**
	 * 販売店名（カナ）
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "販売店名（カナ）", required = false, position = 3, allowableValues = "range[0,255]")
	private String salesCompanyNameKana;

	/**
	 * MoM非連携_企業代表者名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_企業代表者名", required = false, position = 4, allowableValues = "range[0,255]")
	private String companyRepresentativeName;

	/**
	 * MoM非連携_企業代表者名(カナ)
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_企業代表者名(カナ)", required = false, position = 5, allowableValues = "range[0,255]")
	private String companyRepresentativeNameKana;
}
