package jp.co.ricoh.cotos.commonlib.converter.master;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.master.ReportTemplateMaster.LifecycleStatus;

@Converter(autoApply = true)
public class LifeCycleStatusConverter implements AttributeConverter<LifecycleStatus, String> {

	@Override
	public String convertToDatabaseColumn(LifecycleStatus targetLifecycleStatus) {
		if (targetLifecycleStatus == null)
			return null;
		return targetLifecycleStatus.toString();
	}

	@Override
	public LifecycleStatus convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return LifecycleStatus.fromString(value); //IllegalArgumentExceptionはContractType.fromString側で投げている
	}
}