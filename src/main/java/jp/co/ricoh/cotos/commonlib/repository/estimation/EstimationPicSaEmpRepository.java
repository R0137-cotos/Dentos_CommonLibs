package jp.co.ricoh.cotos.commonlib.repository.estimation;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.estimation.EstimationPicSaEmp;

@Repository
public interface EstimationPicSaEmpRepository extends CrudRepository<EstimationPicSaEmp, Long> {
}
