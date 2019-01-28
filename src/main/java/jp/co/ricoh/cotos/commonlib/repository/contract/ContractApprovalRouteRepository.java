package jp.co.ricoh.cotos.commonlib.repository.contract;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import jp.co.ricoh.cotos.commonlib.entity.contract.ContractApprovalRoute;

public interface ContractApprovalRouteRepository extends CrudRepository<ContractApprovalRoute, Long> {
	@Query(value = "SELECT * FROM contract_approval_route WHERE contract_id = :CONTRACT_ID AND target_lifecycle_status = :TARGET_LIFECYCLE_STATUS", nativeQuery = true)
	public ContractApprovalRoute findByContractIdAndTargetLifecycleStatus(@Param("CONTRACT_ID") Long contractId, @Param("TARGET_LIFECYCLE_STATUS") String targetLifecycleStatus);
}
