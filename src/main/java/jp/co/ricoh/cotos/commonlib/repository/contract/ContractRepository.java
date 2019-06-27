package jp.co.ricoh.cotos.commonlib.repository.contract;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.contract.Contract;

@Repository
public interface ContractRepository extends CrudRepository<Contract, Long> {

	public Contract findByIdAndAppIdIn(Long id, List<String> appId);

	public Contract findByIdAndAppIdNotIn(Long id, List<String> appId);

	public List<Contract> findByContractNumber(String contractNumber);

	public List<Contract> findByContractNumberAndContractBranchNumber(String contractNumber, int contractBranchNumber);

	public List<Contract> findByOriginContractId(Long originContractId);

	@Query(value = "SELECT * FROM CONTRACT WHERE LIFECYCLE_STATUS = '8' AND CANCEL_SCHEDULED_DATE <= :opDate", nativeQuery = true)
	public List<Contract> findByLifecycleAndCancelScheduledDate(@Param("opDate") String opDate);

	public List<Contract> findByRjManageNumber(String rjManageNumber);

	public List<Contract> findByWebOrderNumber(String webOrderNumber);

	public List<Contract> findByEstimationIdOrderByContractBranchNumberAsc(long estimationId);

	@Query(value = "SELECT * FROM CONTRACT WHERE CONTRACT_TYPE = '3' AND LIFECYCLE_STATUS = '5' AND CHANGE_PREFERRED_DATE <= :changePreferredDate", nativeQuery = true)
	public List<Contract> findByContractTypeAndChangePreferredDate(@Param("changePreferredDate") Date changePreferredDate);

	@Query(value = "SELECT * FROM CONTRACT WHERE (CONTRACT_TYPE = '3' AND LIFECYCLE_STATUS = '5' AND CHANGE_PREFERRED_DATE <= :preferredDate) OR ((CONTRACT_TYPE = '1' OR CONTRACT_TYPE = '2') AND LIFECYCLE_STATUS = '11' AND CONCLUSION_PREFERRED_DATE <= :preferredDate)", nativeQuery = true)
	public List<Contract> findByContractTypeAndPreferredDate(@Param("preferredDate") Date preferredDate);

	@Query(value = "SELECT * FROM CONTRACT c, "
			+ "CONTRACT_DETAIL d, "
			+ "ITEM_CONTRACT e, "
			+ "CONTRACT_AUTO_UPDATE_MASTER a "
			+ "WHERE (c.LIFECYCLE_STATUS = '6') AND "
			+ "(c.id = d.contract_id AND d.id = e.contract_detail_id AND e.item_master_id = a.item_master_id AND a.contract_update_type = '1') AND "
			+ "NOT EXISTS (SELECT * FROM CONTRACT WHERE (LIFECYCLE_STATUS = '5' OR LIFECYCLE_STATUS = '11')) AND "
			+ "c.SERVICE_TERM_END < :preferredDate", nativeQuery = true)
	public List<Contract> findByAutoUpdaterecord(@Param("preferredDate") Date preferredDate);
}
