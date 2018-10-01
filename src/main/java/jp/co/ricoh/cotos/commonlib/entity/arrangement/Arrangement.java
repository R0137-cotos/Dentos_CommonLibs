package jp.co.ricoh.cotos.commonlib.entity.arrangement;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

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

		手配中, 手配完了;

		@JsonValue
		public String toValue() {
			return this.name();
		}

		@JsonCreator
		public static Enum<WorkflowStatus> fromValue(String name) {
			return Arrays.stream(values()).filter(v -> v.name() == name).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(name)));
		}
	}

	@Id
	@ApiModelProperty(value = "手配ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]", readOnly = true)
	private long id;

	/**
	 * 契約ID
	 */
	@ApiModelProperty(value = "契約ID", required = true, position = 2, allowableValues = "range[0,9999999999999999999]")
	private long contractId;

	/**
	 * 解約フラグ
	 */
	@ApiModelProperty(value = "解約フラグ", required = true, position = 3, allowableValues = "range[0,9]")
	private int disengagementFlg;

	/**
	 * ワークフロー状態
	 */
	@ApiModelProperty(value = "ワークフロー状態", required = true, position = 4)
	private WorkflowStatus workflowStatus;

	/**
	 * 手配業務
	 */
	@OneToMany(mappedBy = "arrangement")
	@ApiModelProperty(value = "手配業務", required = false, position = 5)
	private List<ArrangementWork> arrangementWorkList;

}