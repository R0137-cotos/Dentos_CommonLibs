package jp.co.ricoh.cotos.commonlib.entity.accounting;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.master.ItemMaster.CostType;
import jp.co.ricoh.cotos.commonlib.entity.master.ItemMaster.ItemType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "accounting")
public class Accounting extends EntityBase {

	/** 計上ID */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accounting_seq")
	@SequenceGenerator(name = "accounting_seq", sequenceName = "accounting_seq", allocationSize = 1)
	@ApiModelProperty(value = "計上ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/** RJ管理番号 */
	@Size(max = 255)
	@ApiModelProperty(value = "RJ管理番号", required = false, position = 2, allowableValues = "range[0,255]")
	private String rjManageNumber;

	/** 契約ID */
	@ApiModelProperty(value = "契約ID", required = false, position = 3, allowableValues = "range[0,9999999999999999999]")
	private Long contractId;

	/** 契約明細ID */
	@ApiModelProperty(value = "契約明細ID", required = false, position = 4, allowableValues = "range[0,9999999999999999999]")
	private Long contractDetailId;

	/** 取引年月日 */
	@Size(max = 255)
	@ApiModelProperty(value = "取引年月日", required = false, position = 5, allowableValues = "range[0,255]")
	private String transactionDate;

	/** 締日 */
	@Size(max = 255)
	@ApiModelProperty(value = "締日", required = false, position = 6, allowableValues = "range[0,255]")
	private String closingDate;

	/** 商流区分 */
	@Size(max = 255)
	@ApiModelProperty(value = "商流区分", required = false, position = 7, allowableValues = "range[0,255]")
	private String dealerFlow;

	/** 費用種別 */
	@ApiModelProperty(value = "費用種別", required = false, allowableValues = "初期費(\"1\"), 月額_定額(\"2\"), 年額(\"3\"), 月額_従量(\"4\")", example = "1", position = 8)
	private CostType costType;

	/** 品種区分 */
	@ApiModelProperty(value = "品種区分", required = false, allowableValues = "なし(\"0\"), 基本(\"1\"), オプション(\"2\")", example = "1", position = 9)
	private ItemType itemType;

	/** 請求年月 */
	@Size(max = 255)
	@ApiModelProperty(value = "請求年月", required = false, position = 10, allowableValues = "range[0,255]")
	private String billingDate;

	/** サービス期間開始日 */
	@Size(max = 255)
	@ApiModelProperty(value = "サービス期間開始日", required = false, position = 11, allowableValues = "range[0,255]")
	private String srvStartDate;

	/** サービス期間終了日 */
	@Size(max = 255)
	@ApiModelProperty(value = "サービス期間終了日", required = false, position = 12, allowableValues = "range[0,255]")
	private String srvEndDate;

	/** 注文番号 */
	@Size(max = 255)
	@ApiModelProperty(value = "注文番号", required = false, position = 13, allowableValues = "range[0,255]")
	private String webOrderNo;

	/** 品種コード */
	@Size(max = 255)
	@ApiModelProperty(value = "品種コード", required = false, position = 14, allowableValues = "range[0,255]")
	private String productTypeCd;

	/** 品種名 */
	@Size(max = 255)
	@ApiModelProperty(value = "品種名", required = false, position = 15, allowableValues = "range[0,255]")
	private String productTypeName;

	/** FFM計上処理フラグ */
	@Max(9)
	@ApiModelProperty(value = "FFM計上処理フラグ", required = false, position = 16, allowableValues = "range[0,9]")
	private Integer ffmFlg;

	/** CUBIC計上処理フラグ */
	@Max(9)
	@ApiModelProperty(value = "CUBIC計上処理フラグ", required = false, position = 17, allowableValues = "range[0,9]")
	private Integer cubicFlg;

	/** データ作成日 */
	@Size(max = 255)
	@ApiModelProperty(value = "データ作成日", required = false, position = 18, allowableValues = "range[0,255]")
	private String ffmDataCreateDate;

	/** データ作成時間 */
	@Size(max = 255)
	@ApiModelProperty(value = "データ作成時間", required = false, position = 19, allowableValues = "range[0,255]")
	private String ffmDataCreateTime;

	/** FFM会社コード */
	@Size(max = 255)
	@ApiModelProperty(value = "FFM会社コード", required = false, position = 20, allowableValues = "range[0,255]")
	private String ffmCompanyCd;

	/** 契約種類区分 */
	@Size(max = 255)
	@ApiModelProperty(value = "契約種類区分", required = false, position = 21, allowableValues = "range[0,255]")
	private String ffmContractTypeKbn;

	/** 作成データパターン */
	@Size(max = 255)
	@ApiModelProperty(value = "作成データパターン", required = false, position = 22, allowableValues = "range[0,255]")
	private String ffmDataPtn;

	/** 勘定識別 */
	@Size(max = 255)
	@ApiModelProperty(value = "勘定識別", required = false, position = 23, allowableValues = "range[0,255]")
	private String ffmAccountType;

	/** データ種別 */
	@Size(max = 255)
	@ApiModelProperty(value = "データ種別", required = false, position = 24, allowableValues = "range[0,255]")
	private String ffmDataType;

	/** 赤黒区分 */
	@Size(max = 255)
	@ApiModelProperty(value = "赤黒区分", required = false, position = 25, allowableValues = "range[0,255]")
	private String ffmRedBlackType;

	/** 債権債務照合キー */
	@Size(max = 255)
	@ApiModelProperty(value = "債権債務照合キー", required = false, position = 26, allowableValues = "range[0,255]")
	private String ffmMatchingKey;

	/** NSPユニークキー */
	@Size(max = 255)
	@ApiModelProperty(value = "NSPユニークキー", required = false, position = 27, allowableValues = "range[0,255]")
	private String ffmNspKey;

	/** 案件番号 */
	@Size(max = 255)
	@ApiModelProperty(value = "案件番号", required = false, position = 28, allowableValues = "range[0,255]")
	private String ffmProjectNo;

	/** 契約書番号 */
	@Size(max = 255)
	@ApiModelProperty(value = "契約書番号", required = false, position = 29, allowableValues = "range[0,255]")
	private String ffmContractDocNo;

	/** 契約番号 */
	@Size(max = 255)
	@ApiModelProperty(value = "契約番号", required = false, position = 30, allowableValues = "range[0,255]")
	private String ffmContractNo;

	/** 契約明細番号 */
	@Size(max = 255)
	@ApiModelProperty(value = "契約明細番号", required = false, position = 31, allowableValues = "range[0,255]")
	private String ffmContractDetailNo;

	/** 請求明細番号 */
	@Size(max = 255)
	@ApiModelProperty(value = "請求明細番号", required = false, position = 32, allowableValues = "range[0,255]")
	private String ffmBillingDetailNo;

	/** お問合せ番号 */
	@Size(max = 255)
	@ApiModelProperty(value = "お問合せ番号", required = false, position = 33, allowableValues = "range[0,255]")
	private String ffmInqNo;

	/** お問合せ明細番号 */
	@Size(max = 255)
	@ApiModelProperty(value = "お問合せ明細番号", required = false, position = 34, allowableValues = "range[0,255]")
	private String ffmInqDetailNo;

	/** 手配時の案件番号 */
	@Size(max = 255)
	@ApiModelProperty(value = "手配時の案件番号", required = false, position = 35, allowableValues = "range[0,255]")
	private String ffmArrProjectNo;

	/** 手配時の問合せ番号 */
	@Size(max = 255)
	@ApiModelProperty(value = "手配時の問合せ番号", required = false, position = 36, allowableValues = "range[0,255]")
	private String ffmArrInqNo;

	/** 赤伝理由 */
	@Size(max = 255)
	@ApiModelProperty(value = "赤伝理由", required = false, position = 37, allowableValues = "range[0,255]")
	private String ffmCancelReason;

	/** 元契約番号 */
	@Size(max = 255)
	@ApiModelProperty(value = "元契約番号", required = false, position = 38, allowableValues = "range[0,255]")
	private String ffmOrgContractCd;

	/** 元請求明細番号 */
	@Size(max = 255)
	@ApiModelProperty(value = "元請求明細番号", required = false, position = 39, allowableValues = "range[0,255]")
	private String ffmOrgContractDetailNo;

	/** 請求条件 */
	@Size(max = 255)
	@ApiModelProperty(value = "請求条件", required = false, position = 40, allowableValues = "range[0,255]")
	private String ffmBillingCondition;

	/** 請求分割回数 */
	@Max(99)
	@ApiModelProperty(value = "請求分割回数", required = false, position = 41, allowableValues = "range[0,99]")
	private Integer ffmTotalBillingCount;

	/** 契約締結日 */
	@Size(max = 255)
	@ApiModelProperty(value = "契約締結日", required = false, position = 42, allowableValues = "range[0,255]")
	private String ffmContractDate;

	/** 契約期間（開始） */
	@Size(max = 255)
	@ApiModelProperty(value = "契約期間（開始）", required = false, position = 43, allowableValues = "range[0,255]")
	private String ffmContractPeriodStart;

	/** 契約期間（終了） */
	@Size(max = 255)
	@ApiModelProperty(value = "契約期間（終了）", required = false, position = 44, allowableValues = "range[0,255]")
	private String ffmContractPeriodEnd;

	/** 契約ＳＳコード */
	@Size(max = 255)
	@ApiModelProperty(value = "契約ＳＳコード", required = false, position = 45, allowableValues = "range[0,255]")
	private String ffmContractSscd;

	/** 契約ＳＳ社員コード */
	@Size(max = 255)
	@ApiModelProperty(value = "契約ＳＳ社員コード", required = false, position = 46, allowableValues = "range[0,255]")
	private String ffmContractSspiccd;

	/** 振替先課所コード */
	@Size(max = 255)
	@ApiModelProperty(value = "振替先課所コード", required = false, position = 47, allowableValues = "range[0,255]")
	private String ffmTrnsLocationCd;

	/** 振替先社員コード */
	@Size(max = 255)
	@ApiModelProperty(value = "振替先社員コード", required = false, position = 48, allowableValues = "range[0,255]")
	private String ffmTrnsPicCd;

	/** 保守契約／リース/レンタルＮｏ */
	@Size(max = 255)
	@ApiModelProperty(value = "保守契約／リース/レンタルＮｏ", required = false, position = 49, allowableValues = "range[0,255]")
	private String ffmMntLeaseNo;

	/** 契約金額 */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "契約金額", required = false, position = 50, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal ffmContractPrice;

	/** 仕切前計上金額 */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "仕切前計上金額", required = false, position = 51, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal ffmPriceBeforeInvoice;

	/** 仕切前消費税額 */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "仕切前消費税額", required = false, position = 52, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal ffmTaxPriceBeforeInvoice;

	/** 商品コード */
	@Size(max = 255)
	@ApiModelProperty(value = "商品コード", required = false, position = 53, allowableValues = "range[0,255]")
	private String ffmProdactCd;

	/** 機種略号 */
	@Size(max = 255)
	@ApiModelProperty(value = "機種略号", required = false, position = 54, allowableValues = "range[0,255]")
	private String ffmModelId;

	/** 機番 */
	@Size(max = 255)
	@ApiModelProperty(value = "機番", required = false, position = 55, allowableValues = "range[0,255]")
	private String ffmSerialId;

	/** 見積時の入力商品名 */
	@Size(max = 255)
	@ApiModelProperty(value = "見積時の入力商品名", required = false, position = 56, allowableValues = "range[0,255]")
	private String ffmQuotationProdactName;

	/** 原価計上商品コード */
	@Size(max = 255)
	@ApiModelProperty(value = "原価計上商品コード", required = false, position = 57, allowableValues = "range[0,255]")
	private String ffmCostProdactName;

	/** 仕入区分 */
	@Size(max = 255)
	@ApiModelProperty(value = "仕入区分", required = false, position = 58, allowableValues = "range[0,255]")
	private String ffmPurchaseType;

	/** 仕入値引区分 */
	@Size(max = 255)
	@ApiModelProperty(value = "仕入値引区分", required = false, position = 59, allowableValues = "range[0,255]")
	private String ffmPurchaseDiscntType;

	/** 仕入購買区分 */
	@Size(max = 255)
	@ApiModelProperty(value = "仕入購買区分", required = false, position = 60, allowableValues = "range[0,255]")
	private String ffmPurchaseClassType;

	/** 仕入取引日 */
	@Size(max = 255)
	@ApiModelProperty(value = "仕入取引日", required = false, position = 61, allowableValues = "range[0,255]")
	private String ffmPurchaseDate;

	/** 他社商品区分 */
	@Size(max = 255)
	@Column(name = "ffm_non_r_item_cd")
	@ApiModelProperty(value = "他社商品区分", required = false, position = 62, allowableValues = "range[0,255]")
	private String ffmNonRItemCd;

	/** 仕入取引先コード */
	@Size(max = 255)
	@ApiModelProperty(value = "仕入取引先コード", required = false, position = 63, allowableValues = "range[0,255]")
	private String ffmSupplierCd;

	/** 仕入課所設定区分 */
	@Size(max = 255)
	@ApiModelProperty(value = "仕入課所設定区分", required = false, position = 64, allowableValues = "range[0,255]")
	private String ffmDeptAssortType;

	/** 仕入課所コード */
	@Size(max = 255)
	@ApiModelProperty(value = "仕入課所コード", required = false, position = 65, allowableValues = "range[0,255]")
	private String ffmPurchaseLocationCd;

	/** 仕入責任得意先コード */
	@Size(max = 255)
	@ApiModelProperty(value = "仕入責任得意先コード", required = false, position = 66, allowableValues = "range[0,255]")
	private String ffmPurchaseRespClientCd;

	/** 在庫区コード */
	@Size(max = 255)
	@ApiModelProperty(value = "在庫区コード", required = false, position = 67, allowableValues = "range[0,255]")
	private String ffmRdStrctInventoryCd;

	/** 特価番号 */
	@Size(max = 255)
	@ApiModelProperty(value = "特価番号", required = false, position = 68, allowableValues = "range[0,255]")
	private String ffmDealsNo;

	/** 仕入先請求ＮＯ */
	@Size(max = 255)
	@ApiModelProperty(value = "仕入先請求ＮＯ", required = false, position = 69, allowableValues = "range[0,255]")
	private String ffmSupplierBillingNo;

	/** 商品名（支払通知書用） */
	@Size(max = 255)
	@ApiModelProperty(value = "商品名（支払通知書用）", required = false, position = 70, allowableValues = "range[0,255]")
	private String ffmPaymentProdactName;

	/** 売上区分 */
	@Size(max = 255)
	@ApiModelProperty(value = "売上区分", required = false, position = 71, allowableValues = "range[0,255]")
	private String ffmSalesType;

	/** 売上値引区分 */
	@Size(max = 255)
	@ApiModelProperty(value = "売上値引区分", required = false, position = 72, allowableValues = "range[0,255]")
	private String ffmSalesDiscountType;

	/** 売上取引日（納品日） */
	@Size(max = 255)
	@ApiModelProperty(value = "売上取引日（納品日）", required = false, position = 73, allowableValues = "range[0,255]")
	private String ffmSalesTradeDate;

	/** 得意先コード */
	@Size(max = 255)
	@ApiModelProperty(value = "得意先コード", required = false, position = 74, allowableValues = "range[0,255]")
	private String ffmClientCd;

	/** 売上課所設定区分 */
	@Size(max = 255)
	@ApiModelProperty(value = "売上課所設定区分", required = false, position = 75, allowableValues = "range[0,255]")
	private String ffmSalesLocationType;

	/** 売上課所コード */
	@Size(max = 255)
	@ApiModelProperty(value = "売上課所コード", required = false, position = 76, allowableValues = "range[0,255]")
	private String ffmSalesLocationCd;

	/** 売上社員設定区分 */
	@Size(max = 255)
	@ApiModelProperty(value = "売上社員設定区分", required = false, position = 77, allowableValues = "range[0,255]")
	private String ffmSalesEmpType;

	/** 売上社員コード */
	@Size(max = 255)
	@ApiModelProperty(value = "売上社員コード", required = false, position = 78, allowableValues = "range[0,255]")
	private String ffmSalesEmpCd;

	/** 値引番号 */
	@Size(max = 255)
	@ApiModelProperty(value = "値引番号", required = false, position = 79, allowableValues = "range[0,255]")
	private String ffmDiscntNo;

	/** 伝票番号 */
	@Size(max = 255)
	@ApiModelProperty(value = "伝票番号", required = false, position = 80, allowableValues = "range[0,255]")
	private String ffmSlipNo;

	/** 契約区分 */
	@Size(max = 255)
	@ApiModelProperty(value = "契約区分", required = false, position = 81, allowableValues = "range[0,255]")
	private String ffmContractType;

	/** 売上原価金額 */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "売上原価金額", required = false, position = 82, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal ffmRevenueCostprice;

	/** 振替先振替金額 */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "振替先振替金額", required = false, position = 83, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal ffmTrnsPrice;

	/** 代直区分（販売店データリンク・売上用） */
	@Size(max = 255)
	@ApiModelProperty(value = "代直区分（販売店データリンク・売上用）", required = false, position = 84, allowableValues = "range[0,255]")
	private String ffmDistType;

	/** 売上数量 */
	@Max(99999)
	@ApiModelProperty(value = "売上数量", required = false, position = 85, allowableValues = "range[0,99999]")
	private Integer ffmUserSalesCnt;

	/** ユーザ売上単価 */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "ユーザ売上単価", required = false, position = 86, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal ffmUserSalesPrice;

	/** ユーザ売上単価（税込） */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "ユーザ売上単価（税込）", required = false, position = 87, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal ffmUserSalesPriceInTax;

	/** ユーザ売上金額 */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "ユーザ売上金額", required = false, position = 88, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal ffmUserSalesAmt;

	/** ユーザ売上金額（税込） */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "ユーザ売上金額（税込）", required = false, position = 89, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal ffmUserSalesAmtInTax;

	/** ユーザ売上消費税区分 */
	@Size(max = 255)
	@ApiModelProperty(value = "ユーザ売上消費税区分", required = false, position = 90, allowableValues = "range[0,255]")
	private String ffmUserSalesTaxType;

	/** ユーザ売上消費税率区分 */
	@Size(max = 255)
	@ApiModelProperty(value = "ユーザ売上消費税率区分", required = false, position = 91, allowableValues = "range[0,255]")
	private String ffmUserSalesTaxRate;

	/** ユーザ売上消費税額 */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "ユーザ売上消費税額", required = false, position = 92, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal ffmUserSalesTaxPrice;

	/** RJ売上数量 */
	@Max(99999)
	@ApiModelProperty(value = "RJ売上数量", required = false, position = 93, allowableValues = "range[0,99999]")
	private Integer ffmRjSalesCnt;

	/** RJ売上単価 */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "RJ売上単価", required = false, position = 94, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal ffmRjSalesPrice;

	/** RJ売上単価（税込） */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "RJ売上単価（税込）", required = false, position = 95, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal ffmRjSalesPriceInTax;

	/** RJ売上金額 */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "RJ売上金額", required = false, position = 96, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal ffmRjSalesAmt;

	/** RJ売上金額（税込） */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "RJ売上金額（税込）", required = false, position = 97, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal ffmRjSalesAmtInTax;

	/** RJ売上消費税区分 */
	@Size(max = 255)
	@ApiModelProperty(value = "RJ売上消費税区分", required = false, position = 98, allowableValues = "range[0,255]")
	private String ffmRjSalesTaxType;

	/** RJ売上消費税率区分 */
	@Size(max = 255)
	@ApiModelProperty(value = "RJ売上消費税率区分", required = false, position = 99, allowableValues = "range[0,255]")
	private String ffmRjSalesTaxRate;

	/** RJ売上消費税額 */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "RJ売上消費税額", required = false, position = 100, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal ffmRjSalesTaxPrice;

	/** RJ仕入数量 */
	@Max(99999)
	@ApiModelProperty(value = "RJ仕入数量", required = false, position = 101, allowableValues = "range[0,99999]")
	private Integer ffmRjPurchaseCnt;

	/** RJ仕入単価 */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "RJ仕入単価", required = false, position = 102, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal ffmRjPurchasePrice;

	/** RJ仕入単価（税込） */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "RJ仕入単価（税込）", required = false, position = 103, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal ffmRjPurchasePriceInTax;

	/** RJ仕入金額 */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "RJ仕入金額", required = false, position = 104, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal ffmRjPurchaseAmt;

	/** RJ仕入金額（税込） */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "RJ仕入金額（税込）", required = false, position = 105, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal ffmRjPurchaseAmtInTax;

	/** RJ仕入消費税区分 */
	@Size(max = 255)
	@ApiModelProperty(value = "RJ仕入消費税区分", required = false, position = 106, allowableValues = "range[0,255]")
	private String ffmRjPurchaseTaxType;

	/** RJ仕入消費税率区分 */
	@Size(max = 255)
	@ApiModelProperty(value = "RJ仕入消費税率区分", required = false, position = 107, allowableValues = "range[0,255]")
	private String ffmRjPurchaseTaxRate;

	/** RJ仕入消費税額 */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "RJ仕入消費税額", required = false, position = 108, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal ffmRjPurchaseTaxPrice;

	/** 販売店売上数量 */
	@Max(99999)
	@ApiModelProperty(value = "販売店売上数量", required = false, position = 109, allowableValues = "range[0,99999]")
	private Integer ffmShopSalesCnt;

	/** 販売店売上単価 */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "販売店売上単価", required = false, position = 110, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal ffmShopSalesPrice;

	/** 販売店売上単価（税込） */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "販売店売上単価（税込）", required = false, position = 111, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal ffmShopSalesPriceInTax;

	/** 販売店売上金額 */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "販売店売上金額", required = false, position = 112, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal ffmShopSalesAmt;

	/** 販売店売上金額（税込） */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "販売店売上金額（税込）", required = false, position = 113, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal ffmShopSalesAmtInTax;

	/** 販売店売上消費税区分 */
	@Size(max = 255)
	@ApiModelProperty(value = "販売店売上消費税区分", required = false, position = 114, allowableValues = "range[0,255]")
	private String ffmShopSalesTaxType;

	/** 販売店売上消費税率区分 */
	@Size(max = 255)
	@ApiModelProperty(value = "販売店売上消費税率区分", required = false, position = 115, allowableValues = "range[0,255]")
	private String ffmShopSalesTaxRate;

	/** 販売店売上消費税額 */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "販売店売上消費税額", required = false, position = 116, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal ffmShopSalesTaxPrice;

	/** R原価数量 */
	@Column(name = "ffm_r_cost_cnt")
	@Max(99999)
	@ApiModelProperty(value = "R原価数量", required = false, position = 117, allowableValues = "range[0,99999]")
	private Integer ffmRCostCnt;

	/** R原価単価 */
	@Column(name = "ffm_r_cost_price")
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "R原価単価", required = false, position = 118, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal ffmRCostPrice;

	/** R原価単価（税込） */
	@Column(name = "ffm_r_cost_price_in_tax")
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "R原価単価（税込）", required = false, position = 119, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal ffmRCostPriceInTax;

	/** R原価金額 */
	@Column(name = "ffm_r_cost_amt")
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "R原価金額", required = false, position = 120, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal ffmRCostAmt;

	/** R原価金額（税込） */
	@Column(name = "ffm_r_cost_amt_in_tax")
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "R原価金額（税込）", required = false, position = 121, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal ffmRCostAmtInTax;

	/** R原価消費税区分 */
	@Column(name = "ffm_r_cost_tax_type")
	@Size(max = 255)
	@ApiModelProperty(value = "R原価消費税区分", required = false, position = 122, allowableValues = "range[0,255]")
	private String ffmRCostTaxType;

	/** R原価消費税率区分 */
	@Column(name = "ffm_r_cost_tax_rate")
	@Size(max = 255)
	@ApiModelProperty(value = "R原価消費税率区分", required = false, position = 123, allowableValues = "range[0,255]")
	private String ffmRCostTaxRate;

	/** R原価消費税額 */
	@Column(name = "ffm_r_cost_tax_price")
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "R原価消費税額", required = false, position = 124, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal ffmRCostTaxPrice;

	/** 手数料数量 */
	@Max(99999)
	@ApiModelProperty(value = "手数料数量", required = false, position = 125, allowableValues = "range[0,99999]")
	private Integer ffmCommissionCnt;

	/** 手数料単価 */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "手数料単価", required = false, position = 126, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal ffmCommissionPrice;

	/** 手数料単価（税込） */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "手数料単価（税込）", required = false, position = 127, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal ffmCommissionPriceInTax;

	/** 手数料金額 */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "手数料金額", required = false, position = 128, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal ffmCommissionAmt;

	/** 手数料金額（税込） */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "手数料金額（税込）", required = false, position = 129, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal ffmCommissionAmtInTax;

	/** 手数料消費税区分 */
	@Size(max = 255)
	@ApiModelProperty(value = "手数料消費税区分", required = false, position = 130, allowableValues = "range[0,255]")
	private String ffmCommissionTaxType;

	/** 手数料消費税率区分 */
	@Size(max = 255)
	@ApiModelProperty(value = "手数料消費税率区分", required = false, position = 131, allowableValues = "range[0,255]")
	private String ffmCommissionTaxRate;

	/** 手数料消費税額 */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "手数料消費税額", required = false, position = 132, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal ffmCommissionTaxPrice;

	/** 請求書明細識別コード */
	@Size(max = 255)
	@ApiModelProperty(value = "請求書明細識別コード", required = false, position = 133, allowableValues = "range[0,255]")
	private String ffmBillDetailCd;

	/** 納品書要否区分 */
	@Size(max = 255)
	@ApiModelProperty(value = "納品書要否区分", required = false, position = 134, allowableValues = "range[0,255]")
	private String ffmBillOutputFlg;

	/** 納品書出力パターン */
	@Size(max = 255)
	@ApiModelProperty(value = "納品書出力パターン", required = false, position = 135, allowableValues = "range[0,255]")
	private String ffmBillOutputPtn;

	/** 納品書出力形式 */
	@Size(max = 255)
	@ApiModelProperty(value = "納品書出力形式", required = false, position = 136, allowableValues = "range[0,255]")
	private String ffmBillOutputFmt;

	/** 請求書発行システム */
	@Size(max = 255)
	@ApiModelProperty(value = "請求書発行システム", required = false, position = 137, allowableValues = "range[0,255]")
	private String ffmBillOutputSystem;

	/** 商品名パターン番号（納品書・請求書用） */
	@Size(max = 255)
	@ApiModelProperty(value = "商品名パターン番号（納品書・請求書用）", required = false, position = 138, allowableValues = "range[0,255]")
	private String ffmProdactPtnNo;

	/** 商品名（納品書・請求書用） */
	@Size(max = 255)
	@ApiModelProperty(value = "商品名（納品書・請求書用）", required = false, position = 139, allowableValues = "range[0,255]")
	private String ffmProdactNameForBill;

	/** 業務への連絡事項 */
	@Size(max = 255)
	@ApiModelProperty(value = "業務への連絡事項", required = false, position = 140, allowableValues = "range[0,255]")
	private String ffmMessageForBiz;

	/** 備考（納品書・請求書用） */
	@Size(max = 255)
	@ApiModelProperty(value = "備考（納品書・請求書用）", required = false, position = 141, allowableValues = "range[0,255]")
	private String ffmRemarkForBill;

	/** 請求期間（開始） */
	@Column(name = "ffm_r_billing_period_start")
	@Size(max = 255)
	@ApiModelProperty(value = "請求期間（開始）", required = false, position = 142, allowableValues = "range[0,255]")
	private String ffmRBillingPeriodStart;

	/** 請求期間（終了） */
	@Column(name = "ffm_r_billing_period_end")
	@Size(max = 255)
	@ApiModelProperty(value = "請求期間（終了）", required = false, position = 143, allowableValues = "range[0,255]")
	private String ffmRBillingPeriodEnd;

	/** 請求月 */
	@Size(max = 255)
	@ApiModelProperty(value = "請求月", required = false, position = 144, allowableValues = "range[0,255]")
	private String ffmBillingYm;

	/** 今回の請求回数 */
	@Max(99999)
	@ApiModelProperty(value = "今回の請求回数", required = false, position = 145, allowableValues = "range[0,99999]")
	private Integer ffmThisBillingCnt;

	/** カウンター */
	@Size(max = 255)
	@ApiModelProperty(value = "カウンター", required = false, position = 146, allowableValues = "range[0,255]")
	private String ffmCounter;

	/** コメント１ */
	@Size(max = 255)
	@ApiModelProperty(value = "コメント１", required = false, position = 147, allowableValues = "range[0,255]")
	private String ffmOutputComment1;

	/** コメント２ */
	@Size(max = 255)
	@ApiModelProperty(value = "コメント２", required = false, position = 148, allowableValues = "range[0,255]")
	private String ffmOutputComment2;

	/** 強制フラグ */
	@Max(9)
	@ApiModelProperty(value = "強制フラグ", required = false, position = 149, allowableValues = "range[0,9]")
	private Integer ffmForcedFlg;

	/** 機器設置先名 */
	@Size(max = 255)
	@ApiModelProperty(value = "機器設置先名", required = false, position = 150, allowableValues = "range[0,255]")
	private String ffmInstalltionName;

	/** 機器設置先部課名 */
	@Size(max = 255)
	@ApiModelProperty(value = "機器設置先部課名", required = false, position = 151, allowableValues = "range[0,255]")
	private String ffmInstalltionDptName;

	/** RINGS届先コード(3桁） */
	@Size(max = 255)
	@ApiModelProperty(value = "RINGS届先コード(3桁）", required = false, position = 152, allowableValues = "range[0,255]")
	private String ffmRingsDstCd;

	/** OE届先コード(11桁） */
	@Size(max = 255)
	@ApiModelProperty(value = "OE届先コード(11桁）", required = false, position = 153, allowableValues = "range[0,255]")
	private String ffmOeDstCd;

	/** 納品場所識別 */
	@Size(max = 255)
	@ApiModelProperty(value = "納品場所識別", required = false, position = 154, allowableValues = "range[0,255]")
	private String ffmDstType;

	/** 届先名１（会社名） */
	@Size(max = 255)
	@ApiModelProperty(value = "届先名１（会社名）", required = false, position = 155, allowableValues = "range[0,255]")
	private String ffmDstName1;

	/** 届先名２（会社部課名） */
	@Size(max = 255)
	@ApiModelProperty(value = "届先名２（会社部課名）", required = false, position = 156, allowableValues = "range[0,255]")
	private String ffmDstName2;

	/** 顧客名 */
	@Size(max = 255)
	@ApiModelProperty(value = "顧客名", required = false, position = 157, allowableValues = "range[0,255]")
	private String ffmDstClientName;

	/** 届先住所１ */
	@Size(max = 255)
	@ApiModelProperty(value = "届先住所１", required = false, position = 158, allowableValues = "range[0,255]")
	private String ffmDstAddr1;

	/** 届先住所２ */
	@Size(max = 255)
	@ApiModelProperty(value = "届先住所２", required = false, position = 159, allowableValues = "range[0,255]")
	private String ffmDstAddr2;

	/** 届先住所３ */
	@Size(max = 255)
	@ApiModelProperty(value = "届先住所３", required = false, position = 160, allowableValues = "range[0,255]")
	private String ffmDstAddr3;

	/** 届先郵便番号 */
	@Size(max = 255)
	@ApiModelProperty(value = "届先郵便番号", required = false, position = 161, allowableValues = "range[0,255]")
	private String ffmDstZipCd;

	/** 届先電話番号 */
	@Size(max = 255)
	@ApiModelProperty(value = "届先電話番号", required = false, position = 162, allowableValues = "range[0,255]")
	private String ffmDstTel;

	/** 届先ＦＡＸ番号 */
	@Size(max = 255)
	@ApiModelProperty(value = "届先ＦＡＸ番号", required = false, position = 163, allowableValues = "range[0,255]")
	private String ffmDstFax;

	/** 届先名（カナ） */
	@Size(max = 255)
	@ApiModelProperty(value = "届先名（カナ）", required = false, position = 164, allowableValues = "range[0,255]")
	private String ffmDstNameKana;

	/** 得意先コード（二次店） */
	@Size(max = 255)
	@ApiModelProperty(value = "得意先コード（二次店）", required = false, position = 165, allowableValues = "range[0,255]")
	private String ffmClientCdSec;

	/** 届先コード（二次店） */
	@Size(max = 255)
	@ApiModelProperty(value = "届先コード（二次店）", required = false, position = 166, allowableValues = "range[0,255]")
	private String ffmDstCdSec;

	/** 支払利息相当額 */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "支払利息相当額", required = false, position = 167, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal ffmInterestExpensePrice;

	/** 受取利息相当額 */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "受取利息相当額", required = false, position = 168, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal ffmInterestIncomePrice;

	/** 見積番号 */
	@Size(max = 255)
	@ApiModelProperty(value = "見積番号", required = false, position = 169, allowableValues = "range[0,255]")
	private String ffmQuotationCd;

	/** 見積明細番号 */
	@Size(max = 255)
	@ApiModelProperty(value = "見積明細番号", required = false, position = 170, allowableValues = "range[0,255]")
	private String ffmQuotationDetailCd;

	/** 本体見積明細番号 */
	@Size(max = 255)
	@ApiModelProperty(value = "本体見積明細番号", required = false, position = 171, allowableValues = "range[0,255]")
	private String ffmMainQuotationDetailCd;

	/** R請求書摘要 */
	@Size(max = 255)
	@ApiModelProperty(value = "R請求書摘要", required = false, position = 172, allowableValues = "range[0,255]")
	private String chgBillingText;

	/** 一次店R会社コード */
	@Column(name = "chg_r_company_code_1st")
	@Size(max = 255)
	@ApiModelProperty(value = "一次店R会社コード", required = false, position = 173, allowableValues = "range[0,255]")
	private String chgRCompanyCode1st;

	/** 一次店R会社名 */
	@Column(name = "chg_r_company_name_1st")
	@Size(max = 255)
	@ApiModelProperty(value = "一次店R会社名", required = false, position = 174, allowableValues = "range[0,255]")
	private String chgRCompanyName1st;

	/** 一次店販売店ID */
	@Column(name = "chg_shop_id_1st")
	@Size(max = 255)
	@ApiModelProperty(value = "一次店販売店ID", required = false, position = 175, allowableValues = "range[0,255]")
	private String chgShopId1st;

	/** 一次店販売店名 */
	@Column(name = "chg_shop_name_1st")
	@Size(max = 255)
	@ApiModelProperty(value = "一次店販売店名", required = false, position = 176, allowableValues = "range[0,255]")
	private String chgShopName1st;

	/** 一次店販売店摘要 */
	@Column(name = "chg_shop_text_1st")
	@Size(max = 255)
	@ApiModelProperty(value = "一次店販売店摘要", required = false, position = 177, allowableValues = "range[0,255]")
	private String chgShopText1st;

	/** 二次店R会社コード */
	@Column(name = "chg_r_company_code_2st")
	@Size(max = 255)
	@ApiModelProperty(value = "二次店R会社コード", required = false, position = 178, allowableValues = "range[0,255]")
	private String chgRCompanyCode2st;

	/** 二次店R会社名 */
	@Column(name = "chg_r_company_name_2st")
	@Size(max = 255)
	@ApiModelProperty(value = "二次店R会社名", required = false, position = 179, allowableValues = "range[0,255]")
	private String chgRCompanyName2st;

	/** 二次店販売店ID */
	@Column(name = "chg_shop_id_2st")
	@Size(max = 255)
	@ApiModelProperty(value = "二次店販売店ID", required = false, position = 180, allowableValues = "range[0,255]")
	private String chgShopId2st;

	/** 二次店販売店名 */
	@Column(name = "chg_shop_name_2st")
	@Size(max = 255)
	@ApiModelProperty(value = "二次店販売店名", required = false, position = 181, allowableValues = "range[0,255]")
	private String chgShopName2st;

	/** 二次店販売店摘要 */
	@Column(name = "chg_shop_text_2st")
	@Size(max = 255)
	@ApiModelProperty(value = "二次店販売店摘要", required = false, position = 182, allowableValues = "range[0,255]")
	private String chgShopText2st;

	/** フォーマット種別 */
	@Size(max = 255)
	@ApiModelProperty(value = "フォーマット種別", required = false, position = 183, allowableValues = "range[0,255]")
	private String cubicFmtType;

	/** 勘定科目コード */
	@Size(max = 255)
	@ApiModelProperty(value = "勘定科目コード", required = false, position = 184, allowableValues = "range[0,255]")
	private String cubicAccountingCd;

	/** 貸借区分 */
	@Size(max = 255)
	@ApiModelProperty(value = "貸借区分", required = false, position = 185, allowableValues = "range[0,255]")
	private String cubicLcType;

	/** システムコード */
	@Size(max = 255)
	@ApiModelProperty(value = "システムコード", required = false, position = 186, allowableValues = "range[0,255]")
	private String cubicSystemCd;

	/** CUBIC会社コード */
	@Size(max = 255)
	@ApiModelProperty(value = "CUBIC会社コード", required = false, position = 187, allowableValues = "range[0,255]")
	private String cubicCompanyCd;

	/** 会計計上日 */
	@Size(max = 255)
	@ApiModelProperty(value = "会計計上日", required = false, position = 188, allowableValues = "range[0,255]")
	private String cubicAccountingDate;

	/** 伝票ＮＯ */
	@Size(max = 255)
	@ApiModelProperty(value = "伝票ＮＯ", required = false, position = 189, allowableValues = "range[0,255]")
	private String cubicVoucherDate;

	/** 伝票明細NO */
	@Size(max = 255)
	@ApiModelProperty(value = "伝票明細NO", required = false, position = 190, allowableValues = "range[0,255]")
	private String cubicVoucherDetailDate;

	/** 計上部門コード */
	@Size(max = 255)
	@ApiModelProperty(value = "計上部門コード", required = false, position = 191, allowableValues = "range[0,255]")
	private String cubicAccountDeptCd;

	/** 商品軸 */
	@Size(max = 255)
	@ApiModelProperty(value = "商品軸", required = false, position = 192, allowableValues = "range[0,255]")
	private String cubicProductAxis;

	/** 営業軸 */
	@Size(max = 255)
	@ApiModelProperty(value = "営業軸", required = false, position = 193, allowableValues = "range[0,255]")
	private String cubicSalesAxis;

	/** 決算識別子 */
	@Size(max = 255)
	@ApiModelProperty(value = "決算識別子", required = false, position = 194, allowableValues = "range[0,255]")
	private String cubicFinancialIdentifier;

	/** CUBIC品種コード */
	@Size(max = 255)
	@ApiModelProperty(value = "CUBIC品種コード", required = false, position = 195, allowableValues = "range[0,255]")
	private String cubicProductTypeCd;

	/** 増減理由 */
	@Size(max = 255)
	@ApiModelProperty(value = "増減理由", required = false, position = 196, allowableValues = "range[0,255]")
	private String cubicInDecReason;

	/** 環境会計コード */
	@Size(max = 255)
	@ApiModelProperty(value = "環境会計コード", required = false, position = 197, allowableValues = "range[0,255]")
	private String cubicEnvAccountCd;

	/** プロジェクトコード */
	@Size(max = 255)
	@ApiModelProperty(value = "プロジェクトコード", required = false, position = 198, allowableValues = "range[0,255]")
	private String cubicProjectCd;

	/** 数量 */
	@Max(99999)
	@ApiModelProperty(value = "数量", required = false, position = 199, allowableValues = "range[0,99999]")
	private Integer cubicCount;

	/** 取引金額 */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "取引金額", required = false, position = 200, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal cubicAmount;

	/** 外貨取引金額 */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "外貨取引金額", required = false, position = 201, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal cubicAmountForForeign;

	/** 通貨コード */
	@Size(max = 255)
	@ApiModelProperty(value = "通貨コード", required = false, position = 202, allowableValues = "range[0,255]")
	private String cubicCurrencyCd;

	/** 通貨換算タイプ */
	@Size(max = 255)
	@ApiModelProperty(value = "通貨換算タイプ", required = false, position = 203, allowableValues = "range[0,255]")
	private String cubicCurrencyConvType;

	/** 通貨換算レート */
	@DecimalMax("99999.99")
	@ApiModelProperty(value = "通貨換算レート", required = false, position = 204, allowableValues = "range[0.00,99999.99]")
	private BigDecimal cubicCurrencyConvRate;

	/** 通貨換算日 */
	@Size(max = 255)
	@ApiModelProperty(value = "通貨換算日", required = false, position = 205, allowableValues = "range[0,255]")
	private String cubicCurrencyConvDate;

	/** 摘要 */
	@Size(max = 255)
	@ApiModelProperty(value = "摘要", required = false, position = 206, allowableValues = "range[0,255]")
	private String cubicText;

	/** 明細摘要 */
	@Size(max = 255)
	@ApiModelProperty(value = "明細摘要", required = false, position = 207, allowableValues = "range[0,255]")
	private String cubicTextDetail;

	/** 各社セグメント */
	@Size(max = 255)
	@ApiModelProperty(value = "各社セグメント", required = false, position = 208, allowableValues = "range[0,255]")
	private String cubicCoSegment;

	/** 管理セグメント予備2 */
	@Size(max = 255)
	@ApiModelProperty(value = "管理セグメント予備2", required = false, position = 209, allowableValues = "range[0,255]")
	private String cubicCoSegment1;

	/** 管理セグメント予備3 */
	@Size(max = 255)
	@ApiModelProperty(value = "管理セグメント予備3", required = false, position = 210, allowableValues = "range[0,255]")
	private String cubicCoSegment2;

	/** 消し込みキー */
	@Size(max = 255)
	@ApiModelProperty(value = "消し込みキー", required = false, position = 211, allowableValues = "range[0,255]")
	private String cubicDeleteKey;

	/** 扱い者コード */
	@Size(max = 255)
	@ApiModelProperty(value = "扱い者コード", required = false, position = 212, allowableValues = "range[0,255]")
	private String cubicOperatorCd;

	/** グリーン購買コード */
	@Size(max = 255)
	@ApiModelProperty(value = "グリーン購買コード", required = false, position = 213, allowableValues = "range[0,255]")
	private String cubicGreenBuyCd;

	/** 案件ＮＯ */
	@Size(max = 255)
	@ApiModelProperty(value = "案件ＮＯ", required = false, position = 214, allowableValues = "range[0,255]")
	private String cubicProjectNo;

	/** Ｄ／Ｆ ＮＯ */
	@Size(max = 255)
	@ApiModelProperty(value = "Ｄ／Ｆ　ＮＯ", required = false, position = 215, allowableValues = "range[0,255]")
	private String cubicDfNo;

	/** 予算ＮＯ */
	@Size(max = 255)
	@ApiModelProperty(value = "予算ＮＯ", required = false, position = 216, allowableValues = "range[0,255]")
	private String cubicBudgetNo;

	/** 顧客コード */
	@Size(max = 255)
	@ApiModelProperty(value = "顧客コード", required = false, position = 217, allowableValues = "range[0,255]")
	private String cubicClientCd;

	/** 各社固有管理セグメント1 */
	@Size(max = 255)
	@ApiModelProperty(value = "各社固有管理セグメント1", required = false, position = 218, allowableValues = "range[0,255]")
	private String cubicCoMgtSegment;

	/** 取引日 */
	@Size(max = 255)
	@ApiModelProperty(value = "取引日", required = false, position = 219, allowableValues = "range[0,255]")
	private String cubicTransactionDate;

	/** 請求先サイトコード */
	@Size(max = 255)
	@ApiModelProperty(value = "請求先サイトコード", required = false, position = 220, allowableValues = "range[0,255]")
	private String cubicBillDstSiteCd;

	/** 国内／海外区分 */
	@Size(max = 255)
	@ApiModelProperty(value = "国内／海外区分", required = false, position = 221, allowableValues = "range[0,255]")
	private String cubicDomesticForeignType;

	/** 取引単価（税抜） */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "取引単価（税抜）", required = false, position = 222, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal cubicSalesPriceNoTax;

	/** 外貨取引単価 */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "外貨取引単価", required = false, position = 223, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal cubicSalesPriceForeign;

	/** 回収条件名 */
	@Size(max = 255)
	@ApiModelProperty(value = "回収条件名", required = false, position = 224, allowableValues = "range[0,255]")
	private String cubicRecoveryReqName;

	/** 回収方法名 */
	@Size(max = 255)
	@ApiModelProperty(value = "回収方法名", required = false, position = 225, allowableValues = "range[0,255]")
	private String cubicRecoveryMethodName;

	/** 回収起算日 */
	@Size(max = 255)
	@ApiModelProperty(value = "回収起算日", required = false, position = 226, allowableValues = "range[0,255]")
	private String cubicRecoveryDate;

	/** 請求分類名 */
	@Size(max = 255)
	@ApiModelProperty(value = "請求分類名", required = false, position = 227, allowableValues = "range[0,255]")
	private String cubicBillingTypeName;

	/** CUBIC請求書明細識別コード */
	@Size(max = 255)
	@ApiModelProperty(value = "CUBIC請求書明細識別コード", required = false, position = 228, allowableValues = "range[0,255]")
	private String cubicBillDetailTypeCode;

	/** 値引名称 */
	@Size(max = 255)
	@ApiModelProperty(value = "値引名称", required = false, position = 229, allowableValues = "range[0,255]")
	private String cubicDiscountName;

	/** 請求書発行区分 */
	@Size(max = 255)
	@ApiModelProperty(value = "請求書発行区分", required = false, position = 230, allowableValues = "range[0,255]")
	private String cubicBillOutputType;

	/** 請求書ＮＯ */
	@Size(max = 255)
	@ApiModelProperty(value = "請求書ＮＯ", required = false, position = 231, allowableValues = "range[0,255]")
	private String cubicBillingNo;

	/** 荷為替手形ＮＯ */
	@Size(max = 255)
	@ApiModelProperty(value = "荷為替手形ＮＯ", required = false, position = 232, allowableValues = "range[0,255]")
	private String cubicDocumentaryBillNo;

	/** Ｐ／ＣキーＮＯ */
	@Size(max = 255)
	@ApiModelProperty(value = "Ｐ／ＣキーＮＯ", required = false, position = 233, allowableValues = "range[0,255]")
	private String cubicPcKeyNo;

	/** 請求書出力用伝票ＮＯ */
	@Size(max = 255)
	@ApiModelProperty(value = "請求書出力用伝票ＮＯ", required = false, position = 234, allowableValues = "range[0,255]")
	private String cubicBillingOutputNo;

	/** 受注ＮＯ */
	@Size(max = 255)
	@ApiModelProperty(value = "受注ＮＯ", required = false, position = 235, allowableValues = "range[0,255]")
	private String cubicReceivedOrderNo;

	/** 発注ＮＯ */
	@Size(max = 255)
	@ApiModelProperty(value = "発注ＮＯ", required = false, position = 236, allowableValues = "range[0,255]")
	private String cubicOrderNo;

	/** 前受管理ＮＯ */
	@Size(max = 255)
	@ApiModelProperty(value = "前受管理ＮＯ", required = false, position = 237, allowableValues = "range[0,255]")
	private String cubicBeforeManageNo;

	/** 前受金消込額 */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "前受金消込額", required = false, position = 238, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal cubicBeforeCancelAmt;

	/** 追加ＴＥＲＭ */
	@Size(max = 255)
	@ApiModelProperty(value = "追加ＴＥＲＭ", required = false, position = 239, allowableValues = "range[0,255]")
	private String cubicAddTerm;

	/** 契約NO */
	@Size(max = 255)
	@ApiModelProperty(value = "契約NO", required = false, position = 240, allowableValues = "range[0,255]")
	private String cubicContractNo;

	/** 品名 */
	@Size(max = 255)
	@ApiModelProperty(value = "品名", required = false, position = 241, allowableValues = "range[0,255]")
	private String cubicProdactName;

	/** 債権債務汎用照合キー */
	@Size(max = 255)
	@ApiModelProperty(value = "債権債務汎用照合キー", required = false, position = 242, allowableValues = "range[0,255]")
	private String cubicMatchingKey;

	/** 汎用転送データ */
	@Size(max = 255)
	@ApiModelProperty(value = "汎用転送データ", required = false, position = 243, allowableValues = "range[0,255]")
	private String cubicGeneralTransferData;

	/** 元伝票ＮＯ（赤伝時） */
	@Size(max = 255)
	@ApiModelProperty(value = "元伝票ＮＯ（赤伝時）", required = false, position = 244, allowableValues = "range[0,255]")
	private String cubicOrgSlipNoForRed;

	/** 元伝票明細ＮＯ（赤伝時） */
	@Size(max = 255)
	@ApiModelProperty(value = "元伝票明細ＮＯ（赤伝時）", required = false, position = 245, allowableValues = "range[0,255]")
	private String cubicOrgSlipDetailNoForRed;

	/** 元会計計上日（赤伝時） */
	@Size(max = 255)
	@ApiModelProperty(value = "元会計計上日（赤伝時）", required = false, position = 246, allowableValues = "range[0,255]")
	private String cubicOrgAcctDateForRed;

	/** 設置先サイトコード */
	@Size(max = 255)
	@ApiModelProperty(value = "設置先サイトコード", required = false, position = 247, allowableValues = "range[0,255]")
	private String cubicDstSiteCd;

	/** 項目予備1 */
	@Size(max = 255)
	@ApiModelProperty(value = "項目予備1", required = false, position = 248, allowableValues = "range[0,255]")
	private String cubicItem1;

	/** 項目予備2 */
	@Size(max = 255)
	@ApiModelProperty(value = "項目予備2", required = false, position = 249, allowableValues = "range[0,255]")
	private String cubicItem2;

	/** 項目予備3 */
	@Size(max = 255)
	@ApiModelProperty(value = "項目予備3", required = false, position = 250, allowableValues = "range[0,255]")
	private String cubicItem3;

	/** 拡張項目 */
	@ApiModelProperty(value = "拡張項目", required = false, position = 251)
	@Lob
	private String extendItem;

}
