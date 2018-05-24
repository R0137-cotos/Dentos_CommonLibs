package jp.co.ricoh.cotos.commonlib.common.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.common.entity.Contract;
import jp.co.ricoh.cotos.commonlib.common.entity.DealerCon;

@Repository
public interface DealerConRepository extends CrudRepository<DealerCon, Long> {
	public void deleteByContract(Contract contract);
}
