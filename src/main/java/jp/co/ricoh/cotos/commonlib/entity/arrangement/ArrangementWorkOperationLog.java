package jp.co.ricoh.cotos.commonlib.entity.arrangement;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
		業務受付, 業務受付取消, 作業完了報告, 新規作成, 担当作業者設定, 更新, 作業完了, 業務完了取消, 保留更新, エラー発生, エラー解消, 業務指定作成, 帳票出力
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "arrangement_work_operation_log_seq")
	@SequenceGenerator(name = "arrangement_work_operation_log_seq", sequenceName = "arrangement_work_operation_log_seq", allocationSize = 1)
	@ApiModelProperty(value = "手配業務操作履歴ID (作成時不要)", required = true, position = 1, allowableValues = "range[0,9223372036854775807]", readOnly = true)
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
	@NotNull
	@Column(nullable = false)
	@ApiModelProperty(value = "操作者MoM社員ID<br/>※POST時「RJ社員情報マスタ」存在チェック実施", required = true, position = 4)
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
	@ApiModelProperty(value = "実施日時(作成時不要)", required = true, position = 7, readOnly = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date operatedAt;

	@PrePersist
	public void prePersist() {
		super.prePersist();
		this.operatedAt = super.getCreatedAt();
	}

}
