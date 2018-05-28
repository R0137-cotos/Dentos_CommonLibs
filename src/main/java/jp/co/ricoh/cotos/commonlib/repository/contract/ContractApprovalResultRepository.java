package jp.co.ricoh.cotos.commonlib.repository.contract;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.contract.ContractApprovalResult;

@Repository
public interface ContractApprovalResultRepository extends CrudRepository<ContractApprovalResult, Long> {
	@Query(value = "SELECT * FROM contract_approval_result WHERE approval_route_id = :APPROVAL_ROUTE_ID AND processed_at = (SELECT MAX(processed_at) FROM contract_approval_result WHERE approval_route_id = :APPROVAL_ROUTE_ID)", nativeQuery = true)
	public ContractApprovalResult findByApprovalRouteIdAndMaxProcessedAt(@Param("APPROVAL_ROUTE_ID") Long approvalRouteId);
}
