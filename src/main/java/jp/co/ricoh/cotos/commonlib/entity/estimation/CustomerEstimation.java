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
	@ApiModelProperty(value = "ID", required = true, position = 1)
	private long id;

	/**
	 * MoM非連携_担当者氏名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_担当者氏名", required = false, position = 2, allowableValues = "range[0,255]")
	private String picName;

	/**
	 * MoM非連携_担当者氏名（カナ）
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_担当者氏名（カナ）", required = false, position = 3, allowableValues = "range[0,255]")
	private String picNameKana;

	/**
	 * MoM非連携_担当者部署
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_担当者部署", required = false, position = 4, allowableValues = "range[0,255]")
	private String picDeptName;

	/**
	 * MoM非連携_担当者電話番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_担当者電話番号", required = false, position = 5, allowableValues = "range[0,255]")
	private String picPhoneNumber;

	/**
	 * MoM非連携_担当者FAX番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_担当者FAX番号", required = false, position = 6, allowableValues = "range[0,255]")
	private String picFaxNumber;

	/**
	 * MoM非連携_担当者メールアドレス
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_担当者メールアドレス", required = false, position = 7, allowableValues = "range[0,255]")
	private String picMailAddress;

	/**
	 * 見積
	 */
	@OneToOne(optional = false)
	@JoinColumn(name = "estimation_id", referencedColumnName = "id")
	@ApiModelProperty(value = "見積", required = true, position = 8)
	@JsonIgnore
	private Estimation estimation;

}
