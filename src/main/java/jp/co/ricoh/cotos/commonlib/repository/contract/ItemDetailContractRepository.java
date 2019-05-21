package jp.co.ricoh.cotos.commonlib.repository.contract;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.contract.ItemDetailContract;

@Repository
public interface ItemDetailContractRepository extends CrudRepository<ItemDetailContract, Long> {

}
