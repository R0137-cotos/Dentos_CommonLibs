package jp.co.ricoh.cotos.commonlib.excel;

import java.util.List;

import lombok.Data;

@Data
public class TestExcelEntity{

	/**
	 * 標準テンプレート
	 */
	private TestExcelSimpleData simpleData;

	/**
	 * 繰り返しテンプレート
	 */
	private TestExcelPersonData personData;

	/**
	 * 複数シートテンプレート
	 */
	private List<TestExcelPersonData> multiSheetPersonData;

	/**
	 * 複数シートテンプレートシート名配列
	 */
	private List<String> multiSheetPersonDataSheetNames;
}
