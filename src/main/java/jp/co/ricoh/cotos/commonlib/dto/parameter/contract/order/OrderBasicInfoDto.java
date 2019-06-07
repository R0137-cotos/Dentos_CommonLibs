package jp.co.ricoh.cotos.commonlib.dto.parameter.contract.order;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
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
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "注文番号", required = false, position = 2, allowableValues = "range[0,]")
	private String ordererNumber;

	/**
	 * 注文タイプ
	 */
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "注文タイプ", required = false, position = 3, allowableValues = "range[0,]")
	private OrdererType ordererType;

	/**
	 * 商品種別
	 */
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "商品種別", required = false, position = 4, allowableValues = "range[0,]")
	private ProductType productType;

	/**
	 * 契約番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "契約番号", required = false, position = 5, allowableValues = "range[0,]")
	private String rjManageNumber;

	/**
	 * メーカー契約番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "メーカー契約番号", required = false, position = 6, allowableValues = "range[0,]")
	private String makerManageNumber;

	/**
	 * 商流区分（代直区分）
	 */
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "商流区分（代直区分）", required = false, position = 7, allowableValues = "range[0,]")
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
	@Column(nullable = false)
	@ApiModelProperty(value = "解約予定日", required = false, position = 9)
	private Date cancelScheduledDate;

	/**
	 * 初期費合計
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "初期費合計", required = false, position = 10)
	private BigDecimal initialTotalAmount;

	/**
	 * 月額費合計
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "月額費合計", required = false, position = 11)
	private BigDecimal monthlyTotalAmount;

	/**
	 * 年額費合計
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "年額費合計", required = false, position = 12)
	private BigDecimal yearlyTotalAmount;

	@ApiModelProperty(value = "注文商品グループ情報", required = false, position = 13)
	private OrderProductGroupInfoDto orderProductGroupInfoDTO;

	@ApiModelProperty(value = "注文サービス固有情報", required = false, position = 14)
	private OrderServiceInnerInfoDto orderServiceInnerInfoDTO;

	@ApiModelProperty(value = "注文販売店情報", required = false, position = 15)
	private OrderDistributorInfoDto orderDistributorInfoDTO;

	@ApiModelProperty(value = "注文セットアップ先情報", required = false, position = 16)
	private OrderSetupInfoDto orderSetupInfoDTO;

	@ApiModelProperty(value = "注文者情報", required = false, position = 17)
	private OrdererInfoDto ordererInfoDTO;

	@ApiModelProperty(value = "注文商品情報", required = false, position = 18)
	private List<OrderProductInfoDto> orderProductInfoDTOList;

	@ApiModelProperty(value = "注文担当支社情報", required = false, position = 19)
	private OrderBranchCustomerInfoDto orderBranchCustomerInfoDTO;

	@ApiModelProperty(value = "注文顧客情報", required = false, position = 20)
	private OrderContractorInfoDto orderContractorInfoDTO;

}
