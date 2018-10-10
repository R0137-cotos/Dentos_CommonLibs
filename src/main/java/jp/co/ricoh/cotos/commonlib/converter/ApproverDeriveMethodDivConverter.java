package jp.co.ricoh.cotos.commonlib.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.master.ApprovalRouteNodeMaster.AuthorizerDeriveMethodDiv;

@Converter(autoApply = true)
public class ApproverDeriveMethodDivConverter implements AttributeConverter<AuthorizerDeriveMethodDiv, String> {

	@Override
	public String convertToDatabaseColumn(AuthorizerDeriveMethodDiv approverDeriveMethodDiv) {
		switch (approverDeriveMethodDiv) {
		case 直属上司指定:
		case 組織絶対階層指定:
		case 組織直接指定:
		case ユーザー直接指定:
			return approverDeriveMethodDiv.toString();
		default:
			throw new IllegalArgumentException("Unknown value: " + approverDeriveMethodDiv);
		}
	}

	@Override
	public AuthorizerDeriveMethodDiv convertToEntityAttribute(String value) {
		switch (value) {
		case "1":
			return AuthorizerDeriveMethodDiv.直属上司指定;
		case "2":
			return AuthorizerDeriveMethodDiv.組織絶対階層指定;
		case "3":
			return AuthorizerDeriveMethodDiv.組織直接指定;
		case "4":
			return AuthorizerDeriveMethodDiv.ユーザー直接指定;
		default:
			throw new IllegalArgumentException("Unknown value: " + value);
		}
	}
}