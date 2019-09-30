package jp.co.ricoh.cotos.commonlib.repository.master;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import jp.co.ricoh.cotos.commonlib.entity.master.BusinessCalendar;

public interface BusinessCalendarRepository extends CrudRepository<BusinessCalendar, Date> {

	public List<BusinessCalendar> findByBusinessDayBetween(Date priodFrom, Date priodTo);
}
