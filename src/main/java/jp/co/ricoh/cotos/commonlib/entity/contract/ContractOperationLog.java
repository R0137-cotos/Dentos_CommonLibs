package jp.co.ricoh.cotos.commonlib.entity.contract;

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
 * 契約操作履歴を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@EntityListeners(ContractOperationLogListener.class)
@Data
@Table(name = "contract_operation_log")
public class ContractOperationLog extends EntityBase {

	public enum OperationLogType {
		売上開始指示, 売上計上開始, 売上計上停止, キャンセル手続き, キャンセル中止, 解約手続き, 解約確定, 解約手続き中止, 変更キャンセル, 変更確定, 新規作成, プラン変更, 確定, 情報変更, 新規作成_情報変更, 更新, 業務依頼, 業務処理完了,
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contract_operation_log_seq")
	@SequenceGenerator(name = "contract_operation_log_seq", sequenceName = "contract_operation_log_seq", allocationSize = 1)
	@ApiModelProperty(value = "操作履歴ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 契約
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "contract_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "契約", required = true, position = 2)
	private Contract contract;

	/**
	 * 操作内容
	 */
	@Column(nullable = false)
	@NotNull
	@ApiModelProperty(value = "操作内容", required = true, position = 3, allowableValues = "range[0,1000]")
	@Enumerated(EnumType.STRING)
	private OperationLogType operation;

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
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	@ApiModelProperty(value = "実施日時", required = true, position = 7, readOnly = true)
	private Date operatedAt;

	@PrePersist
	public void prePersist() {
		super.prePersist();
		this.operatedAt = super.getCreatedAt();
	}
}