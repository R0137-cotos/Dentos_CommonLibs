package jp.co.ricoh.cotos.commonlib.repository.contract;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.contract.ContractApprovalRouteNode;

@Repository
public interface ContractApprovalRouteNodeRepository extends CrudRepository<ContractApprovalRouteNode, Long> {
	public ContractApprovalRouteNode findByContractApprovalRouteIdAndApprovalOrderAndApproverEmpId(Long contractApprovalRouteId, int approvalOrder, String approverEmpId);
}
