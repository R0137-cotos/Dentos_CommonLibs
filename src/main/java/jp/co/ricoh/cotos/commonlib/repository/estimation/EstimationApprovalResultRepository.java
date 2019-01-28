package jp.co.ricoh.cotos.commonlib.repository.estimation;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationApprovalResult;

@Repository
public interface EstimationApprovalResultRepository extends CrudRepository<EstimationApprovalResult, Long> {
	@Query(value = "SELECT * FROM estimation_approval_result WHERE approval_route_id = :APPROVAL_ROUTE_ID AND processed_at = (SELECT MAX(processed_at) FROM estimation_approval_result WHERE approval_route_id = :APPROVAL_ROUTE_ID)", nativeQuery = true)
	public EstimationApprovalResult findByApprovalRouteIdAndMaxProcessedAt(@Param("APPROVAL_ROUTE_ID") Long approvalRouteId);
}
