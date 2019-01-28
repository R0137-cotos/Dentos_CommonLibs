package jp.co.ricoh.cotos.commonlib.repository.estimation;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation;
import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationDetail;

@Repository
public interface EstimationDetailRepository extends CrudRepository<EstimationDetail, Long> {
	public void deleteByEstimation(Estimation estimation);
}
