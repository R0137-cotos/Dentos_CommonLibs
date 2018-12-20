package jp.co.ricoh.cotos.commonlib.entity.contract;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.common.CustomerAbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 契約情報の中で保持する顧客情報を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "customer_contract")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class CustomerContract extends CustomerAbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_contract_seq")
	@SequenceGenerator(name = "customer_contract_seq", sequenceName = "customer_contract_seq", allocationSize = 1)
	@ApiModelProperty(value = "ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 企業名（カナ）
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "企業名（カナ）", required = false, position = 2, allowableValues = "range[0,255]")
	private String companyNameKana;

	/**
	 * 企業代表者名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "企業代表者名", required = false, position = 3, allowableValues = "range[0,255]")
	private String companyRepresentativeName;

	/**
	 * MoM非連携_企業代表者名（カナ）
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_企業代表者名（カナ）", required = false, position = 4, allowableValues = "range[0,255]")
	private String companyRepresentativeNameKana;

	/**
	 * MoM非連携_担当者氏名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_担当者氏名", required = false, position = 5, allowableValues = "range[0,255]")
	private String picName;

	/**
	 * MoM非連携_担当者氏名（カナ）
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_担当者氏名（カナ）", required = false, position = 6, allowableValues = "range[0,255]")
	private String picNameKana;

	/**
	 * MoM非連携_担当者部署
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_担当者部署", required = false, position = 7, allowableValues = "range[0,255]")
	private String picDeptName;

	/**
	 * MoM非連携_担当者電話番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_担当者電話番号", required = false, position = 8, allowableValues = "range[0,255]")
	private String picPhoneNumber;

	/**
	 * MoM非連携_担当者FAX番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_担当者FAX番号", required = false, position = 9, allowableValues = "range[0,255]")
	private String picFaxNumber;

	/**
	 * MoM非連携_担当者メールアドレス
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_担当者メールアドレス", required = false, position = 10, allowableValues = "range[0,255]")
	private String picMailAddress;

	/**
	 * 契約
	 */
	@OneToOne(optional = false)
	@JoinColumn(name = "contract_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "契約", required = true, position = 11)
	private Contract contract;

	/**
	 * NetRicoh会員ID
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "NetRicoh会員ID", required = false, position = 12, allowableValues = "range[0,255]")
	private String netricohAccount;

	/**
	 * 設置先名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "設置先名", required = false, position = 13, allowableValues = "range[0,255]")
	private String setupCorpNm;

	/**
	 * 設置先郵便番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "設置先郵便番号", required = false, position = 14, allowableValues = "range[0,255]")
	private String setupPostCd;

	/**
	 * 設置先住所
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "設置先住所", required = false, position = 15, allowableValues = "range[0,255]")
	private String setupAddr;

	/**
	 * 設置先電話番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "設置先電話番号", required = false, position = 16, allowableValues = "range[0,255]")
	private String setupTel;
}