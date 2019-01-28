package jp.co.ricoh.cotos.commonlib.repository.master;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import jp.co.ricoh.cotos.commonlib.entity.master.ArrangementWorkCompMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.ItemMaster;

public interface ArrangementWorkCompMasterRepository extends CrudRepository<ArrangementWorkCompMaster, Long> {
	
	public List<ArrangementWorkCompMaster>  findByItemMasterIn(List<ItemMaster> itemMaster);
}
