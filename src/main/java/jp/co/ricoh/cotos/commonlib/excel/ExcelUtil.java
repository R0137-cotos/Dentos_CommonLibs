package jp.co.ricoh.cotos.commonlib.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;

import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.exception.ErrorInfo;
import jp.co.ricoh.cotos.commonlib.logic.check.CheckUtil;

@Component
public class ExcelUtil {

	@Autowired
	CheckUtil checkUtil;

	/**
	 * エクセル帳票ファイルを生成する
	 * @param templateFilePath 入力テンプレートエクセルファイルパス
	 * @param entity マッピング用エンティティクラス
	 * @param outputFilePath 出力エクセル帳票ファイルパス
	 */
	public <T> void outputExcelReports(String templateFilePath, T entity, String outputFilePath) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 引数チェック
		if (entity == null) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(errorInfoList, "ParameterEmptyError", new String[] { "マッピング用エンティティクラス" }));
		}
		if (Strings.isNullOrEmpty(templateFilePath)) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(errorInfoList, "ParameterEmptyError", new String[] { "入力テンプレートエクセルファイルパス" }));
		}
		if (Strings.isNullOrEmpty(outputFilePath)) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(errorInfoList, "ParameterEmptyError", new String[] { "出力エクセル帳票ファイルパス" }));
		}

		// ファイルチェック
		File inputTemplateFile = new File(templateFilePath);
		File outputFile = new File(outputFilePath);
		if (!inputTemplateFile.exists()) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(errorInfoList, "FileNotFoundError", new String[] { inputTemplateFile.getAbsolutePath() }));
		}
		if (outputFile.exists()) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(errorInfoList, "FileFoundError", new String[] { outputFile.getAbsolutePath() }));
		}

		// エクセルコンテキストへマッピング情報を設定
		Context context = new Context();
		for (Field field : entity.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			try {
				context.putVar(field.getName(), field.get(entity));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new ErrorCheckException(checkUtil.addErrorInfo(errorInfoList, "FileMappingError", new String[] { "マッピング用エンティティクラス" }));
			}
		}

		// エクセル出力
		try (InputStream in = new FileInputStream(inputTemplateFile)) {
			Files.createDirectories(outputFile.toPath().getParent());
			try (OutputStream out = new FileOutputStream(outputFile)) {
				JxlsHelper.getInstance().setUseFastFormulaProcessor(false).setDeleteTemplateSheet(true).processTemplate(in, out, context);
			}
		} catch (FileNotFoundException e) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(errorInfoList, "FileNotFoundError", new String[] { inputTemplateFile.getAbsolutePath() }));
		} catch (IOException e) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(errorInfoList, "FileOutputError", new String[] { outputFile.getAbsolutePath() }));
		}
	}

	/**
	 * エクセル帳票ファイルから指定したシートを削除する
	 * @param filePath 入出力エクセル帳票ファイル
	 * @param sheetNameList 削除シート名配列
	 */
	public void deleteExcelSheet(String filePath, List<String> sheetNameList) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		//引数チェック
		if (Strings.isNullOrEmpty(filePath)) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(errorInfoList, "ParameterEmptyError", new String[] { "入出力エクセル帳票ファイルパス" }));
		}
		if (sheetNameList == null || sheetNameList.size() == 0) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(errorInfoList, "ParameterEmptyError", new String[] { "削除シート名配列" }));
		}

		// ファイルチェック
		File inputFile = new File(filePath);
		if (!inputFile.exists()) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(errorInfoList, "FileNotFoundError", new String[] { inputFile.getAbsolutePath() }));
		}
		if (!inputFile.canWrite()) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(errorInfoList, "FileReadOnlyError", new String[] { inputFile.getAbsolutePath() }));
		}

		Path tempPath = Paths.get(inputFile.getParent(), UUID.randomUUID().toString().replaceAll("-", "") + ".bak");

		try (InputStream in = new FileInputStream(inputFile)) {
			// シート削除エクセルファイルをテンポラリとして作成
			try (OutputStream out = new FileOutputStream(tempPath.toFile())) {
				Workbook wb = WorkbookFactory.create(in);
				sheetNameList.stream().map(s -> wb.getSheetIndex(s)).filter(idx -> idx >= 0).forEach(idx -> wb.removeSheetAt(idx));
				wb.write(out);
			}

			// シート削除後ファイルのコピー
			try {
				Files.copy(tempPath, inputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				throw new ErrorCheckException(checkUtil.addErrorInfo(errorInfoList, "FileCopyError", new String[] { tempPath.toFile().getAbsolutePath() }));
			}
		} catch (FileNotFoundException e) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(errorInfoList, "FileNotFoundError", new String[] { inputFile.getAbsolutePath() }));
		} catch (IOException | EncryptedDocumentException | InvalidFormatException e) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(errorInfoList, "FileOutputError", new String[] { inputFile.getAbsolutePath() }));
		} finally {
			// テンポラリファイル削除
			try {
				Files.deleteIfExists(tempPath);
			} catch (IOException e) {
				throw new ErrorCheckException(checkUtil.addErrorInfo(errorInfoList, "FileDeleteError", new String[] { tempPath.toFile().getAbsolutePath() }));
			}
		}
	}
}
