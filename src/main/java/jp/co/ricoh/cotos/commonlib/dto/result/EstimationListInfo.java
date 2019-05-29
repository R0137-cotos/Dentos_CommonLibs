package jp.co.ricoh.cotos.commonlib.dto.result;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation.EstimationType;
import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation.LifecycleStatus;
import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation.WorkflowStatus;
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
	@ApiModelProperty(value = "連番", required = true, position = 1)
	private long seqNo;

	@ApiModelProperty(value = "見積ID", required = true, position = 2)
	private long id;

	/**
	 * 見積番号
	 */
	@ApiModelProperty(value = "見積番号", required = true, position = 3, allowableValues = "range[0,18]")
	private String estimateNumber;

	/**
	 * 見積種別
	 */
	@ApiModelProperty(value = "見積種別<br />" //
			+ "新規、契約変更等の見積種別を表す。", //
			required = false, position = 4) //
	private EstimationType estimationtype;

	/**
	 * 見積件名
	 */
	@ApiModelProperty(value = "見積件名", required = false, position = 5, allowableValues = "range[0,255]")
	private String estimationTitle;

	/**
	 * 見積ステータス
	 */
	@ApiModelProperty(value = "見積ステータス<br />" //
			+ "状態遷移上のワークフロー状態を表す。", //
			required = false, position = 6) //
	private WorkflowStatus status;

	/**
	 * 見積状態
	 */
	@ApiModelProperty(value = "見積状態<br />" //
			+ "状態遷移上のライフサイクル状態を表す。", //
			required = false, position = 7) //
	private LifecycleStatus lifecycleStatus;

	/**
	 * 案件番号
	 */
	@ApiModelProperty(value = "案件番号", required = false, position = 8, allowableValues = "range[0,255]")
	private String caseNumber;

	/**
	 * 事業所名
	 */
	@ApiModelProperty(value = "事業所名", required = false, position = 9, allowableValues = "range[0,255]")
	private String officeName;

	/**
	 * 企業名
	 */
	@ApiModelProperty(value = "企業名", required = false, position = 10, allowableValues = "range[0,255]")
	private String customerName;

	/**
	 * 部門名
	 */
	@ApiModelProperty(value = "部門名", required = false, position = 11, allowableValues = "range[0,255]")
	private String departmentName;

	/**
	 * 担当営業
	 */
	@ApiModelProperty(value = "担当営業", required = false, position = 12, allowableValues = "range[0,8]")
	private String picEmptxName;

	/**
	 * 商品名
	 */
	@ApiModelProperty(value = "商品名", required = false, position = 13, allowableValues = "range[0,255]")
	private String itemName;

	/**
	 * 掲示日
	 */
	@ApiModelProperty(value = "掲示日", required = false, position = 14)
	@Temporal(TemporalType.TIMESTAMP)
	private Date coverPresentationDate;

	/**
	 * 担当支社名
	 */
	@ApiModelProperty(value = "担当支社名", required = false, position = 15, allowableValues = "range[0,255]")
	private String picAffiliateName;

	@PrePersist
	public void prePersist() {
		throw new IllegalAccessError();
	}
}