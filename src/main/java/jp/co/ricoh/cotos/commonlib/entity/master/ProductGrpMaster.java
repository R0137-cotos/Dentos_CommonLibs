package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品グループマスタ
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "product_grp_master")
public class ProductGrpMaster extends EntityBaseMaster {

	/**
	 * 商品グループマスタID
	 */
	@Id
	@ApiModelProperty(value = "商品グループマスタID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 承認ルートグループマスタ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "estimation_approval_route_grp_id")	// TODO ER図では外部キーが不明
	@ApiModelProperty(value = "承認ルートグループマスタ", required = false, position = 3)
	private ApprovalRouteGrpMaster approvalRouteGrpMaster;

	/**
	 * 承認ルートグループマスタ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "constract_approval_route_grp_id")
	@ApiModelProperty(value = "承認ルートグループマスタ", required = false, position = 5)
	private ApprovalRouteGrpMaster contractApprovalRouteGrpMaster;

	/**
	 * 商品グループ名
	 */
	@ApiModelProperty(value = "商品グループ名", required = true, position = 6, allowableValues = "range[255]")
	private long productGrpName;

	/**
	 * 積上げ可能期間(開始日)
	 */
	@ApiModelProperty(value = "積上げ可能期間(開始日)", required = true, position = 7)
	private Date effectiveFrom;

	/**
	 * 積上げ可能期間(終了日)
	 */
	@ApiModelProperty(value = "積上げ可能期間(終了日)", required = true, position = 8)
	private Date effectiveTo;

}
