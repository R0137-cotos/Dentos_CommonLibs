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

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.common.EmployeeAbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 契約情報の中で保持する追加編集者社員を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@EntityListeners(ContractAddedEditorEmpListener.class)
@Data
@Table(name = "contract_added_editor_emp")
public class ContractAddedEditorEmp extends EmployeeAbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contract_added_editor_emp_seq")
	@SequenceGenerator(name = "contract_added_editor_emp_seq", sequenceName = "contract_added_editor_emp_seq", allocationSize = 1)
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
