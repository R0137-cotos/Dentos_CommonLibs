package jp.co.ricoh.cotos.commonlib.repository.master;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.EstimationChecklistCompMaster;

@Repository
public interface EstimationChecklistCompMasterRepository extends CrudRepository<EstimationChecklistCompMaster, Long> {

	@Query(value = "SELECT * FROM estimation_checklist_comp_master WHERE product_master_id = :PRODUCT_MASTER_ID AND (target_estimation_type = '1' OR target_estimation_type = :TARGET_ESTIMATION_TYPE) AND target_lifecycle_status = :TARGET_LIFECYCLE_STATUS order by id", nativeQuery = true)
	public List<EstimationChecklistCompMaster> findByProductMasterIdAndTargetEstimationTypeAndTargetLifecycleStatus(@Param("PRODUCT_MASTER_ID") Long productMasterId, @Param("TARGET_ESTIMATION_TYPE") String targetEstimationType, @Param("TARGET_LIFECYCLE_STATUS") String targetLifecycleStatus);

}
