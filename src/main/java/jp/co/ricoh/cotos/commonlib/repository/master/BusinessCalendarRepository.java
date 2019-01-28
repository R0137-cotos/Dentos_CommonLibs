package jp.co.ricoh.cotos.commonlib.repository.master;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;

import jp.co.ricoh.cotos.commonlib.entity.master.BusinessCalendar;

public interface BusinessCalendarRepository extends CrudRepository<BusinessCalendar, Date> {
}
