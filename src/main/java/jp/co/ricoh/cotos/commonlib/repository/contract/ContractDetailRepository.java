package jp.co.ricoh.cotos.commonlib.repository.contract;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.contract.Contract;
import jp.co.ricoh.cotos.commonlib.entity.contract.ContractDetail;

@Repository
public interface ContractDetailRepository extends CrudRepository<ContractDetail, Long> {
	public void deleteByContract(Contract contract);
}
