package jp.co.ricoh.cotos.commonlib.repository.master;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.LedgerMaster;

@Repository
public interface LedgerMasterRepository extends JpaRepository<LedgerMaster, Long> {
	public List<LedgerMaster> findByProductMasterId(long productMasterId);
}
