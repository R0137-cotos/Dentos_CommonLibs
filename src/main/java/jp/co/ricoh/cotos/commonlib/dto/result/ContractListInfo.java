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
	@ApiModelProperty(value = "契約番号", required = true, position = 2, allowableValues = "range[0,18]")
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
	@ApiModelProperty(value = "契約種別", required = false, position = 4)
	private ContractType contractType;

	// TODO ライフサイクル状態とワークフロー状態に変更、契約の検索API担当者が修正
	// /**
	// * 契約ステータス
	// */
	// @Enumerated(EnumType.STRING)
	// @ApiModelProperty(value = "契約ステータス", required = false, position = 5)
	// private ContractStatus contractStatus;

	/**
	 * 見積番号
	 */
	@ApiModelProperty(value = "見積番号", required = false, position = 6, allowableValues = "range[0,18]")
	private String estimateNumber;

	/**
	 * 見積件名
	 */
	@ApiModelProperty(value = "見積件名", required = false, position = 7, allowableValues = "range[0,255]")
	private String estimationTitle;

	/**
	 * 案件番号
	 */
	@ApiModelProperty(value = "案件番号", required = false, position = 8, allowableValues = "range[0,255]")
	private String caseNumber;

	/**
	 * サービス開始日
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "サービス開始日", required = false, position = 9)
	private Date serviceTermStart;

	/**
	 * サービス終了日
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "サービス終了日", required = false, position = 10)
	private Date serviceTermEnd;

	/**
	 * 見積掲示日
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "見積掲示日", required = false, position = 11)
	private Date estimationPresentationDate;

	/**
	 * お客様企業名
	 */
	@ApiModelProperty(value = "お客様企業名", required = false, position = 12, allowableValues = "range[0,255]")
	private String companyName;

	/**
	 * お客様事業所名
	 */
	@ApiModelProperty(value = "お客様事業所名", required = false, position = 13, allowableValues = "range[0,255]")
	private String officeName;

	/**
	 * お客様部門名
	 */
	@ApiModelProperty(value = "お客様部門名", required = false, position = 14, allowableValues = "range[0,255]")
	private String departmentName;

	/**
	 * 商品名
	 */
	@ApiModelProperty(value = "商品名", required = false, position = 15, allowableValues = "range[0,255]")
	private String productName;

	/**
	 * 担当営業氏名
	 */
	@ApiModelProperty(value = "担当営業氏名", required = false, position = 16, allowableValues = "range[0,255]")
	private String picEmptxName;

	/**
	 * 担当支社名
	 */
	@ApiModelProperty(value = "担当支社名", required = false, position = 17, allowableValues = "range[0,255]")
	private String picAffiliateName;

	@PrePersist
	public void prePersist() {
		throw new IllegalAccessError();
	}
}