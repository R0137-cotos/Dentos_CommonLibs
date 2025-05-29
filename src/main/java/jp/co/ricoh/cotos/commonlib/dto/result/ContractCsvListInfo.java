package jp.co.ricoh.cotos.commonlib.dto.result;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.Data;

/**
 * 契約CSVリスト取得するためのDtoです。<br/>
 * 一覧を取得するためには、こちらのクラスを使ってください。 <br/>
 * このクラスを使用してDBへの保存を行うことは出来ません。
 */
@Entity
@Data
@JsonPropertyOrder({ "契約ID", "契約番号", "案件No", "案件名", "契約種別", "契約ステータス", "変更元契約番号", "変更元契約ID", "サービス識別番号", "契約日", "請求開始月", "サービス開始日", "サービス終了日", "解約予定日", "作成元見積ID", "見積番号", "見積件名", "商流区分", "発行書式", "得意先コード", "得意先宛先名", "契約用見積掲示日" })
public class ContractCsvListInfo {

	@Id
	@JsonProperty("契約ID")
	private long id;

	/**
	 * 契約番号
	 */
	@JsonProperty("契約番号")
	private String contractNumber;

	/**
	 * 案件No
	 */
	@JsonProperty("案件No")
	private String caseNumber;

	/**
	 * 案件名
	 */
	@JsonProperty("案件名")
	private String caseTitle;

	/**
	 * 契約種別
	 */
	@JsonProperty("契約種別")
	private String contractType;

	/**
	 * 契約ステータス
	 */
	@JsonProperty("契約ステータス")
	private String contractStatus;

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
	 * 契約日
	 */
	@JsonProperty("契約日")
	private String contractDate;

	/**
	 * 請求開始月
	 */
	@JsonProperty("請求開始月")
	private String billingMonth;

	/**
	 * サービス開始日
	 */
	@JsonProperty("サービス開始日")
	private String serviceTermStart;

	/**
	 * サービス終了日
	 */
	@JsonProperty("サービス終了日")
	private String serviceTermEnd;

	/**
	 * 解約予定日
	 */
	@JsonProperty("解約予定日")
	private String cancelScheduledDate;

	/**
	 * 作成元見積Id
	 */
	@JsonProperty("作成元見積ID")
	private long originEstimationId;

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
	 * 得意先コード
	 */
	@JsonProperty("得意先コード")
	private String billingCustomerSpCode;

	/**
	 * 得意先宛先名
	 */
	@JsonProperty("得意先宛先名")
	private String billingCustomerSpName;

	/**
	 * 契約用見積掲示日
	 */
	@JsonProperty("契約用見積掲示日")
	private String estimationPresentationDate;
}
