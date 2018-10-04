package jp.co.ricoh.cotos.commonlib.entity.arrangement;

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
 * 手配業務ルートノードを表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "arrangement_work_approval_route_node")
public class ArrangementWorkApprovalRouteNode extends EntityBase {

	@Id
	@ApiModelProperty(value = "手配業務承認ルートノードID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 手配業務承認ルート
	 */
	@ManyToOne
	@JoinColumn(name = "arrangement_work_approval_route_id", referencedColumnName = "id")
	@ApiModelProperty(value = "手配業務承認ルート", required = true, position = 2)
	private ArrangementWorkApprovalRoute arrangementWorkApprovalRoute;

	/**
	 * 承認順
	 */
	@ApiModelProperty(value = "承認順", required = true, position = 3, allowableValues = "range[0,999]")
	private long approvalOrder;

	/**
	 * 承認者組織階層レベル
	 */
	@ApiModelProperty(value = "承認者組織階層レベル", required = false, position = 4, allowableValues = "range[0,9]")
	private Integer approverOrgLevel;

	/**
	 * 承認者MoM社員ID
	 */
	@ApiModelProperty(value = "承認者MoM社員ID", required = true, position = 5, allowableValues = "range[0,255]")
	private String approverEmpId;

	/**
	 * 承認者氏名
	 */
	@ApiModelProperty(value = "承認者氏名", required = true, position = 6, allowableValues = "range[0,255]")
	private String approverName;

	/**
	 * 承認者組織名
	 */
	@ApiModelProperty(value = "承認者組織名", required = false, position = 7, allowableValues = "range[0,255]")
	private String approverOrgName;

	/**
	 * 代理承認者MoM社員ID
	 */
	@ApiModelProperty(value = "代理承認者MoM社員ID", required = false, position = 8, allowableValues = "range[0,255]")
	private String subApproverEmpId;

	/**
	 * 代理承認者氏名
	 */
	@ApiModelProperty(value = "代理承認者氏名", required = false, position = 9, allowableValues = "range[0,255]")
	private String subApproverName;

	/**
	 * 代理承認者組織名
	 */
	@ApiModelProperty(value = "代理承認者組織名", required = false, position = 10, allowableValues = "range[0,255]")
	private String subApproverOrgName;

}
