package jp.co.ricoh.cotos.commonlib.entity.master;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 品種を表すEntity
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "item_master")
public class ItemMaster extends EntityBaseMaster {

	public enum ItemType {

		なし("0"), 基本("1"), オプション("2");

		private final String text;

		private ItemType(final String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return this.text;
		}

		public static ItemType fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	public enum CostType {

		初期費("1"), 月額("2"), 年額("3");

		private final String text;

		private CostType(final String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return this.text;
		}

		public static CostType fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_master_seq")
	@SequenceGenerator(name = "item_master_seq", sequenceName = "item_master_seq", allocationSize = 1)
	@ApiModelProperty(value = "品種マスタID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
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
	 * 品種名
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "品種名", required = true, position = 3, allowableValues = "range[0,255]")
	private String itemName;

	/**
	 * リコー品種コード
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "リコー品種コード", required = true, position = 4, allowableValues = "range[0,255]")
	private String ricohItemCode;

	/**
	 * 品種区分
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = "品種区分", required = true, position = 5)
	private ItemType itemType;

	/**
	 * 費用種別
	 */
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	@ApiModelProperty(value = "費用種別", required = true, position = 6)
	private CostType costType;

	/**
	 * 仕切価格
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "仕切価格", required = true, position = 7, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal partitionPrice;

	/**
	 * 積上げ可能期間（開始日）
	 */
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "積上げ可能期間（開始日）", required = true, position = 8, allowableValues = "range[0,19]")
	private Date effectiveFrom;

	/**
	 * 積上げ可能期間（終了日）
	 */
	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@ApiModelProperty(value = "積上げ可能期間（終了日）", required = true, position = 9, allowableValues = "range[0,19]")
	private Date effectiveTo;

	/**
	 * 計上分解構成マスタ
	 */
	@OneToMany(mappedBy = "itemMaster")
	@ApiModelProperty(value = "計上分解構成マスタ", required = false, position = 10)
	private List<RecordDecomposeCompMaster> recordDecomposeCompMasterList;

	/**
	 * 手配業務構成マスタ
	 */
	@OneToMany(mappedBy = "itemMaster")
	@ApiModelProperty(value = "手配業務構成マスタ", required = false, position = 11)
	private List<ArrangementWorkCompMaster> arrangementWorkCompMasterList;

}
