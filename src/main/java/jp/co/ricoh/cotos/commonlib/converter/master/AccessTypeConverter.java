package jp.co.ricoh.cotos.commonlib.converter.master;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.AccessType;

@Converter(autoApply = true)
public class AccessTypeConverter implements AttributeConverter<AccessType, String> {

	@Override
	public String convertToDatabaseColumn(AccessType accessType) {
		if (accessType == null)
			return null;
		return accessType.toString();
	}

	@Override
	public AccessType convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return AccessType.fromString(value); //IllegalArgumentExceptionはContractType.fromString側で投げている
	}
}