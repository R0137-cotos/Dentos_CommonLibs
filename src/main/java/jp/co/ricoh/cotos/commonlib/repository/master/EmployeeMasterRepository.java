package jp.co.ricoh.cotos.commonlib.repository.master;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.EmployeeMaster;

@Repository
public interface EmployeeMasterRepository extends CrudRepository<EmployeeMaster, String> {
	public EmployeeMaster findByMomEmployeeId(String momEmployeeId);
}
