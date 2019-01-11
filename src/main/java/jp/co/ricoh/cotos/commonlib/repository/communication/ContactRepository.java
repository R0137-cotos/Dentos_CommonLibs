package jp.co.ricoh.cotos.commonlib.repository.communication;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.communication.Contact;

@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {
	public List<Contact> findByEstimationIdAndServiceCategoryAndParentIdIsNullOrderByIdDesc(long estimationId, String serviceCategory);
}
