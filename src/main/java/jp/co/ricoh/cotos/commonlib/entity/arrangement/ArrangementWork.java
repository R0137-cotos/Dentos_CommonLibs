package jp.co.ricoh.cotos.commonlib.entity.arrangement;

import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
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

		受付待ち("1"), 作業中("2"), 作業完了報告("3"), 承認依頼中("4"), 作業完了("5"), エラー("6");

		private final String text;

		private WorkflowStatus(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static WorkflowStatus fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "arrangement_work_seq")
	@SequenceGenerator(name = "arrangement_work_seq", sequenceName = "arrangement_work_seq", allocationSize = 1)
	@ApiModelProperty(value = "手配業務ID (作成時不要)", required = true, position = 1, allowableValues = "range[0,9223372036854775807]", readOnly = true)
	private long id;

	/**
	 * 手配
	 */
	@Valid
	@NotNull
	@ManyToOne(optional = false)
	@JoinColumn(name = "arrangement_id", referencedColumnName = "id")
	@ApiModelProperty(value = "手配", required = true, position = 2)
	private Arrangement arrangement;

	/**
	 * 手配業務タイプマスタID
	 */
	@Column(nullable = false)
	@Min(0)
	@ApiModelProperty(value = "手配業務タイプマスタID", required = true, position = 3, allowableValues = "range[0,9223372036854775807]")
	private long arrangementWorkTypeMasterId;

	/**
	 * ワークフロー状態
	 */
	@Column(nullable = false)
	@NotNull
	@ApiModelProperty(value = "ワークフロー状態", required = true, allowableValues = "受付待ち(\"1\"), 作業中(\"2\"), 作業完了報告(\"3\"), 承認依頼中(\"4\"), 作業完了(\"5\"), エラー(\"6\")", example = "1", position = 4)
	private WorkflowStatus workflowStatus;

	/**
	 * メモ
	 */
	@Size(max = 4000)
	@ApiModelProperty(value = "メモ", required = false, position = 5, allowableValues = "range[0,4000]")
	private String memo;

	/**
	 * 保留フラグ
	 */
	@Column(nullable = false)
	@Max(9)
	@Min(0)
	@ApiModelProperty(value = "保留フラグ", required = true, position = 6, allowableValues = "range[0,9]")
	private int holdingFlg;

	/**
	 * 手配業務承認ルート
	 */
	@OneToOne(mappedBy = "arrangementWork")
	@ApiModelProperty(value = "手配業務承認ルート", required = false, position = 7)
	private ArrangementWorkApprovalRoute arrangementWorkApprovalRoute;

	/**
	 * 担当作業者社員
	 */
	@OneToOne(mappedBy = "arrangementWork")
	@ApiModelProperty(value = "担当作業者社員", required = false, position = 8)
	private ArrangementPicWorkerEmp arrangementPicWorkerEmp;

	/**
	 * 手配業務操作履歴
	 */
	@OneToMany(mappedBy = "arrangementWork")
	@OrderBy("operatedAt ASC")
	@ApiModelProperty(value = "手配業務操作履歴", required = false, position = 9)
	private List<ArrangementWorkOperationLog> arrangementWorkOperationLogList;

	/**
	 * 手配業務添付ファイル
	 */
	@OneToMany(mappedBy = "arrangementWork")
	@ApiModelProperty(value = "手配業務添付ファイル", required = false, position = 10)
	private List<ArrangementWorkAttachedFile> workAttachedFileList;

	/**
	 * 手配業務チェック結果
	 */
	@OneToMany(mappedBy = "arrangementWork")
	@OrderBy("displayOrder ASC")
	@ApiModelProperty(value = "手配業務チェック結果", required = false, position = 11)
	private List<ArrangementWorkCheckResult> arrangementWorkCheckResultList;

	/**
	 * アプリケーションID
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "アプリケーションID", required = false, position = 12, allowableValues = "range[0,255]")
	private String appId;

}