package jp.co.ricoh.cotos.commonlib.repository.estimation;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation;

@Repository
public interface EstimationRepository extends CrudRepository<Estimation, Long> {

	public List<Estimation> findByIdInOrderByEstimateNumber(List<Long> id);

	public List<Estimation> findByOriginContractNumberInOrderByEstimateNumber(String originContractNumber);
}
