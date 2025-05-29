package jp.co.ricoh.cotos.commonlib.converter.master;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.master.EstimationChecklistCompMaster.TargetEstimationType;

@Converter(autoApply = true)
public class TargetEstimationTypeConverter implements AttributeConverter<TargetEstimationType, String> {

	@Override
	public String convertToDatabaseColumn(TargetEstimationType targetEstimationType) {
		if (targetEstimationType == null)
			return null;
		return targetEstimationType.toString();
	}

	@Override
	public TargetEstimationType convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return TargetEstimationType.fromString(value); //IllegalArgumentExceptionはContractType.fromString側で投げている
	}
}