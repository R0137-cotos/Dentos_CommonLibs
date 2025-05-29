package jp.co.ricoh.cotos.commonlib.dto.parameter.arrangementDelegation;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ContractInfoDto {

	/**
	 * 契約番号
	 */
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "契約番号", required = true, position = 1, allowableValues = "range[0,255]")
	private String contractNumber;

	/**
	 * 契約番号枝番
	 */
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "契約番号枝番", required = true, position = 2, allowableValues = "range[0,255]")
	private String contractBranchNumber;

	/**
	 * 契約明細情報リスト
	 */
	@Valid
	@ApiModelProperty(value = "契約明細情報リスト", required = false, position = 3)
	private List<ContractDetailDelegationDto> contractDetailList;

	/**
	 * 商品(契約用)リスト
	 */
	@Valid
	@ApiModelProperty(value = "商品(契約用)リスト", required = false, position = 4)
	private List<ProductContractDelegationDto> productContractList;

}
