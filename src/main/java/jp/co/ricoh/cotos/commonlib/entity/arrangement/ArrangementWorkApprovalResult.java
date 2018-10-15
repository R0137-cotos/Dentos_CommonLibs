package jp.co.ricoh.cotos.commonlib.entity.arrangement;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.ApprovalProcessCategory;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 手配業務の承認実績を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "arrangement_work_approval_result")
public class ArrangementWorkApprovalResult extends EntityBase {

	@Id
	@ApiModelProperty(value = "手配業務承認実績ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 手配業務承認ルート
	 */
	@ManyToOne(optional = true)
	@JoinColumn(name = "arrange_work_approval_route_id", referencedColumnName = "id")
	@ApiModelProperty(value = "手配業務承認ルート", required = true, position = 2)
	private ArrangementWorkApprovalRoute arrangementWorkApprovalRoute;

	/**
	 * 承認処理カテゴリ
	 */
	@ApiModelProperty(value = "承認処理カテゴリ", required = true, position = 3, allowableValues = "range[0,255]")
	private ApprovalProcessCategory approvalProcessCategory;

	/**
	 * 処理実施者MoM社員ID
	 */
	@ApiModelProperty(value = "処理実施者MoM社員ID", required = true, position = 4, allowableValues = "range[0,255]")
	private String actualEmpId;

	/**
	 * 処理実施者氏名
	 */
	@ApiModelProperty(value = "処理実施者氏名", required = true, position = 5, allowableValues = "range[0,255]")
	private String actualUserName;

	/**
	 * 処理実施者組織名
	 */
	@ApiModelProperty(value = "処理実施者組織名", required = false, position = 6, allowableValues = "range[0,255]")
	private String actualOrgName;

	/**
	 * コメント
	 */
	@ApiModelProperty(value = "コメント", required = false, position = 7, allowableValues = "range[0,255]")
	private String requestComment;

	/**
	 * 実施日時
	 */
	@ApiModelProperty(value = "実施日時", required = true, position = 8, readOnly = true)
	private Date processedAt;

	@PrePersist
	public void prePersist() {
		this.processedAt = new Date();
	}
}
