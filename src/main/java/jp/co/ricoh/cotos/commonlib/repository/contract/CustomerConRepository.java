package jp.co.ricoh.cotos.commonlib.repository.contract;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.contract.Contract;
import jp.co.ricoh.cotos.commonlib.entity.contract.CustomerCon;

@Repository
public interface CustomerConRepository extends CrudRepository<CustomerCon, Long> {
	public void deleteByContract(Contract contract);
}
