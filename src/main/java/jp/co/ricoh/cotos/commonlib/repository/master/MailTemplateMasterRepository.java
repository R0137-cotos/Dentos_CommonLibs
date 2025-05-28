package jp.co.ricoh.cotos.commonlib.repository.master;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.MailTemplateMaster;

@Repository
public interface MailTemplateMasterRepository extends CrudRepository<MailTemplateMaster, Long> {
	@Query(value = "FROM MailTemplateMaster WHERE service_category = :SERVICE_CATEGORY AND process_category = :PROCESS_CATEGORY", nativeQuery = true)
	public MailTemplateMaster findByServiceCategoryAndProcessCategory(@Param("SERVICE_CATEGORY") String serviceCategory, @Param("PROCESS_CATEGORY") String processCategory);

	@Query(value = "SELECT * FROM (SELECT * FROM mail_template_master WHERE service_category = :SERVICE_CATEGORY AND process_category = :PROCESS_CATEGORY AND (product_grp_master_id = :PRODUCT_GRP_MASTER_ID OR product_grp_master_id = 0) order by product_grp_master_id desc) WHERE rownum = 1 ", nativeQuery = true)
	public MailTemplateMaster findByServiceCategoryAndProcessCategoryAndProductGrpMasterId(@Param("SERVICE_CATEGORY") String serviceCategory, @Param("PROCESS_CATEGORY") String processCategory, @Param("PRODUCT_GRP_MASTER_ID") Long productGrpMasterId);

}
