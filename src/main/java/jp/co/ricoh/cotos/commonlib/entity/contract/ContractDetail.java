package jp.co.ricoh.cotos.commonlib.entity.contract;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.DetailStatus;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.InitialAccountSalesStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 契約明細を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "contract_detail")
public class ContractDetail extends EntityBase {
	
	public enum FfmInsideTransStatus {
		未処理("0"),CSV作成済み("1"),連携済み("2");
		
		private final String text;
		
		private FfmInsideTransStatus(final String text) {
			this.text = text;
		}
		
		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static FfmInsideTransStatus fromString(final String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}
	
	public enum AbsConInsideTransStatus {
		未処理("0"),TSV作成済み("1"),連携済み("2"),連携エラー("3");
		
		private final String text;
		
		private AbsConInsideTransStatus(final String text) {
			this.text = text;
		}
		
		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static AbsConInsideTransStatus fromString(final String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contract_detail_seq")
	@SequenceGenerator(name = "contract_detail_seq", sequenceName = "contract_detail_seq", allocationSize = 1)
	@ApiModelProperty(value = "契約明細ID(作成時不要)", required = true, position = 1, allowableValues = "range[0,9223372036854775807]", readOnly = true)
	private long id;

	/**
	 * 契約
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "contract_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "契約", required = true, position = 2)
	private Contract contract;

	/**
	 * 状態
	 */
	@Column(nullable = false)
	@NotNull
	@ApiModelProperty(value = "状態", required = true, allowableValues = "NONE(\"1\"), ADD(\"2\"), DELETE(\"3\"), UPDATE(\"4\")", example = "", position = 3)
	private DetailStatus state;

	/**
	 * 変更前数量
	 */
	@Max(99999)
	@Min(0)
	@ApiModelProperty(value = "変更前数量", required = false, position = 4, allowableValues = "range[0,99999]")
	private Integer beforeQuantity;

	/**
	 * 数量
	 */
	@Column(nullable = false)
	@Max(99999)
	@Min(0)
	@ApiModelProperty(value = "数量", required = true, position = 5, allowableValues = "range[0,99999]")
	private int quantity;

	/**
	 * 単価
	 */
	@Column(nullable = false)
	@NotNull
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "単価", required = true, position = 6, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal unitPrice;

	/**
	 * 金額
	 */
	@Column(nullable = false)
	@NotNull
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "金額", required = true, position = 7, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal amountSummary;

	/**
	 * 摘要
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "摘要", required = false, position = 8, allowableValues = "range[0,255]")
	private String detailAbstract;

	/**
	 * 拡張項目
	 */
	@ApiModelProperty(value = "拡張項目", required = false, position = 9)
	@Lob
	private String extendsParameter;

	/**
	 * イニシャル売上計上処理状態
	 */
	@ApiModelProperty(value = "イニシャル売上計上処理状態", required = false, position = 10)
	private InitialAccountSalesStatus initialAccountSalesStatus;

	/**
	 * イニシャル売上計上処理日
	 */
	@ApiModelProperty(value = "イニシャル売上計上処理日", required = false, position = 11)
	@Temporal(TemporalType.DATE)
	private Date initialAccountSalesDate;

	/**
	 * 品種(契約用)
	 */
	@Valid
	@OneToOne(mappedBy = "contractDetail")
	@ApiModelProperty(value = "品種(契約用)", required = false, position = 12)
	private ItemContract itemContract;
	
	/**
	 * 注文番号
	 */
	@ApiModelProperty(value = "注文番号", required = false, position = 13)
	private String orderNo;

	/**
	 * FFM内部振替処理状態
	 */
	@ApiModelProperty(value = "FFM内部振替処理状態", required = false, position = 14, allowableValues = "range[0,255]")
	private FfmInsideTransStatus ffmInsideTransStatus;
	
	/**
	 * FFM内部振替連携日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "FFM内部振替連携日", required = false, position = 15)
	private Date ffmInsideLinkDate;
	
	/**
	 * 統合契約内部振替処理状態
	 */
	@ApiModelProperty(value = "統合契約内部振替処理状態", required = false, position = 16, allowableValues = "range[0,255]")
	private AbsConInsideTransStatus absConInsideTransStatus;
	
	/**
	 * 統合契約内部振替連携日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "統合契約内部振替連携日", required = false, position = 17)
	private Date absConInsideLinkDate;
}