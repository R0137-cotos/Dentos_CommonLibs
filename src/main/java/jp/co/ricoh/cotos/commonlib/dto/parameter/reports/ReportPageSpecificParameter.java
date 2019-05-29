package jp.co.ricoh.cotos.commonlib.dto.parameter.reports;

import javax.validation.constraints.Size;

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
	@ApiModelProperty(value = "状態", required = false, allowableValues = "NOUPDATE(\"1\"), ADD(\"2\"), DELETE(\"3\"), UPDATE(\"4\")", example = "1")
	private DetailStatus status;

	/**
	 * テンプレート区分
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "テンプレート区分", required = false, allowableValues = "range[0,255]")
	private String templateType;
}
