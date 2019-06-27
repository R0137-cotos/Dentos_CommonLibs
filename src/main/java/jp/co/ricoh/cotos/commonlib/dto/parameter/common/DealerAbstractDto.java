package jp.co.ricoh.cotos.commonlib.dto.parameter.common;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.DealerFlowOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class DealerAbstractDto extends DtoBase {

	/**
	 * MoM企事部システム連携ID
	 */
	@NotEmpty
	@Size(max = 255)
	@ApiModelProperty(value = "MoM企事部システム連携ID", required = true, position = 51, allowableValues = "range[0,255]")
	private String momKjbSystemId;

	/**
	 * 販売店名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "販売店名", required = false, position = 52, allowableValues = "range[0,255]")
	private String dealerName;

	/**
	 * 郵便番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "郵便番号", required = false, position = 53, allowableValues = "range[0,255]")
	private String postNumber;

	/**
	 * 住所
	 */
	@Size(max = 1000)
	@ApiModelProperty(value = "住所", required = false, position = 54, allowableValues = "range[0,1000]")
	private String address;

	/**
	 * 会社代表電話番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "会社代表電話番号", required = false, position = 55, allowableValues = "range[0,255]")
	private String orgPhoneNumber;

	/**
	 * 担当者名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "担当者名", required = false, position = 56, allowableValues = "range[0,255]")
	private String picName;

	/**
	 * 担当者部署名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "担当者部署名", required = false, position = 57, allowableValues = "range[0,255]")
	private String picDeptName;

	/**
	 * 担当者電話番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "担当者電話番号", required = false, position = 58, allowableValues = "range[0,255]")
	private String picPhoneNumber;

	/**
	 * 担当者FAX番号
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "担当者FAX番号", required = false, position = 59, allowableValues = "range[0,255]")
	private String picFaxNumber;

	/**
	 * 販売店商流順
	 */
	@NotNull
	@ApiModelProperty(value = "販売店商流順", required = true, allowableValues = "販売店(\"1\"), 母店(\"2\")", example = "1", position = 60)
	private DealerFlowOrder dealerFlowOrder;

	/**
	 * MoM会社ID
	 */
	@Size(max = 255)
	@Column
	@ApiModelProperty(value = "MoM会社ID", required = false, position = 61, allowableValues = "range[0,255]")
	private String distributorMomCmpId;

	/**
	 * MoM販売店識別コード
	 */
	@Size(max = 255)
	@Column
	@ApiModelProperty(value = "MoM販売店識別コード", required = false, position = 62, allowableValues = "range[0,255]")
	private String distributorMomShikiCd;

	/**
	 * MoM組織ID
	 */
	@Size(max = 255)
	@Column
	@ApiModelProperty(value = "MoM組織ID", required = false, position = 63, allowableValues = "range[0,255]")
	private String distributorMomSoshikiId;

	/**
	 * MoMデポコード
	 */
	@Size(max = 255)
	@Column
	@ApiModelProperty(value = "MoMデポコード", required = false, position = 64, allowableValues = "range[0,255]")
	private String distributorMomDepoCd;

	/**
	 * MoM設置届先サイトID
	 */
	@Size(max = 255)
	@Column
	@ApiModelProperty(value = "MoM設置届先サイトID", required = false, position = 65, allowableValues = "range[0,255]")
	private String orbSendSiteId;

	/**
	 * 担当者名（カナ）
	 */
	@Size(max = 255)
	@Column
	@ApiModelProperty(value = "担当者名（カナ）", required = false, position = 66, allowableValues = "range[0,255]")
	private String picNameKana;

	/**
	 * 販売店名（カナ）
	 */
	@Size(max = 255)
	@Column
	@ApiModelProperty(value = "販売店名（カナ）", required = false, position = 67, allowableValues = "range[0,255]")
	private String dealerNameKana;

	/**
	 * MoM非連携_企業代表者名
	 */
	@Size(max = 255)
	@Column
	@ApiModelProperty(value = "MoM非連携_企業代表者名", required = false, position = 68, allowableValues = "range[0,255]")
	private String companyRepresentativeName;

	/**
	 * MoM非連携_企業代表者名（カナ）
	 */
	@Size(max = 255)
	@Column
	@ApiModelProperty(value = "MoM非連携_企業代表者名（カナ）", required = false, position = 69, allowableValues = "range[0,255]")
	private String companyRepresentativeNameKana;

	/**
	 * MoM企事部ID
	 */
	@Size(max = 255)
	@Column
	@ApiModelProperty(value = "MoM企事部ID", required = false, position = 70, allowableValues = "range[0,255]")
	private String momCustId;

}
