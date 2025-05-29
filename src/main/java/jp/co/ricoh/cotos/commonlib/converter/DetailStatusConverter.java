package jp.co.ricoh.cotos.commonlib.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.EnumType.DetailStatus;

@Converter(autoApply = true)
public class DetailStatusConverter implements AttributeConverter<DetailStatus, String> {

	@Override
	public String convertToDatabaseColumn(DetailStatus detailStatus) {
		if (detailStatus == null)
			return null;
		return detailStatus.toString();
	}

	@Override
	public DetailStatus convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return DetailStatus.fromString(value); // IllegalArgumentExceptionはContractType.fromString側で投げている
	}

}