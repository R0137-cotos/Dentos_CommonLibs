package jp.co.ricoh.cotos.commonlib.common.entity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.common.master.Product;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "contract")
@EntityListeners(ContractListener.class)
public class Contract extends EntityBase {

	public enum ContractStatus {

		作成中, 承認依頼中, 承認済み;

		@JsonValue
		public String toValue() {
			return this.name();
		}

		@JsonCreator
		public static Enum<ContractStatus> fromValue(String name) {
			return Arrays.stream(values()).filter(v -> v.name() == name).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(name)));
		}
	}

	public enum ContractType {

		新規, プラン変更, 情報変更, 解約;

		@JsonValue
		public String toValue() {
			return this.name();
		}

		@JsonCreator
		public static Enum<ContractType> fromValue(String name) {
			return Arrays.stream(values()).filter(v -> v.name() == name).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(name)));
		}
	}

	public enum CommercialFlowDiv {

		直売, 代売_接点店, 代売_母店_接点店;

		@JsonValue
		public String toValue() {
			return this.name();
		}

		@JsonCreator
		public static Enum<CommercialFlowDiv> fromValue(String name) {
			return Arrays.stream(values()).filter(v -> v.name() == name).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(name)));
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contract_seq")
	@SequenceGenerator(name = "contract_seq", sequenceName = "contract_seq", allocationSize = 1)
	@ApiModelProperty(value = "契約ID", required = true, position = 1)
	private long id;

	/**
	 * 契約種別
	 */
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = "契約種別", required = false, position = 2)
	private ContractType contractType;

	/**
	 * ステータス
	 */
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	@ApiModelProperty(value = "契約ステータス", required = false, position = 3)
	private ContractStatus contractStatus;

	/**
	 * 商品マスタ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "productId")
	@ApiModelProperty(value = "商品マスタ", required = false, position = 4)
	private Product product;

	/**
	 * 案件番号
	 */
	@ApiModelProperty(value = "案件番号", required = false, position = 5, allowableValues = "range[0,255]")
	private String caseNumber;

	/**
	 * 案件件名
	 */
	@ApiModelProperty(value = "案件件名", required = false, position = 6, allowableValues = "range[0,255]")
	private String caseTitle;

	/**
	 * 契約番号
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "契約番号", required = true, position = 7, allowableValues = "range[0,18]")
	private String contractNumber;

	/**
	 * 変更元契約番号
	 */
	@ApiModelProperty(value = "変更元契約番号", required = false, position = 8, allowableValues = "range[0,18]")
	private String originContractNumber;

	/**
	 * 変更元契約ID
	 */
	@ApiModelProperty(value = "変更元契約ID", required = false, position = 9)
	private Long originContractId;

	/**
	 * サービス識別番号
	 */
	@ApiModelProperty(value = "サービス識別番号", required = false, position = 10, allowableValues = "range[0,18]")
	private String serviceIdentificationNumber;

	/**
	 * 契約日
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "契約日", required = false, position = 11)
	private Date contractDate;

	/**
	 * 請求開始月
	 */
	@ApiModelProperty(value = "請求開始月", required = false, position = 12, allowableValues = "range[0,255]")
	private String billingMonth;

	/**
	 * サービス開始日
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "サービス開始日", required = false, position = 13)
	private Date serviceTermStart;

	/**
	 * サービス終了日
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "サービス終了日", required = false, position = 14)
	private Date serviceTermEnd;

	/**
	 * 解約予定日
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "解約予定日", required = false, position = 15)
	private Date cancelScheduledDate;

	/**
	 * 作成元見積ID
	 */
	@ApiModelProperty(value = "作成元見積ID", required = false, position = 16)
	private long originEstimationId;

	/**
	 * 見積番号
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "見積番号", required = true, position = 17, allowableValues = "range[0,18]")
	private String estimateNumber;

	/**
	 * 見積件名
	 */
	@ApiModelProperty(value = "見積件名", required = false, position = 18, allowableValues = "range[0,255]")
	private String estimationTitle;

	/**
	 * 商流区分
	 */
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = "商流区分", required = false, position = 19)
	private CommercialFlowDiv commercialFlowDiv;

	/**
	 * 担当営業社員
	 */
	@ManyToMany
	@JoinTable(name = "contractEmptx", joinColumns = @JoinColumn(name = "contractId", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "employeeId", referencedColumnName = "id"))
	@ApiModelProperty(value = "担当営業社員", required = false, position = 20)
	private List<EmployeeCon> contractEmptxList;

	/**
	 * 発行書式
	 */
	@ApiModelProperty(value = "発行書式", required = false, position = 21, allowableValues = "range[0,255]")
	private String issueFormat;

	/**
	 * 得意先コード
	 */
	@ApiModelProperty(value = "得意先コード", required = false, position = 22, allowableValues = "range[0,255]")
	private String billingCustomerSpCode;

	/**
	 * 得意先名
	 */
	@ApiModelProperty(value = "得意先名", required = false, position = 23, allowableValues = "range[0,255]")
	private String billingCustomerSpName;

	/**
	 * 契約用見積掲示日
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "契約用見積掲示日", required = false, position = 24)
	private Date estimationPresentationDate;

	/**
	 * 拡張項目
	 */
	@ApiModelProperty(value = "拡張項目", required = false, position = 25, allowableValues = "range[0,255]")
	private String extendsParameter;

	/**
	 * 顧客情報
	 */
	@OneToOne(mappedBy = "contract")
	@ApiModelProperty(value = "顧客情報", required = false, position = 26)
	private CustomerCon customerCon;

	/**
	 * 販売店情報
	 */
	@OneToMany(mappedBy = "contract")
	@ApiModelProperty(value = "販売店情報", required = false, position = 27)
	private List<DealerCon> dealerCon;

	/**
	 * 契約明細
	 */
	@OneToMany(mappedBy = "contract")
	@ApiModelProperty(value = "契約明細情報", required = false, position = 28)
	private List<ContractDetail> details;

	/**
	 * 承認情報
	 */
	@OneToOne(mappedBy = "contract")
	@ApiModelProperty(value = "承認情報", required = false, position = 29)
	private ContractApprovalRoute contractApprovalRoute;

	/**
	 * 契約操作履歴情報
	 */
	@OneToMany(mappedBy = "contract")
	@ApiModelProperty(value = "操作履歴情報", required = false, position = 30)
	private List<OperationLogCon> operationLogCon;

	/**
	 * 契約追加編集者
	 */
	@ManyToMany
	@JoinTable(name = "contractAddedEdit", joinColumns = @JoinColumn(name = "contractId", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "addedEditorEmptxId", referencedColumnName = "id"))
	@ApiModelProperty(value = "追加編集者情報", required = false, position = 31)
	private List<EmployeeCon> contractAddedEditList;

}
