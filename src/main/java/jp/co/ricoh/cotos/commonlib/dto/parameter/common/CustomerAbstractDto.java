package jp.co.ricoh.cotos.commonlib.dto.parameter.common;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.master.VKjbMaster.DepartmentDiv;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class CustomerAbstractDto extends DtoBase {

	/**
	 * MoM企事部システム連携ID
	 */
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "MoM企事部システム連携ID", required = true, position = 51, allowableValues = "range[0,255]")
	private String momKjbSystemId;

	/**
	 * MoM企事部ID
	 */
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "MoM企事部ID", required = true, position = 52, allowableValues = "range[0,255]")
	private String momCustId;

	/**
	 * MoM企業ID
	 */
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "MoM企業ID", required = true, position = 53, allowableValues = "range[0,255]")
	private String companyId;

	/**
	 * MoM事業所ID
	 */
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "MoM事業所ID", required = true, position = 54, allowableValues = "range[0,255]")
	private String officeId;

	/**
	 * 企事部設定区分
	 */
	@NotNull
	@ApiModelProperty(value = "企事部設定区分", required = true, allowableValues = "企事(\"1\"), 企事部(\"2\")", example = "1", position = 55)
	private DepartmentDiv departmentDiv;

	/**
	 * 顧客名
	 */
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "顧客名", required = true, position = 56, allowableValues = "range[0,255]")
	private String customerName;

	/**
	 * 企業名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "企業名", required = false, position = 57, allowableValues = "range[0,255]")
	private String companyName;

	/**
	 * 企業名（カナ）
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "企業名（カナ）", required = false, position = 58, allowableValues = "range[0,255]")
	private String companyNameKana;

	/**
	 * 事業所名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "事業所名", required = false, position = 59, allowableValues = "range[0,255]")
	private String officeName;

	/**
	 * 部門名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "部門名", required = false, position = 60, allowableValues = "range[0,255]")
	private String departmentName;

	/**
	 * 郵便番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "郵便番号", required = false, position = 61, allowableValues = "range[0,255]")
	private String postNumber;

	/**
	 * 住所
	 */
	@Size(max = 1000)
	@ApiModelProperty(value = "住所", required = false, position = 62, allowableValues = "range[0,1000]")
	private String address;

	/**
	 * 電話番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "電話番号", required = false, position = 63, allowableValues = "range[0,255]")
	private String phoneNumber;

	/**
	 * FAX番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "FAX番号", required = false, position = 64, allowableValues = "range[0,255]")
	private String faxNumber;

	/**
	 * 企業代表者名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "企業代表者名", required = false, position = 65, allowableValues = "range[0,255]")
	private String companyRepresentativeName;

	/**
	 * MoM非連携_担当者氏名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_担当者氏名", required = false, position = 66, allowableValues = "range[0,255]")
	private String picName;

	/**
	 * MoM非連携_担当者氏名（カナ）
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_担当者氏名（カナ）", required = false, position = 67, allowableValues = "range[0,255]")
	private String picNameKana;

	/**
	 * MoM非連携_担当者部署
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_担当者部署", required = false, position = 68, allowableValues = "range[0,255]")
	private String picDeptName;

	/**
	 * MoM非連携_担当者電話番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_担当者電話番号", required = false, position = 69, allowableValues = "range[0,255]")
	private String picPhoneNumber;

	/**
	 * MoM非連携_担当者FAX番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_担当者FAX番号", required = false, position = 70, allowableValues = "range[0,255]")
	private String picFaxNumber;

	/**
	 * MoM非連携_担当者メールアドレス
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_担当者メールアドレス", required = false, position = 71, allowableValues = "range[0,255]")
	private String picMailAddress;

	/**
	 * MoM非連携_企業代表者名（カナ）
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "MoM非連携_企業代表者名（カナ）", required = false, position = 72, allowableValues = "range[0,255]")
	private String companyRepresentativeNameKana;

}
