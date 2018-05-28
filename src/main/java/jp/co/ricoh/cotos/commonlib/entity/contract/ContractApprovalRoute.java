package jp.co.ricoh.cotos.commonlib.entity.contract;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 契約承認ルートを表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table
public class ContractApprovalRoute extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contract_approval_route_seq")
	@SequenceGenerator(name = "contract_approval_route_seq", sequenceName = "contract_approval_route_seq", allocationSize = 1)
	@ApiModelProperty(value = "契約承認ルートID", required = true, position = 1)
	private long id;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "contractApprovalRoute")
	@OrderBy("approvalOrder ASC")
	@ApiModelProperty(value = "契約承認ルートノード", required = false, position = 2)
	private List<ContractApprovalRouteNode> contractApprovalRouteNodeList;

	/**
	 * 契約
	 */
	@OneToOne(optional = false)
	@JoinColumn(name = "contractId", referencedColumnName = "id")
	@JsonIgnore
	private Contract contract;

	/**
	 * 社員
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "approvalRequesterEmptxId")
	@ApiModelProperty(value = "社員(承認依頼者)", required = false, position = 3)
	private EmployeeCon approvalRequester;

	/**
	 * 売価承認フラグ
	 */
	@Column(length = 1)
	@ApiModelProperty(value = "売価承認フラグ", required = false, position = 4)
	private boolean specialPriceApprovalFlg;

	@OneToMany(mappedBy = "contractApprovalRoute")
	@ApiModelProperty(value = "見積承認実績", required = false, position = 5)
	private List<ContractApprovalResult> contractApprovalResult;
}
