package jp.co.ricoh.cotos.commonlib.entity.estimation;

import jakarta.persistence.Column;
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
import jp.co.ricoh.cotos.commonlib.entity.common.EmployeeAbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 見積情報の中で保持する担当SA社員を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@EntityListeners(EstimationPicSaEmpListener.class)
@Data
@Table(name = "estimation_pic_sa_emp")
public class EstimationPicSaEmp extends EmployeeAbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estimation_pic_sa_emp_seq")
	@SequenceGenerator(name = "estimation_pic_sa_emp_seq", sequenceName = "estimation_pic_sa_emp_seq", allocationSize = 1)
	@ApiModelProperty(value = "ID(作成時不要)", required = true, position = 1, allowableValues = "range[0,9223372036854775807]", readOnly = true)
	private long id;

	/**
	 * 見積
	 */
	@OneToOne(optional = false)
	@JoinColumn(name = "estimation_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "見積", required = true, position = 2)
	private Estimation estimation;

	/**
	 * MoM企事部システム連携ID
	 */
	@Column
	@Size(max = 255)
	@ApiModelProperty(value = "MoM企事部システム連携ID", required = false, position = 64, allowableValues = "range[0,255]")
	private String momKjbSystemId;

	/**
	 * MoM企事部ID
	 */
	@Column
	@Size(max = 255)
	@ApiModelProperty(value = "MoM企事部ID", required = false, position = 65, allowableValues = "range[0,255]")
	private String momCustId;

	/**
	 * 販売会社名（カナ）
	 */
	@Column
	@Size(max = 255)
	@ApiModelProperty(value = "販売会社名（カナ）", required = false, position = 66, allowableValues = "range[0,255]")
	private String salesCompanyNameKana;

	/**
	 * MoM非連携_企業代表者名
	 */
	@Column
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_企業代表者名", required = false, position = 67, allowableValues = "range[0,255]")
	private String companyRepresentativeName;

	/**
	 * MoM非連携_企業代表者名（カナ）
	 */
	@Column
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_企業代表者名（カナ）", required = false, position = 68, allowableValues = "range[0,255]")
	private String companyRepresentativeNameKana;

}
