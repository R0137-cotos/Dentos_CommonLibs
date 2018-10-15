package jp.co.ricoh.cotos.commonlib.entity.estimation;

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
 * 見積承認実績を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "estimation_approval_result")
public class EstimationApprovalResult extends EntityBase {

	@Id
	@ApiModelProperty(value = "見積承認実績ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 見積承認ルート
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "estimation_approval_route_id", referencedColumnName = "id")
	@ApiModelProperty(value = "見積承認ルート", required = true, position = 2)
	private EstimationApprovalRoute estimationApprovalRoute;

	/**
	 * 承認処理カテゴリー
	 */
	@ApiModelProperty(value = "承認処理カテゴリー", required = true, position = 3)
	private ApprovalProcessCategory approvalProcessCategory;

	/**
	 * 処理実施者MoM社員ID
	 */
	@ApiModelProperty(value = "処理実施者MoM社員ID", required = true, position = 4, allowableValues = "range[0,255]")
	private String actualEmpId;

	/**
	 * 処理実施者社員名
	 */
	@ApiModelProperty(value = "処理実施者社員名", required = true, position = 5, allowableValues = "range[0,255]")
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
