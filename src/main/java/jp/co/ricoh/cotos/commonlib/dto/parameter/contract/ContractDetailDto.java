package jp.co.ricoh.cotos.commonlib.dto.parameter.contract;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.DtoBase;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.DetailStatus;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.InitialAccountSalesStatus;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractDetail.AbsConInsideTransStatus;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractDetail.FfmInsideTransStatus;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractDetail.RunningAccountSalesStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ContractDetailDto extends DtoBase {

	/**
	 * 状態
	 */
	@NotNull
	@ApiModelProperty(value = "状態", required = true, allowableValues = "NONE(\"1\"), ADD(\"2\"), DELETE(\"3\"), UPDATE(\"4\")", example = "", position = 3)
	private DetailStatus state;

	/**
	 * 変更前数量
	 */
	@Max(99999)
	@Min(0)
	@ApiModelProperty(value = "変更前数量", required = false, position = 4, allowableValues = "range[0,99999]")
	private Integer beforeQuantity;

	/**
	 * 数量
	 */
	@Min(0)
	@Max(99999)
	@ApiModelProperty(value = "数量", required = true, position = 5, allowableValues = "range[0,99999]")
	private int quantity;

	/**
	 * 単価
	 */
	@NotNull
	@DecimalMin("0.00")
	@Digits(integer = 19, fraction = 2)
	@ApiModelProperty(value = "単価", required = true, position = 6, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal unitPrice;

	/**
	 * 金額
	 */
	@NotNull
	@DecimalMin("0.00")
	@Digits(integer = 19, fraction = 2)
	@ApiModelProperty(value = "金額", required = true, position = 7, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal amountSummary;

	/**
	 * 摘要
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "摘要", required = false, position = 8, allowableValues = "range[0,255]")
	private String detailAbstract;

	/**
	 * 拡張項目
	 */
	@ApiModelProperty(value = "拡張項目", required = false, position = 9)
	@Lob
	private String extendsParameter;

	/**
	 * イニシャル売上計上処理状態
	 */
	@ApiModelProperty(value = "イニシャル売上計上処理状態", required = false, allowableValues = "未計上(\"0\"), 計上済み(\"1\"), 処理不要(\"2\"), 処理不可(\"3\")", position = 10)
	private InitialAccountSalesStatus initialAccountSalesStatus;

	/**
	 * イニシャル売上計上処理日
	 */
	@ApiModelProperty(value = "イニシャル売上計上処理日", required = false, position = 11)
	@Temporal(TemporalType.DATE)
	private Date initialAccountSalesDate;

	/**
	 * 注文番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "注文番号", required = false, position = 13, allowableValues = "range[0,255]")
	private String orderNo;

	/**
	 * FFM内部振替処理状態
	 */
	@ApiModelProperty(value = "FFM内部振替処理状態", required = false, allowableValues = "未処理(\"0\"), CSV作成済み(\"1\"), 連携済み(\"2\"), 対象外(\"3\")", position = 14)
	private FfmInsideTransStatus ffmInsideTransStatus;

	/**
	 * FFM内部振替連携日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "FFM内部振替連携日", required = false, position = 15)
	private Date ffmInsideLinkDate;

	/**
	 * 統合契約内部振替処理状態
	 */
	@ApiModelProperty(value = "統合契約内部振替処理状態", required = false, allowableValues = "未処理(\"0\"), TSV作成済み(\"1\"), 連携済み(\"2\"), 連携エラー(\"3\"), 対象外(\"4\")", position = 16)
	private AbsConInsideTransStatus absConInsideTransStatus;

	/**
	 * 統合契約内部振替連携日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "統合契約内部振替連携日", required = false, position = 17)
	private Date absConInsideLinkDate;

	/**
	 * ランニング売上計上処理状態
	 */
	@ApiModelProperty(value = "ランニング売上計上処理状態", required = false, allowableValues = "正常(\"0\"), 処理エラー(\"1\")", position = 18)
	private RunningAccountSalesStatus runningAccountSalesStatus;

	/**
	 * ランニング売上計上処理日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "ランニング売上計上処理日", required = false, position = 19)
	private Date runningAccountSalesDate;

	@Valid
	@NotNull
	@OneToOne(mappedBy = "contractDetail")
	@ApiModelProperty(value = "品種(契約用)", required = true, position = 12)
	private ItemContractDto itemContract;
}
