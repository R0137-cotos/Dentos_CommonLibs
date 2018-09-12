package jp.co.ricoh.cotos.commonlib.repository.estimation;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.estimation.CustomerEst;
import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation;

@Repository
public interface CustomerRepository extends CrudRepository<CustomerEst, Long> {
	public void deleteByEstimation(Estimation estioma);
}
