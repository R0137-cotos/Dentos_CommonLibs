package jp.co.ricoh.cotos.commonlib.common.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.common.master.ApprovalRouteNodeMaster.ApproverDeriveMethodDiv;

@Converter(autoApply = true)
public class ApproverDeriveMethodDivConverter implements AttributeConverter<ApproverDeriveMethodDiv, String> {

	@Override
	public String convertToDatabaseColumn(ApproverDeriveMethodDiv approverDeriveMethodDiv) {
		switch (approverDeriveMethodDiv) {
		case 直属上司指定:
		case 組織絶対階層指定:
		case 組織直接指定:
		case ユーザー直接指定:
			return approverDeriveMethodDiv.toValue();
		default:
			throw new IllegalArgumentException("Unknown value: " + approverDeriveMethodDiv);
		}
	}

	@Override
	public ApproverDeriveMethodDiv convertToEntityAttribute(String value) {
		switch (value) {
		case "1":
			return ApproverDeriveMethodDiv.直属上司指定;
		case "2":
			return ApproverDeriveMethodDiv.組織絶対階層指定;
		case "3":
			return ApproverDeriveMethodDiv.組織直接指定;
		case "4":
			return ApproverDeriveMethodDiv.ユーザー直接指定;
		default:
			throw new IllegalArgumentException("Unknown value: " + value);
		}
	}
}