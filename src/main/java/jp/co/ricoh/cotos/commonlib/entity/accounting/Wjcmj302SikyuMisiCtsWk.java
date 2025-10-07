package jp.co.ricoh.cotos.commonlib.entity.accounting;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "wjcmj302_sikyu_misi_cts_wk")
public class Wjcmj302SikyuMisiCtsWk {

	/** 連携元管理ID **/
	private String rnkiMtKnrId;

	/** 連携元管理明細ID **/
	@Id
	private String rnkiMtKnrMisiId;

	/** 契約No **/
	private String kiykNo;

	/** 連携元契約明細汎用区分 **/
	private String rnkimtKiykMisiHnyuKbn;

	/** 連携元契約明細汎用ID **/
	private String rnkimtKiykMisiHnyuId;

	/** 連携元請求ID **/
	private String rnkimtSikyuId;

	/** 連携元商流別請求ID **/
	private String rnkimtShuryubtSikyuId;

	/** 連携元商流別請求明細ID **/
	private String rnkimtShuryubtSikyuMisiId;

	/** データ連携日 **/
	private String dataRnkiHi;

	/** 請求回数 **/
	private Integer sikyuKisu;

	/** 対象年月 **/
	private String tisyuNngt;

	/** 消込ステータス **/
	private String kskmStts;

	/** 売上処理ステータス **/
	private String uragSyrStatus;

	/** 仕入処理ステータス **/
	private String sirSyrStatus;

	/** 請求処理ステータス **/
	private String sikyuSyrStatus;

	/** 売上取引日 **/
	private Date uragTrhkHi;

	/** 仕入取引日 **/
	private Date sirTrhkHi;

	/** 請求取引日 **/
	private Date sikyuTrhkHi;

	/** 債権債務照合キー **/
	private String siknSimSyuguKey;

	/** 請求集約キー **/
	private String sikyuShuykKey;

	/** 赤伝元連携元商流別請求明細ID **/
	private Long admtRnkimtSrybtSkyMisiId;

	/** 請求カウンタ **/
	private Long sikyuCuntr;

	/** 請求期間開始日 **/
	private Date sikyuKknKisHi;

	/** 請求期間終了日 **/
	private Date sikyuKknSyuryuHi;

	/** 料金マスタ適用判定日 **/
	private Date ryuknMstrTkyuHntiHi;

	/** 契約金額 **/
	private BigDecimal kiykKngk;

	/** 売価金額 **/
	private BigDecimal bikKngk;

	/** 売価単価 **/
	private BigDecimal bikTnk;

	/** 売価金額消費税率 **/
	private Integer bikKngkSyuhZirt;

	/** 売価金額税区分 **/
	private String bikKngkZiKbn;

	/** 原価金額 **/
	private BigDecimal gnkKngk;

	/** 原価単価 **/
	private BigDecimal gnkTnk;

	/** 原価金額消費税率 **/
	private Integer gnkKngkSyuhziRt;

	/** 原価金額税区分 **/
	private String gnkKngkZiKbn;

	/** 委託料率 **/
	private BigDecimal itkryuRt;

	/** 販売店手数料率 **/
	private BigDecimal hnbitnTsuryuRt;

	/** 販売店手数料金額 **/
	private BigDecimal hnbitnTsuryuKngk;

	/** 発生元原価金額消費税額 **/
	private Long hseiMtGnkKngkSyuhziGk;

	/** 作成時契約金額消費税額 **/
	private Long sksijKiykKngkSyuhziGk;

	/** 作成時売価金額消費税額 **/
	private Long sksizBikKngkSyuhZigk;

	/** 作成時原価金額消費税額 **/
	private Long sksijGnkKngkSyuhziGk;

	/** 納品書記事欄1 **/
	private String rNuhnshKjrn1;

	/** 納品書記事欄2 **/
	private String rNuhnshKjrn2;

	/** 納品書記事欄3 **/
	private String rNuhnshKjrn3;

	/** RINGS納品書備考欄 **/
	private String ringsNuhnsh;

	/** 各社固有管理セグメント **/
	private String kkshKyuKnrSgmnt;

	/** 納品／請求ＮＯ **/
	private String diliverBillingNo;

	/** 伝票区分 **/
	private String dnpyuKbn;

	/** データ区分 **/
	private String dataKbn;

	/** 出荷形態区分 **/
	private String shkkKitiKbn;

	/** 問合No **/
	private String tiawsNo;

	/** 依頼No **/
	private String iriNo;

	/** 配送形態区分 **/
	private String hisuKitiKbn;

	/** 共通コード **/
	private String kyutuCd;

	/** IBMシステム区分 **/
	private String ibmSysKbn;

	/** データ発注区分 **/
	private String dataHcchuKbn;

	/** 重量 **/
	private BigDecimal juryu;

	/** 処理済フラグ **/
	private String syrzmFlg;

	/** 発注識別区分 **/
	private String hcchuSkbtKbn;

	/** 発注店識別区分 **/
	private String hcchutnSkbtKbn;

	/** 計上状態区分 **/
	private String kijyoJutiKbn;

	/** 半日一日配送区分 **/
	private String hnncIcncHisuKbn;

	/** 納品時作業コード1 **/
	private String nuhnjSgyuCd1;

	/** 納品時作業コード2 **/
	private String nuhnjSgyuCd2;

	/** 納品時作業コード3 **/
	private String nuhnjSgyuCd3;

	/** 納品時作業コード4 **/
	private String nuhnjSgyuCd4;

	/** 納品時作業コード5 **/
	private String nuhnjSgyuCd5;

	/** 納品時作業コード6 **/
	private String nuhnjSgyuCd6;

	/** 納品時作業コード7 **/
	private String nuhnjSgyuCd7;

	/** 納品時作業コード8 **/
	private String nuhnjSgyuCd8;

	/** 配送料金請求有無フラグ **/
	private String hisuRyuknSikyuUmFlg;

	/** 振替商品コード **/
	private String frkeSyuhnCd;

	/** テリトリーコード **/
	private String trtrCd;

	/** ベンダーコード **/
	private String vndrCd;

	/** 仕入リンク区分 **/
	private String sirLnkKbn;

	/** 記事コード **/
	private String kjCd;

	/** バッチコントロール0 **/
	private String batCntrl1;

	/** 受注担当者 **/
	private String jucyuTntsh;

	/** バッチコントロール1 **/
	private String batCntrl2;

	/** 処理区分 **/
	private String syriKbn;

	/** 受領書有無フラグ **/
	private String jryushUmFlg;

	/** リコー情報・処理区分 **/
	private String ricohZyuhuShrKbn;

	/** リコー情報・届先コード **/
	private String ricohZyuhuTdkskCd;

	/** リコー情報・届先・販店 **/
	private String ricohZyuhuTdkskHntn;

	/** 案件番号 **/
	private String anknBngu;

	/** 特価番号 **/
	private String tkkBngu;

	/** 納品管理番号 **/
	private String nuhnKnrBng;

	/** FFM受注番号 **/
	private String ffmJchuBng;

	/** 商品名判別コード **/
	private String syuhnMiHnbtCd;

	/** 発注No **/
	private String hcchuNo;

	/** 納入先 **/
	private String nunyusk;

	/** 仕入先請求先番号 **/
	private String sirskSikyuskBng;

	/** Medi@発注コード **/
	private String mdHcchuCd;

	/** 照合キー1 **/
	private String shuguKey1;

	/** 照合キー2 **/
	private String shuguKey2;

	/** 照合キー3 **/
	private String shuguKey3;

	/** 照合キー4 **/
	private String shuguKey4;

	/** 照合キー5 **/
	private String shuguKey5;

	/** 商品名(納品書・請求書用) **/
	private String syuhnMiNuhnsySikyusyYu;

	/** 取込処理中フラグ **/
	private String trkmShrchFlg;

	/** 計上時契約金額消費税額 **/
	private Long kijyojKiykKngkSyuhziGk;

	/** 計上時売価金額消費税額 **/
	private Long kijyojBikKngkSyuhziGk;

	/** 売上管理原価 **/
	private BigDecimal uragKnrGnk;

	/** 登録者ID **/
	private String turksyId;

	/** 登録者名 **/
	private String turksyMi;

	/** 登録履歴日時 **/
	private String turkRrkNtj;

	/** 更新者ID **/
	private String kusnsyId;

	/** 更新者名 **/
	private String kusnsyMi;

	/** 更新履歴日時ID **/
	private String kusnRrkNtj;

}
