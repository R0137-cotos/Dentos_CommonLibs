package jp.co.ricoh.cotos.commonlib.converter.contract;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.contract.ContractDetail.AbsConInsideTransStatus;

@Converter(autoApply = true)
public class AbsConInsideTransStatusConverter implements AttributeConverter<AbsConInsideTransStatus, String> {

	@Override
	public String convertToDatabaseColumn(AbsConInsideTransStatus absConInsideTransStatus) {
		if (absConInsideTransStatus == null)
			return null;
		return absConInsideTransStatus.toString();
	}

	@Override
	public AbsConInsideTransStatus convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return AbsConInsideTransStatus.fromString(value); // IllegalArgumentExceptionはAbsConInsideTransStatus.fromString側で投げている
	}

}
