package jp.co.ricoh.cotos.commonlib.entity.estimation;

import jakarta.persistence.PrePersist;
import jakarta.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jp.co.ricoh.cotos.commonlib.entity.master.ProductMaster;
import jp.co.ricoh.cotos.commonlib.repository.master.ProductMasterRepository;

@Component
public class ProductEstimationListener {

	private static ProductMasterRepository productMasterRepository;

	@Autowired
	public void setProductMasterRepository(ProductMasterRepository productMasterRepository) {
		ProductEstimationListener.productMasterRepository = productMasterRepository;
	}

	/**
	 * 商品マスタ情報を商品（見積用）トランザクションに紐づけます。
	 *
	 * @param productEstimation
	 */
	@PrePersist
	@Transactional
	public void appendsEstimationProductsFields(ProductEstimation productEstimation) {
		ProductMaster productMaster = productMasterRepository.findById(productEstimation.getProductMasterId()).get();
		productEstimation.setProductMasterId(productMaster.getId());
		BeanUtils.copyProperties(productMaster, productEstimation, "id", "updatedAt", "updatedUserId", "createdAt", "createdUserId", "version");
		productEstimation.setProductEstimationName(productMaster.getProductName());
	}

}
