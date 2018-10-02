package jp.co.ricoh.cotos.commonlib.dto.parameter;

import java.math.BigDecimal;

import javax.persistence.Lob;

import lombok.Data;

@Data
public class EstimationDetailRegisterParameter {

	/**
	 * ステータス
	 */
	private String status;

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
