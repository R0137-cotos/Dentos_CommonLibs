package jp.co.ricoh.cotos.commonlib.entity.contract;

import javax.persistence.PrePersist;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.entity.master.ProductMaster;
import jp.co.ricoh.cotos.commonlib.repository.master.ProductMasterRepository;

@Component
public class ProductContractListener {

	private static ProductMasterRepository productMasterRepository;

	@Autowired
	public void setProductMasterRepository(ProductMasterRepository productMasterRepository) {
		ProductContractListener.productMasterRepository = productMasterRepository;
	}

	/**
	 * 商品マスタ情報を商品（契約用）トランザクションに紐づけます。
	 * 
	 * @param productContract
	 */
	@PrePersist
	@Transactional
	public void appendsContractProductsFields(ProductContract productContract) {
		ProductMaster productMaster = productMasterRepository.findOne(productContract.getProductMasterId());
		productContract.setProductMasterId(productMaster.getId());
		BeanUtils.copyProperties(productMaster, productContract, "id");
		productContract.setProductContractName(productMaster.getProductName());
	}

}
