package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import lombok.Data;

/**
 * MoM請求売上先サイト情報マスタ
 */
@Entity
@Data
@Table(name = "mv_t_jmci101")
public class MvTJmci101Master {

	/** サイト番号 */
	@Id
	private String customerSiteNumber;

	/** 営業単位コード */
	private String salesUnitCode;

	/** 販社コード */
	private String hanshCd;

	/** 管理情報ID */
	private String systemSeqNumber;

	/** 顧客（企業）ID */
	private String customerNumber;

	/** MoM企業ID */
	private String sortCompanyId;

	/** MoM事業所ID */
	private String businessPlaceId;

	/** MoM部門ID */
	private String bmnId;

	/** 上流システムコード */
	private String originalSystemCode;

	/** コピー識別 */
	private String copyClassification;

	/** 名寄せ親MoM企業ID */
	private String linkCompanyCode;

	/** 名寄せ親MoM事業所ID */
	private String linkBusinessPlaceCode;

	/** 名寄せ親MoM部門ID */
	private String linkPostCode;

	/** システム連携ID */
	private String cuicCompanyId;

	/** 名寄せ親MOM顧客企事部ID */
	private String linkMomCstId;

	/** 名寄せ区分 */
	private String identityCls;

	/** 住所１ */
	private String address1;

	/** 住所２ */
	private String address2;

	/** 事業所正式名称 */
	private String ebsBusinessPlaceName;

	/** 郵便番号 */
	private String postalCode;

	/** 所在地の識別 */
	private String identifyingAddressFlag;

	/** 顧客住所バーコード情報 */
	private String addressBarcode;

	/** 有効 */
	private String businessPlaceStatus;

	/** カテゴリ */
	private String customerCategoryCode;

	/** 代替MoM企業ID */
	private String substiutionCustomerId;

	/** 代替MoM事業所ID */
	private String substiutionEstmtId;

	/** 住所コード */
	private String jyushoCode;

	/** 代替システム連携ID */
	private String substiutionCompanyId;

	/** 請求先名特別対応フラグ */
	private String billingCustomerSpecialFlg;

	/** 事業所正式名称カナ */
	private String businessPlaceNameKana;

	/** フリー入力項目1 */
	@Column(name = "free_01")
	private String free01;

	/** フリー入力項目2 */
	@Column(name = "free_02")
	private String free02;

	/** フリー入力項目3 */
	@Column(name = "free_03")
	private String free03;

	/** フリー入力項目4 */
	@Column(name = "free_04")
	private String free04;

	/** フリー入力項目5 */
	@Column(name = "free_05")
	private String free05;

	/** フリー入力項目6 */
	@Column(name = "free_06")
	private String free06;

	/** フリー入力項目7 */
	@Column(name = "free_07")
	private String free07;

	/** フリー入力項目8 */
	@Column(name = "free_08")
	private String free08;

	/** フリー入力項目9 */
	@Column(name = "free_09")
	private String free09;

	/** フリー入力項目10 */
	@Column(name = "free_10")
	private String free10;

	/** 使用目的 */
	private String siteUseCode;

	/** 事業所簡略名称 */
	private String location;

	/** 請求先事業所 */
	private String billingCustomerAddress;

	/** 主 */
	private String primaryFlag;

	/** 有効 */
	private String status;

	/** 税金コード */
	private String taxCode;

	/** 回収条件 */
	private String standardTerms;

	/** 手形サイト */
	private String billSite;

	/** 営業担当ID */
	private String primarySalesrepId;

	/** 受注タイプ */
	private String orderTypeId;

	/** 価格表 */
	private String priceListId;

	/** 倉庫ID */
	private String warehouseId;

	/** 出荷方法 */
	private String freightTerm;

	/** 親請求先サイトコード */
	private String destinationBillSiteCd;

	/** 請求書発送区分 */
	private String invoiceShippingCls;

	/** 請求書フォーム区分 */
	private String invoiceFormKbn;

	/** 振込票付加フラグ */
	private String trnsfrFormFlg;

	/** 請求分類合計出力フラグ */
	private String invoiceTypeTotalFlg;

	/** Ｐ／Ｃ請求一覧表出力区分 */
	private String pcInvoiceListIssueKbn;

	/** 代直区分 */
	private String sellingKbn;

	/** 振込先自社口座_銀行コード(1) */
	@Column(name = "bnk_cd_1")
	private String bnkCd1;

	/** 振込先自社口座_本支店コード(1) */
	@Column(name = "bnk_branch_cd_1")
	private String bnkBranchCd1;

	/** 振込先自社口座_口座番号(1) */
	@Column(name = "bnk_account_cd_1")
	private String bnkAccountCd1;

	/** 振込先自社口座_通貨コード(1) */
	@Column(name = "currency_cd_1")
	private String currencyCd1;

	/** 振込先自社口座_銀行コード(2) */
	@Column(name = "bnk_cd_2")
	private String bnkCd2;

	/** 振込先自社口座_支店コード(2) */
	@Column(name = "bnk_branch_cd_2")
	private String bnkBranchCd2;

	/** 振込先自社口座_口座番号(2) */
	@Column(name = "bnk_account_cd_2")
	private String bnkAccountCd2;

	/** 振込先自社口座_通貨コード(2) */
	@Column(name = "currency_cd_2")
	private String currencyCd2;

	/** 請求作業区分 */
	private String invoiceWorkKbn;

	/** 請求作業区分補足 */
	private String invoiceWorkSupplement;

	/** 代表入金サイトコード */
	private String representativeCode;

	/** 自振引落日 */
	private String autoTransferDay;

	/** 相殺コード */
	private String offsetCode;

	/** 領収証№入力区分 */
	private String receiptFlag;

	/** 督促状発行区分 */
	private String dunningLetterFlag;

	/** 残高確認書（売掛金）出力フラグ */
	private String accountReceivableFlag;

	/** 残高確認書（未収金）出力フラグ */
	private String accruedFlag;

	/** 残高連絡票出力フラグ */
	private String balanceReportFlag;

	/** デビクレステートメント出力フラグ */
	private String demoStatementFlag;

	/** 回収メモ */
	private String collectMemo;

	/** 回収方法補足 */
	private String collectMethodSupplement;

	/** 照合実施フラグ */
	private String matchingFlag;

	/** 銀行手数料内生対象フラグ */
	private String bankChargeFlag;

	/** 顧客担当部門コード */
	private String customerPersonSectionCode;

	/** 請求書送付先詳細情報 */
	private String invoiceDestDetailInfo;

	/** 売掛/未収区分 */
	private String siteCls;

	/** ステートメント送付先フラグ */
	private String statementFlag;

	/** ０円明細表示フラグ */
	private String cubarZeroLineDispFlg;

	/** 請求書残高表示区分 */
	private String cubarInvBalDispFlg;

	/** 振込先自社口座_口座種別(1) */
	private String bankAccountCls1;

	/** 振込先自社口座_口座種別(2) */
	private String bankAccountCls2;

	/** RINGS親請求先非連携フラグ */
	private String ringsParentUpdateFlg;

	/** 日本OSフラグ */
	private String iciJpOsFlg;

	/** 他社代行回収先サイトコード */
	private String agencyDestinationCd;

	/** 取引先コード（RINGS用） */
	private String ringsOriginalSystemCode;

	/** 単価小数表示区分 */
	private String unitPriceSmallindication;

	/** 回収方法名 */
	private String collectMethodName;

	/** 主 */
	private String bankPrimaryFlag;

	/** 有効日（自） */
	@Temporal(TemporalType.DATE)
	private Date bankStartDate;

	/** 有効日（至） */
	@Temporal(TemporalType.DATE)
	private Date bankEndDate;

	/** プロファイル区分 */
	private String profileKbn;

	/** 回収担当者名 */
	private String collectorName;

	/** 与信チェック */
	private String creditChecking;

	/** 通貨 */
	private String creditCurrencyCode;

	/** 与信限度額 */
	private Long overallCreditLimit;

	/** 受注当り与信限度額 */

	private Long trxCreditLimit;

	/** ICI更新日時 */
	@Temporal(TemporalType.DATE)
	private Date iciUpdtDt;

	/** MoM最終更新日時時刻 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date scMomUpdtDtTm;

	/** 領収証自動発行区分 */
	private String receiptAutoPrintKbn;

	/** バーチャル口座使用区分 */
	private String virtualBnkAccountUseKbn;

	/** ICIﾘﾌｧﾚﾝｽ情報更新日 */
	@Temporal(TemporalType.DATE)
	private Date rfiUpdtDt;

	/** 請求書処理日区分 */
	private String invoicePrcsDtKbn;

	/** バーチャル銀行コード */
	private String virtualBnkCd;

	/** バーチャル本支店コード */
	private String virtualBnkBranchCd;

	/** バーチャル口座種別 */
	private String virtualBankAccountType;

	/** バーチャル口座番号 */
	private String virtualBankAccountNum;

	/** ICI作成日時 */
	@Temporal(TemporalType.DATE)
	private Date iciCrtDt;

	/** 更新者ID（売上先情報） */
	private String sciLastUpdatedBy;

	/** クレンジング事業所正式名称 */
	private String clnEbsBusinessPlaceName;

	/** クレンジング事業所正式名カナ */
	private String clnBusinessPlaceNameKana;

	/** MoM会社ID */
	private String momKishId;

	/** RINGS住所情報非連携フラグ */
	private String ringsAddressUpdateFlag;

	/** 都道府県コード */
	private String todohukenCode;

	/** 手入力フラグ */
	private String addressCdRefFlag;

	/** フリー項目表示区分 */
	private String freeDispKbn;

	/** 勘定区分 */
	private String accountKbn;
}