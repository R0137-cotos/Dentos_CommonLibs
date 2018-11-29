package jp.co.ricoh.cotos.commonlib.repository.contract;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import jp.co.ricoh.cotos.commonlib.entity.contract.ContractApprovalRoute;

public interface ContractApprovalRouteRepository extends CrudRepository<ContractApprovalRoute, Long> {
	@Query(value = "SELECT * FROM contract_approval_route WHERE contract_id = :CONTRACT_ID AND approval_requester_emp_id = :APPROVAL_REQUESTER_EMP_ID AND target_lifecycle_status = :TARGET_LIFECYCLE_STATUS", nativeQuery = true)
	public ContractApprovalRoute findByContractIdAndApprovalRequesterEmpIdAndTargetLifecycleStatus(@Param("CONTRACT_ID") Long contractId, @Param("APPROVAL_REQUESTER_EMP_ID") String approvalRequesterEmpId, @Param("TARGET_LIFECYCLE_STATUS") String targetLifecycleStatus);
}
