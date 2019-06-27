package jp.co.ricoh.cotos.commonlib.logic.businessday;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.entity.master.NonBusinessDayCalendarMaster;
import jp.co.ricoh.cotos.commonlib.repository.master.NonBusinessDayCalendarMasterRepository;

/**
 * 営業日共通クラス
 */
@Component
public class BusinessDayUtil {

	@Autowired
	private NonBusinessDayCalendarMasterRepository nonBusinessDayCalendarMasterRepository;

	/**
	 * 営業日かどうか
	 * 
	 * @param date
	 *            日付
	 * @return
	 */
	public boolean isBusinessDay(Date date) {
		NonBusinessDayCalendarMaster nonBusinessDayCalendarMaster = nonBusinessDayCalendarMasterRepository.findOne(date);
		return nonBusinessDayCalendarMaster == null;
	}

	/**
	 * 最短営業日取得
	 * 
	 * @param date
	 *            日付
	 * @param leadTime
	 *            日数
	 * @return
	 */
	public Date findShortestBusinessDay(Date date, int leadTime) {
		Date retDate = date;
		Calendar calendar = Calendar.getInstance();
		for (int i = 1; i <= leadTime + 1; i++) {
			while (true) {
				calendar.setTime(retDate);
				calendar.add(Calendar.DATE, 1);
				retDate = calendar.getTime();
				if (isBusinessDay(retDate)) {
					break;
				}
			}
		}

		return retDate;
	}

	/**
	 * 最短営業日取得_時間計算
	 * 
	 * @param date
	 *            日付
	 * @param leadTime
	 *            日数
	 * @param baseDate
	 *            基準日時
	 * @return
	 */
	public Date findShortestBusinessDayTimeCalc(Date date, int leadTime, Date baseDateTime) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(baseDateTime);
		Date retDate = findShortestBusinessDay(date, leadTime);
		if (calendar.get(Calendar.AM_PM) == Calendar.PM) {
			calendar.setTime(retDate);
			calendar.add(Calendar.DATE, 1);
			retDate = calendar.getTime();
		}

		return retDate;
	}
}
