package jp.co.ricoh.cotos.commonlib.entity.master;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 機種構成マスタ
 */
@Entity
@Data
@ToString(exclude = { "itemMaster" })
@EqualsAndHashCode(callSuper = true)
@Table(name = "equipment_comp_master")
public class EquipmentCompMaster extends EntityBaseMaster {

	/**
	 * 機種構成マスタID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "equipment_comp_master_seq")
	@SequenceGenerator(name = "equipment_comp_master_seq", sequenceName = "equipment_comp_master_seq", allocationSize = 1)
	@ApiModelProperty(value = "機種構成マスタID", required = true, position = 1, allowableValues = "range[0,9999999999999999999999999999]")
	private long id;

	/**
	 * 機種コード
	 */
	@Column(nullable = false)
	@Size(max = 255)
	@ApiModelProperty(value = "機種コード", required = true, position = 2, allowableValues = "range[0,255]")
	private String equipmentCode;

	/**
	 * 本体フラグ
	 */
	@Max(9)
	@Min(0)
	@ApiModelProperty(value = "本体フラグ", required = false, position = 3, allowableValues = "range[0,9]")
	private Integer bodyFlg;

	/**
	 * サービス機器フラグ
	 */
	@Max(9)
	@Min(0)
	@ApiModelProperty(value = "サービス機器フラグ", required = false, position = 4, allowableValues = "range[0,9]")
	private Integer serviceMachineFlg;

	/**
	 * 保守売上連携フラグ
	 */
	@Max(9)
	@Min(0)
	@ApiModelProperty(value = "保守売上連携フラグ", required = false, position = 5, allowableValues = "range[0,9]")
	private Integer maintenanceLinkageFlg;

	/**
	 * 保守形態
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "保守形態", required = false, position = 6, allowableValues = "range[0,255]")
	private String maintenanceForm;

	/**
	 * 品種マスタ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "item_master_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "品種マスタ", required = true, position = 7)
	private ItemMaster itemMaster;

	/**
	 * 点検診断月指定
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "点検診断月指定", required = false, position = 8, allowableValues = "range[0,255]")
	private String inspectionMonth;

	/**
	 * 点検診断月(12ヶ月分)
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "点検診断月(12ヶ月分)", required = false, position = 9, allowableValues = "range[0,255]")
	private String inspectionMonthYearWorth;
}
