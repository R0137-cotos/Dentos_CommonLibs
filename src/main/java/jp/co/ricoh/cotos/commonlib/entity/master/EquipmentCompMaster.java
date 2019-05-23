package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 機種構成マスタ
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "equipment_comp_master")
public class EquipmentCompMaster extends EntityBaseMaster {
	
	/**
	 * 本体フラグ
	 */
	public enum bodyFlg {
		
		オプション("0"), 本体("1");
		
		private final String text;

		private bodyFlg(final String text) {
			this.text = text;
		}
		
		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static bodyFlg fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}
	
	/**
	 * サービス機器フラグ
	 */
	public enum serviceMachineFlg {
		
		本体機器("0"), サービス機器("1");
		
		private final String text;

		private serviceMachineFlg(final String text) {
			this.text = text;
		}
		
		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static serviceMachineFlg fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}
	
	/**
	 * 保守売上連携フラグ
	 */
	public enum maintenanceLinkageFlg {
		
		連携しない("0"), 連携する("1");
		
		private final String text;

		private maintenanceLinkageFlg(final String text) {
			this.text = text;
		}
		
		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static maintenanceLinkageFlg fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}
	
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
	 * 保守形態
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "保守形態", required = false, position = 3, allowableValues = "range[0,255]")
	private String maintenanceForm;
	
	/**
	 * 品種マスタ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "ricoh_item_code", referencedColumnName = "ricoh_item_code")
	@JsonIgnore
	@ApiModelProperty(value = "品種マスタ", required = true, position = 4)
	private ItemMaster itemMaster;
	
	/**
	 * 点検診断月指定
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "点検診断月指定", required = false, position = 5, allowableValues = "range[0,255]")
	private String inspectionMonth;
	
	/**
	 * 点検診断月(12ヶ月分)
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "点検診断月(12ヶ月分)", required = false, position = 6, allowableValues = "range[0,255]")
	private String inspectionMonthYearWorth;
}
