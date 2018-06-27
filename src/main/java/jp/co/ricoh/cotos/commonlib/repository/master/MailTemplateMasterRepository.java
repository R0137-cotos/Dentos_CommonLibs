package jp.co.ricoh.cotos.commonlib.repository.master;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.communication.Communication.CommunicationCategory;
import jp.co.ricoh.cotos.commonlib.entity.communication.Communication.ProcessCategory;
import jp.co.ricoh.cotos.commonlib.entity.master.MailTemplateMaster;

@Repository
public interface MailTemplateMasterRepository extends CrudRepository<MailTemplateMaster, Long> {
	public MailTemplateMaster findByCommunicationCategoryAndProcessCategory(CommunicationCategory communicationCategory, ProcessCategory processCaetgory);

}
