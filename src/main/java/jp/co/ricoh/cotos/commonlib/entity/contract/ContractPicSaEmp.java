package jp.co.ricoh.cotos.commonlib.entity.contract;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.common.EmployeeAbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 契約情報の中で保持する担当SA社員を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@EntityListeners(ContractPicSaEmpListener.class)
@Data
@Table(name = "contract_pic_sa_emp")
public class ContractPicSaEmp extends EmployeeAbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contract_pic_sa_emp_seq")
	@SequenceGenerator(name = "contract_pic_sa_emp_seq", sequenceName = "contract_pic_sa_emp_seq", allocationSize = 1)
	@ApiModelProperty(value = "ID(作成時不要)", required = true, position = 1, allowableValues = "range[0,9999999999999999999]", readOnly = true)
	private long id;

	/**
	 * 拡張項目
	 */
	@ApiModelProperty(value = "拡張項目", required = false, position = 2)
	@Lob
	private String extendsParameter;

	/**
	 * MoM非連携_MoM企事部システム連携ID
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_MoM企事部システム連携ID", required = false, position = 3, allowableValues = "range[0,255]")
	private String momKjbSystemId;

	/**
	 * MoM非連携_MoM企事部ID
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_MoM企事部ID", required = false, position = 4, allowableValues = "range[0,255]")
	private String momKjbId;

	/**
	 * 販売店名（カナ）
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "販売店名（カナ）", required = false, position = 5, allowableValues = "range[0,255]")
	private String salesCompanyNameKana;

	/**
	 * MoM非連携_企業代表者名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_企業代表者名", required = false, position = 6, allowableValues = "range[0,255]")
	private String companyRepresentativeName;

	/**
	 * MoM非連携_企業代表者名(カナ)
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_企業代表者名(カナ)", required = false, position = 7, allowableValues = "range[0,255]")
	private String companyRepresentativeNameKana;

	/**
	 * 契約
	 */
	@OneToOne(optional = false)
	@JoinColumn(name = "contract_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "契約", required = true, position = 8)
	private Contract contract;

}
