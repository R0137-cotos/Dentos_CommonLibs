package jp.co.ricoh.cotos.commonlib.dto.parameter.common;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 添付ファイル情報を表すDto
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AttachedFileDto extends DtoBase {

	/**
	 * 物理ファイル名
	 */
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "物理ファイル名", required = true, position = 2, allowableValues = "range[0,255]")
	private String filePhysicsName;

	/**
	 * ファイルサイズ
	 */
	@Min(0)
	@ApiModelProperty(value = "ファイルサイズ", required = true, position = 3, allowableValues = "range[0,9999999999999999999]")
	private long fileSize;

	/**
	 * コンテンツタイプ
	 */
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "コンテンツタイプ", required = true, position = 4, allowableValues = "range[0,255]")
	private String contentType;

	/**
	 * サーバーパス
	 */
	@NotNull
	@Size(max = 1000)
	@ApiModelProperty(value = "サーバーパス", required = true, position = 5, allowableValues = "range[0,1000]")
	private String savedPath;
}
