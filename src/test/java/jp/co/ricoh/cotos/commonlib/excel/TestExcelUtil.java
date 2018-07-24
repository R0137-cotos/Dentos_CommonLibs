package jp.co.ricoh.cotos.commonlib.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;

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

import jp.co.ricoh.cotos.commonlib.excel.seishiki.InputEntity;
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

	// ============================================
	// OutputExcelReports
	// ============================================
	@Test
	public void 正常系_単一シート出力テンプレート() throws Exception {
		// XLSX形式
		excelUtil.OutputExcelReports("src/test/resources/excel/template/single.xlsx", convertYaml2Entity("src/test/resources/excel/entityData/both.yml"), "output/output.xlsx");
		String resultExcelFilePath = "output/output.xlsx";
		String referenceExcelFilePath = "src/test/resources/excel/reference/output_single.xlsx";
		compareEXCEL(referenceExcelFilePath, resultExcelFilePath);

		// XLS形式
		excelUtil.OutputExcelReports("src/test/resources/excel/template/single.xlsx", convertYaml2Entity("src/test/resources/excel/entityData/both.yml"), "output/output.xls");
		String resultExcelFilePathForXls = "output/output.xls";
		compareEXCEL(referenceExcelFilePath, resultExcelFilePathForXls);
	}

	@Test
	public void 正常系_複数シート出力テンプレート() throws Exception {
		// XLSX形式
		excelUtil.OutputExcelReports("src/test/resources/excel/template/multi.xlsx", convertYaml2Entity("src/test/resources/excel/entityData/both.yml"), "output/output.xlsx");
		String resultExcelFilePath = "output/output.xlsx";
		String referenceExcelFilePath = "src/test/resources/excel/reference/output_multi.xlsx";
		compareEXCEL(referenceExcelFilePath, resultExcelFilePath);

		// XLS形式
		excelUtil.OutputExcelReports("src/test/resources/excel/template/multi.xlsx", convertYaml2Entity("src/test/resources/excel/entityData/both.yml"), "output/output.xls");
		String resultExcelFilePathForXls = "output/output.xls";
		compareEXCEL(referenceExcelFilePath, resultExcelFilePathForXls);
	}

	@Test
	public void 正常系_単一および複数シート出力テンプレート() throws Exception {
		// XLSX形式
		excelUtil.OutputExcelReports("src/test/resources/excel/template/both.xlsx", convertYaml2Entity("src/test/resources/excel/entityData/both.yml"), "output/output.xlsx");
		String resultExcelFilePath = "output/output.xlsx";
		String referenceExcelFilePath = "src/test/resources/excel/reference/output_both.xlsx";
		compareEXCEL(referenceExcelFilePath, resultExcelFilePath);

		// XLS形式
		excelUtil.OutputExcelReports("src/test/resources/excel/template/both.xlsx", convertYaml2Entity("src/test/resources/excel/entityData/both.yml"), "output/output.xls");
		String resultExcelFilePathForXls = "output/output.xls";
		compareEXCEL(referenceExcelFilePath, resultExcelFilePathForXls);
	}

	@Test
	public void 正常系_単数シート出力テンプレート２シート() throws Exception {
		// XLSX形式
		excelUtil.OutputExcelReports("src/test/resources/excel/template/single2sheet.xlsx", convertYaml2Entity("src/test/resources/excel/entityData/both.yml"), "output/output.xlsx");
		String resultExcelFilePath = "output/output.xlsx";
		String referenceExcelFilePath = "src/test/resources/excel/reference/output_single2sheet.xlsx";
		compareEXCEL(referenceExcelFilePath, resultExcelFilePath);

		// XLS形式
		excelUtil.OutputExcelReports("src/test/resources/excel/template/single2sheet.xlsx", convertYaml2Entity("src/test/resources/excel/entityData/both.yml"), "output/output.xls");
		String resultExcelFilePathForXls = "output/output.xls";
		compareEXCEL(referenceExcelFilePath, resultExcelFilePathForXls);
	}

	@Test
	public void 異常系_テンプレートファイルパスNULL() throws ParseException, JsonParseException, JsonMappingException, IOException {
		try {
			// Action
			excelUtil.OutputExcelReports(null, convertYaml2Entity("src/test/resources/excel/entityData/both.yml"), "output/output.xlsx");
		} catch (ErrorCheckException e) {
			// Check
			Assert.assertTrue("エクセル帳票が出力されないこと", Files.notExists(Paths.get("output/output.xls")));
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00200", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメーターtemplateFilePathが未設定です。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	public void 異常系_テンプレートファイルパスが空文字() throws ParseException, JsonParseException, JsonMappingException, IOException {
		try {
			// Action
			excelUtil.OutputExcelReports("", convertYaml2Entity("src/test/resources/excel/entityData/both.yml"), "output/output.xlsx");
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
			excelUtil.OutputExcelReports("src/test/resources/excel/template/single.xlsx", null, "output/output.xlsx");
		} catch (ErrorCheckException e) {
			// Check
			Assert.assertTrue("エクセル帳票が出力されないこと", Files.notExists(Paths.get("output/output.xls")));
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00200", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメーターentityが未設定です。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	public void 異常系_出力エクセル帳票ファイルパスNULL() throws ParseException, JsonParseException, JsonMappingException, IOException {
		try {
			// Action
			excelUtil.OutputExcelReports("src/test/resources/excel/template/template.xls", convertYaml2Entity("src/test/resources/excel/entityData/both.yml"), null);
		} catch (ErrorCheckException e) {
			// Check
			Assert.assertTrue("エクセル帳票が出力されないこと", Files.notExists(Paths.get("output/output.xls")));
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00200", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメーターoutputFilePathが未設定です。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	public void 異常系_出力エクセル帳票ファイルパスが空文字() throws ParseException, JsonParseException, JsonMappingException, IOException {
		try {
			// Action
			excelUtil.OutputExcelReports("src/test/resources/excel/template/single.xlsx", convertYaml2Entity("src/test/resources/excel/entityData/both.yml"), "");
		} catch (ErrorCheckException e) {
			// Check
			Assert.assertTrue("エクセル帳票が出力されないこと", Files.notExists(Paths.get("output/output.xls")));
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00200", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメーターoutputFilePathが未設定です。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	public void 異常系_テンプレートファイルが存在しない() throws ParseException, JsonParseException, JsonMappingException, IOException {
		try {
			// Action
			excelUtil.OutputExcelReports("dummy.xls", convertYaml2Entity("src/test/resources/excel/entityData/both.yml"), "output/output.xlsx");
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
		Files.createFile(Paths.get("output/output.xls"));

		try {
			// Action
			excelUtil.OutputExcelReports("src/test/resources/excel/template/single.xlsx", convertYaml2Entity("src/test/resources/excel/entityData/both.yml"), "output/output.xlsx");
		} catch (ErrorCheckException e) {
			// Check
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00114", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", (new File("output/output.xls")).getAbsolutePath() + "は既に存在します。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	// ============================================
	// DeleteExcelSheet
	// ============================================
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

	// ============================================
	// TestUtil
	// ============================================
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

	private InputEntity convertYaml2Entity(String filePath) throws JsonParseException, JsonMappingException, IOException {
		return new ObjectMapper(new YAMLFactory()).readValue(Files.newInputStream(Paths.get(filePath)), InputEntity.class);
	}

	private void convertEntity2YamlFile(String filePath, InputEntity entity) throws IOException {
		String str = new ObjectMapper(new YAMLFactory()).writeValueAsString(entity);
		Files.write(Paths.get(filePath), str.getBytes(StandardCharsets.UTF_8));
	}
}
