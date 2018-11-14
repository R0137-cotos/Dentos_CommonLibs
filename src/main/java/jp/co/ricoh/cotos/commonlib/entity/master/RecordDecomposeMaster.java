package jp.co.ricoh.cotos.commonlib.entity.master;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 計上分解マスタを表すEntity
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "record_decompose_master")
public class RecordDecomposeMaster extends EntityBaseMaster {

	public enum RecordType {

		原価振替("1"), 割戻("2");

		private final String text;

		private RecordType(final String text) {
			this.text = text;
		}

		@Override
		@JsonValue
		public String toString() {
			return this.text;
		}

		@JsonCreator
		public static RecordType fromString(String string) {
			return Arrays.stream(values()).filter(v -> v.text.equals(string)).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(string)));
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "record_decompose_master_seq")
	@SequenceGenerator(name = "record_decompose_master_seq", sequenceName = "record_decompose_master_seq", allocationSize = 1)
	@ApiModelProperty(value = "計上分解マスタID", required = true, position = 1, allowableValues = "range[0,9999999999999999999]")
	private long id;

	/**
	 * 計上種別
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "計上種別", required = true, allowableValues = "原価振替(\"1\"), 割戻(\"2\")", example = "1", position = 2)
	private RecordType recordType;

	/**
	 * 説明
	 */
	@ApiModelProperty(value = "説明", required = false, position = 3, allowableValues = "range[0,255]")
	private String description;

	/**
	 * 振替先コード
	 */
	@ApiModelProperty(value = "振替先コード", required = false, position = 4, allowableValues = "range[0,255]")
	private String transferDestCode;

	/**
	 * 振替先名
	 */
	@ApiModelProperty(value = "振替先名", required = false, position = 5, allowableValues = "range[0,255]")
	private String transferDestName;

	/**
	 * 数量
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "数量", required = true, position = 6, allowableValues = "range[0,99999]")
	private int quantity;

	/**
	 * 対象金額
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "対象金額", required = true, position = 7, allowableValues = "range[0.00,9999999999999999999.99]")
	private BigDecimal amount;

	/**
	 * 計上分解構成マスタ
	 */
	@OneToMany(mappedBy = "recordDecomposeMaster")
	@ApiModelProperty(value = "計上分解構成マスタ", required = false, position = 8)
	private List<RecordDecomposeCompMaster> recordDecomposeCompMasterList;

}
