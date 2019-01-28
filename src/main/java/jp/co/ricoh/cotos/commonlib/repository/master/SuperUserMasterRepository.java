package jp.co.ricoh.cotos.commonlib.repository.master;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.SuperUserMaster;

@Repository
public interface SuperUserMasterRepository extends CrudRepository<SuperUserMaster, Long> {
	public boolean existsByUserId(String userId);
}
