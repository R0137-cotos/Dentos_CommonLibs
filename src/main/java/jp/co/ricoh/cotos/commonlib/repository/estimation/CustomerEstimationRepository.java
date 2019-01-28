package jp.co.ricoh.cotos.commonlib.repository.estimation;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.estimation.CustomerEstimation;
import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation;

@Repository
public interface CustomerEstimationRepository extends CrudRepository<CustomerEstimation, Long> {
	public void deleteByEstimation(Estimation estioma);
}
