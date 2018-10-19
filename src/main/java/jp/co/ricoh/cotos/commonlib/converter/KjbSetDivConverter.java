package jp.co.ricoh.cotos.commonlib.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.master.VKjbMaster.DepartmentDiv;

@Converter(autoApply = true)
public class KjbSetDivConverter implements AttributeConverter<DepartmentDiv, String> {

	@Override
	public String convertToDatabaseColumn(DepartmentDiv kjbSetDiv) {
		switch (kjbSetDiv) {
		case 企事:
			return "1";
		case 企事部:
			return "2";
		default:
			throw new IllegalArgumentException("Unknown value: " + kjbSetDiv);
		}
	}

	@Override
	public DepartmentDiv convertToEntityAttribute(String value) {
		switch (value) {
		case "1":
			return DepartmentDiv.企事;
		case "2":
			return DepartmentDiv.企事部;
		default:
			throw new IllegalArgumentException("Unknown value: " + value);
		}
	}
}