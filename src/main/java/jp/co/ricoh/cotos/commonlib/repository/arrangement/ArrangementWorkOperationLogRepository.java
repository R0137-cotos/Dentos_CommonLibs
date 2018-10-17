package jp.co.ricoh.cotos.commonlib.repository.arrangement;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWorkOperationLog;

@Repository
public interface ArrangementWorkOperationLogRepository extends CrudRepository<ArrangementWorkOperationLog, Long> {

}
