package jp.co.ricoh.cotos.commonlib.dto.parameter.reports;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.master.ReportTemplateMaster.LifecycleStatus;
import jp.co.ricoh.cotos.commonlib.entity.master.ReportTemplateMaster.OutputType;
import jp.co.ricoh.cotos.commonlib.entity.master.ReportTemplateMaster.ServiceCategory;
import jp.co.ricoh.cotos.commonlib.entity.master.ReportTemplateMaster.TargetType;
import jp.co.ricoh.cotos.commonlib.entity.master.ReportTemplateMaster.WorkflowStatus;
import lombok.Data;

/**
 * 帳票特定パラメーター
 */
@Data
public class ReportSpecificParameter {

	/**
	 * 出力形式
	 */
	@ApiModelProperty(value = "出力形式", required = false, allowableValues = "PDF(\"1\"), Excel(\"2\")", example = "1", position = 1)
	private OutputType outputType;

	/**
	 * サービスカテゴリ
	 */
	@ApiModelProperty(value = "サービスカテゴリ", required = false, allowableValues = "見積(\"1\"), 契約(\"2\"), 手配(\3\")", example = "1", position = 2)
	private ServiceCategory serviceCategory;

	/**
	 * 対象文書ID
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "対象文書ID" //
			+ "見積：見積ID<br />" //
			+ "契約：契約ID<br />" //
			+ "手配：手配業務ID<br />", required = false, allowableValues = "range[0,255]", position = 3)
	private String targetDocId;

	/**
	 * 対象種別
	 */
	@ApiModelProperty(value = "対象種別", required = false, allowableValues = "新規(\"1\"), 契約変更(\"2\"), 情報変更(\"3\"), 解約(\"4\")", example = "1", position = 4)
	private TargetType targetType;

	/**
	 * 商流区分
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "商流区分", required = false, allowableValues = "range[0,255]", position = 5)
	private String commercialFlowDiv;

	/**
	 * 商品マスタID
	 */
	@ApiModelProperty(value = "商品マスタID", required = false, allowableValues = "range[0,9999999999999999999]", position = 6)
	private Long productMasterId;

	/**
	 * テンプレート区分
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "テンプレート区分", required = false, allowableValues = "range[0,255]", position = 7)
	private String templateType;

	/**
	 * ライフサイクル状態
	 */
	@ApiModelProperty(value = "ライフサイクル状態", required = false, allowableValues = "作成中(\"1\"), 作成完了(\"2\"), 受注(\"3\"), 失注(\"4\"), 破棄(\"5\"), キャンセル手続き中(\"6\"), 予定日待ち(\"7\"), 締結中(\"8\"), 解約手続き中(\"9\"), 解約予定日待ち(\"10\"), 解約(\"11\"), 旧契約(\"12\"), 締結待ち(\"13\")", example = "1", position = 8)
	private LifecycleStatus lifecycleStatus;

	/**
	 * ワークフロー状態
	 */
	@ApiModelProperty(value = "ワークフロー状態", required = false, allowableValues = "作成中(\"1\"), 業務依頼中(\"2\"), 業務処理完了(\"3\"), 承認依頼中(\"4\"), 承認済(\"5\"), 顧客提示済(\"6\"), キャンセル申請中(\"7\"), 売上可能(\"8\"), 解約申請中(\"9\"), 手配中(\"10\"), 手配完了(\"11\"), 受付待ち(\"12\"), 作業中(\"13\"), 作業完了報告(\"14\"), 作業完了(\"15\"), エラー(\"16\")", example = "1", position = 9)
	private WorkflowStatus workflowStatus;

	/**
	 * 帳票ページ特定パラメーター配列
	 */
	@Valid
	@ApiModelProperty(value = "帳票ページ特定パラメーター配列", required = false, position = 10)
	private List<ReportPageSpecificParameter> reportPageSpecificParameter;
}
