package jp.co.ricoh.cotos.commonlib.entity.contract.order;

import java.math.BigDecimal;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.contract.order.OrderManagementInfo.CaptureStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 注文商品情報
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "order_product_info")
public class OrderProductInfo extends EntityBase {
	public enum ChargeRule {

		有料("0"), 初月無料("1"), 無料期間指定("2");

		private final String text;

		private ChargeRule(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static ChargeRule fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}
	
	public enum ProvideMethod {
		初期("1"), 月額("2"), 年額("3");

		private final String text;

		private ProvideMethod(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static ProvideMethod fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}
	/**
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_product_info_seq")
	@SequenceGenerator(name = "order_product_info_seq", sequenceName = "order_product_info_seq", allocationSize = 1)
	@ApiModelProperty(value = "ID", required = true, position = 1)
	private long id;

	/**
	 * 注文基本情報ID
	 */
	@OneToOne(optional = false)
	@JoinColumn(name = "order_basic_info_id", referencedColumnName = "id")
	@JsonIgnore
	private OrderBasicInfo orderBasicInfo;

	/**
	 * 商品コード（RICOH品種コード）
	 */
	@Column
	@ApiModelProperty(value = "商品コード（RICOH品種コード）", required = false, position = 2, allowableValues = "range[0,]")
	private String productCd;

	/**
	 * 課金制約ルール
	 */
	@Column
	@ApiModelProperty(value = "課金制約ルール", required = false, position = 3, allowableValues = "range[0,]")
	private ChargeRule chargeRule;

	/**
	 * 無料期間
	 */
	@Column
	@ApiModelProperty(value = "無料期間", required = false, position = 4, allowableValues = "range[0,]")
	private String freePeriod;

	/**
	 * 商品名
	 */
	@Column
	@ApiModelProperty(value = "商品名", required = false, position = 5, allowableValues = "range[0,]")
	private String productName;

	/**
	 * 提供方法
	 */
	@Column
	@ApiModelProperty(value = "提供方法", required = false, position = 6, allowableValues = "range[0,]")
	private ProvideMethod provideMethod;

	/**
	 * 変更後数量
	 */
	@Column
	@ApiModelProperty(value = "変更後数量", required = false, position = 7)
	private Integer quantity;

	/**
	 * 変更前数量
	 */
	@Column
	@ApiModelProperty(value = "変更前数量", required = false, position = 8)
	private Integer beforeQuantity;

	/**
	 * 差分
	 */
	@Column
	@ApiModelProperty(value = "差分", required = false, position = 9)
	private Integer differenceQuantity;

	/**
	 * 売価単価
	 */
	@Column
	@ApiModelProperty(value = "売価単価", required = false, position = 10)
	private BigDecimal unitPrice;

	/**
	 * 売価合計
	 */
	@Column
	@ApiModelProperty(value = "売価合計", required = false, position = 11)
	private BigDecimal amountSummary;

}
