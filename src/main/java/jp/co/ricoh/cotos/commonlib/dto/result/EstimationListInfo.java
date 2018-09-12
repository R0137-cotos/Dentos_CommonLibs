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
import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation.EstimationDiv;
import lombok.Data;

/**
 * 見積をリスト取得するためのDtoです。<br/>
 * 一覧を取得するためには、こちらのクラスを使ってください。 <br/>
 * このクラスを使用してDBへの保存を行うことは出来ません。
 */
@Entity
@Data
public class EstimationListInfo {

	@Id
	@ApiModelProperty(value = "見積ID", required = true, position = 1)
	private long id;

	/**
	 * 見積番号
	 */
	@ApiModelProperty(value = "見積番号", required = true, position = 2, allowableValues = "range[0,18]")
	private String estimateNumber;

	/**
	 * 見積件名
	 */
	@ApiModelProperty(value = "見積件名", required = false, position = 3, allowableValues = "range[0,255]")
	private String estimationTitle;

	/**
	 * 案件番号
	 */
	@ApiModelProperty(value = "案件番号", required = false, position = 4, allowableValues = "range[0,255]")
	private String caseNumber;

	/**
	 * 企業名
	 */
	@ApiModelProperty(value = "企業名", required = false, position = 5, allowableValues = "range[0,255]")
	private String customerName;

	/**
	 * 担当営業
	 */
	@ApiModelProperty(value = "担当営業", required = false, position = 6, allowableValues = "range[0,8]")
	private String picEmptxName;

	/**
	 * 掲示日
	 */
	@ApiModelProperty(value = "掲示日", required = false, position = 7)
	@Temporal(TemporalType.TIMESTAMP)
	private Date coverPresentationDate;

	/**
	 * 見積種別
	 */
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = "見積種別", required = false, position = 8)
	private EstimationDiv estimationDiv;

	/**
	 * 見積ステータス
	 */
	// TODO ライフサイクル状態とワークフロー状態に変更、契約の検索API担当者が修正
	// @Enumerated(EnumType.STRING)
	// @ApiModelProperty(value = "見積ステータス", required = false, position = 9)
	// private Status status;

	/**
	 * 事業所名
	 */
	@ApiModelProperty(value = "事業所名", required = false, position = 10, allowableValues = "range[0,255]")
	private String officeName;

	/**
	 * 部門名
	 */
	@ApiModelProperty(value = "部門名", required = false, position = 11, allowableValues = "range[0,255]")
	private String departmentName;

	/**
	 * 商品名
	 */
	@ApiModelProperty(value = "商品名", required = false, position = 12, allowableValues = "range[0,255]")
	private String itemName;

	/**
	 * 担当支社名
	 */
	@ApiModelProperty(value = "担当支社名", required = false, position = 13, allowableValues = "range[0,255]")
	private String picAffiliateName;

	@PrePersist
	public void prePersist() {
		throw new IllegalAccessError();
	}
}
