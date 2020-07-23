package jp.co.ricoh.cotos.commonlib.repository.estimation;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.estimation.DealerEstimation;
import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation;

@Repository
public interface DealerEstimationRepository extends CrudRepository<DealerEstimation, Long> {
	public void deleteByEstimation(Estimation estimation);
}
