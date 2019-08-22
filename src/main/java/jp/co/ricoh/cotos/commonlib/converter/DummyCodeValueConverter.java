package jp.co.ricoh.cotos.commonlib.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.EnumType.DummyCodeValue;

@Converter(autoApply = true)
public class DummyCodeValueConverter implements AttributeConverter<DummyCodeValue, String> {
	@Override
	public String convertToDatabaseColumn(DummyCodeValue dummyCodeValue) {
		if (dummyCodeValue == null)
			return null;
		return dummyCodeValue.toString();
	}

	@Override
	public DummyCodeValue convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return DummyCodeValue.fromString(value); // IllegalArgumentExceptionはContractType.fromString側で投げている
	}
}
