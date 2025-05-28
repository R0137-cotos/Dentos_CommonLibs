package jp.co.ricoh.cotos.commonlib.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.security.mom.MomAuthorityService.AuthLevel;

@Converter(autoApply = true)
public class AuthLevelConverter implements AttributeConverter<AuthLevel, String> {

	@Override
	public String convertToDatabaseColumn(AuthLevel authLevel) {
		if (authLevel == null)
			return null;
		return authLevel.toValue();
	}

	@Override
	public AuthLevel convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return AuthLevel.fromString(value); // IllegalArgumentExceptionはContractType.fromString側で投げている
	}

}