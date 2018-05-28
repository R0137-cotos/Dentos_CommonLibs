package jp.co.ricoh.cotos.commonlib.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationApprovalResult.ApprovalProcessCategory;

@Converter(autoApply = true)
public class ApprovalProcessCategoryDivConverter implements AttributeConverter<ApprovalProcessCategory, String> {

	@Override
	public String convertToDatabaseColumn(ApprovalProcessCategory approverProcessCategory) {
		switch (approverProcessCategory) {
		case 承認依頼:
		case 承認依頼差戻:
		case 承認:
			return approverProcessCategory.toValue();
		default:
			throw new IllegalArgumentException("Unknown value: " + approverProcessCategory);
		}
	}

	@Override
	public ApprovalProcessCategory convertToEntityAttribute(String value) {
		switch (value) {
		case "承認依頼":
			return ApprovalProcessCategory.承認依頼;
		case "承認依頼差戻":
			return ApprovalProcessCategory.承認依頼差戻;
		case "承認":
			return ApprovalProcessCategory.承認;
		default:
			throw new IllegalArgumentException("Unknown value: " + value);
		}
	}
}