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

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.common.CustomerAbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 設置先(契約用)を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@EntityListeners(ContractInstallationLocationListener.class)
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
	 * MoM非連携_企業代表者名（カナ）
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_企業代表者名（カナ）", required = false, position = 2, allowableValues = "range[0,255]")
	private String companyRepresentativeNameKana;

	/**
	 * MoM非連携_郵便番号(手入力)
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_郵便番号(手入力)", required = false, position = 72, allowableValues = "range[0,255]")
	private String inputPostNumber;

	/**
	 * MoM非連携_住所(手入力)
	 */
	@Size(max = 1000)
	@ApiModelProperty(value = "MoM非連携_住所(手入力)", required = false, position = 73, allowableValues = "range[0,1000]")
	private String inputAddress;

	/**
	 * 契約
	 */
	@OneToOne(optional = false)
	@JsonIgnore
	@JoinColumn(name = "contract_id", referencedColumnName = "id")
	@ApiModelProperty(value = "契約", required = true, position = 74)
	private Contract contract;
}
