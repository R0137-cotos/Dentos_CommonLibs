package jp.co.ricoh.cotos.commonlib.converter.master;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.ParameterType;

@Converter(autoApply = true)
public class ParameterTypeConverter implements AttributeConverter<ParameterType, String> {

	@Override
	public String convertToDatabaseColumn(ParameterType parameterType) {
		if (parameterType == null)
			return null;
		return parameterType.toString();
	}

	@Override
	public ParameterType convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return ParameterType.fromString(value); //IllegalArgumentExceptionはContractType.fromString側で投げている
	}
}