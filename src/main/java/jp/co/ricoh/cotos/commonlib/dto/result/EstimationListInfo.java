package jp.co.ricoh.cotos.commonlib.dto.result;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

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
			required = false, allowableValues = "新規(\"1\"), 契約変更(\"2\")", position = 4) //
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
			required = false, allowableValues = "作成中(\"1\"), 業務依頼中(\"2\"), 業務処理完了(\"3\"), 承認依頼中(\"4\"), 承認済(\"5\"), 顧客提示済(\"6\")", position = 6) //
	private WorkflowStatus status;

	/**
	 * 見積状態
	 */
	@ApiModelProperty(value = "見積状態<br />" //
			+ "状態遷移上のライフサイクル状態を表す。", //
			required = false, allowableValues = "作成中(\"1\"), 作成完了(\"2\"), 受注(\"3\"), 失注(\"4\"), 破棄(\"5\")", position = 7) //
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

	/**
	 * RJ管理番号
	 */
	@ApiModelProperty(value = "RJ管理番号", required = false, position = 16, allowableValues = "range[0,255]")
	private String rjManageNumber;

	/**
	 * 恒久契約識別番号
	 */
	@ApiModelProperty(value = "R恒久契約識別番号", required = false, position = 17, allowableValues = "range[0,255]")
	private String immutableContIdentNumber;

	/**
	 * MoM企業ID
	 */
	@ApiModelProperty(value = "MoM企業ID", required = false, position = 18, allowableValues = "range[0,255]")
	private String companyId;

	/**
	 * MoM企事部ID
	 */
	@ApiModelProperty(value = "MoM企事部ID", required = false, position = 19, allowableValues = "range[0,255]")
	private String momCustId;

	/**
	 * 商流区分
	 */
	@ApiModelProperty(value = "商流区分", required = false, position = 20, allowableValues = "range[0,255]")
	private String commercialFlowDiv;

	/**
	 * 支社コード
	 */
	@ApiModelProperty(value = "支社コード", required = false, position = 21, allowableValues = "range[0,255]")
	private String ringsHanshCd;

	/**
	 * CUBIC部門コード
	 */
	@ApiModelProperty(value = "CUBIC部門コード", required = false, position = 22, allowableValues = "range[0,255]")
	private String cubicOrgId;

	/**
	 * 部署
	 */
	@ApiModelProperty(value = "部署", required = false, position = 23, allowableValues = "range[0,255]")
	private String salesDepartmentName;

	/**
	 *  RINGS社員コード
	 */
	@ApiModelProperty(value = "RINGS社員コード", required = false, position = 24, allowableValues = "range[0,255]")
	private String ringsEmpCd;

	/**
	 * 作成者
	 */
	@ApiModelProperty(value = "作成者", required = false, position = 25, allowableValues = "range[0,255]")
	private String createdUser;

	/**
	 * 登録日時
	 */
	@ApiModelProperty(value = "登録日時", required = false, position = 26)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;

	/**
	 * 更新日時
	 */
	@ApiModelProperty(value = "更新日時", required = false, position = 27)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

	@PrePersist
	public void prePersist() {
		throw new IllegalAccessError();
	}
}