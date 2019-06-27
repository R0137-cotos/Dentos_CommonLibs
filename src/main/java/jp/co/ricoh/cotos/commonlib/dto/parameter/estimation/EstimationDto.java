package jp.co.ricoh.cotos.commonlib.dto.parameter.estimation;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.DtoBase;
import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation.EstimationType;
import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation.LifecycleStatus;
import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation.WorkflowStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class EstimationDto extends DtoBase {

	/**
	 * 商品グループマスタID
	 */
	@Min(0)
	@ApiModelProperty(value = "商品グループマスタID", required = true, position = 3, allowableValues = "range[0,9223372036854775807]")
	private long productGrpMasterId;

	/**
	 * ライフサイクル状態
	 */
	@NotNull
	@ApiModelProperty(value = "ライフサイクル状態", required = true, allowableValues = "作成中(\"1\"), 作成完了(\"2\"), 受注(\"3\"), 失注(\"4\"), 破棄(\"5\")", example = "1", position = 4)
	private LifecycleStatus lifecycleStatus;

	/**
	 * ワークフロー状態
	 */
	@NotNull
	@ApiModelProperty(value = "ワークフロー状態", required = true, allowableValues = "作成中(\"1\"), 業務依頼中(\"2\"), 業務処理完了(\"3\"), 承認依頼中(\"4\"), 承認済(\"5\"), 顧客提示済(\"6\")", example = "1", position = 5)
	private WorkflowStatus workflowStatus;

	/**
	 * 恒久契約識別番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "恒久契約識別番号", required = false, position = 6, allowableValues = "range[0,255]")
	private String immutableContIdentNumber;

	/**
	 * 案件番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "案件番号", required = false, position = 7, allowableValues = "range[0,255]")
	private String caseNumber;

	/**
	 * 案件名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "案件名", required = false, position = 8, allowableValues = "range[0,255]")
	private String caseTitle;

	/**
	 * 見積番号
	 */
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "見積番号", required = true, position = 9, allowableValues = "range[0,255]")
	private String estimationNumber;

	/**
	 * 見積番号枝番
	 */
	@Min(0)
	@Max(99)
	@ApiModelProperty(value = "見積番号枝番", required = true, position = 10, allowableValues = "range[0,99]")
	private int estimationBranchNumber;

	/**
	 * 見積件名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "見積件名", required = false, position = 11, allowableValues = "range[0,255]")
	private String estimationTitle;

	/**
	 * 見積種別
	 */
	@NotNull
	@ApiModelProperty(value = "見積種別", required = true, allowableValues = "新規(\"1\"), 契約変更(\"2\")", example = "1", position = 12)
	private EstimationType estimationType;

	/**
	 * 見積作成元システム区分
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "見積作成元システム区分", required = false, position = 13)
	private String estimatedSystemDiv;

	/**
	 * 変更元契約番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "変更元契約番号", required = false, position = 14, allowableValues = "range[0,255]")
	private String originContractNumber;

	/**
	 * 変更元契約番号枝番
	 */
	@Min(0)
	@Max(99)
	@ApiModelProperty(value = "変更元契約番号枝番", required = false, position = 15, allowableValues = "range[0,99]")
	private Integer originContractBranchNumber;

	/**
	 * 変更元契約ID
	 */
	@Min(0)
	@ApiModelProperty(value = "変更元契約ID", required = false, position = 16, allowableValues = "range[0,9223372036854775807]")
	private Long originContractId;

	/**
	 * 商流区分
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "商流区分", required = false, position = 17, allowableValues = "range[0,255]")
	private String commercialFlowDiv;

	/**
	 * 発行書式
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "発行書式", required = false, position = 18, allowableValues = "range[0,255]")
	private String issueFormat;

	/**
	 * 帳票用見積件名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "帳票用見積件名", required = false, position = 19, allowableValues = "range[0,255]")
	private String issueEstimationTitle;

	/**
	 * 帳票用顧客企業名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "帳票用顧客企業名", required = false, position = 20, allowableValues = "range[0,255]")
	private String issueCustomerCorpName;

	/**
	 * 見積有効期限
	 */
	@ApiModelProperty(value = "見積有効期限", required = false, position = 21)
	@Temporal(TemporalType.DATE)
	private Date estimationLimit;

	/**
	 * 見積鑑用企業名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "見積鑑用企業名", required = false, position = 22, allowableValues = "range[0,255]")
	private String coverCompanyName;

	/**
	 * 見積鑑用敬称
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "見積鑑用敬称", required = false, position = 23, allowableValues = "range[0,255]")
	private String coverTitle;

	/**
	 * 見積鑑用見積件名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "見積鑑用見積件名", required = false, position = 24, allowableValues = "range[0,255]")
	private String coverEstimationSubject;

	/**
	 * 見積鑑用支払条件
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "見積鑑用支払条件", required = false, position = 25, allowableValues = "range[0,255]")
	private String coverPaymentTerms;

	/**
	 * 見積鑑用納期
	 */
	@ApiModelProperty(value = "見積鑑用納期", required = false, position = 26)
	@Temporal(TemporalType.DATE)
	private Date coverDeliveryDate;

	/**
	 * 見積鑑用有効期限
	 */
	@ApiModelProperty(value = "見積鑑用有効期限", required = false, position = 27)
	@Temporal(TemporalType.DATE)
	private Date coverExpirationDate;

	/**
	 * 見積鑑用備考
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "見積鑑用備考", required = false, position = 28, allowableValues = "range[0,255]")
	private String coverRemarks;

	/**
	 * 見積鑑用見積提示日
	 */
	@ApiModelProperty(value = "見積鑑用見積提示日", required = false, position = 29)
	@Temporal(TemporalType.DATE)
	private Date coverPresentationDate;

	/**
	 * 見積発行元会社名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "見積発行元会社名", required = false, position = 30, allowableValues = "range[0,255]")
	private String publishCompany;

	/**
	 * 見積発行元所属
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "見積発行元所属", required = false, position = 31, allowableValues = "range[0,255]")
	private String publishDepartment;

	/**
	 * 見積発行元郵便番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "見積発行元郵便番号", required = false, position = 32, allowableValues = "range[0,255]")
	private String publishPostNumber;

	/**
	 * 見積発行元住所
	 */
	@Size(max = 1000)
	@ApiModelProperty(value = "見積発行元住所", required = false, position = 33, allowableValues = "range[0,1000]")
	private String publishAddress;

	/**
	 * 見積発行元電話番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "見積発行元電話番号", required = false, position = 34, allowableValues = "range[0,255]")
	private String publishTel;

	/**
	 * 見積発行元FAX番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "見積発行元FAX番号", required = false, position = 35, allowableValues = "range[0,255]")
	private String publishFax;

	/**
	 * 見積発行元担当者名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "見積発行元担当者名", required = false, position = 36, allowableValues = "range[0,255]")
	private String publishEmployee;

	/**
	 * 特価希望理由
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "特価希望理由", required = false, position = 37, allowableValues = "range[0,255]")
	private String spPriceApplyReason;

	/**
	 * 特価希望理由テキスト
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "特価希望理由テキスト", required = false, position = 38, allowableValues = "range[0,255]")
	private String spPriceApplyReasonText;

	/**
	 * 主競合先名称
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "主競合先名称", required = false, position = 39, allowableValues = "range[0,255]")
	private String mainCompetitorName;

	/**
	 * 競合情報
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "競合情報", required = false, position = 40, allowableValues = "range[0,255]")
	private String competitionInfo;

	/**
	 * 競合先契約種別
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "競合先契約種別", required = false, position = 41, allowableValues = "range[0,255]")
	private String competitionContractDiv;

	/**
	 * 競合先基本料金
	 */
	@DecimalMin("0.00")
	@Digits(integer = 19, fraction = 2)
	@ApiModelProperty(value = "競合先基本料金", required = false, position = 42, allowableValues = "range[0.00,9223372036854775807.99]")
	private BigDecimal competitionAmount;

	/**
	 * 拡張項目
	 */
	@ApiModelProperty(value = "拡張項目", required = false, position = 43)
	private String extendsParameter;

	/**
	 * 見積承認ルート
	 */
	@Valid
	@OneToOne(mappedBy = "estimation")
	@ApiModelProperty(value = "見積承認ルート", required = false, position = 44)
	private EstimationApprovalRouteDto estimationApprovalRoute;

	/**
	 * 見積添付ファイル
	 */
	@Valid
	@OneToMany(mappedBy = "estimation")
	@ApiModelProperty(value = "見積添付ファイル", required = false, position = 45)
	private List<EstimationAttachedFileDto> estimationAttachedFileList;

	/**
	 * 見積担当SA社員
	 */
	@Valid
	@NotNull
	@OneToOne(mappedBy = "estimation")
	@ApiModelProperty(value = "見積担当SA社員", required = true, position = 46)
	private EstimationPicSaEmpDto estimationPicSaEmp;

	/**
	 * 見積追加編集者社員
	 */
	@Valid
	@OneToMany(mappedBy = "estimation")
	@ApiModelProperty(value = "見積追加編集者社員", required = false, position = 47)
	private List<EstimationAddedEditorEmpDto> estimationAddedEditorEmpList;

	/**
	 * 販売店（見積用）
	 */
	@Valid
	@OneToMany(mappedBy = "estimation")
	@ApiModelProperty(value = "販売店(見積用)", required = false, position = 48)
	private List<DealerEstimationDto> dealerEstimationList;

	/**
	 * 顧客（見積用）
	 */
	@Valid
	@NotNull
	@OneToOne(mappedBy = "estimation")
	@ApiModelProperty(value = "顧客(見積用)", required = true, position = 49)
	private CustomerEstimationDto customerEstimation;

	/**
	 * 見積チェック結果
	 */
	@Valid
	@OneToMany(mappedBy = "estimation")
	@ApiModelProperty(value = "見積チェック結果(作成時不要)", required = false, position = 50)
	private List<EstimationCheckResultDto> estimationCheckResultList;

	/**
	 * 見積明細
	 */
	@Valid
	@OneToMany(mappedBy = "estimation")
	@ApiModelProperty(value = "見積明細", required = false, position = 51)
	private List<EstimationDetailDto> estimationDetailList;

	/**
	 * 商品（見積用）
	 */
	@Valid
	@OneToMany(mappedBy = "estimation")
	@ApiModelProperty(value = "商品（見積用）", required = false, position = 52)
	private List<ProductEstimationDto> productEstimationList;

	/**
	 * アプリケーションID
	 */
	@Valid
	@Size(max = 255)
	@ApiModelProperty(value = "アプリケーションID", required = false, position = 53, allowableValues = "range[0,255]")
	private String appId;

	/**
	 * RJ管理番号
	 */
	@Valid
	@Size(max = 255)
	@ApiModelProperty(value = "RJ管理番号", required = false, position = 54, allowableValues = "range[0,255]")
	private String rjManageNumber;

	/**
	 * 帳票用消費税率区分
	 */
	@Valid
	@Size(max = 255)
	@ApiModelProperty(value = "帳票用消費税率区分", required = false, position = 55, allowableValues = "range[0,255]")
	private String issueTaxCodeValue;
}
