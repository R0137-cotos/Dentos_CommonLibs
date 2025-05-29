package jp.co.ricoh.cotos.commonlib.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.EnumType.ApprovalTargetType;

@Converter(autoApply = true)
public class ApprovalTargetTypeConverter implements AttributeConverter<ApprovalTargetType, String> {
	@Override
	public String convertToDatabaseColumn(ApprovalTargetType approvalTargetType) {
		if (approvalTargetType == null)
			return null;
		return approvalTargetType.toString();
	}

	@Override
	public ApprovalTargetType convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return ApprovalTargetType.fromString(value); // IllegalArgumentExceptionはContractType.fromString側で投げている
	}
}
