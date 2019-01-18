package jp.co.ricoh.cotos.commonlib.repository.common;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.common.MailSendHistory;
import jp.co.ricoh.cotos.commonlib.entity.common.MailSendHistory.MailSendType;
import jp.co.ricoh.cotos.commonlib.entity.master.MailControlMaster;

@Repository
public interface MailSendHistoryRepository extends CrudRepository<MailSendHistory, Long> {

	public MailSendHistory findByTargetDataIdAndMailControlMasterAndMailSendType(long targetDataId, MailControlMaster mailControlMaster, MailSendType mailSendType);
}
