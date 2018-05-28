package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

/**
 * 承認ルートグループマスタ
 */
@Entity
@Data
@Table(name = "approval_route_grp_master")
public class ApprovalRouteGrpMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "approval_route_grp_master_seq")
	@SequenceGenerator(name = "approval_route_grp_master_seq", sequenceName = "approval_route_grp_master_seq", allocationSize = 1)
	private long id;

	/**
	 * 承認ルートグループ名
	 */
	@Column(length = 255, nullable = false)
	private String approvalRouteGrpName;

	/**
	 * 説明
	 */
	@Column(length = 255)
	private String description;

	/**
	 * 承認ルートマスタリスト
	 */
	@OneToMany(mappedBy = "approvalRouteGrpMaster", fetch = FetchType.EAGER)
	@OrderBy("condDetermineOrder ASC")
	private List<ApprovalRouteMaster> approvalRouteMasterList;

}
