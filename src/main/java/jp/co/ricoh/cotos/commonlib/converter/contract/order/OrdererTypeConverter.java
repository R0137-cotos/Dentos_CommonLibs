package jp.co.ricoh.cotos.commonlib.converter.contract.order;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.contract.order.OrderBasicInfo.OrdererType;

@Converter(autoApply = true)
public class OrdererTypeConverter implements AttributeConverter<OrdererType, String> {
	@Override
	public String convertToDatabaseColumn(OrdererType contractType) {
		if (contractType == null)
			return null;
		return contractType.toString();
	}

	@Override
	public OrdererType convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return OrdererType.fromString(value); // IllegalArgumentExceptionはContractType.fromString側で投げている
	}
}
