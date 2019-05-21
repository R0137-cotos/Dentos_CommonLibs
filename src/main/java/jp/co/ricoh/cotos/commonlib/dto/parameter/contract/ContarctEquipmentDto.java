package jp.co.ricoh.cotos.commonlib.dto.parameter.contract;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.DtoBase;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContarctEquipment.BodyFlg;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContarctEquipment.IsysoneProcStatus;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContarctEquipment.MaintenanceLinkageCsvCreateStatus;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContarctEquipment.ServiceMachineFlg;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ContarctEquipmentDto extends DtoBase {
	
	/**
	 * 機種コード
	 */
	@Size(max = 255)
	@Column(nullable = false)
	@ApiModelProperty(value = "機種コード", required = true, position = 3, allowableValues = "range[0,255]")
	private String equipmentCode;
	
	/**
	 * 機番
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "機番", required = false, position = 4, allowableValues = "range[0,255]")
	private String equipmentNo;
	
	/**
	 * 本体フラグ
	 */
	@ApiModelProperty(value = "本体フラグ", required = false, position = 5, allowableValues = "オプション(0),本体(1)")
	private BodyFlg bodyFlg;
	
	/**
	 * サービス機器フラグ
	 */
	@ApiModelProperty(value = "サービス機器フラグ", required = false, position = 6, allowableValues = "本体機器(0),サービス機器(1)")
	private ServiceMachineFlg serviceMachineFlg;
	
	/**
	 * 設置日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "設置日", required = false, position = 7)
	private Date installationDate;
	
	/**
	 * 購入形態
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "購入形態", required = false, position = 8, allowableValues = "range[0,255]")
	private String purchaseForm;
	
	/**
	 * 納入形態
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "納入形態", required = false, position = 9, allowableValues = "range[0,255]")
	private String deliveryForm;
	
	/**
	 * 保守形態
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "保守形態", required = false, position = 10, allowableValues = "range[0,255]")
	private String maintenanceForm;
	
	/**
	 * 納入機器区分
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "納入機器区分", required = false, position = 11, allowableValues = "range[0,255]")
	private String deliveryMachineDiv;
	
	/**
	 * メンテの注意(カナ)
	 */
	@Size(max = 1000)
	@ApiModelProperty(value = "メンテの注意(カナ)", required = false, position = 12, allowableValues = "range[0,1000]")
	private String maintenanceNoteKana;
	
	/**
	 * Isys-One 処理状態
	 */
	@ApiModelProperty(value = "Isys-One 処理状態", required = false, position = 13, allowableValues = "未処理(\"0\"),CSV作成済み(\"1\"),連携済み(\"2\"),連携エラー(\"3\")")
	private IsysoneProcStatus isysoneProcStatus;
	
	/**
	 * Isys-One 連携日時
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "Isys-One 連携日時(1時間毎に連携するため日時とする)", required = false, position = 14)
	private Date isysoneLinkageAt;
	
	/**
	 * 保守売上連携用CSV作成状態
	 */
	@ApiModelProperty(value = "保守売上連携用CSV作成状態", required = false, position = 15)
	private MaintenanceLinkageCsvCreateStatus maintenanceLinkageCsvCreateStatus;
	
	/**
	 * 保守売上連携用CSV作成日
	 */
	@Temporal(TemporalType.DATE)
	@ApiModelProperty(value = "保守売上連携用CSV作成日", required = false, position = 16)
	private Date maintenanceLinkageCsvCreateDate;
	
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
