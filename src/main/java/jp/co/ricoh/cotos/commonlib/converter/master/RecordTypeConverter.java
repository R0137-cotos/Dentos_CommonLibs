package jp.co.ricoh.cotos.commonlib.converter.master;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.master.RecordDecomposeMaster.RecordType;

@Converter(autoApply = true)
public class RecordTypeConverter implements AttributeConverter<RecordType, String> {

	@Override
	public String convertToDatabaseColumn(RecordType recordType) {
		if (recordType == null)
			return null;
		return recordType.toString();
	}

	@Override
	public RecordType convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return RecordType.fromString(value); //IllegalArgumentExceptionはContractType.fromString側で投げている
	}
}