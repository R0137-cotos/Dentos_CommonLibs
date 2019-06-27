package jp.co.ricoh.cotos.commonlib.entity.contract;

import javax.persistence.Column;
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
	 * 契約
	 */
	@OneToOne(optional = false)
	@JoinColumn(name = "contract_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "契約", required = true, position = 2)
	private Contract contract;

	/**
	 * 拡張項目
	 */
	@ApiModelProperty(value = "拡張項目", required = false, position = 3)
	@Lob
	private String extendsParameter;
	
	/**
	 * MoM企事部システム連携ID
	 */
	@Column
	@ApiModelProperty(value = "MoM企事部システム連携ID", required = false, position = 15, allowableValues = "range[0,]")
	private String momKjbSystemId;

	/**
	 * MoM企事部ID
	 */
	@Column
	@ApiModelProperty(value = "MoM企事部ID", required = false, position = 16, allowableValues = "range[0,]")
	private String momCustId;

	/**
	 * 販売会社名（カナ）
	 */
	@Column
	@ApiModelProperty(value = "販売会社名（カナ）", required = false, position = 17, allowableValues = "range[0,]")
	private String salesCompanyNameKana;

	/**
	 * MoM非連携_企業代表者名
	 */
	@Column
	@ApiModelProperty(value = "MoM非連携_企業代表者名", required = false, position = 18, allowableValues = "range[0,]")
	private String companyRepresentativeName;

	/**
	 * MoM非連携_企業代表者名（カナ）
	 */
	@Column
	@ApiModelProperty(value = "MoM非連携_企業代表者名（カナ）", required = false, position = 19, allowableValues = "range[0,]")
	private String companyRepresentativeNameKana;
	
}
