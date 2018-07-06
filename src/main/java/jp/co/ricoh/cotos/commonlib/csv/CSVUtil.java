package jp.co.ricoh.cotos.commonlib.csv;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.DefaultSerializerProvider;
import com.fasterxml.jackson.dataformat.csv.CsvGenerator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

import jp.co.ricoh.cotos.commonlib.entity.CsvParam;
import lombok.AllArgsConstructor;

/**
 * CSV Reader/Writer ユーティリティー
 */
@AllArgsConstructor
public class CSVUtil {

	CsvMapper mapper;

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
	public <T> List<T> readCsvFile(String filePath, Class<T> resultClass, CsvParam param) throws JsonProcessingException, FileNotFoundException, IOException {
		// 各種パラメーター設定
		CsvParam prm = Optional.ofNullable(param).orElse(CsvParam.builder().build());
		CsvSchema schema = mapper.typedSchemaFor(resultClass) //
				.withUseHeader(prm.isHeader()) //
				.withColumnSeparator(prm.getSeparator()) //
				.withLineSeparator(prm.getLineSeparator()) //
				.withNullValue(prm.getNullValueString()); //

		// CSV読み込み
		MappingIterator<T> it = mapper.reader(schema).forType(resultClass).readValues(new InputStreamReader(new FileInputStream(filePath), prm.getCharset()));
		return StreamSupport.stream(Spliterators.spliteratorUnknownSize(it, 0), false).collect(Collectors.toCollection(ArrayList::new));
	}

	/**
	 * CSVファイルを出力します。
	 *
	 * @param filePath 出力先CSVファイルパス
	 * @param writeClass 展開元エンティティ（CSVの出力項目および出力順に影響）
	 * @param param CSV出力パラメーター
	 * @throws IOException
	 */
	public <T> void writeCsvFile(String filePath, List<T> writeClass, CsvParam param) throws IOException {
		CsvParam prm = Optional.ofNullable(param).orElse(CsvParam.builder().build());

		// アペンドモード判定
		StandardOpenOption option = Optional.of(prm.isAppendMode()).filter(s -> s == true).map(s -> StandardOpenOption.APPEND).orElse(StandardOpenOption.TRUNCATE_EXISTING);

		// アペンドモードかつファイルが存在する場合はヘッダー行を出力しない
		boolean isHeader = Optional.of(filePath).filter(s -> prm.isAppendMode() && Files.exists(Paths.get(s))).map(s -> false).orElse(prm.isHeader());

		// 各種パラメーター設定
		CsvSchema schema = mapper.typedSchemaFor(writeClass.get(0).getClass()) //
				.withUseHeader(isHeader) //
				.withColumnSeparator(prm.getSeparator()) //
				.withLineSeparator(prm.getLineSeparator()); //
		mapper.configure(CsvGenerator.Feature.ALWAYS_QUOTE_STRINGS, prm.isQuote());

		// シリアライザーの設定
		DefaultSerializerProvider dsp = new DefaultSerializerProvider.Impl();
		dsp.setNullValueSerializer(new NullValueSerializer(prm.getNullValueString()));
		mapper.setSerializerProvider(dsp);

		// CSV書き込み
		Files.createDirectories(Paths.get(filePath).getParent());
		try {
			try (BufferedWriter out = Files.newBufferedWriter(Paths.get(filePath), param.getCharset(), StandardOpenOption.CREATE, option)) {
				mapper.writer(schema).writeValues(out).writeAll(writeClass);
			}
		} catch (Exception e) {
			Files.deleteIfExists(Paths.get(filePath));
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
