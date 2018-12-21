package jp.co.ricoh.cotos.commonlib.repository.common;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.common.MailSendHistory;

@Repository
public interface MailSendHistoryRepository extends CrudRepository<MailSendHistory, Long> {

}
