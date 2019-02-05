package jp.co.ricoh.cotos.commonlib.entity.common;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * COTOS社員エンティティー共通項目 COTOSの社員情報を管理するエンティティーはこのクラスのサブクラスとしてください。
 * 当クラスに項目追加する場合は、以下のクラスにも同様の項目を追加してください。
 * jp.co.ricoh.cotos.commonlib.dto.parameter.common.EmployeeAbstractDto
 */
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass
@Data
public class EmployeeAbstractEntity extends EntityBase {

	/**
	 * MoM社員ID
	 */
	@NotEmpty
	@ApiModelProperty(value = "MoM社員ID", required = true, position = 51, allowableValues = "range[0,24]")
	private String momEmployeeId;

	/**
	 * 所属組織MoM組織ID
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "所属組織MoM組織ID(作成時不要)", required = false, position = 52, allowableValues = "range[0,255]", readOnly = true)
	private String momOrgId;

	/**
	 * 所属組織階層レベル
	 */
	@Max(9)
	@ApiModelProperty(value = "所属組織階層レベル(作成時不要)", required = false, position = 53, allowableValues = "range[0,9]", readOnly = true)
	private Integer orgHierarchyLevel;

	/**
	 * 所属組織名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "所属組織名(作成時不要)", required = false, position = 54, allowableValues = "range[0,255]", readOnly = true)
	private String orgName;

	/**
	 * 販売会社名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "販売会社名(作成時不要)", required = false, position = 55, allowableValues = "range[0,255]", readOnly = true)
	private String salesCompanyName;

	/**
	 * 会社代表電話番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "会社代表電話番号(作成時不要)", required = false, position = 56, allowableValues = "range[0,255]", readOnly = true)
	private String orgPhoneNumber;

	/**
	 * 社員名
	 */
	@NotEmpty
	@Size(max = 255)
	@Column(nullable = false)
	@ApiModelProperty(value = "社員名(作成時不要)", required = true, position = 57, allowableValues = "range[0,255]", readOnly = true)
	private String employeeName;

	/**
	 * 部署名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "部署名(作成時不要)", required = false, position = 58, allowableValues = "range[0,255]", readOnly = true)
	private String salesDepartmentName;

	/**
	 * 郵便番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "郵便番号(作成時不要)", required = false, position = 59, allowableValues = "range[0,255]", readOnly = true)
	private String postNumber;

	/**
	 * 住所
	 */
	@Size(max = 1000)
	@ApiModelProperty(value = "住所(作成時不要)", required = false, position = 60, allowableValues = "range[0,1000]", readOnly = true)
	private String address;

	/**
	 * 電話番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "電話番号(作成時不要)", required = false, position = 61, allowableValues = "range[0,255]", readOnly = true)
	private String phoneNumber;

	/**
	 * FAX番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "FAX番号(作成時不要)", required = false, position = 62, allowableValues = "range[0,255]", readOnly = true)
	private String faxNumber;

	/**
	 * メールアドレス
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "メールアドレス(作成時不要)", required = false, position = 63, allowableValues = "range[0,255]", readOnly = true)
	private String mailAddress;
}
