package jp.co.ricoh.cotos.commonlib.repository.master;

import org.springframework.data.repository.CrudRepository;

import jp.co.ricoh.cotos.commonlib.entity.master.ArrangementChecklistCompMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.ItemMaster;

public interface ArrangementChecklistCompMasterRepository extends CrudRepository<ArrangementChecklistCompMaster, Long> {

	public ItemMaster findByArrangementWorkTypeMasterId(String arrangementWorkTypeMasterId);

}
