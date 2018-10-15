package jp.co.ricoh.cotos.commonlib.entity.contract;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.master.ItemMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.ItemMaster.CostType;
import jp.co.ricoh.cotos.commonlib.entity.master.ItemMaster.ItemType;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 品種を表すEntity
 */

@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "item_contract")
public class ItemContract extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_contract_seq")
	@SequenceGenerator(name = "item_contract_seq", sequenceName = "item_contract_seq", allocationSize = 1)
	@ApiModelProperty(value = "ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 品種マスタ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "item_master_id", referencedColumnName = "id")
	@ApiModelProperty(value = "品種マスタ", required = true, position = 2)
	private ItemMaster itemMaster;

	/**
	 * 商品マスタID
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "商品マスタID", required = true, position = 3, allowableValues = "range[0,9999999999999999999]")
	private long productMasterId;

	/**
	 * 品種名
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "品種名", required = true, position = 4, allowableValues = "range[0,255]")
	private String name;

	/**
	 * リコー品種コード
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "リコー品種コード", required = true, position = 5, allowableValues = "range[0,255]")
	private String ricohItemCode;

	/**
	 * 品種区分
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "品種区分", required = true, position = 6)
	private ItemType itemType;

	/**
	 * 費用種別
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "費用種別", required = true, position = 7)
	private CostType costType;

	/**
	 * 仕切価格
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "仕切価格", required = true, position = 8, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal partitionPrice;

	/**
	 * 積上げ可能期間(開始日)
	 */
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "積上げ可能期間(開始日)", required = true, position = 9)
	private Date effectiveFrom;

	/**
	 * 積上げ可能期間(終了日)
	 */
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "積上げ可能期間(終了日)", required = true, position = 10)
	private Date effectiveTo;

	/**
	 * 契約明細
	 */
	@OneToOne(optional = false)
	@JoinColumn(name = "contract_detail_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "契約明細", required = true, position = 8)
	private ContractDetail contrantDetail;
}
