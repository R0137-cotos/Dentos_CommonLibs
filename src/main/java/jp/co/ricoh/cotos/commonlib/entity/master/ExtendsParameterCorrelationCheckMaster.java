package jp.co.ricoh.cotos.commonlib.entity.master;

import java.io.Serializable;
import java.util.Arrays;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 拡張項目相関チェックマスタ
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "extends_parameter_correlation_check_master")
public class ExtendsParameterCorrelationCheckMaster extends EntityBaseMaster {

	public enum Domain {
		estimation("1"), contract("2");

		private final String text;

		private Domain(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static Domain fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	@Embeddable
	@Data
	public static class Id implements Serializable {

		/**
		 * シリアルバージョンID
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 商品マスタID
		 */
		@Column(name = "product_master_id", nullable = false)
		@ApiModelProperty(value = "商品マスタID", required = true, position = 1)
		private long productMasterId;

		/**
		 * ドメイン
		 */
		@Column(nullable = false)
		@ApiModelProperty(value = "システムドメイン", required = true, position = 2)
		private Domain domain;
	}

	@EmbeddedId
	private Id id;

	@ManyToOne
	@JoinColumn(name = "product_master_id", referencedColumnName = "id")
	@MapsId("productMasterId")
	@JsonIgnore
	private ProductMaster productMaster;

	/**
	 * 相関チェック条件式
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "相関チェック条件式", required = true, position = 3)
	@Lob
	private String correlationCheckConditionFormula;
}
