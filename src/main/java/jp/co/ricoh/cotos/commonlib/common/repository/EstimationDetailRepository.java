package jp.co.ricoh.cotos.commonlib.common.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.common.entity.Estimation;
import jp.co.ricoh.cotos.commonlib.common.entity.EstimationDetail;

@Repository
public interface EstimationDetailRepository extends CrudRepository<EstimationDetail, Long> {
	public void deleteByEstimation(Estimation estimation);
}
