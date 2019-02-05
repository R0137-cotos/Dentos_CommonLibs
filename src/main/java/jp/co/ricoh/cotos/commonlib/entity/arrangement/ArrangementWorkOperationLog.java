package jp.co.ricoh.cotos.commonlib.entity.arrangement;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 手配業務操作履歴を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@EntityListeners(ArrangementWorkOperationLogListener.class)
@Data
@Table(name = "arrangement_work_operation_log")
public class ArrangementWorkOperationLog extends EntityBase {

	public enum Operation {
		業務受付, 業務受付取消, 作業完了報告, 新規作成, 担当作業者設定, 更新, 作業完了, 業務完了取消, 保留更新,
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "arrangement_work_operation_log_seq")
	@SequenceGenerator(name = "arrangement_work_operation_log_seq", sequenceName = "arrangement_work_operation_log_seq", allocationSize = 1)
	@ApiModelProperty(value = "手配業務操作履歴ID (作成時不要)", required = true, position = 1, allowableValues = "range[0,9999999999999999999]", readOnly = true)
	private long id;

	/**
	 * 手配業務
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "arrangement_work_id", referencedColumnName = "id")
	@ApiModelProperty(value = "手配業務", required = true, position = 2)
	@JsonIgnore
	private ArrangementWork arrangementWork;

	/**
	 * 操作内容
	 */
	@Column(nullable = false)
	@NotNull
	@ApiModelProperty(value = "操作内容", required = true, position = 3, allowableValues = "range[0,1000]")
	@Enumerated(EnumType.STRING)
	private Operation operation;

	/**
	 * 操作者MoM社員ID
	 */
	@Column(nullable = false)
	@NotEmpty
	@Size(max = 255)
	@ApiModelProperty(value = "操作者MoM社員ID", required = true, position = 4, allowableValues = "range[0,255]")
	private String operatorEmpId;

	/**
	 * 操作者氏名
	 */
	@Column(nullable = false)
	@NotEmpty
	@Size(max = 255)
	@ApiModelProperty(value = "操作者氏名", required = true, position = 5, allowableValues = "range[0,255]")
	private String operatorName;

	/**
	 * 操作者組織名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "操作者組織名", required = false, position = 6, allowableValues = "range[0,255]")
	private String operatorOrgName;

	/**
	 * 実施日時
	 */
	@Column(nullable = false)
	@NotNull
	@ApiModelProperty(value = "実施日時", required = true, position = 7)
	@Temporal(TemporalType.TIMESTAMP)
	private Date operatedAt;

	@PrePersist
	public void prePersist() {
		super.prePersist();
		this.operatedAt = super.getCreatedAt();
	}

}
