package jp.co.ricoh.cotos.commonlib.repository.communication;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.communication.ContactTo;

@Repository
public interface ContactToRepository extends CrudRepository<ContactTo, Long>  {
}
