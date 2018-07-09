package jp.co.ricoh.cotos.commonlib.TestCSVUtil;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;

import jp.co.ricoh.cotos.commonlib.csv.CSVUtil;
import jp.co.ricoh.cotos.commonlib.entity.CsvParam;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCSVWriter {

	@Autowired
	CSVUtil csvUtil;

	@Before
	public void beforeProcess() throws IOException {
		Files.deleteIfExists(Paths.get("output/dir/output.csv"));
		Files.deleteIfExists(Paths.get("output/dir"));
		Files.deleteIfExists(Paths.get("output/output.csv"));
	}

	@Test
	public void 正常系_CSVファイル書き込みテスト_デフォルトパラメーター() throws JsonProcessingException, FileNotFoundException, IOException, ParseException {
		CsvParam param = CsvParam.builder().build();

		List<TestCSVData> list = new ArrayList<>();
		list.add(new TestCSVData(1, "テスト１", 12, new SimpleDateFormat("yyyy/MM/dd").parse("2018/12/12"), 75.4));
		list.add(new TestCSVData(2, "テスト２", 10, new SimpleDateFormat("yyyy/MM/dd").parse("2016/03/15"), 40.5));
		list.add(new TestCSVData(3, null, 9, new SimpleDateFormat("yyyy/MM/dd").parse("2015/01/02"), 100.1));

		csvUtil.writeCsvFile("output/output.csv", list, param);

		List<String> actual = Files.readAllLines(Paths.get("output/output.csv"), StandardCharsets.UTF_8);
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/input_default.csv"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVファイルが書き込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVファイル書き込みテスト_ヘッダーなし() throws JsonProcessingException, FileNotFoundException, IOException, ParseException {
		CsvParam param = CsvParam.builder().header(false).build();

		List<TestCSVData> list = new ArrayList<>();
		list.add(new TestCSVData(1, "テスト１", 12, new SimpleDateFormat("yyyy/MM/dd").parse("2018/12/12"), 75.4));
		list.add(new TestCSVData(2, "テスト２", 10, new SimpleDateFormat("yyyy/MM/dd").parse("2016/03/15"), 40.5));
		list.add(new TestCSVData(3, null, 9, new SimpleDateFormat("yyyy/MM/dd").parse("2015/01/02"), 100.1));

		csvUtil.writeCsvFile("output/output.csv", list, param);

		List<String> actual = Files.readAllLines(Paths.get("output/output.csv"), StandardCharsets.UTF_8);
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/input_withoutHeader.csv"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVファイルが書き込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVファイル書き込みテスト_ヘッダー2バイト文字() throws JsonProcessingException, FileNotFoundException, IOException, ParseException {
		CsvParam param = CsvParam.builder().build();

		List<TestCSVDataHeaderJapanese> list = new ArrayList<>();
		list.add(new TestCSVDataHeaderJapanese(1, "テスト１", 12, new SimpleDateFormat("yyyy/MM/dd").parse("2018/12/12"), 75.4));
		list.add(new TestCSVDataHeaderJapanese(2, "テスト２", 10, new SimpleDateFormat("yyyy/MM/dd").parse("2016/03/15"), 40.5));
		list.add(new TestCSVDataHeaderJapanese(3, null, 9, new SimpleDateFormat("yyyy/MM/dd").parse("2015/01/02"), 100.1));

		csvUtil.writeCsvFile("output/output.csv", list, param);

		List<String> actual = Files.readAllLines(Paths.get("output/output.csv"), StandardCharsets.UTF_8);
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/input_columnnameJapanese.csv"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVファイルが書き込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVファイル書き込みテスト_セパレータータブ() throws JsonProcessingException, FileNotFoundException, IOException, ParseException {
		CsvParam param = CsvParam.builder().separator('\t').build();

		List<TestCSVData> list = new ArrayList<>();
		list.add(new TestCSVData(1, "テスト１", 12, new SimpleDateFormat("yyyy/MM/dd").parse("2018/12/12"), 75.4));
		list.add(new TestCSVData(2, "テスト２", 10, new SimpleDateFormat("yyyy/MM/dd").parse("2016/03/15"), 40.5));
		list.add(new TestCSVData(3, null, 9, new SimpleDateFormat("yyyy/MM/dd").parse("2015/01/02"), 100.1));

		csvUtil.writeCsvFile("output/output.csv", list, param);

		List<String> actual = Files.readAllLines(Paths.get("output/output.csv"), StandardCharsets.UTF_8);
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/input_seperatorTab.csv"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVファイルが書き込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVファイル書き込みテスト_SJISのCSVファイル() throws JsonProcessingException, FileNotFoundException, IOException, ParseException {
		CsvParam param = CsvParam.builder().charset(Charset.forName("Shift-JIS")).build();

		List<TestCSVData> list = new ArrayList<>();
		list.add(new TestCSVData(1, "テスト１", 12, new SimpleDateFormat("yyyy/MM/dd").parse("2018/12/12"), 75.4));
		list.add(new TestCSVData(2, "テスト２", 10, new SimpleDateFormat("yyyy/MM/dd").parse("2016/03/15"), 40.5));
		list.add(new TestCSVData(3, null, 9, new SimpleDateFormat("yyyy/MM/dd").parse("2015/01/02"), 100.1));

		csvUtil.writeCsvFile("output/output.csv", list, param);

		List<String> actual = Files.readAllLines(Paths.get("output/output.csv"), Charset.forName("Shift-JIS"));
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/input_default.csv"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVファイルが書き込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVファイル書き込みテスト_改行コードCRLF() throws JsonProcessingException, FileNotFoundException, IOException, ParseException {
		CsvParam param = CsvParam.builder().lineSeparator("\r\n").build();

		List<TestCSVData> list = new ArrayList<>();
		list.add(new TestCSVData(1, "テスト１", 12, new SimpleDateFormat("yyyy/MM/dd").parse("2018/12/12"), 75.4));
		list.add(new TestCSVData(2, "テスト２", 10, new SimpleDateFormat("yyyy/MM/dd").parse("2016/03/15"), 40.5));
		list.add(new TestCSVData(3, null, 9, new SimpleDateFormat("yyyy/MM/dd").parse("2015/01/02"), 100.1));

		csvUtil.writeCsvFile("output/output.csv", list, param);

		List<String> actual = Files.readAllLines(Paths.get("output/output.csv"), StandardCharsets.UTF_8);
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/input_default.csv"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVファイルが書き込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVファイル書き込みテスト_囲み文字なし() throws JsonProcessingException, FileNotFoundException, IOException, ParseException {
		CsvParam param = CsvParam.builder().quote(false).build();

		List<TestCSVData> list = new ArrayList<>();
		list.add(new TestCSVData(1, "テスト１", 12, new SimpleDateFormat("yyyy/MM/dd").parse("2018/12/12"), 75.4));
		list.add(new TestCSVData(2, "テスト２", 10, new SimpleDateFormat("yyyy/MM/dd").parse("2016/03/15"), 40.5));
		list.add(new TestCSVData(3, null, 9, new SimpleDateFormat("yyyy/MM/dd").parse("2015/01/02"), 100.1));

		csvUtil.writeCsvFile("output/output.csv", list, param);

		List<String> actual = Files.readAllLines(Paths.get("output/output.csv"), StandardCharsets.UTF_8);
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/input_withoutQuote.csv"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVファイルが書き込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVファイル書き込みテスト_文字列にカンマが含まれるがダブルクォートで囲みなし指定() throws JsonProcessingException, FileNotFoundException, IOException, ParseException {
		CsvParam param = CsvParam.builder().quote(false).build();

		List<TestCSVData> list = new ArrayList<>();
		list.add(new TestCSVData(1, "テスト１", 12, new SimpleDateFormat("yyyy/MM/dd").parse("2018/12/12"), 75.4));
		list.add(new TestCSVData(2, "テスト,２", 10, new SimpleDateFormat("yyyy/MM/dd").parse("2016/03/15"), 40.5));
		list.add(new TestCSVData(3, null, 9, new SimpleDateFormat("yyyy/MM/dd").parse("2015/01/02"), 100.1));

		csvUtil.writeCsvFile("output/output.csv", list, param);

		List<String> actual = Files.readAllLines(Paths.get("output/output.csv"), StandardCharsets.UTF_8);
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/input_commaItemWithoutQuote.csv"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVファイルが書き込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVファイル書き込みテスト_NULL文字列変更() throws JsonProcessingException, FileNotFoundException, IOException, ParseException {
		CsvParam param = CsvParam.builder().nullValueString("").build();

		List<TestCSVData> list = new ArrayList<>();
		list.add(new TestCSVData(1, "テスト１", 12, new SimpleDateFormat("yyyy/MM/dd").parse("2018/12/12"), 75.4));
		list.add(new TestCSVData(2, "テスト２", 10, new SimpleDateFormat("yyyy/MM/dd").parse("2016/03/15"), 40.5));
		list.add(new TestCSVData(3, null, 9, new SimpleDateFormat("yyyy/MM/dd").parse("2015/01/02"), 100.1));

		csvUtil.writeCsvFile("output/output.csv", list, param);

		List<String> actual = Files.readAllLines(Paths.get("output/output.csv"), StandardCharsets.UTF_8);
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/input_nullValue.csv"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVファイルが書き込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVファイル書き込みテスト_アペンドモードOFF() throws JsonProcessingException, FileNotFoundException, IOException, ParseException {
		CsvParam param = CsvParam.builder().build();

		List<TestCSVData> list = new ArrayList<>();
		list.add(new TestCSVData(1, "テスト１", 12, new SimpleDateFormat("yyyy/MM/dd").parse("2018/12/12"), 75.4));
		list.add(new TestCSVData(2, "テスト２", 10, new SimpleDateFormat("yyyy/MM/dd").parse("2016/03/15"), 40.5));
		list.add(new TestCSVData(3, null, 9, new SimpleDateFormat("yyyy/MM/dd").parse("2015/01/02"), 100.1));

		csvUtil.writeCsvFile("output/output.csv", list, param);
		csvUtil.writeCsvFile("output/output.csv", list, param);

		List<String> actual = Files.readAllLines(Paths.get("output/output.csv"), StandardCharsets.UTF_8);
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/input_default.csv"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVファイルが書き込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVファイル書き込みテスト_アペンドモードON() throws JsonProcessingException, FileNotFoundException, IOException, ParseException {
		CsvParam param = CsvParam.builder().appendMode(true).build();

		List<TestCSVData> list = new ArrayList<>();
		list.add(new TestCSVData(1, "テスト１", 12, new SimpleDateFormat("yyyy/MM/dd").parse("2018/12/12"), 75.4));
		list.add(new TestCSVData(2, "テスト２", 10, new SimpleDateFormat("yyyy/MM/dd").parse("2016/03/15"), 40.5));
		list.add(new TestCSVData(3, null, 9, new SimpleDateFormat("yyyy/MM/dd").parse("2015/01/02"), 100.1));

		csvUtil.writeCsvFile("output/output.csv", list, param);
		csvUtil.writeCsvFile("output/output.csv", list, param);

		List<String> actual = Files.readAllLines(Paths.get("output/output.csv"), StandardCharsets.UTF_8);
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/output_append.csv"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVファイルが書き込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVファイル書き込みテスト_ディレクトリが存在しない場合でも作成される() throws JsonProcessingException, FileNotFoundException, IOException, ParseException {
		CsvParam param = CsvParam.builder().build();

		List<TestCSVData> list = new ArrayList<>();
		list.add(new TestCSVData(1, "テスト１", 12, new SimpleDateFormat("yyyy/MM/dd").parse("2018/12/12"), 75.4));
		list.add(new TestCSVData(2, "テスト２", 10, new SimpleDateFormat("yyyy/MM/dd").parse("2016/03/15"), 40.5));
		list.add(new TestCSVData(3, null, 9, new SimpleDateFormat("yyyy/MM/dd").parse("2015/01/02"), 100.1));

		csvUtil.writeCsvFile("output/dir/output.csv", list, param);

		List<String> actual = Files.readAllLines(Paths.get("output/dir/output.csv"), StandardCharsets.UTF_8);
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/input_default.csv"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVファイルが書き込めること", expected, actual);
	}
}
