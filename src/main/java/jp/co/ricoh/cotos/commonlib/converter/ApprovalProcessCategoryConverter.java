package jp.co.ricoh.cotos.commonlib.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.EnumType.ApprovalProcessCategory;

@Converter(autoApply = true)
public class ApprovalProcessCategoryConverter implements AttributeConverter<ApprovalProcessCategory, String> {

	@Override
	public String convertToDatabaseColumn(ApprovalProcessCategory approverProcessCategory) {
		if (approverProcessCategory == null)
			return null;
		return approverProcessCategory.toString();
	}

	@Override
	public ApprovalProcessCategory convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return ApprovalProcessCategory.fromString(value); // IllegalArgumentExceptionはContractType.fromString側で投げている
	}

}