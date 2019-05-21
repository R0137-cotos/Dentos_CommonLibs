package jp.co.ricoh.cotos.commonlib.entity.contract;

import java.math.BigDecimal;
import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 品種明細(契約用)を表すEntity
 */
@Entity
@EqualsAndHashCode(callSuper = true)
@Data
@Table(name = "item_detail_contract")
public class ItemDetailContract extends EntityBase {
	
	public enum InitialRunningDiv {
		
		イニシャル("1"), ランニング("2");
		
		private final String text;

		private InitialRunningDiv(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static InitialRunningDiv fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}
	
	/**
	 * 品種明細ID
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_detail_contract_seq")
	@SequenceGenerator(name = "item_detail_contract_seq", sequenceName = "item_detail_contract_seq", allocationSize = 1)
	@ApiModelProperty(value = "品種明細ID(作成時不要)", required = true, position = 1, allowableValues = "range[0,9223372036854775807]", readOnly = true)
	private long id;
	
	/**
	 * 原価
	 */
	@DecimalMax("9999999999999999999.99")
	@ApiModelProperty(value = "原価", required = false, position = 2, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal price;
	
	/**
	 * 振替先課所コード
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "振替先課所コード", required = false, position = 3, allowableValues = "range[0,255]")
	private String transToServiceOrgCode;
	
	/**
	 * 振替先課所名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "振替先課所名", required = false, position = 4, allowableValues = "range[0,255]")
	private String transToServiceOrgName;
	
	/**
	 * イニシャル/ランニング区分
	 */
	@ApiModelProperty(value = "イニシャル/ランニング区分", required = false, position = 4, allowableValues = "イニシャル(\"1\"), ランニング(\"2\")")
	private InitialRunningDiv initialRunningDiv;
	
	/**
	 * 品種ID
	 */
	@ManyToOne(optional = false)
	@JsonIgnore
	@JoinColumn(name = "item_contract_id", referencedColumnName = "id")
	@ApiModelProperty(value = "品種(契約用)", required = true, position = 5)
	private ItemContract itemContract;

}
