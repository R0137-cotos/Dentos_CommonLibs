package jp.co.ricoh.cotos.commonlib.entity.estimation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.master.VKjbMaster.DepartmentDiv;
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
public class CustomerEstimation extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_estimation_seq")
	@SequenceGenerator(name = "customer_estimation_seq", sequenceName = "customer_estimation_seq", allocationSize = 1)
	@ApiModelProperty(value = "ID", required = true, position = 1)
	private long id;

	/**
	 * MoM企事部システム連携ID
	 */
	@ApiModelProperty(value = "MoM企事部システム連携ID", required = true, position = 2, allowableValues = "range[0,15]")
	private String momKjbSystemId;

	/**
	 * MoM企事部ID
	 */
	@Column(nullable = false)
	@NotEmpty
	@Size(max = 255)
	@ApiModelProperty(value = "MoM企事部ID", required = true, position = 3, allowableValues = "range[0,255]")
	private String momCustId;

	/**
	 * MoM企業ID
	 */
	@Column(nullable = false)
	@NotEmpty
	@Size(max = 255)
	@ApiModelProperty(value = "MoM企業ID", required = true, position = 4, allowableValues = "range[0,255]")
	private String companyId;

	/**
	 * MoM事業所ID
	 */
	@Column(nullable = false)
	@NotEmpty
	@Size(max = 255)
	@ApiModelProperty(value = "MoM事業所ID", required = true, position = 5, allowableValues = "range[0,255]")
	private String officeId;

	/**
	 * 企事部設定区分
	 */
	@Column(nullable = false)
	@NotNull
	@ApiModelProperty(value = "企事部設定区分", required = true, allowableValues = "企事(\"1\"), 企事部(\"2\")", example = "1", position = 6)
	private DepartmentDiv departmentDiv;

	/**
	 * 顧客名
	 */
	@Column(nullable = false)
	@NotEmpty
	@Size(max = 255)
	@ApiModelProperty(value = "顧客名", required = true, position = 7, allowableValues = "range[0,255]")
	private String customerName;

	/**
	 * 企業名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "企業名", required = false, position = 8, allowableValues = "range[0,255]")
	private String companyName;

	/**
	 * 事業所名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "事業所名", required = false, position = 9, allowableValues = "range[0,255]")
	private String officeName;

	/**
	 * 部門名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "部門名", required = false, position = 10, allowableValues = "range[0,255]")
	private String departmentName;

	/**
	 * 郵便番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "郵便番号", required = false, position = 11, allowableValues = "range[0,255]")
	private String postNumber;

	/**
	 * 住所
	 */
	@Size(max = 1000)
	@ApiModelProperty(value = "住所", required = false, position = 12, allowableValues = "range[0,1000]")
	private String address;

	/**
	 * 電話番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "電話番号", required = false, position = 13, allowableValues = "range[0,255]")
	private String phoneNumber;

	/**
	 * FAX番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "FAX番号", required = false, position = 14, allowableValues = "range[0,255]")
	private String faxNumber;

	/**
	 * 見積
	 */
	@OneToOne(optional = false)
	@JoinColumn(name = "estimation_id", referencedColumnName = "id")
	@ApiModelProperty(value = "見積", required = true, position = 15)
	@JsonIgnore
	private Estimation estimation;

}
