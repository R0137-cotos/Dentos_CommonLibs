package jp.co.ricoh.cotos.commonlib.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.EnumType.ProcessCategory;

@Converter(autoApply = true)
public class ProcessCategoryConverter implements AttributeConverter<ProcessCategory, String> {
	@Override
	public String convertToDatabaseColumn(ProcessCategory processCategory) {
		if (processCategory == null)
			return null;
		return processCategory.toString();
	}

	@Override
	public ProcessCategory convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return ProcessCategory.fromString(value); // IllegalArgumentExceptionはContractType.fromString側で投げている
	}
}
