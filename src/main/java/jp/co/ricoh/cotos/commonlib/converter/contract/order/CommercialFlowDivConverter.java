package jp.co.ricoh.cotos.commonlib.converter.contract.order;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.contract.order.OrderBasicInfo.CommercialFlowDiv;

@Converter(autoApply = true)
public class CommercialFlowDivConverter implements AttributeConverter<CommercialFlowDiv, String> {
	@Override
	public String convertToDatabaseColumn(CommercialFlowDiv contractType) {
		if (contractType == null)
			return null;
		return contractType.toString();
	}

	@Override
	public CommercialFlowDiv convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return CommercialFlowDiv.fromString(value); // IllegalArgumentExceptionはContractType.fromString側で投げている
	}
}
