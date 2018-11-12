package jp.co.ricoh.cotos.commonlib.repository.communication;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.EnumType.ApprovalTargetType;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.WorkflowType;
import jp.co.ricoh.cotos.commonlib.entity.communication.Communication;

@Repository
public interface CommunicationRepository extends CrudRepository<Communication, Long> {
	List<Communication> findByTargetDocKeyAndWorkflowTypeAndApprovalTargetType(String tartgetDocKey, WorkflowType workflowType, ApprovalTargetType approvalTargetType);
}
