package jp.co.ricoh.cotos.commonlib.converter.master.contract;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.master.ContractChecklistCompMaster.TargetLifecycleStatus;

@Converter(autoApply = true)
public class TargetLifeCycleStatusConverter implements AttributeConverter<TargetLifecycleStatus, String> {

	@Override
	public String convertToDatabaseColumn(TargetLifecycleStatus targetLifecycleStatus) {
		if (targetLifecycleStatus == null)
			return null;
		return targetLifecycleStatus.toString();
	}

	@Override
	public TargetLifecycleStatus convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return TargetLifecycleStatus.fromString(value); //IllegalArgumentExceptionはContractType.fromString側で投げている
	}
}