package jp.co.ricoh.cotos.commonlib.dto.parameter.contract.order;

import java.util.List;

import jakarta.validation.Valid;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 注文基本情報DTO
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class OrderBasicInfoDto {

	@ApiModelProperty(value = "注文基底情報", required = false, position = 13)
	@Valid
	private OrderBasicContentsDto basicContents;
	
	@ApiModelProperty(value = "注文商品グループ情報", required = false, position = 13)
	@Valid
	private OrderProductGroupInfoDto productGroupInfo;

	@ApiModelProperty(value = "注文サービス固有情報", required = false, position = 14)
	@Valid
	private OrderServiceInnerInfoDto serviceInnerInfo;

	@ApiModelProperty(value = "注文販売店情報", required = false, position = 15)
	@Valid
	private OrderDistributorInfoDto distributorInfo;

	@ApiModelProperty(value = "注文セットアップ先情報", required = false, position = 16)
	@Valid
	private OrderSetupInfoDto setupInfo;

	@ApiModelProperty(value = "注文者情報", required = false, position = 17)
	@Valid
	private OrdererInfoDto ordererInfo;

	@ApiModelProperty(value = "注文商品情報", required = false, position = 18)
	@Valid
	private List<OrderProductInfoDto> productInfo;

	@ApiModelProperty(value = "注文担当支社情報", required = false, position = 19)
	@Valid
	private OrderBranchCustomerInfoDto branchCustomerInfo;

	@ApiModelProperty(value = "注文顧客情報", required = false, position = 20)
	@Valid
	private OrderContractorInfoDto contractorInfo;

}
