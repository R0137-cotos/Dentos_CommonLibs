package jp.co.ricoh.cotos.commonlib.converter.communication;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.communication.ContactTo.SendType;

@Converter(autoApply = true)
public class SendTypeConverter implements AttributeConverter<SendType, String> {
	@Override
	public String convertToDatabaseColumn(SendType sendType) {
		if (sendType == null)
			return null;
		return sendType.toString();
	}

	@Override
	public SendType convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return SendType.fromString(value); // IllegalArgumentExceptionはContractType.fromString側で投げている
	}
}
