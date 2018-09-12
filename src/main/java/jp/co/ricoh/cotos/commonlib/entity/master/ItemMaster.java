package jp.co.ricoh.cotos.commonlib.entity.master;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 品種を表すEntity
 */
@Entity
@Data
@Table(name = "item_master")
public class ItemMaster {

	public enum ProductType {

		基本, オプション;

		@JsonValue
		public String toValue() {
			return this.name();
		}

		@JsonCreator
		public static Enum<ProductType> fromValue(String name) {
			return Arrays.stream(values()).filter(v -> v.name() == name).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(name)));
		}
	}

	public enum CostType {

		初期費, 月額, 年額;

		@JsonValue
		public String toValue() {
			return this.name();
		}

		@JsonCreator
		public static Enum<CostType> fromValue(String name) {
			return Arrays.stream(values()).filter(v -> v.name() == name).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(name)));
		}
	}

	@Id
	@ApiModelProperty(value = "品種マスタID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 品種名
	 */
	@ApiModelProperty(value = "品種名", required = true, position = 2, allowableValues = "range[0,255]")
	private String name;

	/**
	 * リコー品種コード
	 */
	@ApiModelProperty(value = "リコー品種コード", required = true, position = 3, allowableValues = "range[0,255]")
	private String ricohItemCode;

	@ManyToOne(optional = false)
	@JoinColumn(name = "product_id", referencedColumnName = "id")
	@ApiModelProperty(value = "商品マスタ", required = true, position = 4)
	private Product product;

	/**
	 * 手配業務構成マスタ
	 */
	@OneToMany(mappedBy = "itemMaster")
	@ApiModelProperty(value = "手配業務構成マスタ", required = false, position = 5)
	private List<ArrangeWorkCompMaster> arrangeWorkCompMasterList;

	/**
	 * 商品区分
	 */
	@ApiModelProperty(value = "商品区分", required = true, position = 6)
	private ProductType productType;

	/**
	 * 費用種別
	 */
	@ApiModelProperty(value = "費用種別", required = true, position = 7)
	private CostType costType;

	/**
	 * 仕切価格
	 */
	@ApiModelProperty(value = "仕切価格", required = true, position = 8, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal partitionPrice;

	/**
	 * 積上げ可能期間（開始日）
	 */
	@ApiModelProperty(value = "積上げ可能期間（開始日）", required = true, position = 9, allowableValues = "range[0,19]")
	@Pattern(regexp = "YYYY-MM-DD HH:mm:ss")
	private String effectiveFrom;

	/**
	 * 積上げ可能期間（終了日）
	 */
	@ApiModelProperty(value = "積上げ可能期間（終了日）", required = true, position = 10, allowableValues = "range[0,19]")
	@Pattern(regexp = "YYYY-MM-DD HH:mm:ss")
	private String effectiveTo;

	/**
	 * 計上分解構成マスタ
	 */
	@OneToMany(mappedBy = "itemMaster")
	@ApiModelProperty(value = "計上分解構成マスタ", required = false, position = 11)
	private List<RecordDecomposeCompMaster> recordDecomposeCompMasterList;

}
