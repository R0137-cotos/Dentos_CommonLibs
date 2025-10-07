package jp.co.ricoh.cotos.commonlib.entity.arrangement;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OrderBy;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.master.ApprovalRouteNodeMaster.ApproverDeriveMethodDiv;
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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "arrangement_work_approval_route_node_seq")
	@SequenceGenerator(name = "arrangement_work_approval_route_node_seq", sequenceName = "arrangement_work_approval_route_node_seq", allocationSize = 1)
	@ApiModelProperty(value = "手配業務承認ルートノードID (作成時不要)", required = true, position = 1, allowableValues = "range[0,9223372036854775807]", readOnly = true)
	private long id;

	/**
	 * 手配業務承認ルート
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "arrangement_work_approval_route_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "手配業務承認ルート", required = true, position = 2)
	private ArrangementWorkApprovalRoute arrangementWorkApprovalRoute;

	/**
	 * 承認順
	 */
	@Column(nullable = false)
	@OrderBy("desc")
	@Max(999)
	@Min(0)
	@ApiModelProperty(value = "承認順", required = true, position = 3, allowableValues = "range[0,999]")
	private int approvalOrder;

	/**
	 * 承認者組織階層レベル
	 */
	@Max(9)
	@Min(0)
	@ApiModelProperty(value = "承認者組織階層レベル", required = false, position = 4, allowableValues = "range[0,9]")
	private Integer approverOrgLevel;

	/**
	 * 承認者MoM社員ID
	 */
	@Column(nullable = false)
	@Size(max = 255)
	@NotNull
	@ApiModelProperty(value = "承認者MoM社員ID", required = true, position = 5, allowableValues = "range[0,255]")
	private String approverEmpId;

	/**
	 * 承認者氏名
	 */
	@Column(nullable = false)
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "承認者氏名", required = true, position = 6, allowableValues = "range[0,255]")
	private String approverName;

	/**
	 * 承認者組織名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "承認者組織名", required = false, position = 7, allowableValues = "range[0,255]")
	private String approverOrgName;

	/**
	 * 代理承認者MoM社員ID
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "代理承認者MoM社員ID", required = false, position = 8, allowableValues = "range[0,255]")
	private String subApproverEmpId;

	/**
	 * 代理承認者氏名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "代理承認者氏名", required = false, position = 9, allowableValues = "range[0,255]")
	private String subApproverName;

	/**
	 * 代理承認者組織名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "代理承認者組織名", required = false, position = 10, allowableValues = "range[0,255]")
	private String subApproverOrgName;

	/**
	 * 承認者導出方式区分
	 */
	@ApiModelProperty(value = "承認者導出方式区分", required = false, allowableValues = "直属上司指定(\"1\"), 組織絶対階層指定(\"2\"), 組織直接指定(\"3\"), ユーザー直接指定(\"4\")", example = "1", position = 11)
	private ApproverDeriveMethodDiv approverDeriveMethodDiv;
}
