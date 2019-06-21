package jp.co.ricoh.cotos.commonlib.repository.master;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.ItemMaster;

@Repository
public interface ItemMasterRepository extends CrudRepository<ItemMaster, Long> {
	public ItemMaster findByProductMasterIdAndRicohItemCode(long productMasterId, String code);

	public List<ItemMaster> findByIdIn(List<Long> id);
}
