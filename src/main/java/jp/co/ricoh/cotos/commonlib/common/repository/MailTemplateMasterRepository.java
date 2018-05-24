package jp.co.ricoh.cotos.commonlib.common.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.common.entity.Communication.CommunicationCategory;
import jp.co.ricoh.cotos.commonlib.common.entity.Communication.ProcessCategory;
import jp.co.ricoh.cotos.commonlib.common.master.MailTemplateMaster;

@Repository
public interface MailTemplateMasterRepository extends CrudRepository<MailTemplateMaster, Long> {
	public MailTemplateMaster findByCommunicationCategoryAndProcessCategory(CommunicationCategory communicationCategory, ProcessCategory processCaetgory);

}
