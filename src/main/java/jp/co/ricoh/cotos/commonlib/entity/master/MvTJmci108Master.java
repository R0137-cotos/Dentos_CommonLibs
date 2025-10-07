package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import lombok.Data;

/**
 * 販売会社用売上先情報マスタ
 */
@Entity
@Data
@Table(name = "mv_t_jmci108")
public class MvTJmci108Master {

	/**販売会社用請求売上先情報ID*/
	@Id
	private String hnbiKishSusInfoId;

	/**サイト番号*/
	private String customerSiteNumber;

	/**営業単位コード*/
	private String salesUnitCode;

	/**請求先分類*/
	private String susBnri;

	/**請求売上先担当セールスRINGS社員ｺｰﾄﾞ*/
	private String susSalRingsShainCd;

	/**請求売上先担当セールスMOM社員ｺｰﾄﾞ*/
	private String susSalMomShainCd;

	/**業務担当者MOM社員ｺｰﾄﾞ*/
	private String gyomTntMomShainCd;

	/**取引停止区分*/
	private String delTeisiKbn;

	/**取引先コード*/
	private String ringsSalCd;

	/**請求書振替対象*/
	private String seikysyoFrkaeTish;

	/**請求先親子区分*/
	private String susOyaKoKbn;

	/**税区分*/
	private String taxKbn;

	/**代表コードフラグ*/
	private String dhyCdKbn;

	/**単価Ｍ表示区分*/
	@Column(name = "unit_price_m_hyoji_kbn")
	private String unitPriceMHyojiKbn;

	/**単価用取引先コード*/
	private String unitPriceTorkDelCd;

	/**端数値引(桁数)*/
	private String hasuNebikKeta;

	/**登録形態*/
	private String trokKitiCd;

	/**関連会社用得意先コード*/
	private String knrnKishTokuisakiCode;

	/**PMMC諸口区分*/
	private String pmmcShogutiKbn;

	/**ルート・マシン用納品書出力区分*/
	private String routeMachineNouhinKbn;

	/**サプライ用納品書出力区分*/
	private String supplyNouhinKbn;

	/**緊急対応確認要否区分*/
	private String kikyuChkKbn;

	/**受注月次限度額*/
	private Long juchuAtrLimit;

	/**基本契約有無*/
	private String khnKykUm;

	/**NEW-S区分*/
	@Column(name = "new_s_kbn")
	private String newSKbn;

	/**納期回答区分*/
	private String nukKaitoKbn;

	/**FAX-NO*/
	private String faxNo;

	/**ICI更新日時*/
	@Temporal(TemporalType.DATE)
	private Date iciUpdtDt;

	/**MoM最終更新日時時刻*/
	@Temporal(TemporalType.TIMESTAMP)
	private Date scMomUpdtDtTm;

	/**RINGS得意先マスタ削除フラグ*/
	private String rngDelFlg;

	/**納品書種類*/
	private String nohinShurui;

	/**与信免除区分*/
	private String yoshinMnjyKbn;

	/**PMMC取引先コード*/
	private String pmmcTrhkskCd;

	/**指定注文書有無区分*/
	private String steiOrderUmuKbn;

	/**明細合算親サイトコード*/
	private String dtlTtlOyaBillSiteCd;

	/**統一単価取引先コード*/
	private String toitsuTankaTrhkskCd;

	/**単価用取引先コード（7桁）*/
	private String tankaTrhkskCd;

	/**取引先コード（7桁）*/
	private String trhkskCd;

	/**保守仕切率取引先コード*/
	private String hoshuSkriTrhkskCd;
}