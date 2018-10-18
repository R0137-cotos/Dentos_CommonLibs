package jp.co.ricoh.cotos.commonlib.dto.parameter;

import java.nio.charset.Charset;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CsvParameter {

	/**
	 * ヘッダー行の有無 ヘッダー行がないCSV情報を生成する
	 */
	@Builder.Default
	private boolean header = true;

	/**
	 * セパレーター文字 セパレーターを指定文字でCSV情報を生成する
	 */
	@Builder.Default
	private char separator = ',';

	/**
	 * 文字コード CSV情報の文字コードを指定して生成する
	 */
	@Builder.Default
	private Charset charset = Charset.forName("UTF-8");

	/**
	 * 改行コード 指定した改行コードでCSV情報を生成する
	 */
	@Builder.Default
	private String lineSeparator = "\n";

	/**
	 * 文字列をダブルクォートで囲むか否か 文字列項目をダブルクォートで囲んでCSV情報を生成する
	 */
	@Builder.Default
	private boolean quote = true;

	/**
	 * NULL項目の文字列 NULL項目を指定した文字列でCSV情報を生成する ダブルクォートで囲むか否かのパラメーター有効化対象外
	 */
	@Builder.Default
	private String nullValueString = "null";
}
