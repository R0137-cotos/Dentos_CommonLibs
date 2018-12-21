package jp.co.ricoh.cotos.commonlib.dto.parameter;

import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * 帳票ページDTO
 */
@Data
public class ReportSourcePageDto {

	/**
	 * SVFフォームファイルパス
	 */
	private String formFilePath;

	/**
	 * 帳票データ部マッピング
	 */
	private Map<String, List<String>> dataMapList;
}
