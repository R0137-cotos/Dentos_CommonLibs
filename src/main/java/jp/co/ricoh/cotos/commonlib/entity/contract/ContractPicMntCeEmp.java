package jp.co.ricoh.cotos.commonlib.entity.contract;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.common.EmployeeAbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 契約情報の中で保持する契約保守担当CE社員情報を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@EntityListeners(ContractPicMntCeEmpListener.class)
@Data
@Table(name = "contract_pic_mnt_ce_emp")
@ApiModel(description = "契約保守担当CE社員(作成時不要)")
public class ContractPicMntCeEmp extends EmployeeAbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contract_pic_ce_emp_seq")
	@SequenceGenerator(name = "contract_pic_ce_emp_seq", sequenceName = "contract_pic_ce_emp_seq", allocationSize = 1)
	@ApiModelProperty(value = "ID(作成時不要)", required = true, position = 1, allowableValues = "range[0,9223372036854775807]", readOnly = true)
	private long id;

	/**
	 * 契約
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "contract_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "契約", required = true, position = 2)
	private Contract contract;
}
