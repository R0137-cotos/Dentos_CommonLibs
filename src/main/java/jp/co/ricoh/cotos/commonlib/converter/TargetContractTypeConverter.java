package jp.co.ricoh.cotos.commonlib.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.EnumType.TargetContractType;

@Converter(autoApply = true)
public class TargetContractTypeConverter implements AttributeConverter<TargetContractType, String> {
	@Override
	public String convertToDatabaseColumn(TargetContractType targetContractType) {
		if (targetContractType == null)
			return null;
		return targetContractType.toString();
	}

	@Override
	public TargetContractType convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return TargetContractType.fromString(value); //IllegalArgumentExceptionはContractType.fromString側で投げている
	}
}
