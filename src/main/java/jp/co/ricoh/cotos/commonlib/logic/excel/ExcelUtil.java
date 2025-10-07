package jp.co.ricoh.cotos.commonlib.logic.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.jxls.builder.JxlsOutput;
import org.jxls.builder.KeepTemplateSheet;
import org.jxls.transform.poi.JxlsPoiTemplateFillerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.base.Strings;

import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.exception.ErrorFatalException;
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
	@Deprecated
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
		Map<String, Object> contextMap = new HashMap<>();
		contextMap.put(entity.getClass().getSimpleName().substring(0, 1).toLowerCase() + entity.getClass().getSimpleName().substring(1), entity);

		// エクセル出力
		try (InputStream in = new FileInputStream(inputTemplateFile); OutputStream out = new FileOutputStream(outputFile)) {
			Files.createDirectories(outputFile.toPath().getParent());

			JxlsOutput output = new JxlsOutput() {
				@Override
				public OutputStream getOutputStream() throws IOException {
					return out;
				}
			};
			
			JxlsPoiTemplateFillerBuilder.newInstance().withKeepTemplateSheet(KeepTemplateSheet.KEEP).withTemplate(in).buildAndFill(contextMap, output);
		} catch (FileNotFoundException e) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(errorInfoList, "FileNotFoundError", new String[] { inputTemplateFile.getAbsolutePath() }));
		} catch (IOException e) {
			throw new ErrorFatalException(checkUtil.addErrorInfo(errorInfoList, "FileOutputFailed", new String[] { outputFile.getAbsolutePath() }));
		}
	}

	/**
	 * エクセル帳票ファイルから指定したシートを削除する
	 * @param filePath 入出力エクセル帳票ファイル
	 * @param sheetNameList 削除シート名配列
	 */
	@Deprecated
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
			try (OutputStream out = new FileOutputStream(tempPath.toFile()); Workbook wb = WorkbookFactory.create(in)) {
				sheetNameList.stream().map(s -> wb.getSheetIndex(s)).filter(idx -> idx >= 0).forEach(idx -> wb.removeSheetAt(idx));
				wb.write(out);
			}

			// シート削除後ファイルのコピー
			try {
				Files.copy(tempPath, inputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				throw new ErrorFatalException(checkUtil.addErrorInfo(errorInfoList, "FileCopyFailed", new String[] { tempPath.toFile().getAbsolutePath() }));
			}
		} catch (FileNotFoundException e) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(errorInfoList, "FileNotFoundError", new String[] { inputFile.getAbsolutePath() }));
		} catch (IOException | EncryptedDocumentException e) {
			throw new ErrorFatalException(checkUtil.addErrorInfo(errorInfoList, "FileOutputFailed", new String[] { inputFile.getAbsolutePath() }));
		} finally {
			// テンポラリファイル削除
			try {
				Files.deleteIfExists(tempPath);
			} catch (IOException e) {
				throw new ErrorFatalException(checkUtil.addErrorInfo(errorInfoList, "FileDeleteFailed", new String[] { tempPath.toFile().getAbsolutePath() }));
			}
		}
	}
}