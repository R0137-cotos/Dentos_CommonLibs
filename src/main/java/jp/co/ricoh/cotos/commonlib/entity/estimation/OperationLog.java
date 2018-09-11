package jp.co.ricoh.cotos.commonlib.entity.estimation;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 見積操作履歴を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "operation_log")
public class OperationLog extends EntityBase {

	@Id
	@ApiModelProperty(value = "操作履歴ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 見積
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "estimation_id", referencedColumnName = "id")
	@ApiModelProperty(value = "見積", required = true, position = 2)
	private Estimation estimation;

	/**
	 * 操作内容
	 */
	@ApiModelProperty(value = "操作内容", required = true, position = 3, allowableValues = "range[0,1000]")
	private String operation;

	/**
	 * 操作者MoM社員ID
	 */
	@ApiModelProperty(value = "操作者MoM社員ID", required = true, position = 4)
	private String operatorEmpId;

	/**
	 * 操作者氏名
	 */
	@ApiModelProperty(value = "操作者氏名", required = false, position = 5, allowableValues = "range[0,255]")
	private String operatorName;

	/**
	 * 操作者組織名
	 */
	@ApiModelProperty(value = "操作者組織名", required = false, position = 6, allowableValues = "range[0,255]")
	private String operatorOrgName;

	/**
	 * 実施日時
	 */
	@ApiModelProperty(value = "実施日時", required = true, position = 7)
	private Date operatedAt;
}
