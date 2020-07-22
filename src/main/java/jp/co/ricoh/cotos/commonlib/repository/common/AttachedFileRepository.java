package jp.co.ricoh.cotos.commonlib.repository.common;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.common.AttachedFile;

@Repository
public interface AttachedFileRepository extends CrudRepository<AttachedFile, Long> {

}
