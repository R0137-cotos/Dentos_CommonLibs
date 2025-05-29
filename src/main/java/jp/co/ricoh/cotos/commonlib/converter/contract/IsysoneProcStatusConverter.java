package jp.co.ricoh.cotos.commonlib.converter.contract;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.contract.ContractEquipment.IsysoneProcStatus;

@Converter(autoApply = true)
public class IsysoneProcStatusConverter implements AttributeConverter<IsysoneProcStatus, String> {

	@Override
	public String convertToDatabaseColumn(IsysoneProcStatus isysoneProcStatus) {
		if (isysoneProcStatus == null)
			return null;
		return isysoneProcStatus.toString();
	}

	@Override
	public IsysoneProcStatus convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return IsysoneProcStatus.fromString(value); // IllegalArgumentExceptionはIsysoneProcStatus.fromString側で投げている
	}

}
