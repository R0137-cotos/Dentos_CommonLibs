package jp.co.ricoh.cotos.commonlib.repository.master;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.ProductGrpMaster;

@Repository
public interface ProductGrpMasterRepository extends CrudRepository<ProductGrpMaster, Long> {

	public ProductGrpMaster findByProductGroupCd(String productGroupCd);

	public List<ProductGrpMaster> findByProductGroupCdIn(List<String> productGrpCodeList);

}