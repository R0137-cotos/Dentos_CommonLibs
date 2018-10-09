package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 見積チェックリスト構成マスタを表すEntity
 */
@Entity
@Data
@Table(name = "estimation_checklist_comp_master")
public class EstimationChecklistCompMaster {

	public enum TargetEstimationType {

		共通("1"), 新規("2"), プラン変更("3");

		private final String text;

		private TargetEstimationType(final String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return this.text;
		}

		public static TargetEstimationType fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	public enum TargetLifecycleStatus {

		作成中;

		@JsonValue
		public String toValue() {
			return this.name();
		}

		@JsonCreator
		public static Enum<TargetLifecycleStatus> fromValue(String name) {
			return Arrays.stream(values()).filter(v -> v.name() == name).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(name)));
		}
	}

	@Id
	@ApiModelProperty(value = "チェックリスト構成マスタID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 商品
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "product_id", referencedColumnName = "id")
	@ApiModelProperty(value = "商品マスタ", required = true, position = 2)
	private ProductMaster productMaster;

	/**
	 * 対象見積種別
	 */
	@ApiModelProperty(value = "対象見積種別<br /> "//
			+ "共通/新規/プラン変更<br /> ", required = true, position = 3)
	private TargetEstimationType targetEstimationType;

	/**
	 * 対象ライフサイクル状態
	 */
	@ApiModelProperty(value = "対象ライフサイクル状態<br /> "//
			+ "作成中<br /> ", required = true, position = 4)
	private TargetLifecycleStatus targetLifecycleStatus;

	/**
	 * 汎用チェック事項マスタ
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "gp_check_matter_master_id", referencedColumnName = "id")
	@ApiModelProperty(value = "汎用チェック事項マスタ", required = true, position = 5)
	private GpCheckMatterMaster gpCheckMatterMaster;

	/**
	 * 表示順
	 */
	@ApiModelProperty(value = "表示順", required = true, position = 6, allowableValues = "range[0,999]")
	private int displayOrder;

}
