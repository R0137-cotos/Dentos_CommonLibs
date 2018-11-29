package jp.co.ricoh.cotos.commonlib.repository.estimation;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationApprovalRouteNode;

@Repository
public interface EstimationApprovalRouteNodeRepository extends CrudRepository<EstimationApprovalRouteNode, Long> {
	public EstimationApprovalRouteNode findByEstimationApprovalRouteIdAndApprovalOrderAndApproverEmpId(Long estimationApprovalRouteId, int approvalOrder, String approverEmpId);
}
