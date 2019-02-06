package jp.co.ricoh.cotos.commonlib.entity.estimation;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 見積承認ルートを表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "estimation_approval_route")
public class EstimationApprovalRoute extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estimation_approval_route_seq")
	@SequenceGenerator(name = "estimation_approval_route_seq", sequenceName = "estimation_approval_route_seq", allocationSize = 1)
	@ApiModelProperty(value = "見積承認ルートID(作成時不要)", required = true, position = 1, allowableValues = "range[0,9999999999999999999]", readOnly = true)
	private long id;

	/**
	 * 見積
	 */
	@OneToOne
	@ApiModelProperty(value = "見積", required = true, position = 2)
	@JoinColumn(name = "estimation_id", referencedColumnName = "id")
	@JsonIgnore
	private Estimation estimation;

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
	 * 特価承認対象フラグ
	 */
	@Column(nullable = false)
	@Max(9)
	@Min(0)
	@ApiModelProperty(value = "特価承認対象フラグ", required = true, position = 6, allowableValues = "range[0,9]")
	private int specialPriceApprovalFlg;

	/**
	 * 見積承認実績
	 */
	@OneToMany(mappedBy = "estimationApprovalRoute")
	@OrderBy("processedAt ASC")
	@Valid
	@ApiModelProperty(value = "見積承認実績(作成時不要)", required = false, position = 7, readOnly = true)
	private List<EstimationApprovalResult> estimationApprovalResultList;

	/**
	 * 見積承認ルートノード
	 */
	@NotNull
	@OneToMany(mappedBy = "estimationApprovalRoute")
	@OrderBy("approvalOrder ASC")
	@Valid
	@ApiModelProperty(value = "見積承認ルートノード(作成時不要)", required = true, position = 8, readOnly = true)
	private List<EstimationApprovalRouteNode> estimationApprovalRouteNodeList;

}
