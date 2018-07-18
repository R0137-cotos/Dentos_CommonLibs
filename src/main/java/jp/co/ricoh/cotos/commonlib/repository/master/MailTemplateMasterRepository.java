package jp.co.ricoh.cotos.commonlib.repository.master;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.MailTemplateMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.MailTemplateMaster.ProcessCategory;
import jp.co.ricoh.cotos.commonlib.entity.master.MailTemplateMaster.ServiceCategory;

@Repository
public interface MailTemplateMasterRepository extends CrudRepository<MailTemplateMaster, Long> {
	public MailTemplateMaster findByServiceCategoryAndProcessCategory(ServiceCategory serviceCategory, ProcessCategory processCategory);

}
