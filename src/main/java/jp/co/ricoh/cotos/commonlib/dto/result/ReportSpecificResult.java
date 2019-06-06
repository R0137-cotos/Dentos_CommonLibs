package jp.co.ricoh.cotos.commonlib.dto.result;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 帳票特定結果パラメーター
 */
@Data
public class ReportSpecificResult {
	/**
	 * テンプレートID
	 */
	@ApiModelProperty(value = "テンプレートID", required = true, allowableValues = "range[0,9999999999999999999]", position = 1)
	private long id;

	/**
	 * テンプレート名(帳票作成時不要)
	 */
	@ApiModelProperty(value = "テンプレート名(帳票作成時不要)", required = false, allowableValues = "range[255]", position = 2)
	private String templateName;

	/**
	 * 拡張子(帳票作成時不要)
	 */
	@ApiModelProperty(value = "拡張子(帳票作成時不要)", required = false, allowableValues = "range[255]", position = 3)
	private String extension;

	/**
	 * ページIDリスト
	 */
	@NotEmpty
	@ApiModelProperty(value = "ページIDリスト", required = true, allowableValues = "range[255]", position = 4)
	private List<Long> pageIdList;
}
