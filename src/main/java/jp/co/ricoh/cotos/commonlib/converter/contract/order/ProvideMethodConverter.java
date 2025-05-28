package jp.co.ricoh.cotos.commonlib.converter.contract.order;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.contract.order.OrderProductInfo.ProvideMethod;

@Converter(autoApply = true)
public class ProvideMethodConverter implements AttributeConverter<ProvideMethod, String> {
	@Override
	public String convertToDatabaseColumn(ProvideMethod contractType) {
		if (contractType == null)
			return null;
		return contractType.toString();
	}

	@Override
	public ProvideMethod convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return ProvideMethod.fromString(value); // IllegalArgumentExceptionはContractType.fromString側で投げている
	}
}
