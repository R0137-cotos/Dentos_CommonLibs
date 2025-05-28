package jp.co.ricoh.cotos.commonlib.converter.contract;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.EnumType.InitialAccountSalesStatus;

@Converter(autoApply = true)
public class InitialAccountSalesStatusConverter implements AttributeConverter<InitialAccountSalesStatus, String> {
	@Override
	public String convertToDatabaseColumn(InitialAccountSalesStatus initialAccountSalesStatus) {
		if (initialAccountSalesStatus == null)
			return null;
		return initialAccountSalesStatus.toString();
	}

	@Override
	public InitialAccountSalesStatus convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return InitialAccountSalesStatus.fromString(value); // IllegalArgumentExceptionはContractType.fromString側で投げている
	}
}
