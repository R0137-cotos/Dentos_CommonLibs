package jp.co.ricoh.cotos.commonlib.repository.estimation;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.estimation.Estimation;

@Repository
public interface EstimationRepository extends CrudRepository<Estimation, Long> {

	public List<Estimation> findByIdInOrderByEstimationNumber(List<Long> id);

	public List<Estimation> findByOriginContractNumberInOrderByEstimationNumber(String originContractNumber);

	public List<Estimation> findByEstimationNumberAndEstimationBranchNumber(String estimationNumber, int estimationBranchNumber);

	public List<Estimation> findByOriginContractId(String originContractId);
}
