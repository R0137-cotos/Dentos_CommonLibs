package jp.co.ricoh.cotos.commonlib.common.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.common.entity.Dealer;
import jp.co.ricoh.cotos.commonlib.common.entity.Estimation;

@Repository
public interface DealerRepository extends CrudRepository<Dealer, Long> {
	public void deleteByEstimation(Estimation estimation);
}
