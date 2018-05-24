package jp.co.ricoh.cotos.commonlib.common.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.common.master.ItemMaster;

@Repository
public interface ItemMasterRepository extends CrudRepository<ItemMaster, Long> {
	public ItemMaster findByItemCode(String code);
}
