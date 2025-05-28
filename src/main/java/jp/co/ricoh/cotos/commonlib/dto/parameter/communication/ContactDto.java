package jp.co.ricoh.cotos.commonlib.dto.parameter.communication;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Lob;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.DtoBase;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.ServiceCategory;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class ContactDto extends DtoBase {

	/**
	 * 見積ID
	 */
	@Min(0)
	@ApiModelProperty(value = "見積ID", required = true, position = 3, allowableValues = "range[0,9223372036854775807]")
	private long estimationId;

	/**
	 * 子問い合わせリスト
	 */
	@Valid
	@ApiModelProperty(value = "子問い合わせリスト", required = false, position = 4)
	private List<ContactDto> children;

	/**
	 * 送信者MoM社員ID
	 */
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "送信者MoM社員ID (作成時不要)", required = true, position = 5, allowableValues = "range[0,255]", readOnly = true)
	private String contactFromEmpId;

	/**
	 * サービスカテゴリ
	 */
	@ApiModelProperty(value = "サービスカテゴリ", required = false, allowableValues = "見積(\"1\"), 契約(\"2\"), 手配(\"3\")", example = "1", position = 6)
	private ServiceCategory serviceCategory;

	/**
	 * タイトル
	 */
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "タイトル", required = false, position = 7, allowableValues = "range[0,255]")
	private String title;

	/**
	 * 内容
	 */
	@NotNull
	@ApiModelProperty(value = "内容", required = false, position = 8)
	@Lob
	private String content;

	/**
	 * 送信日時
	 */
	@ApiModelProperty(value = "送信日時", required = true, position = 9)
	@Temporal(TemporalType.TIMESTAMP)
	private Date sendAt;

	/**
	 * 送信者氏名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "送信者氏名", required = false, position = 10, allowableValues = "range[0,255]")
	private String contactFromEmpName;

	/**
	 * 宛先
	 */
	@NotNull
	@Valid
	@ApiModelProperty(value = "宛先", required = true, position = 11)
	private List<ContactToDto> contactToList;
}