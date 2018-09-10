package jp.co.ricoh.cotos.commonlib.entity.arrangement;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 手配業務に紐づく添付ファイル情報を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "work_attached_file")
public class WorkAttachedFile extends EntityBase {

	@Id
	@ApiModelProperty(value = "手配業務添付ファイルID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 手配業務
	 */
	@ManyToOne
	@ApiModelProperty(value = "手配業務", required = true, position = 2)
	private ArrangementWork arrangementWork;

	/**
	 * ファイル種類
	 */
	@ApiModelProperty(value = "ファイル種類", required = false, position = 3, allowableValues = "range[0,1000]")
	private String fileKind;

	/**
	 * 添付ファイルパス
	 */
	@ApiModelProperty(value = "添付ファイルパス", required = true, position = 4, allowableValues = "range[0,1023]")
	private String attachedFilePass;

	/**
	 * コメント
	 */
	@ApiModelProperty(value = "コメント", required = false, position = 5, allowableValues = "range[0,1000]")
	private String comment;

	/**
	 * 添付者MoM社員ID
	 */
	@ApiModelProperty(value = "添付者MoM社員ID", required = true, position = 6, allowableValues = "range[0,255]")
	private String attachedEmpId;

	/**
	 * 添付者氏名
	 */
	@ApiModelProperty(value = "添付者氏名", required = true, position = 7, allowableValues = "range[0,255]")
	private String attachedEmpName;

	/**
	 * 添付者組織名
	 */
	@ApiModelProperty(value = "添付者組織名", required = false, position = 8, allowableValues = "range[0,255]")
	private String attachedOrgName;

	/**
	 * 添付日時
	 */
	@ApiModelProperty(value = "添付日時", required = true, position = 9)
	@Temporal(TemporalType.TIMESTAMP)
	private Date attachedAt;

}
