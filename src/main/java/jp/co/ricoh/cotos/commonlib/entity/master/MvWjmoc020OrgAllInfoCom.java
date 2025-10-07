package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import lombok.Data;

/**
 * MoM組織情報提供マスタ
 */
@Entity
@Data
@Table(name = "mv_wjmoc020_org_all_info_com")
public class MvWjmoc020OrgAllInfoCom {

	/** MOM組織ID */
	@Id
	private String orgId;

	/** 有効開始日 */
	@Temporal(TemporalType.DATE)
	private Date startDate;

	/** 有効終了日 */
	@Temporal(TemporalType.DATE)
	private Date endDate;

	/** MOM会社ID */
	private String corpId;

	/** MOM組織名称 */
	private String orgBaseName;

	/** MOM組織名称（カナ） */
	private String orgBaseKanaName;

	/** CUBIC会社コード */
	private String cubicCorpId;

	/** CUBIC部門コード */
	private String cubicOrgId;

	/** RINGS販社コード */
	private String ringsHanshCd;

	/** RINGS課所コード */
	private String ringsSectCd;

	/** 郵便番号 */
	private String postNo;

	/** 住所コード */
	private String addrCd;

	/** 番地 */
	private String street;

	/** ビル名 */
	private String building;

	/** 電話番号 */
	private String tel;

	/** FAX番号 */
	private String fax;

	/** 組織長 */
	private String orgLeaderEmpId;

	/** 組織区分 */
	private String orgKbn;

	/** 登録ステータス区分 */
	private String registerKbn;

	/** 組織改編後MOM組織ID */
	private String repairOrgId;

	/** メイン在庫区 */
	private String stokOrgId;

	/** 経費負担組織 */
	private String experOrgId;

	/** 原価調整組織 */
	private String costOrgId;

	/** MOM設置届先サイトID */
	private String sendSiteId;

	/** 預かり在庫区区分 */
	private String stokKbn;

	/** 通過在庫区対象フラグ */
	private String currencyFlg;

	/** 管理委託区分 */
	private String entrustKbn;

	/** 組織内共有設定フラグ */
	private String orgComFlg;

	/** 商流決定フラグ */
	private String comMovFlg;

	/** MOM組織ソート順 */
	private Integer momSort;

	/** NS1組織コード1 */
	private String orgCd1;

	/** NS1組織コード2 */
	private String orgCd2;

	/** NS1組織コード3 */
	private String orgCd3;

	/** NS1組織コード4 */
	private String orgCd4;

	/** NS1組織コード5 */
	private String orgCd5;

	/** 適用年月日 */
	@Temporal(TemporalType.DATE)
	private Date aplDate;

	/** 組織レベル */
	private Integer orgLevel;

	/** 組織正式名称 */
	private String orgName;

	/** 組織正式名称（カナ） */
	private String orgKanaName;

	/** 組織簡略名称 */
	private String abbrName;

	/** 正式名称1 */
	private String orgName1;

	/** 正式名称2 */
	private String orgName2;

	/** 正式名称3 */
	private String orgName3;

	/** 正式名称4 */
	private String orgName4;

	/** 正式名称5 */
	private String orgName5;

	/** 簡略名称1 */
	private String abbrName1;

	/** 簡略名称2 */
	private String abbrName2;

	/** 簡略名称3 */
	private String abbrName3;

	/** 簡略名称4 */
	private String abbrName4;

	/** 簡略名称5 */
	private String abbrName5;

	/** 経費コード */
	private String experCd;

	/** 課所名称 */
	private String orrRingsOrgName;

	/** 課所名称（カナ） */
	private String orrRingsOrgKanaName;

	/** 売上発生区分 */
	private String orrSalesOccurKbn;

	/** 在庫発生区分 */
	private String orrStockOccurKbn;

	/** 経理発生区分 */
	private String orrManageOccurKbn;

	/** 売上集計区分 */
	private String orrSalesTotalKbn;

	/** 在庫集計区分 */
	private String orrStockTotalKbn;

	/** 経理集計区分 */
	private String orrManageTotalKbn;

	/** 売上集計レベル */
	private String orrSalesTotalLevel;

	/** 在庫集計レベル */
	private String orrStockTotalLevel;

	/** 経理集計レベル */
	private String orrManageTotalLevel;

	/** 売上集計組織 */
	private String orrSalesTotalOrg;

	/** 経理集計組織 */
	private String orrManageTotalOrg;

	/** 出納管理組織 */
	private String orrCbAdmOrg;

	/** 財源組織 */
	private String orrFinancialOrg;

	/** 登録日時 */
	@Temporal(TemporalType.DATE)
	private Date registerDate;

	/** 更新日時 */
	@Temporal(TemporalType.DATE)
	private Date updatingDate;

	/** 登録者ID */
	private String registerUserId;

	/** 登録者名 */
	private String registerUserName;

	/** 更新者ID */
	private String updatingUserId;

	/** 更新者名 */
	private String updatingUserName;
}
