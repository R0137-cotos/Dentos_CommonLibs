package jp.co.ricoh.cotos.commonlib.repository.master;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.ExtendsParameterCorrelationCheckMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.ExtendsParameterCorrelationCheckMaster.Id;

@Repository
public interface ExtendsParameterCorrelationCheckMasterRepository extends CrudRepository<ExtendsParameterCorrelationCheckMaster, Id> {
	@Query(value = "SELECT * FROM extends_parameter_correlation_check_master WHERE product_master_id = :PRODUCT_MASTER_ID AND domain = :DOMAIN", nativeQuery = true)
	public ExtendsParameterCorrelationCheckMaster findByProductMasterIdAndDomain(@Param("PRODUCT_MASTER_ID") long productMasterId, @Param("DOMAIN") String domain);
}
