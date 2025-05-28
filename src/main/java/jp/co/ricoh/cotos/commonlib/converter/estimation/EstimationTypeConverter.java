package jp.co.ricoh.cotos.commonlib.converter.estimation;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation.EstimationType;

@Converter(autoApply = true)
public class EstimationTypeConverter implements AttributeConverter<EstimationType, String> {
	@Override
	public String convertToDatabaseColumn(EstimationType estimationType) {
		if (estimationType == null)
			return null;
		return estimationType.toString();
	}

	@Override
	public EstimationType convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return EstimationType.fromString(value); // IllegalArgumentExceptionはContractType.fromString側で投げている
	}
}
