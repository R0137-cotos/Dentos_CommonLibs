package jp.co.ricoh.cotos.commonlib.entity.estimation;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.master.Product;
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

	public enum lifecycleStatus {

		作成中, 作成完了, 受注, 破棄, 失注;

		@JsonValue
		public String toValue() {
			return this.name();
		}

		@JsonCreator
		public static Enum<lifecycleStatus> fromValue(String name) {
			return Arrays.stream(values()).filter(v -> v.name() == name).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(name)));
		}
	}

	public enum workflowStatus {

		作成中, 業務依頼中, 業務処理完了, 承認中, 承認済, 顧客提示済;

		@JsonValue
		public String toValue() {
			return this.name();
		}

		@JsonCreator
		public static Enum<workflowStatus> fromValue(String name) {
			return Arrays.stream(values()).filter(v -> v.name() == name).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(name)));
		}
	}

	public enum EstimationDiv {

		新規, 再見積, プラン変更;

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

		直売, 代売_接点店, 代売_母店_接点店;// TODO ERD、汎用コード値資料に記載がないため正しいか確認

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
	@ApiModelProperty(value = "見積ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * ライフサイクル状態
	 */
	@ApiModelProperty(value = "ライフサイクル状態", required = true, position = 2)
	private lifecycleStatus lifecycleStatus;

	/**
	 * ワークフロー状態
	 */
	@ApiModelProperty(value = "ワークフロー状態", required = true, position = 3)
	private workflowStatus workflowStatus;

	/**
	 * 商品マスタ
	 */
	@ManyToOne
	@JoinColumn(name = "product_id")
	@ApiModelProperty(value = "商品マスタ", required = false, position = 4)
	private Product productMaster;

	/**
	 * 案件番号
	 */
	@ApiModelProperty(value = "案件番号", required = true, position = 5, allowableValues = "range[0,255]")
	private String caseNumber;

	/**
	 * 案件名
	 */
	@ApiModelProperty(value = "案件名", required = true, position = 6, allowableValues = "range[0,255]")
	private String caseTitle;

	/**
	 * 見積番号
	 */
	@ApiModelProperty(value = "見積番号", required = true, position = 7, allowableValues = "range[0,255]")
	private String estimateNumber;

	/**
	 * 見積番号枝番
	 */
	@ApiModelProperty(value = "見積番号枝番", required = true, position = 8, allowableValues = "range[0,999]")
	private long estimateBranchNumber;

	/**
	 * 見積件名
	 */
	@ApiModelProperty(value = "見積件名", required = true, position = 9, allowableValues = "range[0,255]")
	private String estimationTitle;

	/**
	 * 見積種別
	 */
	@ApiModelProperty(value = "見積種別", required = true, position = 10)
	private EstimationDiv estimationType;

	/**
	 * 見積作成元システム区分
	 */
	// TODO 区分値不明
	@ApiModelProperty(value = "見積作成元システム区分", required = true, position = 11)
	private String estimatedSystemDiv;

	/**
	 * サービス識別番号
	 */
	@ApiModelProperty(value = "サービス識別番号", required = true, position = 12, allowableValues = "range[0,255]")
	private String serviceIdentificationNumber;

	/**
	 * 変更元契約番号
	 */
	@ApiModelProperty(value = "変更元契約番号", required = true, position = 13, allowableValues = "range[0,255]")
	private String originContractNumber;

	/**
	 * 変更元契約番号枝番
	 */
	@ApiModelProperty(value = "変更元契約番号枝番", required = true, position = 14, allowableValues = "range[0,999]")
	private long originContractBranchNumber;

	/**
	 * 変更元契約ID
	 */
	@ApiModelProperty(value = "変更元契約ID", required = true, position = 15, allowableValues = "range[0,9999999999999999999]")
	private long originContractId;

	/**
	 * 商流区分
	 */
	@ApiModelProperty(value = "商流区分", required = true, position = 16)
	private CommercialFlowDiv commercialFlowDiv;

	/**
	 * 発行書式
	 */
	@ApiModelProperty(value = "発行書式", required = true, position = 17, allowableValues = "range[0,255]")
	private String issueFormat;

	/**
	 * 帳票用見積件名
	 */
	@ApiModelProperty(value = "帳票用見積件名", required = true, position = 18, allowableValues = "range[0,255]")
	private String issueEstimationTitle;

	/**
	 * 帳票用顧客企業名
	 */
	@ApiModelProperty(value = "帳票用顧客企業名", required = true, position = 19, allowableValues = "range[0,255]")
	private String issueCustomerCorpName;

	/**
	 * 見積有効期限
	 */
	@ApiModelProperty(value = "見積有効期限", required = true, position = 20)
	private Date estimationLimit;

	/**
	 * 見積鑑用企業名
	 */
	@ApiModelProperty(value = "見積鑑用企業名", required = true, position = 21, allowableValues = "range[0,255]")
	private String coverCompanyName;

	/**
	 * 見積鑑用敬称
	 */
	@ApiModelProperty(value = "見積鑑用敬称", required = true, position = 22, allowableValues = "range[0,255]")
	private String coverTitle;

	/**
	 * 見積鑑用見積件名
	 */
	@ApiModelProperty(value = "見積鑑用見積件名", required = true, position = 23, allowableValues = "range[0,255]")
	private String coverEstimationSubject;

	/** 見積鑑用支払条件 */
	@ApiModelProperty(value = "見積鑑用支払条件", required = true, position = 24, allowableValues = "range[0,255]")
	private String coverPaymentTerms;

	/** 見積鑑用納期 */
	@ApiModelProperty(value = "見積鑑用納期", required = true, position = 25)
	private Date coverDeliveryDate;

	/** 見積鑑用有効期限 */
	@ApiModelProperty(value = "見積鑑用有効期限", required = true, position = 26)
	private Date coverExpirationDate;

	/** 見積鑑用備考 */
	@ApiModelProperty(value = "見積鑑用備考", required = true, position = 27, allowableValues = "range[0,255]")
	private String coverRemarks;

	/** 見積鑑用見積提示日 */
	@ApiModelProperty(value = "見積鑑用見積提示日", required = true, position = 28)
	private Date coverPresentationDate;

	/** 見積発行元会社名 */
	@ApiModelProperty(value = "見積発行元会社名", required = true, position = 29, allowableValues = "range[0,255]")
	private String publishCompany;

	/** 見積発行元所属 */
	@ApiModelProperty(value = "見積発行元所属", required = true, position = 30, allowableValues = "range[0,255]")
	private String publishDepartment;

	/** 見積発行元郵便番号 */
	@ApiModelProperty(value = "見積発行元郵便番号", required = true, position = 31, allowableValues = "range[0,255]")
	private String publishPostNumber;

	/** 見積発行元住所 */
	@ApiModelProperty(value = "見積発行元住所", required = true, position = 32, allowableValues = "range[0,1000]")
	private String publishAddress;

	/** 見積発行元電話番号 */
	@ApiModelProperty(value = "見積発行元電話番号", required = true, position = 33, allowableValues = "range[0,255]")
	private String publishTel;

	/** 見積発行元FAX番号 */
	@ApiModelProperty(value = "見積発行元FAX番号", required = true, position = 34, allowableValues = "range[0,255]")
	private String publishFax;

	/** 見積発行元担当者名 */
	@ApiModelProperty(value = "見積発行元担当者名", required = true, position = 35, allowableValues = "range[0,255]")
	private String publishEmployee;

	/** 特価希望理由 */
	@ApiModelProperty(value = "特価希望理由", required = true, position = 36, allowableValues = "range[0,255]")
	private String spPriceApplyReason;

	/** 特価希望理由テキスト */
	@ApiModelProperty(value = "特価希望理由テキスト", required = true, position = 37, allowableValues = "range[0,255]")
	private String spPriceApplyReasonText;

	/** 主競合先名称 */
	@ApiModelProperty(value = "主競合先名称", required = true, position = 38, allowableValues = "range[0,255]")
	private String mainCompetitorName;

	/** 競合情報 */
	@ApiModelProperty(value = "競合情報", required = true, position = 39, allowableValues = "range[0,255]")
	private String competitionInfo;

	/** 競合先契約種別 */
	// TODO 区分値不明
	@ApiModelProperty(value = "競合先契約種別", required = true, position = 40, allowableValues = "range[0,255]")
	private String competitionContractDiv;

	/** 競合先基本料金 */
	@ApiModelProperty(value = "競合先基本料金", required = true, position = 41, allowableValues = "range[0,9999999999999999999]")
	private BigDecimal competitionAmount;

	/** 拡張項目 */
	@ApiModelProperty(value = "拡張項目", required = true, position = 42)
	private String extendsParameter;

	/**
	 * 見積担当SA社員
	 */
	@OneToOne(mappedBy = "estimation")
	@ApiModelProperty(value = "見積担当SA社員", required = true, position = 43)
	private EstimationPicSaEmp estimationPicSaEmp;

}
