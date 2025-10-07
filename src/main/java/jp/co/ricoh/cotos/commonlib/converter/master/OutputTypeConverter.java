package jp.co.ricoh.cotos.commonlib.converter.master;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.master.ReportTemplateMaster.OutputType;

@Converter(autoApply = true)
public class OutputTypeConverter implements AttributeConverter<OutputType, String> {

	@Override
	public String convertToDatabaseColumn(OutputType outputType) {
		if (outputType == null)
			return null;
		return outputType.toString();
	}

	@Override
	public OutputType convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return OutputType.fromString(value); // IllegalArgumentExceptionはContractType.fromString側で投げている
	}
}
