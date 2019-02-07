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
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
	@ApiModelProperty(value = "ID(作成時不要)", required = true, position = 1, allowableValues = "range[0,9999999999999999999]", readOnly = true)
	private long id;

	/**
	 * 品種マスタID
	 */
	@Min(0)
	@Column(nullable = false)
	@ApiModelProperty(value = "品種マスタID", required = true, position = 2, allowableValues = "range[0,9999999999999999999]")
	private long itemMasterId;

	/**
	 * 商品マスタ
	 */
	@Min(0)
	@ApiModelProperty(value = "商品マスタ", required = true, position = 3, allowableValues = "range[0,9999999999999999999]")
	private long productMasterId;

	/**
	 * 品種名
	 */
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "品種名", required = true, position = 4, allowableValues = "range[0,255]")
	private String itemEstimationName;

	/**
	 * リコー品種コード
	 */
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "リコー品種コード", required = true, position = 5, allowableValues = "range[0,255]")
	private String ricohItemCode;

	/**
	 * 品種区分
	 */
	@NotNull
	@ApiModelProperty(value = "品種区分", required = true, allowableValues = "なし(\"0\"), 基本(\"1\"), オプション(\"2\")", example = "1", position = 6)
	private ItemType itemType;

	/**
	 * 費用種別
	 */
	@NotNull
	@ApiModelProperty(value = "費用種別", required = true, allowableValues = "初期費(\"1\"), 月額(\"2\"), 年額(\"3\")", example = "1", position = 7)
	private CostType costType;

	/**
	 * 仕切価格
	 */
	@NotNull
	@DecimalMin("0.00")
	@Digits(integer = 19, fraction = 2)
	@ApiModelProperty(value = "仕切価格", required = true, position = 8, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal partitionPrice;

	/**
	 * 見積明細
	 */
	@OneToOne(optional = false)
	@JoinColumn(name = "estimation_detail_id", referencedColumnName = "id")
	@ApiModelProperty(value = "見積明細", required = true, position = 9)
	@JsonIgnore
	private EstimationDetail estimationDetail;

	@PrePersist
	public void prePersist() {
		super.setCreatedAt(new Date());
	}

}
