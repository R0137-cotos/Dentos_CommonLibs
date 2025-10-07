package jp.co.ricoh.cotos.commonlib.converter.contract.order;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.contract.order.OrderManagementInfo.CaptureStatus;

@Converter(autoApply = true)
public class CaptureStatusConverter implements AttributeConverter<CaptureStatus, String> {
	@Override
	public String convertToDatabaseColumn(CaptureStatus contractType) {
		if (contractType == null)
			return null;
		return contractType.toString();
	}

	@Override
	public CaptureStatus convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return CaptureStatus.fromString(value); // IllegalArgumentExceptionはContractType.fromString側で投げている
	}
}
