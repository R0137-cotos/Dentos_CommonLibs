package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.Arrays;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation.EstimationType;
import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation.LifecycleStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 見積チェックリスト構成マスタを表すEntity
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "estimation_checklist_comp_master")
public class EstimationChecklistCompMaster extends EntityBaseMaster {

	public enum TargetEstimationType {

		共通("1"), 新規("2"), 契約変更("3");

		private final String text;

		private TargetEstimationType(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static TargetEstimationType fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}

		public static TargetEstimationType fromEstimationType(EstimationType estimationType) {

			// TargetEstimationType と EstimationType 間で区分値構造が異なることによる変換処理
			switch (estimationType) {
			case 新規:
				return TargetEstimationType.新規;
			case 契約変更:
				return TargetEstimationType.契約変更;
			default:
				throw new IllegalArgumentException(String.valueOf(estimationType.toString()));
			}
		};
	}

	public enum TargetLifecycleStatus {

		作成中("1");

		private final String text;

		private TargetLifecycleStatus(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static TargetLifecycleStatus fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}

		public static TargetLifecycleStatus fromLifeCycleStatus(LifecycleStatus lifeCycleStatus) {
			// TargetLifecycleStatus と LifecycleStatus 間で区分値構造が異なることによる変換処理
			switch (lifeCycleStatus) {
			case 作成中:
				return TargetLifecycleStatus.作成中;
			default:
				throw new IllegalArgumentException(String.valueOf(lifeCycleStatus.toString()));
			}
		};
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estimation_checklist_comp_master_seq")
	@SequenceGenerator(name = "estimation_checklist_comp_master_seq", sequenceName = "estimation_checklist_comp_master_seq", allocationSize = 1)
	@ApiModelProperty(value = "見積チェックリスト構成マスタID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 商品マスタ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "product_master_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "商品マスタ", required = true, position = 2)
	private ProductMaster productMaster;

	/**
	 * 対象見積種別
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "対象見積種別<br /> "//
			+ "共通/新規/契約変更<br /> ", required = true, allowableValues = "共通(\"1\"), 新規(\"2\"), 契約変更(\"3\")", example = "1", position = 3)
	private TargetEstimationType targetEstimationType;

	/**
	 * 対象ライフサイクル状態
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "対象ライフサイクル状態<br /> "//
			+ "作成中<br /> ", required = true, allowableValues = "作成中(\"1\")", example = "1", position = 4)
	private TargetLifecycleStatus targetLifecycleStatus;

	/**
	 * 汎用チェック事項マスタ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "gp_check_matter_master_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "汎用チェック事項マスタ", required = true, position = 5)
	private GpCheckMatterMaster gpCheckMatterMaster;

	/**
	 * 表示順
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "表示順", required = true, position = 6, allowableValues = "range[0,999]")
	private int displayOrder;

}
