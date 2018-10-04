package jp.co.ricoh.cotos.commonlib.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.master.VKbMaster.KjbSetDiv;

@Converter(autoApply = true)
public class KjbSetDivConverter implements AttributeConverter<KjbSetDiv, String> {

	@Override
	public String convertToDatabaseColumn(KjbSetDiv kjbSetDiv) {
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
	public KjbSetDiv convertToEntityAttribute(String value) {
		switch (value) {
		case "1":
			return KjbSetDiv.企事;
		case "2":
			return KjbSetDiv.企事部;
		default:
			throw new IllegalArgumentException("Unknown value: " + value);
		}
	}
}