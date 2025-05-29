
package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.Date;
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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Size;

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
	private String productGroupCd;

	/**
	 * 初期費内部振替対象フラグ
	 */
	@ApiModelProperty(value = "初期費内部振替対象フラグ", required = false, position = 9, allowableValues = "range[0,9]")
	private Integer initialExpensesInsideTransFlg;

	/**
	 * 期間売内部振替対象フラグ
	 */
	@ApiModelProperty(value = "期間売内部振替対象フラグ", required = false, position = 10, allowableValues = "range[0,9]")
	private Integer periodSellingInsideTransFlg;

	/**
	 * VUP連携商品フラグ
	 */
	@ApiModelProperty(value = "VUP連携商品フラグ", required = false, position = 11, allowableValues = "range[0,9]")
	private Integer vupLinkageProductFlg;
}
