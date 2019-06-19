package jp.co.ricoh.cotos.commonlib.repository.master;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.VDirectDeliveryDealerInfoMaster;
import jp.co.ricoh.cotos.commonlib.entity.master.VDirectDeliveryDealerInfoMaster.Id;

@Repository
public interface VDirectDeliveryDealerInfoMasterRepository extends JpaRepository<VDirectDeliveryDealerInfoMaster, Id> {
	
}
