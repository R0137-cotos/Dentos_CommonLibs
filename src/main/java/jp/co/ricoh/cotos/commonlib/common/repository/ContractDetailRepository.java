package jp.co.ricoh.cotos.commonlib.common.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.common.entity.Contract;
import jp.co.ricoh.cotos.commonlib.common.entity.ContractDetail;

@Repository
public interface ContractDetailRepository extends CrudRepository<ContractDetail, Long> {
	public void deleteByContract(Contract contract);
}
