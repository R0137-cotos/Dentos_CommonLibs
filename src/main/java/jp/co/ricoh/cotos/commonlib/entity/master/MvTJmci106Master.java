package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ICI事業所情報マスタ
 */
@Entity
@Data
@Table(name = "mv_t_jmci106")
public class MvTJmci106Master {

	/** MoM事業所ID */
	@Id
	@ApiModelProperty(value = "MoM事業所ID", required = true, position = 1)
	private String businessPlaceId;

	/** 事業所名_漢字 */
	@ApiModelProperty(value = "事業所名_漢字", required = false, position = 2)
	private String estmtName;

	/** 事業所名_カナ */
	@ApiModelProperty(value = "事業所名_カナ", required = false, position = 3)
	private String estmtPhonetic;

	/** 名寄せ用_事業所名_漢字 */
	@ApiModelProperty(value = "名寄せ用_事業所名_漢字", required = false, position = 4)
	private String estmtNameFormal;

	/** 名寄せ用_事業所名_カナ */
	@ApiModelProperty(value = "名寄せ用_事業所名_カナ", required = false, position = 5)
	private String estmtPhoneticFormal;

	/** 事業所有効フラグ */
	@ApiModelProperty(value = "事業所有効フラグ", required = false, position = 6)
	private String businessEnabledFlag;

	/** ICI更新日時 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "ICI更新日時", required = false, position = 7)
	private Date iciUpdtDt;

	/** MoM最終更新日時時刻 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "MoM最終更新日時時刻", required = false, position = 8)
	private Date scMomUpdtDtTm;
}
