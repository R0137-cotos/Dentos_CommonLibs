package jp.co.ricoh.cotos.commonlib.csv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Spliterators;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.RuntimeJsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.dataformat.csv.CsvGenerator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.google.common.base.Strings;

import jp.co.ricoh.cotos.commonlib.entity.CsvParam;
import jp.co.ricoh.cotos.commonlib.exception.ErrorCheckException;
import jp.co.ricoh.cotos.commonlib.exception.ErrorInfo;
import jp.co.ricoh.cotos.commonlib.logic.check.CheckUtil;
import lombok.AllArgsConstructor;

/**
 * CSV Reader/Writer ユーティリティー
 */
@Component
public class CSVUtil {

	CsvMapper mapper = new CsvMapper();

	@Autowired
	CheckUtil checkUtil;

	/**
	 * CSVファイルを読み込んでオブジェクトに展開します。
	 *
	 * @param filePath 読み込み元CSVファイルパス
	 * @param resultClass 展開先エンティティクラス型
	 * @param CSV読み込みパラメーター
	 * @return 展開エンティティ配列
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws JsonProcessingException
	 */
	public <T> List<T> readCsvFile(String filePath, Class<T> entityClass, CsvParam param) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 引数チェック
		if (Strings.isNullOrEmpty(filePath)) {
			throw new ErrorCheckException(checkUtil.addErrorInfoColumnCheck(new ArrayList<ErrorInfo>(), "filePath", "NotEmptyError"));
		}
		if (entityClass == null) {
			throw new ErrorCheckException(checkUtil.addErrorInfoColumnCheck(new ArrayList<ErrorInfo>(), "entityClass", "NotEmptyError"));
		}
		CsvParam prm = Optional.ofNullable(param).orElse(CsvParam.builder().build());

		// 各種パラメーター設定
		CsvSchema schema = mapper.typedSchemaFor(entityClass) //
				.withUseHeader(prm.isHeader()) //
				.withColumnSeparator(prm.getSeparator()) //
				.withLineSeparator(prm.getLineSeparator()) //
				.withNullValue(prm.getNullValueString()); //

		// CSV読み込み
		MappingIterator<T> it;
		List<T> entityList = null;
		File inputFile = new File(filePath);
		try {
			it = mapper.reader(schema).forType(entityClass).readValues(new InputStreamReader(new FileInputStream(filePath), prm.getCharset()));
			entityList = StreamSupport.stream(Spliterators.spliteratorUnknownSize(it, 0), false).collect(Collectors.toCollection(ArrayList::new));
		} catch (JsonProcessingException | RuntimeJsonMappingException e) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "FileFormatError", new String[] { inputFile.getAbsolutePath() });
			throw new ErrorCheckException(errorInfoList);
		} catch (FileNotFoundException e) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "FileNotFoundError", new String[] { inputFile.getAbsolutePath() });
			throw new ErrorCheckException(errorInfoList);
		} catch (IOException e) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "FileReadFailed", new String[] { inputFile.getAbsolutePath() });
			throw new ErrorCheckException(errorInfoList);
		}
		return entityList;
	}

	/**
	 * CSVファイルを出力します。
	 *
	 * @param filePath 出力先CSVファイルパス
	 * @param entityList 展開元エンティティ（CSVの出力項目および出力順に影響）
	 * @param param CSV出力パラメーター
	 * @throws IOException
	 */
	public <T> void writeCsvFile(String filePath, List<T> entityList, CsvParam param) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 引数チェック
		if (Strings.isNullOrEmpty(filePath)) {
			throw new ErrorCheckException(checkUtil.addErrorInfoColumnCheck(new ArrayList<ErrorInfo>(), "filePath", "NotEmptyError"));
		}
		if (entityList == null) {
			throw new ErrorCheckException(checkUtil.addErrorInfoColumnCheck(new ArrayList<ErrorInfo>(), "entityList", "NotEmptyError"));
		}
		CsvParam prm = Optional.ofNullable(param).orElse(CsvParam.builder().build());

		// アペンドモード判定
		StandardOpenOption option = Optional.of(prm.isAppendMode()).filter(s -> s == true).map(s -> StandardOpenOption.APPEND).orElse(StandardOpenOption.TRUNCATE_EXISTING);

		// アペンドモードかつファイルが存在する場合はヘッダー行を出力しない
		boolean isHeader = Optional.of(filePath).filter(s -> prm.isAppendMode() && Files.exists(Paths.get(s))).map(s -> false).orElse(prm.isHeader());

		// 各種パラメーター設定
		CsvSchema schema = mapper.typedSchemaFor(entityList.get(0).getClass()) //
				.withUseHeader(isHeader) //
				.withColumnSeparator(prm.getSeparator()) //
				.withLineSeparator(prm.getLineSeparator()); //
		mapper.configure(CsvGenerator.Feature.ALWAYS_QUOTE_STRINGS, prm.isQuote());

		// シリアライザーの設定
		DefaultSerializerProvider dsp = new DefaultSerializerProvider.Impl();
		dsp.setNullValueSerializer(new NullValueSerializer(prm.getNullValueString()));
		mapper.setSerializerProvider(dsp);

		// アペンドモードすでにファイルが存在する場合は元のCSVファイルをバックアップ
		File outputFile = new File(filePath);
		Path randomFilePathForNotDuplicate = Paths.get(outputFile.toPath().getParent().toString(), UUID.randomUUID().toString());
		if (Optional.of(outputFile.toPath()).filter(s -> prm.isAppendMode()).map(s -> Files.exists(s)).orElse(false)) {
			try {
				Files.copy(outputFile.toPath(), randomFilePathForNotDuplicate);
			} catch (IOException e) {
				errorInfoList = checkUtil.addErrorInfo(errorInfoList, "FileCopyFailed", new String[] { outputFile.getAbsolutePath() });
				throw new ErrorCheckException(errorInfoList);
			}
		}

		// CSV書き込み
		try {
			Files.createDirectories(outputFile.toPath().getParent());
			try (BufferedWriter out = Files.newBufferedWriter(outputFile.toPath(), prm.getCharset(), StandardOpenOption.CREATE, option)) {
				mapper.writer(schema).writeValues(out).writeAll(entityList);
			}
		} catch (IOException e) {
			// CSVファイルをバックアップから復元する
			if (Optional.of(randomFilePathForNotDuplicate).map(s -> Files.exists(s)).get()) {
				try {
					Files.copy(randomFilePathForNotDuplicate, outputFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException ex) {
					errorInfoList = checkUtil.addErrorInfo(errorInfoList, "FileRestoreFailed", new String[] { outputFile.getAbsolutePath() });
				}

				try {
					Files.delete(randomFilePathForNotDuplicate);
				} catch (IOException ex) {
					errorInfoList = checkUtil.addErrorInfo(errorInfoList, "FileDeleteFailed", new String[] { randomFilePathForNotDuplicate.toFile().getAbsolutePath() });
				}
			}
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "FileWriteFailed", new String[] { outputFile.getAbsolutePath() });
			throw new ErrorCheckException(errorInfoList);
		}

		try {
			Files.deleteIfExists(randomFilePathForNotDuplicate);
		} catch (IOException e) {
			errorInfoList = checkUtil.addErrorInfo(errorInfoList, "FileDeleteFailed", new String[] { randomFilePathForNotDuplicate.toFile().getAbsolutePath() });
			throw new ErrorCheckException(errorInfoList);
		}
	}

	/**
	 * NULLオブジェクトのカスタムシリアライザー
	 */
	@AllArgsConstructor
	private static class NullValueSerializer extends JsonSerializer<Object> {
		private String nullValueString;

		@Override
		public void serialize(Object t, JsonGenerator jsonGenerator, SerializerProvider sp) throws IOException, JsonProcessingException {
			if (t == null) {
				jsonGenerator.writeRawValue(nullValueString);
			}
		}
	}
}
