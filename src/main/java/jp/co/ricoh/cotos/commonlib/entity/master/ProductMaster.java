package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.Arrays;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 商品マスタ
 */
@Entity
@Data
@ToString(exclude = { "itemMasterList" })
@EqualsAndHashCode(callSuper = true)
@Table(name = "product_master")
public class ProductMaster extends EntityBaseMaster {

	/**
	 * 契約種類区分
	 */
	public enum ContractClassDiv {

		年間保守契約_RP("100"), 年間保守契約_PC_NW機器_その他("101"), 受託保守("102");

		private final String text;

		private ContractClassDiv(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static ContractClassDiv fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_master_seq")
	@SequenceGenerator(name = "product_master_seq", sequenceName = "product_master_seq", allocationSize = 1)
	@ApiModelProperty(value = "商品マスタID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 商品名
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "商品名", required = true, position = 2, allowableValues = "range[0,255]")
	private String productName;

	/**
	 * 代表品種マスタID
	 */
	@ApiModelProperty(value = "代表品種マスタID", required = false, position = 3, allowableValues = "range[0,9999999999999999999]")
	private Long repItemMasterId;

	/**
	 * 商品構成マスタ
	 */
	@OneToMany(mappedBy = "productMaster")
	@JsonIgnore
	@ApiModelProperty(value = "商品構成マスタ", required = false, position = 4)
	private List<ProductCompMaster> productCompMasterList;

	/**
	 * 品種マスタ
	 */
	@OneToMany(mappedBy = "productMaster")
	@ApiModelProperty(value = "品種マスタ", required = false, position = 5)
	private List<ItemMaster> itemMasterList;

	/**
	 * 見積チェックリスト構成マスタ
	 */
	@OneToMany(mappedBy = "productMaster")
	@ApiModelProperty(value = "チェックリスト構成マスタ", required = false, position = 6)
	private List<EstimationChecklistCompMaster> estimationChecklistCompMasterList;

	/**
	 * 契約チェックリスト構成マスタ
	 */
	@OneToMany(mappedBy = "productMaster")
	@ApiModelProperty(value = "チェックリスト構成マスタ", required = false, position = 7)
	private List<ContractChecklistCompMaster> contractChecklistCompMasterList;

	/**
	 * アプリケーションID
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "アプリケーションID", required = false, position = 8, allowableValues = "range[0,255]")
	private String appId;

	/**
	 * 商品固有項目VIEW識別子
	 */
	@ApiModelProperty(value = "商品固有項目VIEW識別子", required = false, position = 9)
	private String viewNameSuffix;

	/**
	 * JSONスキーママスタ
	 */
	@OneToMany(mappedBy = "productMaster")
	@ApiModelProperty(value = "JSONスキーママスタ", required = false, position = 10)
	private List<JsonSchemaMaster> jsonSchemaMasterList;

	/**
	 * 拡張項目相関チェックマスタ
	 */
	@OneToMany(mappedBy = "productMaster", orphanRemoval = true, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@Fetch(FetchMode.SUBSELECT)
	@ApiModelProperty(value = "拡張項目相関チェックマスタ", required = false, position = 11)
	private List<ExtendsParameterCorrelationCheckMaster> extendsParameterCorrelationCheckMasterList;

	/**
	 * 契約種類区分
	 */
	@ApiModelProperty(value = "契約種類区分", required = false, position = 12)
	private ContractClassDiv contractClassDiv;

	/**
	 * SBU区分
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "SBU区分", required = false, position = 13, allowableValues = "range[0,255]")
	private String sbuDiv;

	/**
	 * 商品種類区分
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "商品種類区分", required = false, position = 14, allowableValues = "range[0,255]")
	private String productClassDiv;
}
