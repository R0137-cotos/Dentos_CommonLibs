package jp.co.ricoh.cotos.commonlib.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.EnumType.DetailStatus;
import jp.co.ricoh.cotos.commonlib.entity.master.ItemMaster.CostType;

@Converter(autoApply = true)
public class CostTypeConverter implements AttributeConverter<CostType, String> {

	@Override
	public String convertToDatabaseColumn(CostType costType) {
		if (costType == null)
			return null;
		return costType.toString();
	}

	@Override
	public CostType convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return CostType.fromString(value); // IllegalArgumentExceptionはContractType.fromString側で投げている
	}

}