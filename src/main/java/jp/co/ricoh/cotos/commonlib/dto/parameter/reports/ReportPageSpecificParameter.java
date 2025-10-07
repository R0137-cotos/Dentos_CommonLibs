package jp.co.ricoh.cotos.commonlib.dto.parameter.reports;

import jakarta.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.DetailStatus;
import lombok.Data;

/**
 * 帳票ページ特定パラメーター
 */
@Data
public class ReportPageSpecificParameter {

	/**
	 * 状態
	 */
	@ApiModelProperty(value = "状態", required = false, allowableValues = "NOUPDATE(\"1\"), ADD(\"2\"), DELETE(\"3\"), UPDATE(\"4\")", example = "1", position = 1)
	private DetailStatus status;

	/**
	 * リコー品種コード
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "リコー品種コード", required = false, allowableValues = "range[0,255]", position = 2)
	private String ricohItemCode;
}
