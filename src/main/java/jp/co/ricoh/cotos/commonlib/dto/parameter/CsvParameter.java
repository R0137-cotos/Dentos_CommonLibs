package jp.co.ricoh.cotos.commonlib.dto.parameter;

import java.nio.charset.Charset;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CsvParameter {

	/**
	 * ヘッダー行の有無
	 * readCsvFile:ヘッダー行がないCSVを読み込む
	 * writeCsvFile:ヘッダー行がないCSVファイルを書き込む
	 */
	@Builder.Default
	private boolean header = true;

	/**
	 * セパレーター文字
	 * readCsvFile:セパレーターが指定文字のCSVを読み込む
	 * writeCsvFile:セパレーターを指定文字でCSVファイルを書き込む
	 */
	@Builder.Default
	private char separator = ',';

	/**
	 * 文字コード
	 * readCsvFile:CSVファイルの文字コードを指定して読み込む
	 * writeCsvFile:CSVファイルの文字コードを指定して書き込む
	 */
	@Builder.Default
	private Charset charset = Charset.forName("UTF-8");

	/**
	 * 改行コード
	 * readCsvFile:未使用
	 * writeCsvFile:指定した改行コードでCSVファイルを書き込む
	 */
	@Builder.Default
	private String lineSeparator = "\n";

	/**
	 * 文字列をダブルクォートで囲むか否か
	 * readCsvFile:未使用
	 * writeCsvFile:文字列項目をダブルクォートで囲んでCSVファイルを書き込む
	 */
	@Builder.Default
	private boolean quote = true;

	/**
	 * NULL項目の文字列
	 * readCsvFile:指定した文字列をNULLとしてエンティティに設定
	 * writeCsvFile:NULL項目を指定した文字列でCSVファイルに書き込む
	 *              ダブルクォートで囲むか否かのパラメーター有効化対象外
	 */
	@Builder.Default
	private String nullValueString = "null";

	/**
	 * 追記モード
	 * readCsvFile:未使用
	 * writeCsvFile:追記モードでCSVファイルを書き込む
	 */
	@Builder.Default
	private boolean appendMode = false;
}
