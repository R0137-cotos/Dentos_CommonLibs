package jp.co.ricoh.cotos.commonlib.dto.parameter.estimation;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.common.AttachedFile;
import lombok.Data;

@Data
public class EstimationAttachedFileDto {

	/**
	 * ID
	 */
	@ApiModelProperty(value = "ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * version
	 */
	@ApiModelProperty(value = "version", required = true, position = 2, allowableValues = "range[0,9999999999999999999]")
	private long version;

	/**
	 * ファイル名
	 */
	@ApiModelProperty(value = "ファイル名", required = true, position = 3, allowableValues = "range[0,255]")
	private String fileName;

	/**
	 * ファイル種類
	 */
	@ApiModelProperty(value = "ファイル種類", required = false, position = 4, allowableValues = "range[0,255]")
	private String fileKind;

	/**
	 * 添付ファイル
	 */
	@OneToOne(optional = false)
	@JoinColumn(name = "attached_file_id", referencedColumnName = "id")
	@ApiModelProperty(value = "添付ファイル", required = true, position = 5)
	private AttachedFile attachedFile;

	/**
	 * コメント
	 */
	@ApiModelProperty(value = "コメント", required = false, position = 6, allowableValues = "range[0,1000]")
	private String attachedComment;

	/**
	 * 添付者MoM社員ID
	 */
	@ApiModelProperty(value = "添付者MoM社員ID", required = true, position = 7, allowableValues = "range[0,255]")
	private String attachedEmpId;

	/**
	 * 添付者氏名
	 */
	@ApiModelProperty(value = "添付者氏名", required = true, position = 8, allowableValues = "range[0,255]")
	private String attachedEmpName;

	/**
	 * 添付者組織名
	 */
	@ApiModelProperty(value = "添付者組織名", required = false, position = 9, allowableValues = "range[0,255]")
	private String attachedOrgName;
	/**
	 * ファイル情報
	 */
	@Transient
	@ApiModelProperty(hidden = true)
	private MultipartFile multipartFile;
}
