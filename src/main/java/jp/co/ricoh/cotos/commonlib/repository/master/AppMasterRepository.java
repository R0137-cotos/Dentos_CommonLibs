package jp.co.ricoh.cotos.commonlib.repository.master;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.AppMaster;

@Repository
public interface AppMasterRepository extends CrudRepository<AppMaster, String> {
	public AppMaster findByAppIdAndPassword(String appId, String password);
}
