package jp.co.ricoh.cotos.commonlib.repository.arrangement;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.arrangement.Arrangement;

@Repository
public interface ArrangementRepository extends CrudRepository<Arrangement, Long> {
	public boolean existsByContractIdAndDisengagementFlg(long contractId, int disengagementFlg);
}
