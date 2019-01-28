package jp.co.ricoh.cotos.commonlib.dto.result;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWork;
import jp.co.ricoh.cotos.commonlib.entity.contract.Contract.ContractType;
import lombok.Data;

/**
 * 手配をリスト取得するためのDtoです。<br/>
 * 一覧を取得するためには、こちらのクラスを使ってください。 <br/>
 * このクラスを使用してDBへの保存を行うことは出来ません。
 */
@Entity
@Data
public class ArrangementListInfo {

	@Id
	@ApiModelProperty(value = "連番", required = true, position = 1)
	private long seqNo;
	
	/**
	 * 契約ID
	 */
	@ApiModelProperty(value = "契約ID", required = true, position = 2)
	private long contractId;

	/**
	 * 手配ID
	 */
	@ApiModelProperty(value = "手配ID", required = true, position = 3)
	private long arrangementId;

	/**
	 * 手配業務ID
	 */
	@ApiModelProperty(value = "手配業務ID", required = true, position = 4)
	private long arrangementWorkId;

	/**
	 * 契約番号
	 */
	@ApiModelProperty(value = "契約番号<br />" //
			+ "契約番号 + \"-\" + 契約番号枝番", //
			required = false, position = 5, allowableValues = "range[0,18]") //
	private String contractNumber;

	/**
	 * サービス識別番号
	 */
	@ApiModelProperty(value = "サービス識別番号", required = false, position = 6, allowableValues = "range[0,18]")
	private String serviceIdentificationNumber;

	/**
	 * 契約状態
	 */
	@ApiModelProperty(value = "契約状態<br />" //
			+ "状態遷移上のワークフロー状態を表す。", //
			required = false, position = 7) //
	private jp.co.ricoh.cotos.commonlib.entity.contract.Contract.WorkflowStatus contractCondition;

	/**
	 * 契約種別
	 */
	@ApiModelProperty(value = "契約種別<br />" //
			+ "新規, プラン変更, 解約などの契約種別を表す。", //
			required = false, position = 8) //
	private ContractType contractType;

	/**
	 * お客様顧客名
	 */
	@ApiModelProperty(value = "お客様企業名", required = false, position = 9, allowableValues = "range[0,255]")
	private String customerName;

	/**
	 * 商品名
	 */
	@ApiModelProperty(value = "商品名称", required = false, position = 10, allowableValues = "range[0,255]")
	private String productName;

	/**
	 * 希望納期
	 */
	@ApiModelProperty(value = "希望納期", required = false, position = 11)
	@Temporal(TemporalType.TIMESTAMP)
	private Date desiredDeliveryDate;

	/**
	 * 手配業務
	 */
	@ApiModelProperty(value = "手配業務", required = false, position = 12, allowableValues = "range[0,255]")
	private String arrangementName;

	/**
	 * 業務受理日時
	 */
	@ApiModelProperty(value = "業務受理日時", required = false, position = 13)
	@Temporal(TemporalType.TIMESTAMP)
	private Date businessAcceptanceDateTime;
	
	/**
	 * 手配業務担当者
	 */
	@ApiModelProperty(value = "手配業務担当者", required = false, position = 14, allowableValues = "range[0,255]")
	private String workUserName;

	/**
	 * 手配業務ステータス
	 */
	@ApiModelProperty(value = "手配業務ステータス<br />" //
			+ "状態遷移上のワークフロー状態を表す。", //
			required = false, position = 15) //
	private ArrangementWork.WorkflowStatus arrangementWorkStatus;

	/**
	 * 見積書番号
	 */
	@ApiModelProperty(value = "見積書番号", required = false, position = 16, allowableValues = "range[0,18]")
	private String estimateNumber;

	/**
	 * 担当営業氏名
	 */
	@ApiModelProperty(value = "担当営業氏名", required = false, position = 17, allowableValues = "range[0,255]")
	private String picEmptxName;

	/**
	 * 担当支社
	 */
	@ApiModelProperty(value = "担当支社", required = false, position = 18, allowableValues = "range[0,255]")
	private String picAffiliateName;

	/**
	 * サービス開始日
	 */
	@ApiModelProperty(value = "サービス開始日", required = false, position = 19)
	@Temporal(TemporalType.TIMESTAMP)
	private Date serviceTermStart;

	/**
	 * サービス終了日
	 */
	@ApiModelProperty(value = "サービス終了日", required = false, position = 20)
	@Temporal(TemporalType.TIMESTAMP)
	private Date serviceTermEnd;

	@PrePersist
	public void prePersist() {
		throw new IllegalAccessError();
	}
}