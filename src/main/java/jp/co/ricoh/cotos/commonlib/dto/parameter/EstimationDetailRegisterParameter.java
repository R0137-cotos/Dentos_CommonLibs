package jp.co.ricoh.cotos.commonlib.dto.parameter;

import java.math.BigDecimal;
import java.util.Arrays;

import javax.persistence.Lob;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.Data;

@Data
public class EstimationDetailRegisterParameter {

	public enum Status {

		ADD, DELETE, ー;

		@JsonValue
		public String toValue() {
			return this.name();
		}

		@JsonCreator
		public static Enum<Status> fromValue(String name) {
			return Arrays.stream(values()).filter(v -> v.name() == name).findFirst().orElseThrow(() -> new IllegalArgumentException(String.valueOf(name)));
		}
	}

	/**
	 * ステータス
	 */
	private Status status;

	/**
	 * 品種コード
	 */
	private String ricohItemCode;

	/**
	 * 数量
	 */
	private int quantity;

	/**
	 * 見積金額
	 */
	private BigDecimal amountSummary;

	/**
	 * 拡張項目
	 */
	@Lob
	private String extendsParameter;

}
