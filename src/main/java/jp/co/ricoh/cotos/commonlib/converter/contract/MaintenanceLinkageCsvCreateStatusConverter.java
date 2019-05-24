package jp.co.ricoh.cotos.commonlib.converter.contract;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import jp.co.ricoh.cotos.commonlib.entity.contract.ContractEquipment.MaintenanceLinkageCsvCreateStatus;

@Converter(autoApply = true)
public class MaintenanceLinkageCsvCreateStatusConverter implements AttributeConverter<MaintenanceLinkageCsvCreateStatus, String> {

	@Override
	public String convertToDatabaseColumn(MaintenanceLinkageCsvCreateStatus maintenanceLinkageCsvCreateStatus) {
		if (maintenanceLinkageCsvCreateStatus == null)
			return null;
		return maintenanceLinkageCsvCreateStatus.toString();
	}

	@Override
	public MaintenanceLinkageCsvCreateStatus convertToEntityAttribute(String value) {
		if (value == null)
			return null;
		return MaintenanceLinkageCsvCreateStatus.fromString(value); // IllegalArgumentExceptionはMaintenanceLinkageCsvCreateStatus.fromString側で投げている
	}

}
