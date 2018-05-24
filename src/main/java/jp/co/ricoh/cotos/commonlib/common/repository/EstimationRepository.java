package jp.co.ricoh.cotos.commonlib.common.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.common.entity.Estimation;

@Repository
public interface EstimationRepository extends CrudRepository<Estimation, Long> {

	public List<Estimation> findByIdInOrderByEstimateNumber(List<Long> id);
	
	public List<Estimation> findByOriginContractNumberInOrderByEstimateNumber(String originContractNumber);
}
