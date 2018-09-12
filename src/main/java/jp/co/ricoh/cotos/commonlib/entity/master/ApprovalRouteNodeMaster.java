package jp.co.ricoh.cotos.commonlib.entity.master;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 承認ルートノードマスタ
 */
@Entity
@Data
@Table(name = "approval_route_node_master")
public class ApprovalRouteNodeMaster {

	public enum AuthorizerClass {

		メイン承認者("1"), 代理承認者("2");

		private String value;

		AuthorizerClass(final String value) {
			this.value = value;
		}

		public String toValue() {
			return this.value;
		}
	}

	public enum AuthorizerDeriveMethodDiv {

		直属上司指定("1"), 組織絶対階層指定("2"), 組織直接指定("3"), ユーザー直接指定("4");

		private String value;

		AuthorizerDeriveMethodDiv(final String value) {
			this.value = value;
		}

		public String toValue() {
			return this.value;
		}
	}

	@Id
	@ApiModelProperty(value = "承認ルートノードマスタID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 承認ルートマスタ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "approval_route_id", referencedColumnName = "id")
	@ApiModelProperty(value = "承認ルートマスタ", required = true, position = 2)
	private ApprovalRouteMaster approvalRouteMaster;

	/**
	 * 承認順
	 */
	@ApiModelProperty(value = "承認順", required = true, position = 3, allowableValues = "range[0,999]")
	private int approvalOrder;

	/**
	 * 承認者種別
	 */
	@ApiModelProperty(value = "承認者種別", required = true, position = 4)
	private AuthorizerClass authorizerClass;

	/**
	 * 承認者導出方式区分
	 */
	@ApiModelProperty(value = "承認者導出方式区分", required = true, position = 5)
	private AuthorizerDeriveMethodDiv approverDeriveMethodDiv;

	/**
	 * 組織階層レベル
	 */
	@ApiModelProperty(value = "承認順", required = false, position = 6, allowableValues = "range[0,9]")
	private Integer hierarchyLevel;

	/**
	 * MoM組織ID
	 */
	@ApiModelProperty(value = "MoM組織ID", required = false, position = 7, allowableValues = "range[0,255]")
	private String momOrgId;

	/**
	 * MoM社員ID
	 */
	@ApiModelProperty(value = "MoM社員ID", required = false, position = 8, allowableValues = "range[0,255]")
	private String momEmpId;

}
