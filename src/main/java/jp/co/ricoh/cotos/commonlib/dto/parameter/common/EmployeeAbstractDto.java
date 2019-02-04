package jp.co.ricoh.cotos.commonlib.dto.parameter.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EmployeeAbstractDto {

	/**
	 * MoM社員ID
	 */
	@ApiModelProperty(value = "MoM社員ID", required = true, position = 51, allowableValues = "range[0,24]")
	private String momEmployeeId;

	/**
	 * 所属組織MoM組織ID
	 */
	@ApiModelProperty(value = "所属組織MoM組織ID", required = false, position = 52, allowableValues = "range[0,255]")
	private String momOrgId;

	/**
	 * 所属組織階層レベル
	 */
	@ApiModelProperty(value = "所属組織階層レベル", required = false, position = 53, allowableValues = "range[0,9]")
	private Integer orgHierarchyLevel;

	/**
	 * 所属組織名
	 */
	@ApiModelProperty(value = "所属組織名", required = false, position = 54, allowableValues = "range[0,255]")
	private String orgName;

	/**
	 * 販売会社名
	 */
	@ApiModelProperty(value = "販売会社名", required = false, position = 55, allowableValues = "range[0,255]")
	private String salesCompanyName;

	/**
	 * 会社代表電話番号
	 */
	@ApiModelProperty(value = "会社代表電話番号", required = false, position = 56, allowableValues = "range[0,255]")
	private String orgPhoneNumber;

	/**
	 * 社員名
	 */
	@ApiModelProperty(value = "社員名", required = true, position = 57, allowableValues = "range[0,255]")
	private String employeeName;

	/**
	 * 部署名
	 */
	@ApiModelProperty(value = "部署名", required = false, position = 58, allowableValues = "range[0,255]")
	private String salesDepartmentName;

	/**
	 * 郵便番号
	 */
	@ApiModelProperty(value = "郵便番号", required = false, position = 59, allowableValues = "range[0,255]")
	private String postNumber;

	/**
	 * 住所
	 */
	@ApiModelProperty(value = "住所", required = false, position = 60, allowableValues = "range[0,1000]")
	private String address;

	/**
	 * 電話番号
	 */
	@ApiModelProperty(value = "電話番号", required = false, position = 61, allowableValues = "range[0,255]")
	private String phoneNumber;

	/**
	 * FAX番号
	 */
	@ApiModelProperty(value = "FAX番号", required = false, position = 62, allowableValues = "range[0,255]")
	private String faxNumber;

	/**
	 * メールアドレス
	 */
	@ApiModelProperty(value = "メールアドレス", required = false, position = 63, allowableValues = "range[0,255]")
	private String mailAddress;
}
