package jp.co.ricoh.cotos.commonlib.TestCSVUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
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
public class TestCSVWriter {

	@Autowired
	CSVUtil csvUtil;

	@Before
	public void beforeProcess() throws IOException {
		Files.createDirectories(Paths.get("output/"));

		Files.deleteIfExists(Paths.get("output/dir/output.csv"));
		Files.deleteIfExists(Paths.get("output/dir"));

		Stream.of(Paths.get("output/").toFile().listFiles()).forEach(s -> {
			s.setWritable(true);
			try {
				Files.delete(s.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	@Test
	public void 正常系_CSVファイル書き込みテスト_デフォルトパラメーター() throws ErrorCheckException, IOException, ParseException {
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
	public void 正常系_CSVファイル書き込みテスト_デフォルトパラメーターNULL() throws ErrorCheckException, IOException, ParseException {
		List<TestCSVData> list = new ArrayList<>();
		list.add(new TestCSVData(1, "テスト１", 12, new SimpleDateFormat("yyyy/MM/dd").parse("2018/12/12"), 75.4));
		list.add(new TestCSVData(2, "テスト２", 10, new SimpleDateFormat("yyyy/MM/dd").parse("2016/03/15"), 40.5));
		list.add(new TestCSVData(3, null, 9, new SimpleDateFormat("yyyy/MM/dd").parse("2015/01/02"), 100.1));

		csvUtil.writeCsvFile("output/output.csv", list, null);

		List<String> actual = Files.readAllLines(Paths.get("output/output.csv"), StandardCharsets.UTF_8);
		List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/input_default.csv"), StandardCharsets.UTF_8);
		Assert.assertEquals("CSVファイルが書き込めること", expected, actual);
	}

	@Test
	public void 正常系_CSVファイル書き込みテスト_ヘッダーなし() throws ErrorCheckException, IOException, ParseException {
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
	public void 正常系_CSVファイル書き込みテスト_ヘッダー2バイト文字() throws ErrorCheckException, IOException, ParseException {
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
	public void 正常系_CSVファイル書き込みテスト_セパレータータブ() throws ErrorCheckException, IOException, ParseException {
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
	public void 正常系_CSVファイル書き込みテスト_SJISのCSVファイル() throws ErrorCheckException, IOException, ParseException {
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
	public void 正常系_CSVファイル書き込みテスト_改行コードCRLF() throws ErrorCheckException, IOException, ParseException {
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
	public void 正常系_CSVファイル書き込みテスト_囲み文字なし() throws ErrorCheckException, IOException, ParseException {
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
	public void 正常系_CSVファイル書き込みテスト_文字列にカンマが含まれるがダブルクォートで囲みなし指定() throws ErrorCheckException, IOException, ParseException {
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
	public void 正常系_CSVファイル書き込みテスト_NULL文字列変更() throws ErrorCheckException, IOException, ParseException {
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
	public void 正常系_CSVファイル書き込みテスト_アペンドモードOFF() throws ErrorCheckException, IOException, ParseException {
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
	public void 正常系_CSVファイル書き込みテスト_アペンドモードON() throws ErrorCheckException, IOException, ParseException {
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

		List<Path> fileList = Files.list(Paths.get("output/output.csv").getParent()).filter(s -> !s.toString().endsWith("output.csv") && !s.toString().endsWith(".gitignore")).collect(Collectors.toList());
		Assert.assertTrue("バックアップファイルが残らないこと", fileList.size() == 0);
	}

	@Test
	public void 正常系_CSVファイル書き込みテスト_ディレクトリが存在しない場合でも作成される() throws ErrorCheckException, IOException, ParseException {
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

	@Test
	public void 異常系_CSVファイル書き込みテスト_ファイルパスにNULLを与える() throws ErrorCheckException, IOException, ParseException {
		CsvParam param = CsvParam.builder().build();

		List<TestCSVData> list = new ArrayList<>();
		list.add(new TestCSVData(1, "テスト１", 12, new SimpleDateFormat("yyyy/MM/dd").parse("2018/12/12"), 75.4));
		list.add(new TestCSVData(2, "テスト２", 10, new SimpleDateFormat("yyyy/MM/dd").parse("2016/03/15"), 40.5));
		list.add(new TestCSVData(3, null, 9, new SimpleDateFormat("yyyy/MM/dd").parse("2015/01/02"), 100.1));

		try {
			csvUtil.writeCsvFile(null, list, param);
			Assert.fail("正常終了した");
		} catch (ErrorCheckException e) {
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00200", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメーターfilePathが未設定です。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	public void 異常系_CSVファイル書き込みテスト_エンティティパラメーターにNULLを与える() throws ErrorCheckException, IOException, ParseException {
		CsvParam param = CsvParam.builder().build();

		try {
			csvUtil.writeCsvFile("output/output.csv", null, param);
			Assert.fail("正常終了した");
		} catch (ErrorCheckException e) {
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00200", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメーターentityListが未設定です。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	public void 異常系_CSVファイル書き込みテスト_エンティティパラメーターにサイズゼロのリストを与える() throws ErrorCheckException, IOException, ParseException {
		CsvParam param = CsvParam.builder().build();

		try {
			csvUtil.writeCsvFile("output/output.csv", new ArrayList<TestCSVData>(), param);
			Assert.fail("正常終了した");
		} catch (ErrorCheckException e) {
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00200", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメーターentityListが未設定です。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	public void 異常系_CSVファイル書き込みテスト_読み取り専用ファイルにアペンドしようとする() throws ErrorCheckException, IOException, ParseException {
		CsvParam param = CsvParam.builder().appendMode(true).build();

		List<TestCSVData> list = new ArrayList<>();
		list.add(new TestCSVData(1, "テスト１", 12, new SimpleDateFormat("yyyy/MM/dd").parse("2018/12/12"), 75.4));
		list.add(new TestCSVData(2, "テスト２", 10, new SimpleDateFormat("yyyy/MM/dd").parse("2016/03/15"), 40.5));
		list.add(new TestCSVData(3, null, 9, new SimpleDateFormat("yyyy/MM/dd").parse("2015/01/02"), 100.1));

		csvUtil.writeCsvFile("output/output.csv", list, param);

		File file = new File("output/output.csv");
		file.setReadOnly();

		try {
			csvUtil.writeCsvFile("output/output.csv", list, param);
			Assert.fail("正常終了した");
		} catch (ErrorCheckException e) {
			file.setWritable(true);

			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00107", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", file.getAbsolutePath() + "は読み取り専用ファイルです。", e.getErrorInfoList().get(0).getErrorMessage());

			List<String> actual = Files.readAllLines(Paths.get("output/output.csv"), StandardCharsets.UTF_8);
			List<String> expected = Files.readAllLines(Paths.get("src/test/resources/csv/input_default.csv"), StandardCharsets.UTF_8);
			Assert.assertEquals("2回目の書き込み処理が反映されないこと", expected, actual);
		}
	}
}
