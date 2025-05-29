package jp.co.ricoh.cotos.commonlib.entity.arrangement;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.common.EmployeeAbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 手配業務を担当する作業社員を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@EntityListeners(ArrangementPicWorkerEmpListener.class)
@Data
@Table(name = "arrangement_pic_worker_emp")
@ApiModel(description = "担当作業者社員(作成時不要)")
public class ArrangementPicWorkerEmp extends EmployeeAbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "arrangement_pic_worker_emp_seq")
	@SequenceGenerator(name = "arrangement_pic_worker_emp_seq", sequenceName = "arrangement_pic_worker_emp_seq", allocationSize = 1)
	@ApiModelProperty(value = "ID (作成時不要)", required = true, position = 1, allowableValues = "range[0,9223372036854775807]", readOnly = true)
	private long id;

	/**
	 * 手配業務
	 */
	@OneToOne(optional = false)
	@JoinColumn(name = "arrangement_work_id", referencedColumnName = "id")
	@ApiModelProperty(value = "手配業務", required = true, position = 2)
	@JsonIgnore
	private ArrangementWork arrangementWork;

}
