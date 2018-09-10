package jp.co.ricoh.cotos.commonlib.entity.arrangement;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import jp.co.ricoh.cotos.commonlib.entity.master.ArrangementMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 手配業務情報を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "arrangement_work")
public class ArrangementWork extends EntityBase {

	public enum WorkflowStatus {

		受付待ち, 作業中, 作業完了報告, 承認中, 作業完了;

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
	@ApiModelProperty(value = "手配業務ID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 手配
	 */
	@ManyToOne(optional = false)
	@ApiModelProperty(value = "手配", required = true, position = 2)
	private Arrangement arrangement;

	/**
	 * 手配業務タイプマスタ
	 */
	@ManyToOne(optional = true)
	@ApiModelProperty(value = "手配業務タイプマスタ", required = true, position = 3)
	private ArrangementMaster arrangeWorkTypeMaster;

	/**
	 * ワークフロー状態
	 */
	@ApiModelProperty(value = "手配業務ステータス", required = true, position = 4)
	private WorkflowStatus workflowStatus;

	/**
	 * メモ
	 */
	@ApiModelProperty(value = "メモ", required = false, position = 5, allowableValues = "range[0,4000]")
	private String memo;

	/**
	 * 手配業務承認ルート
	 */
	@OneToOne(mappedBy = "arrangementWork")
	@ApiModelProperty(value = "手配業務承認ルート", required = false, position = 6)
	private ArrangementWorkApprovalRoute arrangementWorkApprovalRoute;

	/**
	 * 担当作業者社員
	 */
	@OneToOne(mappedBy = "arrangementWork")
	@ApiModelProperty(value = "担当作業者社員", required = false, position = 7)
	private ArrangePicWorkerEmp arrangementPicWorkerEmp;

	/**
	 * 手配業務操作履歴
	 */
	@OneToMany(mappedBy = "arrangementWork")
	@ApiModelProperty(value = "手配業務操作履歴", required = false, position = 8)
	private List<ArrangeWorkOperationLog> arrangeWorkOperationLogList;

	/**
	 * 手配業務添付ファイル
	 */
	@OneToMany(mappedBy = "arrangementWork")
	@ApiModelProperty(value = "手配業務添付ファイル", required = false, position = 9)
	private List<WorkAttachedFile> workAttachedFileList;

	/**
	 * 手配業務チェック結果
	 */
	@OneToMany(mappedBy = "arrangementWork")
	private List<ArrangeWorkCheckResult> arrangeWorkCheckResultList;

}