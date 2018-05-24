package jp.co.ricoh.cotos.commonlib.common.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.common.entity.Communication;
import jp.co.ricoh.cotos.commonlib.common.entity.Communication.CommunicationCategory;
import jp.co.ricoh.cotos.commonlib.common.entity.Communication.ProcessCategory;
import jp.co.ricoh.cotos.commonlib.common.entity.Communication.Status;

import java.util.List;

@Repository
public interface CommunicationRepository extends CrudRepository<Communication, Long> {
	public Communication findByRequestFromAndRequestToAndTargetDocKeyAndCommunicationCategory(String requestFrom, String requestTo, String targetDocKey, CommunicationCategory communicationCategory);

	public Communication findByRequestFromAndRequestToAndTargetDocKeyAndStatusAndCommunicationCategory(String requestFrom, String requestTo, String targetDocKey, Status status, CommunicationCategory communicationCategory);

	public Communication findByRequestFromAndRequestToAndTargetDocKeyAndStatusAndCommunicationCategoryAndProcessCategory(String requestFrom, String requestTo, String targetDocKey, Status status, CommunicationCategory communicationCategory, ProcessCategory processCategory);

	@Query(value = "SELECT * FROM communication WHERE target_doc_key = :TARGET_DOC_KEY AND communicated_at = (SELECT MAX(communicated_at) FROM communication WHERE target_doc_key = :TARGET_DOC_KEY)", nativeQuery = true)
	public Communication findByTargetDocKeyAndMaxCommunicatedAt(@Param("TARGET_DOC_KEY") String targetDocKey);

	@Query(value = "SELECT * FROM Communication comm1 WHERE comm1.COMMUNICATED_AT IN (SELECT MAX(COMMUNICATED_AT) FROM Communication comm2 GROUP BY comm2.TARGET_DOC_NUMBER) AND comm1.STATUS <> '完了' AND comm1.REQUEST_TO = :request_To", nativeQuery = true)
	public List<Communication> findMyTaskByRequestTo(@Param("request_To") String requestTo);

	@Query(value = "SELECT * FROM communication WHERE target_doc_key = :TARGET_DOC_KEY AND communication_category = :COMMUNICATION_CATEGORY AND STATUS <> '完了'", nativeQuery = true)
	public List<Communication> findByTargetDocKeyAndCommunicationCategory(@Param("TARGET_DOC_KEY") String targetDocKey, @Param("COMMUNICATION_CATEGORY") String communicationCategory);

}
