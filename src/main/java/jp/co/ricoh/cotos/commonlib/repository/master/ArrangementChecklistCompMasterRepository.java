package jp.co.ricoh.cotos.commonlib.repository.master;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import jp.co.ricoh.cotos.commonlib.entity.master.ArrangementChecklistCompMaster;

public interface ArrangementChecklistCompMasterRepository extends CrudRepository<ArrangementChecklistCompMaster, Long> {

	public List<ArrangementChecklistCompMaster> findByArrangementWorkTypeMasterId(String arrangementWorkTypeMasterId);

}
