package jp.co.ricoh.cotos.commonlib.converter.contract;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.contract.Contract.SsWorkRequestCreateStatus;

@Converter(autoApply = true)
public class SsWorkRequestCreateStatusConverter implements AttributeConverter<SsWorkRequestCreateStatus, String> {

	@Override
	public String convertToDatabaseColumn(SsWorkRequestCreateStatus ssWorkRequestCreateStatus) {
		if (ssWorkRequestCreateStatus == null)
			return null;
		return ssWorkRequestCreateStatus.toString();
	}

	@Override
	public SsWorkRequestCreateStatus convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return SsWorkRequestCreateStatus.fromString(value); // IllegalArgumentExceptionはSsWorkRequestCreateStatus.fromString側で投げている
	}

}
