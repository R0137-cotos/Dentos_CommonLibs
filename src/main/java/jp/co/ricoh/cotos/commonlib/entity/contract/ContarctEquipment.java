package jp.co.ricoh.cotos.commonlib.entity.contract;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 契約機種を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "contarct_equipment")
public class ContarctEquipment extends EntityBase {
	
	public enum BodyFlg {
		オプション(0),本体(1);
		
		private final Integer num;
		
		private BodyFlg(final Integer num) {
			this.num = num;
		}
		
		@Override
		@JsonValue
		public String toString() {
			return this.num.toString();
		}

		@JsonCreator
		public static BodyFlg fromString(final Integer num) {
			return Arrays.stream(values()).filter(v -> v.num.equals(num)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(num)));
		}
	}
	
	public enum ServiceMachineFlg {
		本体機器(0),サービス機器(1);
		
		private final Integer num;
		
		private ServiceMachineFlg(final Integer num) {
			this.num = num;
		}
		
		@Override
		@JsonValue
		public String toString() {
			return this.num.toString();
		}

		@JsonCreator
		public static ServiceMachineFlg fromString(final Integer num) {
			return Arrays.stream(values()).filter(v -> v.num.equals(num)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(num)));
		}
	}
	
	public enum IsysoneProcStatus {
		未処理("0"),CSV作成済み("1"),連携済み("2"),連携エラー("3");
		
		private final String text;
		
		private IsysoneProcStatus(final String text) {
			this.text = text;
		}
		
		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static IsysoneProcStatus fromString(final String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}
	
	public enum MaintenanceLinkageCsvCreateStatus {
		未作成("0"),作成済み("1"),作成不要("2"),作成エラー("3");
		
		private final String text;
		
		private MaintenanceLinkageCsvCreateStatus(final String text) {
			this.text = text;
		}
		
		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static MaintenanceLinkageCsvCreateStatus fromString(final String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	/**
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contarct_equipment_seq")
	@SequenceGenerator(name = "contarct_equipment_seq", sequenceName = "contarct_equipment_seq", allocationSize = 1)
	@ApiModelProperty(value = "ID(作成時不要)", required = true, position = 1, allowableValues = "range[0,9223372036854775807]", readOnly = true)
	private long Id;
	
	/**
	 * 機種コード
	 */
	@Size(max = 255)
	@Column(nullable = false)
	@ApiModelProperty(value = "機種コード", required = true, position = 2, allowableValues = "range[0,255]")
	private String equipmentCode;
	
	/**
	 * 機番
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "機番", required = false, position = 3, allowableValues = "range[0,255]")
	private String equipmentNo;
	
	/**
	 * 本体フラグ
	 */
	@ApiModelProperty(value = "本体フラグ", required = false, position = 4, allowableValues = "オプション(0),本体(1)")
	private BodyFlg bodyFlg;
	
	/**
	 * サービス機器フラグ
	 */
	@ApiModelProperty(value = "サービス機器フラグ", required = false, position = 5, allowableValues = "本体機器(0),サービス機器(1)")
	private ServiceMachineFlg serviceMachineFlg;
	
	/**
	 * 設置日
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "設置日", required = false, position = 6)
	private Date installationDate;
	
	/**
	 * 購入形態
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "購入形態", required = false, position = 7, allowableValues = "range[0,255]")
	private String purchaseForm;
	
	/**
	 * 納入形態
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "納入形態", required = false, position = 8, allowableValues = "range[0,255]")
	private String deliveryForm;
	
	/**
	 * 保守形態
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "保守形態", required = false, position = 9, allowableValues = "range[0,255]")
	private String maintenanceForm;
	
	/**
	 * 納入機器区分
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "納入機器区分", required = false, position = 10, allowableValues = "range[0,255]")
	private String deliveryMachineDiv;
	
	/**
	 * メンテの注意(カナ)
	 */
	@Size(max = 1000)
	@ApiModelProperty(value = "メンテの注意(カナ)", required = false, position = 11, allowableValues = "range[0,1000]")
	private String maintenanceNoteKana;
	
	/**
	 * Isys-One 処理状態
	 */
	@ApiModelProperty(value = "Isys-One 処理状態", required = false, position = 12, allowableValues = "未処理(\"0\"),CSV作成済み(\"1\"),連携済み(\"2\"),連携エラー(\"3\")")
	private IsysoneProcStatus isysoneProcStatus;
	
	/**
	 * Isys-One 連携日時
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "Isys-One 連携日時(1時間毎に連携するため日時とする)", required = false, position = 13)
	private Date isysoneLinkageAt;
	
	/**
	 * 保守売上連携用CSV作成状態
	 */
	@ApiModelProperty(value = "保守売上連携用CSV作成状態", required = false, position = 14, allowableValues = "未作成(\"0\"),作成済み(\"1\"),作成不要(\"2\"),作成エラー(\"3\")")
	private MaintenanceLinkageCsvCreateStatus maintenanceLinkageCsvCreateStatus;
	
	/**
	 * 保守売上連携用CSV作成日
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "保守売上連携用CSV作成日", required = false, position = 15)
	private Date maintenanceLinkageCsvCreateDate;
	
	/**
	 * 契約
	 */
	@ManyToOne(optional = false)
	@NotNull
	@JsonIgnore
	@JoinColumn(name = "contract_id", referencedColumnName = "id")
	@ApiModelProperty(value = "契約", required = true, position = 16)
	private Contract contract;
	
	/**
	 * 点検診断月指定
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "点検診断月指定", required = false, position = 17, allowableValues = "range[0,255]")
	private String inspectionMonth;

	/**
	 * 点検診断月(12ヶ月分)
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "点検診断月(12ヶ月分)", required = false, position = 18, allowableValues = "range[0,255]")
	private String inspectionMonthYearWorth;
	
}
