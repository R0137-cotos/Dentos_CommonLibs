package jp.co.ricoh.cotos.commonlib.converter.master;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.master.ApprovalRouteNodeMaster.ApproverDeriveMethodDiv;

@Converter(autoApply = true)
public class ApproverDeriveMethodDivConverter implements AttributeConverter<ApproverDeriveMethodDiv, String> {
	@Override
	public String convertToDatabaseColumn(ApproverDeriveMethodDiv approverDeriveMethodDiv) {
		if (approverDeriveMethodDiv == null)
			return null;
		return approverDeriveMethodDiv.toString();
	}

	@Override
	public ApproverDeriveMethodDiv convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return ApproverDeriveMethodDiv.fromString(value); //IllegalArgumentExceptionはContractType.fromString側で投げている
	}
}
