package jp.co.ricoh.cotos.commonlib.repository.master;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.ProductMaster;

@Repository
public interface ProductMasterRepository extends CrudRepository<ProductMaster, Long> {
	public List<ProductMaster> findAllByOrderByIdAsc();
}