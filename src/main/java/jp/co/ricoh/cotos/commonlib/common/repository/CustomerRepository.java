package jp.co.ricoh.cotos.commonlib.common.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.common.entity.Customer;
import jp.co.ricoh.cotos.commonlib.common.entity.Estimation;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
	public void deleteByEstimation(Estimation estioma);
}
