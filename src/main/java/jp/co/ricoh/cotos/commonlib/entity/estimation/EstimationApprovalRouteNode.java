package jp.co.ricoh.cotos.commonlib.entity.estimation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 見積承認を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table
public class EstimationApprovalRouteNode extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estimation_approval_seq")
	@SequenceGenerator(name = "estimation_approval_seq", sequenceName = "estimation_approval_seq", allocationSize = 1)
	@ApiModelProperty(value = "見積承認ルートノードID", required = true, position = 1)
	private long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "estimationApprovalRouteId")
	@JsonIgnore
	private EstimationApprovalRoute estimationApprovalRoute;

	/**
	 * 承認順
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "承認順", required = true, position = 2)
	private long approvalOrder;

	/**
	 * 組織階層レベル
	 */
	@ApiModelProperty(value = "組織階層レベル", required = false, position = 3)
	private Integer hierarchyLevel;

	@ManyToOne(optional = false)
	@JoinColumn(name = "approverEmptxId")
	@ApiModelProperty(value = "社員(承認者)", required = true, position = 4)
	private Employee approverEmployee;

	@ManyToOne
	@JoinColumn(name = "subApproverEmptxId")
	@ApiModelProperty(value = "社員(代理承認者)", required = false, position = 5)
	private Employee subApproverEmployee;
}
