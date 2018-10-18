package jp.co.ricoh.cotos.commonlib.repository.master;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.MailTemplateMaster;

@Repository
public interface MailTemplateMasterRepository extends CrudRepository<MailTemplateMaster, Long> {
	@Query(value = "FROM MailTemplateMaster WHERE service_category = :SERVICE_CATEGORY AND process_category = :PROCESS_CATEGORY")
	public MailTemplateMaster findByServiceCategoryAndProcessCategory(@Param("SERVICE_CATEGORY") String serviceCategory, @Param("PROCESS_CATEGORY") String processCategory);

}
