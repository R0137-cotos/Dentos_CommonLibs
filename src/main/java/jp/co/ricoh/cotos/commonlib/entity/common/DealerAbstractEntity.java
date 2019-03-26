package jp.co.ricoh.cotos.commonlib.entity.common;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.DealerFlowOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * COTOS販売店エンティティー共通項目 COTOSの販売店情報を管理するエンティティーはこのクラスのサブクラスとしてください。
 * 当クラスに項目追加する場合は、以下のクラスにも同様の項目を追加してください。
 * jp.co.ricoh.cotos.commonlib.dto.parameter.common.DealerAbstractDto
 */
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass
@Data
public class DealerAbstractEntity extends EntityBase {

	/**
	 * MoM企事部システム連携ID
	 */
	@Column(nullable = false)
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "MoM企事部システム連携ID<br/>※POST時「企事部マスタ」存在チェック実施", required = true, position = 51, allowableValues = "range[0,255]")
	private String momKjbSystemId;

	/**
	 * 販売店名
	 */
	@ApiModelProperty(value = "販売店名(作成時不要)", required = false, position = 52, allowableValues = "range[0,255]", readOnly = true)
	private String dealerName;

	/**
	 * 郵便番号
	 */
	@ApiModelProperty(value = "郵便番号(作成時不要)", required = false, position = 53, allowableValues = "range[0,255]", readOnly = true)
	private String postNumber;

	/**
	 * 住所
	 */
	@ApiModelProperty(value = "住所(作成時不要)", required = false, position = 54, allowableValues = "range[0,1000]", readOnly = true)
	private String address;

	/**
	 * 会社代表電話番号
	 */
	@ApiModelProperty(value = "会社代表電話番号(作成時不要)", required = false, position = 55, allowableValues = "range[0,255]", readOnly = true)
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
	@Column(nullable = false)
	@NotNull
	@ApiModelProperty(value = "販売店商流順", required = true, allowableValues = "販売店(\"1\"), 母店(\"2\")", example = "1", position = 60)
	private DealerFlowOrder dealerFlowOrder;
}
