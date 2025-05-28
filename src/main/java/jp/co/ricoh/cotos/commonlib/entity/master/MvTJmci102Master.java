package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import lombok.Data;

/**
 * MOM請求売上先銀行口座情報
 */
@Entity
@Data
@Table(name = "mv_t_jmci102")
public class MvTJmci102Master {

	/** サイト番号 */
	@Id
	private String customerSiteNumber;

	/** 銀行口座ID */
	private String bankAccountId;

	/** 自振口座_口座番号 */
	private String bankAccountNum;

	/** 自振口座_通貨 */
	private String bankCurrencyCode;

	/** 自振口座_主 */
	private String currencyPrimaryFlag;

	/** 自振口座_有効日（自） */
	@Temporal(TemporalType.DATE)
	private Date currencyStartDate;

	/** 自振口座_有効日（至） */
	@Temporal(TemporalType.DATE)
	private Date currencyEndDate;

	/** 自振口座_銀行番号 */
	private String bankNumber;

	/** 自振口座_銀行名 */
	private String bankName;

	/** 自振口座_支店番号 */
	private String bankNum;

	/** 自振口座_支店名 */
	private String bankBranchName;

	/** 自振口座_口座種別 */
	private String bankAccountType;

	/** 自振口座_口座名義人カナ名 */
	private String accountHolderNameAlt;

	/** ICI更新日時 */
	@Temporal(TemporalType.DATE)
	private Date iciUpdtDt;

	/** MoM最終更新日時時刻 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date scMomUpdtDtTm;

}
