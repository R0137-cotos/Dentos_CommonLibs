package jp.co.ricoh.cotos.commonlib.common.master;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Data
@Table(name = "mv_t_jmci105")
public class MvTJmci105Master {

	/** 顧客番号 */
	@Id
	private String customerNumber;

	/** MOM企業ID */
	private String sortCompanyId;

	/** 顧客タイプ */
	private String cstTyp;

	/** 顧客有効フラグ */
	private String customerEnabledFlag;

	/** 企業名_漢字 */
	private String enterpriseName;

	/** 企業名_ｶﾅ */
	private String enterprisePhonetic;

	/** 名寄せ用_企業名_漢字 */
	private String enterpriseNameFormal;

	/** 名寄せ用_企業名_ｶﾅ */
	private String enterprisePhoneticFormal;

	/** 法人格付企業名_漢字 */
	private String cstKnjiNm;

	/** 法人格付企業名_ｶﾅ */
	private String cstKanaNm;

	/** 関係会社区分 */
	private String kanCorpKb;

	/** 関係会社コード */
	private String kanCorpCd;

	/** 系列会社区分 */
	private String affiliatedCompanyCode;

	/** 連結会社区分 */
	private String connectCompanyCls;

	/** ICI更新日時 */
	@Temporal(TemporalType.DATE)
	private Date iciUpdtDt;

	/** MoM最終更新日時時刻 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date scMomUpdtDtTm;
}