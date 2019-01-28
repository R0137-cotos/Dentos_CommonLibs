package jp.co.ricoh.cotos.commonlib.repository.master;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.MvEmployeeMaster;

@Repository
public interface MvEmployeeMasterRepository extends CrudRepository<MvEmployeeMaster, String> {
	public MvEmployeeMaster findByMomEmployeeId(String momEmployeeId);
}
