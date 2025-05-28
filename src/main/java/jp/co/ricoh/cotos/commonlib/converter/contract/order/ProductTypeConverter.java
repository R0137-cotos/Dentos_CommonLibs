package jp.co.ricoh.cotos.commonlib.converter.contract.order;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.contract.order.OrderBasicInfo.ProductType;

@Converter(autoApply = true)
public class ProductTypeConverter implements AttributeConverter<ProductType, String> {
	@Override
	public String convertToDatabaseColumn(ProductType contractType) {
		if (contractType == null)
			return null;
		return contractType.toString();
	}

	@Override
	public ProductType convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return ProductType.fromString(value); // IllegalArgumentExceptionはContractType.fromString側で投げている
	}
}
