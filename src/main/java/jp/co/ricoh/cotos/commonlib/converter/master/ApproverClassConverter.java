package jp.co.ricoh.cotos.commonlib.converter.master;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.master.ApprovalRouteNodeMaster.ApproverClass;

@Converter(autoApply = true)
public class ApproverClassConverter implements AttributeConverter<ApproverClass, String> {
	@Override
	public String convertToDatabaseColumn(ApproverClass approverClass) {
		if (approverClass == null)
			return null;
		return approverClass.toString();
	}

	@Override
	public ApproverClass convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return ApproverClass.fromString(value); //IllegalArgumentExceptionはContractType.fromString側で投げている
	}
}
