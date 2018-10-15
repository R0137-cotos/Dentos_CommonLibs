package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	@Column(nullable = false)
 	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_grp_master")
 	@SequenceGenerator(name = "product_grp_master", sequenceName = "product_grp_master", allocationSize = 1)
	@ApiModelProperty(value = "商品グループマスタID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 見積承認ルートグループマスタ
	 */
	@ManyToOne(optional = false)
	@Column(nullable = false)
	@JoinColumn(name = "estimation_approval_route_grp_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "承認ルートグループマスタ", required = true, position = 2)
	private ApprovalRouteGrpMaster estimationApprovalRouteGrpMaster;

	/**
	 * 契約承認ルートグループマスタ
	 */
	@ManyToOne(optional = false)
	@Column(nullable = false)
	@JoinColumn(name = "contract_approval_route_grp_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "契約承認ルートグループマスタ", required = true, position = 3)
	private ApprovalRouteGrpMaster contractApprovalRouteGrpMaster;

	/**
	 * 商品グループ名
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "商品グループ名", required = true, position = 4, allowableValues = "range[255]")
	private long productGrpName;

	/**
	 * 積上げ可能期間(開始日)
	 */
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "積上げ可能期間(開始日)", required = true, position = 5)
	private Date effectiveFrom;

	/**
	 * 積上げ可能期間(終了日)
	 */
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "積上げ可能期間(終了日)", required = true, position = 6)
	private Date effectiveTo;

}
