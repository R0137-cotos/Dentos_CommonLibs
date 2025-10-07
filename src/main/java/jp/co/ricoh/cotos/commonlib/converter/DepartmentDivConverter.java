package jp.co.ricoh.cotos.commonlib.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.master.VKjbMaster.DepartmentDiv;

@Converter(autoApply = true)
public class DepartmentDivConverter implements AttributeConverter<DepartmentDiv, String> {

	@Override
	public String convertToDatabaseColumn(DepartmentDiv departmentDiv) {
		if (departmentDiv == null)
			return null;
		return departmentDiv.toString();
	}

	@Override
	public DepartmentDiv convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return DepartmentDiv.fromString(value); //IllegalArgumentExceptionはContractType.fromString側で投げている
	}
}