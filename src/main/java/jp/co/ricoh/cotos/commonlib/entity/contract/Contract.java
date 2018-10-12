package jp.co.ricoh.cotos.commonlib.entity.contract;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.master.ProductMaster;
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

		新規("1"), プラン変更("2"), 情報変更("3");

		private final String text;

		private ContractType(final String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return this.text;
		}

		public static ContractType fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	public enum LifecycleStatus {

		作成中("1"), 作成完了("2"), キャンセル手続き中("3"), 破棄("4"), 予定日待ち("5"), 締結中("6"), 解約手続き中("7"), 解約予定日待ち("8"), 解約("9"), 旧契約("10");

		private final String text;

		private LifecycleStatus(final String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return this.text;
		}

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
		public String toString() {
			return this.text;
		}

		public static WorkflowStatus fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	@Id
	@ApiModelProperty(value = "契約ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 契約種別
	 */
	@ApiModelProperty(value = "契約種別", required = true, position = 2)
	private ContractType contractType;

	/**
	 * ライフサイクル状態
	 */
	@ApiModelProperty(value = "ライフサイクル状態", required = true, position = 3)
	private LifecycleStatus lifecycleStatus;

	/**
	 * ワークフロー状態
	 */
	@ApiModelProperty(value = "ワークフロー状態", required = true, position = 4)
	private WorkflowStatus workflowStatus;

	/**
	 * 商品マスタ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "product_master_id", referencedColumnName = "id")
	@ApiModelProperty(value = "商品マスタ", required = true, position = 5)
	private ProductMaster productMaster;

	/**
	 * 案件番号
	 */
	@ApiModelProperty(value = "案件番号", required = false, position = 6, allowableValues = "range[0,255]")
	private String caseNumber;

	/**
	 * 案件名
	 */
	@ApiModelProperty(value = "案件名", required = false, position = 7, allowableValues = "range[0,255]")
	private String caseTitle;

	/**
	 * 契約番号
	 */
	@ApiModelProperty(value = "契約番号", required = true, position = 8, allowableValues = "range[0,255]", readOnly = true)
	@Pattern(regexp = "CAYYYYMMDDNNNNN")
	private String contractNumber;

	/**
	 * 契約番号枝番
	 */
	@ApiModelProperty(value = "契約番号枝番", required = true, position = 9, allowableValues = "range[0,99]", readOnly = true)
	private int contractBranchNumber;

	/**
	 * 契約件名
	 */
	@ApiModelProperty(value = "契約件名", required = false, position = 10, allowableValues = "range[0,255]")
	private String contractTitle;

	/**
	 * 変更元契約番号
	 */
	@ApiModelProperty(value = "変更元契約番号", required = false, position = 11, allowableValues = "range[0,255]")
	private String originContractNumber;

	/**
	 * 変更元契約番号枝番
	 */
	@ApiModelProperty(value = "変更元契約番号枝番", required = false, position = 12, allowableValues = "range[0,99]")
	private String originContractBranchNumber;

	/**
	 * 変更元契約ID
	 */
	@ApiModelProperty(value = "変更元契約ID", required = false, position = 13, allowableValues = "range[0,9999999999999999999]")
	private Long originContractId;

	/**
	 * サービス識別番号
	 */
	@ApiModelProperty(value = "サービス識別番号", required = false, position = 14, allowableValues = "range[0,15]")
	@Pattern(regexp = "CSYYYYMMDDNNNNN")
	private String serviceIdentificationNumber;

	/**
	 * 導入希望日
	 */
	@ApiModelProperty(value = "導入希望日", required = false, position = 15)
	private Date introductionPreferredDate;

	/**
	 * 変更希望日
	 */
	@ApiModelProperty(value = "変更希望日", required = false, position = 16)
	private Date changePreferredDate;

	/**
	 * 契約日
	 */
	@ApiModelProperty(value = "契約日", required = false, position = 17)
	private Date contractDate;

	/**
	 * 売上計上フラグ
	 */
	@ApiModelProperty(value = "売上計上フラグ", required = true, position = 18)
	private int accountSalesFlag;

	/**
	 * 請求開始日
	 */
	@ApiModelProperty(value = "請求開始日", required = false, position = 18)
	private Date billingDate;

	/**
	 * サービス開始日
	 */
	@ApiModelProperty(value = "サービス開始日", required = false, position = 19)
	private Date serviceTermStart;

	/**
	 * サービス終了日
	 */
	@ApiModelProperty(value = "サービス終了日", required = false, position = 20)
	private Date serviceTermEnd;

	/**
	 * 解約予定日
	 */
	@ApiModelProperty(value = "解約予定日", required = false, position = 21)
	private Date cancelScheduledDate;

	/**
	 * 見積番号
	 */
	@ApiModelProperty(value = "見積番号", required = true, position = 22, allowableValues = "range[0,18]")
	private String estimateNumber;

	/**
	 * 見積件名
	 */
	@ApiModelProperty(value = "見積件名", required = false, position = 23, allowableValues = "range[0,255]")
	private String estimationTitle;

	/**
	 * 商流区分
	 */
	@ApiModelProperty(value = "商流区分", required = false, position = 24, allowableValues = "range[0,255]")
	private String commercialFlowDiv;

	/**
	 * 発行書式
	 */
	@ApiModelProperty(value = "発行書式", required = false, position = 25, allowableValues = "range[0,255]")
	private String issueFormat;

	/**
	 * 得意先コード
	 */
	@ApiModelProperty(value = "得意先コード", required = false, position = 26, allowableValues = "range[0,255]")
	private String issueEstimationTitle;

	/**
	 * 得意先宛先名
	 */
	@ApiModelProperty(value = "得意先宛先名", required = false, position = 27, allowableValues = "range[0,255]")
	private String issueCustomerCorpName;

	/**
	 * 拡張項目
	 */
	@ApiModelProperty(value = "拡張項目", required = false, position = 28)
	private String extendsParameter;

	/**
	 * 契約明細
	 */
	@OneToMany(mappedBy = "contract")
	@ApiModelProperty(value = "契約明細", required = true, position = 29)
	private List<ContractDetail> detailList;

	/**
	 * 契約チェック結果
	 */
	@OneToMany(mappedBy = "contract")
	@ApiModelProperty(value = "契約チェック結果", required = false, position = 30)
	private List<ContractCheckResult> chechResultList;

	/**
	 * 契約承認ルート
	 */
	@OneToMany(mappedBy = "contract")
	@ApiModelProperty(value = "契約承認ルート", required = false, position = 31)
	private List<ContractApprovalRoute> approvalRouteList;

	/**
	 * 契約添付ファイル
	 */
	@OneToMany(mappedBy = "contract")
	@ApiModelProperty(value = "契約添付ファイル", required = false, position = 32)
	private List<ContractAttachedFile> attachedFileList;

	/**
	 * 契約担当SA社員
	 */
	@OneToOne(mappedBy = "contract")
	@ApiModelProperty(value = "契約担当SA社員", required = true, position = 33)
	private ContractPicSaEmp picSaEmpList;

	/**
	 * 契約追加編集者社員
	 */
	@OneToMany(mappedBy = "contract")
	@ApiModelProperty(value = "契約追加編集者社員", required = false, position = 34)
	private List<ContractAddedEditorEmp> addedEditorEmpList;

	/**
	 * 販売店(契約用)
	 */
	@OneToMany(mappedBy = "contract")
	@ApiModelProperty(value = "販売店(契約用)", required = false, position = 35)
	private List<DealerContract> dealerContractList;

	/**
	 * 顧客(契約用)
	 */
	@OneToOne(mappedBy = "contract")
	@ApiModelProperty(value = "顧客(契約用)", required = true, position = 36)
	private CustomerContract contractCustomer;

	/**
	 * 契約操作履歴
	 */
	@OneToMany(mappedBy = "contract")
	@ApiModelProperty(value = "契約操作履歴", required = true, position = 37, readOnly = true)
	private List<ContractOperationLog> operationLogList;
}
