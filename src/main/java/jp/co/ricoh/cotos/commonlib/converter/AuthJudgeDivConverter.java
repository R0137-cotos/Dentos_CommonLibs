package jp.co.ricoh.cotos.commonlib.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.master.AuthPatternMaster.AuthJudgeDiv;

@Converter(autoApply = true)
public class AuthJudgeDivConverter implements AttributeConverter<AuthJudgeDiv, String> {

	@Override
	public String convertToDatabaseColumn(AuthJudgeDiv authJudgeDiv) {
		if (authJudgeDiv == null)
			return null;
		return authJudgeDiv.toString();
	}

	@Override
	public AuthJudgeDiv convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return AuthJudgeDiv.fromString(value); // IllegalArgumentExceptionはAuthJudgeDiv.fromString側で投げている
	}

}