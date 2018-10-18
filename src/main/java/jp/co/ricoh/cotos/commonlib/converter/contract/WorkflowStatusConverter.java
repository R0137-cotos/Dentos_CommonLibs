package jp.co.ricoh.cotos.commonlib.converter.contract;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.contract.Contract.WorkflowStatus;

@Converter(autoApply = true)
public class WorkflowStatusConverter implements AttributeConverter<WorkflowStatus, String> {
	@Override
	public String convertToDatabaseColumn(WorkflowStatus workflowStatus) {
		if (workflowStatus == null)
			return null;
		return workflowStatus.toString();
	}

	@Override
	public WorkflowStatus convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return WorkflowStatus.fromString(value); // IllegalArgumentExceptionはContractType.fromString側で投げている
	}
}
