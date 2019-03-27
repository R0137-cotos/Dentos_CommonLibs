package jp.co.ricoh.cotos.commonlib.repository.master;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.ArrangementWorkTypeMaster;

@Repository
public interface ArrangementWorkTypeMasterRepository extends CrudRepository<ArrangementWorkTypeMaster, Long> {
	public List<ArrangementWorkTypeMaster> findByAppIdOrderByIdAsc(String appId);

	public List<ArrangementWorkTypeMaster> findByAppIdNotInOrderByIdAsc(List<String> appId);
}
