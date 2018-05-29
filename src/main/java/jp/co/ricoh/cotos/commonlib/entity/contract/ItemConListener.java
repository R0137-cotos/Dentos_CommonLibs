package jp.co.ricoh.cotos.commonlib.entity.contract;

import javax.persistence.PrePersist;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.entity.master.ItemMaster;
import jp.co.ricoh.cotos.commonlib.repository.master.ItemMasterRepository;

@Component
public class ItemConListener {

	private static ItemMasterRepository itemMasterRepository;

	@Autowired
	public void setItemMasterRepository(ItemMasterRepository itemMasterRepository) {
		ItemConListener.itemMasterRepository = itemMasterRepository;
	}

	/**
	 * ItemMaster情報をItemConトランザクションに紐づけます。
	 *
	 * @param entity
	 */
	@PrePersist
	@Transactional
	public void appendsContractItemFields(ItemCon itemCon) {
		ItemMaster itemMaster = itemMasterRepository.findByItemCode(itemCon.getItemCode());
		itemCon.setItemMaster(itemMaster);
		BeanUtils.copyProperties(itemMaster, itemCon, "id");
	}
}
