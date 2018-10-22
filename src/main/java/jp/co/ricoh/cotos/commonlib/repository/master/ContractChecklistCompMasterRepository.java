package jp.co.ricoh.cotos.commonlib.repository.master;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import jp.co.ricoh.cotos.commonlib.entity.master.ContractChecklistCompMaster;

public interface ContractChecklistCompMasterRepository extends CrudRepository<ContractChecklistCompMaster, Long> {

	@Query(value = "FROM ContractChecklistCompMaster WHERE product_master_id = :product_master_id AND target_contract_type = :target_contract_type AND target_lifecycle_status = :target_lifecycle_status")
	public List<ContractChecklistCompMaster> findByProductMasterIdAndTargetContractTypeAndTargetLifecycleStatus(@Param("product_master_id") Long productMasterId, @Param("target_contract_type") String targetContractType, @Param("target_lifecycle_status") Long targetLifecycleStatus);

}
