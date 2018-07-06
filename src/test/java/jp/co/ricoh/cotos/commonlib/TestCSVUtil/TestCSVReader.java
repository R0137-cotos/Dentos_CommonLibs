package jp.co.ricoh.cotos.commonlib.TestCSVUtil;

import java.io.FileNotFoundException;
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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;

import jp.co.ricoh.cotos.commonlib.csv.CSVUtil;
import jp.co.ricoh.cotos.commonlib.entity.CsvParam;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCSVReader {

	@Autowired
	CSVUtil csvUtil;

	@Test
	public void 正常系_CSVファイル読み込みテスト_デフォルトパラメーター() throws JsonProcessingException, FileNotFoundException, IOException {
		CsvParam param = CsvParam.builder().build();

		List<TestCSVData> list = csvUtil.readCsvFile("src/test/resources/csv/input_default.csv", TestCSVData.class, param);

		List<String> actual = list.stream().map(s -> s.toString()).collect(Collectors.toList());
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/output_default.txt"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVファイルが読み込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVファイル読み込みテスト_ヘッダーなし() throws JsonProcessingException, FileNotFoundException, IOException {
		CsvParam param = CsvParam.builder().header(false).build();

		List<TestCSVData> list = csvUtil.readCsvFile("src/test/resources/csv/input_withoutHeader.csv", TestCSVData.class, param);

		List<String> actual = list.stream().map(s -> s.toString()).collect(Collectors.toList());
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/output_default.txt"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVファイルが読み込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVファイル読み込みテスト_ヘッダー2バイト文字() throws JsonProcessingException, FileNotFoundException, IOException {
		CsvParam param = CsvParam.builder().build();

		List<TestCSVDataHeaderJapanese> list = csvUtil.readCsvFile("src/test/resources/csv/input_columnnameJapanese.csv", TestCSVDataHeaderJapanese.class, param);

		List<String> actual = list.stream().map(s -> s.toString()).collect(Collectors.toList());
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/output_columnnameJapanese.txt"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVファイルが読み込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVファイル読み込みテスト_セパレータータブ() throws JsonProcessingException, FileNotFoundException, IOException {
		CsvParam param = CsvParam.builder().separator('\t').build();

		List<TestCSVData> list = csvUtil.readCsvFile("src/test/resources/csv/input_seperatorTab.csv", TestCSVData.class, param);

		List<String> actual = list.stream().map(s -> s.toString()).collect(Collectors.toList());
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/output_default.txt"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVファイルが読み込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVファイル読み込みテスト_SJISのCSVファイル() throws JsonProcessingException, FileNotFoundException, IOException {
		CsvParam param = CsvParam.builder().charset(Charset.forName("Shift-JIS")).build();

		List<TestCSVData> list = csvUtil.readCsvFile("src/test/resources/csv/input_shiftJIS.csv", TestCSVData.class, param);

		List<String> actual = list.stream().map(s -> s.toString()).collect(Collectors.toList());
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/output_default.txt"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVファイルが読み込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVファイル読み込みテスト_UTF8BOM付き() throws JsonProcessingException, FileNotFoundException, IOException {
		// パラメーターデフォルトのままで読み込みできる
		CsvParam param = CsvParam.builder().build();

		List<TestCSVData> list = csvUtil.readCsvFile("src/test/resources/csv/input_bom.csv", TestCSVData.class, param);

		List<String> actual = list.stream().map(s -> s.toString()).collect(Collectors.toList());
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/output_default.txt"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVファイルが読み込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVファイル読み込みテスト_文字コードがあっていなくても化けたまま読み込める() throws JsonProcessingException, FileNotFoundException, IOException {
		CsvParam param = CsvParam.builder().charset(Charset.forName("Shift-JIS")).build();

		List<TestCSVData> list = csvUtil.readCsvFile("src/test/resources/csv/input_default.csv", TestCSVData.class, param);
		Assert.assertTrue("結果が格納されること", null != list);
	}

	@Test
	public void 正常系_CSVファイル読み込みテスト_改行コードCRLF() throws JsonProcessingException, FileNotFoundException, IOException {
		// パラメーターに改行コード未指定でも読み込みできる
		CsvParam param = CsvParam.builder().build();

		List<TestCSVData> list = csvUtil.readCsvFile("src/test/resources/csv/input_crlf.csv", TestCSVData.class, param);

		List<String> actual = list.stream().map(s -> s.toString()).collect(Collectors.toList());
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/output_default.txt"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVファイルが読み込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVファイル読み込みテスト_ダブルクォート囲みなし() throws JsonProcessingException, FileNotFoundException, IOException {
		// パラメーターデフォルトのままで読み込みできる
		CsvParam param = CsvParam.builder().build();

		List<TestCSVData> list = csvUtil.readCsvFile("src/test/resources/csv/input_withoutQuote.csv", TestCSVData.class, param);

		List<String> actual = list.stream().map(s -> s.toString()).collect(Collectors.toList());
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/output_default.txt"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVファイルが読み込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVファイル読み込みテスト_項目にカンマありでダブルクォートパラメーター未指定() throws JsonProcessingException, FileNotFoundException, IOException {
		// パラメーターデフォルトのままで読み込みできる
		CsvParam param = CsvParam.builder().build();

		List<TestCSVData> list = csvUtil.readCsvFile("src/test/resources/csv/input_commaItem.csv", TestCSVData.class, param);

		List<String> actual = list.stream().map(s -> s.toString()).collect(Collectors.toList());
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/output_commaItem.txt"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVファイルが読み込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVファイル読み込みテスト_NULLとして扱う文字列を設定() throws JsonProcessingException, FileNotFoundException, IOException {
		CsvParam param = CsvParam.builder().nullValueString("テスト２").build();

		List<TestCSVData> list = csvUtil.readCsvFile("src/test/resources/csv/input_default.csv", TestCSVData.class, param);

		List<String> actual = list.stream().map(s -> s.toString()).collect(Collectors.toList());
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/output_nullValue.txt"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVファイルが読み込めること", expected, actual);
	}

	@Test
	public void 異常系_CSVファイル読み込みテスト_ヘッダー設定不一致() throws JsonProcessingException, FileNotFoundException, IOException {
		CsvParam param = CsvParam.builder().header(false).build();
		List<TestCSVData> list = null;
		try {
			list = csvUtil.readCsvFile("src/test/resources/csv/input_default.csv", TestCSVData.class, param);
			Assert.fail("正常終了した");
		} catch (RuntimeJsonMappingException e) {
			Assert.assertTrue("結果が格納されないこと", null == list);
		}
	}

	@Test
	public void 異常系_CSVファイル読み込みテスト_セパレーター設定不一致() throws JsonProcessingException, FileNotFoundException, IOException {
		CsvParam param = CsvParam.builder().separator('\"').build();
		List<TestCSVData> list = null;
		try {
			list = csvUtil.readCsvFile("src/test/resources/csv/input_default.csv", TestCSVData.class, param);
			Assert.fail("正常終了した");
		} catch (JsonParseException e) {
			Assert.assertTrue("結果が格納されないこと", null == list);
		}
	}

	@Test
	public void 異常系_CSVファイル読み込みテスト_存在しないファイル() throws JsonProcessingException, FileNotFoundException, IOException {
		CsvParam param = CsvParam.builder().separator('\"').build();
		List<TestCSVData> list = null;
		try {
			list = csvUtil.readCsvFile("dummy.csv", TestCSVData.class, param);
			Assert.fail("正常終了した");
		} catch (FileNotFoundException e) {
			Assert.assertTrue("結果が格納されないこと", null == list);
		}
	}
}
