package jp.co.ricoh.cotos.commonlib.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.EnumType.InitialRunningDiv;

@Converter(autoApply = true)
public class InitialRunningDivConverter implements AttributeConverter<InitialRunningDiv, String> {

	@Override
	public String convertToDatabaseColumn(InitialRunningDiv initialRunningDiv) {
		if (initialRunningDiv == null)
			return null;
		return initialRunningDiv.toString();
	}

	@Override
	public InitialRunningDiv convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return InitialRunningDiv.fromString(value); //IllegalArgumentExceptionはInitialRunningDiv.fromString側で投げている
	}

}
