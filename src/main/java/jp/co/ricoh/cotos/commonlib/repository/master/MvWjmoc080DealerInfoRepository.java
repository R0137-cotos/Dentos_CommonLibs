package jp.co.ricoh.cotos.commonlib.repository.master;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.MvWjmoc080DealerInfo;
import jp.co.ricoh.cotos.commonlib.entity.master.MvWjmoc080DealerInfo.Id;;

@Repository
public interface MvWjmoc080DealerInfoRepository extends CrudRepository<MvWjmoc080DealerInfo, Id> {

}
