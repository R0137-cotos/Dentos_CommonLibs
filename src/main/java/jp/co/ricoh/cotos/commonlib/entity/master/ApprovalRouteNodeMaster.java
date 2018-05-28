package jp.co.ricoh.cotos.commonlib.entity.master;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jp.co.ricoh.cotos.commonlib.converter.ApproverDeriveMethodDivConverter;
import lombok.Data;

/**
 * 承認ルートノードマスタ
 */
@Entity
@Data
@Table(name = "approval_route_node_master")
public class ApprovalRouteNodeMaster {

	public enum ApproverDeriveMethodDiv {

		直属上司指定("1"), 組織絶対階層指定("2"), 組織直接指定("3"), ユーザー直接指定("4");

		private String value;

		ApproverDeriveMethodDiv(final String value) {
			this.value = value;
		}

		public String toValue() {
			return this.value;
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "approval_route_node_master_seq")
	@SequenceGenerator(name = "approval_route_node_master_seq", sequenceName = "approval_route_node_master_seq", allocationSize = 1)
	private long id;

	/**
	 * 承認順
	 */
	private long approvalOrder;

	/**
	 * 承認者導出方式区分
	 */
	@Convert(converter = ApproverDeriveMethodDivConverter.class)
	@Column(length = 1, nullable = false)
	private ApproverDeriveMethodDiv approverDeriveMethodDiv;

	/**
	 * 組織階層レベル
	 */
	private Integer hierarchyLevel;

	/**
	 * MoM組織ID
	 */
	@Column(length = 255)
	private String momOrgId;

	/**
	 * MoM社員ID
	 */
	@Column(length = 255)
	private String momEmpId;

	/**
	 * 承認ルートマスタ
	 */
	@ManyToOne
	@JoinColumn(name = "approval_route_id")
	@JsonIgnore
	private ApprovalRouteMaster approvalRouteMaster;

}
