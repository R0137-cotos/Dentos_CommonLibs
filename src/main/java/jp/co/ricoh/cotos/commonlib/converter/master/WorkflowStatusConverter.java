package jp.co.ricoh.cotos.commonlib.converter.master;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.master.ReportTemplateMaster.WorkflowStatus;

@Converter(autoApply = true)
public class WorkflowStatusConverter implements AttributeConverter<WorkflowStatus, String> {

	@Override
	public String convertToDatabaseColumn(WorkflowStatus targetWorkflowStatus) {
		if (targetWorkflowStatus == null)
			return null;
		return targetWorkflowStatus.toString();
	}

	@Override
	public WorkflowStatus convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return WorkflowStatus.fromString(value); //IllegalArgumentExceptionはContractType.fromString側で投げている
	}
}