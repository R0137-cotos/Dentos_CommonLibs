package jp.co.ricoh.cotos.commonlib.converter.master;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.master.ItemMaster.ItemType;


@Converter(autoApply = true)
public class ItemTypeConverter implements AttributeConverter<ItemType, String> {

	@Override
	public String convertToDatabaseColumn(ItemType itemType) {
		if (itemType == null)
			return null;
		return itemType.toString();
	}

	@Override
	public ItemType convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return ItemType.fromString(value); //IllegalArgumentExceptionはContractType.fromString側で投げている
	}
}