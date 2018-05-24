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

/**
 * 見積を表すEntityです。
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@EntityListeners(EstimationListener.class)
@Data
@Table(name = "estimation")
public class Estimation extends EntityBase {

	public enum Status {

		作成中, 承認依頼中, 承認済み, 受注, 失注;

		@JsonValue
		public String toValue() {
			return this.name();
		}

		@JsonCreator
		public static Enum<Status> fromValue(String name) {
			return Arrays.stream(values()).filter(v -> v.name() == name).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(name)));
		}
	}

	public enum EstimationDiv {

		新規, 変更;

		@JsonValue
		public String toValue() {
			return this.name();
		}

		@JsonCreator
		public static Enum<EstimationDiv> fromValue(String name) {
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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estimation_seq")
	@SequenceGenerator(name = "estimation_seq", sequenceName = "estimation_seq", allocationSize = 1)
	@ApiModelProperty(value = "見積ID", required = true, position = 1)
	private long id;

	/**
	 * 見積番号 numberはOracleの予約後だったのでestimateつけた。
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "見積番号", required = true, position = 2, allowableValues = "range[0,18]")
	private String estimateNumber;

	/**
	 * 見積件名
	 */
	@ApiModelProperty(value = "見積件名", required = false, position = 3, allowableValues = "range[0,255]")
	private String estimationTitle;

	/**
	 * ステータス
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = "見積ステータス", required = false, position = 4)
	private Status status;

	/**
	 * 見積種別
	 */
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = "見積種別", required = false, position = 5)
	private EstimationDiv estimationDiv;

	/**
	 * 商品マスタ
	 */
	@ManyToOne
	@JoinColumn(name = "productId")
	@ApiModelProperty(value = "商品マスタ", required = false, position = 6)
	private Product product;

	/**
	 * 案件番号
	 */
	@ApiModelProperty(value = "案件番号", required = false, position = 7, allowableValues = "range[0,255]")
	private String caseNumber;

	/**
	 * 案件件名
	 */
	@ApiModelProperty(value = "案件件名", required = false, position = 8, allowableValues = "range[0,255]")
	private String caseTitle;

	/**
	 * 変更元契約番号
	 */
	@ApiModelProperty(value = "変更元契約番号", required = false, position = 9, allowableValues = "range[0,18]")
	private String originContractNumber;

	/**
	 * 変更元契約ID
	 */
	@ApiModelProperty(value = "変更元契約ID", required = false, position = 10)
	private Long originContractId;

	/**
	 * サービス識別番号
	 */
	@ApiModelProperty(value = "サービス識別番号", required = false, position = 11, allowableValues = "range[0,18]")
	private String serviceIdentificationNumber;

	/**
	 * 商流区分
	 */
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = "商流区分", required = false, position = 12)
	private CommercialFlowDiv commercialFlowDiv;

	/**
	 * 発行書式
	 */
	@ApiModelProperty(value = "発行書式", required = false, position = 13, allowableValues = "range[0,255]")
	private String issueFormat;

	/**
	 * 帳票用見積件名
	 */
	@ApiModelProperty(value = "帳票用見積件名", required = false, position = 14, allowableValues = "range[0,255]")
	private String issueEstimationTitle;

	/**
	 * 帳票用顧客企業名
	 */
	@ApiModelProperty(value = "帳票用顧客企業名", required = false, position = 15, allowableValues = "range[0,255]")
	private String issueCustomerCorpName;

	/**
	 * 見積鑑用企業名
	 */
	@ApiModelProperty(value = "見積鑑用企業名", required = false, position = 16, allowableValues = "range[0,255]")
	private String coverCompanyName;

	/**
	 * 見積鑑用敬称
	 */
	@ApiModelProperty(value = "見積鑑用敬称", required = false, position = 17, allowableValues = "range[0,255]")
	private String coverTitle;

	/**
	 * 見積鑑用見積件名
	 */
	@ApiModelProperty(value = "見積鑑用見積件名", required = false, position = 18, allowableValues = "range[0,255]")
	private String coverEstimationSubject;

	/**
	 * 見積鑑用納期
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "見積鑑用納期", required = false, position = 19)
	private Date coverDeliveryDate;

	/**
	 * 見積鑑用支払条件
	 */
	@ApiModelProperty(value = "見積鑑用支払条件", required = false, position = 20, allowableValues = "range[0,255]")
	private String coverPaymentTerms;

	/**
	 * 見積鑑用有効期限
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "見積鑑用有効期限", required = false, position = 21)
	private Date coverExpirationDate;

	/**
	 * 見積鑑用備考
	 */
	@ApiModelProperty(value = "見積鑑用備考", required = false, position = 22, allowableValues = "range[0,255]")
	private String coverRemarks;

	/**
	 * 見積鑑用見積掲示日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "見積鑑用見積掲示日", required = false, position = 23)
	private Date coverPresentationDate;

	/**
	 * 見積発行元会社名
	 */
	@ApiModelProperty(value = "見積発行元会社名", required = false, position = 24, allowableValues = "range[0,255]")
	private String publishCompany;

	/**
	 * 見積発行元所属
	 */
	@ApiModelProperty(value = "見積発行元所属", required = false, position = 25, allowableValues = "range[0,255]")
	private String publishDepartment;

	/**
	 * 見積発行元郵便番号
	 */
	@ApiModelProperty(value = "見積発行元郵便番号", required = false, position = 26, allowableValues = "range[0,255]")
	private String publishPostNumber;

	/**
	 * 見積発行元住所
	 */
	@ApiModelProperty(value = "見積発行元住所", required = false, position = 27, allowableValues = "range[0,255]")
	private String publishAddress;

	/**
	 * 見積発行元電話番号
	 */
	@ApiModelProperty(value = "見積発行元電話番号", required = false, position = 28, allowableValues = "range[0,255]")
	private String publishTel;

	/**
	 * 見積発行元FAX番号
	 */
	@ApiModelProperty(value = "見積発行元FAX番号", required = false, position = 29, allowableValues = "range[0,255]")
	private String publishFax;

	/**
	 * 見積発行元担当者名
	 */
	@ApiModelProperty(value = "見積発行元担当者名", required = false, position = 30, allowableValues = "range[0,255]")
	private String publishEmployee;

	/**
	 * 特価希望理由
	 */
	@ApiModelProperty(value = "特価希望理由", required = false, position = 31, allowableValues = "range[0,255]")
	private String spPriceApplyReason;

	/**
	 * 特価希望理由テキスト
	 */
	@ApiModelProperty(value = "特価希望理由テキスト", required = false, position = 32, allowableValues = "range[0,255]")
	private String spPriceApplyReasonText;

	/**
	 * 主競合先名称
	 */
	@ApiModelProperty(value = "主競合先名称", required = false, position = 33, allowableValues = "range[0,255]")
	private String mainCompetitorName;

	/**
	 * 競合情報
	 */
	@ApiModelProperty(value = "競合情報", required = false, position = 34, allowableValues = "range[0,255]")
	private String competitionInfo;

	/**
	 * 競合先契約種別
	 */
	@ApiModelProperty(value = "競合先契約種別", required = false, position = 35, allowableValues = "range[0,255]")
	private String competitionContractDiv;

	/**
	 * 競合先基本料金
	 */
	@ApiModelProperty(value = "競合先基本料金", required = false, position = 36)
	private Long competitionAmount;

	/**
	 * 拡張項目
	 */
	@ApiModelProperty(value = "拡張項目", required = false, position = 37, allowableValues = "range[0,255]")
	private String extendsParameter;

	/**
	 * 顧客情報
	 */
	@OneToOne(mappedBy = "estimation")
	@ApiModelProperty(value = "顧客情報", required = false, position = 38)
	private Customer customer;

	/**
	 * 販売店情報
	 */
	@OneToMany(mappedBy = "estimation")
	@ApiModelProperty(value = "販売店情報", required = false, position = 39)
	private List<Dealer> dealer;

	/**
	 * 担当営業社員
	 */
	@ManyToMany
	@JoinTable(name = "estimationEmptx", joinColumns = @JoinColumn(name = "estimationId", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "employeeId", referencedColumnName = "id"))
	@ApiModelProperty(value = "担当営業社員", required = false, position = 40)
	private List<Employee> estimationEmptxList;

	/**
	 * 承認情報
	 */
	@OneToOne(mappedBy = "estimation")
	@ApiModelProperty(value = "承認情報", required = false, position = 41)
	private EstimationApprovalRoute estimationApprovalRoute;

	/**
	 * 見積明細
	 */
	@OneToMany(mappedBy = "estimation")
	@ApiModelProperty(value = "見積明細", required = false, position = 42)
	private List<EstimationDetail> details;

	/**
	 * 追加編集者
	 */
	@ManyToMany
	@JoinTable(name = "estimationAddedEdit", joinColumns = @JoinColumn(name = "estimationId", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "addedEditorEmptxId", referencedColumnName = "id"))
	@ApiModelProperty(value = "追加編集者", required = false, position = 43)
	private List<Employee> estimationAddedEditList;

	/**
	 * 見積操作履歴
	 */
	@OneToMany(mappedBy = "estimation")
	@ApiModelProperty(value = "見積操作履歴", required = false, position = 44)
	private List<OperationLog> operationLog;
}
