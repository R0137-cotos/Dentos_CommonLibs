package jp.co.ricoh.cotos.commonlib.dto.parameter;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 帳票DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
