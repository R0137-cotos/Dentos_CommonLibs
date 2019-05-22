package jp.co.ricoh.cotos.commonlib.entity.contract;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 契約導入担当SS組織を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "contract_pic_int_ss_org")
public class ContractPicIntSsOrg extends EntityBase {

	/**
	 * ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contract_pic_int_ss_org_seq")
	@SequenceGenerator(name = "contract_pic_int_ss_org_seq", sequenceName = "contract_pic_int_ss_org_seq", allocationSize = 1)
	@ApiModelProperty(value = "ID(作成時不要)", required = true, position = 1, allowableValues = "range[0,9223372036854775807]", readOnly = true)
	private long id;

	/**
	 * MoM組織ID
	 */
	@Column(nullable = false)
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "MoM組織ID", required = false, position = 2, allowableValues = "range[0,255]")
	private String momOrgId;

	/**
	 * 課所名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "課所名", required = true, position = 3, allowableValues = "range[0,255]")
	private String serviceOrgName;

	/**
	 * 契約
	 */
	@OneToOne(optional = false)
	@NotNull
	@JsonIgnore
	@JoinColumn(name = "contract_id", referencedColumnName = "id")
	@ApiModelProperty(value = "契約", required = true, position = 4)
	private Contract contract;

}
