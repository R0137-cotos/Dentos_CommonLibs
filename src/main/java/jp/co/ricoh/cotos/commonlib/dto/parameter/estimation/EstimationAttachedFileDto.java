package jp.co.ricoh.cotos.commonlib.dto.parameter.estimation;

import java.util.Date;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.AttachedFileDto;
import jp.co.ricoh.cotos.commonlib.dto.parameter.common.DtoBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class EstimationAttachedFileDto extends DtoBase {

	/**
	 * ファイル名
	 */
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "ファイル名", required = true, position = 3, allowableValues = "range[0,255]")
	private String fileName;

	/**
	 * ファイル種類
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "ファイル種類", required = false, position = 4, allowableValues = "range[0,255]")
	private String fileKind;

	/**
	 * 添付ファイル
	 */
	@Valid
	@NotNull
	@JoinColumn(name = "attached_file_id", referencedColumnName = "id")
	@ApiModelProperty(value = "添付ファイル", required = true, position = 5)
	private AttachedFileDto attachedFile;

	/**
	 * コメント
	 */
	@Size(max = 1000)
	@ApiModelProperty(value = "コメント", required = false, position = 6, allowableValues = "range[0,1000]")
	private String attachedComment;

	/**
	 * 添付者MoM社員ID
	 */
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "添付者MoM社員ID", required = true, position = 7, allowableValues = "range[0,255]")
	private String attachedEmpId;

	/**
	 * 添付者氏名
	 */
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "添付者氏名", required = true, position = 8, allowableValues = "range[0,255]")
	private String attachedEmpName;

	/**
	 * 添付者組織名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "添付者組織名", required = false, position = 9, allowableValues = "range[0,255]")
	private String attachedOrgName;

	/**
	 * 添付日時
	 */
	@ApiModelProperty(value = "添付日時", required = true, position = 10)
	@Temporal(TemporalType.TIMESTAMP)
	private Date attachedAt;
}
