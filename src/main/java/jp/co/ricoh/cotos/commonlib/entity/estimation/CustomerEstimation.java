package jp.co.ricoh.cotos.commonlib.entity.estimation;

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
 * 見積情報の顧客を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@EntityListeners(CustomerEstimationListener.class)
@Data
@Table(name = "customer_estimation")
public class CustomerEstimation extends CustomerAbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_estimation_seq")
	@SequenceGenerator(name = "customer_estimation_seq", sequenceName = "customer_estimation_seq", allocationSize = 1)
	@ApiModelProperty(value = "ID(作成時不要)", required = true, position = 1, readOnly = true)
	private long id;

	/**
	 * MoM非連携_企業代表者名(カナ)
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_企業代表者名(カナ)", required = false, position = 2, allowableValues = "range[0,255]")
	private String companyRepresentativeNameKana;

	/**
	 * 見積
	 */
	@OneToOne(optional = false)
	@JoinColumn(name = "estimation_id", referencedColumnName = "id")
	@ApiModelProperty(value = "見積", required = true, position = 3)
	@JsonIgnore
	private Estimation estimation;

}
