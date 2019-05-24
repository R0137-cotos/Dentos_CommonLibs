package jp.co.ricoh.cotos.commonlib.converter.master;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.master.ProductMaster.ContractClassDiv;

@Converter(autoApply = true)
public class ContractClassDivConverter implements AttributeConverter<ContractClassDiv, String> {

	@Override
	public String convertToDatabaseColumn(ContractClassDiv contractClassDiv) {
		if (contractClassDiv == null)
			return null;
		return contractClassDiv.toString();
	}

	@Override
	public ContractClassDiv convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return ContractClassDiv.fromString(value); // IllegalArgumentExceptionはContractType.fromString側で投げている
	}
}
