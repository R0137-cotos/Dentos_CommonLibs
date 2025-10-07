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
import java.util.List;
import java.util.stream.Stream;

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

import jp.co.ricoh.cotos.commonlib.excel.entity.InputEntity;
import jp.co.ricoh.cotos.commonlib.excel.entity.ritos.Customer;
import jp.co.ricoh.cotos.commonlib.excel.entity.ritos.Dealer;
import jp.co.ricoh.cotos.commonlib.excel.entity.ritos.Detail;
import jp.co.ricoh.cotos.commonlib.excel.entity.ritos.InitialDetail;
import jp.co.ricoh.cotos.commonlib.excel.entity.ritos.Shinsei;
import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.logic.excel.ExcelUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestExcelUtil {

	@Autowired
	ExcelUtil excelUtil;

	@Before
	public void init() throws IOException {
		Files.createDirectories(Paths.get("outputExcel/"));
		Stream.of(Paths.get("outputExcel/").toFile().listFiles()).forEach(s -> {
			s.setWritable(true);
			try {
				Files.delete(s.toPath());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	// ============================================
	// OutputExcelReports
	// ============================================
	@Test
	public void 正常系_RITOS帳票出力テスト() throws Exception {
		Shinsei shinsei = new Shinsei();
		shinsei.setItemName("Office365");
		shinsei.setDocNumber("20101126130000");
		shinsei.setKjbId("123456");
		shinsei.setPaymentFlagName("ランニング費用");

		List<InitialDetail> initialDetailList = new ArrayList<>();
		initialDetailList.add(new InitialDetail("CD001",1));
		initialDetailList.add(new InitialDetail("CD002", 100));
		initialDetailList.add(new InitialDetail("CD003", 100));
		shinsei.setInitialDetailList(initialDetailList);

		List<Detail> detailList = new ArrayList<>();
		detailList.add(new Detail("CD004", 100));
		detailList.add(new Detail("CD005", 1));
		detailList.add(new Detail("CD006", 1));
		shinsei.setDetailList(detailList);

		Dealer dealer = new Dealer();
		dealer.setPostCode("4562215");
		dealer.setAddress("東京都中央区晴海１－１");
		dealer.setAddressKana("トウキョウトチュウオウクハルミ");
		dealer.setName("リコー太郎");
		dealer.setNameKana("リコータロウ");
		dealer.setApprover("リコー一郎");
		dealer.setApproverKana("リコーイチロウ");
		shinsei.setDealer(dealer);

		Customer customer = new Customer();
		customer.setPostCode("1234567");
		customer.setAddress("神奈川県横浜市都筑区１－１");
		customer.setAddressKana("カナガワケンヨコハマシツヅキク");
		customer.setName("リコー次郎");
		customer.setNameKana("リコージロウ");
		shinsei.setCustomer(customer);

		// XLSX形式
		excelUtil.outputExcelReports("src/test/resources/excel/template/ritos.xlsx", shinsei, "outputExcel/ritos_output.xlsx");
		String resultExcelFilePathForXls = "outputExcel/ritos_output.xlsx";
		String referenceExcelFilePath = "src/test/resources/excel/reference/ritos_output_ref.xlsx";
		compareEXCEL(referenceExcelFilePath, resultExcelFilePathForXls);
	}

	@Test
	public void 正常系_単一シート出力テンプレート() throws Exception {
		// XLSX形式
		excelUtil.outputExcelReports("src/test/resources/excel/template/single.xlsx", convertYaml2Entity("src/test/resources/excel/entityData/both.yml"), "outputExcel/output.xlsx");
		String resultExcelFilePath = "outputExcel/output.xlsx";
		String referenceExcelFilePath = "src/test/resources/excel/reference/output_single.xlsx";
		compareEXCEL(referenceExcelFilePath, resultExcelFilePath);

		// XLS形式
		excelUtil.outputExcelReports("src/test/resources/excel/template/single.xlsx", convertYaml2Entity("src/test/resources/excel/entityData/both.yml"), "outputExcel/output.xls");
		String resultExcelFilePathForXls = "outputExcel/output.xls";
		compareEXCEL(referenceExcelFilePath, resultExcelFilePathForXls);
	}

	@Test
	public void 正常系_複数シート出力テンプレート() throws Exception {
		// XLSX形式
		excelUtil.outputExcelReports("src/test/resources/excel/template/multi.xlsx", convertYaml2Entity("src/test/resources/excel/entityData/both.yml"), "outputExcel/output.xlsx");
		String resultExcelFilePath = "outputExcel/output.xlsx";
		String referenceExcelFilePath = "src/test/resources/excel/reference/output_multi.xlsx";
		compareEXCEL(referenceExcelFilePath, resultExcelFilePath);

		// XLS形式
		excelUtil.outputExcelReports("src/test/resources/excel/template/multi.xlsx", convertYaml2Entity("src/test/resources/excel/entityData/both.yml"), "outputExcel/output.xls");
		String resultExcelFilePathForXls = "outputExcel/output.xls";
		compareEXCEL(referenceExcelFilePath, resultExcelFilePathForXls);
	}

	@Test
	public void 正常系_単一および複数シート出力テンプレート() throws Exception {
		// XLSX形式
		excelUtil.outputExcelReports("src/test/resources/excel/template/both.xlsx", convertYaml2Entity("src/test/resources/excel/entityData/both.yml"), "outputExcel/output.xlsx");
		String resultExcelFilePath = "outputExcel/output.xlsx";
		String referenceExcelFilePath = "src/test/resources/excel/reference/output_both.xlsx";
		compareEXCEL(referenceExcelFilePath, resultExcelFilePath);

		// XLS形式
		excelUtil.outputExcelReports("src/test/resources/excel/template/both.xlsx", convertYaml2Entity("src/test/resources/excel/entityData/both.yml"), "outputExcel/output.xls");
		String resultExcelFilePathForXls = "outputExcel/output.xls";
		compareEXCEL(referenceExcelFilePath, resultExcelFilePathForXls);
	}

	@Test
	public void 正常系_単数シート出力テンプレート２シート() throws Exception {
		// XLSX形式
		excelUtil.outputExcelReports("src/test/resources/excel/template/single2sheet.xlsx", convertYaml2Entity("src/test/resources/excel/entityData/both.yml"), "outputExcel/output.xlsx");
		String resultExcelFilePath = "outputExcel/output.xlsx";
		String referenceExcelFilePath = "src/test/resources/excel/reference/output_single2sheet.xlsx";
		compareEXCEL(referenceExcelFilePath, resultExcelFilePath);

		// XLS形式
		excelUtil.outputExcelReports("src/test/resources/excel/template/single2sheet.xlsx", convertYaml2Entity("src/test/resources/excel/entityData/both.yml"), "outputExcel/output.xls");
		String resultExcelFilePathForXls = "outputExcel/output.xls";
		compareEXCEL(referenceExcelFilePath, resultExcelFilePathForXls);
	}

	@Test
	public void 異常系_テンプレートファイルパスNULL() throws ParseException, JsonParseException, JsonMappingException, IOException {
		try {
			// Action
			excelUtil.outputExcelReports(null, convertYaml2Entity("src/test/resources/excel/entityData/both.yml"), "outputExcel/output.xlsx");
		} catch (ErrorCheckException e) {
			// Check
			Assert.assertTrue("エクセル帳票が出力されないこと", Files.notExists(Paths.get("outputExcel/output.xls")));
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00001", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータ「入力テンプレートエクセルファイルパス」が設定されていません。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	public void 異常系_テンプレートファイルパスが空文字() throws ParseException, JsonParseException, JsonMappingException, IOException {
		try {
			// Action
			excelUtil.outputExcelReports("", convertYaml2Entity("src/test/resources/excel/entityData/both.yml"), "outputExcel/output.xlsx");
		} catch (ErrorCheckException e) {
			// Check
			Assert.assertTrue("エクセル帳票が出力されないこと", Files.notExists(Paths.get("outputExcel/output.xls")));
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00001", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータ「入力テンプレートエクセルファイルパス」が設定されていません。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	public void 異常系_マッピング用エンティティNULL() throws ParseException {
		try {
			// Action
			excelUtil.outputExcelReports("src/test/resources/excel/template/single.xlsx", null, "outputExcel/output.xlsx");
		} catch (ErrorCheckException e) {
			// Check
			Assert.assertTrue("エクセル帳票が出力されないこと", Files.notExists(Paths.get("outputExcel/output.xls")));
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00001", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータ「マッピング用エンティティクラス」が設定されていません。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	public void 異常系_出力エクセル帳票ファイルパスNULL() throws ParseException, JsonParseException, JsonMappingException, IOException {
		try {
			// Action
			excelUtil.outputExcelReports("src/test/resources/excel/template/template.xls", convertYaml2Entity("src/test/resources/excel/entityData/both.yml"), null);
		} catch (ErrorCheckException e) {
			// Check
			Assert.assertTrue("エクセル帳票が出力されないこと", Files.notExists(Paths.get("outputExcel/output.xls")));
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00001", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータ「出力エクセル帳票ファイルパス」が設定されていません。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	public void 異常系_出力エクセル帳票ファイルパスが空文字() throws ParseException, JsonParseException, JsonMappingException, IOException {
		try {
			// Action
			excelUtil.outputExcelReports("src/test/resources/excel/template/single.xlsx", convertYaml2Entity("src/test/resources/excel/entityData/both.yml"), "");
		} catch (ErrorCheckException e) {
			// Check
			Assert.assertTrue("エクセル帳票が出力されないこと", Files.notExists(Paths.get("outputExcel/output.xls")));
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00001", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータ「出力エクセル帳票ファイルパス」が設定されていません。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	public void 異常系_テンプレートファイルが存在しない() throws ParseException, JsonParseException, JsonMappingException, IOException {
		try {
			// Action
			excelUtil.outputExcelReports("dummy.xls", convertYaml2Entity("src/test/resources/excel/entityData/both.yml"), "outputExcel/output.xlsx");
		} catch (ErrorCheckException e) {
			// Check
			Assert.assertTrue("エクセル帳票が出力されないこと", Files.notExists(Paths.get("outputExcel/output.xls")));
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00100", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "指定されたファイルが存在しません。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	public void 異常系_エクセル帳票ファイルが既に存在する() throws ParseException, IOException {
		// Prepare
		Files.createFile(Paths.get("outputExcel/output.xls"));

		try {
			// Action
			excelUtil.outputExcelReports("src/test/resources/excel/template/single.xlsx", convertYaml2Entity("src/test/resources/excel/entityData/both.yml"), "outputExcel/output.xlsx");
		} catch (ErrorCheckException e) {
			// Check
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00114", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", (new File("outputExcel/output.xls")).getAbsolutePath() + "は既に存在します。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	// ============================================
	// DeleteExcelSheet
	// ============================================
	@Test
	public void 正常系_存在するシートを複数削除() throws Exception {
		// Prepare
		Files.copy(Paths.get("src/test/resources/excel/input/input.xlsx"), Paths.get("outputExcel/output.xlsx"));

		// Action
		excelUtil.deleteExcelSheet("outputExcel/output.xlsx", Arrays.asList("Sheet1", "Sheet2"));

		// Check
		String resultExcelFilePath = "outputExcel/output.xlsx";
		String referenceExcelFilePath = "src/test/resources/excel/reference/output_delete1and2.xlsx";
		compareEXCEL(referenceExcelFilePath, resultExcelFilePath);
	}

	@Test
	public void 正常系_存在しないシートを複数削除() throws Exception {
		// Prepare
		Files.copy(Paths.get("src/test/resources/excel/input/input.xlsx"), Paths.get("outputExcel/output.xlsx"));

		// Action
		excelUtil.deleteExcelSheet("outputExcel/output.xlsx", Arrays.asList("Sheet4", "Sheet5"));

		// Check
		String resultExcelFilePath = "outputExcel/output.xlsx";
		String referenceExcelFilePath = "src/test/resources/excel/input/input.xlsx";
		compareEXCEL(referenceExcelFilePath, resultExcelFilePath);
	}

	@Test
	public void 正常系_旧形式エクセルファイルを入力() throws Exception {
		// Prepare
		Files.copy(Paths.get("src/test/resources/excel/input/input.xls"), Paths.get("outputExcel/output.xls"));

		// Action
		excelUtil.deleteExcelSheet("outputExcel/output.xls", Arrays.asList("Sheet1", "Sheet2"));

		// Check
		String resultExcelFilePath = "outputExcel/output.xls";
		String referenceExcelFilePath = "src/test/resources/excel/reference/output_delete1and2.xlsx";
		compareEXCEL(referenceExcelFilePath, resultExcelFilePath);
	}

	@Test
	public void 正常系_存在するシートと存在しないシートを複数指定() throws Exception {
		// Prepare
		Files.copy(Paths.get("src/test/resources/excel/input/input.xlsx"), Paths.get("outputExcel/output.xlsx"));

		// Action
		excelUtil.deleteExcelSheet("outputExcel/output.xlsx", Arrays.asList("Sheet1", "Sheet4", "Sheet2", "Sheet5"));

		// Check
		String resultExcelFilePath = "outputExcel/output.xlsx";
		String referenceExcelFilePath = "src/test/resources/excel/reference/output_delete1and2.xlsx";
		compareEXCEL(referenceExcelFilePath, resultExcelFilePath);
	}

	@Test
	public void 異常系_入出力エクセル帳票ファイルNULL() throws Exception {
		try {
			// Action
			excelUtil.deleteExcelSheet(null, Arrays.asList("Sheet1", "Sheet2"));
		} catch (ErrorCheckException e) {
			// Check
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00001", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータ「入出力エクセル帳票ファイルパス」が設定されていません。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	public void 異常系_入出力エクセル帳票ファイル空文字() throws Exception {
		// Prepare
		Files.copy(Paths.get("src/test/resources/excel/input/input.xlsx"), Paths.get("outputExcel/output.xlsx"));

		try {
			// Action
			excelUtil.deleteExcelSheet("", Arrays.asList("Sheet1", "Sheet2"));
		} catch (ErrorCheckException e) {
			// Check
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00001", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータ「入出力エクセル帳票ファイルパス」が設定されていません。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	public void 異常系_削除シート名配列NULL() throws Exception {
		// Prepare
		Files.copy(Paths.get("src/test/resources/excel/input/input.xlsx"), Paths.get("outputExcel/output.xlsx"));

		try {
			// Action
			excelUtil.deleteExcelSheet("outputExcel/output.xlsx", null);
		} catch (ErrorCheckException e) {
			// Check
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00001", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータ「削除シート名配列」が設定されていません。", e.getErrorInfoList().get(0).getErrorMessage());

			// ファイルが変わっていないこと
			String resultExcelFilePath = "outputExcel/output.xlsx";
			String referenceExcelFilePath = "src/test/resources/excel/input/input.xlsx";
			compareEXCEL(referenceExcelFilePath, resultExcelFilePath);
		}
	}

	@Test
	public void 異常系_削除シート名配列サイズゼロ() throws Exception {
		// Prepare
		Files.copy(Paths.get("src/test/resources/excel/input/input.xlsx"), Paths.get("outputExcel/output.xlsx"));

		try {
			// Action
			excelUtil.deleteExcelSheet("outputExcel/output.xlsx", new ArrayList<String>());
		} catch (ErrorCheckException e) {
			// Check
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00001", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "パラメータ「削除シート名配列」が設定されていません。", e.getErrorInfoList().get(0).getErrorMessage());

			// ファイルが変わっていないこと
			String resultExcelFilePath = "outputExcel/output.xlsx";
			String referenceExcelFilePath = "src/test/resources/excel/input/input.xlsx";
			compareEXCEL(referenceExcelFilePath, resultExcelFilePath);
		}
	}

	@Test
	public void 異常系_入出力エクセル帳票ファイルが存在しない() throws Exception {
		try {
			// Action
			excelUtil.deleteExcelSheet("dummy.xlsx", Arrays.asList("Sheet1", "Sheet2"));
		} catch (ErrorCheckException e) {
			// Check
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00100", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", "指定されたファイルが存在しません。", e.getErrorInfoList().get(0).getErrorMessage());
		}
	}

	@Test
	public void 異常系_入出力エクセル帳票ファイルが読み取り専用() throws Exception {
		// Prepare
		Files.copy(Paths.get("src/test/resources/excel/input/input_readOnly.xlsx"), Paths.get("outputExcel/output.xlsx"));

		try {
			// Action
			excelUtil.deleteExcelSheet("outputExcel/output.xlsx", Arrays.asList("Sheet1", "Sheet2"));
		} catch (ErrorCheckException e) {
			// Check
			Assert.assertEquals("エラーIDが正しく設定されること", "ROT00107", e.getErrorInfoList().get(0).getErrorId());
			Assert.assertEquals("エラーメッセージが正しく設定されること", (new File("outputExcel/output.xlsx")).getAbsolutePath() + "は読み取り専用ファイルです。", e.getErrorInfoList().get(0).getErrorMessage());
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
		CellType cellType = cell.getCellType();
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

	@SuppressWarnings("unused")
	private void convertEntity2YamlFile(String filePath, InputEntity entity) throws IOException {
		String str = new ObjectMapper(new YAMLFactory()).writeValueAsString(entity);
		Files.write(Paths.get(filePath), str.getBytes(StandardCharsets.UTF_8));
	}
}