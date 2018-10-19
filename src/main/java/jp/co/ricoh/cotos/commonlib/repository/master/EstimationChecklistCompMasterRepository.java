package jp.co.ricoh.cotos.commonlib.repository.master;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import jp.co.ricoh.cotos.commonlib.entity.master.EstimationChecklistCompMaster;
public interface EstimationChecklistCompMasterRepository extends CrudRepository<EstimationChecklistCompMaster, Long> {

	@Query(value = "FROM EstimationChecklistCompMaster WHERE product_master_id = :product_master_id AND target_estimation_type = :target_estimation_type AND target_lifecycle_status = :target_lifecycle_status")
	public EstimationChecklistCompMaster findByProductMasterIdAndTargetEstimationTypeAndTargetLifecycleStatus(@Param("product_master_id") Long productMasterId, @Param("target_estimation_type") String targetEstimationType,@Param("target_lifecycle_status") Long targetLifecycleStatus);

}
