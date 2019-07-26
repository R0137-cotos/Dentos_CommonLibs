package jp.co.ricoh.cotos.commonlib.repository.master;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.NonBusinessDayCalendarMaster;

@Repository
public interface NonBusinessDayCalendarMasterRepository extends CrudRepository<NonBusinessDayCalendarMaster, Date> {

}
