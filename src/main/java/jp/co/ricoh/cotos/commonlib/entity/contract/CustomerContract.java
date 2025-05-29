package jp.co.ricoh.cotos.commonlib.entity.contract;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

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
@EntityListeners(CustomerContractListener.class)
@Data
@Table(name = "customer_contract")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class CustomerContract extends CustomerAbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_contract_seq")
	@SequenceGenerator(name = "customer_contract_seq", sequenceName = "customer_contract_seq", allocationSize = 1)
	@ApiModelProperty(value = "ID(作成時不要)", required = true, position = 1, allowableValues = "range[0,9223372036854775807]", readOnly = true)
	private long id;

	/**
	 * 契約
	 */
	@OneToOne(optional = false)
	@JoinColumn(name = "contract_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "契約", required = true, position = 3)
	private Contract contract;

	/**
	 * NetRicoh会員ID
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "NetRicoh会員ID", required = false, position = 4, allowableValues = "range[0,255]")
	private String netricohAccount;

	/**
	 * 設置先名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "設置先名", required = false, position = 5, allowableValues = "range[0,255]")
	private String setupCorpNm;

	/**
	 * 設置先郵便番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "設置先郵便番号", required = false, position = 6, allowableValues = "range[0,255]")
	private String setupPostCd;

	/**
	 * 設置先住所
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "設置先住所", required = false, position = 7, allowableValues = "range[0,255]")
	private String setupAddr;

	/**
	 * 設置先電話番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "設置先電話番号", required = false, position = 8, allowableValues = "range[0,255]")
	private String setupTel;
}