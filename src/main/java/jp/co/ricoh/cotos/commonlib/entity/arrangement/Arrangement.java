package jp.co.ricoh.cotos.commonlib.entity.arrangement;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 契約情報と紐づく手配情報を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "arrangement")
public class Arrangement extends EntityBase {
	public enum WorkflowStatus {

		手配中("1"), 手配完了("2");

		private final String text;

		private WorkflowStatus(final String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return this.text;
		}

		public static WorkflowStatus fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "arrangement_seq")
	@SequenceGenerator(name = "arrangement_seq", sequenceName = "arrangement_seq", allocationSize = 1)
	@ApiModelProperty(value = "手配ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 契約ID
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "契約ID", required = true, position = 2, allowableValues = "range[0,9999999999999999999]")
	private long contractId;

	/**
	 * 解約フラグ
	 */
	@Column(nullable = false)
	@Max(9L)
	@ApiModelProperty(value = "解約フラグ", required = true, position = 3, allowableValues = "range[0,9]")
	private int disengagementFlg;

	/**
	 * ワークフロー状態
	 */
	@Column(nullable = false)
	@NotNull
	@ApiModelProperty(value = "ワークフロー状態", required = true, position = 4)
	private WorkflowStatus workflowStatus;

	/**
	 * 手配業務
	 */
	@OneToMany(mappedBy = "arrangement")
	@ApiModelProperty(value = "手配業務", required = true, position = 5)
	private List<ArrangementWork> arrangementWorkList;

}