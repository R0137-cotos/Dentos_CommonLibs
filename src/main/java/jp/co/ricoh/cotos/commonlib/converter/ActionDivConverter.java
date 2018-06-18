package jp.co.ricoh.cotos.commonlib.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.security.mom.MomAuthorityService.ActionDiv;

@Converter(autoApply = true)
public class ActionDivConverter implements AttributeConverter<ActionDiv, String> {

	@Override
	public String convertToDatabaseColumn(ActionDiv actionDiv) {

		if (actionDiv == null)
			return null;

		switch (actionDiv) {
		case 照会:
		case 登録:
		case 更新:
		case 削除:
		case 印刷:
		case ダウンロード:
		case 集計:
			return actionDiv.toValue();
		default:
			throw new IllegalArgumentException("Unknown value: " + actionDiv);
		}
	}

	@Override
	public ActionDiv convertToEntityAttribute(String value) {

		if (value == null)
			return null;

		switch (value) {
		case "01":
			return ActionDiv.照会;
		case "02":
			return ActionDiv.登録;
		case "03":
			return ActionDiv.更新;
		case "04":
			return ActionDiv.削除;
		case "05":
			return ActionDiv.印刷;
		case "06":
			return ActionDiv.ダウンロード;
		case "07":
			return ActionDiv.集計;
		default:
			throw new IllegalArgumentException("Unknown value: " + value);
		}
	}
}