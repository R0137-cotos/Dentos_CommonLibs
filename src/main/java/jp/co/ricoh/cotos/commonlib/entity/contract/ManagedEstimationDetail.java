package jp.co.ricoh.cotos.commonlib.entity.contract;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.DetailStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "managed_estimation_detail")
public class ManagedEstimationDetail extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "managed_estimation_detail_seq")
	@SequenceGenerator(name = "managed_estimation_detail_seq", sequenceName = "managed_estimation_detail_seq", allocationSize = 1)
	@ApiModelProperty(value = "見積明細管理ID(作成時不要)", required = true, position = 1, readOnly = true)
	private long id;

	/**
	 * 見積
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "contract_id", referencedColumnName = "id")
	@ApiModelProperty(value = "契約", required = true, position = 2)
	@JsonIgnore
	private Contract contract;

	/**
	 * 状態
	 */
	@Column(nullable = false)
	@NotNull
	@ApiModelProperty(value = "状態", required = true, allowableValues = "NOUPDATE(\"1\"), ADD(\"2\"), DELETE(\"3\"), UPDATE(\"4\")", example = "1", position = 3)
	private DetailStatus state;

	/**
	 * 変更前数量
	 */
	@Column(nullable = false)
	@Max(99999)
	@Min(0)
	@ApiModelProperty(value = "変更前数量", required = true, position = 4, allowableValues = "range[0,99999]")
	private int beforeQuantity;

	/**
	 * 数量
	 */
	@Column(nullable = false)
	@Max(99999)
	@Min(0)
	@ApiModelProperty(value = "数量", required = true, position = 5, allowableValues = "range[0,99999]")
	private int quantity;

	/**
	 * 見積単価
	 */
	@Column(nullable = false)
	@NotNull
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "見積単価", required = true, position = 6, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal estimationUnitPrice;

	/**
	 * 見積金額
	 */
	@Column(nullable = false)
	@NotNull
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "見積金額", required = true, position = 7, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal estimationAmountSummary;

	/**
	 * 摘要
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "摘要", required = false, position = 8, allowableValues = "range[0,255]")
	private String detailAbstract;

	/**
	 * 拡張項目
	 */
	@Lob
	@ApiModelProperty(value = "拡張項目", required = false, position = 9)
	private String extendsParameter;

	/**
	 * 品種マスタID
	 */
	@Min(0)
	@Column(nullable = false)
	@ApiModelProperty(value = "品種マスタID", required = true, position = 10, allowableValues = "range[0,9223372036854775807]")
	private long itemMasterId;

	/**
	 * リコー品種コード
	 */
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "リコー品種コード", required = true, position = 11, allowableValues = "range[0,255]")
	private String ricohItemCode;

}
