package jp.co.ricoh.cotos.commonlib.TestCSVUtil;

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
import jp.co.ricoh.cotos.commonlib.entity.CsvParam;
import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCSVReaderForData {

	@Autowired
	CSVUtil csvUtil;

	@Test
	public void 正常系_CSVデータ読み込みテスト_デフォルトパラメーター() throws ErrorCheckException, IOException {
		CsvParam param = CsvParam.builder().build();

		String csvData = new String(Files.readAllBytes(Paths.get("src/test/resources/csv/input_default.csv")), StandardCharsets.UTF_8);
		List<TestCSVData> list = csvUtil.readCsvData(csvData, TestCSVData.class, param);

		List<String> actual = list.stream().map(s -> s.toString()).collect(Collectors.toList());
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/output_default.txt"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVデータが読み込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVデータ読み込みテスト_デフォルトパラメーターNULL() throws ErrorCheckException, IOException {
		String csvData = new String(Files.readAllBytes(Paths.get("src/test/resources/csv/input_default.csv")), StandardCharsets.UTF_8);
		List<TestCSVData> list = csvUtil.readCsvData(csvData, TestCSVData.class, null);

		List<String> actual = list.stream().map(s -> s.toString()).collect(Collectors.toList());
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/output_default.txt"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVデータが読み込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVデータ読み込みテスト_ヘッダーなし() throws ErrorCheckException, IOException {
		CsvParam param = CsvParam.builder().header(false).build();

		String csvData = new String(Files.readAllBytes(Paths.get("src/test/resources/csv/input_withoutHeader.csv")), StandardCharsets.UTF_8);
		List<TestCSVData> list = csvUtil.readCsvData(csvData, TestCSVData.class, param);

		List<String> actual = list.stream().map(s -> s.toString()).collect(Collectors.toList());
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/output_default.txt"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVデータが読み込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVデータ読み込みテスト_ヘッダー2バイト文字() throws ErrorCheckException, IOException {
		CsvParam param = CsvParam.builder().build();

		String csvData = new String(Files.readAllBytes(Paths.get("src/test/resources/csv/input_columnnameJapanese.csv")), StandardCharsets.UTF_8);
		List<TestCSVDataHeaderJapanese> list = csvUtil.readCsvData(csvData, TestCSVDataHeaderJapanese.class, param);

		List<String> actual = list.stream().map(s -> s.toString()).collect(Collectors.toList());
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/output_columnnameJapanese.txt"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVデータが読み込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVデータ読み込みテスト_セパレータータブ() throws ErrorCheckException, IOException {
		CsvParam param = CsvParam.builder().separator('\t').build();

		String csvData = new String(Files.readAllBytes(Paths.get("src/test/resources/csv/input_seperatorTab.csv")), StandardCharsets.UTF_8);
		List<TestCSVData> list = csvUtil.readCsvData(csvData, TestCSVData.class, param);

		List<String> actual = list.stream().map(s -> s.toString()).collect(Collectors.toList());
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/output_default.txt"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVデータが読み込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVデータ読み込みテスト_UTF8BOM付き() throws ErrorCheckException, IOException {
		// パラメーターデフォルトのままで読み込みできる
		CsvParam param = CsvParam.builder().build();

		String csvData = new String(Files.readAllBytes(Paths.get("src/test/resources/csv/input_bom.csv")), StandardCharsets.UTF_8);
		List<TestCSVData> list = csvUtil.readCsvData(csvData, TestCSVData.class, param);

		List<String> actual = list.stream().map(s -> s.toString()).collect(Collectors.toList());
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/output_default.txt"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVデータが読み込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVデータ読み込みテスト_文字コードがあっていなくても化けたまま読み込める() throws ErrorCheckException, IOException {
		CsvParam param = CsvParam.builder().charset(Charset.forName("Shift-JIS")).build();

		String csvData = new String(Files.readAllBytes(Paths.get("src/test/resources/csv/input_default.csv")), StandardCharsets.UTF_8);
		List<TestCSVData> list = csvUtil.readCsvData(csvData, TestCSVData.class, param);
		Assert.assertTrue("結果が格納されること", null != list);
	}

	@Test
	public void 正常系_CSVデータ読み込みテスト_改行コードCRLF() throws ErrorCheckException, IOException {
		// パラメーターに改行コード未指定でも読み込みできる
		CsvParam param = CsvParam.builder().build();

		String csvData = new String(Files.readAllBytes(Paths.get("src/test/resources/csv/input_crlf.csv")), StandardCharsets.UTF_8);
		List<TestCSVData> list = csvUtil.readCsvData(csvData, TestCSVData.class, param);

		List<String> actual = list.stream().map(s -> s.toString()).collect(Collectors.toList());
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/output_default.txt"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVデータが読み込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVデータ読み込みテスト_ダブルクォート囲みなし() throws ErrorCheckException, IOException {
		// パラメーターデフォルトのままで読み込みできる
		CsvParam param = CsvParam.builder().build();

		String csvData = new String(Files.readAllBytes(Paths.get("src/test/resources/csv/input_withoutQuote.csv")), StandardCharsets.UTF_8);
		List<TestCSVData> list = csvUtil.readCsvData(csvData, TestCSVData.class, param);

		List<String> actual = list.stream().map(s -> s.toString()).collect(Collectors.toList());
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/output_default.txt"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVデータが読み込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVデータ読み込みテスト_項目にカンマありでダブルクォートパラメーター未指定() throws ErrorCheckException, IOException {
		// パラメーターデフォルトのままで読み込みできる
		CsvParam param = CsvParam.builder().build();

		String csvData = new String(Files.readAllBytes(Paths.get("src/test/resources/csv/input_commaItem.csv")), StandardCharsets.UTF_8);
		List<TestCSVData> list = csvUtil.readCsvData(csvData, TestCSVData.class, param);

		List<String> actual = list.stream().map(s -> s.toString()).collect(Collectors.toList());
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/output_commaItem.txt"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVデータが読み込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVデータ読み込みテスト_NULLとして扱う文字列を設定() throws ErrorCheckException, IOException {
		CsvParam param = CsvParam.builder().nullValueString("テスト２").build();

		String csvData = new String(Files.readAllBytes(Paths.get("src/test/resources/csv/input_default.csv")), StandardCharsets.UTF_8);
		List<TestCSVData> list = csvUtil.readCsvData(csvData, TestCSVData.class, param);

		List<String> actual = list.stream().map(s -> s.toString()).collect(Collectors.toList());
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/output_nullValue.txt"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVデータが読み込めること", expected, actual);
	}

	@Test
	public void 異常系_CSVデータ読み込みテスト_CSVデータにNULLを与える() throws ErrorCheckException {
		CsvParam param = CsvParam.builder().header(false).build();
		List<TestCSVData> list = null;
		try {
			list = csvUtil.readCsvData(null, TestCSVData.class, param);
			Assert.fail("正常終了した");
		} catch (ErrorCheckException e) {
			Assert.assertTrue("結果が格納されないこと", null == list);
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00200", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメーターcsvDataが未設定です。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	public void 異常系_CSVデータ読み込みテスト_CSVデータに空文字を与える() throws ErrorCheckException {
		CsvParam param = CsvParam.builder().header(false).build();
		List<TestCSVData> list = null;
		try {
			list = csvUtil.readCsvData("", TestCSVData.class, param);
			Assert.fail("正常終了した");
		} catch (ErrorCheckException e) {
			Assert.assertTrue("結果が格納されないこと", null == list);
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00200", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメーターcsvDataが未設定です。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	public void 異常系_CSVデータ読み込みテスト_エンティティクラスにNULLを与える() throws ErrorCheckException, IOException {
		CsvParam param = CsvParam.builder().header(false).build();
		List<TestCSVData> list = null;
		try {
			String csvData = new String(Files.readAllBytes(Paths.get("src/test/resources/csv/input_default.csv")), StandardCharsets.UTF_8);
			list = csvUtil.readCsvData(csvData, null, param);
			Assert.fail("正常終了した");
		} catch (ErrorCheckException e) {
			Assert.assertTrue("結果が格納されないこと", null == list);
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00200", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメーターentityClassが未設定です。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	public void 異常系_CSVデータ読み込みテスト_ヘッダー設定不一致() throws ErrorCheckException, IOException {
		CsvParam param = CsvParam.builder().header(false).build();
		List<TestCSVData> list = null;
		try {
			String csvData = new String(Files.readAllBytes(Paths.get("src/test/resources/csv/input_default.csv")), StandardCharsets.UTF_8);
			list = csvUtil.readCsvData(csvData, TestCSVData.class, param);
			Assert.fail("正常終了した");
		} catch (ErrorCheckException e) {
			Assert.assertTrue("結果が格納されないこと", null == list);
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00101", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "CSVデータのフォーマットが不正です。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	public void 異常系_CSVデータ読み込みテスト_セパレーター設定不一致() throws ErrorCheckException, IOException {
		CsvParam param = CsvParam.builder().separator('\"').build();
		List<TestCSVData> list = null;
		try {
			String csvData = new String(Files.readAllBytes(Paths.get("src/test/resources/csv/input_default.csv")), StandardCharsets.UTF_8);
			list = csvUtil.readCsvData(csvData, TestCSVData.class, param);
			Assert.fail("正常終了した");
		} catch (ErrorCheckException e) {
			Assert.assertTrue("結果が格納されないこと", null == list);
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00101", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "CSVデータのフォーマットが不正です。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}
}
