package jp.co.ricoh.cotos.commonlib.entity.estimation;

import javax.persistence.PrePersist;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.entity.master.ItemMaster;
import jp.co.ricoh.cotos.commonlib.repository.master.ItemMasterRepository;

@Component
public class ItemEstimationListener {

	private static ItemMasterRepository itemMasterRepository;

	@Autowired
	public void setItemMasterRepository(ItemMasterRepository itemMasterRepository) {
		ItemEstimationListener.itemMasterRepository = itemMasterRepository;
	}

	/**
	 * 品種マスタ情報を品種（見積用）トランザクションに紐づけます。
	 *
	 * @param itemEstimation
	 */
	@PrePersist
	@Transactional
	public void appendsEstimationItemFields(ItemEstimation itemEstimation) {
		ItemMaster itemMaster = itemMasterRepository.findByRicohItemCode(itemEstimation.getRicohItemCode());
		itemEstimation.setItemMasterId(itemMaster.getId());
		BeanUtils.copyProperties(itemMaster, itemEstimation, "id", "updatedAt", "updatedUserId", "createdAt", "createdUserId", "version");
		itemEstimation.setItemEstimationName(itemMaster.getItemName());
	}

}
