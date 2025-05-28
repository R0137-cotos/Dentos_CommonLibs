package jp.co.ricoh.cotos.commonlib.converter.contract;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.contract.Contract.ContractType;

@Converter(autoApply = true)
public class ContractTypeConverter implements AttributeConverter<ContractType, String> {
	@Override
	public String convertToDatabaseColumn(ContractType contractType) {
		if (contractType == null)
			return null;
		return contractType.toString();
	}

	@Override
	public ContractType convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return ContractType.fromString(value); // IllegalArgumentExceptionはContractType.fromString側で投げている
	}
}
