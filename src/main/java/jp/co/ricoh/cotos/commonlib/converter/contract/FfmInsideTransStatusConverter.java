package jp.co.ricoh.cotos.commonlib.converter.contract;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.contract.ContractDetail.FfmInsideTransStatus;

@Converter(autoApply = true)
public class FfmInsideTransStatusConverter implements AttributeConverter<FfmInsideTransStatus, String> {

	@Override
	public String convertToDatabaseColumn(FfmInsideTransStatus ffmInsideTransStatus) {
		if (ffmInsideTransStatus == null)
			return null;
		return ffmInsideTransStatus.toString();
	}

	@Override
	public FfmInsideTransStatus convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return FfmInsideTransStatus.fromString(value); // IllegalArgumentExceptionはFfmInsideTransStatus.fromString側で投げている
	}

}
