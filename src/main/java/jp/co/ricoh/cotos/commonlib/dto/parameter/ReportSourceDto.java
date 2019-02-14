package jp.co.ricoh.cotos.commonlib.dto.parameter;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 帳票DTO
 */
@Data
@AllArgsConstructor
public class ReportSourceDto {

	/**
	 * 出力PDFファイルパス
	 */
	private String fileName;

	/**
	 * 帳票ページDTO配列
	 */
	private List<ReportSourcePageDto> reportList;
}
