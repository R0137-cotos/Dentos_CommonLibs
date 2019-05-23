package jp.co.ricoh.cotos.commonlib.entity.master;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.InitialRunningDiv;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 品種振替構成マスタ
 */
@Entity
@Data
@ToString(exclude = { "itemMaster" })
@EqualsAndHashCode(callSuper = true)
@Table(name = "item_trans_comp_master")
public class ItemTransCompMaster extends EntityBaseMaster {

	/**
	 * 品種振替構成マスタID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_trans_comp_master_seq")
	@SequenceGenerator(name = "item_trans_comp_master_seq", sequenceName = "item_trans_comp_master_seq", allocationSize = 1)
	@ApiModelProperty(value = "ID", required = true, position = 1, allowableValues = "range[0,9223372036854775807]")
	private long id;

	/**
	 * 原価
	 */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "原価", required = false, position = 2, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal price;

	/**
	 * 振替先課所コード
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "振替先課所コード", required = false, position = 3, allowableValues = "range[0,255]")
	private String transToServiceOrgCode;

	/**
	 * イニシャル/ランニング区分
	 */
	@ApiModelProperty(value = "イニシャル/ランニング区分", required = false, allowableValues = "イニシャル(\"1\"), ランニング(\"2\"), 期間売(\"3\")", position = 4)
	private InitialRunningDiv initialRunningDiv;

	/**
	 * 品種マスタ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "item_master_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "品種マスタ", required = true, position = 5)
	private ItemMaster itemMaster;
}
