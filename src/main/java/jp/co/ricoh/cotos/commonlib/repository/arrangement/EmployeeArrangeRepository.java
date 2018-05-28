package jp.co.ricoh.cotos.commonlib.repository.arrangement;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.arrangement.EmployeeArrange;

@Repository
public interface EmployeeArrangeRepository extends CrudRepository<EmployeeArrange, Long> {

}
