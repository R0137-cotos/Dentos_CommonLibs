package jp.co.ricoh.cotos.commonlib.converter.contract.order;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.contract.order.OrderProductInfo.ChargeRule;

@Converter(autoApply = true)
public class ChargeRuleConverter implements AttributeConverter<ChargeRule, String> {
	@Override
	public String convertToDatabaseColumn(ChargeRule contractType) {
		if (contractType == null)
			return null;
		return contractType.toString();
	}

	@Override
	public ChargeRule convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return ChargeRule.fromString(value); // IllegalArgumentExceptionはContractType.fromString側で投げている
	}
}
