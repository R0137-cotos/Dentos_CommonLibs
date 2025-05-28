package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.Arrays;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 企事部マスタ
 */
@Entity
@Data
@Table(name = "v_kjb_master")
public class VKjbMaster {

	public enum DepartmentDiv {

		企事("1"), 企事部("2");

		private final String text;

		private DepartmentDiv(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static DepartmentDiv fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	/**
	 * MoM顧客システム連携ID
	 */
	@Id
	@Column(length = 15)
	@ApiModelProperty(value = "MoM顧客システム連携ID", required = true, position = 1, allowableValues = "range[0,15]")
	private String mclMomRelId;

	/**
	 * MoM顧客企事部ID
	 */
	@Column(length = 15)
	@ApiModelProperty(value = "MoM顧客企事部ID", required = false, position = 2, allowableValues = "range[0,15]")
	private String mclMomKjbId;

	/**
	 * 販社ｺｰﾄﾞ
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "販社ｺｰﾄﾞ", required = false, position = 3, allowableValues = "range[0,3]")
	private String mclHanshCd;

	/**
	 * 最終更新日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "最終更新日", required = false, position = 4)
	private Date mclUpdtDt;

	/**
	 * ﾊﾞｯﾁ処理見做し日
	 */
	@Column(length = 8)
	@ApiModelProperty(value = "ﾊﾞｯﾁ処理見做し日", required = false, position = 5)
	private String mclBtPrcsAsofDt;

	/**
	 * ﾊﾞｯﾁ処理日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "ﾊﾞｯﾁ処理日", required = false, position = 6)
	private Date mclBtPrcsDt;

	/**
	 * 無効フラグ
	 */
	@Column(length = 1)
	@ApiModelProperty(value = "無効フラグ", required = false, position = 7, allowableValues = "range[0,1]")
	private String mclDltFlg;

	/**
	 * 更新ﾕｰｻﾞID
	 */
	@Column(length = 64)
	@ApiModelProperty(value = "更新ﾕｰｻﾞID", required = false, position = 8, allowableValues = "range[0,64]")
	private String mclUpdtUserId;

	/**
	 * 担当者ID
	 */
	@Column(length = 15)
	@ApiModelProperty(value = "担当者ID", required = false, position = 9, allowableValues = "range[0,15]")
	private String mclTntoshId;

	/**
	 * 作成元システム
	 */
	@Column(length = 2)
	@ApiModelProperty(value = "作成元システム", required = false, position = 10, allowableValues = "range[0,2]")
	private String mclCrtSysCd;

	/**
	 * MoM顧客ID
	 */
	@Column(length = 15)
	@ApiModelProperty(value = "MoM顧客ID", required = false, position = 11, allowableValues = "range[0,15]")
	private String prflMomCstId;

	/**
	 * MoM企業ID
	 */
	@Column(length = 15)
	@ApiModelProperty(value = "MoM企業ID", required = false, position = 12, allowableValues = "range[0,15]")
	private String prflMomKgyId;

	/**
	 * MoM事業所ID
	 */
	@Column(length = 15)
	@ApiModelProperty(value = "MoM事業所ID", required = false, position = 13, allowableValues = "range[0,15]")
	private String prflMomJgsId;

	/**
	 * MoM部門ID
	 */
	@Column(length = 15)
	@ApiModelProperty(value = "MoM部門ID", required = false, position = 14, allowableValues = "range[0,15]")
	private String prflMomBmnId;

	/**
	 * 名寄せ親MoM顧客ID
	 */
	@Column(length = 15)
	@ApiModelProperty(value = "名寄せ親MoM顧客ID", required = false, position = 15, allowableValues = "range[0,15]")
	private String prflNyoOyaMomCstId;

	/**
	 * 企事部設定区分
	 */
	@Column(length = 1)
	@ApiModelProperty(value = "企事部設定区分", required = false, position = 16, allowableValues = "企事(\"1\"), 企事部(\"2\")", example = "1")
	private DepartmentDiv prflKjbSetKbn;

	/**
	 * 無効フラグ
	 */
	@Column(length = 1)
	@ApiModelProperty(value = "無効フラグ", required = false, position = 17, allowableValues = "range[0,1]")
	private String prflDltFlg;

	/**
	 * WEB作成日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "WEB作成日", required = false, position = 18)
	private Date prflScWebCrtDt;

	/**
	 * WEB作成担当者ユーザID
	 */
	@Column(length = 64)
	@ApiModelProperty(value = "WEB作成担当者ユーザID", required = false, position = 19, allowableValues = "range[0,64]")
	private String prflScWebCrtUserId;

	/**
	 * WEB作成担当者販社コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "WEB作成担当者販社コード", required = false, position = 20, allowableValues = "range[0,64]")
	private String prflScWebCrtHanshCd;

	/**
	 * WEB作成機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "WEB作成機能コード", required = false, position = 21, allowableValues = "range[0,3]")
	private String prflScWebCrtKinoCd;

	/**
	 * WEB更新日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "WEB更新日", required = false, position = 22)
	private Date prflScWebUpdtDt;

	/**
	 * WEB更新担当者ユーザID
	 */
	@Column(length = 64)
	@ApiModelProperty(value = "WEB更新担当者ユーザID", required = false, position = 23, allowableValues = "range[0,64]")
	private String prflScWebUpdtUserId;

	/**
	 * WEB更新担当者販社コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "WEB更新担当者販社コード", required = false, position = 24, allowableValues = "range[0,3]")
	private String prflScWebUpdtHanshCd;

	/**
	 * WEB更新機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "WEB更新機能コード", required = false, position = 25, allowableValues = "range[0,3]")
	private String prflScWebUpdtKinoCd;

	/**
	 * バッチ作成機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "バッチ作成機能コード", required = false, position = 26, allowableValues = "range[0,3]")
	private String prflScBtCrtKinoCd;

	/**
	 * バッチ作成処理見做し日
	 */
	@Column(length = 8)
	@ApiModelProperty(value = "バッチ作成処理見做し日", required = false, position = 27, allowableValues = "range[0,8]")
	private String prflScBtCrtAsofDt;

	/**
	 * バッチ作成日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "バッチ作成日", required = false, position = 28)
	private Date prflScBtCrtDt;

	/**
	 * バッチ更新機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "バッチ更新機能コード", required = false, position = 29, allowableValues = "range[0,3]")
	private String prflScBtUpdtKinoCd;

	/**
	 * バッチ更新処理見做し日
	 */
	@Column(length = 8)
	@ApiModelProperty(value = "バッチ更新処理見做し日", required = false, position = 30, allowableValues = "range[0,8]")
	private String prflScBtUpdtAsofDt;

	/**
	 * バッチ更新日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "バッチ更新日", required = false, position = 31)
	private Date prflScBtUpdtDt;

	/**
	 * システム管理対応日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "システム管理対応日", required = false, position = 32)
	private Date prflScSysKnrDt;

	/**
	 * システム管理NO
	 */
	@Column(length = 5)
	@ApiModelProperty(value = "システム管理NO", required = false, position = 33, allowableValues = "range[0,5]")
	private String prflScSysKnrNo;

	/**
	 * MoM最終更新機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "MoM最終更新機能コード", required = false, position = 34, allowableValues = "range[0,3]")
	private String prflScMomUpdtKinoCd;

	/**
	 * MoM最終更新日時時刻
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "MoM最終更新日時時刻", required = false, position = 35)
	private Date prflScMomUpdtDtTm;

	/**
	 * 企業名_漢字
	 */
	@Column(length = 202)
	@ApiModelProperty(value = "企業名_漢字", required = false, position = 36, allowableValues = "range[0,202]")
	private String kgyKgyNmKnji;

	/**
	 * 企業名_カナ
	 */
	@Column(length = 202)
	@ApiModelProperty(value = "企業名_カナ", required = false, position = 37, allowableValues = "range[0,202]")
	private String kgyKgyNmKana;

	/**
	 * 企業法人格区分
	 */
	@Column(length = 2)
	@ApiModelProperty(value = "企業法人格区分", required = false, position = 38, allowableValues = "range[0,2]")
	private String kgyHjnKakuCd;

	/**
	 * 企業法人格前後区分
	 */
	@Column(length = 1)
	@ApiModelProperty(value = "企業法人格前後区分", required = false, position = 39, allowableValues = "range[0,1]")
	private String kgyHjnKakuZengoCd;

	/**
	 * 企業郵便番号
	 */
	@Column(length = 50)
	@ApiModelProperty(value = "企業郵便番号", required = false, position = 40, allowableValues = "range[0,50]")
	private String kgyKgyPostNum;

	/**
	 * 企業住所コード
	 */
	@Column(length = 11)
	@ApiModelProperty(value = "企業住所コード", required = false, position = 41, allowableValues = "range[0,11]")
	private String kgyKgyAdsCd;

	/**
	 * 企業住所字通称名
	 */
	@Column(length = 500)
	@ApiModelProperty(value = "企業住所字通称名", required = false, position = 42, allowableValues = "range[0,500]")
	private String kgyKgyAdsAzatusyoNm;

	/**
	 * 企業住所番地名
	 */
	@Column(length = 20)
	@ApiModelProperty(value = "企業住所番地名", required = false, position = 43, allowableValues = "range[0,20]")
	private String kgyKgyAdsBantiNm;

	/**
	 * 企業住所号名
	 */
	@Column(length = 20)
	@ApiModelProperty(value = "企業住所号名", required = false, position = 44, allowableValues = "range[0,20]")
	private String kgyKgyAdsGoNm;

	/**
	 * 企業住所ビル名
	 */
	@Column(length = 100)
	@ApiModelProperty(value = "企業住所ビル名", required = false, position = 45, allowableValues = "range[0,100]")
	private String kgyKgyAdsBldgNm;

	/**
	 * 企業住所フロア名
	 */
	@Column(length = 100)
	@ApiModelProperty(value = "企業住所フロア名", required = false, position = 46, allowableValues = "range[0,100]")
	private String kgyKgyAdsFlorNm;

	/**
	 * 企業代表TEL
	 */
	@Column(length = 50)
	@ApiModelProperty(value = "企業代表TEL", required = false, position = 47, allowableValues = "range[0,50]")
	private String kgyKgyTelNum;

	/**
	 * 企業代表FAX
	 */
	@Column(length = 40)
	@ApiModelProperty(value = "企業代表FAX", required = false, position = 48, allowableValues = "range[0,40]")
	private String kgyKgyFaxNum;

	/**
	 * 企業ホームページURL
	 */
	@Column(length = 128)
	@ApiModelProperty(value = "企業ホームページURL", required = false, position = 49, allowableValues = "range[0,128]")
	private String kgyKgyHpUrl;

	/**
	 * 企業業種コード
	 */
	@Column(length = 5)
	@ApiModelProperty(value = "企業業種コード", required = false, position = 50, allowableValues = "range[0,5]")
	private String kgyKgyGyshCd;

	/**
	 * 企業従業員規模コード
	 */
	@Column(length = 1)
	@ApiModelProperty(value = "企業従業員規模コード", required = false, position = 51, allowableValues = "range[0,1]")
	private String kgyKgyJgyinKboCd;

	/**
	 * 企業従業員数
	 */
	@ApiModelProperty(value = "企業従業員数", required = false, position = 52)
	private Integer kgyKgyJgyinQty;

	/**
	 * 企業代表者氏名
	 */
	@Column(length = 42)
	@ApiModelProperty(value = "企業代表者氏名", required = false, position = 53, allowableValues = "range[0,42]")
	private String kgyKgyDhyshNmKnji;

	/**
	 * 企業代表者役職名
	 */
	@Column(length = 22)
	@ApiModelProperty(value = "企業代表者役職名", required = false, position = 54, allowableValues = "range[0,22]")
	private String kgyKgyDhyshYksykNmKnji;

	/**
	 * 企業COSMOSコード
	 */
	@Column(length = 9)
	@ApiModelProperty(value = "企業代表者役職名", required = false, position = 55, allowableValues = "range[0,9]")
	private String kgyKgyNumCsmsCd;

	/**
	 * 企業名略称_1
	 */
	@Column(length = 40)
	@ApiModelProperty(value = "企業名略称_1", required = false, position = 56, allowableValues = "range[0,40]")
	private String kgyKgySplNm1;

	/**
	 * 企業名略称_2
	 */
	@Column(length = 40)
	@ApiModelProperty(value = "企業名略称_2", required = false, position = 57, allowableValues = "range[0,40]")
	private String kgyKgySplNm2;

	/**
	 * 企業資本金_千円
	 */
	@ApiModelProperty(value = "企業資本金_千円", required = false, position = 58)
	private Long kgyKgyShnkn;

	/**
	 * 企業仕入先メモ
	 */
	@Column(length = 62)
	@ApiModelProperty(value = "企業仕入先メモ", required = false, position = 59, allowableValues = "range[0,62]")
	private String kgyKgySirsk;

	/**
	 * 企業販売先メモ
	 */
	@Column(length = 62)
	@ApiModelProperty(value = "企業販売先メモ", required = false, position = 60, allowableValues = "range[0,62]")
	private String kgyKgyHnbisk;

	/**
	 * 企業事業所数
	 */
	@ApiModelProperty(value = "企業事業所数", required = false, position = 61)
	private Integer kgyKgyJgsQty;

	/**
	 * 企業倒産閉鎖状態区分
	 */
	@Column(length = 1)
	@ApiModelProperty(value = "企業倒産閉鎖状態区分", required = false, position = 62, allowableValues = "range[0,1]")
	private String kgyKgyTousanHeisaInfoKbn;

	/**
	 * 企業設立年月
	 */
	@Column(length = 6)
	@ApiModelProperty(value = "企業設立年月", required = false, position = 63, allowableValues = "range[0,6]")
	private String kgyStrtsYearMs;

	/**
	 * 企業決算月
	 */
	@Column(length = 2)
	@ApiModelProperty(value = "企業決算月", required = false, position = 64, allowableValues = "range[0,2]")
	private String kgyKsnkiMs;

	/**
	 * 名寄せ用企業名_漢字
	 */
	@Column(length = 202)
	@ApiModelProperty(value = "名寄せ用企業名_漢字", required = false, position = 65, allowableValues = "range[0,202]")
	private String kgyNyoKgyNmKnji;

	/**
	 * 名寄せ用企業名_カナ
	 */
	@Column(length = 202)
	@ApiModelProperty(value = "名寄せ用企業名_カナ", required = false, position = 66, allowableValues = "range[0,202]")
	private String kgyNyoKgyNmKana;

	/**
	 * 名寄せ用企業名略称_1
	 */
	@Column(length = 40)
	@ApiModelProperty(value = "名寄せ用企業名略称_1", required = false, position = 67, allowableValues = "range[0,40]")
	private String kgyNyoKgySplNm1;

	/**
	 * 名寄せ用企業名略称_2
	 */
	@Column(length = 40)
	@ApiModelProperty(value = "名寄せ用企業名略称_2", required = false, position = 68, allowableValues = "range[0,40]")
	private String kgyNyoKgySplNm2;

	/**
	 * 与信限度額
	 */
	@ApiModelProperty(value = "与信限度額", required = false, position = 69)
	private Long kgyYoshinLimit;

	/**
	 * MA用民間顧客識別区分
	 */
	@Column(length = 1)
	@ApiModelProperty(value = "MA用民間顧客識別区分", required = false, position = 70, allowableValues = "range[0,1]")
	private String kgyMknKokykSkbtKbn;

	/**
	 * MA対象フラグ
	 */
	@Column(length = 1)
	@ApiModelProperty(value = "MA対象フラグ", required = false, position = 71, allowableValues = "range[0,1]")
	private String kgyMaTishFlg;

	/**
	 * 名寄せ親MoM企業ID
	 */
	@Column(length = 15)
	@ApiModelProperty(value = "名寄せ親MoM企業ID", required = false, position = 72, allowableValues = "range[0,15]")
	private String kgyNyoOyaMomKgyId;

	/**
	 * 企業MoM顧客フラグ
	 */
	@Column(length = 1)
	@ApiModelProperty(value = "企業MoM顧客フラグ", required = false, position = 73, allowableValues = "range[0,1]")
	private String kgyKgyMomKokykFlg;

	/**
	 * 無効フラグ
	 */
	@Column(length = 1)
	@ApiModelProperty(value = "無効フラグ", required = false, position = 74, allowableValues = "range[0,1]")
	private String kgyDltFlg;

	/**
	 * WEB作成日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "WEB作成日", required = false, position = 75)
	private Date kgyScWebCrtDt;

	/**
	 * WEB作成担当者ユーザID
	 */
	@Column(length = 64)
	@ApiModelProperty(value = "WEB作成担当者ユーザID", required = false, position = 76, allowableValues = "range[0,64]")
	private String kgyScWebCrtUserId;

	/**
	 * WEB作成担当者販社コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "WEB作成担当者販社コード", required = false, position = 77, allowableValues = "range[0,3]")
	private String kgyScWebCrtHanshCd;

	/**
	 * WEB作成機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "WEB作成機能コード", required = false, position = 78, allowableValues = "range[0,3]")
	private String kgyScWebCrtKinoCd;

	/**
	 * WEB更新日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "WEB更新日", required = false, position = 79)
	private Date kgyScWebUpdtDt;

	/**
	 * WEB更新担当者ユーザID
	 */
	@Column(length = 64)
	@ApiModelProperty(value = "WEB更新担当者ユーザID", required = false, position = 80, allowableValues = "range[0,64]")
	private String kgyScWebUpdtUserId;

	/**
	 * WEB更新担当者販社コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "WEB更新担当者販社コード", required = false, position = 81, allowableValues = "range[0,3]")
	private String kgyScWebUpdtHanshCd;

	/**
	 * WEB更新機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "WEB更新機能コード", required = false, position = 82, allowableValues = "range[0,3]")
	private String kgyScWebUpdtKinoCd;

	/**
	 * バッチ作成機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "バッチ作成機能コード", required = false, position = 83, allowableValues = "range[0,3]")
	private String kgyScBtCrtKinoCd;

	/**
	 * バッチ作成処理見做し日
	 */
	@Column(length = 8)
	@ApiModelProperty(value = "バッチ作成処理見做し日", required = false, position = 84, allowableValues = "range[0,8]")
	private String kgyScBtCrtAsofDt;

	/**
	 * バッチ作成日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "バッチ作成日", required = false, position = 85)
	private Date kgyScBtCrtDt;

	/**
	 * バッチ更新機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "バッチ更新機能コード", required = false, position = 86, allowableValues = "range[0,3]")
	private String kgyScBtUpdtKinoCd;

	/**
	 * バッチ更新処理見做し日
	 */
	@Column(length = 8)
	@ApiModelProperty(value = "バッチ更新処理見做し日", required = false, position = 87, allowableValues = "range[0,8]")
	private String kgyScBtUpdtAsofDt;

	/**
	 * バッチ更新日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "バッチ更新日", required = false, position = 88)
	private Date kgyScBtUpdtDt;

	/**
	 * システム管理対応日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "システム管理対応日", required = false, position = 89)
	private Date kgyScSysKnrDt;

	/**
	 * システム管理NO
	 */
	@Column(length = 5)
	@ApiModelProperty(value = "システム管理NO", required = false, position = 90, allowableValues = "range[0,5]")
	private String kgyScSysKnrNo;

	/**
	 * MoM最終更新機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "MoM最終更新機能コード", required = false, position = 91, allowableValues = "range[0,3]")
	private String kgyScMomUpdtKinoCd;

	/**
	 * MoM最終更新日時時刻
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "MoM最終更新日時時刻", required = false, position = 92)
	private Date kgyScMomUpdtDtTm;

	/**
	 * CUIC法人格名
	 */
	@Column(length = 100)
	@ApiModelProperty(value = "CUIC法人格名", required = false, position = 93, allowableValues = "range[0,100]")
	private String kgyCuicHjnKaku;

	/**
	 * CUIC業種_企業
	 */
	@Column(length = 100)
	@ApiModelProperty(value = "CUIC業種_企業", required = false, position = 94, allowableValues = "range[0,100]")
	private String kgyCuicGyshKgy;

	/**
	 * CUICクレンジング前_企業名_漢字
	 */
	@Column(length = 202)
	@ApiModelProperty(value = "CUICクレンジング前_企業名_漢字", required = false, position = 95, allowableValues = "range[0,202]")
	private String kgyCuicClnMaeKgyKnji;

	/**
	 * CUICクレンジング前_企業名_カナ
	 */
	@Column(length = 202)
	@ApiModelProperty(value = "CUICクレンジング前_企業名_カナ", required = false, position = 96, allowableValues = "range[0,202]")
	private String kgyCuicClnMaeKgyKana;

	/**
	 * CUICクレンジング前_郵便番号
	 */
	@Column(length = 30)
	@ApiModelProperty(value = "CUICクレンジング前_郵便番号", required = false, position = 97, allowableValues = "range[0,30]")
	private String kgyCuicClnMaePostNum;

	/**
	 * CUICクレンジング前_住所
	 */
	@Column(length = 460)
	@ApiModelProperty(value = "CUICクレンジング前_住所", required = false, position = 98, allowableValues = "range[0,460]")
	private String kgyCuicClnMaeAds;

	/**
	 * CUIC住所コード住所
	 */
	@Column(length = 460)
	@ApiModelProperty(value = "CUIC住所コード住所", required = false, position = 99, allowableValues = "range[0,460]")
	private String kgyCuicAds;

	/**
	 * CUIC番地_数字
	 */
	@ApiModelProperty(value = "CUIC番地_数字", required = false, position = 100)
	private Long kgyCuicBantiNum;

	/**
	 * CUIC号_数字
	 */
	@ApiModelProperty(value = "CUIC号_数字", required = false, position = 101)
	private Long kgyCuicGoNum;

	/**
	 * CUIC住所取得エラｰフラグ
	 */
	@Column(length = 1)
	@ApiModelProperty(value = "CUIC住所取得エラｰフラグ", required = false, position = 102, allowableValues = "range[0,1]")
	private String kgyCuicAdsErrFlg;

	/**
	 * CUICクレンジング前_電話番号
	 */
	@Column(length = 40)
	@ApiModelProperty(value = "CUICクレンジング前_電話番号", required = false, position = 103, allowableValues = "range[0,40]")
	private String kgyCuicClnMaeTelNum;

	/**
	 * CUICクレンジング前_FAX番号
	 */
	@Column(length = 40)
	@ApiModelProperty(value = "CUICクレンジング前_FAX番号", required = false, position = 104, allowableValues = "range[0,40]")
	private String kgyCuicClnMaeFaxNum;

	/**
	 * CUIC従業員規模_企業
	 */
	@Column(length = 100)
	@ApiModelProperty(value = "CUICクレンジング前_FAX番号", required = false, position = 105, allowableValues = "range[0,100]")
	private String kgyCuicJgyinKboKgy;

	/**
	 * CUIC業種_帝国コード
	 */
	@Column(length = 5)
	@ApiModelProperty(value = "CUIC業種_帝国コード", required = false, position = 106, allowableValues = "range[0,5]")
	private String kgyCuicJgyshTikkCd;

	/**
	 * CUIC販社コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "CUIC販社コード", required = false, position = 107, allowableValues = "range[0,3]")
	private String kgyCuicHanshCd;

	/**
	 * CUIC更新担当者コード
	 */
	@Column(length = 6)
	@ApiModelProperty(value = "CUIC更新担当者コード", required = false, position = 108, allowableValues = "range[0,6]")
	private String kgyCuicUpdtTntoshCd;

	/**
	 * CUIC更新端末コード
	 */
	@Column(length = 15)
	@ApiModelProperty(value = "CUIC更新端末コード", required = false, position = 109, allowableValues = "range[0,15]")
	private String kgyCuicUpdtTermCd;

	/**
	 * CUICMCDB更新日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "CUICMCDB更新日", required = false, position = 110)
	private Date kgyCuicMcdbUpdtDt;

	/**
	 * CUIC作成元システムコード
	 */
	@Column(length = 2)
	@ApiModelProperty(value = "CUIC作成元システムコード", required = false, position = 111, allowableValues = "range[0,2]")
	private String kgyCuicCrtSysCd;

	/**
	 * CUIC更新元システムコード
	 */
	@Column(length = 2)
	@ApiModelProperty(value = "CUIC更新元システムコード", required = false, position = 112, allowableValues = "range[0,2]")
	private String kgyCuicUpdtSysCd;

	/**
	 * CUIC元システム作成日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "CUIC元システム作成日", required = false, position = 113)
	private Date kgyCuicMotoSysCrtdDt;

	/**
	 * CUIC元システム更新日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "CUIC元システム更新日", required = false, position = 114)
	private Date kgyCuicMotoSysUpdtDt;

	/**
	 * CUICバッチ処理做し日
	 */
	@Column(length = 8)
	@ApiModelProperty(value = "CUICバッチ処理做し日", required = false, position = 115, allowableValues = "range[0,8]")
	private String kgyCuicBtPrcsAsofDt;

	/**
	 * CUICバッチ処理日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "CUICバッチ処理日", required = false, position = 116)
	private Date kgyCuicBtPrcsDt;

	/**
	 * CUICMCDB削除フラグ
	 */
	@Column(length = 1)
	@ApiModelProperty(value = "CUICMCDB削除フラグ", required = false, position = 117, allowableValues = "range[0,1]")
	private String kgyCuicMcdbDltFlg;

	/**
	 * CUIC更新担当者販社コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "CUIC更新担当者販社コード", required = false, position = 118, allowableValues = "range[0,3]")
	private String kgyCuicUpdtTntoshHanshCd;

	/**
	 * CUICMCDB作成手段
	 */
	@Column(length = 2)
	@ApiModelProperty(value = "CUICMCDB作成手段", required = false, position = 119, allowableValues = "range[0,2]")
	private String kgyCuicMcdbCrtdMtd;

	/**
	 * CUICMCDB更新手段
	 */
	@Column(length = 2)
	@ApiModelProperty(value = "CUICMCDB更新手段", required = false, position = 120, allowableValues = "range[0,2]")
	private String kgyCuicMcdbUpdtMtd;

	/**
	 * CUICMCDB作成日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "CUICMCDB作成日", required = false, position = 121)
	private Date kgyCuicMcdbCrtdDt;

	/**
	 * CUIC更新ユーザID
	 */
	@Column(length = 64)
	@ApiModelProperty(value = "CUIC更新ユーザID", required = false, position = 122, allowableValues = "range[0,64]")
	private String kgyCuicUpdtUserId;

	/**
	 * 市場層コード
	 */
	@Column(length = 2)
	@ApiModelProperty(value = "市場層コード", required = false, position = 123, allowableValues = "range[0,2]")
	private String kgySijosoCd;

	/**
	 * MoM会社ID
	 */
	@Column(length = 6)
	@ApiModelProperty(value = "MoM会社ID", required = false, position = 124, allowableValues = "range[0,6]")
	private String kgyMomKishId;

	/**
	 * LBCコード
	 */
	@Column(length = 11)
	@ApiModelProperty(value = "LBCコード", required = false, position = 125, allowableValues = "range[0,11]")
	private String kgyLbcCd;

	/**
	 * 法人マイナンバー
	 */
	@Column(length = 13)
	@ApiModelProperty(value = "法人マイナンバー", required = false, position = 126, allowableValues = "range[0,13]")
	private String kgyCorporateNumber;

	/**
	 * 住所郵便番号
	 */
	@Column(length = 50)
	@ApiModelProperty(value = "住所郵便番号", required = false, position = 127, allowableValues = "range[0,50]")
	private String adsKadsPostNum;

	/**
	 * 住所都道府県名_漢字
	 */
	@Column(length = 10)
	@ApiModelProperty(value = "住所都道府県名_漢字", required = false, position = 128, allowableValues = "range[0,10]")
	private String adsKtdhknNmKnji;

	/**
	 * 住所市区郡町村名_漢字
	 */
	@Column(length = 26)
	@ApiModelProperty(value = "住所市区郡町村名_漢字", required = false, position = 129, allowableValues = "range[0,26]")
	private String adsKskugnchosnKnji;

	/**
	 * 住所大字・通称名_漢字
	 */
	@Column(length = 38)
	@ApiModelProperty(value = "住所大字・通称名_漢字", required = false, position = 130, allowableValues = "range[0,38]")
	private String adsKowaTusyoKnji;

	/**
	 * 住所字名・丁目_漢字
	 */
	@Column(length = 26)
	@ApiModelProperty(value = "住所字名・丁目_漢字", required = false, position = 131, allowableValues = "range[0,26]")
	private String adsKkowChomeKnji;

	/**
	 * 住所都道府県名_カナ
	 */
	@Column(length = 8)
	@ApiModelProperty(value = "住所都道府県名_カナ", required = false, position = 132, allowableValues = "range[0,8]")
	private String adsKtdhknNmKana;

	/**
	 * 住所市区郡町村名_カナ
	 */
	@Column(length = 24)
	@ApiModelProperty(value = "住所市区郡町村名_カナ", required = false, position = 133, allowableValues = "range[0,24]")
	private String adsKskugnchosnKana;

	/**
	 * 住所大字・通称名_カナ
	 */
	@Column(length = 36)
	@ApiModelProperty(value = "住所大字・通称名_カナ", required = false, position = 134, allowableValues = "range[0,36]")
	private String adsKowaTusyoKana;

	/**
	 * 住所字名・丁目_カナ
	 */
	@Column(length = 24)
	@ApiModelProperty(value = "住所字名・丁目_カナ", required = false, position = 135, allowableValues = "range[0,24]")
	private String adsKkowChomeKana;

	/**
	 * 住所コード大（都道府県）
	 */
	@Column(name = "ADS_KADS_CD_L", length = 2)
	@ApiModelProperty(value = "住所コード大（都道府県）", required = false, position = 136, allowableValues = "range[0,2]")
	private String adsKadsCdL;

	/**
	 * 住所コード大中（市・群）
	 */
	@Column(length = 5)
	@ApiModelProperty(value = "住所コード大中（市・群）", required = false, position = 137, allowableValues = "range[0,5]")
	private String adsKadsCdLm;

	/**
	 * 住所コード大中小（町・村・大字）
	 */
	@Column(length = 8)
	@ApiModelProperty(value = "住所コード大中小（町・村・大字）", required = false, position = 138, allowableValues = "range[0,8]")
	private String adsKadsCdLms;

	/**
	 * 無効フラグ
	 */
	@Column(length = 1)
	@ApiModelProperty(value = "無効フラグ", required = false, position = 139, allowableValues = "range[0,1]")
	private String adsKdltFlg;

	/**
	 * WEB作成日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "無効フラグ", required = false, position = 140)
	private Date adsKscWebCrtDt;

	/**
	 * WEB作成担当者ユーザID
	 */
	@Column(length = 64)
	@ApiModelProperty(value = "WEB作成担当者ユーザID", required = false, position = 141, allowableValues = "range[0,64]")
	private String adsKscWebCrtUserId;

	/**
	 * WEB作成担当者販社コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "WEB作成担当者販社コード", required = false, position = 142, allowableValues = "range[0,3]")
	private String adsKscWebCrtHanshCd;

	/**
	 * WEB作成機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "WEB作成機能コード", required = false, position = 143, allowableValues = "range[0,3]")
	private String adsKscWebCrtKinoCd;

	/**
	 * WEB更新日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "WEB更新日", required = false, position = 144)
	private Date adsKscWebUpdtDt;

	/**
	 * WEB更新担当者ユーザID
	 */
	@Column(length = 64)
	@ApiModelProperty(value = "WEB更新担当者ユーザID", required = false, position = 145, allowableValues = "range[0,3]")
	private String adsKscWebUpdtUserId;

	/**
	 * WEB更新担当者販社コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "WEB更新担当者販社コード", required = false, position = 146, allowableValues = "range[0,3]")
	private String adsKscWebUpdtHanshCd;

	/**
	 * WEB更新機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "WEB更新機能コード", required = false, position = 147, allowableValues = "range[0,3]")
	private String adsKscWebUpdtKinoCd;

	/**
	 * バッチ作成機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "バッチ作成機能コード", required = false, position = 148, allowableValues = "range[0,3]")
	private String adsKscBtCrtKinoCd;

	/**
	 * バッチ作成処理見做し日
	 */
	@Column(length = 8)
	@ApiModelProperty(value = "バッチ作成処理見做し日", required = false, position = 149, allowableValues = "range[0,8]")
	private String adsKscBtCrtAsofDt;

	/**
	 * バッチ作成日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "バッチ作成日", required = false, position = 150)
	private Date adsKscBtCrtDt;

	/**
	 * バッチ更新機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "バッチ更新機能コード", required = false, position = 151, allowableValues = "range[0,3]")
	private String adsKscBtUpdtKinoCd;

	/**
	 * バッチ更新処理見做し日
	 */
	@Column(length = 8)
	@ApiModelProperty(value = "バッチ更新処理見做し日", required = false, position = 152, allowableValues = "range[0,8]")
	private String adsKscBtUpdtAsofDt;

	/**
	 * バッチ更新日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "バッチ更新日", required = false, position = 153)
	private Date adsKscBtUpdtDt;

	/**
	 * システム管理対応日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "システム管理対応日", required = false, position = 154)
	private Date adsKscSysKnrDt;

	/**
	 * システム管理NO
	 */
	@Column(length = 5)
	@ApiModelProperty(value = "システム管理NO", required = false, position = 155, allowableValues = "range[0,5]")
	private String adsKscSysKnrNo;

	/**
	 * MoM最終更新機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "MoM最終更新機能コード", required = false, position = 156, allowableValues = "range[0,3]")
	private String adsKscMomUpdtKinoCd;

	/**
	 * MoM最終更新日時時刻
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "MoM最終更新日時時刻", required = false, position = 157)
	private Date adsKscMomUpdtDtTm;

	/**
	 * 郵便番号関連１
	 */
	@Column(name = "ADS_KPOST_NUM_INFO_1", length = 1)
	@ApiModelProperty(value = "郵便番号関連１", required = false, position = 158, allowableValues = "range[0,1]")
	private String adsKpostNumInfo1;

	/**
	 * 郵便番号関連２
	 */
	@Column(name = "ADS_KPOST_NUM_INFO_2", length = 1)
	@ApiModelProperty(value = "郵便番号関連２", required = false, position = 159, allowableValues = "range[0,1]")
	private String adsKpostNumInfo2;

	/**
	 * 登録年月
	 */
	@Column(length = 6)
	@ApiModelProperty(value = "登録年月", required = false, position = 160, allowableValues = "range[0,6]")
	private String adsKtourokuYearMs;

	/**
	 * 変更年月
	 */
	@Column(length = 6)
	@ApiModelProperty(value = "変更年月", required = false, position = 161, allowableValues = "range[0,6]")
	private String adsKhenkouYearMs;

	/**
	 * 更新区分
	 */
	@Column(length = 1)
	@ApiModelProperty(value = "更新区分", required = false, position = 162, allowableValues = "range[0,1]")
	private String adsKkoushinKbn;

	/**
	 * 業種名_漢字
	 */
	@Column(length = 62)
	@ApiModelProperty(value = "業種名_漢字", required = false, position = 163, allowableValues = "range[0,62]")
	private String gyshKgyshNmKnji;

	/**
	 * 業種名_カナ
	 */
	@Column(length = 35)
	@ApiModelProperty(value = "業種名_カナ", required = false, position = 164, allowableValues = "range[0,35]")
	private String gyshKgyshNmKana;

	/**
	 * 無効フラグ
	 */
	@Column(length = 1)
	@ApiModelProperty(value = "無効フラグ", required = false, position = 165, allowableValues = "range[0,1]")
	private String gyshKdltFlg;

	/**
	 * WEB作成日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "WEB作成日", required = false, position = 166)
	private Date gyshKscWebCrtDt;

	/**
	 * WEB作成担当者ユーザID
	 */
	@Column(length = 64)
	@ApiModelProperty(value = "WEB作成担当者ユーザID", required = false, position = 167, allowableValues = "range[0,64]")
	private String gyshKscWebCrtUserId;

	/**
	 * WEB作成担当者販社コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "WEB作成担当者販社コード", required = false, position = 168, allowableValues = "range[0,3]")
	private String gyshKscWebCrtHanshCd;

	/**
	 * WEB作成機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "WEB作成機能コード", required = false, position = 169, allowableValues = "range[0,3]")
	private String gyshKscWebCrtKinoCd;

	/**
	 * WEB更新日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "WEB更新日", required = false, position = 170)
	private Date gyshKscWebUpdtDt;

	/**
	 * WEB更新担当者ユーザID
	 */
	@Column(length = 64)
	@ApiModelProperty(value = "WEB更新担当者ユーザID", required = false, position = 171, allowableValues = "range[0,64]")
	private String gyshKscWebUpdtUserId;

	/**
	 * WEB更新担当者販社コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "WEB更新担当者販社コード", required = false, position = 172, allowableValues = "range[0,3]")
	private String gyshKscWebUpdtHanshCd;

	/**
	 * WEB更新機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "WEB更新機能コード", required = false, position = 173, allowableValues = "range[0,3]")
	private String gyshKscWebUpdtKinoCd;

	/**
	 * バッチ作成機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "バッチ作成機能コード", required = false, position = 174, allowableValues = "range[0,3]")
	private String gyshKscBtCrtKinoCd;

	/**
	 * バッチ作成処理見做し日
	 */
	@Column(length = 8)
	@ApiModelProperty(value = "バッチ作成処理見做し日", required = false, position = 175, allowableValues = "range[0,8]")
	private String gyshKscBtCrtAsofDt;

	/**
	 * バッチ作成日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "バッチ作成日", required = false, position = 176)
	private Date gyshKscBtCrtDt;

	/**
	 * バッチ更新機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "バッチ更新機能コード", required = false, position = 177, allowableValues = "range[0,3]")
	private String gyshKscBtUpdtKinoCd;

	/**
	 * バッチ更新処理見做し日
	 */
	@Column(length = 8)
	@ApiModelProperty(value = "バッチ更新処理見做し日", required = false, position = 178, allowableValues = "range[0,8]")
	private String gyshKscBtUpdtAsofDt;

	/**
	 * バッチ更新日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "バッチ更新日", required = false, position = 179)
	private Date gyshKscBtUpdtDt;

	/**
	 * システム管理対応日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "システム管理対応日", required = false, position = 180)
	private Date gyshKscSysKnrDt;

	/**
	 * システム管理NO
	 */
	@Column(length = 5)
	@ApiModelProperty(value = "システム管理NO", required = false, position = 181, allowableValues = "range[0,5]")
	private String gyshKscSysKnrNo;

	/**
	 * MoM最終更新機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "MoM最終更新機能コード", required = false, position = 182, allowableValues = "range[0,3]")
	private String gyshKscMomUpdtKinoCd;

	/**
	 * MoM最終更新日時時刻
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "MoM最終更新日時時刻", required = false, position = 183)
	private Date gyshKscMomUpdtDtTm;

	/**
	 * 事業所名_漢字
	 */
	@Column(length = 100)
	@ApiModelProperty(value = "事業所名_漢字", required = false, position = 184, allowableValues = "range[0,100]")
	private String jgsJgsNmKnji;

	/**
	 * 事業所名_カナ
	 */
	@Column(length = 200)
	@ApiModelProperty(value = "事業所名_カナ", required = false, position = 185, allowableValues = "range[0,200]")
	private String jgsJgsNmKana;

	/**
	 * 事業所郵便番号
	 */
	@Column(length = 50)
	@ApiModelProperty(value = "事業所郵便番号", required = false, position = 186, allowableValues = "range[0,50]")
	private String jgsJgsPostNum;

	/**
	 * 事業所住所コード
	 */
	@Column(length = 11)
	@ApiModelProperty(value = "事業所住所コード", required = false, position = 187, allowableValues = "range[0,11]")
	private String jgsJgsAdsCd;

	/**
	 * 事業所住所字通称名
	 */
	@Column(length = 500)
	@ApiModelProperty(value = "事業所住所字通称名", required = false, position = 188, allowableValues = "range[0,500]")
	private String jgsJgsAdsAzatusyoNm;

	/**
	 * 事業所住所番地名
	 */
	@Column(length = 20)
	@ApiModelProperty(value = "事業所住所番地名", required = false, position = 189, allowableValues = "range[0,20]")
	private String jgsJgsAdsBantiNm;

	/**
	 * 事業所住所号名
	 */
	@Column(length = 20)
	@ApiModelProperty(value = "事業所住所号名", required = false, position = 190, allowableValues = "range[0,20]")
	private String jgsJgsAdsGoNm;

	/**
	 * 事業所住所ビル名
	 */
	@Column(length = 100)
	@ApiModelProperty(value = "事業所住所ビル名", required = false, position = 191, allowableValues = "range[0,100]")
	private String jgsJgsAdsBldgNm;

	/**
	 * 事業所住所フロア名
	 */
	@Column(length = 100)
	@ApiModelProperty(value = "事業所住所フロア名", required = false, position = 192, allowableValues = "range[0,100]")
	private String jgsJgsAdsFlorNm;

	/**
	 * 事業所業種コード
	 */
	@Column(length = 5)
	@ApiModelProperty(value = "事業所業種コード", required = false, position = 193, allowableValues = "range[0,5]")
	private String jgsJgsGyshCd;

	/**
	 * 事業所従業員規模コード
	 */
	@Column(length = 1)
	@ApiModelProperty(value = "事業所従業員規模コード", required = false, position = 194, allowableValues = "range[0,1]")
	private String jgsJgsJgyinKboCd;

	/**
	 * 事業所従業員数
	 */
	@ApiModelProperty(value = "事業所従業員数", required = false, position = 195)
	private Integer jgsJgsJgyinQty;

	/**
	 * 企業COSMOSコード
	 */
	@Column(length = 9)
	@ApiModelProperty(value = "企業COSMOSコード", required = false, position = 196, allowableValues = "range[0,9]")
	private String jgsKgyNumCsmsCd;

	/**
	 * 事業所COSMOSコード
	 */
	@Column(length = 5)
	@ApiModelProperty(value = "事業所COSMOSコード", required = false, position = 197, allowableValues = "range[0,5]")
	private String jgsJgsNumCsmsCd;

	/**
	 * 重点事業所コード
	 */
	@Column(length = 1)
	@ApiModelProperty(value = "重点事業所コード", required = false, position = 198, allowableValues = "range[0,1]")
	private String jgsJtnJgsCd;

	/**
	 * 事業所市場分類1_コード
	 */
	@Column(name = "JGS_SIJO_BUNRUI_1_CD", length = 2)
	@ApiModelProperty(value = "事業所市場分類1_コード", required = false, position = 199, allowableValues = "range[0,2]")
	private String jgsSijoBunrui1Cd;

	/**
	 * 事業所市場分類2_コード
	 */
	@Column(name = "JGS_SIJO_BUNRUI_2_CD", length = 2)
	@ApiModelProperty(value = "事業所市場分類2_コード", required = false, position = 200, allowableValues = "range[0,2]")
	private String jgsSijoBunrui2Cd;

	/**
	 * 事業所主力商品メモ
	 */
	@Column(length = 32)
	@ApiModelProperty(value = "事業所主力商品メモ", required = false, position = 201, allowableValues = "range[0,32]")
	private String jgsShrykShhnKnji;

	/**
	 * 事業所情報化進展ステージコード
	 */
	@Column(length = 2)
	@ApiModelProperty(value = "事業所情報化進展ステージコード", required = false, position = 202, allowableValues = "range[0,2]")
	private String jgsSysInfnStgCd;

	/**
	 * 事業所画像高度化進展ステージメモ
	 */
	@Column(length = 100)
	@ApiModelProperty(value = "事業所画像高度化進展ステージメモ", required = false, position = 203, allowableValues = "range[0,100]")
	private String jgsPpcInfnStg;

	/**
	 * 事業所状態区分
	 */
	@Column(length = 1)
	@ApiModelProperty(value = "事業所状態区分", required = false, position = 204, allowableValues = "range[0,1]")
	private String jgsJgsInfoKbn;

	/**
	 * 事業所業務名メモ
	 */
	@Column(length = 100)
	@ApiModelProperty(value = "事業所業務名メモ", required = false, position = 205, allowableValues = "range[0,100]")
	private String jgsJgsGyomNmKnji;

	/**
	 * 事業所利用グループウェアコード
	 */
	@Column(length = 2)
	@ApiModelProperty(value = "事業所利用グループウェアコード", required = false, position = 206, allowableValues = "range[0,2]")
	private String jgsJgsGrpwareCd;

	/**
	 * 名寄せ用事業所名_漢字
	 */
	@Column(length = 200)
	@ApiModelProperty(value = "名寄せ用事業所名_漢字", required = false, position = 207, allowableValues = "range[0,200]")
	private String jgsNyoJgsNmKnji;

	/**
	 * 名寄せ用事業所名_カナ
	 */
	@Column(length = 200)
	@ApiModelProperty(value = "名寄せ用事業所名_カナ", required = false, position = 208, allowableValues = "range[0,200]")
	private String jgsNyoJgsNmKana;

	/**
	 * 名寄せ親MoM事業所ID
	 */
	@Column(length = 15)
	@ApiModelProperty(value = "名寄せ親MoM事業所ID", required = false, position = 209, allowableValues = "range[0,15]")
	private String jgsNyoOyaMomJgsId;

	/**
	 * 事業所代表TEL
	 */
	@Column(length = 50)
	@ApiModelProperty(value = "事業所代表TEL", required = false, position = 210, allowableValues = "range[0,50]")
	private String jgsJgsTelNum;

	/**
	 * 事業所代表FAX
	 */
	@Column(length = 40)
	@ApiModelProperty(value = "事業所代表FAX", required = false, position = 211, allowableValues = "range[0,40]")
	private String jgsJgsFaxNum;

	/**
	 * 事業所MoM顧客フラグ
	 */
	@Column(length = 1)
	@ApiModelProperty(value = "事業所MoM顧客フラグ", required = false, position = 212, allowableValues = "range[0,1]")
	private String jgsJgsMomKokykFlg;

	/**
	 * DMC担当顧客フラグ
	 */
	@Column(length = 1)
	@ApiModelProperty(value = "DMC担当顧客フラグ", required = false, position = 213, allowableValues = "range[0,1]")
	private String jgsDmcTntoCstFlg;

	/**
	 * 無効フラグ
	 */
	@Column(length = 1)
	@ApiModelProperty(value = "無効フラグ", required = false, position = 214, allowableValues = "range[0,1]")
	private String jgsDltFlg;

	/**
	 * WEB作成日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "WEB作成日", required = false, position = 215)
	private Date jgsScWebCrtDt;

	/**
	 * WEB作成担当者ユーザID
	 */
	@Column(length = 64)
	@ApiModelProperty(value = "WEB作成担当者ユーザID", required = false, position = 216, allowableValues = "range[0,64]")
	private String jgsScWebCrtUserId;

	/**
	 * WEB作成担当者販社コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "WEB作成担当者販社コード", required = false, position = 217, allowableValues = "range[0,3]")
	private String jgsScWebCrtHanshCd;

	/**
	 * WEB作成機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "WEB作成機能コード", required = false, position = 218, allowableValues = "range[0,3]")
	private String jgsScWebCrtKinoCd;

	/**
	 * WEB更新日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "WEB更新日", required = false, position = 219)
	private Date jgsScWebUpdtDt;

	/**
	 * WEB更新担当者ユーザID
	 */
	@Column(length = 64)
	@ApiModelProperty(value = "WEB更新担当者ユーザID", required = false, position = 220, allowableValues = "range[0,64]")
	private String jgsScWebUpdtUserId;

	/**
	 * WEB更新担当者販社コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "WEB更新担当者販社コード", required = false, position = 221, allowableValues = "range[0,3]")
	private String jgsScWebUpdtHanshCd;

	/**
	 * WEB更新機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "WEB更新担当者ユーザID", required = false, position = 222, allowableValues = "range[0,3]")
	private String jgsScWebUpdtKinoCd;

	/**
	 * バッチ作成機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "バッチ作成機能コード", required = false, position = 223, allowableValues = "range[0,3]")
	private String jgsScBtCrtKinoCd;

	/**
	 * バッチ作成処理見做し日
	 */
	@Column(length = 8)
	@ApiModelProperty(value = "バッチ作成処理見做し日", required = false, position = 224, allowableValues = "range[0,8]")
	private String jgsScBtCrtAsofDt;

	/**
	 * バッチ作成日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "バッチ作成日", required = false, position = 225)
	private Date jgsScBtCrtDt;

	/**
	 * バッチ更新機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "バッチ更新機能コード", required = false, position = 226, allowableValues = "range[0,3]")
	private String jgsScBtUpdtKinoCd;

	/**
	 * バッチ更新処理見做し日
	 */
	@Column(length = 8)
	@ApiModelProperty(value = "バッチ更新処理見做し日", required = false, position = 227, allowableValues = "range[0,8]")
	private String jgsScBtUpdtAsofDt;

	/**
	 * バッチ更新日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "バッチ更新日", required = false, position = 228)
	private Date jgsScBtUpdtDt;

	/**
	 * システム管理対応日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "システム管理対応日", required = false, position = 229)
	private Date jgsScSysKnrDt;

	/**
	 * システム管理NO
	 */
	@Column(length = 5)
	@ApiModelProperty(value = "システム管理NO", required = false, position = 230, allowableValues = "range[0,5]")
	private String jgsScSysKnrNo;

	/**
	 * MoM最終更新機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "MoM最終更新機能コード", required = false, position = 231, allowableValues = "range[0,3]")
	private String jgsScMomUpdtKinoCd;

	/**
	 * MoM最終更新日時時刻
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "MoM最終更新日時時刻", required = false, position = 232)
	private Date jgsScMomUpdtDtTm;

	/**
	 * 市場層コード
	 */
	@Column(length = 2)
	@ApiModelProperty(value = "市場層コード", required = false, position = 233, allowableValues = "range[0,2]")
	private String jgsSijosoCd;

	/**
	 * 顧客関係力ｺｰﾄﾞ
	 */
	@Column(length = 1)
	@ApiModelProperty(value = "顧客関係力ｺｰﾄﾞ", required = false, position = 234, allowableValues = "range[0,1]")
	private String jgsCstKnkrykCd;

	/**
	 * LBCコード
	 */
	@Column(length = 11)
	@ApiModelProperty(value = "LBCコード", required = false, position = 235, allowableValues = "range[0,11]")
	private String jgsLbcCd;

	/**
	 * 住所郵便番号
	 */
	@Column(length = 50)
	@ApiModelProperty(value = "住所郵便番号", required = false, position = 236, allowableValues = "range[0,50]")
	private String adsJadsPostNum;

	/**
	 * 住所都道府県名_漢字
	 */
	@Column(length = 10)
	@ApiModelProperty(value = "住所都道府県名_漢字", required = false, position = 237, allowableValues = "range[0,10]")
	private String adsJtdhknNmKnji;

	/**
	 * 住所市区郡町村名_漢字
	 */
	@Column(length = 26)
	@ApiModelProperty(value = "住所市区郡町村名_漢字", required = false, position = 238, allowableValues = "range[0,26]")
	private String adsJskugnchosnKnji;

	/**
	 * 住所大字・通称名_漢字
	 */
	@Column(length = 38)
	@ApiModelProperty(value = "住所大字・通称名_漢字", required = false, position = 239, allowableValues = "range[0,38]")
	private String adsJowaTusyoKnji;

	/**
	 * 住所字名・丁目_漢字
	 */
	@Column(length = 26)
	@ApiModelProperty(value = "住所字名・丁目_漢字", required = false, position = 240, allowableValues = "range[0,26]")
	private String adsJkowChomeKnji;

	/**
	 * 住所都道府県名_カナ
	 */
	@Column(length = 8)
	@ApiModelProperty(value = "住所都道府県名_カナ", required = false, position = 241, allowableValues = "range[0,8]")
	private String adsJtdhknNmKana;

	/**
	 * 住所市区郡町村名_カナ
	 */
	@Column(length = 24)
	@ApiModelProperty(value = "住所市区郡町村名_カナ", required = false, position = 242, allowableValues = "range[0,24]")
	private String adsJskugnchosnKana;

	/**
	 * 住所大字・通称名_カナ
	 */
	@Column(length = 36)
	@ApiModelProperty(value = "住所市区郡町村名_カナ", required = false, position = 243, allowableValues = "range[0,24]")
	private String adsJowaTusyoKana;

	/**
	 * 住所字名・丁目_カナ
	 */
	@Column(length = 24)
	@ApiModelProperty(value = "住所市区郡町村名_カナ", required = false, position = 244, allowableValues = "range[0,24]")
	private String adsJkowChomeKana;

	/**
	 * 住所コード大（都道府県）
	 */
	@Column(name = "ADS_JADS_CD_L", length = 2)
	@ApiModelProperty(value = "住所コード大（都道府県）", required = false, position = 245, allowableValues = "range[0,2]")
	private String adsJadsCdL;

	/**
	 * 住所コード大中（市・群）
	 */
	@Column(length = 5)
	@ApiModelProperty(value = "住所コード大中（市・群）", required = false, position = 246, allowableValues = "range[0,5]")
	private String adsJadsCdLm;

	/**
	 * 住所コード大中小（町・村・大字）
	 */
	@Column(length = 8)
	@ApiModelProperty(value = "住所コード大中小（町・村・大字）", required = false, position = 247, allowableValues = "range[0,8]")
	private String adsJadsCdLms;

	/**
	 * 無効フラグ
	 */
	@Column(length = 1)
	@ApiModelProperty(value = "無効フラグ", required = false, position = 248, allowableValues = "range[0,1]")
	private String adsJdltFlg;

	/**
	 * WEB作成日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "WEB作成日", required = false, position = 249)
	private Date adsJscWebCrtDt;

	/**
	 * WEB作成担当者ユーザID
	 */
	@Column(length = 64)
	@ApiModelProperty(value = "WEB作成担当者ユーザID", required = false, position = 250, allowableValues = "range[0,64]")
	private String adsJscWebCrtUserId;

	/**
	 * WEB作成担当者販社コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "WEB作成担当者販社コード", required = false, position = 251, allowableValues = "range[0,3]")
	private String adsJscWebCrtHanshCd;

	/**
	 * WEB作成機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "WEB作成機能コード", required = false, position = 252, allowableValues = "range[0,3]")
	private String adsJscWebCrtKinoCd;

	/**
	 * WEB更新日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "WEB更新日", required = false, position = 253)
	private Date adsJscWebUpdtDt;

	/**
	 * WEB更新担当者ユーザID
	 */
	@Column(length = 64)
	@ApiModelProperty(value = "WEB更新担当者ユーザID", required = false, position = 254, allowableValues = "range[0,64]")
	private String adsJscWebUpdtUserId;

	/**
	 * WEB更新担当者販社コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "WEB更新担当者販社コード", required = false, position = 255, allowableValues = "range[0,3]")
	private String adsJscWebUpdtHanshCd;

	/**
	 * WEB更新機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "WEB更新機能コード", required = false, position = 256, allowableValues = "range[0,3]")
	private String adsJscWebUpdtKinoCd;

	/**
	 * バッチ作成機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "バッチ作成機能コード", required = false, position = 257, allowableValues = "range[0,3]")
	private String adsJscBtCrtKinoCd;

	/**
	 * バッチ作成処理見做し日
	 */
	@Column(length = 8)
	@ApiModelProperty(value = "バッチ作成処理見做し日", required = false, position = 258, allowableValues = "range[0,8]")
	private String adsJscBtCrtAsofDt;

	/**
	 * バッチ作成日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "バッチ作成日", required = false, position = 259)
	private Date adsJscBtCrtDt;

	/**
	 * バッチ更新機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "バッチ更新機能コード", required = false, position = 260, allowableValues = "range[0,3]")
	private String adsJscBtUpdtKinoCd;

	/**
	 * バッチ更新処理見做し日
	 */
	@Column(length = 8)
	@ApiModelProperty(value = "バッチ更新処理見做し日", required = false, position = 261, allowableValues = "range[0,8]")
	private String adsJscBtUpdtAsofDt;

	/**
	 * バッチ更新日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "バッチ更新日", required = false, position = 262)
	private Date adsJscBtUpdtDt;

	/**
	 * システム管理対応日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "システム管理対応日", required = false, position = 263)
	private Date adsJscSysKnrDt;

	/**
	 * システム管理NO
	 */
	@Column(length = 5)
	@ApiModelProperty(value = "システム管理NO", required = false, position = 264, allowableValues = "range[0,5]")
	private String adsJscSysKnrNo;

	/**
	 * MoM最終更新機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "MoM最終更新機能コード", required = false, position = 265, allowableValues = "range[0,3]")
	private String adsJscMomUpdtKinoCd;

	/**
	 * MoM最終更新日時時刻
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "MoM最終更新日時時刻", required = false, position = 266)
	private Date adsJscMomUpdtDtTm;

	/**
	 * 郵便番号関連１
	 */
	@Column(name = "ADS_JPOST_NUM_INFO_1", length = 1)
	@ApiModelProperty(value = "郵便番号関連１", required = false, position = 267, allowableValues = "range[0,1]")
	private String adsJpostNumInfo1;

	/**
	 * 郵便番号関連２
	 */
	@Column(name = "ADS_JPOST_NUM_INFO_2", length = 1)
	@ApiModelProperty(value = "郵郵便番号関連２", required = false, position = 268, allowableValues = "range[0,1]")
	private String adsJpostNumInfo2;

	/**
	 * 登録年月
	 */
	@Column(length = 6)
	@ApiModelProperty(value = "登録年月", required = false, position = 269, allowableValues = "range[0,6]")
	private String adsJtourokuYearMs;

	/**
	 * 変更年月
	 */
	@Column(length = 6)
	@ApiModelProperty(value = "変更年月", required = false, position = 270, allowableValues = "range[0,6]")
	private String adsJhenkouYearMs;

	/**
	 * 更新区分
	 */
	@Column(length = 1)
	@ApiModelProperty(value = "更新区分", required = false, position = 271, allowableValues = "range[0,1]")
	private String adsJkoushinKbn;

	/**
	 * 業種名_漢字
	 */
	@Column(length = 62)
	@ApiModelProperty(value = "業種名_漢字", required = false, position = 272, allowableValues = "range[0,62]")
	private String gyshJgyshNmKnji;

	/**
	 * 業種名_カナ
	 */
	@Column(length = 35)
	@ApiModelProperty(value = "業種名_カナ", required = false, position = 273, allowableValues = "range[0,35)]")
	private String gyshJgyshNmKana;

	/**
	 * 無効フラグ
	 */
	@Column(length = 1)
	@ApiModelProperty(value = "無効フラグ", required = false, position = 274, allowableValues = "range[0,1]")
	private String gyshJdltFlg;

	/**
	 * WEB作成日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "WEB作成日", required = false, position = 275)
	private Date gyshJscWebCrtDt;

	/**
	 * WEB作成担当者ユーザID
	 */
	@Column(length = 64)
	@ApiModelProperty(value = "WEB作成担当者ユーザID", required = false, position = 276, allowableValues = "range[0,64]")
	private String gyshJscWebCrtUserId;

	/**
	 * WEB作成担当者販社コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "WEB作成担当者販社コード", required = false, position = 277, allowableValues = "range[0,1]")
	private String gyshJscWebCrtHanshCd;

	/**
	 * WEB作成機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "WEB作成機能コード", required = false, position = 278, allowableValues = "range[0,3]")
	private String gyshJscWebCrtKinoCd;

	/**
	 * WEB更新日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "WEB更新日", required = false, position = 279)
	private Date gyshJscWebUpdtDt;

	/**
	 * WEB更新担当者ユーザID
	 */
	@Column(length = 64)
	@ApiModelProperty(value = "WEB更新担当者ユーザID", required = false, position = 280, allowableValues = "range[0,64]")
	private String gyshJscWebUpdtUserId;

	/**
	 * WEB更新担当者販社コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "WEB更新担当者販社コード", required = false, position = 281, allowableValues = "range[0,3]")
	private String gyshJscWebUpdtHanshCd;

	/**
	 * WEB更新機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "WEB更新機能コード", required = false, position = 282, allowableValues = "range[0,3]")
	private String gyshJscWebUpdtKinoCd;

	/**
	 * バッチ作成機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "バッチ作成機能コード", required = false, position = 283, allowableValues = "range[0,3]")
	private String gyshJscBtCrtKinoCd;

	/**
	 * バッチ作成処理見做し日
	 */
	@Column(length = 8)
	@ApiModelProperty(value = "バッチ作成処理見做し日", required = false, position = 284, allowableValues = "range[0,8]")
	private String gyshJscBtCrtAsofDt;

	/**
	 * バッチ作成日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "バッチ作成日", required = false, position = 285)
	private Date gyshJscBtCrtDt;

	/**
	 * バッチ更新機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "バッチ更新機能コード", required = false, position = 286, allowableValues = "range[0,3]")
	private String gyshJscBtUpdtKinoCd;

	/**
	 * バッチ更新処理見做し日
	 */
	@Column(length = 8)
	@ApiModelProperty(value = "バッチ更新処理見做し日", required = false, position = 287, allowableValues = "range[0,8]")
	private String gyshJscBtUpdtAsofDt;

	/**
	 * バッチ更新日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "バッチ更新日", required = false, position = 288)
	private Date gyshJscBtUpdtDt;

	/**
	 * システム管理対応日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "システム管理対応日", required = false, position = 289)
	private Date gyshJscSysKnrDt;

	/**
	 * システム管理NO
	 */
	@Column(length = 5)
	@ApiModelProperty(value = "システム管理NO", required = false, position = 290, allowableValues = "range[0,5]")
	private String gyshJscSysKnrNo;

	/**
	 * MoM最終更新機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "MoM最終更新機能コード", required = false, position = 291, allowableValues = "range[0,3]")
	private String gyshJscMomUpdtKinoCd;

	/**
	 * MoM最終更新日時時刻
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "MoM最終更新日時時刻", required = false, position = 292)
	private Date gyshJscMomUpdtDtTm;

	/**
	 * 部門名_漢字
	 */
	@Column(length = 200)
	@ApiModelProperty(value = "部門名_漢字", required = false, position = 293, allowableValues = "range[0,200]")
	private String bmnBmnNmKnji;

	/**
	 * 部門名_カナ
	 */
	@Column(length = 200)
	@ApiModelProperty(value = "部門名_カナ", required = false, position = 294, allowableValues = "range[0,200]")
	private String bmnBmnNmKana;

	/**
	 * MoM企業ID
	 */
	@Column(length = 15)
	@ApiModelProperty(value = "MoM企業ID", required = false, position = 295, allowableValues = "range[0,15]")
	private String bmnMomKgyId;

	/**
	 * 部門代表TEL
	 */
	@Column(length = 50)
	@ApiModelProperty(value = "部門代表TEL", required = false, position = 296, allowableValues = "range[0,50]")
	private String bmnBmnTelNum;

	/**
	 * 部門代表FAX
	 */
	@Column(length = 40)
	@ApiModelProperty(value = "部門代表FAX", required = false, position = 297, allowableValues = "range[0,40]")
	private String bmnBmnFaxNum;

	/**
	 * 親MoM部門ID
	 */
	@Column(length = 15)
	@ApiModelProperty(value = "親MoM部門ID", required = false, position = 298, allowableValues = "range[0,15]")
	private String bmnOyaMomBmnId;

	/**
	 * 部門簡略名1
	 */
	@Column(length = 40)
	@ApiModelProperty(value = "部門簡略名1", required = false, position = 299, allowableValues = "range[0,40]")
	private String bmnBmnSplNm1;

	/**
	 * 部門簡略名2
	 */
	@Column(length = 40)
	@ApiModelProperty(value = "部門簡略名2", required = false, position = 300, allowableValues = "range[0,40]")
	private String bmnBmnSplNm2;

	/**
	 * 名寄せ用部門名漢字
	 */
	@Column(length = 200)
	@ApiModelProperty(value = "名寄せ用部門名漢字", required = false, position = 301, allowableValues = "range[0,200]")
	private String bmnNyoBmnNmKnji;

	/**
	 * 名寄せ用部門名カナ
	 */
	@Column(length = 200)
	@ApiModelProperty(value = "名寄せ用部門名カナ", required = false, position = 302, allowableValues = "range[0,200]")
	private String bmnNyoBmnNmKana;

	/**
	 * 名寄せ用部門簡略名1
	 */
	@Column(length = 40)
	@ApiModelProperty(value = "名寄せ用部門簡略名1", required = false, position = 303, allowableValues = "range[0,40]")
	private String bmnNyoBmnSplNm1;

	/**
	 * 名寄せ用部門簡略名2
	 */
	@Column(length = 40)
	@ApiModelProperty(value = "名寄せ用部門簡略名2", required = false, position = 304, allowableValues = "range[0,40]")
	private String bmnNyoBmnSplNm2;

	/**
	 * 名寄せ親MoM部門ID
	 */
	@Column(length = 15)
	@ApiModelProperty(value = "名寄せ親MoM部門ID", required = false, position = 305, allowableValues = "range[0,15]")
	private String bmnNyoOyaMomBmnId;

	/**
	 * 無効フラグ
	 */
	@Column(length = 1)
	@ApiModelProperty(value = "無効フラグ", required = false, position = 306, allowableValues = "range[0,1]")
	private String bmnDltFlg;

	/**
	 * WEB作成日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "WEB作成日", required = false, position = 307)
	private Date bmnScWebCrtDt;

	/**
	 * WEB作成担当者ユーザID
	 */
	@Column(length = 64)
	@ApiModelProperty(value = "無効フラグ", required = false, position = 308, allowableValues = "range[0,1]")
	private String bmnScWebCrtUserId;

	/**
	 * WEB作成担当者販社コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "WEB作成担当者販社コード", required = false, position = 309, allowableValues = "range[0,3]")
	private String bmnScWebCrtHanshCd;

	/**
	 * WEB作成機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "WEB作成機能コード", required = false, position = 310, allowableValues = "range[0,3]")
	private String bmnScWebCrtKinoCd;

	/**
	 * WEB更新日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "WEB更新日", required = false, position = 311)
	private Date bmnScWebUpdtDt;

	/**
	 * WEB更新担当者ユーザID
	 */
	@Column(length = 64)
	@ApiModelProperty(value = "WEB更新担当者ユーザID", required = false, position = 312, allowableValues = "range[0,64]")
	private String bmnScWebUpdtUserId;

	/**
	 * WEB更新担当者販社コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "WEB更新担当者販社コード", required = false, position = 313, allowableValues = "range[0,3]")
	private String bmnScWebUpdtHanshCd;

	/**
	 * WEB更新機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "WEB更新機能コード", required = false, position = 314, allowableValues = "range[0,3]")
	private String bmnScWebUpdtKinoCd;

	/**
	 * バッチ作成機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "バッチ作成機能コード", required = false, position = 315, allowableValues = "range[0,3]")
	private String bmnScBtCrtKinoCd;

	/**
	 * バッチ作成処理見做し日
	 */
	@Column(length = 8)
	@ApiModelProperty(value = "バッチ作成処理見做し日", required = false, position = 316, allowableValues = "range[0,8]")
	private String bmnScBtCrtAsofDt;

	/**
	 * バッチ作成日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "バッチ作成日", required = false, position = 317)
	private Date bmnScBtCrtDt;

	/**
	 * バッチ更新機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "バッチ更新機能コード", required = false, position = 318, allowableValues = "range[0,3]")
	private String bmnScBtUpdtKinoCd;

	/**
	 * バッチ更新処理見做し日
	 */
	@Column(length = 8)
	@ApiModelProperty(value = "バッチ更新処理見做し日", required = false, position = 319, allowableValues = "range[0,8]")
	private String bmnScBtUpdtAsofDt;

	/**
	 * バッチ更新日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "バッチ更新日", required = false, position = 320)
	private Date bmnScBtUpdtDt;

	/**
	 * システム管理対応日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "システム管理対応日", required = false, position = 321)
	private Date bmnScSysKnrDt;

	/**
	 * システム管理NO
	 */
	@Column(length = 5)
	@ApiModelProperty(value = "システム管理NO", required = false, position = 322, allowableValues = "range[0,5]")
	private String bmnScSysKnrNo;

	/**
	 * MoM最終更新機能コード
	 */
	@Column(length = 3)
	@ApiModelProperty(value = "MoM最終更新機能コード", required = false, position = 323, allowableValues = "range[0,3]")
	private String bmnScMomUpdtKinoCd;

	/**
	 * MoM最終更新日時時刻
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "MoM最終更新日時時刻", required = false, position = 324)
	private Date bmnScMomUpdtDtTm;

	/**
	 * KG営業セグメント区分
	 */
	@Column(length = 4)
	@ApiModelProperty(value = "KG営業セグメント区分", required = false, position = 323, allowableValues = "range[0,4]")
	private String bmnKgeigySgmtKbn;

	/**
	 * 部門種別区分
	 */
	@Column(length = 1)
	@ApiModelProperty(value = "部門種別区分", required = false, position = 323, allowableValues = "range[0,1]")
	private String bmnBmnShubtKbn;

}
