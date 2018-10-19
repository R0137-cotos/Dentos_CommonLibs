package jp.co.ricoh.cotos.commonlib.converter.estimation;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.EnumType.ApprovalProcessCategory;

@Converter(autoApply = true)
public class ApprovalProcessCategoryConverter implements AttributeConverter<ApprovalProcessCategory, String> {
	@Override
	public String convertToDatabaseColumn(ApprovalProcessCategory approvalProcessCategory) {
		if (approvalProcessCategory == null)
			return null;
		return approvalProcessCategory.toString();
	}

	@Override
	public ApprovalProcessCategory convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return ApprovalProcessCategory.fromString(value);
	}
}