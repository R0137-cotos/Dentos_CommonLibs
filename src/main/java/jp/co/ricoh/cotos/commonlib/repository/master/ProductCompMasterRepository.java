package jp.co.ricoh.cotos.commonlib.repository.master;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.ProductCompMaster;

@Repository
public interface ProductCompMasterRepository extends CrudRepository<ProductCompMaster, Long> {

}