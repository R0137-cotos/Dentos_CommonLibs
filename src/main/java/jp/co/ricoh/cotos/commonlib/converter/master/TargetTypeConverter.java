package jp.co.ricoh.cotos.commonlib.converter.master;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.master.ReportTemplateMaster.TargetType;

@Converter(autoApply = true)
public class TargetTypeConverter implements AttributeConverter<TargetType, String> {

	@Override
	public String convertToDatabaseColumn(TargetType targetType) {
		if (targetType == null)
			return null;
		return targetType.toString();
	}

	@Override
	public TargetType convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return TargetType.fromString(value); // IllegalArgumentExceptionはContractType.fromString側で投げている
	}
}
