package jp.co.ricoh.cotos.commonlib.repository.communication;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.communication.Contact;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {

	@Query(value = "SELECT * FROM CONTACT WHERE estimation_id = :ESTIMATION_ID AND service_category = :SERVICE_CATEGORY AND parent_id IS NULL order by id DESC", nativeQuery = true)
	public List<Contact> findByEstimationIdAndServiceCategoryAndParentIdIsNullOrderByIdDesc(@Param("ESTIMATION_ID") long estimationId, @Param("SERVICE_CATEGORY") String serviceCategory);

	@Query(value = "SELECT * FROM CONTACT WHERE estimation_id = :ESTIMATION_ID AND service_category = :SERVICE_CATEGORY AND parent_id IS NULL AND app_id in :APP_ID order by id DESC", nativeQuery = true)
	public List<Contact> findByEstimationIdAndServiceCategoryAndParentIdIsNullAndAppIdInOrderByIdDesc(@Param("ESTIMATION_ID") long estimationId, @Param("SERVICE_CATEGORY") String serviceCategory, @Param("APP_ID") List<String> appId);

	@Query(value = "SELECT * FROM CONTACT WHERE estimation_id = :ESTIMATION_ID AND service_category = :SERVICE_CATEGORY AND parent_id IS NULL AND app_id not in :APP_ID order by id DESC", nativeQuery = true)
	public List<Contact> findByEstimationIdAndServiceCategoryAndParentIdIsNullAndAppIdNotInOrderByIdDesc(@Param("ESTIMATION_ID") long estimationId, @Param("SERVICE_CATEGORY") String serviceCategory, @Param("APP_ID") List<String> appId);
}
