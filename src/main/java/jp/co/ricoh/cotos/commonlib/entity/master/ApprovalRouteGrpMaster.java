package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 承認ルートグループマスタ
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "approval_route_grp_master")
public class ApprovalRouteGrpMaster extends EntityBaseMaster {

	@Id
	@ApiModelProperty(value = "承認ルートグループマスタID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 承認ルートグループ名
	 */
	@ApiModelProperty(value = "承認ルートグループ名", required = true, position = 2, allowableValues = "range[0,255]")
	private String approvalRouteGrpName;

	/**
	 * 説明
	 */
	@ApiModelProperty(value = "説明", required = false, position = 3, allowableValues = "range[0,255]")
	private String description;

	/**
	 * 承認ルートマスタ
	 */
	@OneToMany(mappedBy = "approvalRouteGrpMaster")
	@ApiModelProperty(value = "承認ルートマスタ", required = true, position = 4)
	private List<ApprovalRouteMaster> approvalRouteMasterList;

	/**
	 * 商品グループマスタ（見積承認）
	 */
	@OneToMany(mappedBy = "estimationApprovalRouteGrpMaster")
	@ApiModelProperty(value = "商品グループマスタ（見積承認）", required = true, position = 5)
	private List<ProductGrpMaster> productGrpMasterList;

	/**
	 * 商品グループマスタ（契約承認）
	 */
	@OneToMany(mappedBy = "contractApprovalRouteGrpMaster")
	@ApiModelProperty(value = "商品グループマスタ（契約承認）", required = true, position = 6)
	private List<ProductGrpMaster> contractProductGrpMasterList;

	/**
	 * 手配業務タイプマスタ
	 */
	@OneToMany(mappedBy = "approvalRouteGrpMaster")
	@ApiModelProperty(value = "手配業務タイプマスタ", required = true, position = 7)
	private List<ArrangementWorkTypeMaster> arrangementWorkTypeMasterList;

}
