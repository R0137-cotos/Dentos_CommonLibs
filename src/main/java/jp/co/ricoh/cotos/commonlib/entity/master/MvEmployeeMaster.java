package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * RJ社員情報マスタ
 */
@Entity
@Data
@Table(name = "mv_employee_master")
public class MvEmployeeMaster {

	/**
	 * MOM社員ID
	 */
	@Id
	@Column(length = 24, name = "emp_id")
	@ApiModelProperty(value = "MOM社員ID", required = true, position = 1, allowableValues = "range[0,24]")
	private String momEmployeeId;

	/**
	 * MOM会社ID
	 */
	@Column(length = 18, name = "corp_id")
	@ApiModelProperty(value = "MOM会社ID", required = false, position = 2, allowableValues = "range[0,18]")
	private String momCompanyId;

	/**
	 * SINGLE_USER_ID
	 */
	@Column(length = 300, name = "singleuser_id")
	@ApiModelProperty(value = "SINGLE_USER_ID", required = false, position = 3, allowableValues = "range[0,300]")
	private String singleUserId;

	/**
	 * 業務用氏名（姓）
	 */
	@Column(length = 300)
	@ApiModelProperty(value = "業務用氏名（姓）", required = false, position = 4, allowableValues = "range[0,300]")
	private String jobname1;

	/**
	 * 業務用氏名（名）
	 */
	@Column(length = 300)
	@ApiModelProperty(value = "業務用氏名（名）", required = false, position = 5, allowableValues = "range[0,300]")
	private String jobname2;

	/**
	 * 業務用氏名（姓カナ）
	 */
	@Column(length = 300)
	@ApiModelProperty(value = "業務用氏名（姓カナ）", required = false, position = 6, allowableValues = "range[0,300]")
	private String kanaJobname1;

	/**
	 * 業務用氏名（名カナ）
	 */
	@Column(length = 300)
	@ApiModelProperty(value = "業務用氏名（名カナ）", required = false, position = 7, allowableValues = "range[0,300]")
	private String kanaJobname2;

	/**
	 * EMAILアドレス
	 */
	@Column(length = 300, name = "email")
	@ApiModelProperty(value = "EMAILアドレス", required = false, position = 8, allowableValues = "range[0,300]")
	private String mailAddress;

	/**
	 * 電話番号
	 */
	@Column(length = 600, name = "tel")
	@ApiModelProperty(value = "電話番号", required = false, position = 9, allowableValues = "range[0,600]")
	private String phoneNumber;

	/**
	 * FAX番号
	 */
	@Column(length = 600, name = "fax")
	@ApiModelProperty(value = "FAX番号", required = false, position = 10, allowableValues = "range[0,600]")
	private String faxNumber;

	/**
	 * CEコード
	 */
	@Column(length = 15)
	@ApiModelProperty(value = "CEコード", required = false, position = 11, allowableValues = "range[0,15]")
	private String ceCd;

	/**
	 * 所属組織_MoM組織ID
	 */
	@Column(length = 21, name = "org_id")
	@ApiModelProperty(value = "所属組織_MoM組織ID", required = false, position = 12, allowableValues = "range[0,21]")
	private String momOrgId;

	/**
	 * 退職区分
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "退職区分", required = false, position = 13, allowableValues = "range[0,3]")
	private String retiredKbn;

	/**
	 * 利用者区分
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "利用者区分", required = false, position = 14, allowableValues = "range[0,3]")
	private String userType;

	/**
	 * RINGS販社コード
	 */
	@Column(length = 9)
	@ApiModelProperty(value = "RINGS販社コード", required = false, position = 15, allowableValues = "range[0,9]")
	private String ringsHanshCd;

	/**
	 * RINGS社員コード
	 */
	@Column(length = 15)
	@ApiModelProperty(value = "RINGS社員コード", required = false, position = 16, allowableValues = "range[0,15]")
	private String ringsEmpCd;

	/**
	 * 社員番号
	 */
	@Column(length = 300, name = "emb_user_number")
	@ApiModelProperty(value = "社員番号", required = false, position = 17, allowableValues = "range[0,300]")
	private String userNumber;

	/**
	 * 業務用上司
	 */
	@Column(length = 24)
	@ApiModelProperty(value = "業務用上司", required = false, position = 18, allowableValues = "range[0,24]")
	private String embJobreaderEmpId;

	/**
	 * 業務センターCD
	 */
	@Column(length = 9)
	@ApiModelProperty(value = "業務センターCD", required = false, position = 19, allowableValues = "range[0,9]")
	private String embOperationCenterCd;

	/**
	 * NotesID
	 */
	@Column(length = 300)
	@ApiModelProperty(value = "NotesID", required = false, position = 20, allowableValues = "range[0,300]")
	private String notesId;

	/**
	 * 登録日時
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "登録日時", required = false, position = 21)
	private Date registerDate;

	/**
	 * 更新日時
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "更新日時", required = false, position = 22)
	private Date updatingDate;

	/**
	 * 登録者ID
	 */
	@Column(length = 24)
	@ApiModelProperty(value = "登録者ID", required = false, position = 23, allowableValues = "range[0,24]")
	private String registerUserId;

	/**
	 * 登録者名
	 */
	@Column(length = 300)
	@ApiModelProperty(value = "登録者名", required = false, position = 24, allowableValues = "range[0,300]")
	private String registerUserName;

	/**
	 * 更新者ID
	 */
	@Column(length = 24)
	@ApiModelProperty(value = "更新者ID", required = false, position = 25, allowableValues = "range[0,24]")
	private String updatingUserId;

	/**
	 * 更新者名
	 */
	@Column(length = 300)
	@ApiModelProperty(value = "更新者名", required = false, position = 26, allowableValues = "range[0,300]")
	private String updatingUserName;

	/**
	 * 販社コード
	 */
	@Column(length = 9)
	@ApiModelProperty(value = "販社コード", required = false, position = 27, allowableValues = "range[0,9]")
	private String hanshCd;

	/**
	 * 従業員番号
	 */
	@Column(length = 300, name = "emb_emp_number")
	@ApiModelProperty(value = "従業員番号", required = false, position = 28, allowableValues = "range[0,300]")
	private String empNumber;

	/**
	 * 専門資格コード
	 */
	@Column(length = 36)
	@ApiModelProperty(value = "専門資格コード", required = false, position = 29, allowableValues = "range[0,36]")
	private String embCompetenceCd;

	/**
	 * 派遣区分コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "派遣区分コード", required = false, position = 30, allowableValues = "range[0,3]")
	private String embVisitKbn;

	/**
	 * 入社年月日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "入社年月日", required = false, position = 31)
	private Date embEnterDate;

	/**
	 * 有効開始日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "有効開始日", required = false, position = 32)
	private Date startDate;

	/**
	 * 有効終了日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "有効終了日", required = false, position = 33)
	private Date endDate;

	/**
	 * MOM組織名称
	 */
	@Column(length = 3000)
	@ApiModelProperty(value = "MOM組織名称", required = false, position = 34, allowableValues = "range[0,3000]")
	private String orgBaseName;

	/**
	 * MOM組織名称（カナ）
	 */
	@Column(length = 3000)
	@ApiModelProperty(value = "MOM組織名称（カナ）", required = false, position = 35, allowableValues = "range[0,3000]")
	private String orgBaseKanaName;

	/**
	 * CUBIC会社コード
	 */
	@Column(length = 720)
	@ApiModelProperty(value = "CUBIC会社コード", required = false, position = 36, allowableValues = "range[0,720]")
	private String cubicCorpId;

	/**
	 * CUBIC部門コード
	 */
	@Column(length = 75)
	@ApiModelProperty(value = "CUBIC部門コード", required = false, position = 37, allowableValues = "range[0,75]")
	private String cubicOrgId;

	/**
	 * RINGS販社コード
	 */
	@Column(length = 9)
	@ApiModelProperty(value = "RINGS販社コード", required = false, position = 38, allowableValues = "range[0,9]")
	private String orgRingsHanshCd;

	/**
	 * RINGS課所コード
	 */
	@Column(length = 12)
	@ApiModelProperty(value = "RINGS課所コード", required = false, position = 39, allowableValues = "range[0,12]")
	private String ringsSectCd;

	/**
	 * 郵便番号
	 */
	@Column(length = 21, name = "post_no")
	@ApiModelProperty(value = "郵便番号", required = false, position = 40, allowableValues = "range[0,21]")
	private String postNumber;

	/**
	 * 住所コード
	 */
	@Column(length = 33)
	@ApiModelProperty(value = "住所コード", required = false, position = 41, allowableValues = "range[0,33]")
	private String addrCd;

	/**
	 * 番地
	 */
	@Column(length = 90)
	@ApiModelProperty(value = "番地", required = false, position = 42, allowableValues = "range[0,90]")
	private String street;

	/**
	 * ビル名
	 */
	@Column(length = 90)
	@ApiModelProperty(value = "ビル名", required = false, position = 43, allowableValues = "range[0,90]")
	private String building;

	/**
	 * 電話番号
	 */
	@Column(length = 54, name = "org_tel")
	@ApiModelProperty(value = "電話番号", required = false, position = 44, allowableValues = "range[0,54]")
	private String orgPhoneNumber;

	/**
	 * FAX番号
	 */
	@Column(length = 54, name = "org_fax")
	@ApiModelProperty(value = "FAX番号", required = false, position = 45, allowableValues = "range[0,54]")
	private String orgFaxNumber;

	/**
	 * 組織長
	 */
	@Column(length = 24)
	@ApiModelProperty(value = "組織長", required = false, position = 46, allowableValues = "range[0,24]")
	private String orgLeaderEmpId;

	/**
	 * 組織区分
	 */
	@Column(length = 6)
	@ApiModelProperty(value = "組織区分", required = false, position = 47, allowableValues = "range[0,6]")
	private String orgKbn;

	/**
	 * 登録ステータス区分
	 */
	@Column(length = 6)
	@ApiModelProperty(value = "登録ステータス区分", required = false, position = 48, allowableValues = "range[0,6]")
	private String registerKbn;

	/**
	 * 組織改編後MOM組織ID
	 */
	@Column(length = 21)
	@ApiModelProperty(value = "組織改編後MOM組織ID", required = false, position = 49, allowableValues = "range[0,21]")
	private String repairOrgId;

	/**
	 * メイン在庫区
	 */
	@Column(length = 21)
	@ApiModelProperty(value = "メイン在庫区", required = false, position = 50, allowableValues = "range[0,21]")
	private String stokOrgId;

	/**
	 * 経費負担組織
	 */
	@Column(length = 21)
	@ApiModelProperty(value = "経費負担組織", required = false, position = 51, allowableValues = "range[0,21]")
	private String experOrgId;

	/**
	 * 原価調整組織
	 */
	@Column(length = 21)
	@ApiModelProperty(value = "原価調整組織", required = false, position = 52, allowableValues = "range[0,21]")
	private String costOrgId;

	/**
	 * MOM設置届先サイトID
	 */
	@Column(length = 45)
	@ApiModelProperty(value = "MOM設置届先サイトID", required = false, position = 53, allowableValues = "range[0,45]")
	private String sendSiteId;

	/**
	 * 預かり在庫区区分
	 */
	@Column(length = 6)
	@ApiModelProperty(value = "預かり在庫区区分", required = false, position = 54, allowableValues = "range[0,6]")
	private String stokKbn;

	/**
	 * 通過在庫区対象フラグ
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "通過在庫区対象フラグ", required = false, position = 55, allowableValues = "range[0,3]")
	private String currencyFlg;

	/**
	 * 管理委託区分
	 */
	@Column(length = 6)
	@ApiModelProperty(value = "管理委託区分", required = false, position = 56, allowableValues = "range[0,6]")
	private String entrustKbn;

	/**
	 * 組織内共有設定フラグ
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "組織内共有設定フラグ", required = false, position = 57, allowableValues = "range[0,3]")
	private String orgComFlg;

	/**
	 * 商流決定フラグ
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "商流決定フラグ", required = false, position = 58, allowableValues = "range[0,3]")
	private String comMovFlg;

	/**
	 * MOM組織ソート順
	 */
	@ApiModelProperty(value = "MOM組織ソート順", required = false, position = 59)
	private Integer momSort;

	/**
	 * NS1組織コード1
	 */
	@Column(length = 60)
	@ApiModelProperty(value = "NS1組織コード1", required = false, position = 60, allowableValues = "range[0,60]")
	private String orgCd1;

	/**
	 * NS1組織コード2
	 */
	@Column(length = 60)
	@ApiModelProperty(value = "NS1組織コード2", required = false, position = 61, allowableValues = "range[0,60]")
	private String orgCd2;

	/**
	 * NS1組織コード3
	 */
	@Column(length = 60)
	@ApiModelProperty(value = "NS1組織コード3", required = false, position = 62, allowableValues = "range[0,60]")
	private String orgCd3;

	/**
	 * NS1組織コード4
	 */
	@Column(length = 60)
	@ApiModelProperty(value = "NS1組織コード4", required = false, position = 63, allowableValues = "range[0,60]")
	private String orgCd4;

	/**
	 * NS1組織コード5
	 */
	@Column(length = 60)
	@ApiModelProperty(value = "NS1組織コード5", required = false, position = 64, allowableValues = "range[0,60]")
	private String orgCd5;

	/**
	 * 適用年月日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "適用年月日", required = false, position = 65)
	private Date aplDate;

	/**
	 * 組織レベル
	 */
	@Column(name = "org_level")
	@ApiModelProperty(value = "組織レベル", required = false, position = 66)
	private Integer orgHierarchyLevel;

	/**
	 * 組織正式名称
	 */
	@Column(length = 3000, name = "org_name")
	@ApiModelProperty(value = "組織正式名称", required = false, position = 67, allowableValues = "range[0,3000]")
	private String orgFullname;

	/**
	 * 組織正式名称（カナ）
	 */
	@Column(length = 3000)
	@ApiModelProperty(value = "組織正式名称（カナ）", required = false, position = 68, allowableValues = "range[0,3000]")
	private String orgKanaName;

	/**
	 * 組織簡略名称
	 */
	@Column(length = 3000, name = "abbr_name")
	@ApiModelProperty(value = "組織簡略名称", required = false, position = 69, allowableValues = "range[0,3000]")
	private String orgName;

	/**
	 * 正式名称1
	 */
	@Column(length = 600)
	@ApiModelProperty(value = "正式名称1", required = false, position = 70, allowableValues = "range[0,600]")
	private String orgName1;

	/**
	 * 正式名称2
	 */
	@Column(length = 600)
	@ApiModelProperty(value = "正式名称2", required = false, position = 71, allowableValues = "range[0,600]")
	private String orgName2;

	/**
	 * 正式名称3
	 */
	@Column(length = 600)
	@ApiModelProperty(value = "正式名称3", required = false, position = 72, allowableValues = "range[0,600]")
	private String orgName3;

	/**
	 * 正式名称4
	 */
	@Column(length = 600)
	@ApiModelProperty(value = "正式名称4", required = false, position = 73, allowableValues = "range[0,600]")
	private String orgName4;

	/**
	 * 正式名称5
	 */
	@Column(length = 600)
	@ApiModelProperty(value = "正式名称5", required = false, position = 74, allowableValues = "range[0,600]")
	private String orgName5;

	/**
	 * 簡略名称1
	 */
	@Column(length = 600)
	@ApiModelProperty(value = "簡略名称1", required = false, position = 75, allowableValues = "range[0,600]")
	private String abbrName1;

	/**
	 * 簡略名称2
	 */
	@Column(length = 600)
	@ApiModelProperty(value = "簡略名称2", required = false, position = 76, allowableValues = "range[0,600]")
	private String abbrName2;

	/**
	 * 簡略名称3
	 */
	@Column(length = 600)
	@ApiModelProperty(value = "簡略名称3", required = false, position = 77, allowableValues = "range[0,600]")
	private String abbrName3;

	/**
	 * 簡略名称4
	 */
	@Column(length = 600)
	@ApiModelProperty(value = "簡略名称4", required = false, position = 78, allowableValues = "range[0,600]")
	private String abbrName4;

	/**
	 * 簡略名称5
	 */
	@Column(length = 600)
	@ApiModelProperty(value = "簡略名称5", required = false, position = 79, allowableValues = "range[0,600]")
	private String abbrName5;

	/**
	 * 経費コード
	 */
	@Column(length = 21)
	@ApiModelProperty(value = "経費コード", required = false, position = 80, allowableValues = "range[0,21]")
	private String experCd;

	/**
	 * 課所名称
	 */
	@Column(length = 60)
	@ApiModelProperty(value = "課所名称", required = false, position = 81, allowableValues = "range[0,60]")
	private String orrRingsOrgName;

	/**
	 * 課所名称（カナ）
	 */
	@Column(length = 45)
	@ApiModelProperty(value = "課所名称（カナ）", required = false, position = 82, allowableValues = "range[0,45]")
	private String orrRingsOrgKanaName;

	/**
	 * 売上発生区分
	 */
	@Column(length = 6)
	@ApiModelProperty(value = "売上発生区分", required = false, position = 83, allowableValues = "range[0,6]")
	private String orrSalesOccurKbn;

	/**
	 * 在庫発生区分
	 */
	@Column(length = 6)
	@ApiModelProperty(value = "在庫発生区分", required = false, position = 84, allowableValues = "range[0,6]")
	private String orrStockOccurKbn;

	/**
	 * 経理発生区分
	 */
	@Column(length = 6)
	@ApiModelProperty(value = "経理発生区分", required = false, position = 85, allowableValues = "range[0,6]")
	private String orrManageOccurKbn;

	/**
	 * 売上集計区分
	 */
	@Column(length = 6)
	@ApiModelProperty(value = "売上集計区分", required = false, position = 86, allowableValues = "range[0,6]")
	private String orrSalesTotalKbn;

	/**
	 * 在庫集計区分
	 */
	@Column(length = 6)
	@ApiModelProperty(value = "在庫集計区分", required = false, position = 87, allowableValues = "range[0,6]")
	private String orrStockTotalKbn;

	/**
	 * 経理集計区分
	 */
	@Column(length = 6)
	@ApiModelProperty(value = "経理集計区分", required = false, position = 88, allowableValues = "range[0,6]")
	private String orrManageTotalKbn;

	/**
	 * 売上集計レベル
	 */
	@Column(length = 30)
	@ApiModelProperty(value = "売上集計レベル", required = false, position = 89, allowableValues = "range[0,30]")
	private String orrSalesTotalLevel;

	/**
	 * 在庫集計レベル
	 */
	@Column(length = 30)
	@ApiModelProperty(value = "在庫集計レベル", required = false, position = 90, allowableValues = "range[0,30]")
	private String orrStockTotalLevel;

	/**
	 * 経理集計レベル
	 */
	@Column(length = 30)
	@ApiModelProperty(value = "経理集計レベル", required = false, position = 91, allowableValues = "range[0,30]")
	private String orrManageTotalLevel;

	/**
	 * 売上集計組織
	 */
	@Column(length = 21)
	@ApiModelProperty(value = "売上集計組織", required = false, position = 92, allowableValues = "range[0,21]")
	private String orrSalesTotalOrg;

	/**
	 * 経理集計組織
	 */
	@Column(length = 21)
	@ApiModelProperty(value = "経理集計組織", required = false, position = 93, allowableValues = "range[0,21]")
	private String orrManageTotalOrg;

	/**
	 * 出納管理組織
	 */
	@Column(length = 21)
	@ApiModelProperty(value = "出納管理組織", required = false, position = 94, allowableValues = "range[0,21]")
	private String orrCbAdmOrg;

	/**
	 * 財源組織
	 */
	@Column(length = 21)
	@ApiModelProperty(value = "財源組織", required = false, position = 95, allowableValues = "range[0,21]")
	private String orrFinancialOrg;

	/**
	 * 登録日時
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "登録日時", required = false, position = 96)
	private Date orgRegisterDate;

	/**
	 * 更新日時
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "更新日時", required = false, position = 97)
	private Date orgUpdatingDate;

	/**
	 * 登録者ID
	 */
	@Column(length = 24)
	@ApiModelProperty(value = "登録者ID", required = false, position = 98, allowableValues = "range[0,24]")
	private String orgRegisterUserId;

	/**
	 * 登録者名
	 */
	@Column(length = 300)
	@ApiModelProperty(value = "登録者名", required = false, position = 99, allowableValues = "range[0,300]")
	private String orgRegisterUserName;

	/**
	 * 更新者ID
	 */
	@Column(length = 24)
	@ApiModelProperty(value = "更新者ID", required = false, position = 100, allowableValues = "range[0,24]")
	private String orgUpdatingUserId;

	/**
	 * 更新者名
	 */
	@Column(length = 300)
	@ApiModelProperty(value = "更新者名", required = false, position = 101, allowableValues = "range[0,300]")
	private String orgUpdatingUserName;

	/**
	 * 住所郵便番号
	 */
	@Column(length = 150)
	@ApiModelProperty(value = "住所郵便番号", required = false, position = 102, allowableValues = "range[0,150]")
	private String adsPostNum;

	/**
	 * 住所都道府県名_漢字
	 */
	@Column(length = 30)
	@ApiModelProperty(value = "住所都道府県名_漢字", required = false, position = 103, allowableValues = "range[0,30]")
	private String tdhknNmKnji;

	/**
	 * 住所市区郡町村名_漢字
	 */
	@Column(length = 78)
	@ApiModelProperty(value = "住所市区郡町村名_漢字", required = false, position = 104, allowableValues = "range[0,78]")
	private String skugnchosnKnji;

	/**
	 * 住所大字・通称名_漢字
	 */
	@Column(length = 114)
	@ApiModelProperty(value = "住所大字・通称名_漢字", required = false, position = 105, allowableValues = "range[0,114]")
	private String owaTusyoKnji;

	/**
	 * 住所字名・丁目_漢字
	 */
	@Column(length = 78)
	@ApiModelProperty(value = "住所字名・丁目_漢字", required = false, position = 106, allowableValues = "range[0,78]")
	private String kowChomeKnji;

	/**
	 * 住所都道府県名_カナ
	 */
	@Column(length = 24)
	@ApiModelProperty(value = "住所都道府県名_カナ", required = false, position = 107, allowableValues = "range[0,24]")
	private String tdhknNmKana;

	/**
	 * 住所市区郡町村名_カナ
	 */
	@Column(length = 72)
	@ApiModelProperty(value = "住所市区郡町村名_カナ", required = false, position = 108, allowableValues = "range[0,72]")
	private String skugnchosnKana;

	/**
	 * 住所大字・通称名_カナ
	 */
	@Column(length = 108)
	@ApiModelProperty(value = "住所大字・通称名_カナ", required = false, position = 109, allowableValues = "range[0,108]")
	private String owaTusyoKana;

	/**
	 * 住所字名・丁目_カナ
	 */
	@Column(length = 72)
	@ApiModelProperty(value = "住所字名・丁目_カナ", required = false, position = 110, allowableValues = "range[0,72]")
	private String kowChomeKana;

	/**
	 * 住所コード大（都道府県）
	 */
	@Column(name = "ads_cd_l", length = 6)
	@ApiModelProperty(value = "住所コード大（都道府県）", required = false, position = 111, allowableValues = "range[0,6]")
	private String adsCdL;

	/**
	 * 住所コード大中（市・群）
	 */
	@Column(length = 15)
	@ApiModelProperty(value = "住所コード大中（市・群）", required = false, position = 112, allowableValues = "range[0,15]")
	private String adsCdLm;

	/**
	 * 住所コード大中小（町・村・大字）
	 */
	@Column(length = 24)
	@ApiModelProperty(value = "住所コード大中小（町・村・大字）", required = false, position = 113, allowableValues = "range[0,24]")
	private String adsCdLms;

	/**
	 * 無効フラグ
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "無効フラグ", required = false, position = 114, allowableValues = "range[0,3]")
	private String dltFlg;

	/**
	 * WEB作成日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "WEB作成日", required = false, position = 115)
	private Date scWebCrtDt;

	/**
	 * WEB作成担当者ユーザID
	 */
	@Column(length = 192)
	@ApiModelProperty(value = "WEB作成担当者ユーザID", required = false, position = 116, allowableValues = "range[0,192]")
	private String scWebCrtUserId;

	/**
	 * WEB作成担当者販社コード
	 */
	@Column(length = 9)
	@ApiModelProperty(value = "WEB作成担当者販社コード", required = false, position = 117, allowableValues = "range[0,9]")
	private String scWebCrtHanshCd;

	/**
	 * WEB作成機能コード
	 */
	@Column(length = 9)
	@ApiModelProperty(value = "WEB作成機能コード", required = false, position = 118, allowableValues = "range[0,9]")
	private String scWebCrtKinoCd;

	/**
	 * WEB更新日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "WEB更新日", required = false, position = 119)
	private Date scWebUpdtDt;

	/**
	 * WEB更新担当者ユーザID
	 */
	@Column(length = 192)
	@ApiModelProperty(value = "WEB更新担当者ユーザID", required = false, position = 120, allowableValues = "range[0,192]")
	private String scWebUpdtUserId;

	/**
	 * WEB更新担当者販社コード
	 */
	@Column(length = 9)
	@ApiModelProperty(value = "WEB更新担当者販社コード", required = false, position = 121, allowableValues = "range[0,9]")
	private String scWebUpdtHanshCd;

	/**
	 * WEB更新機能コード
	 */
	@Column(length = 9)
	@ApiModelProperty(value = "WEB更新機能コード", required = false, position = 122, allowableValues = "range[0,9]")
	private String scWebUpdtKinoCd;

	/**
	 * バッチ作成機能コード
	 */
	@Column(length = 9)
	@ApiModelProperty(value = "バッチ作成機能コード", required = false, position = 123, allowableValues = "range[0,9]")
	private String scBtCrtKinoCd;

	/**
	 * バッチ作成処理見做し日
	 */
	@Column(length = 24)
	@ApiModelProperty(value = "バッチ作成処理見做し日", required = false, position = 124, allowableValues = "range[0,24]")
	private String scBtCrtAsofDt;

	/**
	 * バッチ作成日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "バッチ作成日", required = false, position = 125)
	private Date scBtCrtDt;

	/**
	 * バッチ更新機能コード
	 */
	@Column(length = 9)
	@ApiModelProperty(value = "バッチ更新機能コード", required = false, position = 126, allowableValues = "range[0,9]")
	private String scBtUpdtKinoCd;

	/**
	 * バッチ更新処理見做し日
	 */
	@Column(length = 24)
	@ApiModelProperty(value = "バッチ作成処理見做し日", required = false, position = 127, allowableValues = "range[0,24]")
	private String scBtUpdtAsofDt;

	/**
	 * バッチ更新日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "バッチ更新日", required = false, position = 128)
	private Date scBtUpdtDt;

	/**
	 * システム管理対応日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "システム管理対応日", required = false, position = 129)
	private Date scSysKnrDt;

	/**
	 * システム管理NO
	 */
	@Column(length = 15)
	@ApiModelProperty(value = "システム管理NO", required = false, position = 130, allowableValues = "range[0,15]")
	private String scSysKnrNo;

	/**
	 * MoM最終更新機能コード
	 */
	@Column(length = 9)
	@ApiModelProperty(value = "MoM最終更新機能コード", required = false, position = 131, allowableValues = "range[0,9]")
	private String scMomUpdtKinoCd;

	/**
	 * MoM最終更新日時時刻
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "MoM最終更新日時時刻", required = false, position = 132)
	private Date scMomUpdtDtTm;

	/**
	 * 郵便番号関連１
	 */
	@Column(name = "post_num_info_1", length = 3)
	@ApiModelProperty(value = "郵便番号関連１", required = false, position = 133, allowableValues = "range[0,3]")
	private String postNumInfo1;

	/**
	 * 郵便番号関連２
	 */
	@Column(name = "post_num_info_2", length = 3)
	@ApiModelProperty(value = "郵便番号関連２", required = false, position = 134, allowableValues = "range[0,3]")
	private String postNumInfo2;

	/**
	 * 登録年月
	 */
	@Column(length = 18)
	@ApiModelProperty(value = "登録年月", required = false, position = 135, allowableValues = "range[0,18]")
	private String tourokuYearMs;

	/**
	 * 変更年月
	 */
	@Column(length = 18)
	@ApiModelProperty(value = "変更年月", required = false, position = 136, allowableValues = "range[0,18]")
	private String henkouYearMs;

	/**
	 * 更新区分
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "更新区分", required = false, position = 137, allowableValues = "range[0,3]")
	private String koushinKbn;

	/**
	 * 販社名
	 */
	@Column(length = 120, name = "hansh_nm")
	@ApiModelProperty(value = "販社名", required = false, position = 138, allowableValues = "range[0,120]")
	private String salesCompanyName;

	/**
	 * 販社正式名称
	 */
	@Column(length = 120)
	@ApiModelProperty(value = "販社正式名称", required = false, position = 139, allowableValues = "range[0,120]")
	private String hanshSeiskNm;

	/**
	 * 更新担当者コード
	 */
	@Column(length = 18)
	@ApiModelProperty(value = "更新担当者コード", required = false, position = 140, allowableValues = "range[0,18]")
	private String updtTntoshCd;

	/**
	 * 更新端末コード
	 */
	@Column(length = 45)
	@ApiModelProperty(value = "更新端末コード", required = false, position = 141, allowableValues = "range[0,45]")
	private String updtTermCd;

	/**
	 * 成元システムコード
	 */
	@Column(length = 6)
	@ApiModelProperty(value = "成元システムコード", required = false, position = 142, allowableValues = "range[0,6]")
	private String crtSysCd;

	/**
	 * 更新元システムコード
	 */
	@Column(length = 6)
	@ApiModelProperty(value = "更新元システムコード", required = false, position = 143, allowableValues = "range[0,6]")
	private String updtSysCd;

	/**
	 * 元システム作成日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "元システム作成日", required = false, position = 144)
	private Date motoSysCrtdDt;

	/**
	 * 元システム更新日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "元システム更新日", required = false, position = 145)
	private Date motoSysUpdtDt;

	/**
	 * バッチ処理做し日
	 */
	@Column(length = 24)
	@ApiModelProperty(value = "バッチ処理做し日", required = false, position = 146, allowableValues = "range[0,24]")
	private String btPrcsAsofDt;

	/**
	 * バッチ処理日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "バッチ処理日", required = false, position = 147)
	private Date btPrcsDt;

	/**
	 * 販社種類区分
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "販社種類区分", required = false, position = 148, allowableValues = "range[0,3]")
	private String hanshSyruiKbn;

	/**
	 * 連結会社コード
	 */
	@Column(length = 9)
	@ApiModelProperty(value = "連結会社コード", required = false, position = 149, allowableValues = "range[0,9]")
	private String renktKishCd;

	/**
	 * MOM会社ID
	 */
	@Column(length = 18)
	@ApiModelProperty(value = "MOM会社ID", required = false, position = 150, allowableValues = "range[0,18]")
	private String hanCorpId;

	/**
	 * RINGS連携対象フラグ
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "RINGS連携対象フラグ日", required = false, position = 151, allowableValues = "range[0,3]")
	private String ringsRelateFlg;

	/**
	 * CUIC連携対象フラグ
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "CUIC連携対象フラグ", required = false, position = 152, allowableValues = "range[0,3]")
	private String cuicRelateFlg;

	/**
	 * P対策責任者
	 */
	@Column(length = 24)
	@ApiModelProperty(value = "P対策責任者", required = false, position = 153, allowableValues = "range[0,24]")
	private String directOrdEmpId;

	/**
	 * 日本OSフラグ
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "日本OSフラグ", required = false, position = 154, allowableValues = "range[0,3]")
	private String osFlg;

	/**
	 * V-UP用販社種類区分
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "V-UP用販社種類区分", required = false, position = 155, allowableValues = "range[0,3]")
	private String hanshSyruiKbnVup;

	/**
	 * 商流代表MoM組織ＩＤ
	 */
	@Column(length = 21)
	@ApiModelProperty(value = "商流代表MoM組織ＩＤ", required = false, position = 156, allowableValues = "range[0,21]")
	private String comDelegateOrgId;

	/**
	 * 有効開始日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "有効開始日", required = false, position = 157)
	private Date cubicStartDate;

	/**
	 * 有効終了日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "有効終了日", required = false, position = 158)
	private Date cubicEndDate;

	/**
	 * 部門カテゴリ
	 */
	@Column(length = 450)
	@ApiModelProperty(value = "部門カテゴリ", required = false, position = 159, allowableValues = "range[0,450]")
	private String departmentDivision;

	/**
	 * 部門カテゴリ名
	 */
	@Column(length = 720)
	@ApiModelProperty(value = "部門カテゴリ名", required = false, position = 160, allowableValues = "range[0,720]")
	private String departmentDivisionNm;

	/**
	 * 部門/組織名
	 */
	@Column(length = 720, name = "department_name")
	@ApiModelProperty(value = "部門/組織名", required = false, position = 161, allowableValues = "range[0,720]")
	private String salesDepartmentName;

	/**
	 * 部門カナ名称
	 */
	@Column(length = 720)
	@ApiModelProperty(value = "部門カナ名称", required = false, position = 162, allowableValues = "range[0,720]")
	private String departmentKana;

	/**
	 * 上位組織コード
	 */
	@Column(length = 75)
	@ApiModelProperty(value = "上位組織コード", required = false, position = 163, allowableValues = "range[0,75]")
	private String highOrgCd;

	/**
	 * 上位組織レベル
	 */
	@Column(length = 90)
	@ApiModelProperty(value = "上位組織レベル", required = false, position = 164, allowableValues = "range[0,90]")
	private String highOrgLevel;

	/**
	 * 管理P/L区分
	 */
	@Column(length = 450)
	@ApiModelProperty(value = "管理P/L区分", required = false, position = 165, allowableValues = "range[0,450]")
	private String frontPl;

	/**
	 * 管理P/L区分名
	 */
	@Column(length = 720)
	@ApiModelProperty(value = "管理P/L区分名", required = false, position = 166, allowableValues = "range[0,720]")
	private String frontPlNm;

	/**
	 * 初期値営業軸
	 */
	@Column(length = 75)
	@ApiModelProperty(value = "初期値営業軸", required = false, position = 167, allowableValues = "range[0,75]")
	private String salesMatrix;

	/**
	 * 初期値営業軸名
	 */
	@Column(length = 720)
	@ApiModelProperty(value = "初期値営業軸名", required = false, position = 168, allowableValues = "range[0,750]")
	private String salesMatrixNm;

	/**
	 * 営業軸配賦率グループ
	 */
	@Column(length = 75)
	@ApiModelProperty(value = "営業軸配賦率グループ", required = false, position = 169, allowableValues = "range[0,75]")
	private String salesMatrixGrpCd;

	/**
	 * 初期値商品軸
	 */
	@Column(length = 75)
	@ApiModelProperty(value = "初期値商品軸", required = false, position = 170, allowableValues = "range[0,75]")
	private String commodityMatrix;

	/**
	 * 初期値商品軸名
	 */
	@Column(length = 720)
	@ApiModelProperty(value = "初期値商品軸名", required = false, position = 171, allowableValues = "range[0,720]")
	private String commodityMatrixNm;

	/**
	 * 商品軸配賦率グループ
	 */
	@Column(length = 75)
	@ApiModelProperty(value = "初期値商品軸名", required = false, position = 172, allowableValues = "range[0,75]")
	private String cmdtyMatrixGrpCd;

	/**
	 * 部門機能
	 */
	@Column(length = 450)
	@ApiModelProperty(value = "部門機能", required = false, position = 173, allowableValues = "range[0,450]")
	private String departmentFunction;

	/**
	 * 部門機能名
	 */
	@Column(length = 720)
	@ApiModelProperty(value = "部門機能名", required = false, position = 174, allowableValues = "range[0,720]")
	private String departmentFunctionNm;

	/**
	 * 経理単位
	 */
	@Column(length = 75)
	@ApiModelProperty(value = "経理単位", required = false, position = 175, allowableValues = "range[0,75]")
	private String changeDepartmentCd;

	/**
	 * 経理単位名
	 */
	@Column(length = 720)
	@ApiModelProperty(value = "経理単位名", required = false, position = 176, allowableValues = "range[0,720]")
	private String changeDepartmentNm;

	/**
	 * 事業所・拠点
	 */
	@Column(length = 15)
	@ApiModelProperty(value = "事業所・拠点", required = false, position = 177, allowableValues = "range[0,15]")
	private String businessEstablishment;

	/**
	 * 事業所・拠点名
	 */
	@Column(length = 240)
	@ApiModelProperty(value = "事業所・拠点名", required = false, position = 178, allowableValues = "range[0,240]")
	private String businessEstablishmentNm;

	/**
	 * 在庫評価区分
	 */
	@Column(length = 450)
	@ApiModelProperty(value = "在庫評価区分", required = false, position = 179, allowableValues = "range[0,450]")
	private String inventoryEstimation;

	/**
	 * 在庫評価区分名
	 */
	@Column(length = 720)
	@ApiModelProperty(value = "在庫評価区分名", required = false, position = 180, allowableValues = "range[0,720]")
	private String inventoryEstimationNm;

	/**
	 * CUBIC部門有効開始日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "CUBIC部門有効開始日", required = false, position = 181)
	private Date cubicDateFrom;

	/**
	 * CUBIC部門有効終了日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "CUBIC部門有効終了日", required = false, position = 182)
	private Date cubicDateTo;

	/**
	 * CUBIC部門入力可能終了日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "CUBIC部門入力可能終了日", required = false, position = 183)
	private Date enterableDateTo;

	/**
	 * 有効フラグ
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "有効フラグ", required = false, position = 184, allowableValues = "range[0,3]")
	private String validFlag;

	/**
	 * セキュリティルール
	 */
	@Column(length = 720)
	@ApiModelProperty(value = "セキュリティルール", required = false, position = 185, allowableValues = "range[0,720]")
	private String securityRule;

	/**
	 * リーダー社員NO
	 */
	@Column(length = 720)
	@ApiModelProperty(value = "リーダー社員NO", required = false, position = 186, allowableValues = "range[0,720]")
	private String leader;

	/**
	 * リーダー社員名
	 */
	@Column(length = 720)
	@ApiModelProperty(value = "リーダー社員名", required = false, position = 187, allowableValues = "range[0,720]")
	private String leaderNm;

	/**
	 * CUBIC部門着手年月
	 */
	@Column(length = 720)
	@ApiModelProperty(value = "CUBIC部門着手年月", required = false, position = 188, allowableValues = "range[0,720]")
	private String beginTime;

	/**
	 * CUBIC部門完成予定年月
	 */
	@Column(length = 720)
	@ApiModelProperty(value = "CUBIC部門完成予定年月", required = false, position = 189, allowableValues = "range[0,720]")
	private String completePlanTime;

	/**
	 * CUBIC部門変更完成予定年月
	 */
	@Column(length = 720)
	@ApiModelProperty(value = "CUBIC部門変更完成予定年月", required = false, position = 190, allowableValues = "range[0,720]")
	private String changePlanTime;

	/**
	 * CUBIC部門完了年月
	 */
	@Column(length = 720)
	@ApiModelProperty(value = "CUBIC部門完了年月", required = false, position = 191, allowableValues = "range[0,720]")
	private String completeTime;

	/**
	 * 対応部門コード
	 */
	@Column(length = 720)
	@ApiModelProperty(value = "対応部門コード", required = false, position = 192, allowableValues = "range[0,720]")
	private String objDepartmentCd;

	/**
	 * 初期値各社セグメントコード
	 */
	@Column(length = 75)
	@ApiModelProperty(value = "初期値各社セグメントコード", required = false, position = 193, allowableValues = "range[0,75]")
	private String defaultOriginalSegmentCd;

	/**
	 * 配賦基準グループコード
	 */
	@Column(length = 75)
	@ApiModelProperty(value = "配賦基準グループコード", required = false, position = 194, allowableValues = "range[0,75]")
	private String distStandGroupCd;

	/**
	 * 部門階層レベル
	 */
	@Column(length = 90)
	@ApiModelProperty(value = "部門階層レベル", required = false, position = 195, allowableValues = "range[0,90]")
	private String hierarchyLevel;

	/**
	 * 組織階層_組織階層名称
	 */
	@Column(length = 720)
	@ApiModelProperty(value = "組織階層_組織階層名称", required = false, position = 196, allowableValues = "range[0,720]")
	private String hierarchyName;

	/**
	 * 組織階層_レベル1組織コード
	 */
	@Column(length = 75)
	@ApiModelProperty(value = "組織階層_レベル1組織コード", required = false, position = 197, allowableValues = "range[0,75]")
	private String structureCode1;

	/**
	 * 組織階層_レベル2組織コード
	 */
	@Column(length = 75)
	@ApiModelProperty(value = "組織階層_レベル2組織コード", required = false, position = 198, allowableValues = "range[0,75]")
	private String structureCode2;

	/**
	 * 組織階層_レベル3組織コード
	 */
	@Column(length = 75)
	@ApiModelProperty(value = "組織階層_レベル3組織コード", required = false, position = 199, allowableValues = "range[0,75]")
	private String structureCode3;

	/**
	 * 組織階層_レベル4組織コード
	 */
	@Column(length = 75)
	@ApiModelProperty(value = "組織階層_レベル4組織コード", required = false, position = 200, allowableValues = "range[0,75]")
	private String structureCode4;

	/**
	 * 組織階層_レベル5組織コード
	 */
	@Column(length = 75)
	@ApiModelProperty(value = "組織階層_レベル5組織コード", required = false, position = 201, allowableValues = "range[0,75]")
	private String structureCode5;

	/**
	 * 組織階層_レベル6組織コード
	 */
	@Column(length = 75)
	@ApiModelProperty(value = "組織階層_レベル6組織コード", required = false, position = 202, allowableValues = "range[0,75]")
	private String structureCode6;

	/**
	 * 請求書問合せ先部門名
	 */
	@Column(length = 720)
	@ApiModelProperty(value = "請求書問合せ先部門名", required = false, position = 203, allowableValues = "range[0,720]")
	private String invDepartmentName;

	/**
	 * 請求書問合せ先郵便番号
	 */
	@Column(length = 30)
	@ApiModelProperty(value = "求書問合せ先郵便番号", required = false, position = 204, allowableValues = "range[0,30]")
	private String invZipCode;

	/**
	 * 請求書問合せ先都道府県
	 */
	@Column(length = 75)
	@ApiModelProperty(value = "請求書問合せ先都道府県", required = false, position = 205, allowableValues = "range[0,75]")
	private String invState;

	/**
	 * 請求書問合せ先住所
	 */
	@Column(length = 720)
	@ApiModelProperty(value = "請求書問合せ先住所", required = false, position = 206, allowableValues = "range[0,720]")
	private String invAddress;

	/**
	 * 請求書問合せ先有効フラグ
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "請求書問合せ先有効フラグ", required = false, position = 207, allowableValues = "range[0,3]")
	private String invEnabledFlag;

	/**
	 * 請求書問合せ先電話番号
	 */
	@Column(length = 42)
	@ApiModelProperty(value = "請求書問合せ先電話番号", required = false, position = 208, allowableValues = "range[0,42]")
	private String invPhoneNum;

	/**
	 * 振込先自社銀行口座1銀行番号
	 */
	@Column(length = 90)
	@ApiModelProperty(value = "振込先自社銀行口座1銀行番号", required = false, position = 209, allowableValues = "range[0,90]")
	private String bankNumber1;

	/**
	 * 振込先自社銀行口座1銀行名
	 */
	@Column(length = 180)
	@ApiModelProperty(value = "振込先自社銀行口座1銀行名", required = false, position = 210, allowableValues = "range[0,180]")
	private String bankName1;

	/**
	 * 振込先自社銀行口座1銀行支店番号
	 */
	@Column(length = 75)
	@ApiModelProperty(value = "振込先自社銀行口座1銀行支店番号", required = false, position = 211, allowableValues = "range[0,75]")
	private String bankNum1;

	/**
	 * 振込先自社銀行口座1銀行支店名
	 */
	@Column(length = 180)
	@ApiModelProperty(value = "振込先自社銀行口座1銀行支店名", required = false, position = 212, allowableValues = "range[0,180]")
	private String bankBranchName1;

	/**
	 * 振込先自社銀行口座1口座種別
	 */
	@Column(length = 75)
	@ApiModelProperty(value = "振込先自社銀行口座1口座種別", required = false, position = 213, allowableValues = "range[0,75]")
	private String bankAccountType1;

	/**
	 * 振込先自社銀行口座1口座種別名
	 */
	@Column(length = 95)
	@ApiModelProperty(value = "振込先自社銀行口座1口座種別名", required = false, position = 214, allowableValues = "range[0,95]")
	private String bankAccountNm1;

	/**
	 * 振込先自社銀行口座1通貨コード
	 */
	@Column(length = 45)
	@ApiModelProperty(value = "振込先自社銀行口座1通貨コード", required = false, position = 215, allowableValues = "range[0,45]")
	private String currencyCode1;

	/**
	 * 振込先自社銀行口座1口座番号
	 */
	@Column(length = 90)
	@ApiModelProperty(value = "振込先自社銀行口座1口座番号", required = false, position = 216, allowableValues = "range[0,90]")
	private String bankAccountNum1;

	/**
	 * 振込先自社銀行口座1銀行口座名
	 */
	@Column(length = 240)
	@ApiModelProperty(value = "振込先自社銀行口座1銀行口座名", required = false, position = 217, allowableValues = "range[0,240]")
	private String bankAccountName1;

	/**
	 * 振込先自社銀行口座1銀行口座名カナ
	 */
	@Column(length = 960)
	@ApiModelProperty(value = "振込先自社銀行口座1銀行口座名カナ", required = false, position = 218, allowableValues = "range[0,960]")
	private String bankAccountNameAlt1;

	/**
	 * 振込先自社銀行口座1銀行口座名義人
	 */
	@Column(length = 720)
	@ApiModelProperty(value = "振込先自社銀行口座1銀行口座名義人", required = false, position = 219, allowableValues = "range[0,720]")
	private String accountHolderName1;

	/**
	 * 振込先自社銀行口座1銀行口座名義人カナ
	 */
	@Column(length = 450)
	@ApiModelProperty(value = "振込先自社銀行口座1銀行口座名義人カナ", required = false, position = 220, allowableValues = "range[0,450]")
	private String accountHolderNameAlt1;

	/**
	 * 振込先自社銀行口座2銀行番号
	 */
	@Column(length = 90)
	@ApiModelProperty(value = "振込先自社銀行口座2銀行番号", required = false, position = 221, allowableValues = "range[0,90]")
	private String bankNumber2;

	/**
	 * 振込先自社銀行口座2銀行名
	 */
	@Column(length = 180)
	@ApiModelProperty(value = "振込先自社銀行口座2銀行名", required = false, position = 222, allowableValues = "range[0,180]")
	private String bankName2;

	/**
	 * 振込先自社銀行口座2銀行支店番号
	 */
	@Column(length = 75)
	@ApiModelProperty(value = "振込先自社銀行口座2銀行支店番号", required = false, position = 223, allowableValues = "range[0,75]")
	private String bankNum2;

	/**
	 * 振込先自社銀行口座2銀行支店名
	 */
	@Column(length = 180)
	@ApiModelProperty(value = "振込先自社銀行口座2銀行支店名", required = false, position = 224, allowableValues = "range[0,180]")
	private String bankBranchName2;

	/**
	 * 振込先自社銀行口座2口座種別
	 */
	@Column(length = 75)
	@ApiModelProperty(value = "振込先自社銀行口座2口座種別", required = false, position = 225, allowableValues = "range[0,75]")
	private String bankAccountType2;

	/**
	 * 振込先自社銀行口座2口座種別名
	 */
	@Column(length = 96)
	@ApiModelProperty(value = "振込先自社銀行口座2口座種別名", required = false, position = 226, allowableValues = "range[0,96]")
	private String bankAccountNm2;

	/**
	 * 振込先自社銀行口座2通貨コード
	 */
	@Column(length = 45)
	@ApiModelProperty(value = "振込先自社銀行口座2通貨コード", required = false, position = 227, allowableValues = "range[0,45]")
	private String currencyCode2;

	/**
	 * 振込先自社銀行口座2口座番号
	 */
	@Column(length = 90)
	@ApiModelProperty(value = "振込先自社銀行口座2口座番号", required = false, position = 228, allowableValues = "range[0,90]")
	private String bankAccountNum2;

	/**
	 * 振込先自社銀行口座2銀行口座名
	 */
	@Column(length = 240)
	@ApiModelProperty(value = "振込先自社銀行口座2銀行口座名", required = false, position = 229, allowableValues = "range[0,240]")
	private String bankAccountName2;

	/**
	 * 振込先自社銀行口座2銀行口座名カナ
	 */
	@Column(length = 960)
	@ApiModelProperty(value = "振込先自社銀行口座2銀行口座名カナ", required = false, position = 230, allowableValues = "range[0,960]")
	private String bankAccountNameAlt2;

	/**
	 * 振込先自社銀行口座2銀行口座名義人
	 */
	@Column(length = 720)
	@ApiModelProperty(value = "振込先自社銀行口座2銀行口座名義人", required = false, position = 231, allowableValues = "range[0,720]")
	private String accountHolderName2;

	/**
	 * 振込先自社銀行口座2銀行口座名義人カナ
	 */
	@Column(length = 450)
	@ApiModelProperty(value = "振込先自社銀行口座2銀行口座名義人カナ", required = false, position = 232, allowableValues = "range[0,450]")
	private String accountHolderNameAlt2;

	/**
	 * 第1請求書コメント有効開始日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "第1請求書コメント有効開始日", required = false, position = 233)
	private Date firstInvDateFrom;

	/**
	 * 第1請求書コメント有効終了日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "第1請求書コメント有効終了日", required = false, position = 234)
	private Date firstInvDateTo;

	/**
	 * 第1請求書コメント1
	 */
	@Column(length = 720)
	@ApiModelProperty(value = "第1請求書コメント1", required = false, position = 235, allowableValues = "range[0,720]")
	private String firstInvComment1;

	/**
	 * 第1請求書コメント2
	 */
	@Column(length = 720)
	@ApiModelProperty(value = "第1請求書コメント2", required = false, position = 236, allowableValues = "range[0,720]")
	private String firstInvComment2;

	/**
	 * 第1請求書コメント3
	 */
	@Column(length = 720)
	@ApiModelProperty(value = "第1請求書コメント3", required = false, position = 237, allowableValues = "range[0,720]")
	private String firstInvComment3;

	/**
	 * 第2請求書コメント有効開始日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "第2請求書コメント有効開始日", required = false, position = 238)
	private Date secondInvDateFrom;

	/**
	 * 第2請求書コメント有効終了日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "第2請求書コメント有効終了日", required = false, position = 239)
	private Date secondInvDateTo;

	/**
	 * 第2請求書コメント1
	 */
	@Column(length = 720)
	@ApiModelProperty(value = "第2請求書コメント1", required = false, position = 240, allowableValues = "range[0,720]")
	private String secondInvComment1;

	/**
	 * 第2請求書コメント2
	 */
	@Column(length = 720)
	@ApiModelProperty(value = "第2請求書コメント2", required = false, position = 241, allowableValues = "range[0,720]")
	private String secondInvComment2;

	/**
	 * 第2請求書コメント3
	 */
	@Column(length = 720)
	@ApiModelProperty(value = "第2請求書コメント3", required = false, position = 242, allowableValues = "range[0,720]")
	private String secondInvComment3;

	/**
	 * ファイブリッジキャビネットID
	 */
	@Column(length = 96)
	@ApiModelProperty(value = "ファイブリッジキャビネットID", required = false, position = 243, allowableValues = "range[0,96]")
	private String fbDeptDistId;

	/**
	 * 登録日時
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "登録日時", required = false, position = 244)
	private Date cubicRegisterDate;

	/**
	 * 更新日時
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "更新日時", required = false, position = 245)
	private Date cubicUpdatingDate;

	/**
	 * 登録者ID
	 */
	@Column(length = 24)
	@ApiModelProperty(value = "登録者ID", required = false, position = 246, allowableValues = "range[0,24]")
	private String cubicRegisterUserId;

	/**
	 * 登録者名
	 */
	@Column(length = 300)
	@ApiModelProperty(value = "登録者名", required = false, position = 247, allowableValues = "range[0,300]")
	private String cubicRegisterUserName;

	/**
	 * 更新者ID
	 */
	@Column(length = 24)
	@ApiModelProperty(value = "登録者ID", required = false, position = 248, allowableValues = "range[0,24]")
	private String cubicUpdatingUserId;

	/**
	 * 更新者名
	 */
	@Column(length = 300)
	@ApiModelProperty(value = "更新者名", required = false, position = 249, allowableValues = "range[0,300]")
	private String cubicUpdatingUserName;
}
