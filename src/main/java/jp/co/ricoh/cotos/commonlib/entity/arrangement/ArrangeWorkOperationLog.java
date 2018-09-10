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
 * 手配業務操作履歴を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "arrangement_work_operation_log")
public class ArrangeWorkOperationLog extends EntityBase {

	@Id
	@ApiModelProperty(value = "手配業務操作履歴ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 手配業務
	 */
	@ManyToOne
	@ApiModelProperty(value = "手配業務", required = true, position = 2)
	private ArrangementWork arrangementWork;

	/**
	 * 操作内容
	 */
	@ApiModelProperty(value = "操作内容", required = true, position = 3, allowableValues = "range[0,1000]")
	private String operation;

	/**
	 * 操作者MoM社員ID
	 */
	@ApiModelProperty(value = "操作者MoM社員ID", required = true, position = 4, allowableValues = "range[0,255]")
	private String opetatorEmpId;

	/**
	 * 操作者氏名
	 */
	@ApiModelProperty(value = "操作者氏名", required = true, position = 5, allowableValues = "range[0,255]")
	private String opetatorName;

	/**
	 * 操作者組織名
	 */
	@ApiModelProperty(value = "操作者組織名", required = false, position = 6, allowableValues = "range[0,255]")
	private String opetatorOrgName;

	/**
	 * 実施日時
	 */
	@ApiModelProperty(value = "実施日時", required = true, position = 7)
	@Temporal(TemporalType.TIMESTAMP)
	private Date operatedAt;

}
