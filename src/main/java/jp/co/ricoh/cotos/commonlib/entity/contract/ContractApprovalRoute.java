package jp.co.ricoh.cotos.commonlib.entity.contract;

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
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contract_approval_route_seq")
	@SequenceGenerator(name = "contract_approval_route_seq", sequenceName = "contract_approval_route_seq", allocationSize = 1)
	@ApiModelProperty(value = "契約承認ルートID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 契約
	 */
	@OneToOne(optional = false)
	@JoinColumn(name = "contract_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "契約", required = true, position = 2)
	private Contract contract;

	/**
	 * 対象ライフサイクル状態
	 */
	@Column(nullable = false)
	@NotNull
	@ApiModelProperty(value = "対象ライフサイクル状態", required = true, allowableValues = "作成中(\"1\"), 作成完了(\"2\"), キャンセル手続き中(\"3\"), 破棄(\"4\"), 予定日待ち(\"5\"), 締結中(\"6\"), 解約手続き中(\"7\"), 解約予定日待ち(\"8\"), 解約(\"9\"), 旧契約(\"10\")", example = "1", position = 3)
	private LifecycleStatus targetLifecycleStatus;

	/**
	 * 承認依頼者MoM社員ID
	 */
	@Column(nullable = false)
	@NotEmpty
	@Size(max = 255)
	@ApiModelProperty(value = "承認依頼者MoM社員ID", required = true, position = 4, allowableValues = "range[0,255]")
	private String approvalRequesterEmpId;

	/**
	 * 承認依頼者氏名
	 */
	@Column(nullable = false)
	@NotEmpty
	@Size(max = 255)
	@ApiModelProperty(value = "承認依頼者氏名", required = true, position = 5, allowableValues = "range[0,255]")
	private String approvalRequesterName;

	/**
	 * 承認依頼者組織名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "承認依頼者組織名", required = false, position = 6, allowableValues = "range[0,255]")
	private String approvalRequesterOrgName;

	/**
	 * 特価承認対象フラグ
	 */
	@Column(nullable = false)
	@Max(9)
	@ApiModelProperty(value = "特価承認対象フラグ", required = true, position = 7, allowableValues = "range[0,9]")
	private int specialPriceApprovalFlg;

	/**
	 * 契約承認実績
	 */
	@OneToMany(mappedBy = "contractApprovalRoute")
	@OrderBy("processedAt ASC")
	@ApiModelProperty(value = "契約承認実績", required = false, position = 8, readOnly = true)
	private List<ContractApprovalResult> contractApprovalResultList;

	/**
	 * 契約承認ルートノード
	 */
	@OneToMany(mappedBy = "contractApprovalRoute")
	@ApiModelProperty(value = "契約承認ルートノード", required = true, position = 9)
	@OrderBy("approvalOrder ASC")
	private List<ContractApprovalRouteNode> contractApprovalRouteNodeList;
}
