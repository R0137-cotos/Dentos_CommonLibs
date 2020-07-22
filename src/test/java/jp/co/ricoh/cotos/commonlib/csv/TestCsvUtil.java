package jp.co.ricoh.cotos.commonlib.csv;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.ricoh.cotos.commonlib.dto.parameter.common.CsvParameter;
import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.logic.csv.CsvUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class TestCsvUtil {

	@Autowired
	CsvUtil csvUtil;

	@Test
	public void 正常系_CSV生成テスト_デフォルトパラメーター() throws ErrorCheckException, IOException, ParseException {
		CsvParameter param = CsvParameter.builder().build();

		List<TestCsvData> list = new ArrayList<>();
		list.add(new TestCsvData(1, "テスト１", 12, new SimpleDateFormat("yyyy/MM/dd").parse("2018/12/12"), 75.4));
		list.add(new TestCsvData(2, "テスト２", 10, new SimpleDateFormat("yyyy/MM/dd").parse("2016/03/15"), 40.5));
		list.add(new TestCsvData(3, null, 9, new SimpleDateFormat("yyyy/MM/dd").parse("2015/01/02"), 100.1));

		byte[] actual = csvUtil.createCsvData(list, param);
		byte[] expected = Files.readAllBytes(Paths.get("src/test/resources/csv/input_default.csv"));
		Assert.assertEquals("生成されたCSV情報が正しいこと", new String(actual, "UTF-8"), new String(expected, "UTF-8"));
	}

	@Test
	public void 正常系_CSV生成テスト_デフォルトパラメーターNULL() throws ErrorCheckException, IOException, ParseException {
		List<TestCsvData> list = new ArrayList<>();
		list.add(new TestCsvData(1, "テスト１", 12, new SimpleDateFormat("yyyy/MM/dd").parse("2018/12/12"), 75.4));
		list.add(new TestCsvData(2, "テスト２", 10, new SimpleDateFormat("yyyy/MM/dd").parse("2016/03/15"), 40.5));
		list.add(new TestCsvData(3, null, 9, new SimpleDateFormat("yyyy/MM/dd").parse("2015/01/02"), 100.1));

		byte[] actual = csvUtil.createCsvData(list, null);
		byte[] expected = Files.readAllBytes(Paths.get("src/test/resources/csv/input_default.csv"));
		Assert.assertEquals("生成されたCSV情報が正しいこと", new String(actual, "UTF-8"), new String(expected, "UTF-8"));
	}

	@Test
	public void 正常系_CSV生成テスト_ヘッダーなし() throws ErrorCheckException, IOException, ParseException {
		CsvParameter param = CsvParameter.builder().header(false).build();

		List<TestCsvData> list = new ArrayList<>();
		list.add(new TestCsvData(1, "テスト１", 12, new SimpleDateFormat("yyyy/MM/dd").parse("2018/12/12"), 75.4));
		list.add(new TestCsvData(2, "テスト２", 10, new SimpleDateFormat("yyyy/MM/dd").parse("2016/03/15"), 40.5));
		list.add(new TestCsvData(3, null, 9, new SimpleDateFormat("yyyy/MM/dd").parse("2015/01/02"), 100.1));

		byte[] actual = csvUtil.createCsvData(list, param);
		byte[] expected = Files.readAllBytes(Paths.get("src/test/resources/csv/input_withoutHeader.csv"));
		Assert.assertEquals("生成されたCSV情報が正しいこと", new String(actual, "UTF-8"), new String(expected, "UTF-8"));
	}

	@Test
	public void 正常系_CSV生成テスト_ヘッダー2バイト文字() throws ErrorCheckException, IOException, ParseException {
		CsvParameter param = CsvParameter.builder().build();

		List<TestCsvDataHeaderJapanese> list = new ArrayList<>();
		list.add(new TestCsvDataHeaderJapanese(1, "テスト１", 12, new SimpleDateFormat("yyyy/MM/dd").parse("2018/12/12"), 75.4));
		list.add(new TestCsvDataHeaderJapanese(2, "テスト２", 10, new SimpleDateFormat("yyyy/MM/dd").parse("2016/03/15"), 40.5));
		list.add(new TestCsvDataHeaderJapanese(3, null, 9, new SimpleDateFormat("yyyy/MM/dd").parse("2015/01/02"), 100.1));

		byte[] actual = csvUtil.createCsvData(list, param);
		byte[] expected = Files.readAllBytes(Paths.get("src/test/resources/csv/input_columnnameJapanese.csv"));
		Assert.assertEquals("生成されたCSV情報が正しいこと", new String(actual, "UTF-8"), new String(expected, "UTF-8"));
	}

	@Test
	public void 正常系_CSV生成テスト_セパレータータブ() throws ErrorCheckException, IOException, ParseException {
		CsvParameter param = CsvParameter.builder().separator('\t').build();

		List<TestCsvData> list = new ArrayList<>();
		list.add(new TestCsvData(1, "テスト１", 12, new SimpleDateFormat("yyyy/MM/dd").parse("2018/12/12"), 75.4));
		list.add(new TestCsvData(2, "テスト２", 10, new SimpleDateFormat("yyyy/MM/dd").parse("2016/03/15"), 40.5));
		list.add(new TestCsvData(3, null, 9, new SimpleDateFormat("yyyy/MM/dd").parse("2015/01/02"), 100.1));

		byte[] actual = csvUtil.createCsvData(list, param);
		byte[] expected = Files.readAllBytes(Paths.get("src/test/resources/csv/input_seperatorTab.csv"));
		Assert.assertEquals("生成されたCSV情報が正しいこと", new String(actual, "UTF-8"), new String(expected, "UTF-8"));
	}

	@Test
	public void 正常系_CSV生成テスト_SJISのCSVファイル() throws ErrorCheckException, IOException, ParseException {
		CsvParameter param = CsvParameter.builder().charset(Charset.forName("Shift-JIS")).build();

		List<TestCsvData> list = new ArrayList<>();
		list.add(new TestCsvData(1, "テスト１", 12, new SimpleDateFormat("yyyy/MM/dd").parse("2018/12/12"), 75.4));
		list.add(new TestCsvData(2, "テスト２", 10, new SimpleDateFormat("yyyy/MM/dd").parse("2016/03/15"), 40.5));
		list.add(new TestCsvData(3, null, 9, new SimpleDateFormat("yyyy/MM/dd").parse("2015/01/02"), 100.1));

		byte[] actual = csvUtil.createCsvData(list, param);
		byte[] expected = Files.readAllBytes(Paths.get("src/test/resources/csv/input_default.csv"));
		Assert.assertEquals("生成されたCSV情報が正しいこと", new String(actual, "Shift-JIS"), new String(expected, "UTF-8"));
	}

	@Test
	public void 正常系_CSV生成テスト_改行コードCRLF() throws ErrorCheckException, IOException, ParseException {
		CsvParameter param = CsvParameter.builder().lineSeparator("\r\n").build();

		List<TestCsvData> list = new ArrayList<>();
		list.add(new TestCsvData(1, "テスト１", 12, new SimpleDateFormat("yyyy/MM/dd").parse("2018/12/12"), 75.4));
		list.add(new TestCsvData(2, "テスト２", 10, new SimpleDateFormat("yyyy/MM/dd").parse("2016/03/15"), 40.5));
		list.add(new TestCsvData(3, null, 9, new SimpleDateFormat("yyyy/MM/dd").parse("2015/01/02"), 100.1));

		byte[] actual = csvUtil.createCsvData(list, param);
		byte[] expected = Files.readAllBytes(Paths.get("src/test/resources/csv/input_crlf.csv"));
		Assert.assertEquals("生成されたCSV情報が正しいこと", new String(actual, "UTF-8"), new String(expected, "UTF-8"));
	}

	@Test
	public void 正常系_CSV生成テスト_囲み文字なし() throws ErrorCheckException, IOException, ParseException {
		CsvParameter param = CsvParameter.builder().quote(false).build();

		List<TestCsvData> list = new ArrayList<>();
		list.add(new TestCsvData(1, "テスト１", 12, new SimpleDateFormat("yyyy/MM/dd").parse("2018/12/12"), 75.4));
		list.add(new TestCsvData(2, "テスト２", 10, new SimpleDateFormat("yyyy/MM/dd").parse("2016/03/15"), 40.5));
		list.add(new TestCsvData(3, null, 9, new SimpleDateFormat("yyyy/MM/dd").parse("2015/01/02"), 100.1));

		byte[] actual = csvUtil.createCsvData(list, param);
		byte[] expected = Files.readAllBytes(Paths.get("src/test/resources/csv/input_withoutQuote.csv"));
		Assert.assertEquals("生成されたCSV情報が正しいこと", new String(actual, "UTF-8"), new String(expected, "UTF-8"));
	}

	@Test
	public void 正常系_CSV生成テスト_文字列にカンマが含まれるがダブルクォートで囲みなし指定() throws ErrorCheckException, IOException, ParseException {
		CsvParameter param = CsvParameter.builder().quote(false).build();

		List<TestCsvData> list = new ArrayList<>();
		list.add(new TestCsvData(1, "テスト１", 12, new SimpleDateFormat("yyyy/MM/dd").parse("2018/12/12"), 75.4));
		list.add(new TestCsvData(2, "テスト,２", 10, new SimpleDateFormat("yyyy/MM/dd").parse("2016/03/15"), 40.5));
		list.add(new TestCsvData(3, null, 9, new SimpleDateFormat("yyyy/MM/dd").parse("2015/01/02"), 100.1));

		byte[] actual = csvUtil.createCsvData(list, param);
		byte[] expected = Files.readAllBytes(Paths.get("src/test/resources/csv/input_commaItemWithoutQuote.csv"));
		Assert.assertEquals("生成されたCSV情報が正しいこと", new String(actual, "UTF-8"), new String(expected, "UTF-8"));
	}

	@Test
	public void 正常系_CSV生成テスト_NULL文字列変更() throws ErrorCheckException, IOException, ParseException {
		CsvParameter param = CsvParameter.builder().nullValueString("").build();

		List<TestCsvData> list = new ArrayList<>();
		list.add(new TestCsvData(1, "テスト１", 12, new SimpleDateFormat("yyyy/MM/dd").parse("2018/12/12"), 75.4));
		list.add(new TestCsvData(2, "テスト２", 10, new SimpleDateFormat("yyyy/MM/dd").parse("2016/03/15"), 40.5));
		list.add(new TestCsvData(3, null, 9, new SimpleDateFormat("yyyy/MM/dd").parse("2015/01/02"), 100.1));

		byte[] actual = csvUtil.createCsvData(list, param);
		byte[] expected = Files.readAllBytes(Paths.get("src/test/resources/csv/input_nullValue.csv"));
		Assert.assertEquals("生成されたCSV情報が正しいこと", new String(actual, "UTF-8"), new String(expected, "UTF-8"));
	}

	@Test
	public void 異常系_CSV生成テスト_エンティティパラメーターにNULLを与える() throws ErrorCheckException, IOException, ParseException {
		CsvParameter param = CsvParameter.builder().build();

		try {
			csvUtil.createCsvData(null, param);
			Assert.fail("正常終了した");
		} catch (ErrorCheckException e) {
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00107", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "CSV生成時に必要なエンティティリストが設定されていません。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	public void 異常系_CSV生成テスト_エンティティパラメーターにサイズゼロのリストを与える() throws ErrorCheckException, IOException, ParseException {
		CsvParameter param = CsvParameter.builder().build();

		try {
			csvUtil.createCsvData(new ArrayList<TestCsvData>(), param);
			Assert.fail("正常終了した");
		} catch (ErrorCheckException e) {
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00107", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "CSV生成時に必要なエンティティリストが設定されていません。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}
}
