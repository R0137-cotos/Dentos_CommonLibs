package jp.co.ricoh.cotos.commonlib.dto.result;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

/**
 * 見積CSVリスト取得するためのDtoです。<br/>
 * 一覧を取得するためには、こちらのクラスを使ってください。 <br/>
 * このクラスを使用してDBへの保存を行うことは出来ません。
 */
@Entity
@Data
@JsonPropertyOrder({ "見積ID", "見積番号", "見積件名", "見積種別", "見積ステータス", "案件番号", "案件件名", "変更元契約番号", "変更元契約ID", "サービス識別番号", "商流区分", "発行書式", "帳票用見積件名", "帳票用顧客企業名", "見積鑑用企業名", "見積鑑用敬称", "見積鑑用見積件名", "見積鑑用納期", "見積鑑用支払条件", "見積鑑用有効期限", "見積鑑用備考", "見積鑑用見積掲示日", "見積発行元会社名", "見積発行元所属", "見積発行元郵便番号", "見積発行元住所", "見積発行元電話番号", "見積発行元FAX番号", "見積発行元担当者名", "特価希望理由", "特価希望理由テキスト", "主競合先名称", "競合情報", "競合先契約種別", "競合先基本料金" })
public class EstimationCsvListInfo {

	@Id
	@JsonProperty("見積ID")
	private long id;

	/**
	 * 見積番号
	 */
	@JsonProperty("見積番号")
	private String estimateNumber;

	/**
	 * 見積件名
	 */
	@JsonProperty("見積件名")
	private String estimationTitle;

	/**
	 * 見積区分
	 */
	@JsonProperty("見積種別")
	private String estimationDiv;

	/**
	 * 見積ステータス
	 */
	@JsonProperty("見積ステータス")
	private String status;

	/**
	 * 案件番号
	 */
	@JsonProperty("案件番号")
	private String caseNumber;

	/**
	 * 案件件名
	 */
	@JsonProperty("案件件名")
	private String caseTitle;

	/**
	 * 変更元契約番号
	 */
	@JsonProperty("変更元契約番号")
	private String originContractNumber;

	/**
	 * 変更元契約ID
	 */
	@JsonProperty("変更元契約ID")
	private Long originContractId;

	/**
	 * サービス識別番号
	 */
	@JsonProperty("サービス識別番号")
	private String serviceIdentificationNumber;

	/**
	 * 商流区分
	 */
	@JsonProperty("商流区分")
	private String commercialFlowDiv;

	/**
	 * 発行書式
	 */
	@JsonProperty("発行書式")
	private String issueFormat;

	/**
	 * 帳票用見積件名
	 */
	@JsonProperty("帳票用見積件名")
	private String issueEstimationTitle;

	/**
	 * 帳票用顧客企業名
	 */
	@JsonProperty("帳票用顧客企業名")
	private String issueCustomerCorpName;

	/**
	 * 見積鑑用企業名
	 */
	@JsonProperty("見積鑑用企業名")
	private String coverCompanyName;

	/**
	 * 見積鑑用敬称
	 */
	@JsonProperty("見積鑑用敬称")
	private String coverTitle;

	/**
	 * 見積鑑用見積件名
	 */
	@JsonProperty("見積鑑用見積件名")
	private String coverEstimationSubject;

	/**
	 * 見積鑑用納期
	 */
	@JsonProperty("見積鑑用納期")
	private String coverDeliveryDate;

	/**
	 * 見積鑑用支払条件
	 */
	@JsonProperty("見積鑑用支払条件")
	private String coverPaymentTerms;

	/**
	 * 見積鑑用有効期限
	 */
	@JsonProperty("見積鑑用有効期限")
	private String coverExpirationDate;

	/**
	 * 見積鑑用備考
	 */
	@JsonProperty("見積鑑用備考")
	private String coverRemarks;

	/**
	 * 見積鑑用見積掲示日
	 */
	@JsonProperty("見積鑑用見積掲示日")
	private String coverPresentationDate;

	/**
	 * 見積発行元会社名
	 */
	@JsonProperty("見積発行元会社名")
	private String publishCompany;

	/**
	 * 見積発行元所属
	 */
	@JsonProperty("見積発行元所属")
	private String publishDepartment;

	/**
	 * 見積発行元郵便番号
	 */
	@JsonProperty("見積発行元郵便番号")
	private String publishPostNumber;

	/**
	 * 見積発行元住所
	 */
	@JsonProperty("見積発行元住所")
	private String publishAddress;

	/**
	 * 見積発行元電話番号
	 */
	@JsonProperty("見積発行元電話番号")
	private String publishTel;

	/**
	 * 見積発行元FAX番号
	 */
	@JsonProperty("見積発行元FAX番号")
	private String publishFax;

	/**
	 * 見積発行元担当者名
	 */
	@JsonProperty("見積発行元担当者名")
	private String publishEmployee;

	/**
	 * 特価希望理由
	 */
	@JsonProperty("特価希望理由")
	private String spPriceApplyReason;

	/**
	 * 特価希望理由テキスト
	 */
	@JsonProperty("特価希望理由テキスト")
	private String spPriceApplyReasonText;

	/**
	 * 主競合先名称
	 */
	@JsonProperty("主競合先名称")
	private String mainCompetitorName;

	/**
	 * 競合情報
	 */
	@JsonProperty("競合情報")
	private String competitionInfo;

	/**
	 * 競合先契約種別
	 */
	@JsonProperty("競合先契約種別")
	private String competitionContractDiv;

	/**
	 * 競合先基本料金
	 */
	@JsonProperty("競合先基本料金")
	private Long competitionAmount;
}
