package jp.co.ricoh.cotos.commonlib.logic.businessday;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import jp.co.ricoh.cotos.commonlib.entity.master.BusinessCalendar;
import jp.co.ricoh.cotos.commonlib.entity.master.NonBusinessDayCalendarMaster;
import jp.co.ricoh.cotos.commonlib.repository.master.BusinessCalendarRepository;
import jp.co.ricoh.cotos.commonlib.repository.master.NonBusinessDayCalendarMasterRepository;

/**
 * 営業日共通クラス
 */
@Component
public class BusinessDayUtil {

	@Autowired
	private NonBusinessDayCalendarMasterRepository nonBusinessDayCalendarMasterRepository;

	@Autowired
	private BusinessCalendarRepository BusinessCalendarRepository;

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
	 * @param isSubtract
	 *            減算するかどうか
	 * @return
	 */
	public Date findShortestBusinessDay(Date date, int leadTime, boolean isSubtract) {
		Date retDate = date;
		Calendar calendar = Calendar.getInstance();
		for (int i = 0; i < leadTime; i++) {
			while (true) {
				calendar.setTime(retDate);
				calendar.add(Calendar.DATE, !isSubtract ? 1 : -1);
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
		Date retDate = findShortestBusinessDay(date, leadTime, false);
		if (calendar.get(Calendar.AM_PM) == Calendar.PM) {
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
	 * 引数月の業務カレンダーリスト取得
	 * 
	 * @param targetYm
	 *            業務カレンダーを取得したい月
	 * @return 業務カレンダーリスト（1月分）
	 */
	public List<BusinessCalendar> findBusinessCalendarForSpecifiedMonth(String targetYm) {
		int year = Integer.parseInt(StringUtils.substring(targetYm, 0, 4));
		int month = StringUtils.length(targetYm) == 7 ? Integer.parseInt(StringUtils.substring(targetYm, 5, 7)) : Integer.parseInt(StringUtils.substring(targetYm, 4, 6));
		return findBusinessCalendarForSpecifiedRange(getStartOfMonth(year, month), getEndOfMonth(year, month));
	}

	/**
	 * 引数範囲の業務カレンダーリスト取得
	 * 
	 * @param targetPriodFrom
	 *            業務カレンダー取得条件(From)
	 * @param targetPriodTo
	 *            業務カレンダー取得条件(To)
	 * @return 業務カレンダーリスト
	 */
	public List<BusinessCalendar> findBusinessCalendarForSpecifiedRange(Date targetPriodFrom, Date targetPriodTo) {
		return BusinessCalendarRepository.findByBusinessDayBetween(targetPriodFrom, targetPriodTo);
	}

	/**
	 * 引数月の月末最終営業日取得
	 * 
	 * @param targetYm
	 *            月末最終営業日を取得したい月
	 * @return 月末最終営業日
	 */
	public Date getLastBusinessDayOfTheMonth(String targetYm) {
		BusinessCalendar result = findBusinessCalendarForSpecifiedMonth(targetYm).stream().sorted(Comparator.comparing(BusinessCalendar::getBusinessDay).reversed()).findFirst().orElse(null);
		return ObjectUtils.isEmpty(result) ? null : result.getBusinessDay();
	}

	/**
	 * 引数年月の月初日取得
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return 月初日
	 */
	public Date getStartOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		// 指定年, 指定月, 1日, 0時0分0秒
		calendar.set(year, month - 1, 1, 0, 0, 0);
		// きっちりミリ秒も0にする
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 引数年月の月末日取得
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return 月末日
	 */
	public Date getEndOfMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();

		// 指定年, 指定月(-1), 1日, 0時0分0秒
		calendar.set(year, month - 1, 1, 0, 0, 0);
		// 翌月にする (年またぎ対応)
		calendar.add(Calendar.MONTH, 1);
		// 0時0分0秒0ミリ秒 - 1ミリ秒で、月末23時59分59秒999ミリ秒にできる
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.MILLISECOND, -1);
		return calendar.getTime();
	}
}
