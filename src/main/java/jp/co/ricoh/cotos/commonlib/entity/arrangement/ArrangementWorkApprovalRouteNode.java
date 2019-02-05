package jp.co.ricoh.cotos.commonlib.entity.arrangement;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "arrangement_work_approval_route_node_seq")
	@SequenceGenerator(name = "arrangement_work_approval_route_node_seq", sequenceName = "arrangement_work_approval_route_node_seq", allocationSize = 1)
	@ApiModelProperty(value = "手配業務承認ルートノードID (作成時不要)", required = true, position = 1, allowableValues = "range[0,9999999999999999999]", readOnly = true)
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
	@Max(999L)
	@ApiModelProperty(value = "承認順", required = true, position = 3, allowableValues = "range[0,999]")
	private long approvalOrder;

	/**
	 * 承認者組織階層レベル
	 */
	@Max(9)
	@ApiModelProperty(value = "承認者組織階層レベル", required = false, position = 4, allowableValues = "range[0,9]")
	private Integer approverOrgLevel;

	/**
	 * 承認者MoM社員ID
	 */
	@Column(nullable = false)
	@NotEmpty
	@Size(max = 255)
	@ApiModelProperty(value = "承認者MoM社員ID (作成時不要)", required = true, position = 5, allowableValues = "range[0,255]", readOnly = true)
	private String approverEmpId;

	/**
	 * 承認者氏名
	 */
	@Column(nullable = false)
	@NotEmpty
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
	@ApiModelProperty(value = "代理承認者MoM社員ID (作成時不要)", required = false, position = 8, allowableValues = "range[0,255]", readOnly = true)
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

}
