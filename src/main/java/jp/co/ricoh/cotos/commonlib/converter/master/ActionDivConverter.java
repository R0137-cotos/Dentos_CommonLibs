package jp.co.ricoh.cotos.commonlib.converter.master;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.ActionDiv;

@Converter(autoApply = true)
public class ActionDivConverter implements AttributeConverter<ActionDiv, String> {

	@Override
	public String convertToDatabaseColumn(ActionDiv actionDiv) {
		if (actionDiv == null)
			return null;
		return actionDiv.toString();
	}

	@Override
	public ActionDiv convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return ActionDiv.fromString(value); //IllegalArgumentExceptionはContractType.fromString側で投げている
	}
}