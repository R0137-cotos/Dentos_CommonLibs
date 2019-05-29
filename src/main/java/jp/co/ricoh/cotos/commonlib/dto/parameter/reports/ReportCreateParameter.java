package jp.co.ricoh.cotos.commonlib.dto.parameter.reports;

import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 帳票作成パラメーター
 */
@Data
public class ReportCreateParameter {

	/**
	 * 出力ファイルパス
	 */
	@Size(max = 255)
	@ApiModelProperty(value = "ファイル名", required = true, position = 3, allowableValues = "range[0,255]")
	private String outputFilePath;

	/**
	 * 帳票テンプレート管理マスタID
	 */
	@ApiModelProperty(value = "テンプレートID", required = true, allowableValues = "range[0,9999999999999999999999999999]")
	private long reportTemplateMasterId;

	/**
	 * 帳票データ部マッピング配列（ページ毎）
	 */
	@NotNull
	@ApiModelProperty(value = "帳票データ部マッピング配列", required = true)
	private List<Map<String, List<String>>> dataMapList;

}
