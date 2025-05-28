package jp.co.ricoh.cotos.commonlib.converter.contract;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.contract.Contract.LifecycleStatus;

@Converter(autoApply = true)
public class LifecycleStatusConverter implements AttributeConverter<LifecycleStatus, String> {
	@Override
	public String convertToDatabaseColumn(LifecycleStatus lifecycleStatus) {
		if (lifecycleStatus == null)
			return null;
		return lifecycleStatus.toString();
	}

	@Override
	public LifecycleStatus convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return LifecycleStatus.fromString(value); // IllegalArgumentExceptionはContractType.fromString側で投げている
	}
}
