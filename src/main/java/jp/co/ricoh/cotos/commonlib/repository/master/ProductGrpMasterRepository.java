package jp.co.ricoh.cotos.commonlib.repository.master;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.ProductGrpMaster;

@Repository
public interface ProductGrpMasterRepository extends CrudRepository<ProductGrpMaster, Long> {

}