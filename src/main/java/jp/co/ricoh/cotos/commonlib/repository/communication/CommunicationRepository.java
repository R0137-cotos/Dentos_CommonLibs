package jp.co.ricoh.cotos.commonlib.repository.communication;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.EnumType.ApprovalTargetType;
import jp.co.ricoh.cotos.commonlib.entity.EnumType.WorkflowType;
import jp.co.ricoh.cotos.commonlib.entity.communication.Communication;

@Repository
public interface CommunicationRepository extends CrudRepository<Communication, Long> {
	List<Communication> findByTargetDocKeyAndWorkflowTypeAndApprovalTargetType(String tartgetDocKey, WorkflowType workflowType, ApprovalTargetType approvalTargetType);

	@Query(value = "FROM Communication WHERE process_category = :PROCESS_CATEGORY AND (request_to_id = :MOM_EMPLOYEE_ID OR request_to_candidate_id = :MOM_EMPLOYEE_ID) AND app_id = :APP_ID order by communicated_at")
	public List<Communication> findByProcessCategoryAndLoginUserMomEmployeeIdAndAppId(@Param("PROCESS_CATEGORY") String processCategory, @Param("MOM_EMPLOYEE_ID") String momEmployeeId, @Param("APP_ID") String appId);

	@Query(value = "FROM Communication WHERE process_category = :PROCESS_CATEGORY AND (request_to_id = :MOM_EMPLOYEE_ID OR request_to_candidate_id = :MOM_EMPLOYEE_ID) AND app_id not in :APP_ID order by communicated_at")
	public List<Communication> findByProcessCategoryAndLoginUserMomEmployeeIdAndAppIdNotIn(@Param("PROCESS_CATEGORY") String processCategory, @Param("MOM_EMPLOYEE_ID") String momEmployeeId, @Param("APP_ID") List<String> appId);
}
