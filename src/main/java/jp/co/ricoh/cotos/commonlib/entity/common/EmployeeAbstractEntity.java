package jp.co.ricoh.cotos.commonlib.entity.common;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	@NotNull
	@Column(nullable = false)
	@Size(max = 255)
	@ApiModelProperty(value = "MoM社員ID<br/>※POST時「RJ社員情報マスタ」存在チェック実施", required = true, position = 51, allowableValues = "range[0,255]")
	private String momEmployeeId;

	/**
	 * 所属組織MoM組織ID
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "所属組織MoM組織ID", required = false, position = 52, allowableValues = "range[0,255]", readOnly = false)
	private String momOrgId;

	/**
	 * 所属組織階層レベル
	 */
	@ApiModelProperty(value = "所属組織階層レベル", required = false, position = 53, allowableValues = "range[0,9]", readOnly = false)
	private Integer orgHierarchyLevel;

	/**
	 * 所属組織名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "所属組織名", required = false, position = 54, allowableValues = "range[0,255]", readOnly = false)
	private String orgName;

	/**
	 * 販売会社名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "販売会社名", required = false, position = 55, allowableValues = "range[0,255]", readOnly = false)
	private String salesCompanyName;

	/**
	 * 会社代表電話番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "会社代表電話番号", required = false, position = 56, allowableValues = "range[0,255]", readOnly = false)
	private String orgPhoneNumber;

	/**
	 * 社員名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "社員名", required = false, position = 57, allowableValues = "range[0,255]", readOnly = false)
	private String employeeName;

	/**
	 * 部署名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "部署名", required = false, position = 58, allowableValues = "range[0,255]", readOnly = false)
	private String salesDepartmentName;

	/**
	 * 郵便番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "郵便番号", required = false, position = 59, allowableValues = "range[0,255]", readOnly = false)
	private String postNumber;

	/**
	 * 住所
	 */
	@Size(max = 1000)
	@ApiModelProperty(value = "住所", required = false, position = 60, allowableValues = "range[0,1000]", readOnly = false)
	private String address;

	/**
	 * 電話番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "電話番号", required = false, position = 61, allowableValues = "range[0,255]", readOnly = false)
	private String phoneNumber;

	/**
	 * FAX番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "FAX番号", required = false, position = 62, allowableValues = "range[0,255]", readOnly = false)
	private String faxNumber;

	/**
	 * メールアドレス
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "メールアドレス", required = false, position = 63, allowableValues = "range[0,255]", readOnly = false)
	private String mailAddress;

	/**
	 * RJ社員マスタから導出済みか
	 */
	@JsonIgnore
	public boolean isAcquiredInfo() {
		// 以下どれか1項目でも設定されていたら導出済みと捉える
		return StringUtils.isNotEmpty(orgName) || StringUtils.isNotEmpty(salesCompanyName) || StringUtils.isNotEmpty(orgPhoneNumber) ||
				StringUtils.isNotEmpty(employeeName) || StringUtils.isNotEmpty(salesDepartmentName) || StringUtils.isNotEmpty(postNumber) ||
				StringUtils.isNotEmpty(address) || StringUtils.isNotEmpty(phoneNumber) || StringUtils.isNotEmpty(faxNumber) ||
				StringUtils.isNotEmpty(mailAddress);
	}

}
