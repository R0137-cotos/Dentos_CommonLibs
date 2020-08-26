package jp.co.ricoh.cotos.commonlib.entity.common;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.master.VKjbMaster.DepartmentDiv;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * COTOS顧客エンティティー共通項目 COTOSの顧客情報を管理するエンティティーはこのクラスのサブクラスとしてください。
 * 当クラスに項目追加する場合は、以下のクラスにも同様の項目を追加してください。
 * jp.co.ricoh.cotos.commonlib.dto.parameter.common.CustomerAbstractDto
 */
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass
@Data
public class CustomerAbstractEntity extends EntityBase {

	/**
	 * MoM企事部システム連携ID
	 */
	@NotNull
	@Column(nullable = false)
	@Size(max = 255)
	@ApiModelProperty(value = "MoM企事部システム連携ID<br/>※POST時「企事部マスタ」存在チェック実施", required = true, position = 51, allowableValues = "range[0,255]")
	private String momKjbSystemId;

	/**
	 * MoM企事部ID
	 */
	@Column(nullable = false)
	@Size(max = 255)
	@ApiModelProperty(value = "MoM企事部ID(作成時不要)", required = true, position = 52, allowableValues = "range[0,255]", readOnly = true)
	private String momCustId;

	/**
	 * MoM企業ID
	 */
	@Column(nullable = false)
	@Size(max = 255)
	@ApiModelProperty(value = "MoM企業ID(作成時不要)", required = true, position = 53, allowableValues = "range[0,255]", readOnly = true)
	private String companyId;

	/**
	 * MoM事業所ID
	 */
	@Column(nullable = false)
	@Size(max = 255)
	@ApiModelProperty(value = "MoM事業所ID(作成時不要)", required = true, position = 54, allowableValues = "range[0,255]", readOnly = true)
	private String officeId;

	/**
	 * 企事部設定区分
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "企事部設定区分(作成時不要)", required = true, allowableValues = "企事(\"1\"), 企事部(\"2\")", example = "1", position = 55, readOnly = true)
	private DepartmentDiv departmentDiv;

	/**
	 * 顧客名
	 */
	@Column(nullable = false)
	@Size(max = 255)
	@ApiModelProperty(value = "顧客名(作成時不要)", required = true, position = 56, allowableValues = "range[0,255]", readOnly = true)
	private String customerName;

	/**
	 * 企業名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "企業名(作成時不要)", required = false, position = 57, allowableValues = "range[0,255]", readOnly = true)
	private String companyName;

	/**
	 * 企業名（カナ）
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "企業名（カナ）(作成時不要)", required = false, position = 58, allowableValues = "range[0,255]", readOnly = true)
	private String companyNameKana;

	/**
	 * 事業所名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "事業所名(作成時不要)", required = false, position = 59, allowableValues = "range[0,255]", readOnly = true)
	private String officeName;

	/**
	 * 部門名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "部門名(作成時不要)", required = false, position = 60, allowableValues = "range[0,255]", readOnly = true)
	private String departmentName;

	/**
	 * 郵便番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "郵便番号(作成時不要)", required = false, position = 61, allowableValues = "range[0,255]", readOnly = true)
	private String postNumber;

	/**
	 * 住所
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "住所(作成時不要)", required = false, position = 62, allowableValues = "range[0,1000]", readOnly = true)
	private String address;

	/**
	 * 電話番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "電話番号(作成時不要)", required = false, position = 63, allowableValues = "range[0,255]", readOnly = true)
	private String phoneNumber;

	/**
	 * FAX番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "FAX番号(作成時不要)", required = false, position = 64, allowableValues = "range[0,255]", readOnly = true)
	private String faxNumber;

	/**
	 * 企業代表者名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "企業代表者名(作成時不要)", required = false, position = 65, allowableValues = "range[0,255]", readOnly = true)
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
	@Column
	@ApiModelProperty(value = "MoM非連携_企業代表者名（カナ）", required = false, position = 72, allowableValues = "range[0,]")
	private String companyRepresentativeNameKana;

	/**
	 * 企事部マスタから導出済みか
	 */
	@JsonIgnore
	public boolean isAcquiredInfo() {
		// 以下どれか1項目でも設定されていたら導出済みと捉える
		return StringUtils.isNotEmpty(customerName) || StringUtils.isNotEmpty(companyName) || StringUtils.isNotEmpty(companyNameKana) ||
				StringUtils.isNotEmpty(officeName) || StringUtils.isNotEmpty(departmentName) || StringUtils.isNotEmpty(postNumber) ||
				StringUtils.isNotEmpty(address) || StringUtils.isNotEmpty(phoneNumber) || StringUtils.isNotEmpty(faxNumber) ||
				StringUtils.isNotEmpty(companyRepresentativeName);
	}

}
