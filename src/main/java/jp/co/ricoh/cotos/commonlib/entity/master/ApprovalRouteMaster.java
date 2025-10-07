package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 承認ルートマスタ
 */
@Entity
@Data
@ToString(exclude = { "approvalRouteGrpMaster" })
@EqualsAndHashCode(callSuper = true)
@Table(name = "approval_route_master")
public class ApprovalRouteMaster extends EntityBaseMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "approval_route_master_seq")
	@SequenceGenerator(name = "approval_route_master_seq", sequenceName = "approval_route_master_seq", allocationSize = 1)
	@ApiModelProperty(value = "承認ルートマスタID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 承認ルートグループマスタ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "approval_route_grp_id", referencedColumnName = "id")
	@ApiModelProperty(value = "承認ルートグループマスタ", required = true, position = 2)
	@JsonIgnore
	private ApprovalRouteGrpMaster approvalRouteGrpMaster;

	/**
	 * 承認ルート名
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "承認ルート名", required = true, position = 3, allowableValues = "range[0,255]")
	private String approvalRouteName;

	/**
	 * 説明
	 */
	@ApiModelProperty(value = "説明", required = false, position = 4, allowableValues = "range[0,255]")
	private String description;

	/**
	 * 特価承認対象フラグ
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "特価承認対象フラグ", required = true, position = 5, allowableValues = "range[0,9]")
	private int specialPriceApprovalFlg;

	/**
	 * ルート条件式
	 */
	@ApiModelProperty(value = "ルート条件式", required = false, position = 6)
	private String routeConditionFormula;

	/**
	 * 条件判定順
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "条件判定順 ", required = true, position = 7, allowableValues = "range[0,999]")
	private int condDetermineOrder;

	/**
	 * 承認ルートノードマスタ
	 */
	@OneToMany(mappedBy = "approvalRouteMaster")
	@ApiModelProperty(value = "承認ルートノードマスタ", required = true, position = 8)
	private List<ApprovalRouteNodeMaster> approvalRouteNodeMasterList;

}
