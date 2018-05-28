package jp.co.ricoh.cotos.commonlib.entity.master;

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
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * 承認ルートマスタ
 */
@Entity
@Data
@Table(name = "approval_route_master")
public class ApprovalRouteMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "approval_route_master_seq")
	@SequenceGenerator(name = "approval_route_master_seq", sequenceName = "approval_route_master_seq", allocationSize = 1)
	private long id;

	/**
	 * 承認ルート名
	 */
	@Column(length = 255, nullable = false)
	private String approvalRouteName;

	/**
	 * 説明
	 */
	@Column(length = 255)
	private String description;

	/**
	 * 特価承認対象フラグ
	 */
	@Column(length = 1)
	private boolean specialPriceApprovalFlg;

	/**
	 * ルート条件式
	 */
	@Column(length = 1000, nullable = false)
	private String routeConditionFormula;

	/**
	 * 条件判定順 (そのルート条件式が適用される判定順を示す)
	 */
	private long condDetermineOrder;

	/**
	 * 承認ルートノードマスタリスト
	 */
	@OneToMany(mappedBy = "approvalRouteMaster", fetch = FetchType.LAZY)
	@OrderBy("approvalOrder ASC")
	private List<ApprovalRouteNodeMaster> approvalRouteNodeMasterList;

	/**
	 * 承認ルートグループマスタ
	 */
	@ManyToOne
	@JoinColumn(name = "approval_route_grp_id")
	@JsonIgnore
	private ApprovalRouteGrpMaster approvalRouteGrpMaster;

}
