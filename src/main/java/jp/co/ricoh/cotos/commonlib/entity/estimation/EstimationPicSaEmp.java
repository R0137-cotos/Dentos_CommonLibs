package jp.co.ricoh.cotos.commonlib.entity.estimation;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
	 * MoM非連携_MoM企事部システム連携ID
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_MoM企事部システム連携ID", required = false, position = 2, allowableValues = "range[0,255]")
	private String momKjbSystemId;

	/**
	 * MoM非連携_MoM企事部ID
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_MoM企事部ID", required = false, position = 3, allowableValues = "range[0,255]")
	private String momCustId;

	/**
	 * 販売店名（カナ）
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "販売店名（カナ）", required = false, position = 4, allowableValues = "range[0,255]")
	private String salesCompanyNameKana;

	/**
	 * MoM非連携_企業代表者名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_企業代表者名", required = false, position = 5, allowableValues = "range[0,255]")
	private String companyRepresentativeName;

	/**
	 * MoM非連携_企業代表者名(カナ)
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_企業代表者名(カナ)", required = false, position = 6, allowableValues = "range[0,255]")
	private String companyRepresentativeNameKana;

	/**
	 * 見積
	 */
	@OneToOne(optional = false)
	@JoinColumn(name = "estimation_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "見積", required = true, position = 7)
	private Estimation estimation;

}
