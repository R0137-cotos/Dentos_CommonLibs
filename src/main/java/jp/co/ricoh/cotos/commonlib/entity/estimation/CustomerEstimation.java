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
	 * 見積
	 */
	@OneToOne(optional = false)
	@JoinColumn(name = "estimation_id", referencedColumnName = "id")
	@ApiModelProperty(value = "見積", required = true, position = 2)
	@JsonIgnore
	private Estimation estimation;

}
