package jp.co.ricoh.cotos.commonlib.entity.estimation;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.common.DealerAbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 見積情報の中で保持する販売店情報を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@EntityListeners(DealerEstimationListener.class)
@Data
@Table(name = "dealer_estimation")
public class DealerEstimation extends DealerAbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dealer_estimation_seq")
	@SequenceGenerator(name = "dealer_estimation_seq", sequenceName = "dealer_estimation_seq", allocationSize = 1)
	@ApiModelProperty(value = "ID(作成時不要)", required = true, position = 1, readOnly = true)
	private long id;

	/**
	 * 見積
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "estimation_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "見積", required = true, position = 2)
	private Estimation estimation;

	/**
	 * 担当者メールアドレス
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "担当者メールアドレス", required = false, position = 3, allowableValues = "range[0,255]")
	private String picMailAddress;

}
