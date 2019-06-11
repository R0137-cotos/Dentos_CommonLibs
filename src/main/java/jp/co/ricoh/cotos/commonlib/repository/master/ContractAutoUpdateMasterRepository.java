package jp.co.ricoh.cotos.commonlib.repository.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.ContractAutoUpdateMaster;

@Repository
public interface ContractAutoUpdateMasterRepository extends JpaRepository<ContractAutoUpdateMaster, Long> {

	public ContractAutoUpdateMaster findByItemMasterId(long itemMasterId);
}
