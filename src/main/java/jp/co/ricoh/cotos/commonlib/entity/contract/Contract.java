package jp.co.ricoh.cotos.commonlib.entity.contract;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 契約鑑を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "contract")
@EntityListeners(ContractListener.class)
public class Contract extends EntityBase {

	public enum ContractType {

		新規("1"), 契約変更("2"), 情報変更("3");

		private final String text;

		private ContractType(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static ContractType fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	public enum LifecycleStatus {

		作成中("1"), 作成完了("2"), キャンセル手続き中("3"), 破棄("4"), 予定日待ち("5"), 締結中("6"), 解約手続き中("7"), 解約予定日待ち("8"), 解約("9"), 旧契約("10"), 締結待ち("11");

		private final String text;

		private LifecycleStatus(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static LifecycleStatus fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	public enum WorkflowStatus {

		作成中("1"), 承認依頼中("2"), 承認済("3"), 業務依頼中("4"), 業務処理完了("5"), キャンセル申請中("6"), 売上可能("7"), 解約申請中("8");

		private final String text;

		private WorkflowStatus(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static WorkflowStatus fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	public enum IfsLinkageCsvCreateStatus {

		未作成("0"), 作成済み("1"), 作成対象外("2"), 作成エラー("3");

		private final String text;

		private IfsLinkageCsvCreateStatus(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static IfsLinkageCsvCreateStatus fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	public enum SsWorkRequestCreateStatus {
		未作成("0"), 作成済み("1"), 作成エラー("2");

		private final String text;

		private SsWorkRequestCreateStatus(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static SsWorkRequestCreateStatus fromString(final String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contract_seq")
	@SequenceGenerator(name = "contract_seq", sequenceName = "contract_seq", allocationSize = 1)
	@ApiModelProperty(value = "契約ID(作成時不要)", required = true, position = 1, allowableValues = "range[0,9223372036854775807]", readOnly = true)
	private long id;

	/**
	 * 契約種別
	 */
	@Column(nullable = false)
	@NotNull
	@ApiModelProperty(value = "契約種別", required = true, position = 2)
	private ContractType contractType;

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
	@ApiModelProperty(value = "ライフサイクル状態(作成時不要)", required = true, allowableValues = "作成中(\"1\"), 作成完了(\"2\"), キャンセル手続き中(\"3\"), 破棄(\"4\"), 予定日待ち(\"5\"), 締結中(\"6\"), 解約手続き中(\"7\"), 解約予定日待ち(\"8\"), 解約(\"9\"), 旧契約(\"10\")", example = "1", position = 4, readOnly = true)
	private LifecycleStatus lifecycleStatus;

	/**
	 * ワークフロー状態
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "ワークフロー状態(作成時不要)", required = true, allowableValues = "作成中(\"1\"), 承認依頼中(\"2\"), 承認済(\"3\"), 業務依頼中(\"4\"), 業務処理完了(\"5\"), キャンセル申請中(\"6\"), 売上可能(\"7\"), 解約申請中(\"8\")", example = "1", position = 5, readOnly = true)
	private WorkflowStatus workflowStatus;

	/**
	 * 恒久契約識別番号
	 */
	@ApiModelProperty(value = "恒久契約識別番号(作成時不要)", required = false, position = 6, allowableValues = "range[0,255]", readOnly = true)
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
	@Column(nullable = false)
	@ApiModelProperty(value = "契約番号(作成時不要)", required = true, position = 9, allowableValues = "range[0,255]", readOnly = true)
	private String contractNumber;

	/**
	 * 契約番号枝番
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "契約番号枝番(作成時不要)", required = true, position = 10, allowableValues = "range[0,99]", readOnly = true)
	private int contractBranchNumber;

	/**
	 * 契約件名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "契約件名", required = false, position = 11, allowableValues = "range[0,255]")
	private String contractTitle;

	/**
	 * 変更元契約番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "変更元契約番号", required = false, position = 12, allowableValues = "range[0,255]")
	private String originContractNumber;

	/**
	 * 変更元契約番号枝番
	 */
	@Max(99)
	@Min(0)
	@ApiModelProperty(value = "変更元契約番号枝番", required = false, position = 13, allowableValues = "range[0,99]")
	private Integer originContractBranchNumber;

	/**
	 * 変更元契約ID
	 */
	@Min(0)
	@ApiModelProperty(value = "変更元契約ID", required = false, position = 14, allowableValues = "range[0,9223372036854775807]")
	private Long originContractId;

	/**
	 * 導入希望日
	 */
	@ApiModelProperty(value = "導入希望日", required = false, position = 15)
	@Temporal(TemporalType.DATE)
	private Date introductionPreferredDate;

	/**
	 * 変更希望日
	 */
	@ApiModelProperty(value = "変更希望日", required = false, position = 16)
	@Temporal(TemporalType.DATE)
	private Date changePreferredDate;

	/**
	 * 契約日
	 */
	@ApiModelProperty(value = "契約日", required = false, position = 17)
	@Temporal(TemporalType.DATE)
	private Date contractDate;

	/**
	 * 売上計上フラグ
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "売上計上フラグ(作成時不要)", required = true, position = 18, allowableValues = "range[0,9]", readOnly = true)
	private int accountSalesFlg;

	/**
	 * 請求開始日
	 */
	@ApiModelProperty(value = "請求開始日", required = false, position = 19)
	@Temporal(TemporalType.DATE)
	private Date billingDate;

	/**
	 * サービス開始日
	 */
	@ApiModelProperty(value = "サービス開始日", required = false, position = 20)
	@Temporal(TemporalType.DATE)
	private Date serviceTermStart;

	/**
	 * サービス終了日
	 */
	@ApiModelProperty(value = "サービス終了日", required = false, position = 21)
	@Temporal(TemporalType.DATE)
	private Date serviceTermEnd;

	/**
	 * 解約予定日
	 */
	@ApiModelProperty(value = "解約予定日", required = false, position = 22)
	@Temporal(TemporalType.DATE)
	private Date cancelScheduledDate;

	/**
	 * 見積番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "見積番号", required = false, position = 23, allowableValues = "range[0,255]")
	private String estimationNumber;

	/**
	 * 見積番号枝番
	 */
	@Max(99)
	@Min(0)
	@ApiModelProperty(value = "見積番号枝番", required = false, position = 24, allowableValues = "range[0,99]")
	private Integer estimationBranchNumber;

	/**
	 * 見積ID
	 */
	@Min(0)
	@ApiModelProperty(value = "見積ID", required = false, position = 25, allowableValues = "range[0,9223372036854775807]")
	private Long estimationId;

	/**
	 * 見積件名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "見積件名", required = false, position = 26, allowableValues = "range[0,255]")
	private String estimationTitle;

	/**
	 * 商流区分
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "商流区分", required = false, position = 27, allowableValues = "range[0,255]")
	private String commercialFlowDiv;

	/**
	 * 発行書式
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "発行書式", required = false, position = 28, allowableValues = "range[0,255]")
	private String issueFormat;

	/**
	 * 得意先コード
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "得意先コード<br/>※POST時「MoM請求売上先サイト情報マスタ」存在チェック実施", required = false, position = 29, allowableValues = "range[0,255]")
	private String billingCustomerSpCode;

	/**
	 * 得意先宛先名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "得意先宛先名", required = false, position = 30, allowableValues = "range[0,255]")
	private String billingCustomerSpName;

	/**
	 * 支払条件
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "支払条件", required = false, position = 31, allowableValues = "range[0,255]")
	private String paymentTerms;

	/**
	 * 支払方法
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "支払方法", required = false, position = 32, allowableValues = "range[0,255]")
	private String paymentMethod;

	/**
	 * 解約理由
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "解約理由", required = false, position = 33, allowableValues = "range[0,255]")
	private String cancelReason;

	/**
	 * その他解約理由
	 */
	@Size(max = 1000)
	@ApiModelProperty(value = "その他解約理由", required = false, position = 34, allowableValues = "range[0,1000]")
	private String cancelReasonEtc;

	/**
	 * 拡張項目
	 */
	@ApiModelProperty(value = "拡張項目", required = false, position = 35)
	@Lob
	private String extendsParameter;

	/**
	 * web受注注文番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "web受注注文番号", required = false, position = 36, allowableValues = "range[0,255]")
	private String webOrderNumber;

	/**
	 * RJ管理番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "RJ管理番号", required = false, position = 37, allowableValues = "range[0,255]")
	private String rjManageNumber;

	/**
	 * 課金開始日
	 */
	@ApiModelProperty(value = "課金開始日", required = false, position = 38)
	@Temporal(TemporalType.DATE)
	private Date billingStartDate;

	/**
	 * 解約注文番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "解約注文番号", required = false, position = 39, allowableValues = "range[0,255]")
	private String cancelOrderNo;

	/**
	 * 締結希望日
	 */
	@ApiModelProperty(value = "締結希望日", required = false, position = 40)
	@Temporal(TemporalType.DATE)
	private Date conclusionPreferredDate;

	/**
	 * IFS連携用CSV作成状態
	 */
	@ApiModelProperty(value = "IFS連携用CSV作成状態", required = false, position = 41)
	private IfsLinkageCsvCreateStatus ifsLinkageCsvCreateStatus;

	/**
	 * IFS連携用CSV作成日
	 */
	@ApiModelProperty(value = "IFS連携用CSV作成日", required = false, position = 42)
	@Temporal(TemporalType.DATE)
	private Date ifsLinkageCsvCreateDate;

	/**
	 * お問い合わせ番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "お問い合わせ番号", required = false, position = 43, allowableValues = "range[0,255]")
	private String contactNo;

	/**
	 * S&S作業依頼作成状態
	 */
	@ApiModelProperty(value = "S&S作業依頼作成状態", required = false, position = 44, allowableValues = "未作成(\"0\"),作成済み(\"1\"),作成エラー(\"2\")")
	private SsWorkRequestCreateStatus ssWorkRequestCreateStatus;

	/**
	 * 帳票用消費税率区分
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "帳票用消費税率区分", required = false, position = 45, allowableValues = "range[0,255]")
	private String issueTaxCodeValue;

	/**
	 * 契約明細
	 */
	@Valid
	@OneToMany(mappedBy = "contract")
	@ApiModelProperty(value = "契約明細", required = true, position = 46)
	private List<ContractDetail> contractDetailList;

	/**
	 * 契約チェック結果
	 */
	@OneToMany(mappedBy = "contract")
	@OrderBy("displayOrder ASC")
	@ApiModelProperty(value = "契約チェック結果(作成時不要)", required = false, position = 47, readOnly = true)
	private List<ContractCheckResult> contractCheckResultList;

	/**
	 * 契約承認ルート
	 */
	@OneToMany(mappedBy = "contract")
	@ApiModelProperty(value = "契約承認ルート(作成時不要)", required = false, position = 48, readOnly = true)
	private List<ContractApprovalRoute> contractApprovalRouteList;

	/**
	 * 契約添付ファイル
	 */
	@OneToMany(mappedBy = "contract")
	@ApiModelProperty(value = "契約添付ファイル(作成時不要)", required = false, position = 49, readOnly = true)
	private List<ContractAttachedFile> contractAttachedFileList;

	/**
	 * 契約担当SA社員
	 */
	@Valid
	@OneToOne(mappedBy = "contract")
	@ApiModelProperty(value = "契約担当SA社員", required = true, position = 50)
	private ContractPicSaEmp contractPicSaEmp;

	/**
	 * 契約追加編集者社員
	 */
	@OneToMany(mappedBy = "contract")
	@ApiModelProperty(value = "契約追加編集者社員(作成時不要)", required = false, position = 51, readOnly = true)
	private List<ContractAddedEditorEmp> contractAddedEditorEmpList;

	/**
	 * 販売店(契約用)
	 */
	@Valid
	@OneToMany(mappedBy = "contract")
	@ApiModelProperty(value = "販売店(契約用)", required = false, position = 52)
	private List<DealerContract> dealerContractList;

	/**
	 * 顧客(契約用)
	 */
	@Valid
	@OneToOne(mappedBy = "contract")
	@ApiModelProperty(value = "顧客(契約用)", required = true, position = 53)
	private CustomerContract customerContract;

	/**
	 * 契約操作履歴
	 */
	@OneToMany(mappedBy = "contract")
	@OrderBy("operatedAt ASC")
	@ApiModelProperty(value = "契約操作履歴(作成時不要)", required = true, position = 54, readOnly = true)
	private List<ContractOperationLog> contractOperationLogList;

	/**
	 * 商品(契約用)
	 */
	@Valid
	@OneToMany(mappedBy = "contract")
	@ApiModelProperty(value = "商品(契約用)", required = true, position = 55)
	private List<ProductContract> productContractList;

	/**
	 * 契約保守担当CE社員
	 */
	@Valid
	@OneToOne(mappedBy = "contract")
	@ApiModelProperty(value = "契約担当CE社員(作成時不要)", required = false, position = 56, readOnly = true)
	private ContractPicMntCeEmp contractPicMntCeEmp;

	/**
	 * 契約保守担当SS組織
	 */
	@Valid
	@OneToOne(mappedBy = "contract")
	@ApiModelProperty(value = "契約保守担当SS組織(作成時不要)", required = false, position = 57, readOnly = true)
	private ContractPicMntSsOrg contractPicMntSsOrg;

	/**
	 * 契約受付担当SS組織
	 */
	@Valid
	@OneToOne(mappedBy = "contract")
	@ApiModelProperty(value = "契約受付担当SS組織(作成時不要)", required = false, position = 58, readOnly = true)
	private ContractPicAccSsOrg contractPicAccSsOrg;

	/**
	 * 契約導入担当SS組織
	 */
	@Valid
	@OneToOne(mappedBy = "contract")
	@ApiModelProperty(value = "契約導入担当SS組織(作成時不要)", required = false, position = 59, readOnly = true)
	private ContractPicIntSsOrg contractPicIntSsOrg;

	/**
	 * 契約添付ファイル履歴
	 */
	@OneToMany(mappedBy = "contract")
	@ApiModelProperty(value = "契約添付ファイル履歴(作成時不要)", required = true, position = 60)
	private List<ContractAttachedFileHistory> contractAttachedFileHistoryList;

	/**
	 * 契約受付担当CE社員
	 */
	@Valid
	@OneToOne(mappedBy = "contract")
	@ApiModelProperty(value = "契約受付担当CE社員(作成時不要)", required = false, position = 61, readOnly = true)
	private ContractPicAccCeEmp contractPicAccCeEmp;

	/**
	 * 契約導入担当CE社員
	 */
	@Valid
	@OneToOne(mappedBy = "contract")
	@ApiModelProperty(value = "契約導入担当CE社員(作成時不要)", required = false, position = 62, readOnly = true)
	private ContractPicIntCeEmp contractPicIntCeEmp;

	/**
	 * 契約機種
	 */
	@Valid
	@OneToMany(mappedBy = "contract")
	@ApiModelProperty(value = "契約機種(作成時不要)", required = false, position = 63, readOnly = true)
	private List<ContractEquipment> contarctEquipmentList;

	/**
	 * 見積明細管理
	 */
	@Valid
	@OneToMany(mappedBy = "contract")
	@ApiModelProperty(value = "見積明細管理", required = true, position = 64)
	private List<ManagedEstimationDetail> managedEstimationDetailList;

	/**
	 * 設置先(契約用)
	 */
	@Valid
	@OneToOne(mappedBy = "contract")
	@ApiModelProperty(value = "設置先(契約用)", required = true, position = 65)
	private ContractInstallationLocation contractInstallationLocation;

	/**
	 * アプリケーションID
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "アプリケーションID", required = false, position = 67, allowableValues = "range[0,255]")
	private String appId;
}
