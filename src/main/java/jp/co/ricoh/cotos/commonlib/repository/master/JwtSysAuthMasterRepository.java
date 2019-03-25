package jp.co.ricoh.cotos.commonlib.repository.master;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.JwtSysAuthMaster;

@Repository
public interface JwtSysAuthMasterRepository extends CrudRepository<JwtSysAuthMaster, String> {
	public JwtSysAuthMaster findByAppIdAndPassword(String appId, String password);
}
