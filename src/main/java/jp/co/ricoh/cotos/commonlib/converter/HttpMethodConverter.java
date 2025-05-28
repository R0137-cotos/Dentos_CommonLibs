package jp.co.ricoh.cotos.commonlib.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import org.springframework.http.HttpMethod;

@Converter(autoApply = true)
public class HttpMethodConverter implements AttributeConverter<HttpMethod, String> {
	@Override
	public String convertToDatabaseColumn(HttpMethod httpMethod) {
		if (httpMethod == null)
			return null;
		return httpMethod.toString();
	}

	@Override
	public HttpMethod convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return HttpMethod.valueOf(value); // IllegalArgumentExceptionはHttpMethod.valueOf側で投げている
	}
}