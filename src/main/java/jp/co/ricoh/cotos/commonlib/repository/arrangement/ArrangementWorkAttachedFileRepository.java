package jp.co.ricoh.cotos.commonlib.repository.arrangement;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.arrangement.ArrangementWorkAttachedFile;

@Repository
public interface ArrangementWorkAttachedFileRepository extends CrudRepository<ArrangementWorkAttachedFile, Long> {

}
