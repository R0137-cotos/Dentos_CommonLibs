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

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 契約チェックリスト構成マスタを表すEntity
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "contract_checklist_comp_master")
public class ContractChecklistCompMaster extends EntityBaseMaster {

	public enum TargetContractType {

		共通("1"), 新規("2"), プラン変更("3"), 情報変更("4");

		private final String text;

		private TargetContractType(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static TargetContractType fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	public enum TargetLifecycleStatus {

		作成中("1"), キャンセル手続き中("2"), 解約手続き中("3");

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
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contract_checklist_comp_master_seq")
	@SequenceGenerator(name = "contract_checklist_comp_master_seq", sequenceName = "contract_checklist_comp_master_seq", allocationSize = 1)
	@ApiModelProperty(value = "契約チェックリスト構成マスタID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 商品
	 */
	@ManyToOne(optional = false)
	@JoinColumn(name = "product_master_id", referencedColumnName = "id")
	@JsonIgnore
	@ApiModelProperty(value = "商品マスタ", required = true, position = 2)
	private ProductMaster productMaster;

	/**
	 * 対象契約種別
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "対象契約種別<br /> "//
			+ "共通/新規/プラン変更/情報変更", required = true, allowableValues = "共通(\"1\"), 新規(\"2\"), プラン変更(\"3\"), 情報変更(\"4\")", example = "1", position = 3)
	private TargetContractType targetContractType;

	/**
	 * 対象ライフサイクル状態
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "対象ライフサイクル状態<br /> "//
			+ "作成中/キャンセル手続き中/解約手続き中", required = true, allowableValues = "作成中(\"1\"), キャンセル手続き中(\"2\"), 解約手続き中(\"3\")", example ="1", position = 4)
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
