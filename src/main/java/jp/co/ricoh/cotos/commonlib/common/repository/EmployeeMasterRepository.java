package jp.co.ricoh.cotos.commonlib.common.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.common.master.EmployeeMaster;

@Repository
public interface EmployeeMasterRepository extends CrudRepository<EmployeeMaster, String> {
	public EmployeeMaster findByMomEmployeeId(String momEmployeeId);
}
