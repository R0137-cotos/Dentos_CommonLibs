package jp.co.ricoh.cotos.commonlib.repository.master;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.ReportTemplateMaster;

@Repository
public interface ReportTemplateMasterRepository extends CrudRepository<ReportTemplateMaster, Long> {

	@Query(value = "SELECT * FROM REPORT_TEMPLATE_MASTER" //
			+ " WHERE (OUTPUT_TYPE = :OUTPUT_TYPE OR :OUTPUT_TYPE IS NULL)" //
			+ " AND (SERVICE_CATEGORY = :SERVICE_CATEGORY OR :SERVICE_CATEGORY IS NULL)" //
			+ " AND (TARGET_TYPE = :TARGET_TYPE OR :TARGET_TYPE IS NULL)" //
			+ " AND (COMMERCIAL_FLOW_DIV = :COMMERCIAL_FLOW_DIV OR :COMMERCIAL_FLOW_DIV IS NULL)" //
			+ " AND (PRODUCT_MASTER_ID = :PRODUCT_MASTER_ID OR :PRODUCT_MASTER_ID IS NULL)" //
			+ " AND (LIFECYCLE_STATUS = :LIFECYCLE_STATUS OR :LIFECYCLE_STATUS IS NULL)" //
			+ " AND (WORKFLOW_STATUS = :WORKFLOW_STATUS OR :WORKFLOW_STATUS IS NULL)", nativeQuery = true)
	List<ReportTemplateMaster> findByReportListParameter(@Param("OUTPUT_TYPE") String outputType, @Param("SERVICE_CATEGORY") String serviceCategory, @Param("TARGET_TYPE") String targetType, @Param("COMMERCIAL_FLOW_DIV") String commercialFlowDiv, @Param("PRODUCT_MASTER_ID") Long productMasterId, @Param("LIFECYCLE_STATUS") String lifecycleStatus, @Param("WORKFLOW_STATUS") String workflowStatus);
}
