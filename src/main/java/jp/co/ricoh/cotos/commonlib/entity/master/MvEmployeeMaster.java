package jp.co.ricoh.cotos.commonlib.entity.master;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * MoM社員マテビューを表すEntity
 */
@Entity
@Data
@Table(name = "mv_employee_master")
public class MvEmployeeMaster {

	/**
	 * MoM社員ID
	 */
	@Id
	@ApiModelProperty(value = "MoM社員ID", required = true, position = 1, allowableValues = "range[0,255]")
	private String momEmpId;

	/**
	 * MoM会社ID
	 */
	@ApiModelProperty(value = "MoM会社ID", required = true, position = 2, allowableValues = "range[0,255]")
	private String momCompanyId;

	/**
	 * SINGLE_USER_ID
	 */
	@ApiModelProperty(value = "SINGLE_USER_ID", required = true, position = 3, allowableValues = "range[0,255]")
	private String singleUserId;
}
