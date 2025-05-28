package jp.co.ricoh.cotos.commonlib.entity.master;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 機種略号マスタ
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "model_abbreviation_master")
public class ModelAbbreviationMaster extends EntityBaseMaster {

	/**
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "model_abbreviation_master_seq")
	@SequenceGenerator(name = "model_abbreviation_master_seq", sequenceName = "model_abbreviation_master_seq", allocationSize = 1)
	@ApiModelProperty(value = "ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999999999999]")
	private long id;

	/**
	 * 機種コード
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "機種コード", required = false, position = 2, allowableValues = "range[0,255]")
	private String nModelCode;

	/**
	 * 機種群コード
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "機種群コード", required = false, position = 3, allowableValues = "range[0,255]")
	private String nModelCategoryCode;

	/**
	 * 機種名(漢字)
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "機種名(漢字)", required = false, position = 4, allowableValues = "range[0,255]")
	private String nModelDesc;

	/**
	 * 機種名(カナ)
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "機種名(カナ)", required = false, position = 5, allowableValues = "range[0,255]")
	private String nModelDescKana;

	/**
	 * 簡略機種名(カナ)
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "簡略機種名(カナ)", required = false, position = 6, allowableValues = "range[0,255]")
	private String nSimpleModelDescKana;

	/**
	 * PM基準
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "PM基準", required = false, position = 7, allowableValues = "range[0,255]")
	private String nPmStandard;

	/**
	 * PM枚数
	 */
	@Max(99999)
	@Min(0)
	@ApiModelProperty(value = "PM枚数", required = false, position = 8, allowableValues = "range[0,99999]")
	private Integer nPmSheets;

	/**
	 * PM枚数2
	 */
	@Max(99999)
	@Min(0)
	@ApiModelProperty(value = "PM枚数2", required = false, position = 9, allowableValues = "range[0,99999]")
	private Integer nPmSheets2;

	/**
	 * PM期間
	 */
	@Max(99999)
	@Min(0)
	@ApiModelProperty(value = "PM期間", required = false, position = 10, allowableValues = "range[0,99999]")
	private Integer nPmPeriod;

	/**
	 * PM期間2
	 */
	@Max(99999)
	@Min(0)
	@ApiModelProperty(value = "PM期間2", required = false, position = 11, allowableValues = "range[0,99999]")
	private Integer nPmPeriod2;

	/**
	 * PMC
	 */
	@Max(99999)
	@Min(0)
	@ApiModelProperty(value = "PMC", required = false, position = 12, allowableValues = "range[0,99999]")
	private Integer nPmc;

	/**
	 * 分岐
	 */
	@Max(99999)
	@Min(0)
	@ApiModelProperty(value = "分岐", required = false, position = 13, allowableValues = "range[0,99999]")
	private Integer nTurning;

	/**
	 * PM回数
	 */
	@Max(99999)
	@Min(0)
	@ApiModelProperty(value = "PM回数", required = false, position = 14, allowableValues = "range[0,99999]")
	private Integer nPmNum;

	/**
	 * 点検起算
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "点検起算", required = false, position = 15, allowableValues = "range[0,255]")
	private String nMaintenanceReckon;

	/**
	 * 点検分岐
	 */
	@Max(99999)
	@Min(0)
	@ApiModelProperty(value = "点検分岐", required = false, position = 16, allowableValues = "range[0,99999]")
	private Integer nMaintenanceTurning;

	/**
	 * 以下PMC
	 */
	@Max(99999)
	@Min(0)
	@ApiModelProperty(value = "以下PMC", required = false, position = 17, allowableValues = "range[0,99999]")
	private Integer nBelowPmc;

	/**
	 * 以上PMC
	 */
	@Max(99999)
	@Min(0)
	@ApiModelProperty(value = "以上PMC", required = false, position = 18, allowableValues = "range[0,99999]")
	private Integer nOverPmc;

	/**
	 * 以下枚数
	 */
	@Max(99999)
	@Min(0)
	@ApiModelProperty(value = "以下枚数", required = false, position = 19, allowableValues = "range[0,99999]")
	private Integer nBelowSheets;

	/**
	 * 以上枚数
	 */
	@Max(99999)
	@Min(0)
	@ApiModelProperty(value = "以上枚数", required = false, position = 20, allowableValues = "range[0,99999]")
	private Integer nOverSheets;

	/**
	 * 点検枚数
	 */
	@Max(99999)
	@Min(0)
	@ApiModelProperty(value = "点検枚数", required = false, position = 21, allowableValues = "range[0,99999]")
	private Integer nInspectionSheets;

	/**
	 * アローワンス
	 */
	@Max(99999)
	@Min(0)
	@ApiModelProperty(value = "アローワンス", required = false, position = 22, allowableValues = "range[0,99999]")
	private Integer nAllowance;

	/**
	 * 限界
	 */
	@Max(99999)
	@Min(0)
	@ApiModelProperty(value = "限界", required = false, position = 23, allowableValues = "range[0,99999]")
	private Integer nLimit;

	/**
	 * 保証期間
	 */
	@Max(99999)
	@Min(0)
	@ApiModelProperty(value = "保証期間", required = false, position = 24, allowableValues = "range[0,99999]")
	private Integer nGuaranteeTerm;

	/**
	 * 換算係数
	 */
	@Max(99999)
	@Min(0)
	@ApiModelProperty(value = "換算係数", required = false, position = 25, allowableValues = "range[0,99999]")
	private Integer nConvFactor;

	/**
	 * PM標準作業時間(分)
	 */
	@Max(99999)
	@Min(0)
	@ApiModelProperty(value = "PM標準作業時間(分)", required = false, position = 26, allowableValues = "range[0,99999]")
	private Integer nPmStandardWorkMin;

	/**
	 * 点検標準作業(時間)
	 */
	@Max(99999)
	@Min(0)
	@ApiModelProperty(value = "点検標準作業(時間)", required = false, position = 27, allowableValues = "range[0,99999]")
	private Integer nMaintStandardWorkHour;

	/**
	 * シリーズコード
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "シリーズコード", required = false, position = 28, allowableValues = "range[0,255]")
	private String nSeriesCode;

	/**
	 * 重点機種区分
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "重点機種区分", required = false, position = 29, allowableValues = "range[0,255]")
	private String nImportantModeType;

	/**
	 * 感熱区分
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "感熱区分", required = false, position = 30, allowableValues = "range[0,255]")
	private String nThermalType;

	/**
	 * 委託料商品コード
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "委託料商品コード", required = false, position = 31, allowableValues = "range[0,255]")
	private String nTrustProdCode;

	/**
	 * 年間保守料
	 */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "年間保守料", required = false, position = 32, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal nMaintFeeYear;

	/**
	 * 月間保守料
	 */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "月間保守料", required = false, position = 33, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal nMaintFeeMonth;

	/**
	 * 初年度保証費(年)
	 */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "初年度保証費(年)", required = false, position = 34, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal nFirstGuaranteeYear;

	/**
	 * 初年度保証費(月)
	 */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "初年度保証費(月)", required = false, position = 35, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal nFirstGuaranteeMonth;

	/**
	 * 作業料
	 */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "作業料", required = false, position = 36, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal nWorkPrice;

	/**
	 * 基本料
	 */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "基本料", required = false, position = 37, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal nBasePrice;

	/**
	 * FAXキット料
	 */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "FAXキット料", required = false, position = 38, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal nFaxKitCharge;

	/**
	 * Hシリーズ請求区分
	 */
	@Size(max = 255)
	@Column(name = "n_h_series_inv_type")
	@ApiModelProperty(value = "Hシリーズ請求区分", required = false, position = 39, allowableValues = "range[0,255]")
	private String nHSeriesInvType;

	/**
	 * OEM区分
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "OEM区分", required = false, position = 40, allowableValues = "range[0,255]")
	private String nOemType;

	/**
	 * システムデバイス区分
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "システムデバイス区分", required = false, position = 41, allowableValues = "range[0,255]")
	private String nSystemDeviceType;

	/**
	 * 更新区分
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "更新区分", required = false, position = 42, allowableValues = "range[0,255]")
	private String nModifyType;

	/**
	 * 更新年月日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "更新年月日", required = false, position = 43)
	private Date nModifyDate;

	/**
	 * 登録年月日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "登録年月日", required = false, position = 44)
	private Date nNewDate;

	/**
	 * リモート診断区分
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "リモート診断区分", required = false, position = 45, allowableValues = "range[0,255]")
	private String nRemoteType;

	/**
	 * 機種グループ
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "機種グループ", required = false, position = 46, allowableValues = "range[0,255]")
	private String nModelGroup;

	/**
	 * 売掛機種群コード
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "売掛機種群コード", required = false, position = 47, allowableValues = "range[0,255]")
	private String nCreditModelCatCode;

	/**
	 * 機種群名(漢字)
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "機種群名(漢字)", required = false, position = 48, allowableValues = "range[0,255]")
	private String nModelCategoryDesc;

	/**
	 * 機種群名(カナ)
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "機種群名(カナ)", required = false, position = 49, allowableValues = "range[0,255]")
	private String nModelCategoryDescKana;

	/**
	 * 機種簡略
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "機種簡略", required = false, position = 50, allowableValues = "range[0,255]")
	private String nSimpleModel;

	/**
	 * PCリンク区分
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "PCリンク区分", required = false, position = 51, allowableValues = "range[0,255]")
	private String nPcLinkType;

	/**
	 * 可能保守形態
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "可能保守形態", required = false, position = 52, allowableValues = "range[0,255]")
	private String nPossibleMaintType;

	/**
	 * 可能機番(始)
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "可能機番(始)", required = false, position = 53, allowableValues = "range[0,255]")
	private String nPossMechNoFrom;

	/**
	 * 可能機番(終)
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "可能機番(終)", required = false, position = 54, allowableValues = "range[0,255]")
	private String nPossMechNoTo;

	/**
	 * PPF基準
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "PPF基準", required = false, position = 55, allowableValues = "range[0,255]")
	private String nPpfStandard;

	/**
	 * 発売年月
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "発売年月", required = false, position = 56, allowableValues = "range[0,255]")
	private String nSellMonth;

	/**
	 * 出荷打切年月
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "出荷打切年月", required = false, position = 57, allowableValues = "range[0,255]")
	private String nCloseMonth;

	/**
	 * 打切告知予定年月
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "打切告知予定年月", required = false, position = 58, allowableValues = "range[0,255]")
	private String nClosePlanMonth;

	/**
	 * 打切告知開始年月
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "打切告知開始年月", required = false, position = 59, allowableValues = "range[0,255]")
	private String nCloseStartMonth;

	/**
	 * L保守開始年月
	 */
	@Size(max = 255)
	@Column(name = "n_l_maint_start_month")
	@ApiModelProperty(value = "L保守開始年月", required = false, position = 60, allowableValues = "range[0,255]")
	private String nLMaintStartMonth;

	/**
	 * AS機フラグ
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "L保守開始年月", required = false, position = 61, allowableValues = "range[0,255]")
	private String nAsFlg;

	/**
	 * RSC機種群
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "RSC機種群", required = false, position = 62, allowableValues = "range[0,255]")
	private String nRscModelCategory;

	/**
	 * 経営分類1
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "経営分類1", required = false, position = 63, allowableValues = "range[0,255]")
	private String nManageGroup1;

	/**
	 * 保証形態1
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "保証形態1", required = false, position = 64, allowableValues = "range[0,255]")
	private String nGuaranteeType1;

	/**
	 * 機種群名(カナ)1
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "機種群名(カナ)1", required = false, position = 65, allowableValues = "range[0,255]")
	private String nModeCatDescKana1;

	/**
	 * 保証形態2
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "保証形態2", required = false, position = 66, allowableValues = "range[0,255]")
	private String nGuaranteeType2;

	/**
	 * 機種群名(カナ)2
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "機種群名(カナ)2", required = false, position = 67, allowableValues = "range[0,255]")
	private String nModeCatDescKana2;

	/**
	 * 保証形態3
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "保証形態3", required = false, position = 68, allowableValues = "range[0,255]")
	private String nGuaranteeType3;

	/**
	 * 機種群名(カナ)3
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "機種群名(カナ)3", required = false, position = 69, allowableValues = "range[0,255]")
	private String nModeCatDescKana3;

	/**
	 * 保証形態4
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "保証形態4", required = false, position = 70, allowableValues = "range[0,255]")
	private String nGuaranteeType4;

	/**
	 * 機種群名(カナ)4
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "機種群名(カナ)4", required = false, position = 71, allowableValues = "range[0,255]")
	private String nModeCatDescKana4;

	/**
	 * 保証形態5
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "保証形態5", required = false, position = 72, allowableValues = "range[0,255]")
	private String nGuaranteeType5;

	/**
	 * 機種群名(カナ)5
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "機種群名(カナ)5", required = false, position = 73, allowableValues = "range[0,255]")
	private String nModeCatDescKana5;

	/**
	 * 保証形態6
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "保証形態6", required = false, position = 74, allowableValues = "range[0,255]")
	private String nGuaranteeType6;

	/**
	 * 機種群名(カナ)6
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "機種群名(カナ)6", required = false, position = 75, allowableValues = "range[0,255]")
	private String nModeCatDescKana6;

	/**
	 * 保証形態7
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "保証形態7", required = false, position = 76, allowableValues = "range[0,255]")
	private String nGuaranteeType7;

	/**
	 * 機種群名(カナ)7
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "機種群名(カナ)7", required = false, position = 77, allowableValues = "range[0,255]")
	private String nModeCatDescKana7;

	/**
	 * 保証形態8
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "保証形態8", required = false, position = 78, allowableValues = "range[0,255]")
	private String nGuaranteeType8;

	/**
	 * 機種群名(カナ)8
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "機種群名(カナ)8", required = false, position = 79, allowableValues = "range[0,255]")
	private String nModeCatDescKana8;

	/**
	 * 保証形態9
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "保証形態9", required = false, position = 80, allowableValues = "range[0,255]")
	private String nGuaranteeType9;

	/**
	 * 機種群名(カナ)9
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "機種群名(カナ)9", required = false, position = 81, allowableValues = "range[0,255]")
	private String nModeCatDescKana9;

	/**
	 * 保証形態10
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "保証形態10", required = false, position = 82, allowableValues = "range[0,255]")
	private String nGuaranteeType10;

	/**
	 * 機種群名(カナ)10
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "機種群名(カナ)10", required = false, position = 83, allowableValues = "range[0,255]")
	private String nModeCatDescKana10;

	/**
	 * 可能保証内契約形態
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "可能保証内契約形態", required = false, position = 84, allowableValues = "range[0,255]")
	private String nGuarantContractType;

	/**
	 * ISO対象機区分
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "ISO対象機区分", required = false, position = 85, allowableValues = "range[0,255]")
	private String nIsoTargetType;

	/**
	 * ISO対象設定年月日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "更新年月日", required = false, position = 86)
	private Date nIsoTargetDate;

	/**
	 * 仮機種コード
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "仮機種コード", required = false, position = 87, allowableValues = "range[0,255]")
	private String nTmpModelCode;

	/**
	 * 仮機種群コード
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "仮機種群コード", required = false, position = 88, allowableValues = "range[0,255]")
	private String nTmpModelCategoryCode;

	/**
	 * PA-PMフラグ
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "PA-PMフラグ", required = false, position = 89, allowableValues = "range[0,255]")
	private String nPaPmFlg;

	/**
	 * PA-PM予定日数
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "PA-PM予定日数", required = false, position = 90, allowableValues = "range[0,255]")
	private String nPaPmPlanDays;
}
