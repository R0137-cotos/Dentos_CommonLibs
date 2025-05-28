package jp.co.ricoh.cotos.commonlib.entity.master;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import lombok.Data;

/**
 * CUBIC部門関連情報報提供テーブル
 */
@Entity
@Data
@Table(name = "mv_wjmoc030_cubic_org_info_com")
public class MvWjmoc030CubicOrgInfoCom {

	@Embeddable
	@Data
	public static class Id implements Serializable {

		/**
		 * シリアルバージョンID
		 */
		private static final long serialVersionUID = 1L;

		/** CUBIC会社コード. */
		private String cubicCorpId;

		/** CUBIC部門コード. */
		private String cubicOrgId;
	}

	@EmbeddedId
	private Id id;

	/** 有効開始日. */
	@Temporal(TemporalType.DATE)
	private Date startDate;

	/** 有効終了日. */
	@Temporal(TemporalType.DATE)
	private Date endDate;

	/** 部門カテゴリ. */
	private String departmentDivision;

	/** 部門カテゴリ名. */
	private String departmentDivisionNm;

	/** 部門/組織名. */
	private String departmentName;

	/** 部門カナ名称. */
	private String departmentKana;

	/** 上位組織コード. */
	private String highOrgCd;

	/** 上位組織レベル. */
	private String highOrgLevel;

	/** 管理P/L区分. */
	private String frontPl;

	/** 管理P/L区分名. */
	private String frontPlNm;

	/** 初期値営業軸. */
	private String salesMatrix;

	/** 初期値営業軸名. */
	private String salesMatrixNm;

	/** 営業軸配賦率グループ. */
	private String salesMatrixGrpCd;

	/** 初期値商品軸. */
	private String commodityMatrix;

	/** 初期値商品軸名. */
	private String commodityMatrixNm;

	/** 商品軸配賦率グループ. */
	private String cmdtyMatrixGrpCd;

	/** 部門機能. */
	private String departmentFunction;

	/** 部門機能名. */
	private String departmentFunctionNm;

	/** 経理単位. */
	private String changeDepartmentCd;

	/** 経理単位名. */
	private String changeDepartmentNm;

	/** 事業所・拠点. */
	private String businessEstablishment;

	/** 事業所・拠点,名. */
	private String businessEstablishmentNm;

	/** 在庫評価区分. */
	private String inventoryEstimation;

	/** 在庫評価区分名. */
	private String inventoryEstimationNm;

	/** CUBIC部門有効開始日. */
	@Temporal(TemporalType.DATE)
	private Date cubicDateFrom;

	/** CUBIC部門有効終了日. */
	@Temporal(TemporalType.DATE)
	private Date cubicDateTo;

	/** CUBIC部門入力可能終了日. */
	@Temporal(TemporalType.DATE)
	private Date enterableDateTo;

	/** 有効フラグ. */
	private String validFlag;

	/** セキュリティルール. */
	private String securityRule;

	/** リーダー社員NO. */
	private String leader;

	/** リーダー社員名. */
	private String leaderNm;

	/** CUBIC部門着手年月. */
	private String beginTime;

	/** CUBIC部門完成予定年月. */
	private String completePlanTime;

	/** CUBIC部門変更完成予定年月. */
	private String changePlanTime;

	/** CUBIC部門完了年月. */
	private String completeTime;

	/** 対応部門コード. */
	private String objDepartmentCd;

	/** 初期値各社セグメントコード. */
	private String defaultOriginalSegmentCd;

	/** 配賦基準グループコード. */
	private String distStandGroupCd;

	/** 部門階層レベル. */
	private String hierarchyLevel;

	/** 組織階層_組織階層名称. */
	private String hierarchyName;

	/** 組織階層_レベル1組織コード. */
	private String structureCode1;

	/** 組織階層_レベル2組織コード. */
	private String structureCode2;

	/** 組織階層_レベル3組織コード. */
	private String structureCode3;

	/** 組織階層_レベル4組織コード. */
	private String structureCode4;

	/** 組織階層_レベル5組織コード. */
	private String structureCode5;

	/** 組織階層_レベル6組織コード. */
	private String structureCode6;

	/** 請求書問合せ先部門名. */
	private String invDepartmentName;

	/** 請求書問合せ先郵便番号. */
	private String invZipCode;

	/** 請求書問合せ先都道府県. */
	private String invState;

	/** 請求書問合せ先住所. */
	private String invAddress;

	/** 請求書問合せ先有効フラグ. */
	private String invEnabledFlag;

	/** 請求書問合せ先電話番号. */
	private String invPhoneNum;

	/** 振込先自社銀行口座1銀行番号. */
	private String bankNumber1;

	/** 振込先自社銀行口座1銀行名. */
	private String bankName1;

	/** 振込先自社銀行口座1銀行支店番号. */
	private String bankNum1;

	/** 振込先自社銀行口座1銀行支店名. */
	private String bankBranchName1;

	/** 振込先自社銀行口座1口座種別. */
	private String bankAccountType1;

	/** 振込先自社銀行口座1口座種別名. */
	private String bankAccountNm1;

	/** 振込先自社銀行口座1通貨コード. */
	private String currencyCode1;

	/** 振込先自社銀行口座1口座番号. */
	private String bankAccountNum1;

	/** 振込先自社銀行口座1銀行口座名. */
	private String bankAccountName1;

	/** 振込先自社銀行口座1銀行口座名カナ. */
	private String bankAccountNameAlt1;

	/** 振込先自社銀行口座1銀行口座名義人. */
	private String accountHolderName1;

	/** 振込先自社銀行口座1銀行口座名義人カナ. */
	private String accountHolderNameAlt1;

	/** 振込先自社銀行口座2銀行番号. */
	private String bankNumber2;

	/** 振込先自社銀行口座2銀行名. */
	private String bankName2;

	/** 振込先自社銀行口座2銀行支店番号. */
	private String bankNum2;

	/** 振込先自社銀行口座2銀行支店名. */
	private String bankBranchName2;

	/** 振込先自社銀行口座2口座種別. */
	private String bankAccountType2;

	/** 振込先自社銀行口座2口座種別名. */
	private String bankAccountNm2;

	/** 振込先自社銀行口座2通貨コード. */
	private String currencyCode2;

	/** 振込先自社銀行口座2口座番号. */
	private String bankAccountNum2;

	/** 振込先自社銀行口座2銀行口座名. */
	private String bankAccountName2;

	/** 振込先自社銀行口座2銀行口座名カナ. */
	private String bankAccountNameAlt2;

	/** 振込先自社銀行口座2銀行口座名義人. */
	private String accountHolderName2;

	/** 振込先自社銀行口座2銀行口座名義人カナ. */
	private String accountHolderNameAlt2;

	/** 第1請求書コメント有効開始日. */
	@Temporal(TemporalType.DATE)
	private Date firstInvDateFrom;

	/** 第1請求書コメント有効終了日. */
	@Temporal(TemporalType.DATE)
	private Date firstInvDateTo;

	/** 第1請求書コメント1. */
	private String firstInvComment1;

	/** 第1請求書コメント2. */
	private String firstInvComment2;

	/** 第1請求書コメント3. */
	private String firstInvComment3;

	/** 第2請求書コメント有効開始日. */
	@Temporal(TemporalType.DATE)
	private Date secondInvDateFrom;

	/** 第2請求書コメント有効終了日. */
	@Temporal(TemporalType.DATE)
	private Date secondInvDateTo;

	/** 第2請求書コメント1. */
	private String secondInvComment1;

	/** 第2請求書コメント2. */
	private String secondInvComment2;

	/** 第2請求書コメント3. */
	private String secondInvComment3;

	/** ファイブリッジキャビネットID. */
	private String fbDeptDistId;

	/** 登録日時. */
	@Temporal(TemporalType.DATE)
	private Date registerDate;

	/** 更新日時. */
	@Temporal(TemporalType.DATE)
	private Date updatingDate;

	/** 登録者ID. */
	private String registerUserId;

	/** 登録者名. */
	private String registerUserName;

	/** 更新者ID. */
	private String updatingUserId;

	/** 更新者名. */
	private String updatingUserName;

}
