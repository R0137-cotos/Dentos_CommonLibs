package jp.co.ricoh.cotos.commonlib.repository.communication;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.communication.Communication;

@Repository
public interface CommunicationRepository extends CrudRepository<Communication, Long> {

}
