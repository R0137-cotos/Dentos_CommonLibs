package jp.co.ricoh.cotos.commonlib.dto.parameter.contract.external;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.DtoBase;
import jp.co.ricoh.cotos.commonlib.dto.parameter.contract.ContractAddedEditorEmpDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.contract.ContractDetailDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.contract.ContractPicSaEmpDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.contract.CustomerContractDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.contract.DealerContractDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.contract.ManagedEstimationDetailDto;
import jp.co.ricoh.cotos.commonlib.entity.contract.Contract.IfsLinkageCsvCreateStatus;
import jp.co.ricoh.cotos.commonlib.entity.contract.Contract.LifecycleStatus;
import jp.co.ricoh.cotos.commonlib.entity.contract.Contract.WorkflowStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ContractExtCreateDto extends DtoBase {

	/**
	 * 商品グループマスタID
	 */
	@Min(0)
	@ApiModelProperty(value = "商品グループマスタID", required = false, position = 3, allowableValues = "range[0,9223372036854775807]")
	private long productGrpMasterId;

	/**
	 * ライフサイクル状態
	 */
	@Column(nullable = false)
	@NotNull
	@ApiModelProperty(value = "ライフサイクル状態", required = true, allowableValues = "作成中(\"1\"), 作成完了(\"2\"), キャンセル手続き中(\"3\"), 破棄(\"4\"), 予定日待ち(\"5\"), 締結中(\"6\"), 解約手続き中(\"7\"), 解約予定日待ち(\"8\"), 解約(\"9\"), 旧契約(\"10\")", example = "1", position = 4)
	private LifecycleStatus lifecycleStatus;

	/**
	 * ワークフロー状態
	 */
	@Column(nullable = false)
	@NotNull
	@ApiModelProperty(value = "ワークフロー状態", required = true, allowableValues = "作成中(\"1\"), 承認依頼中(\"2\"), 承認済(\"3\"), 業務依頼中(\"4\"), 業務処理完了(\"5\"), キャンセル申請中(\"6\"), 売上可能(\"7\"), 解約申請中(\"8\")", example = "1", position = 5)
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
	@ApiModelProperty(value = "案件番号", required = false, position = 7, allowableValues = "range[0,255]")
	@Size(max = 255)
	private String caseNumber;

	/**
	 * 案件名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "案件名", required = false, position = 8, allowableValues = "range[0,255]")
	private String caseTitle;

	/**
	 * 契約番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "契約番号", required = true, position = 9, allowableValues = "range[0,255]")
	private String contractNumber;

	/**
	 * 契約件名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "契約件名", required = false, position = 10, allowableValues = "range[0,255]")
	private String contractTitle;

	/**
	 * 変更元契約番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "変更元契約番号", required = false, position = 11, allowableValues = "range[0,255]")
	private String originContractNumber;

	/**
	 * 変更元契約番号枝番
	 */
	@Max(99)
	@Min(0)
	@ApiModelProperty(value = "変更元契約番号枝番", required = false, position = 12, allowableValues = "range[0,99]")
	private Integer originContractBranchNumber;

	/**
	 * 変更元契約ID
	 */
	@Min(0)
	@ApiModelProperty(value = "変更元契約ID", required = false, position = 13, allowableValues = "range[0,9223372036854775807]")
	private Long originContractId;

	/**
	 * 変更希望日
	 */
	@ApiModelProperty(value = "変更希望日", required = false, position = 15)
	@Temporal(TemporalType.DATE)
	private Date changePreferredDate;

	/**
	 * 契約日
	 */
	@ApiModelProperty(value = "契約日", required = false, position = 16)
	@Temporal(TemporalType.DATE)
	private Date contractDate;

	/**
	 * 売上計上フラグ
	 */
	@Min(0)
	@Max(9)
	@ApiModelProperty(value = "売上計上フラグ", required = true, position = 17, allowableValues = "range[0,9]")
	private int accountSalesFlg;

	/**
	 * 請求開始日
	 */
	@ApiModelProperty(value = "請求開始日", required = false, position = 18)
	@Temporal(TemporalType.DATE)
	private Date billingDate;

	/**
	 * サービス開始日
	 */
	@ApiModelProperty(value = "サービス開始日", required = false, position = 19)
	@Temporal(TemporalType.DATE)
	private Date serviceTermStart;

	/**
	 * サービス終了日
	 */
	@ApiModelProperty(value = "サービス終了日", required = false, position = 20)
	@Temporal(TemporalType.DATE)
	private Date serviceTermEnd;

	/**
	 * 解約予定日
	 */
	@ApiModelProperty(value = "解約予定日", required = false, position = 21)
	@Temporal(TemporalType.DATE)
	private Date cancelScheduledDate;

	/**
	 * 見積番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "見積番号", required = false, position = 22, allowableValues = "range[0,255]")
	private String estimationNumber;

	/**
	 * 見積番号枝番
	 */
	@Max(99)
	@Min(0)
	@ApiModelProperty(value = "見積番号枝番", required = false, position = 23, allowableValues = "range[0,99]")
	private Integer estimationBranchNumber;

	/**
	 * 見積ID
	 */
	@Min(0)
	@ApiModelProperty(value = "見積ID", required = false, position = 24, allowableValues = "range[0,9223372036854775807]")
	private Long estimationId;

	/**
	 * 見積件名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "見積件名", required = false, position = 25, allowableValues = "range[0,255]")
	private String estimationTitle;

	/**
	 * 商流区分
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "商流区分", required = false, position = 26, allowableValues = "range[0,255]")
	private String commercialFlowDiv;

	/**
	 * 発行書式
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "発行書式", required = false, position = 27, allowableValues = "range[0,255]")
	private String issueFormat;

	/**
	 * 得意先コード
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "得意先コード<br/>※POST時「MoM請求売上先サイト情報マスタ」存在チェック実施", required = false, position = 28, allowableValues = "range[0,255]")
	private String billingCustomerSpCode;

	/**
	 * 得意先宛先名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "得意先宛先名", required = false, position = 29, allowableValues = "range[0,255]")
	private String billingCustomerSpName;

	/**
	 * 支払条件
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "支払条件", required = false, position = 30, allowableValues = "range[0,255]")
	private String paymentTerms;

	/**
	 * 支払方法
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "支払方法", required = false, position = 31, allowableValues = "range[0,255]")
	private String paymentMethod;

	/**
	 * 解約理由
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "解約理由", required = false, position = 32, allowableValues = "range[0,255]")
	private String cancelReason;

	/**
	 * その他解約理由
	 */
	@Size(max = 1000)
	@ApiModelProperty(value = "その他解約理由", required = false, position = 33, allowableValues = "range[0,1000]")
	private String cancelReasonEtc;

	/**
	 * 拡張項目
	 */
	@ApiModelProperty(value = "拡張項目", required = false, position = 34)
	@Lob
	private String extendsParameter;

	/**
	 * web受注注文番号
	 */
	@Size(max = 255)
	@NotNull
	@ApiModelProperty(value = "web受注注文番号", required = true, position = 35, allowableValues = "range[0,255]")
	private String webOrderNumber;

	/**
	 * RJ管理番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "RJ管理番号", required = false, position = 36, allowableValues = "range[0,255]")
	private String rjManageNumber;

	/**
	 * 課金開始日
	 */
	@ApiModelProperty(value = "課金開始日", required = false, position = 37)
	@Temporal(TemporalType.DATE)
	private Date billingStartDate;

	/**
	 * 解約注文番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "解約注文番号", required = false, position = 38, allowableValues = "range[0,255]")
	private String cancelOrderNo;

	/**
	 * 締結希望日
	 */
	@ApiModelProperty(value = "締結希望日", required = false, position = 39)
	@Temporal(TemporalType.DATE)
	private Date conclusionPreferredDate;

	/**
	 * IFS連携用CSV作成状態
	 */
	@ApiModelProperty(value = "IFS連携用CSV作成状態", required = false, allowableValues = "未作成(\"0\"), 作成済み(\"1\"), 作成対象外(\"2\"), 作成エラー(\"3\")", position = 40)
	private IfsLinkageCsvCreateStatus ifsLinkageCsvCreateStatus;

	/**
	 * IFS連携用CSV作成日
	 */
	@ApiModelProperty(value = "IFS連携用CSV作成日", required = false, position = 41)
	@Temporal(TemporalType.DATE)
	private Date ifsLinkageCsvCreateDate;

	/**
	 * 届先コード
	 */
	@Size(max = 255)
	@Column
	@ApiModelProperty(value = "届先コード", required = false, position = 42, allowableValues = "range[0,255]")
	private String deliveryCd;

	/**
	 * 届先名
	 */
	@Size(max = 255)
	@Column
	@ApiModelProperty(value = "届先名", required = false, position = 43, allowableValues = "range[0,255]")
	private String deliveryName;
	
	/**
	 * 設置届先サイトID
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "設置届先サイトID", required = false, position = 44, allowableValues = "range[0,255]")
	private String installDeliverySiteId;

	/**
	 * 契約明細
	 */
	@Valid
	@OneToMany(mappedBy = "contract")
	@ApiModelProperty(value = "契約明細", required = false, position = 45)
	private List<ContractDetailDto> contractDetailList;

	/**
	 * 契約担当SA社員
	 */
	@Valid
	@NotNull
	@OneToOne(mappedBy = "contract")
	@ApiModelProperty(value = "契約担当SA社員", required = true, position = 46)
	private ContractPicSaEmpDto contractPicSaEmp;

	/**
	 * 販売店(契約用)
	 */
	@Valid
	@OneToMany(mappedBy = "contract")
	@ApiModelProperty(value = "販売店(契約用)", required = false, position = 47)
	private List<DealerContractDto> dealerContractList;

	/**
	 * 顧客(契約用)
	 */
	@Valid
	@NotNull
	@OneToOne(mappedBy = "contract")
	@ApiModelProperty(value = "顧客(契約用)", required = true, position = 48)
	private CustomerContractDto customerContract;

	/**
	 * 商品(契約用)
	 */
	@Valid
	@OneToMany(mappedBy = "contract")
	@ApiModelProperty(value = "商品(契約用)", required = true, position = 49)
	private List<ProductContractExtCreateDto> productContractList;

	/**
	 * 見積明細管理
	 */
	@Valid
	@OneToMany(mappedBy = "contract")
	@ApiModelProperty(value = "見積明細管理", required = false, position = 50)
	private List<ManagedEstimationDetailDto> managedEstimationDetailList;

	/**
	 * 追加編集者
	 */
	@Valid
	@OneToMany(mappedBy = "contract")
	@ApiModelProperty(value = "追加編集者", required = false, position = 51)
	private List<ContractAddedEditorEmpDto> contractAddedEditorEmpList;
}
