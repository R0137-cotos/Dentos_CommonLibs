package jp.co.ricoh.cotos.commonlib.entity.estimation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.master.MvEmployeeMaster;
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
public class EstimationPicSaEmp extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estimation_pic_sa_emp_seq")
	@SequenceGenerator(name = "estimation_pic_sa_emp_seq", sequenceName = "estimation_pic_sa_emp_seq", allocationSize = 1)
	@ApiModelProperty(value = "ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * RJ社員情報マスタ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "momEmployeeId", referencedColumnName = "emp_id")
	@NotNull
	@ApiModelProperty(value = "RJ社員情報マスタ", required = true, position = 2)
	private MvEmployeeMaster mvEmployeeMaster;

	/**
	 * 所属組織MoM組織ID
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "所属組織MoM組織ID", required = false, position = 3, allowableValues = "range[0,255]")
	private String momOrgId;

	/**
	 * 所属組織階層レベル
	 */
	@Max(9)
	@ApiModelProperty(value = "所属組織階層レベル", required = false, position = 4, allowableValues = "range[0,9]")
	private Integer orgHierarchyLevel;

	/**
	 * 所属組織名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "所属組織名", required = false, position = 5, allowableValues = "range[0,255]")
	private String orgName;

	/**
	 * 販売会社名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "販売会社名", required = false, position = 6, allowableValues = "range[0,255]")
	private String salesCompanyName;

	/**
	 * 会社代表電話番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "会社代表電話番号", required = false, position = 7, allowableValues = "range[0,255]")
	private String orgPhoneNumber;

	/**
	 * 社員名
	 */
	@Column(nullable = false)
	@NotEmpty
	@Size(max = 255)
	@ApiModelProperty(value = "社員名", required = true, position = 8, allowableValues = "range[0,255]")
	private String employeeName;

	/**
	 * 部署名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "部署名", required = false, position = 9, allowableValues = "range[0,255]")
	private String salesDepartmentName;

	/**
	 * 郵便番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "郵便番号", required = false, position = 10, allowableValues = "range[0,255]")
	private String postNumber;

	/**
	 * 住所
	 */
	@Size(max = 1000)
	@ApiModelProperty(value = "住所", required = false, position = 11, allowableValues = "range[0,1000]")
	private String address;

	/**
	 * 電話番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "電話番号", required = false, position = 12, allowableValues = "range[0,255]")
	private String phoneNumber;

	/**
	 * FAX番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "FAX番号", required = false, position = 13, allowableValues = "range[0,255]")
	private String faxNumber;

	/**
	 * メールアドレス
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "メールアドレス", required = false, position = 14, allowableValues = "range[0,255]")
	private String mailAddress;

	/**
	 * 見積
	 */
	@OneToOne(optional = false)
	@JoinColumn(name = "estimation_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "見積", required = true, position = 15)
	private Estimation estimation;

}
