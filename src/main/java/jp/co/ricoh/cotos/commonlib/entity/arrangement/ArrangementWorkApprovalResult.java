package jp.co.ricoh.cotos.commonlib.entity.arrangement;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
@EntityListeners(ArrangementWorkApprovalResultListener.class)
@Data
@Table(name = "arrangement_work_approval_result")
public class ArrangementWorkApprovalResult extends EntityBase {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "arrangement_work_approval_result_seq")
	@SequenceGenerator(name = "arrangement_work_approval_result_seq", sequenceName = "arrangement_work_approval_result_seq", allocationSize = 1)
	@ApiModelProperty(value = "手配業務承認実績ID (作成時不要)", required = true, position = 1, allowableValues = "range[0,9223372036854775807]", readOnly = true)
	private long id;

	/**
	 * 手配業務承認ルート
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "arrangement_work_approval_route_id", referencedColumnName = "id")
	@ApiModelProperty(value = "手配業務承認ルート", required = true, position = 2)
	@JsonIgnore
	private ArrangementWorkApprovalRoute arrangementWorkApprovalRoute;

	/**
	 * 手配業務承認ルートノードID
	 */
	@ApiModelProperty(value = "手配業務承認ルートノードID", required = false, position = 3)
	private Long arrangementWorkApprovalRouteNodeId;

	/**
	 * 承認処理カテゴリー
	 */
	@Column(nullable = false)
	@NotNull
	@ApiModelProperty(value = "承認処理カテゴリー", required = true, allowableValues = "承認依頼(\"1\"), 承認依頼差戻(\"2\"), 承認(\"3\"), 承認依頼取消(\"4\")", example = "1", position = 4)
	private ApprovalProcessCategory approvalProcessCategory;

	/**
	 * 処理実施者MoM社員ID
	 */
	@Column(nullable = false)
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "処理実施者MoM社員ID<br/>※POST時「RJ社員情報マスタ」存在チェック実施", required = true, position = 5, allowableValues = "range[0,255]")
	private String actualEmpId;

	/**
	 * 処理実施者社員名
	 */
	@Column(nullable = false)
	@NotNull
	@Size(max = 255)
	@ApiModelProperty(value = "処理実施者社員名", required = true, position = 6, allowableValues = "range[0,255]")
	private String actualUserName;

	/**
	 * 処理実施者組織名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "処理実施者組織名", required = false, position = 7, allowableValues = "range[0,255]")
	private String actualOrgName;

	/**
	 * コメント
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "コメント", required = false, position = 8, allowableValues = "range[0,255]")
	private String requestComment;

	/**
	 * 実施日時
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "実施日時(作成時不要)", required = true, position = 9, readOnly = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date processedAt;

	@PrePersist
	public void prePersist() {
		super.prePersist();
		this.processedAt = super.getCreatedAt();
	}
}
