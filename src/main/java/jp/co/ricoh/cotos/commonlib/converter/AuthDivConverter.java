package jp.co.ricoh.cotos.commonlib.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.security.mom.MomAuthorityService.AuthDiv;

@Converter(autoApply = true)
public class AuthDivConverter implements AttributeConverter<AuthDiv, String> {

	@Override
	public String convertToDatabaseColumn(AuthDiv authDiv) {

		if (authDiv == null)
			return null;

		switch (authDiv) {
		case 見積_契約_手配:
		case 請求_計上_本部:
		case システム管理:
			return authDiv.toValue();
		default:
			throw new IllegalArgumentException("Unknown value: " + authDiv);
		}
	}

	@Override
	public AuthDiv convertToEntityAttribute(String value) {

		if (value == null)
			return null;

		switch (value) {
		case "2200":
			return AuthDiv.見積_契約_手配;
		case "2210":
			return AuthDiv.請求_計上_本部;
		case "2220":
			return AuthDiv.システム管理;
		default:
			throw new IllegalArgumentException("Unknown value: " + value);
		}
	}
}