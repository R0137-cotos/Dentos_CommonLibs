package jp.co.ricoh.cotos.commonlib.entity.arrangement;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.master.EmployeeMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 手配情報の中で保持する社員情報を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@EntityListeners(EmployeeArrangeListener.class)
@Data
@Table(name = "employee_arrange")
public class EmployeeArrange extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_arrange_seq")
	@SequenceGenerator(name = "employee_arrange_seq", sequenceName = "employee_arrange_seq", allocationSize = 1)
	@ApiModelProperty(value = "手配担当社員ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]", readOnly = true)
	private long id;

	/**
	 * 社員マスター
	 */
	@ManyToOne
	@JoinColumn(name = "momEmployeeId", nullable = false)
	@ApiModelProperty(value = "社員マスター", required = false, position = 2)
	private EmployeeMaster employeeMaster;

	/**
	 * 所属組織MoM組織ID
	 */
	@ApiModelProperty(value = "所属組織MoM組織ID", required = false, position = 3, allowableValues = "range[0,255]")
	private String momOrgId;

	/**
	 * 所属組織階層レベル
	 */
	@ApiModelProperty(value = "所属組織階層レベル", required = false, position = 4)
	private Integer orgHierarchyLevel;

	/**
	 * 所属組織名
	 */
	@ApiModelProperty(value = "所属組織名", required = false, position = 5, allowableValues = "range[0,255]")
	private String orgName;

	/**
	 * 販売会社名
	 */
	@ApiModelProperty(value = "販売会社名", required = false, position = 6, allowableValues = "range[0,255]")
	private String salesCompanyName;

	/**
	 * 会社代表電話番号
	 */
	@ApiModelProperty(value = "会社代表電話番号", required = false, position = 7, allowableValues = "range[0,255]")
	private String orgPhoneNumber;

	/**
	 * 社員名
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "社員名", required = false, position = 8, allowableValues = "range[0,255]")
	private String employeeName;

	/**
	 * 部署名
	 */
	@ApiModelProperty(value = "部署名", required = false, position = 9, allowableValues = "range[0,255]")
	private String salesDepartmentName;

	/**
	 * 郵便番号
	 */
	@ApiModelProperty(value = "郵便番号", required = false, position = 10, allowableValues = "range[0,255]")
	private String postNumber;

	/**
	 * 住所
	 */
	@ApiModelProperty(value = "住所", required = false, position = 11, allowableValues = "range[0,255]")
	private String address;

	/**
	 * 電話番号
	 */
	@ApiModelProperty(value = "電話番号", required = false, position = 12, allowableValues = "range[0,255]")
	private String phoneNumber;

	/**
	 * FAX番号
	 */
	@ApiModelProperty(value = "FAX番号", required = false, position = 13, allowableValues = "range[0,255]")
	private String faxNumber;

	/**
	 * メールアドレス
	 */
	@ApiModelProperty(value = "メールアドレス", required = false, position = 14, allowableValues = "range[0,255]")
	private String mailAddress;
}
