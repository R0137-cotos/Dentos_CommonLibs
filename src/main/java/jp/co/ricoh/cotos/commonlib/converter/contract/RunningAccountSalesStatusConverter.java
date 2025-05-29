package jp.co.ricoh.cotos.commonlib.converter.contract;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.contract.ContractDetail.RunningAccountSalesStatus;

@Converter(autoApply = true)
public class RunningAccountSalesStatusConverter implements AttributeConverter<RunningAccountSalesStatus, String> {

	@Override
	public String convertToDatabaseColumn(RunningAccountSalesStatus runningAccountSalesStatus) {
		if (runningAccountSalesStatus == null)
			return null;
		return runningAccountSalesStatus.toString();
	}

	@Override
	public RunningAccountSalesStatus convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return RunningAccountSalesStatus.fromString(value); // IllegalArgumentExceptionはRunningAccountSalesStatus.fromString側で投げている
	}

}