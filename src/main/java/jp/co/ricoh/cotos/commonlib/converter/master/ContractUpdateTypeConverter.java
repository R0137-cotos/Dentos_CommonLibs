package jp.co.ricoh.cotos.commonlib.converter.master;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.master.ContractAutoUpdateMaster.ContractUpdateType;

@Converter(autoApply = true)
public class ContractUpdateTypeConverter implements AttributeConverter<ContractUpdateType, String> {

	@Override
	public String convertToDatabaseColumn(ContractUpdateType contractUpdateType) {
		if (contractUpdateType == null)
			return null;
		return contractUpdateType.toString();
	}

	@Override
	public ContractUpdateType convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return ContractUpdateType.fromString(value); //IllegalArgumentExceptionはContractUpdateType.fromString側で投げている
	}
}