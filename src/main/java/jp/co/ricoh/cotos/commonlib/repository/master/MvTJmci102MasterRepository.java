package jp.co.ricoh.cotos.commonlib.repository.master;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.MvTJmci102Master;

@Repository
public interface MvTJmci102MasterRepository extends CrudRepository<MvTJmci102Master, String> {
	public MvTJmci102Master findByCustomerSiteNumber(String customerSiteNumber);
}
