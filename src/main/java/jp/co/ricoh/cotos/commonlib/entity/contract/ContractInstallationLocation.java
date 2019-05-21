package jp.co.ricoh.cotos.commonlib.entity.contract;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.common.CustomerAbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 設置先(契約用)を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "contract_installation_location")
public class ContractInstallationLocation extends CustomerAbstractEntity {

	/**
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contract_installation_location_seq")
	@SequenceGenerator(name = "contract_installation_location_seq", sequenceName = "contract_installation_location_seq", allocationSize = 1)
	@ApiModelProperty(value = "ID(作成時不要)", required = true, position = 1, allowableValues = "range[0,9223372036854775807]", readOnly = true)
	private long id;

	/**
	 * MoM非連携_郵便番号(手入力)
	 */
	@ApiModelProperty(value = "MoM非連携_郵便番号(手入力)", required = false, position = 72, allowableValues = "range[0,255]")
	private String inputPostNumber;

	/**
	 * MoM非連携_住所(手入力)
	 */
	@ApiModelProperty(value = "MoM非連携_住所(手入力)", required = false, position = 73, allowableValues = "range[0,1000]")
	private String inputAddress;
	
	/**
	 * 契約
	 */
	@OneToOne(optional = false)
	@NotNull
	@JsonIgnore
	@JoinColumn(name = "contract_id", referencedColumnName = "id")
	@ApiModelProperty(value = "契約", required = true, position = 74)
	private Contract contract;
}
