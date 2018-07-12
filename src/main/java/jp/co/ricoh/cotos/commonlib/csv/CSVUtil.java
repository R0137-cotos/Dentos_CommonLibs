package jp.co.ricoh.cotos.commonlib.csv;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
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
	 * CSVデータを読み込んでオブジェクトに展開します。
	 *
	 * @param filePath 読み込みCSVデータ
	 * @param entityClass 展開先エンティティクラス型
	 * @param param CSV読み込みパラメーター
	 * @return 展開先エンティティ配列
	 * @throws ErrorCheckException
	 */
	public <T> List<T> readCsvData(String csvData, Class<T> entityClass, CsvParam param) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 引数チェック
		if (Strings.isNullOrEmpty(csvData)) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(errorInfoList, "ParameterEmptyError", new String[] { "csvData" }));
		}
		if (entityClass == null) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(errorInfoList, "ParameterEmptyError", new String[] { "entityClass" }));
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
		try {
			it = mapper.reader(schema).forType(entityClass).readValues(new String(csvData.getBytes(prm.getCharset())));
			entityList = StreamSupport.stream(Spliterators.spliteratorUnknownSize(it, 0), false).collect(Collectors.toCollection(ArrayList::new));
		} catch (JsonProcessingException | RuntimeJsonMappingException e) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(errorInfoList, "FileFormatError", new String[] { "CSVデータ" }));
		} catch (IOException e) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(errorInfoList, "FileReadFailed", new String[] { "CSVデータ" }));
		}

		return entityList;
	}

	/**
	 * CSVファイルを読み込んでオブジェクトに展開します。
	 *
	 * @param filePath 読み込み元CSVファイルパス
	 * @param entityClass 展開先エンティティクラス型
	 * @param param CSV読み込みパラメーター
	 * @return 展開先エンティティ配列
	 * @throws ErrorCheckException
	 */
	public <T> List<T> readCsvFile(String filePath, Class<T> entityClass, CsvParam param) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 引数チェック
		if (Strings.isNullOrEmpty(filePath)) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(errorInfoList, "ParameterEmptyError", new String[] { "filePath" }));
		}
		File inputFile = new File(filePath);
		if (Files.notExists(Paths.get(filePath))) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(errorInfoList, "FileNotFoundError", new String[] { inputFile.getAbsolutePath() }));
		}
		if (entityClass == null) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(errorInfoList, "ParameterEmptyError", new String[] { "entityClass" }));
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
		try {
			it = mapper.reader(schema).forType(entityClass).readValues(new String(Files.readAllBytes(Paths.get(filePath)), prm.getCharset()));
			entityList = StreamSupport.stream(Spliterators.spliteratorUnknownSize(it, 0), false).collect(Collectors.toCollection(ArrayList::new));
		} catch (JsonProcessingException | RuntimeJsonMappingException e) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(errorInfoList, "FileFormatError", new String[] { "CSVデータ" }));
		} catch (IOException e) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(errorInfoList, "FileReadFailed", new String[] { "CSVデータ" }));
		}

		return entityList;
	}

	/**
	 * CSVファイルを出力します。
	 *
	 * @param filePath 出力先CSVファイルパス
	 * @param entityList 展開元エンティティ配列
	 * @param param CSV出力パラメーター
	 * @throws ErrorCheckException
	 */
	public <T> void writeCsvFile(String filePath, List<T> entityList, CsvParam param) throws ErrorCheckException {
		List<ErrorInfo> errorInfoList = new ArrayList<>();

		// 引数チェック
		if (Strings.isNullOrEmpty(filePath)) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(errorInfoList, "ParameterEmptyError", new String[] { "filePath" }));
		}
		if (entityList == null) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(errorInfoList, "ParameterEmptyError", new String[] { "entityList" }));
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

			// 読み取り専用ファイルの場合、WindowsとLinuxで上書きコピーと削除の挙動が変わるのでエラーとする
			if (!outputFile.canWrite()) {
				throw new ErrorCheckException(checkUtil.addErrorInfo(errorInfoList, "FileReadOnlyError", new String[] { outputFile.getAbsolutePath() }));
			}

			// バックアップ
			try {
				Files.copy(outputFile.toPath(), randomFilePathForNotDuplicate);
			} catch (IOException e) {
				throw new ErrorCheckException(checkUtil.addErrorInfo(errorInfoList, "FileCopyFailed", new String[] { outputFile.getAbsolutePath() }));
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
			throw new ErrorCheckException(checkUtil.addErrorInfo(errorInfoList, "FileWriteFailed", new String[] { outputFile.getAbsolutePath() }));
		}

		try {
			Files.deleteIfExists(randomFilePathForNotDuplicate);
		} catch (IOException e) {
			throw new ErrorCheckException(checkUtil.addErrorInfo(errorInfoList, "FileDeleteFailed", new String[] { randomFilePathForNotDuplicate.toFile().getAbsolutePath() }));
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
