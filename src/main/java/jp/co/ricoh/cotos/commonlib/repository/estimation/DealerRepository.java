package jp.co.ricoh.cotos.commonlib.repository.estimation;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.estimation.Dealer;
import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation;

@Repository
public interface DealerRepository extends CrudRepository<Dealer, Long> {
	public void deleteByEstimation(Estimation estimation);
}
