package jp.co.ricoh.cotos.commonlib.entity.estimation;

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
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 見積操作履歴を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@EntityListeners(OperationLogListener.class)
@Data
@Table(name = "operation_log")
public class OperationLog extends EntityBase {

	public enum Operation {
		キャンセル, 新規作成, 新規作成_再見積, 新規作成_コピー, 新規作成_プラン変更, 受注, 失注, 更新, 業務依頼, 業務処理完了
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operation_log_seq")
	@SequenceGenerator(name = "operation_log_seq", sequenceName = "operation_log_seq", allocationSize = 1)
	@ApiModelProperty(value = "操作履歴ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 見積
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "estimation_id", referencedColumnName = "id")
	@NotNull
	@ApiModelProperty(value = "見積", required = true, position = 2)
	@JsonIgnore
	private Estimation estimation;

	/**
	 * 操作内容
	 */
	@Column(nullable = false)
	@NotEmpty
	@Size(max = 1000)
	@ApiModelProperty(value = "操作内容", required = true, position = 3, allowableValues = "range[0,1000]")
	@Enumerated(EnumType.STRING)
	private Operation operation;

	/**
	 * 操作者MoM社員ID
	 */
	@NotEmpty
	@Column(nullable = false)
	@ApiModelProperty(value = "操作者MoM社員ID", required = true, position = 4)
	private String operatorEmpId;

	/**
	 * 操作者氏名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "操作者氏名", required = false, position = 5, allowableValues = "range[0,255]")
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
	@ApiModelProperty(value = "実施日時", required = true, position = 7, readOnly = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date operatedAt;

	@PrePersist
	public void prePersist() {
		this.operatedAt = new Date();
	}
}
