package jp.co.ricoh.cotos.commonlib.repository.master;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.DispUrlAuthMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.DispUrlAuthMaster.Id;

@Repository
public interface DispUrlAuthMasterRepository extends CrudRepository<DispUrlAuthMaster, Id> {
	public List<DispUrlAuthMaster> findByIdSystemDomainOrderByIdUrlPatternAscIdActionIdAsc(String systemDomain);
}
