package jp.co.ricoh.cotos.commonlib.entity.contract;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.master.MvEmployeeMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 契約情報の中で保持する担当SA社員を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "contract_pic_sa_emp")
public class ContractPicSaEmp extends EntityBase {

	@Id
	@ApiModelProperty(value = "ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * RJ社員情報マスタ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "mom_employee_id", referencedColumnName = "momEmpId")
	@ApiModelProperty(value = "RJ社員情報マスタ", required = true, position = 2)
	private MvEmployeeMaster mvEmployeeMaster;

	/**
	 * 所属組織MoM組織ID
	 */
	@ApiModelProperty(value = "所属組織MoM組織ID", required = false, position = 3, allowableValues = "range[0,255]")
	private String momOrgId;

	/**
	 * 所属組織階層レベル
	 */
	@ApiModelProperty(value = "所属組織階層レベル", required = false, position = 4, allowableValues = "range[0,9]")
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
	private String orgTel;

	/**
	 * 社員名
	 */
	@ApiModelProperty(value = "社員名", required = true, position = 8, allowableValues = "range[0,255]")
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
	@ApiModelProperty(value = "住所", required = false, position = 11, allowableValues = "range[0,1000]")
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

	/**
	 * 契約
	 */
	@OneToOne(optional = false)
	@JoinColumn(name = "contract_id", referencedColumnName = "id")
	@ApiModelProperty(value = "契約", required = true, position = 15)
	private Contract contract;
}
