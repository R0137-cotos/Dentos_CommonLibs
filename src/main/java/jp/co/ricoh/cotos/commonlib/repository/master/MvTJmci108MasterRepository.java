package jp.co.ricoh.cotos.commonlib.repository.master;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.MvTJmci108Master;

@Repository
public interface MvTJmci108MasterRepository extends CrudRepository<MvTJmci108Master, String> {
	public MvTJmci108Master findByCustomerSiteNumber(String customerSiteNumber);
}
