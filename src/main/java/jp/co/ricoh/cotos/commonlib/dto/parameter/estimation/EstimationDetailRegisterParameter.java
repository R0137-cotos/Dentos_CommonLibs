package jp.co.ricoh.cotos.commonlib.dto.parameter.estimation;

import java.math.BigDecimal;

import javax.persistence.Lob;

import io.swagger.annotations.ApiParam;
import lombok.Data;

@Data
public class EstimationDetailRegisterParameter {

	/**
	 * ステータス
	 */
	@ApiParam(value = "ステータス", allowableValues = "range[0,255]", required = true)
	private String status;

	/**
	 * 品種コード
	 */
	@ApiParam(value = "品種コード", allowableValues = "range[0,255]", required = true)
	private String ricohItemCode;

	/**
	 * 数量
	 */
	@ApiParam(value = "数量", allowableValues = "range[0,99999]", required = true)
	private int quantity;

	/**
	 * 見積金額
	 */
	@ApiParam(value = "見積金額", allowableValues = "range[0.00,9999999999999999999.99]", required = true)
	private BigDecimal amountSummary;

	/**
	 * 拡張項目
	 */
	@ApiParam(value = "拡張項目", required = false)
	@Lob
	private String extendsParameter;

}
