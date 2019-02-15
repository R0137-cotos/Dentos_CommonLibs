package jp.co.ricoh.cotos.commonlib.dto.parameter;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 帳票ページDTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
