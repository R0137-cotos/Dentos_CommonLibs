package jp.co.ricoh.cotos.commonlib.converter.master;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.master.ReportPageMaster.Status;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, String> {

	@Override
	public String convertToDatabaseColumn(Status status) {
		if (status == null)
			return null;
		return status.toString();
	}

	@Override
	public Status convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return Status.fromString(value); // IllegalArgumentExceptionはContractType.fromString側で投げている
	}
}
