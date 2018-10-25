package jp.co.ricoh.cotos.commonlib.entity.contract;

import javax.persistence.PrePersist;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.entity.master.ItemMaster;
import jp.co.ricoh.cotos.commonlib.repository.master.ItemMasterRepository;

@Component
public class ItemContractListener {

	private static ItemMasterRepository itemMasterRepository;

	@Autowired
	public void setItemMasterRepository(ItemMasterRepository itemMasterRepository) {
		ItemContractListener.itemMasterRepository = itemMasterRepository;
	}

	/**
	 * 品種マスタ情報を品種（契約用）トランザクションに紐づけます。
	 *
	 * @param itemContract
	 */
	@PrePersist
	@Transactional
	public void appendsContractItemFields(ItemContract itemContract) {
		ItemMaster itemMaster = itemMasterRepository.findByRicohItemCode(itemContract.getRicohItemCode());
		itemContract.setItemMasterId(itemMaster.getId());
		BeanUtils.copyProperties(itemMaster, itemContract, "id");
		itemContract.setItemContractName(itemMaster.getItemName());
	}

}
