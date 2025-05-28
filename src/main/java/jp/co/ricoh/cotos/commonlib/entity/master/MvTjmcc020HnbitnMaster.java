package jp.co.ricoh.cotos.commonlib.entity.master;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import lombok.Data;

/**
 * MoM販社別販売店
 */
@Entity
@Data
@Table(name = "mv_tjmcc020_hnbitn")
public class MvTjmcc020HnbitnMaster {

	@Embeddable
	@Data
	public static class Id implements Serializable {
		/**
		 * シリアルバージョンID
		 */
		private static final long serialVersionUID = 1L;

		/**MoM企業ID*/
		private String momKgyId;

		/**販社コード*/
		private String hanshCd;

		/**仕切年度*/
		private String nendo;

	}

	@EmbeddedId
	private Id id;

	/**管轄母店MoM企業ID*/
	private String botnMomKgyId;

	/**取引ランクコード*/
	private String trhkRnkCd;

	/**販売店区分*/
	private String hnbitnKbn;

	/**上乗せ率*/
	private BigDecimal uwnsRt;

	/**無効フラグ*/
	private String dltFlg;

	/**WEB作成日*/
	@Temporal(TemporalType.DATE)
	private Date scWebCrtDt;

	/**WEB作成担当者ユーザID*/
	private String scWebCrtUserId;

	/**WEB作成担当者販社コード*/
	private String scWebCrtHanshCd;

	/**WEB作成機能コード*/
	private String scWebCrtKinoCd;

	/**WEB更新日*/
	@Temporal(TemporalType.DATE)
	private Date scWebUpdtDt;

	/**WEB更新担当者ユーザID*/
	private String scWebUpdtUserId;

	/**WEB更新担当者販社コード*/
	private String scWebUpdtHanshCd;

	/**WEB更新機能コード*/
	private String scWebUpdtKinoCd;

	/**バッチ作成機能コード*/
	private String scBtCrtKinoCd;

	/**バッチ作成処理見做し日*/
	private String scBtCrtAsofDt;

	/**バッチ作成日*/
	@Temporal(TemporalType.DATE)
	private Date scBtCrtDt;

	/**バッチ更新機能コード*/
	private String scBtUpdtKinoCd;

	/**バッチ更新処理見做し日*/
	private String scBtUpdtAsofDt;

	/**バッチ更新日*/
	@Temporal(TemporalType.DATE)
	private Date scBtUpdtDt;

	/**システム管理対応日*/
	@Temporal(TemporalType.DATE)
	private Date scSysKnrDt;

	/**システム管理NO*/
	private String scSysKnrNo;

	/**MoM最終更新機能コード*/
	private String scMomUpdtKinoCd;

	/**MoM最終更新日時時刻*/
	@Temporal(TemporalType.TIMESTAMP)
	private Date scMomUpdtDtTm;

	/**特価値引区分*/
	private String tokkNbkKbn;
}