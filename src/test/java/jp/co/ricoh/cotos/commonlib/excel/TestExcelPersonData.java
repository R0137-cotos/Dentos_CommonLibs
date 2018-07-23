package jp.co.ricoh.cotos.commonlib.excel;

import java.util.List;

import lombok.Data;

@Data
public class TestExcelPersonData {
	/**
	 * タイトル
	 */
	private String title;

	/**
	 * シート名
	 */
	private String sheetName;

	/**
	 * 個人情報
	 */
	private List<TestExcelPersonDataRow> dataList;
}
