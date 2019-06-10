package jp.co.ricoh.cotos.commonlib.dto.parameter.contract.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.DtoBase;
import jp.co.ricoh.cotos.commonlib.entity.contract.order.OrderBasicInfo.CommercialFlowDiv;
import jp.co.ricoh.cotos.commonlib.entity.contract.order.OrderBasicInfo.OrdererType;
import jp.co.ricoh.cotos.commonlib.entity.contract.order.OrderBasicInfo.ProductType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 注文基本情報DTO
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class OrderBasicInfoDto extends DtoBase {

	/**
	 * 注文番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "注文番号", required = true, position = 2, allowableValues = "range[0,255]")
	private String ordererNumber;

	/**
	 * 注文タイプ
	 */
	@ApiModelProperty(value = "注文タイプ", required = true, position = 3)
	private OrdererType ordererType;

	/**
	 * 商品種別
	 */
	@ApiModelProperty(value = "商品種別", required = true, position = 4)
	private ProductType productType;

	/**
	 * 契約番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "契約番号", required = false, position = 5, allowableValues = "range[0,255]")
	private String rjManageNumber;

	/**
	 * メーカー契約番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "メーカー契約番号", required = false, position = 6, allowableValues = "range[0,255]")
	private String makerManageNumber;

	/**
	 * 商流区分（代直区分）
	 */
	@ApiModelProperty(value = "商流区分（代直区分）", required = true, position = 7)
	private CommercialFlowDiv commercialFlowDiv;

	/**
	 * 申込日時
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "申込日時", required = false, position = 8)
	private Date ordererDateTime;

	/**
	 * 解約予定日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "解約予定日", required = false, position = 9)
	private Date cancelScheduledDate;

	/**
	 * 初期費合計
	 */
	@DecimalMin("0.00")
	@Digits(integer = 19, fraction = 2)
	@ApiModelProperty(value = "初期費合計", required = false, position = 10, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal initialTotalAmount;

	/**
	 * 月額費合計
	 */
	@DecimalMin("0.00")
	@Digits(integer = 19, fraction = 2)
	@ApiModelProperty(value = "月額費合計", required = false, position = 11, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal monthlyTotalAmount;

	/**
	 * 年額費合計
	 */
	@DecimalMin("0.00")
	@Digits(integer = 19, fraction = 2)
	@ApiModelProperty(value = "年額費合計", required = false, position = 12, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal yearlyTotalAmount;

	@ApiModelProperty(value = "注文商品グループ情報", required = false, position = 13)
	private OrderProductGroupInfoDto orderProductGroupInfoDto;

	@ApiModelProperty(value = "注文サービス固有情報", required = false, position = 14)
	private OrderServiceInnerInfoDto orderServiceInnerInfoDto;

	@ApiModelProperty(value = "注文販売店情報", required = false, position = 15)
	private OrderDistributorInfoDto orderDistributorInfoDto;

	@ApiModelProperty(value = "注文セットアップ先情報", required = false, position = 16)
	private OrderSetupInfoDto orderSetupInfoDto;

	@ApiModelProperty(value = "注文者情報", required = false, position = 17)
	private OrdererInfoDto ordererInfoDto;

	@ApiModelProperty(value = "注文商品情報", required = false, position = 18)
	private List<OrderProductInfoDto> orderProductInfoDtoList;

	@ApiModelProperty(value = "注文担当支社情報", required = false, position = 19)
	private OrderBranchCustomerInfoDto orderBranchCustomerInfoDto;

	@ApiModelProperty(value = "注文顧客情報", required = false, position = 20)
	private OrderContractorInfoDto orderContractorInfoDto;

}
