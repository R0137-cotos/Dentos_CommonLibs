package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
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

		作成中("1");

		private final String text;

		private TargetLifecycleStatus(final String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return this.text;
		}

		public static TargetLifecycleStatus fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	@Id
	@Column(nullable = false)
 	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "estimation_checklist_comp_master_seq")
 	@SequenceGenerator(name = "estimation_checklist_comp_master_seq", sequenceName = "estimation_checklist_comp_master_seq", allocationSize = 1)
	@ApiModelProperty(value = "見積チェックリスト構成マスタID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 商品マスタ
	 */
	@Column(nullable = false)
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
			+ "共通/新規/プラン変更<br /> ", required = true, position = 3)
	private TargetEstimationType targetEstimationType;

	/**
	 * 対象ライフサイクル状態
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "対象ライフサイクル状態<br /> "//
			+ "作成中<br /> ", required = true, position = 4)
	private TargetLifecycleStatus targetLifecycleStatus;

	/**
	 * 汎用チェック事項マスタ
	 */
	@Column(nullable = false)
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
