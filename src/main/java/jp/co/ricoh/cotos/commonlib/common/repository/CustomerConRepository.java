package jp.co.ricoh.cotos.commonlib.common.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.common.entity.Contract;
import jp.co.ricoh.cotos.commonlib.common.entity.CustomerCon;

@Repository
public interface CustomerConRepository extends CrudRepository<CustomerCon, Long> {
	public void deleteByContract(Contract contract);
}
