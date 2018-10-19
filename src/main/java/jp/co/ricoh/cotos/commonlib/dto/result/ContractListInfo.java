package jp.co.ricoh.cotos.commonlib.dto.result;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.contract.Contract.ContractType;
import jp.co.ricoh.cotos.commonlib.entity.contract.Contract.LifecycleStatus;
import jp.co.ricoh.cotos.commonlib.entity.contract.Contract.WorkflowStatus;
import lombok.Data;

/**
 * 契約をリスト取得するためのDtoです。<br/>
 * 一覧を取得するためには、こちらのクラスを使ってください。 <br/>
 * このクラスを使用してDBへの保存を行うことは出来ません。
 */
@Entity
@Data
public class ContractListInfo {

	@Id
	@ApiModelProperty(value = "契約ID", required = true, position = 1)
	private long id;

	/**
	 * 契約番号
	 */
	@ApiModelProperty(value = "契約番号<br />" //
			+ "契約番号 + \"-\" + 契約番号枝番", //
			required = false, position = 2, allowableValues = "range[0,18]") //
	private String contractNumber;

	/**
	 * サービス識別番号
	 */
	@ApiModelProperty(value = "サービス識別番号", required = false, position = 3, allowableValues = "range[0,18]")
	private String serviceIdentificationNumber;

	/**
	 * 契約種別
	 */
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = "契約種別<br />" //
			+ "新規, プラン変更, 解約などの契約種別を表す。", //
			required = false, position = 4) //
	private ContractType contractType;

	/**
	 * 契約ステータス
	 */
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = "契約ステータス<br />" //
			+ "状態遷移上のライフサイクル状態を表す。", //
			required = false, position = 5) //
	private LifecycleStatus contractStatus;

	/**
	 * 契約状態
	 */
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = "契約状態<br />" //
			+ "状態遷移上のワークフロー状態を表す。", //
			required = false, position = 6) //
	private WorkflowStatus contractCondition;

	/**
	 * 見積番号
	 */
	@ApiModelProperty(value = "見積番号<br />" //
			+ "見積番号 + \"-\" + 見積番号枝番", //
			required = false, position = 7, allowableValues = "range[0,18]") //
	private String estimateNumber;

	/**
	 * 見積件名
	 */
	@ApiModelProperty(value = "見積件名", required = false, position = 8, allowableValues = "range[0,255]")
	private String estimationTitle;

	/**
	 * 案件番号
	 */
	@ApiModelProperty(value = "案件番号", required = false, position = 9, allowableValues = "range[0,255]")
	private String caseNumber;

	/**
	 * 請求開始月
	 */
	@ApiModelProperty(value = "請求開始月", required = false, position = 10)
	private String billingStartMonth;

	/**
	 * お客様企業名
	 */
	@ApiModelProperty(value = "お客様企業名", required = false, position = 11, allowableValues = "range[0,255]")
	private String companyName;

	/**
	 * お客様事業所名
	 */
	@ApiModelProperty(value = "お客様事業所名", required = false, position = 12, allowableValues = "range[0,255]")
	private String officeName;

	/**
	 * お客様部門名
	 */
	@ApiModelProperty(value = "お客様部門名", required = false, position = 13, allowableValues = "range[0,255]")
	private String departmentName;

	/**
	 * サービス開始日
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "サービス開始日", required = false, position = 14)
	private Date serviceTermStart;

	/**
	 * サービス終了日
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "サービス終了日", required = false, position = 15)
	private Date serviceTermEnd;

	/**
	 * 商品名称
	 */
	@ApiModelProperty(value = "商品名称", required = false, position = 16, allowableValues = "range[0,255]")
	private String productName;

	/**
	 * 担当営業氏名
	 */
	@ApiModelProperty(value = "担当営業氏名", required = false, position = 17, allowableValues = "range[0,255]")
	private String picEmptxName;

	/**
	 * 担当支社名
	 */
	@ApiModelProperty(value = "担当支社名", required = false, position = 18, allowableValues = "range[0,255]")
	private String picAffiliateName;

	@PrePersist
	public void prePersist() {
		throw new IllegalAccessError();
	}
}