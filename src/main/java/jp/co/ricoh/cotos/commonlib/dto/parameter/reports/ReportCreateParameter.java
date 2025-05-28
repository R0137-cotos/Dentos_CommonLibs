package jp.co.ricoh.cotos.commonlib.dto.parameter.reports;

import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.dto.result.ReportSpecificResult;
import lombok.Data;

/**
 * 帳票作成パラメーター
 */
@Data
public class ReportCreateParameter {

	/**
	 * 出力ファイル名
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "出力ファイル名", required = true, allowableValues = "range[0,255]", position = 1)
	private String outputFileName;

	/**
	 * 帳票テンプレート特定結果
	 */
	@Valid
	@ApiModelProperty(value = "帳票テンプレート特定結果", required = true, position = 2)
	private ReportSpecificResult reportSpecificResult;

	/**
	 * 帳票データ部マッピングリスト（ページ毎）
	 */
	@NotNull
	@ApiModelProperty(value = "帳票データ部マッピングリスト", required = true, position = 3)
	private List<Map<String, List<Object>>> dataMapList;

}
