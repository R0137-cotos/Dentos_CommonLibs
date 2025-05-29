package jp.co.ricoh.cotos.commonlib.entity.accounting;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import lombok.Data;

@Entity
@Data
@Table(name = "wjcmj301_kiyk_sikyu_cts_wk")
public class Wjcmj301KiykSikyuCtsWk {

	/** データ区分 **/
	private String dataKbn;

	/** 契約ID **/
	private String kiykId;

	/** 連携元管理ID **/
	private String rnkiMtKnrId;

	/** 連携元管理明細ID **/
	@Id
	private String rnkiMtKnrMisiId;

	/** データ連携日 **/
	private String dataRnkiHi;

	/** 案件No **/
	private String anknNo;

	/** 契約No **/
	private String kiykNo;

	/** 連携元更新回数 **/
	private Integer rnkiMtKusnKisu;

	/** 連携元契約番号 **/
	private String rnkiMtKiykBng;

	/** 更新区分 **/
	private String kusnKbn;

	/** 契約種類区分 **/
	private String kiykSyriKbn;

	/** 契約名 **/
	private String kiykMi;

	/** 契約締結日 **/
	@Temporal(TemporalType.DATE)
	private Date kiykTiktHi;

	/** サービス開始日 **/
	@Temporal(TemporalType.DATE)
	private Date serviceKisHi;

	/** サービス終了日 **/
	@Temporal(TemporalType.DATE)
	private Date serviceSyuryuHi;

	/** 契約期間（単位含む） **/
	private Long kiykKknTnifkm;

	/** 保守契約個別要求 **/
	private String hsyKiykKbtYukyu;

	/** 業務作業連絡事項 **/
	private String gyumSgyuRnrkJku;

	/** お客様MoM顧客システム連携ID **/
	private String okyksmMmCstId;

	/** お客様画面表示用お客様名 **/
	private String okyksmGmnHyuzYuCustomerMi;

	/** お客様法人格前後区分 **/
	private String okyksmHuznkkZngKbn;

	/** お客様法人格 **/
	private String okyksmHuznkk;

	/** お客様企業名 **/
	private String okyksmKgyuMi;

	/** お客様企業名カナ **/
	private String okyksmKgyuMiKana;

	/** お客様事業所名 **/
	private String okyksmJgysyMi;

	/** お客様事業所名カナ **/
	private String okyksmJgysyMiKana;

	/** お客様企業郵便番号 **/
	private String okyksmKgyuYubn;

	/** お客様企業住所 **/
	private String okyksmKgyuJyusy;

	/** お客様企業ビル名等 **/
	private String okyksmKgyuBldngMiTu;

	/** お客様企業ＴＥＬ **/
	private String okyksmKgyuTel;

	/** お客様企業ＦＡＸ **/
	private String okyksmKgyuFax;

	/** お客様部署名 **/
	private String okyksmBsyMi;

	/** お客様契約担当者名 **/
	private String okyksmKiykTntusyMi;

	/** お客様契約担当者名カナ **/
	private String okyksmKiykTntusyMiKana;

	/** お客様契約担当者メールアドレス **/
	private String okyksmKiykTntusyMlAddr;

	/** 画面表示用販社名 **/
	private String gmnHyuzYouHnsyMi;

	/** 契約担当販社MoM顧客システム連携ID **/
	private String kiykTntuHnsyMmCstId;

	/** RTS契約担当支社コード **/
	private String rtsKiykTntuSsyCd;

	/** RTS契約担当支社名 **/
	private String rtsKiykTntuSsyMi;

	/** 契約担当販社コード **/
	private String kiykTntuHnsyCd;

	/** 契約担当販社名 **/
	private String kiykTntuHnsyMi;

	/** 契約担当組織名 **/
	private String kiykTntuSskMi;

	/** 契約担当者名 **/
	private String kiykTntusyMi;

	/** 契約担当MoM社員ID **/
	private String kiykTntuMmSyinId;

	/** 契約担当_RJ_NS社員コード **/
	private String kiykTntuRjNsSyinCd;

	/** 契約担当_RJ_CUBIC部門コード **/
	private String kiykTntuRjCubicBmnCd;

	/** 契約担当_RTS_NS社員コード **/
	private String kiykTntuRtsNsSyinCd;

	/** 契約担当_RTS_CUBIC部門コード **/
	private String kiykTntuRtsCubicBmnCd;

	/** 契約担当販社郵便番号 **/
	private String kiykTntuHnsyYubn;

	/** 契約担当販社住所 **/
	private String kiykTntuHnsyJyusy;

	/** 契約担当販社住所ビル名等 **/
	private String kiykTntuHnsyJyusyBldMi;

	/** 契約担当販社ＴＥＬ **/
	private String kiykTntuHnsyTel;

	/** 販売店MoM顧客システム連携ID **/
	private String hnbitnMmCstId;

	/** 販売店名 **/
	private String hnbitnMi;

	/** 販売店組織名 **/
	private String hnbitnSskMi;

	/** 販売店担当者名 **/
	private String hnbitnTntusyMi;

	/** 母店MoM顧客システム連携ID **/
	private String btnMmCstId;

	/** 母店名 **/
	private String btnMi;

	/** 母店組織名 **/
	private String btnSskMi;

	/** 母店担当者名 **/
	private String btnTntusyMi;

	/** RTS組織名 **/
	private String rtsSskMi;

	/** RTS契約担当者名 **/
	private String rtsKiykTntusyMi;

	/** 仕入先名 **/
	private String sirskMi;

	/** SIMONS契約形態 **/
	private String simonsKiykKiti;

	/** PM回数 **/
	private String pmKisu;

	/** PM点検月 **/
	private String pmTnknTk;

	/** 強制入力フラグ **/
	private String kyusiNyurykFlg;

	/** 売上得意先コード **/
	private String uragTkiskCd;

	/** 業務担当者名 **/
	private String gyumTntusyMi;

	/** 契約者甲_MoM企業ID **/
	private String kiykshKuMmKgyuId;

	/** 契約者甲_MoM事業所ID **/
	private String kiykshKuMmJgyusyId;

	/** 契約者甲_顧客識別区分 **/
	private String kiykshKuCustomerSkbtKbn;

	/** 契約者甲_会社名 **/
	private String kiykshKuKisyMi;

	/** 契約者甲_担当者名 **/
	private String kiykshKuTntusyMi;

	/** 契約者乙_顧客識別区分 **/
	private String kiykshOtCustomerSkbtKbn;

	/** 契約者乙_会社名 **/
	private String kiykshOtKisyMi;

	/** 売上区分 **/
	private String uragKbn;

	/** 契約担当者通知先メールアドレス **/
	private String kiykTntusyTutskMailAddress;

	/** 請求実施メール送信フラグ（契約担当者） **/
	private String sikyJssMlSndFlgKykTntusy;

	/** 業務担当者通知先メールアドレス **/
	private String gyumTntusyTutskMailAddress;

	/** 請求実施メール送信フラグ（業務担当者） **/
	private String sikyJssMlSndFlgGymTntusy;

	/** 連携元契約URL **/
	private String rnkiMtKiykUrl;

	/** 特殊作業依頼DB文書リンク **/
	private String tkshSgyuIriDbBnsyLnk;

	/** 取込処理中フラグ **/
	private String trkmShrchFlg;

	/** 契約更新方法区分 **/
	private String kiykKusnHuhuKbn;

	/** 契約更新確認手段区分 **/
	private String kiykKusnKknnShdnKbn;

	/** 更新時連絡事項 **/
	private String kusnjRnrkJku;

	/** 更新案内送付先 **/
	private String kusnAnniSufSk;

	/** 作成データパターン区分 **/
	private String sksiDataPatternKbn;

	/** 商品分類区分 **/
	private String syuhnBnriKbn;

	/** 商品コード **/
	private String syuhnCd;

	/** 商品名 **/
	private String syuhnMi;

	/** 商品名（手入力） **/
	private String shuhnmiTnyuryk;

	/** 数量 **/
	private Long suryu;

	/** 単価 **/
	private Long tnk;

	/** 金額 **/
	private Long kngk;

	/** 契約定価単価 **/
	private Long kiykTikTnk;

	/** 契約定価 **/
	private Long kiykTik;

	/** 契約定価判定区分 **/
	private String kiykTikHntiKbn;

	/** バージョン **/
	private String vrsn;

	/** シリアル番号 **/
	private String sralBng;

	/** 集約請求フラグ **/
	private String shuykSikyuFlg;

	/** 社内使用経費課所コード **/
	private String shniSyuKihKasyoCd;

	/** 売上可否理由区分 **/
	private String uragKhRyuKbn;

	/** 売上可否理由 **/
	private String uragKhRyu;

	/** 親契約明細No **/
	private String oyKiykMisiNo;

	/** リース/レンタルNo **/
	private String lasRntlNo;

	/** リース/レンタル期間開始日 **/
	private String lasRntlKknKisHi;

	/** リース/レンタル期間終了日 **/
	private String lasRntlKknSyuryuHi;

	/** 月数 **/
	private String tksu;

	/** 契約月数 **/
	private String kiykGtsu;

	/** 毎月請求開始月区分 **/
	private String mitkSikyuKisTkKbn;

	/** 代行回収会社区分 **/
	private String dikuKishuKisyKbn;

	/** 代行回収会社名 **/
	private String dikuKishuKisyMi;

	/** 請求方法区分 **/
	private String sikyuHuhuKbn;

	/** 計算方法区分 **/
	private String kisnHuhuKbn;

	/** 納品書記事欄パターン区分 **/
	private String nuhnshKjrnPttrnKbn;

	/** 納品書記事欄1手入力フラグ **/
	@Column(name = "nuhnsh_kjrn1_tnyuryk_flg")
	private String nuhnshKjrn1TnyurykFlg;

	/** 納品書記事欄1 **/
	private String rNuhnshKjrn1;

	/** 納品書記事欄2手入力フラグ **/
	@Column(name = "nuhnsh_kjrn2_tnyuryk_flg")
	private String nuhnshKjrn2TnyurykFlg;

	/** 納品書記事欄2 **/
	private String rNuhnshKjrn2;

	/** 納品書記事欄3手入力フラグ **/
	@Column(name = "nuhnsh_kjrn3_tnyuryk_flg")
	private String nuhnshKjrn3TnyurykFlg;

	/** 納品書記事欄3 **/
	private String rNuhnshKjrn3;

	/** 納品書記事欄その他編集依頼 **/
	private String nuhnshKjrnSntHnshuIri;

	/** 納品書備考欄区分 **/
	private String nuhnshBkurnKbn;

	/** 納品書備考欄その他 **/
	private String nuhnshBkurnSnt;

	/** 特記事項 **/
	private String tkkJku;

	/** 表示順 **/
	private String hyuzJn;

	/** 連携元機器ID **/
	private String rnkiMtKkId;

	/** 機種名 **/
	private String ksyMi;

	/** 機種略号 **/
	private String ksyRykgu;

	/** 機番 **/
	private String kiban;

	/** メーカー名 **/
	private String mkrMi;

	/** メーカーコード **/
	private String mkrCd;

	/** 型式 **/
	private String ktsk;

	/** 設置先MoM顧客システム連携ID **/
	private String sttskMmCstId;

	/** 設置先企業名 **/
	private String sttskKgyuMi;

	/** 設置先企業名カナ **/
	private String sttskKgyuMiKana;

	/** 設置先事業所名 **/
	private String sttskJgysyMi;

	/** 設置先事業所名カナ **/
	private String sttskJgysyMiKana;

	/** 設置先部署名 **/
	private String sttskBsyMi;

	/** 設置先郵便番号 **/
	private String sttskKgyuYubn;

	/** 設置先住所 **/
	private String sttskKgyuJyusy;

	/** 設置先住所ビル名等 **/
	private String sttskKgyuBldngMiTu;

	/** 設置先担当者名 **/
	private String sttskTntusyMi;

	/** 設置先担当者名カナ **/
	private String sttskTntusyMiKana;

	/** 設置先TEL **/
	private String sttskTel;

	/** 設置先FAX **/
	private String sttskFax;

	/** サービス実施区委託企業名 **/
	private String serviceJssKuItakKgyuMi;

	/** サービス実施区委託組織名 **/
	private String serviceJssKuItakSskMi;

	/** サービス実施区委託課所コード **/
	private String serviceJssKuItakKasyoCd;

	/** サービス実施区委託担当者名 **/
	private String serviceJssKuItakTntusyMi;

	/** サービス実施区委託担当者CEコード **/
	private String serviceJssKuItakTntuCeCd;

	/** 守秘義務フラグ **/
	private String shhgmFlg;

	/** 保守対象商品分類 **/
	private String hsyTisyuSyuhnBnri;

	/** ＠Remote区分 **/
	private String rmtKbn;

	/** 連携元契約明細汎用区分 **/
	private String rnkimtKiykMisiHnyuKbn;

	/** 連携元契約明細汎用ID **/
	private String rnkimtKiykMisiHnyuId;

	/** 連携元請求条件ID **/
	private String rnkimtSikyuZyuknId;

	/** 連番 **/
	private Integer rnbn;

	/** 商流パターン区分 **/
	private String syuryuPatternKbn;

	/** 売上パターン区分 **/
	private String uragPatternKbn;

	/** 指定計上日 **/
	private String stiKijyoHi;

	/** 仕入先RINGS取引先コード **/
	private String sirskRingsTrhkskCd;

	/** 仕入先CUBICサイトコード **/
	private String sirskCubicSitCd;

	/** CUBIC明細データ連携フラグ **/
	private String cubicDtlRnkiFlg;

	/** サプライ直送No **/
	private String spplyChksuNo;

	/** 料金マスタサブコード **/
	private String ryuknMstrSbCd;

	/** 利用ログ参照区分 **/
	private String ryuLogSnshuKbn;

	/** 利用ログ参照対象サービスサブID **/
	private String ryuLogSnshuTisyuServSbid;

	/** 利用ログ存在チェック区分 **/
	private String ryuLogSnziChkKbn;

	/** レンジ対象区分 **/
	private String rngTisyuKbn;

	/** 利用値単位 **/
	private String ryucTni;

	/** 利用値端数処理位置 **/
	private Integer ryucHsuShrIc;

	/** 利用値端数処理区分 **/
	private String ryucHsuShrKbn;

	/** 利用値変換区分 **/
	private String ryucHnknKbn;

	/** 最小課金単位数 **/
	private BigDecimal sishuKknTnisu;

	/** 度数控除時端数処理位置 **/
	private Integer dsuKujjHsuShrIc;

	/** 度数控除時端数処理区分 **/
	private String dsuKujjHsuShrKbn;

	/** 度数控除値・率 **/
	private BigDecimal dsuKujValueRt;

	/** 度数控除方式区分 **/
	private String dsuKujHuskKbn;

	/** 度数算出方式区分 **/
	private String dsuSnshtHuskKbn;

	/** 度数単位数 **/
	private BigDecimal dsuTnisu;

	/** 度数端数処理位置 **/
	private Integer dsuHsuShrIc;

	/** 度数端数処理区分 **/
	private String dsuHsuShrKbn;

	/** 請求金額計算方式区分 **/
	private String sikyuKngkKisnHuskKbn;

	/** 請求金額上限 **/
	private BigDecimal sikyuKngkJugn;

	/** 支払原価計算方式区分 **/
	private String shriGnkKsnHskKbn;

	/** 支払原価連動区分 **/
	private String shriGnkRnduKbn;

	/** 支払原価対象金額区分 **/
	private String shriGnkTisyuKngkKbn;

	/** 支払原価率 **/
	private BigDecimal shriGnkRt;

	/** 連携元請求ID **/
	private String rnkimtSikyuId;

	/** 連携元商流別請求ID **/
	private String rnkimtShuryubtSikyuId;

	/** 商流店_商流番号 **/
	private Integer syuryutnSyuryuBngu;

	/** 商流店_CUBIC会社コード **/
	private String syuryutnCubicKaisyaCd;

	/** 得意先コード **/
	private String tkiskCd;

	/** 顧客コード **/
	private String customerCd;

	/** 請求総回数 **/
	private Integer sikyuSukisu;

	/** 設置先サイトコード **/
	private String cInstlltnSiteCd;

	/** 納品書届先指定フラグ **/
	private String nuhnshTdkskStiFlg;

	/** 届先コード **/
	private String tdkskCd;

	/** ユーザ名 **/
	private String usrMi;

	/** 届先名 **/
	private String tdkskMi;

	/** 届先名(カナ) **/
	private String tdkskMiKn;

	/** 届先郵便番号 **/
	private String tdkskYubnBngu;

	/** 届先住所1 **/
	@Column(name = "tdksk_zyusy_1")
	private String tdkskZyusy1;

	/** 届先住所2 **/
	@Column(name = "tdksk_zyusy_2")
	private String tdkskZyusy2;

	/** 届先電話番号 **/
	private String tdkskDnwBngu;

	/** 届先FAX番号 **/
	private String tdkskFaxBngu;

	/** 届先住所コード **/
	private String tdkskJushCd;

	/** 売上課所・売上SA個別指定フラグ **/
	private String uragKasyoUragSaKbtStiflg;

	/** 売上SA_NS社員コード（個別指定用） **/
	private String uragSaNsSyinCdKbt;

	/** 売上SA_RINGS社員コード（個別指定用） **/
	private String uragSaRingsSyinCdKbt;

	/** 売上課所_CUBIC部門コード（個別指定用） **/
	private String uragKasyoCubicBmnCdKbt;

	/** 売上計上従業員番号 **/
	private String uragKizyuZyugyuinBngu;

	/** 売価 **/
	private BigDecimal bik;

	/** 原価 **/
	private BigDecimal gnk;

	/** 売上管理原価 **/
	private BigDecimal uragKnrGnk;

	/** 売価単価 **/
	private BigDecimal bikTnk;

	/** 原価単価 **/
	private BigDecimal gnkTnk;

	/** 経過済金額 **/
	private BigDecimal kikzmKngk;

	/** 回収済金額(税込) **/
	private Long kishuzmKngkZk;

	/** 追加請求額 **/
	private BigDecimal tikSikyuGk;

	/** 返金額 **/
	private BigDecimal hnknGk;

	/** 手数料商品コード **/
	private String tsuryuSyuhnCd;

	/** 販社コード **/
	private String hnsyCd;

	/** エリア在庫課所コード **/
	private String araZikKasyoCd;

	/** 在庫課所コード **/
	private String zikKasyoCd;

	/** 費用勘定科目コード **/
	private String hiyoKnjoKmkCd;

	/** 計上部門コード **/
	private String kizyuBmnCd;

	/** 在庫区コード **/
	private String zikKuCd;

	/** 倉庫区コード **/
	private String sukKuCd;

	/** 棚卸区分 **/
	private String tnorsKbn;

	/** 手数料取引先コード **/
	private String tsuryuTrhkskCd;

	/** 相手勘定科目コード **/
	private String aitKntiKmkCd;

	/** 相手計上部門コード **/
	private String aitKijyoBmnCd;

	/** 相手在庫区コード **/
	private String aitZikKuCd;

	/** 相手倉庫区コード **/
	private String aitSukKuCd;

	/** 相手棚卸区分 **/
	private String aitTnorsKbn;

	/** 納品書発行区分 **/
	private String nuhnshHkkuKbn;

	/** 随時請求書発行区分 **/
	private String zijSikyushHkkuKbn;

	/** 随時請求書宛先部署名 **/
	private String zijSikyushAtskBsyMi;

	/** 随時請求書宛名 **/
	private String zijSikyushAtn;

	/** 納品書/随時請求書追加・補足事項 **/
	private String nuhnshZijSikyushTikHskjku;

	/** 商品名パターン番号 **/
	private String syuhnMiPttrnBng;

	/** 契約取引日 **/
	private Date kiykTrhkHi;

	/** 設置先名_tran **/
	private String sttskMi;

	/** 保守開始日 **/
	private Date hsyKisHi;

	/** 保守終了日 **/
	private Date hsySyuryuHi;

	/** 商品コード（リコー品種コード） **/
	private String ricohHnsyCd;

	/** 販サ保守契約DB文書リンク **/
	private String hnsHsyKiykDbBnsyLnk;

	/** 申請元契約ID **/
	private String snsiMtKiykId;

	/** 変更・解約理由コード **/
	private String hnkuKiykRyuCd;

	/** 変更・解約理由 **/
	private String hnkuKiykRyu;

	/** RINGS随時請求書発行フラグ **/
	private String ringsZijSikyushHkkuFlg;

	/** 経過済金額消費税額 **/
	private Long kikzmKngkSyuhziGk;

	/** 経過済売上管理原価 **/
	private BigDecimal kikzmUragKnrGnk;

	/** 解約日 **/
	private String kiykHi;

	/** 契約担当_RJ_従業員番号 **/
	private String kiykTntuRjZyugyuinBngu;

	/** 契約担当_RTS_従業員番号 **/
	private String kiykTntuRtsZyugyuinBngu;

	/** 確定者従業員番号 **/
	private String kktisyZyugyuinBngu;

	/** 確定者MoM会社ID **/
	private String kktisyMmKisyId;

	/** 確定者名 **/
	private String kktisyMi;

	/** 契約者甲の企事部ID **/
	private String kiyksyKuKzbId;

	/** 回収済金額 **/
	private BigDecimal kishuzmKngk;

	/** 回収済金額消費税額 **/
	private Long kishuzmKngkSyuhziGk;

	/** 追加請求額消費税額 **/
	private Long tikSikyuGkSyuhziGk;

	/** 返金額消費税額 **/
	private Long hnknGkSyuhziGk;

	/** 解約時消費税率 **/
	private Integer kiykzSyuhziRt;

	/** 売価消費税額 **/
	private Long bikSyuhziGk;

	/** V-UP連携フラグ **/
	private String vupRnkiFlg;

	/** 明細識別区分 **/
	private String misiSkbtKbn;

	/** 顧客注文番号 **/
	private String kkykTyumnBngu;

	/** お問合せ番号 **/
	private String otiawsBngu;

	/** 見積番号 **/
	private String mtmrBngu;

	/** 見積明細番号 **/
	private String mtmrMisiBngu;

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
