package jp.co.ricoh.cotos.commonlib.repository.contract;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.contract.Contract;

@Repository
public interface ContractRepository extends CrudRepository<Contract, Long> {

	public List<Contract> findByContractNumberAndContractBranchNumber(String contractNumber, int contractBranchNumber);

	public List<Contract> findByOriginContractId(Long originContractId);

	@Query(value = "SELECT * FROM CONTRACT WHERE LIFECYCLE_STATUS = '8' AND CANCEL_SCHEDULED_DATE <= :opDate", nativeQuery = true)
	public List<Contract> findByLifecycleAndCancelScheduledDate(@Param("opDate") String opDate);

	public List<Contract> findByRjManageNumber(String rjManageNumber);

	public List<Contract> findByEstimationIdOrderByContractBranchNumberAsc(long estimationId);
}
