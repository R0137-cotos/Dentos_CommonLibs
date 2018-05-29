package jp.co.ricoh.cotos.commonlib.entity.contract;

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
 * 契約承認を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table
public class ContractApprovalRouteNode extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contract_approval_route_node_seq")
	@SequenceGenerator(name = "contract_approval_route_node_seq", sequenceName = "contract_approval_route_node_seq", allocationSize = 1)
	@ApiModelProperty(value = "契約承認ルートノードID", required = true, position = 1)
	private long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "contractApprovalRouteId")
	@JsonIgnore
	private ContractApprovalRoute contractApprovalRoute;

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
	private EmployeeCon approverEmployee;

	@ManyToOne
	@JoinColumn(name = "subApproverEmptxId")
	@ApiModelProperty(value = "社員(代理承認者)", required = false, position = 5)
	private EmployeeCon subApproverEmployee;
}
