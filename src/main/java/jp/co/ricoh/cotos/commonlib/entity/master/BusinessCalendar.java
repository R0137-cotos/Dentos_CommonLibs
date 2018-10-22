package jp.co.ricoh.cotos.commonlib.entity.master;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 業務カレンダー
 */
@Entity
@Data
@Table(name = "business_calendar")
public class BusinessCalendar {

	@Id
	@ApiModelProperty(value = "カレンダー日付", required = true, position = 1)
	private Date calendarDate;

	/**
	 * 休日フラグ
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "休日フラグ", required = true, position = 2, allowableValues = "range[0,1]")
	private int businessHoliday;

	/**
	 * 営業日付
	 */
	@Column(nullable = false)
	@ApiModelProperty(value = "営業日付", required = true, position = 3)
	private Date businessDay;

}
