package jp.co.ricoh.cotos.commonlib.entity.estimation;

import java.math.BigDecimal;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.DetailStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "estimation_detail")
public class EstimationDetail extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estimation_detail_seq")
	@SequenceGenerator(name = "estimation_detail_seq", sequenceName = "estimation_detail_seq", allocationSize = 1)
	@ApiModelProperty(value = "見積明細ID", required = true, position = 1)
	private long id;

	/**
	 * 見積
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "estimation_id", referencedColumnName = "id")
	@ApiModelProperty(value = "見積", required = true, position = 2)
	@JsonIgnore
	private Estimation estimation;

	/**
	 * 状態
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "状態", required = true, position = 3)
	private DetailStatus status;

	/**
	 * 数量
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "数量", required = true, position = 4, allowableValues = "range[0,99999]")
	private int quantity;

	/**
	 * 見積金額
	 */
	@ApiModelProperty(value = "見積金額", required = false, position = 5, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal amountSummary;

	/**
	 * 摘要
	 */
	@ApiModelProperty(value = "摘要", required = false, position = 6, allowableValues = "range[0,255]")
	private String detailAbstract;

	/**
	 * 拡張項目
	 */
	@Lob
	@ApiModelProperty(value = "拡張項目", required = false, position = 7)
	private String extendsParameter;

	@OneToOne(optional = false)
	@ApiModelProperty(value = "品種(見積用)", required = true, position = 8)
	private ItemEstimation itemEstimation;

}
