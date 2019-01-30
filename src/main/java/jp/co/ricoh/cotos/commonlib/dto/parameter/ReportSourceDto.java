package jp.co.ricoh.cotos.commonlib.dto.parameter;

import java.util.List;

import lombok.Data;

/**
 * 帳票DTO
 */
@Data
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
