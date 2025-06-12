package jp.co.ricoh.cotos.commonlib.repository.master;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.ContractChecklistCompMaster;

@Repository
public interface ContractChecklistCompMasterRepository extends CrudRepository<ContractChecklistCompMaster, Long> {

	@Query(value = "SELECT * FROM contract_checklist_comp_master WHERE product_master_id = :PRODUCT_MASTER_ID AND (target_contract_type = '1' OR target_contract_type = :TARGET_CONTRACT_TYPE) AND target_lifecycle_status = :TARGET_LIFECYCLE_STATUS order by id", nativeQuery = true)
	public List<ContractChecklistCompMaster> findByProductMasterIdAndTargetContractTypeAndTargetLifecycleStatus(@Param("PRODUCT_MASTER_ID") Long productMasterId, @Param("TARGET_CONTRACT_TYPE") String targetContractType, @Param("TARGET_LIFECYCLE_STATUS") String targetLifecycleStatus);

}
