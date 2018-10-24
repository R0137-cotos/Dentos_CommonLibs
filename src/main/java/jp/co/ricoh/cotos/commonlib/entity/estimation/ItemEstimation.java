package jp.co.ricoh.cotos.commonlib.entity.estimation;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.master.ItemMaster.CostType;
import jp.co.ricoh.cotos.commonlib.entity.master.ItemMaster.ItemType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 品種を表すEntity
 */

@Entity
@EqualsAndHashCode(callSuper = true)
@EntityListeners(ItemEstimationListener.class)
@Data
@Table(name = "item_estimation")
public class ItemEstimation extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_estimation_seq")
	@SequenceGenerator(name = "item_estimation_seq", sequenceName = "item_estimation_seq", allocationSize = 1)
	@ApiModelProperty(value = "ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 品種マスタID
	 */
	@Column(nullable = false)
	@NotNull
	@Max(9223372036854775807L)
	@ApiModelProperty(value = "品種マスタID", required = true, position = 2, allowableValues = "range[0,9999999999999999999]")
	private long itemMasterId;

	/**
	 * 商品マスタ
	 */
	@NotNull
	@Max(9223372036854775807L)
	@ApiModelProperty(value = "商品マスタ", required = true, position = 3, allowableValues = "range[0,9999999999999999999]")
	private long productMasterId;

	/**
	 * 品種名
	 */
	@NotEmpty
	@Size(max = 255)
	@ApiModelProperty(value = "品種名", required = true, position = 4, allowableValues = "range[0,255]")
	private String itemEstimationName;

	/**
	 * リコー品種コード
	 */
	@NotEmpty
	@Size(max = 255)
	@ApiModelProperty(value = "リコー品種コード", required = true, position = 5, allowableValues = "range[0,255]")
	private String ricohItemCode;

	/**
	 * 品種区分
	 */
	@NotNull
	@ApiModelProperty(value = "品種区分", required = true, position = 6)
	private ItemType itemType;

	/**
	 * 費用種別
	 */
	@NotNull
	@ApiModelProperty(value = "費用種別", required = true, position = 7)
	private CostType costType;

	/**
	 * 仕切価格
	 */
	@NotNull
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "仕切価格", required = true, position = 8, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal partitionPrice;

	/**
	 * 積上げ可能期間（開始日）
	 */
	@Column(nullable = false)
	@NotNull
	@ApiModelProperty(value = "積上げ可能期間（開始日）", required = true, position = 9, readOnly = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveFrom;

	/**
	 * 積上げ可能期間（終了日）
	 */
	@Column(nullable = false)
	@NotNull
	@ApiModelProperty(value = "積上げ可能期間（終了日）", required = true, position = 10, readOnly = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveTo;

	/**
	 * 見積明細
	 */
	@OneToOne(optional = false)
	@JoinColumn(name = "estimation_detail_id", referencedColumnName = "id")
	@NotNull
	@ApiModelProperty(value = "見積明細", required = true, position = 11)
	@JsonIgnore
	private EstimationDetail estimationDetail;

}
