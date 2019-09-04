package jp.co.ricoh.cotos.commonlib.entity.contract.order;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 注文基本情報
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "order_basic_info")
public class OrderBasicInfo extends EntityBase {

	public enum OrdererType {

		新規("1"), 変更("2"), 解約("3");

		private final String text;

		private OrdererType(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static OrdererType fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	public enum ProductType {

		RSI("1");

		private final String text;

		private ProductType(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static ProductType fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	public enum CommercialFlowDiv {

		代売("1"), 直売("2");

		private final String text;

		private CommercialFlowDiv(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static CommercialFlowDiv fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	/**
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_basic_info_seq")
	@SequenceGenerator(name = "order_basic_info_seq", sequenceName = "order_basic_info_seq", allocationSize = 1)
	@ApiModelProperty(value = "ID", required = true, position = 1)
	private long id;

	/**
	 * 注文番号
	 */
	@Column
	@ApiModelProperty(value = "注文番号", required = false, position = 2, allowableValues = "range[0,]")
	private String ordererNumber;

	/**
	 * 注文タイプ
	 */
	@Column
	@ApiModelProperty(value = "注文タイプ", required = false, position = 3, allowableValues = "新規(\"1\"), 変更(\"2\"), 解約(\"3\")")
	private OrdererType ordererType;

	/**
	 * 商品種別
	 */
	@Column
	@ApiModelProperty(value = "商品種別", required = false, position = 4, allowableValues = "RSI(\"1\")")
	private ProductType productType;

	/**
	 * 契約番号
	 */
	@Column
	@ApiModelProperty(value = "契約番号", required = false, position = 5, allowableValues = "range[0,]")
	private String rjManageNumber;

	/**
	 * メーカー契約番号
	 */
	@Column
	@ApiModelProperty(value = "メーカー契約番号", required = false, position = 6, allowableValues = "range[0,]")
	private String makerManageNumber;

	/**
	 * 商流区分（代直区分）
	 */
	@Column
	@ApiModelProperty(value = "商流区分（代直区分）", required = false, position = 7, allowableValues = "代売(\"1\"), 直売(\"2\")")
	private CommercialFlowDiv commercialFlowDiv;

	/**
	 * 申込日時
	 */
	@Column
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "申込日時", required = false, position = 8)
	private Date ordererDateTime;

	/**
	 * 解約予定日
	 */
	@Column
	@ApiModelProperty(value = "解約予定日", required = false, position = 9)
	private Date cancelScheduledDate;

	/**
	 * 解約理由1
	 */
	@Column(name = "cancel_reason_1")
	@ApiModelProperty(value = "解約理由1", required = false, position = 10)
	private String cancelReason1;

	/**
	 * 解約理由2
	 */
	@Column(name = "cancel_reason_2")
	@ApiModelProperty(value = "解約理由2", required = false, position = 11)
	private String cancelReason2;

	/**
	 * 改善ポイント
	 */
	@Column
	@ApiModelProperty(value = "改善ポイント", required = false, position = 12)
	private String improvementPoint;

	/**
	 * 初期費合計
	 */
	@Column
	@ApiModelProperty(value = "初期費合計", required = false, position = 13)
	private BigDecimal initialTotalAmount;

	/**
	 * 月額費合計
	 */
	@Column
	@ApiModelProperty(value = "月額費合計", required = false, position = 14)
	private BigDecimal monthlyTotalAmount;

	/**
	 * 年額費合計
	 */
	@Column
	@ApiModelProperty(value = "年額費合計", required = false, position = 15)
	private BigDecimal yearlyTotalAmount;

	@OneToOne(mappedBy = "orderBasicInfo")
	@ApiModelProperty(value = "注文商品グループ情報", required = false, position = 16)
	private OrderProductGroupInfo orderProductGroupInfo;

	@OneToOne(mappedBy = "orderBasicInfo")
	@ApiModelProperty(value = "注文サービス固有情報", required = false, position = 17)
	private OrderServiceInnerInfo orderServiceInnerInfo;

	@OneToOne(mappedBy = "orderBasicInfo")
	@ApiModelProperty(value = "注文販売店情報", required = false, position = 18)
	private OrderDistributorInfo orderDistributorInfo;

	@OneToOne(mappedBy = "orderBasicInfo")
	@ApiModelProperty(value = "注文セットアップ先情報", required = false, position = 19)
	private OrderSetupInfo orderSetupInfo;

	@OneToOne(mappedBy = "orderBasicInfo")
	@ApiModelProperty(value = "注文者情報", required = false, position = 20)
	private OrdererInfo ordererInfo;

	@OneToMany(mappedBy = "orderBasicInfo")
	@ApiModelProperty(value = "注文商品情報", required = false, position = 21)
	private List<OrderProductInfo> orderProductInfoList;

	@OneToOne(mappedBy = "orderBasicInfo")
	@ApiModelProperty(value = "注文担当支社情報", required = false, position = 21)
	private OrderBranchCustomerInfo orderBranchCustomerInfo;

	@OneToOne(mappedBy = "orderBasicInfo")
	@ApiModelProperty(value = "注文顧客情報", required = false, position = 22)
	private OrderContractorInfo orderContractorInfo;

	@OneToOne(mappedBy = "orderBasicInfo")
	@ApiModelProperty(value = "注文管理情報", required = false, position = 23)
	private OrderManagementInfo orderManagementInfo;

}