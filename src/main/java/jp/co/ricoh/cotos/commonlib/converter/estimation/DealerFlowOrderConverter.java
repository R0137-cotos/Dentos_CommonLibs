package jp.co.ricoh.cotos.commonlib.converter.estimation;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.EnumType.DealerFlowOrder;

@Converter(autoApply = true)
public class DealerFlowOrderConverter implements AttributeConverter<DealerFlowOrder, String> {
	@Override
	public String convertToDatabaseColumn(DealerFlowOrder dealerFlowOrder) {
		if (dealerFlowOrder == null)
			return null;
		return dealerFlowOrder.toString();
	}

	@Override
	public DealerFlowOrder convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return DealerFlowOrder.fromString(value);
	}
}
