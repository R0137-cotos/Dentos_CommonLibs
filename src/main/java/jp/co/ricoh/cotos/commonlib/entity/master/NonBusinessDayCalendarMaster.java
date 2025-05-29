package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import jp.co.ricoh.cotos.commonlib.entity.EntityBaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 非営業日カレンダーマスタ
 */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "non_business_day_calendar_master")
public class NonBusinessDayCalendarMaster extends EntityBaseMaster {

	@Id
	@ApiModelProperty(value = "非営業日", required = true, position = 1)
	private Date nonBusinessDay;
}
