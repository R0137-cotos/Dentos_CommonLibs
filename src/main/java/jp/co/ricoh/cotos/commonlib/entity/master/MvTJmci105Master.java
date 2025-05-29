package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ICI顧客情報マスタ
 */
@Entity
@Data
@Table(name = "mv_t_jmci105")
public class MvTJmci105Master {

	/** 顧客番号 */
	@Id
	@ApiModelProperty(value = "顧客番号", required = true, position = 1)
	private String customerNumber;

	/** MOM企業ID */
	@ApiModelProperty(value = "MOM企業ID", required = false, position = 2)
	private String sortCompanyId;

	/** 顧客タイプ */
	@ApiModelProperty(value = "顧客タイプ", required = false, position = 3)
	private String cstTyp;

	/** 顧客有効フラグ */
	@ApiModelProperty(value = "顧客有効フラグ", required = false, position = 4)
	private String customerEnabledFlag;

	/** 企業名_漢字 */
	@ApiModelProperty(value = "企業名_漢字", required = false, position = 5)
	private String enterpriseName;

	/** 企業名_ｶﾅ */
	@ApiModelProperty(value = "企業名_ｶﾅ", required = false, position = 6)
	private String enterprisePhonetic;

	/** 名寄せ用_企業名_漢字 */
	@ApiModelProperty(value = "名寄せ用_企業名_漢字", required = false, position = 7)
	private String enterpriseNameFormal;

	/** 名寄せ用_企業名_ｶﾅ */
	@ApiModelProperty(value = "名寄せ用_企業名_ｶﾅ", required = false, position = 8)
	private String enterprisePhoneticFormal;

	/** 法人格付企業名_漢字 */
	@ApiModelProperty(value = "法人格付企業名_漢字", required = false, position = 9)
	private String cstKnjiNm;

	/** 法人格付企業名_ｶﾅ */
	@ApiModelProperty(value = "法人格付企業名_ｶﾅ", required = false, position = 10)
	private String cstKanaNm;

	/** 関係会社区分 */
	@ApiModelProperty(value = "関係会社区分", required = false, position = 11)
	private String kanCorpKb;

	/** 関係会社コード */
	@ApiModelProperty(value = "関係会社コード", required = false, position = 12)
	private String kanCorpCd;

	/** 系列会社区分 */
	@ApiModelProperty(value = "系列会社区分", required = false, position = 13)
	private String affiliatedCompanyCode;

	/** 連結会社区分 */
	@ApiModelProperty(value = "連結会社区分", required = false, position = 14)
	private String connectCompanyCls;

	/** ICI更新日時 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "ICI更新日時", required = false, position = 15)
	private Date iciUpdtDt;

	/** MoM最終更新日時時刻 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "MoM最終更新日時時刻", required = false, position = 16)
	private Date scMomUpdtDtTm;
}