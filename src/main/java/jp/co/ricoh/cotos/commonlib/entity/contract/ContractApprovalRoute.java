package jp.co.ricoh.cotos.commonlib.entity.contract;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.contract.Contract.LifecycleStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 契約承認ルートを表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "contract_approval_route")
public class ContractApprovalRoute extends EntityBase {

	@Id
	@ApiModelProperty(value = "契約承認ルートID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 契約
	 */
	@OneToOne(optional = false)
	@JoinColumn(name = "contract_id", referencedColumnName = "id")
	@ApiModelProperty(value = "契約", required = true, position = 2)
	private Contract contract;

	/**
	 * 対象ライフサイクル状態
	 */
	@ApiModelProperty(value = "対象ライフサイクル状態", required = true, position = 3)
	private LifecycleStatus targetLifecycleStatus;

	/**
	 * 承認依頼者MoM社員ID
	 */
	@ApiModelProperty(value = "承認依頼者MoM社員ID", required = true, position = 4, allowableValues = "range[0,255]")
	private String approvalRequesterEmpId;

	/**
	 * 承認依頼者氏名
	 */
	@ApiModelProperty(value = "承認依頼者氏名", required = true, position = 5, allowableValues = "range[0,255]")
	private String approvalRequesterName;

	/**
	 * 承認依頼者組織名
	 */
	@ApiModelProperty(value = "承認依頼者組織名", required = false, position = 6, allowableValues = "range[0,255]")
	private String approvalRequesterOrgName;

	/**
	 * 特価承認対象フラグ
	 */
	@ApiModelProperty(value = "特価承認対象フラグ", required = true, position = 7, allowableValues = "range[0,9]")
	private int specialPriceApprovalFlg;

	/**
	 * 契約承認実績
	 */
	@OneToMany(mappedBy = "contractApprovalRoute")
	@ApiModelProperty(value = "契約承認実績", required = false, position = 8, readOnly = true)
	private List<ContractApprovalResult> contractApprovalResult;

	/**
	 * 契約承認ルートノード
	 */
	@OneToMany(mappedBy = "contractApprovalRoute")
	@ApiModelProperty(value = "契約承認ルートノード", required = true, position = 9)
	private List<ContractApprovalRouteNode> contractApprovalRouteNode;
}
