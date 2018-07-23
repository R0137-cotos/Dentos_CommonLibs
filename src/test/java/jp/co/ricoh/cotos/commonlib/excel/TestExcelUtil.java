package jp.co.ricoh.cotos.commonlib.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import jp.co.ricoh.cotos.commonlib.excel.seishiki.Application;
import jp.co.ricoh.cotos.commonlib.excel.seishiki.InputEntity;
import jp.co.ricoh.cotos.commonlib.excel.seishiki.ItemInfo;
import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestExcelUtil {

	@Autowired
	ExcelUtil excelUtil;

	@Before
	public void init() throws IOException {
		Files.deleteIfExists(Paths.get("output/output.xls"));
		Files.deleteIfExists(Paths.get("output/output.xlsx"));
	}

	@Test
	public void 正常系_単一シート出力テンプレート() throws ErrorCheckException, ParseException, IOException {
		excelUtil.OutputExcelReports("src/test/resources/excel/template/single.xls", convertYaml2List("src/test/resources/excel/entityData/single.yml"), "output/output.xls");
	}

	@Test
	public void 正常系_複数シート出力テンプレート() throws ErrorCheckException, ParseException, IOException {
		excelUtil.OutputExcelReports("src/test/resources/excel/template/multi.xls", convertYaml2List("src/test/resources/excel/entityData/single.yml"), "output/output.xls");
	}

	@Test
	public void 正常系_単一および複数シート出力テンプレート() throws ErrorCheckException, ParseException, IOException {
		excelUtil.OutputExcelReports("src/test/resources/excel/template/both.xls", convertYaml2List("src/test/resources/excel/entityData/single.yml"), "output/output.xls");
	}

	@Test
	public void 正常系_単一および複数シート出力テンプレート2() throws ErrorCheckException, ParseException, IOException {
		excelUtil.OutputExcelReports("src/test/resources/excel/template/single2sheet.xls", convertYaml2List("src/test/resources/excel/entityData/single.yml"), "output/output.xls");
	}

	@Test
	public void 正常系_エクセル帳票出力およびシート削除組み合わせテスト() throws Exception {
		// Prepare
		TestExcelEntity entity = new TestExcelEntity();
		entity.setSimpleData(createSimpleData());
		entity.setPersonData(createPersonData());
		entity.setMultiSheetPersonData(createMultiPersonData());
		entity.setMultiSheetPersonDataSheetNames(entity.getMultiSheetPersonData().stream().map(s -> s.getSheetName()).collect(Collectors.toList()));

		// Action
		excelUtil.OutputExcelReports("src/test/resources/excel/template/template.xls", entity, "output/output.xls");
		excelUtil.DeleteExcelSheet("output/output.xls", Arrays.asList("複数シートテンプレート", "不要シート"));

		// Check
		String resultExcelFilePath = "output/test_out/output.xls";
		String referenceExcelFilePath = "src/test/resources/excel/reference/output.xls";
		compareEXCEL(referenceExcelFilePath, resultExcelFilePath);
	}

	@Test
	public void 異常系_テンプレートファイルパスNULL() throws ParseException {
		// Prepare
		TestExcelEntity entity = new TestExcelEntity();
		entity.setSimpleData(createSimpleData());
		entity.setPersonData(createPersonData());
		entity.setMultiSheetPersonData(createMultiPersonData());
		entity.setMultiSheetPersonDataSheetNames(entity.getMultiSheetPersonData().stream().map(s -> s.getSheetName()).collect(Collectors.toList()));

		try {
			// Action
			excelUtil.OutputExcelReports(null, entity, "output/output.xls");
		} catch (ErrorCheckException e) {
			// Check
			Assert.assertTrue("エクセル帳票が出力されないこと", Files.notExists(Paths.get("output/output.xls")));
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00200", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメーターtemplateFilePathが未設定です。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	public void 異常系_テンプレートファイルパスが空文字() throws ParseException {
		// Prepare
		TestExcelEntity entity = new TestExcelEntity();
		entity.setSimpleData(createSimpleData());
		entity.setPersonData(createPersonData());
		entity.setMultiSheetPersonData(createMultiPersonData());
		entity.setMultiSheetPersonDataSheetNames(entity.getMultiSheetPersonData().stream().map(s -> s.getSheetName()).collect(Collectors.toList()));

		try {
			// Action
			excelUtil.OutputExcelReports("", entity, "output/output.xls");
		} catch (ErrorCheckException e) {
			// Check
			Assert.assertTrue("エクセル帳票が出力されないこと", Files.notExists(Paths.get("output/output.xls")));
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00200", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメーターtemplateFilePathが未設定です。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	public void 異常系_マッピング用エンティティNULL() throws ParseException {
		try {
			// Action
			excelUtil.OutputExcelReports("src/test/resources/excel/template/template.xls", null, "output/output.xls");
		} catch (ErrorCheckException e) {
			// Check
			Assert.assertTrue("エクセル帳票が出力されないこと", Files.notExists(Paths.get("output/output.xls")));
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00200", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメーターentityが未設定です。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	public void 異常系_出力エクセル帳票ファイルパスNULL() throws ParseException {
		// Prepare
		TestExcelEntity entity = new TestExcelEntity();
		entity.setSimpleData(createSimpleData());
		entity.setPersonData(createPersonData());
		entity.setMultiSheetPersonData(createMultiPersonData());
		entity.setMultiSheetPersonDataSheetNames(entity.getMultiSheetPersonData().stream().map(s -> s.getSheetName()).collect(Collectors.toList()));

		try {
			// Action
			excelUtil.OutputExcelReports("src/test/resources/excel/template/template.xls", entity, null);
		} catch (ErrorCheckException e) {
			// Check
			Assert.assertTrue("エクセル帳票が出力されないこと", Files.notExists(Paths.get("output/output.xls")));
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00200", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメーターoutputFilePathが未設定です。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	public void 異常系_出力エクセル帳票ファイルパスが空文字() throws ParseException {
		// Prepare
		TestExcelEntity entity = new TestExcelEntity();
		entity.setSimpleData(createSimpleData());
		entity.setPersonData(createPersonData());
		entity.setMultiSheetPersonData(createMultiPersonData());
		entity.setMultiSheetPersonDataSheetNames(entity.getMultiSheetPersonData().stream().map(s -> s.getSheetName()).collect(Collectors.toList()));

		try {
			// Action
			excelUtil.OutputExcelReports("src/test/resources/excel/template/template.xls", entity, "");
		} catch (ErrorCheckException e) {
			// Check
			Assert.assertTrue("エクセル帳票が出力されないこと", Files.notExists(Paths.get("output/output.xls")));
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00200", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメーターoutputFilePathが未設定です。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	public void 異常系_テンプレートファイルが存在しない() throws ParseException {
		// Prepare
		TestExcelEntity entity = new TestExcelEntity();
		entity.setSimpleData(createSimpleData());
		entity.setPersonData(createPersonData());
		entity.setMultiSheetPersonData(createMultiPersonData());
		entity.setMultiSheetPersonDataSheetNames(entity.getMultiSheetPersonData().stream().map(s -> s.getSheetName()).collect(Collectors.toList()));

		try {
			// Action
			excelUtil.OutputExcelReports("dummy.xls", entity, "output/output.xls");
		} catch (ErrorCheckException e) {
			// Check
			Assert.assertTrue("エクセル帳票が出力されないこと", Files.notExists(Paths.get("output/output.xls")));
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00100", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", (new File("dummy.xls")).getAbsolutePath() + "が存在しません。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	public void 異常系_エクセル帳票ファイルが既に存在する() throws ParseException, IOException {
		// Prepare
		TestExcelEntity entity = new TestExcelEntity();
		entity.setSimpleData(createSimpleData());
		entity.setPersonData(createPersonData());
		entity.setMultiSheetPersonData(createMultiPersonData());
		entity.setMultiSheetPersonDataSheetNames(entity.getMultiSheetPersonData().stream().map(s -> s.getSheetName()).collect(Collectors.toList()));

		Files.createFile(Paths.get("output/output.xls"));

		try {
			// Action
			excelUtil.OutputExcelReports("src/test/resources/excel/template/template.xls", entity, "output/output.xls");
		} catch (ErrorCheckException e) {
			// Check
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00108", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", (new File("output/output.xls")).getAbsolutePath() + "は既に存在します。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	public void 正常系_存在するシートを複数削除() throws Exception {
		// Prepare
		Files.copy(Paths.get("src/test/resources/excel/input/input.xlsx"), Paths.get("output/output.xlsx"));

		// Action
		excelUtil.DeleteExcelSheet("output/output.xlsx", Arrays.asList("Sheet1", "Sheet2"));

		// Check
		String resultExcelFilePath = "output/output.xlsx";
		String referenceExcelFilePath = "src/test/resources/excel/reference/output_delete1and2.xlsx";
		compareEXCEL(referenceExcelFilePath, resultExcelFilePath);
	}

	@Test
	public void 正常系_存在しないシートを複数削除() throws Exception {
		// Prepare
		Files.copy(Paths.get("src/test/resources/excel/input/input.xlsx"), Paths.get("output/output.xlsx"));

		// Action
		excelUtil.DeleteExcelSheet("output/output.xlsx", Arrays.asList("Sheet4", "Sheet5"));

		// Check
		String resultExcelFilePath = "output/output.xlsx";
		String referenceExcelFilePath = "src/test/resources/excel/input/input.xlsx";
		compareEXCEL(referenceExcelFilePath, resultExcelFilePath);
	}

	@Test
	public void 正常系_旧形式エクセルファイルを入力() throws Exception {
		// Prepare
		Files.copy(Paths.get("src/test/resources/excel/input/input.xls"), Paths.get("output/output.xls"));

		// Action
		excelUtil.DeleteExcelSheet("output/output.xls", Arrays.asList("Sheet1", "Sheet2"));

		// Check
		String resultExcelFilePath = "output/output.xls";
		String referenceExcelFilePath = "src/test/resources/excel/reference/output_delete1and2.xlsx";
		compareEXCEL(referenceExcelFilePath, resultExcelFilePath);
	}

	@Test
	public void 正常系_存在するシートと存在しないシートを複数指定() throws Exception {
		// Prepare
		Files.copy(Paths.get("src/test/resources/excel/input/input.xlsx"), Paths.get("output/output.xlsx"));

		// Action
		excelUtil.DeleteExcelSheet("output/output.xlsx", Arrays.asList("Sheet1", "Sheet4", "Sheet2", "Sheet5"));

		// Check
		String resultExcelFilePath = "output/output.xlsx";
		String referenceExcelFilePath = "src/test/resources/excel/reference/output_delete1and2.xlsx";
		compareEXCEL(referenceExcelFilePath, resultExcelFilePath);
	}

	@Test
	public void 異常系_入出力エクセル帳票ファイルNULL() throws Exception {
		try {
			// Action
			excelUtil.DeleteExcelSheet(null, Arrays.asList("Sheet1", "Sheet2"));
		} catch (ErrorCheckException e) {
			// Check
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00200", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメーターfilePathが未設定です。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	public void 異常系_入出力エクセル帳票ファイル空文字() throws Exception {
		// Prepare
		Files.copy(Paths.get("src/test/resources/excel/input/input.xlsx"), Paths.get("output/output.xlsx"));

		try {
			// Action
			excelUtil.DeleteExcelSheet("", Arrays.asList("Sheet1", "Sheet2"));
		} catch (ErrorCheckException e) {
			// Check
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00200", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメーターfilePathが未設定です。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	public void 異常系_削除シート名配列NULL() throws Exception {
		// Prepare
		Files.copy(Paths.get("src/test/resources/excel/input/input.xlsx"), Paths.get("output/output.xlsx"));

		try {
			// Action
			excelUtil.DeleteExcelSheet("output/output.xlsx", null);
		} catch (ErrorCheckException e) {
			// Check
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00200", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメーターsheetNameListが未設定です。", e.getErrorInfoList().get(0).getErrorMessage());

			// ファイルが変わっていないこと
			String resultExcelFilePath = "output/output.xlsx";
			String referenceExcelFilePath = "src/test/resources/excel/input/input.xlsx";
			compareEXCEL(referenceExcelFilePath, resultExcelFilePath);
		}
	}

	@Test
	public void 異常系_削除シート名配列サイズゼロ() throws Exception {
		// Prepare
		Files.copy(Paths.get("src/test/resources/excel/input/input.xlsx"), Paths.get("output/output.xlsx"));

		try {
			// Action
			excelUtil.DeleteExcelSheet("output/output.xlsx", new ArrayList<String>());
		} catch (ErrorCheckException e) {
			// Check
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00200", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメーターsheetNameListが未設定です。", e.getErrorInfoList().get(0).getErrorMessage());

			// ファイルが変わっていないこと
			String resultExcelFilePath = "output/output.xlsx";
			String referenceExcelFilePath = "src/test/resources/excel/input/input.xlsx";
			compareEXCEL(referenceExcelFilePath, resultExcelFilePath);
		}
	}

	@Test
	public void 異常系_入出力エクセル帳票ファイルが存在しない() throws Exception {
		try {
			// Action
			excelUtil.DeleteExcelSheet("dummy.xlsx", Arrays.asList("Sheet1", "Sheet2"));
		} catch (ErrorCheckException e) {
			// Check
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00100", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", (new File("dummy.xlsx")).getAbsolutePath() + "が存在しません。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	/**
	 * シンプルテンプレート用データ生成
	 * @return
	 */
	private TestExcelSimpleData createSimpleData() {
		TestExcelSimpleData data = new TestExcelSimpleData();
		data.setMessage("テスト");
		data.setNumber(11);
		return data;
	}

	/**
	 * 繰り返しテンプレート用データ生成
	 * @return
	 * @throws ParseException
	 */
	private TestExcelPersonData createPersonData() throws ParseException {
		TestExcelPersonData person = new TestExcelPersonData();
		person.setTitle("リスト");
		List<TestExcelPersonDataRow> dataList = new ArrayList<>();
		dataList.add(new TestExcelPersonDataRow("テスト１", 25, new SimpleDateFormat("yyyy/MM/dd").parse("2018/12/12"), 78.5));
		dataList.add(new TestExcelPersonDataRow("テスト２", 19, new SimpleDateFormat("yyyy/MM/dd").parse("2018/05/05"), 40.2));
		dataList.add(new TestExcelPersonDataRow("テスト３", 2, new SimpleDateFormat("yyyy/MM/dd").parse("2018/01/02"), 10.7));
		person.setDataList(dataList);
		return person;
	}

	/**
	 * 複数シートテンプレート用データ生成
	 * @return
	 * @throws ParseException
	 */
	private List<TestExcelPersonData> createMultiPersonData() throws ParseException {
		List<TestExcelPersonData> sheetList = new ArrayList<>();

		TestExcelPersonData p1 = new TestExcelPersonData();
		p1.setTitle("リスト");
		p1.setSheetName("シート１");
		List<TestExcelPersonDataRow> d1 = new ArrayList<>();
		d1.add(new TestExcelPersonDataRow("テスト１", 25, new SimpleDateFormat("yyyy/MM/dd").parse("2018/12/12"), 78.5));
		p1.setDataList(d1);
		sheetList.add(p1);

		TestExcelPersonData p2 = new TestExcelPersonData();
		p2.setTitle("リスト");
		p2.setSheetName("シート２");
		List<TestExcelPersonDataRow> d2 = new ArrayList<>();
		d2.add(new TestExcelPersonDataRow("テスト１", 25, new SimpleDateFormat("yyyy/MM/dd").parse("2018/12/12"), 78.5));
		d2.add(new TestExcelPersonDataRow("テスト２", 19, new SimpleDateFormat("yyyy/MM/dd").parse("2018/05/05"), 40.2));
		p2.setDataList(d2);
		sheetList.add(p2);

		TestExcelPersonData p3 = new TestExcelPersonData();
		p3.setTitle("リスト");
		p3.setSheetName("シート３");
		List<TestExcelPersonDataRow> d3 = new ArrayList<>();
		d3.add(new TestExcelPersonDataRow("テスト１", 25, new SimpleDateFormat("yyyy/MM/dd").parse("2018/12/12"), 78.5));
		d3.add(new TestExcelPersonDataRow("テスト２", 19, new SimpleDateFormat("yyyy/MM/dd").parse("2018/05/05"), 40.2));
		d3.add(new TestExcelPersonDataRow("テスト３", 2, new SimpleDateFormat("yyyy/MM/dd").parse("2018/01/02"), 10.7));
		p3.setDataList(d3);
		sheetList.add(p3);

		return sheetList;
	}

	private void compareEXCEL(String seikai, String output) throws Exception {
		Assert.assertEquals("エクセルファイルの内容が一致すること", getExcelContentsInfo(seikai), getExcelContentsInfo(output));
	}

	private String getExcelContentsInfo(String xlsFilePath) throws EncryptedDocumentException, InvalidFormatException, FileNotFoundException, IOException {
		StringBuilder sb = new StringBuilder();
		try (Workbook wb = WorkbookFactory.create(new FileInputStream(xlsFilePath))) {
			//全セルを表示する
			for (Sheet sheet : wb) {
				for (Row row : sheet) {
					for (Cell cell : row) {
						sb.append(getCellValue(cell));
						sb.append(",");
					}
					sb.append(System.getProperty("line.separator"));
				}
			}
		}
		return sb.toString();
	}

	private static Object getCellValue(Cell cell) {
		CellType cellType = cell.getCellTypeEnum();
		if (CellType.STRING == cellType) {
			return cell.getRichStringCellValue().getString();
		} else if (CellType.NUMERIC == cellType) {
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue();
			} else {
				return cell.getNumericCellValue();
			}
		} else if (CellType.BOOLEAN == cellType) {
			return cell.getBooleanCellValue();
		} else if (CellType.FORMULA == cellType) {
			return cell.getCellFormula();
		} else {
			return null;
		}
	}

	private InputEntity getInputEntity() throws IOException, ParseException {
		InputEntity entity = new InputEntity();

		// 申込書（単数）の設定
		Application app = new Application();
		app.setTitle("COTOS商材");
		app.setText("テキスト");
		app.setNumber(4);
		List<ItemInfo> itemList = new ArrayList<>();
		itemList.add(new ItemInfo("123456", "電力", new SimpleDateFormat("yyyy/MM/dd").parse("2018/04/01"), 100, 1));
		itemList.add(new ItemInfo("234567", "月額DB", new SimpleDateFormat("yyyy/MM/dd").parse("2018/12/01"), 1850, 2));
		itemList.add(new ItemInfo("345678", "IFS", new SimpleDateFormat("yyyy/MM/dd").parse("2019/02/01"), 1290, 3));
		itemList.add(new ItemInfo("456789", "WWF", new SimpleDateFormat("yyyy/MM/dd").parse("2019/04/01"), 1980, 5));
		app.setItemList(itemList);
		entity.setApp(app);

		// 申込書（複数）の設定
		List<Application> appList = new ArrayList<>();
		{
			Application app1 = new Application();
			app1.setTitle("COTOS商材");
			app1.setText("テキスト");
			app1.setNumber(4);
			List<ItemInfo> itemList1 = new ArrayList<>();
			itemList1.add(new ItemInfo("123456", "電力", new SimpleDateFormat("yyyy/MM/dd").parse("2018/04/01"), 100, 1));
			itemList1.add(new ItemInfo("234567", "月額DB", new SimpleDateFormat("yyyy/MM/dd").parse("2018/12/01"), 1850, 2));
			itemList1.add(new ItemInfo("345678", "IFS", new SimpleDateFormat("yyyy/MM/dd").parse("2019/02/01"), 1290, 3));
			itemList1.add(new ItemInfo("456789", "WWF", new SimpleDateFormat("yyyy/MM/dd").parse("2019/04/01"), 1980, 5));
			app1.setItemList(itemList1);

			Application app2 = new Application();
			app2.setTitle("COTOS商材");
			app2.setText("テキスト");
			app2.setNumber(4);
			List<ItemInfo> itemList2 = new ArrayList<>();
			itemList2.add(new ItemInfo("123456", "電力", new SimpleDateFormat("yyyy/MM/dd").parse("2018/04/01"), 100, 1));
			itemList2.add(new ItemInfo("234567", "月額DB", new SimpleDateFormat("yyyy/MM/dd").parse("2018/12/01"), 1850, 2));
			itemList2.add(new ItemInfo("345678", "IFS", new SimpleDateFormat("yyyy/MM/dd").parse("2019/02/01"), 1290, 3));
			itemList2.add(new ItemInfo("456789", "WWF", new SimpleDateFormat("yyyy/MM/dd").parse("2019/04/01"), 1980, 5));
			app2.setItemList(itemList2);

			appList.add(app1);
			appList.add(app2);
		}
		entity.setAppList(appList);

		// 申込書（複数）のシート名設定
		entity.setAppTitleList(entity.getAppList().stream().map(s -> s.getTitle()).collect(Collectors.toList()));

		convertList2YamlFile("single.yml", entity);
		InputEntity ent = convertYaml2List("single.yml");

		return entity;
	}

	private InputEntity convertYaml2List(String filePath) throws JsonParseException, JsonMappingException, IOException {
		return new ObjectMapper(new YAMLFactory()).readValue(Files.newInputStream(Paths.get(filePath)), InputEntity.class);
	}

	private void convertList2YamlFile(String filePath, InputEntity entity) throws IOException {
		String str = new ObjectMapper(new YAMLFactory()).writeValueAsString(entity);
		Files.write(Paths.get(filePath), str.getBytes(StandardCharsets.UTF_8));
	}
}
