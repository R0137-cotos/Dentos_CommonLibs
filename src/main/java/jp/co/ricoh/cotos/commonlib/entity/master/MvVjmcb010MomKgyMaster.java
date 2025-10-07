package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import lombok.Data;

/**
 * MoM企業
 */
@Entity
@Data
@Table(name = "mv_vjmcb010_mom_kgy")
public class MvVjmcb010MomKgyMaster {

	/**MoM企業ID*/
	@Id
	private String momKgyId;

	/**企業名_漢字*/
	private String kgyNmKnji;

	/**企業名_カナ*/
	private String kgyNmKana;

	/**企業法人格区分*/
	private String hjnKakuCd;

	/**企業法人格前後区分*/
	private String hjnKakuZengoCd;

	/**企業郵便番号*/
	private String kgyPostNum;

	/**企業住所コード*/
	private String kgyAdsCd;

	/**企業住所字通称名*/
	private String kgyAdsAzatusyoNm;

	/**企業住所番地名*/
	private String kgyAdsBantiNm;

	/**企業住所号名*/
	private String kgyAdsGoNm;

	/**企業住所ビル名*/
	private String kgyAdsBldgNm;

	/**企業住所フロア名*/
	private String kgyAdsFlorNm;

	/**企業代表TEL*/
	private String kgyTelNum;

	/**企業代表FAX*/
	private String kgyFaxNum;

	/**企業ホームページURL*/
	private String kgyHpUrl;

	/**企業業種コード*/
	private String kgyGyshCd;

	/**企業従業員規模コード*/
	private String kgyJgyinKboCd;

	/**企業従業員数*/
	private Long kgyJgyinQty;

	/**企業代表者氏名*/
	private String kgyDhyshNmKnji;

	/**企業代表者役職名*/
	private String kgyDhyshYksykNmKnji;

	/**企業COSMOSコード*/
	private String kgyNumCsmsCd;

	/**企業名略称_1*/
	private String kgySplNm1;

	/**企業名略称_2*/
	private String kgySplNm2;

	/**企業資本金_千円*/
	private Long kgyShnkn;

	/**企業仕入先メモ*/
	private String kgySirsk;

	/**企業販売先メモ*/
	private String kgyHnbisk;

	/**企業事業所数*/
	private Long kgyJgsQty;

	/**企業倒産閉鎖状態区分*/
	private String kgyTousanHeisaInfoKbn;

	/**企業設立年月*/
	private String strtsYearMs;

	/**企業決算月*/
	private String ksnkiMs;

	/**名寄せ用企業名_漢字*/
	private String nyoKgyNmKnji;

	/**名寄せ用企業名_カナ*/
	private String nyoKgyNmKana;

	/**名寄せ用企業名略称_1*/
	private String nyoKgySplNm1;

	/**名寄せ用企業名略称_2*/
	private String nyoKgySplNm2;

	/**与信限度額*/
	private Long yoshinLimit;

	/**MA用民間顧客識別区分*/
	private String mknKokykSkbtKbn;

	/**MA対象フラグ*/
	private String maTishFlg;

	/**名寄せ親MoM企業ID*/
	private String nyoOyaMomKgyId;

	/**企業MoM顧客フラグ*/
	private String kgyMomKokykFlg;

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

	/**CUIC法人格名*/
	private String cuicHjnKaku;

	/**CUIC業種_企業*/
	private String cuicGyshKgy;

	/**CUICクレンジング前_企業名_漢字*/
	private String cuicClnMaeKgyKnji;

	/**CUICクレンジング前_企業名_カナ*/
	private String cuicClnMaeKgyKana;

	/**CUICクレンジング前_郵便番号*/
	private String cuicClnMaePostNum;

	/**CUICクレンジング前_住所*/
	private String cuicClnMaeAds;

	/**CUIC住所コード住所*/
	private String cuicAds;

	/**CUIC番地_数字*/
	private Long cuicBantiNum;

	/**CUIC号_数字*/
	private Long cuicGoNum;

	/**CUIC住所取得エラｰフラグ*/
	private String cuicAdsErrFlg;

	/**CUICクレンジング前_電話番号*/
	private String cuicClnMaeTelNum;

	/**CUICクレンジング前_FAX番号*/
	private String cuicClnMaeFaxNum;

	/**CUIC従業員規模_企業*/
	private String cuicJgyinKboKgy;

	/**CUIC業種_帝国コード*/
	private String cuicJgyshTikkCd;

	/**CUIC販社コード*/
	private String cuicHanshCd;

	/**CUIC更新担当者コード*/
	private String cuicUpdtTntoshCd;

	/**CUIC更新端末コード*/
	private String cuicUpdtTermCd;

	/**CUICMCDB更新日*/
	@Temporal(TemporalType.DATE)
	private Date cuicMcdbUpdtDt;

	/**CUIC作成元システムコード*/
	private String cuicCrtSysCd;

	/**CUIC更新元システムコード*/
	private String cuicUpdtSysCd;

	/**CUIC元システム作成日*/
	@Temporal(TemporalType.DATE)
	private Date cuicMotoSysCrtdDt;

	/**CUIC元システム更新日*/
	@Temporal(TemporalType.DATE)
	private Date cuicMotoSysUpdtDt;

	/**CUICバッチ処理做し日*/
	private String cuicBtPrcsAsofDt;

	/**CUICバッチ処理日*/
	@Temporal(TemporalType.DATE)
	private Date cuicBtPrcsDt;

	/**CUICMCDB削除フラグ*/
	private String cuicMcdbDltFlg;

	/**CUIC更新担当者販社コード*/
	private String cuicUpdtTntoshHanshCd;

	/**CUICMCDB作成手段*/
	private String cuicMcdbCrtdMtd;

	/**CUICMCDB更新手段*/
	private String cuicMcdbUpdtMtd;

	/**CUICMCDB作成日*/
	@Temporal(TemporalType.DATE)
	private Date cuicMcdbCrtdDt;

	/**CUIC更新ユーザID*/
	private String cuicUpdtUserId;

	/**市場層コード*/
	private String sijosoCd;

	/**MoM会社ID*/
	private String momKishId;

	/**LBCコード*/
	private String lbcCd;

	/**法人マイナンバー*/
	private String corporateNumber;

}