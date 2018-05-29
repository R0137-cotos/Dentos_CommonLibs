package jp.co.ricoh.cotos.commonlib.entity.estimation;

import javax.persistence.PrePersist;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.entity.master.ItemMaster;
import jp.co.ricoh.cotos.commonlib.repository.master.ItemMasterRepository;

@Component
public class ItemListener {

	private static ItemMasterRepository itemMasterRepository;

	@Autowired
	public void setItemMasterRepository(ItemMasterRepository itemMasterRepository) {
		ItemListener.itemMasterRepository = itemMasterRepository;
	}

	/**
	 * ItemMaster情報をItemトランザクションに紐づけます。
	 *
	 * @param entity
	 */
	@PrePersist
	@Transactional
	public void appendsEstimationItemFields(Item item) {
		ItemMaster itemMaster = itemMasterRepository.findByItemCode(item.getItemCode());
		item.setItemMaster(itemMaster);
		BeanUtils.copyProperties(itemMaster, item, "id");
	}
}
