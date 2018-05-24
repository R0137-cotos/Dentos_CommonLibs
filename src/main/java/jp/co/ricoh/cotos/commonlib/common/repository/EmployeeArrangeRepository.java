package jp.co.ricoh.cotos.commonlib.common.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.common.entity.EmployeeArrange;

@Repository
public interface EmployeeArrangeRepository extends CrudRepository<EmployeeArrange, Long> {

}
