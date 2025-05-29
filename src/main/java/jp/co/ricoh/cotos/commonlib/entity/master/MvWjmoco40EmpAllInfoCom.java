package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import lombok.Data;

@Entity
@Data
@Table(name = "mv_wjmoc040_emp_all_info_com")
public class MvWjmoco40EmpAllInfoCom {

	/** Mom社員ID */
	@Id
	private String empId;

	/** Mom会社ID */
	private String corpId;

	/** SingleUserId */
	private String singleuserId;

	/** 業務用指名(姓) */
	private String jobname1;

	/** 業務用指名(名) */
	private String jobname2;

	/** 業務用指名(姓カナ) */
	private String kanaJobname1;

	/** 業務用指名(名カナ) */
	private String kanaJobname2;

	/** Eメールアドレス */
	private String email;

	/** 電話番号 */
	private String tel;

	/** FAX番号 */
	private String fax;

	/** CEコード */
	private String ceCd;

	/** 所属組織_MoM組織ID */
	private String orgId;

	/** 退職区分 */
	private String retiredKbn;

	/** 利用者区分 */
	private String userType;

	private String ringsHanshCd;

	/** RINGS社員コード */
	private String ringsEmpCd;

	/** 社員番号 */
	private String embUserNumber;

	/** 業務用上司 */
	private String embJobreaderEmpId;

	/** 業務センターCD */
	private String embOperationCenterCd;

	/** Note ID */
	private String notesId;

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

	/** 販社コード */
	private String hanshCd;

	/** 従業員番号 */
	private String embEmpNumber;

	/** 専門資格コード */
	private String embCompetenceCd;

	/** 派遣区分コード */
	private String embVisitKbn;

	/** 入社年月日 */
	@Temporal(TemporalType.DATE)
	private Date embEnterDate;

}
