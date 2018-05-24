package jp.co.ricoh.cotos.commonlib.common.entity;

import javax.persistence.PrePersist;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.common.master.ItemMaster;
import jp.co.ricoh.cotos.commonlib.common.repository.ItemMasterRepository;

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
