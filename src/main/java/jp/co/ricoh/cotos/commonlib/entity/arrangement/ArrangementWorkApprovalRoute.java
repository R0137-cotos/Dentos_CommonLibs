package jp.co.ricoh.cotos.commonlib.entity.arrangement;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderBy;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 手配業務に設定される承認ルートを表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "arrangement_work_approval_route")
@ApiModel(description = "手配業務承認ルート(作成時不要)")
public class ArrangementWorkApprovalRoute extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "arrangement_work_approval_route_seq")
	@SequenceGenerator(name = "arrangement_work_approval_route_seq", sequenceName = "arrangement_work_approval_route_seq", allocationSize = 1)
	@ApiModelProperty(value = "手配業務承認ルートID (作成時不要)", required = true, position = 1, allowableValues = "range[0,9223372036854775807]", readOnly = true)
	private long id;

	/**
	 * 手配業務
	 */
	@OneToOne(optional = false)
	@JsonIgnore
	@JoinColumn(name = "arrangement_work_id", referencedColumnName = "id")
	@ApiModelProperty(value = "手配業務", required = true, position = 2)
	private ArrangementWork arrangementWork;

	/**
	 * 承認依頼者MoM社員ID
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "承認依頼者MoM社員ID", required = true, position = 3, allowableValues = "range[0,255]")
	private String approvalRequesterEmpId;

	/**
	 * 承認依頼者氏名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "承認依頼者氏名", required = true, position = 4, allowableValues = "range[0,255]")
	private String approvalRequesterName;

	/**
	 * 承認依頼者組織名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "承認依頼者組織名", required = false, position = 5, allowableValues = "range[0,255]")
	private String approvalRequesterOrgName;

	/**
	 * 承認ルートマスタID
	 */
	@ApiModelProperty(value = "承認ルートマスタID", required = false, position = 6)
	private Long approvalRouteMasterId;

	/**
	 * 手配業務承認実績
	 */
	@OneToMany(mappedBy = "arrangementWorkApprovalRoute")
	@OrderBy("processedAt ASC")
	@ApiModelProperty(value = "手配業務承認実績", required = false, position = 7)
	private List<ArrangementWorkApprovalResult> arrangementWorkApprovalResultList;

	/**
	 * 手配業務承認ルートノード
	 */
	@OneToMany(mappedBy = "arrangementWorkApprovalRoute")
	@OrderBy("approvalOrder ASC")
	@ApiModelProperty(value = "手配業務承認ルートノード", required = true, position = 8)
	private List<ArrangementWorkApprovalRouteNode> arrangementWorkApprovalRouteNodeList;

}
