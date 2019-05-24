
package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 商品グループマスタ
 */
@Entity
@Data
@ToString(exclude = { "estimationApprovalRouteGrpMaster", "contractApprovalRouteGrpMaster" })
@EqualsAndHashCode(callSuper = true)
@Table(name = "product_grp_master")
public class ProductGrpMaster extends EntityBaseMaster {

	/**
	 * 商品グループマスタID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_grp_master_seq")
	@SequenceGenerator(name = "product_grp_master_seq", sequenceName = "product_grp_master_seq", allocationSize = 1)
	@ApiModelProperty(value = "商品グループマスタID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 見積承認ルートグループマスタ
	 */
	@ManyToOne
	@JoinColumn(name = "estimation_approval_route_grp_id", referencedColumnName = "id")
	@ApiModelProperty(value = "承認ルートグループマスタ（見積）", required = false, position = 2)
	private ApprovalRouteGrpMaster estimationApprovalRouteGrpMaster;

	/**
	 * 契約承認ルートグループマスタ
	 */
	@ManyToOne
	@JoinColumn(name = "contract_approval_route_grp_id", referencedColumnName = "id")
	@ApiModelProperty(value = "承認ルートグループマスタ（契約）", required = false, position = 3)
	private ApprovalRouteGrpMaster contractApprovalRouteGrpMaster;

	/**
	 * 商品構成マスタ
	 */
	@OneToMany(mappedBy = "productGrpMaster")
	@JsonIgnore
	@ApiModelProperty(value = "商品構成マスタ", required = false, position = 4)
	private List<ProductCompMaster> productCompMasterList;

	/**
	 * 商品グループ名
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "商品グループ名", required = true, position = 5, allowableValues = "range[255]")
	private String productGrpName;

	/**
	 * 積上げ可能期間(開始日)
	 */
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "積上げ可能期間(開始日)", required = true, position = 6)
	private Date effectiveFrom;

	/**
	 * 積上げ可能期間(終了日)
	 */
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "積上げ可能期間(終了日)", required = true, position = 7)
	private Date effectiveTo;

	/**
	 * 商品グループコード
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "商品グループコード", required = false, position = 8, allowableValues = "range[0,255]")
	private String productGrpCode;
}
