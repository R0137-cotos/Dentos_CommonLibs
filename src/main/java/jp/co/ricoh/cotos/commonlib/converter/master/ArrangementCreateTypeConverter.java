package jp.co.ricoh.cotos.commonlib.converter.master;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.master.ContractAutoUpdateMaster.ArrangementCreateType;

@Converter(autoApply = true)
public class ArrangementCreateTypeConverter implements AttributeConverter<ArrangementCreateType, String> {

	@Override
	public String convertToDatabaseColumn(ArrangementCreateType arrangementCreateType) {
		if (arrangementCreateType == null)
			return null;
		return arrangementCreateType.toString();
	}

	@Override
	public ArrangementCreateType convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return ArrangementCreateType.fromString(value); //IllegalArgumentExceptionはArrangementCreateType.fromString側で投げている
	}
}