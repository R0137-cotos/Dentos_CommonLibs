package jp.co.ricoh.cotos.commonlib.dto.parameter.contract.order;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.contract.order.OrderBasicInfo.CommercialFlowDiv;
import jp.co.ricoh.cotos.commonlib.entity.contract.order.OrderBasicInfo.OrdererType;
import jp.co.ricoh.cotos.commonlib.entity.contract.order.OrderBasicInfo.ProductType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 注文基底情報
 * @author z00se03039
 *
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class OrderBasicContentsDto {
	/**
	 * 注文番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "注文番号", required = true, position = 2, allowableValues = "range[0,255]")
	private String ordererNumber;

	/**
	 * 注文タイプ
	 */
	@ApiModelProperty(value = "注文タイプ", required = true, allowableValues = "新規(\"1\"), 変更(\"2\"), 解約(\"3\")", position = 3)
	private OrdererType ordererType;

	/**
	 * 商品種別
	 */
	@ApiModelProperty(value = "商品種別", required = true, allowableValues = "RSI(\"1\")", position = 4)
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
	@ApiModelProperty(value = "商流区分（代直区分）", required = true, allowableValues = "代売(\"1\"), 直売(\"2\")", position = 7)
	private CommercialFlowDiv commercialFlowDiv;

	/**
	 * 申込日時
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "申込日時", required = false, position = 8)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Tokyo")
	private Date ordererDateTime;

	/**
	 * 解約予定日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "解約予定日", required = false, position = 9)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date cancelScheduledDate;

	/**
	 * 解約理由1
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "解約理由1", required = false, position = 10, allowableValues = "range[0,255]")
	private String cancelReason1;

	/**
	 * 解約理由2
	 */
	@Size(max = 1000)
	@ApiModelProperty(value = "解約理由2", required = false, position = 11, allowableValues = "range[0,1000]")
	private String cancelReason2;

	/**
	 * 改善ポイント
	 */
	@Size(max = 1000)
	@ApiModelProperty(value = "改善ポイント", required = false, position = 12, allowableValues = "range[0,1000]")
	private String improvementPoint;

	/**
	 * 初期費合計
	 */
	@DecimalMin("0.00")
	@Digits(integer = 19, fraction = 2)
	@ApiModelProperty(value = "初期費合計", required = false, position = 13, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal initialTotalAmount;

	/**
	 * 月額費合計
	 */
	@DecimalMin("0.00")
	@Digits(integer = 19, fraction = 2)
	@ApiModelProperty(value = "月額費合計", required = false, position = 14, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal monthlyTotalAmount;

	/**
	 * 年額費合計
	 */
	@DecimalMin("0.00")
	@Digits(integer = 19, fraction = 2)
	@ApiModelProperty(value = "年額費合計", required = false, position = 15, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal yearlyTotalAmount;
}
