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
		case 新規作成1:
		case 新規作成3:
		case 発注承認:
		case 役務手配:
		case FFM商品原価情報:
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
		case "0001":
			return AuthDiv.新規作成1;
		case "0110":
			return AuthDiv.新規作成3;
		case "0390":
			return AuthDiv.発注承認;
		case "0690":
			return AuthDiv.役務手配;
		case "0455":
			return AuthDiv.FFM商品原価情報;
		default:
			throw new IllegalArgumentException("Unknown value: " + value);
		}
	}
}