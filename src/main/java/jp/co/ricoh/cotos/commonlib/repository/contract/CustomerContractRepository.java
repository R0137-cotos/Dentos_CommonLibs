package jp.co.ricoh.cotos.commonlib.repository.contract;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.contract.CustomerContract;

@Repository
public interface CustomerContractRepository extends CrudRepository<CustomerContract, Long> {

}
