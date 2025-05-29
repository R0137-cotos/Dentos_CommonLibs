package jp.co.ricoh.cotos.commonlib.converter.master;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.AuthDiv;

@Converter(autoApply = true)
public class AuthDivConverter implements AttributeConverter<AuthDiv, String> {

	@Override
	public String convertToDatabaseColumn(AuthDiv authDiv) {
		if (authDiv == null)
			return null;
		return authDiv.toString();
	}

	@Override
	public AuthDiv convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return AuthDiv.fromString(value); //IllegalArgumentExceptionはContractType.fromString側で投げている
	}
}