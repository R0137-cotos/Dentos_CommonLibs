package jp.co.ricoh.cotos.commonlib.converter.arrangement;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.arrangement.Arrangement.WorkflowStatus;

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
