package jp.co.ricoh.cotos.commonlib.testCSVUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.ricoh.cotos.commonlib.csv.CSVUtil;
import jp.co.ricoh.cotos.commonlib.dto.parameter.CsvParameter;
import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCSVReader {

	@Autowired
	CSVUtil csvUtil;

	@Test
	public void 正常系_CSVファイル読み込みテスト_デフォルトパラメーター() throws ErrorCheckException, IOException {
		CsvParameter param = CsvParameter.builder().build();

		List<TestCSVData> list = csvUtil.readCsvFile("src/test/resources/csv/input_default.csv", TestCSVData.class, param);

		List<String> actual = list.stream().map(s -> s.toString()).collect(Collectors.toList());
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/output_default.txt"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVファイルが読み込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVファイル読み込みテスト_デフォルトパラメーターNULL() throws ErrorCheckException, IOException {
		List<TestCSVData> list = csvUtil.readCsvFile("src/test/resources/csv/input_default.csv", TestCSVData.class, null);

		List<String> actual = list.stream().map(s -> s.toString()).collect(Collectors.toList());
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/output_default.txt"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVファイルが読み込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVファイル読み込みテスト_ヘッダーなし() throws ErrorCheckException, IOException {
		CsvParameter param = CsvParameter.builder().header(false).build();

		List<TestCSVData> list = csvUtil.readCsvFile("src/test/resources/csv/input_withoutHeader.csv", TestCSVData.class, param);

		List<String> actual = list.stream().map(s -> s.toString()).collect(Collectors.toList());
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/output_default.txt"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVファイルが読み込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVファイル読み込みテスト_ヘッダー2バイト文字() throws ErrorCheckException, IOException {
		CsvParameter param = CsvParameter.builder().build();

		List<TestCSVDataHeaderJapanese> list = csvUtil.readCsvFile("src/test/resources/csv/input_columnnameJapanese.csv", TestCSVDataHeaderJapanese.class, param);

		List<String> actual = list.stream().map(s -> s.toString()).collect(Collectors.toList());
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/output_columnnameJapanese.txt"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVファイルが読み込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVファイル読み込みテスト_セパレータータブ() throws ErrorCheckException, IOException {
		CsvParameter param = CsvParameter.builder().separator('\t').build();

		List<TestCSVData> list = csvUtil.readCsvFile("src/test/resources/csv/input_seperatorTab.csv", TestCSVData.class, param);

		List<String> actual = list.stream().map(s -> s.toString()).collect(Collectors.toList());
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/output_default.txt"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVファイルが読み込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVファイル読み込みテスト_SJISのCSVファイル() throws ErrorCheckException, IOException {
		CsvParameter param = CsvParameter.builder().charset(Charset.forName("Shift-JIS")).build();

		List<TestCSVData> list = csvUtil.readCsvFile("src/test/resources/csv/input_shiftJIS.csv", TestCSVData.class, param);

		List<String> actual = list.stream().map(s -> s.toString()).collect(Collectors.toList());
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/output_default.txt"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVファイルが読み込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVファイル読み込みテスト_UTF8BOM付き() throws ErrorCheckException, IOException {
		// パラメーターデフォルトのままで読み込みできる
		CsvParameter param = CsvParameter.builder().build();

		List<TestCSVData> list = csvUtil.readCsvFile("src/test/resources/csv/input_bom.csv", TestCSVData.class, param);

		List<String> actual = list.stream().map(s -> s.toString()).collect(Collectors.toList());
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/output_default.txt"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVファイルが読み込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVファイル読み込みテスト_文字コードがあっていなくても化けたまま読み込める() throws ErrorCheckException {
		CsvParameter param = CsvParameter.builder().charset(Charset.forName("Shift-JIS")).build();

		List<TestCSVData> list = csvUtil.readCsvFile("src/test/resources/csv/input_default.csv", TestCSVData.class, param);
		Assert.assertTrue("結果が格納されること", null != list);
	}

	@Test
	public void 正常系_CSVファイル読み込みテスト_改行コードCRLF() throws ErrorCheckException, IOException {
		// パラメーターに改行コード未指定でも読み込みできる
		CsvParameter param = CsvParameter.builder().build();

		List<TestCSVData> list = csvUtil.readCsvFile("src/test/resources/csv/input_crlf.csv", TestCSVData.class, param);

		List<String> actual = list.stream().map(s -> s.toString()).collect(Collectors.toList());
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/output_default.txt"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVファイルが読み込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVファイル読み込みテスト_ダブルクォート囲みなし() throws ErrorCheckException, IOException {
		// パラメーターデフォルトのままで読み込みできる
		CsvParameter param = CsvParameter.builder().build();

		List<TestCSVData> list = csvUtil.readCsvFile("src/test/resources/csv/input_withoutQuote.csv", TestCSVData.class, param);

		List<String> actual = list.stream().map(s -> s.toString()).collect(Collectors.toList());
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/output_default.txt"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVファイルが読み込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVファイル読み込みテスト_項目にカンマありでダブルクォートパラメーター未指定() throws ErrorCheckException, IOException {
		// パラメーターデフォルトのままで読み込みできる
		CsvParameter param = CsvParameter.builder().build();

		List<TestCSVData> list = csvUtil.readCsvFile("src/test/resources/csv/input_commaItem.csv", TestCSVData.class, param);

		List<String> actual = list.stream().map(s -> s.toString()).collect(Collectors.toList());
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/output_commaItem.txt"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVファイルが読み込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVファイル読み込みテスト_NULLとして扱う文字列を設定() throws ErrorCheckException, IOException {
		CsvParameter param = CsvParameter.builder().nullValueString("テスト２").build();

		List<TestCSVData> list = csvUtil.readCsvFile("src/test/resources/csv/input_default.csv", TestCSVData.class, param);

		List<String> actual = list.stream().map(s -> s.toString()).collect(Collectors.toList());
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/output_nullValue.txt"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVファイルが読み込めること", expected, actual);
	}

	@Test
	public void 異常系_CSVファイル読み込みテスト_ファイルパスにNULLを与える() throws ErrorCheckException {
		CsvParameter param = CsvParameter.builder().header(false).build();
		List<TestCSVData> list = null;
		try {
			list = csvUtil.readCsvFile(null, TestCSVData.class, param);
			Assert.fail("正常終了した");
		} catch (ErrorCheckException e) {
			Assert.assertTrue("結果が格納されないこと", null == list);
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00200", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメーター「読み込み元CSVファイルパス」が未設定です。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	public void 異常系_CSVファイル読み込みテスト_エンティティクラスにNULLを与える() throws ErrorCheckException {
		CsvParameter param = CsvParameter.builder().header(false).build();
		List<TestCSVData> list = null;
		try {
			list = csvUtil.readCsvFile("src/test/resources/csv/input_default.csv", null, param);
			Assert.fail("正常終了した");
		} catch (ErrorCheckException e) {
			Assert.assertTrue("結果が格納されないこと", null == list);
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00200", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメーター「展開先エンティティクラス型」が未設定です。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	public void 異常系_CSVファイル読み込みテスト_ヘッダー設定不一致() throws ErrorCheckException {
		CsvParameter param = CsvParameter.builder().header(false).build();
		List<TestCSVData> list = null;
		try {
			list = csvUtil.readCsvFile("src/test/resources/csv/input_default.csv", TestCSVData.class, param);
			Assert.fail("正常終了した");
		} catch (ErrorCheckException e) {
			Assert.assertTrue("結果が格納されないこと", null == list);
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00101", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "CSVデータのフォーマットが不正です。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	public void 異常系_CSVファイル読み込みテスト_セパレーター設定不一致() throws ErrorCheckException {
		CsvParameter param = CsvParameter.builder().separator('\"').build();
		List<TestCSVData> list = null;
		try {
			list = csvUtil.readCsvFile("src/test/resources/csv/input_default.csv", TestCSVData.class, param);
			Assert.fail("正常終了した");
		} catch (ErrorCheckException e) {
			Assert.assertTrue("結果が格納されないこと", null == list);
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00101", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "CSVデータのフォーマットが不正です。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	public void 異常系_CSVファイル読み込みテスト_存在しないファイル() throws ErrorCheckException {
		CsvParameter param = CsvParameter.builder().separator('\"').build();
		List<TestCSVData> list = null;
		try {
			list = csvUtil.readCsvFile("dummy.csv", TestCSVData.class, param);
			Assert.fail("正常終了した");
		} catch (ErrorCheckException e) {
			Assert.assertTrue("結果が格納されないこと", null == list);
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00100", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", (new File("dummy.csv")).getAbsolutePath() + "が存在しません。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}
}
