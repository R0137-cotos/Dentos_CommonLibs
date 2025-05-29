package jp.co.ricoh.cotos.commonlib.entity.contract;

import java.util.Arrays;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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
@Table(name = "contract_equipment")
public class ContractEquipment extends EntityBase {

	public enum IsysoneProcStatus {
		未処理("0"), CSV作成済み("1"), 連携済み("2"), 連携エラー("3");

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

	/**
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contract_equipment_seq")
	@SequenceGenerator(name = "contract_equipment_seq", sequenceName = "contract_equipment_seq", allocationSize = 1)
	@ApiModelProperty(value = "ID(作成時不要)", required = true, position = 1, allowableValues = "range[0,9223372036854775807]", readOnly = true)
	private long id;

	/**
	 * 機種コード
	 */
	@Size(max = 255)
	@NotNull
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
	@ApiModelProperty(value = "本体フラグ", required = false, position = 4, allowableValues = "range[0,9]")
	private Integer bodyFlg;

	/**
	 * サービス機器フラグ
	 */
	@ApiModelProperty(value = "サービス機器フラグ", required = false, position = 5, allowableValues = "range[0,9]")
	private Integer serviceMachineFlg;

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
	 * 契約
	 */
	@ManyToOne(optional = false)
	@JsonIgnore
	@JoinColumn(name = "contract_id", referencedColumnName = "id")
	@ApiModelProperty(value = "契約", required = true, position = 14)
	private Contract contract;

	/**
	 * 点検診断月指定
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "点検診断月指定", required = false, position = 15, allowableValues = "range[0,255]")
	private String inspectionMonth;

	/**
	 * 点検診断月(12ヶ月分)
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "点検診断月(12ヶ月分)", required = false, position = 16, allowableValues = "range[0,255]")
	private String inspectionMonthYearWorth;

}
