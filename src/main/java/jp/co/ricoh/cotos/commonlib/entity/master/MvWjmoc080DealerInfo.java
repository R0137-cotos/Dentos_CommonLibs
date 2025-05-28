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

@Entity
@Data
@Table(name = "mv_wjmoc080_dealer_info")
public class MvWjmoc080DealerInfo {

	@Embeddable
	@Data
	public static class Id implements Serializable {

		/**
		 * シリアルバージョンID
		 */
		private static final long serialVersionUID = 1L;

		/** 販売店識別子コード */
		private String dealerDiscrimCd;

		/** デポコード */
		private String dpCd;
	}

	@EmbeddedId
	private Id id;

	/** 購買商流販売店名 */
	private String prCommDistDealerName;

	/** 購買商流販売店名（カナ） */
	private String prCommDistDealerNameKana;

	/** デポ名 */
	private String dpName;

	/** デポ名（カナ） */
	private String dpNameKana;

	/** テリトリーコード */
	private String territoryCd;

	/** リコー支店コード */
	private String ricohBranchCd;

	/** 販社コード */
	private String salesCd;

	/** csネット便対象区分 */
	private String csNetServCtgr;

	/** 単価管理区分 */
	private String priceMgmtCtgr;

	/** 購買商流売伝出力区分 */
	private String prCommSalemsgOutputCtgr;

	/** 購買商流納品書種類区分 */
	private String prCommDlvryNtcTypeCtgr;

	/** 消費税区分 */
	private String consumptionTaxCtgr;

	/** nr販売店コード */
	private String nrDealerCd;

	/** 販売店売価ランクコード */
	private String dealerSalesPriceRankCd;

	/** 傘下店参考仕切価格区分 */
	private String affshopRefGrpInPriceCtgr;

	/** 会社コード */
	private String companyCd;

	/** 組織コード */
	private String orgCd;

	/** マスタ無効フラグ */
	private String mInvalidFlg;

	/** 作成者id */
	private String createdById;

	/** 作成日時 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDatetime;

	/** 最終更新者id */
	private String lastUpdatedById;

	/** 最終更新日時 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdateDatetime;

}
