package jp.co.ricoh.cotos.commonlib.repository.master;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.ReportPageMaster;

@Repository
public interface ReportPageMasterRepository extends CrudRepository<ReportPageMaster, Long> {

}
