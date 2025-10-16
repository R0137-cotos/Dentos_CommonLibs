package jp.co.ricoh.cotos.commonlib.serializer;

import java.io.IOException;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class UnixTimestampDateSerializer extends JsonSerializer<Date> {

	@Override
	public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		// Date → UNIXタイムスタンプ（ミリ秒）
		gen.writeNumber(value.getTime());
	}

}
