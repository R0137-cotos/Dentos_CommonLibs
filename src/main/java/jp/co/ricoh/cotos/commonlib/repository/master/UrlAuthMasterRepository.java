package jp.co.ricoh.cotos.commonlib.repository.master;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.Domain;
import jp.co.ricoh.cotos.commonlib.entity.master.UrlAuthMaster.Id;

@Repository
public interface UrlAuthMasterRepository extends CrudRepository<UrlAuthMaster, Id> {
	public List<UrlAuthMaster> findByIdMethodAndIdDomainOrderByIdUrlPatternAsc(HttpMethod method, Domain domain);
}
