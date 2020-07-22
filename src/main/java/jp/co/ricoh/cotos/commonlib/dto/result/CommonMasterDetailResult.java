package jp.co.ricoh.cotos.commonlib.dto.result;

import lombok.Data;

/**
 * 汎用マスタ明細結果
 */
@Data
public class CommonMasterDetailResult {

	/**
	 * コード値
	 */
	private String codeValue;

	/**
	 * コード表示値
	 */
	private String displayValue;

	/**
	 * 設定値1
	 */
	private String dataArea1;

	/**
	 * 設定値2
	 */
	private String dataArea2;

	/**
	 * 設定値3
	 */
	private String dataArea3;

	/**
	 * 設定値4
	 */
	private String dataArea4;

	/**
	 * 設定値5
	 */
	private String dataArea5;

	/**
	 * 表示順
	 */
	private Integer displayOrder;
}
