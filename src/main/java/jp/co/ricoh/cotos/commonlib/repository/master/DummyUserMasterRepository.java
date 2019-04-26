package jp.co.ricoh.cotos.commonlib.repository.master;

import org.springframework.data.repository.CrudRepository;

import jp.co.ricoh.cotos.commonlib.entity.master.DummyUserMaster;

public interface DummyUserMasterRepository extends CrudRepository<DummyUserMaster, Long> {
	public boolean existsByUserId(String userId);

	public DummyUserMaster findByUserId(String userId);

}
