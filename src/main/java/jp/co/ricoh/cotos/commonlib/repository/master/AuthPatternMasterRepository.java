package jp.co.ricoh.cotos.commonlib.repository.master;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.AuthPatternMaster;

@Repository
public interface AuthPatternMasterRepository extends CrudRepository<AuthPatternMaster, Long> {
}
