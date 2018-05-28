package jp.co.ricoh.cotos.commonlib.entity.contract;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 契約操作履歴を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "operation_log_con")
public class OperationLogCon extends EntityBase {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "operation_log_seq")
	@SequenceGenerator(name = "operation_log_seq", sequenceName = "operation_log_seq", allocationSize = 1)
	@ApiModelProperty(value = "操作履歴ID", required = true, position = 1)
	private long id;

	@ManyToOne(optional = false)
	@JsonIgnore
	@JoinColumn(name = "contract_id")
	private Contract contract;

	/**
	 * 操作内容
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = "操作内容", required = true, position = 2)
	private Operation operation;

	/**
	 * 操作者MoM社員ID
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "操作者MoM社員ID", required = true, position = 3, allowableValues = "range[0,255]")
	private String operationEmptxId;

	/**
	 * 操作者社員名
	 */
	@ApiModelProperty(value = "操作者社員名", required = false, position = 4, allowableValues = "range[0,255]")
	private String operationEmptxName;

	/**
	 * 操作者部署名
	 */
	@ApiModelProperty(value = "操作者部署名", required = false, position = 5, allowableValues = "range[0,255]")
	private String operationEmptxDepartmentName;

	/**
	 * 実施日時
	 */
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "実施日時", required = false, position = 6)
	private Date operationAt;

	@PrePersist
	public void prePersist() {
		this.operationAt = new Date();
	}

	public enum Operation {

		編集, ステータス, 帳票出力;

		@JsonValue
		public String toValue() {
			return this.name();
		}

		@JsonCreator
		public static Enum<Operation> fromValue(String name) {
			return Arrays.stream(values()).filter(v -> v.name() == name).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(name)));
		}
	}
}