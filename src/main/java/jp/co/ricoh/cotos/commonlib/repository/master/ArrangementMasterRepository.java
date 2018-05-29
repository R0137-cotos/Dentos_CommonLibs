package jp.co.ricoh.cotos.commonlib.repository.master;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.ArrangementMaster;

@Repository
public interface ArrangementMasterRepository extends CrudRepository<ArrangementMaster, String> {
	public List<ArrangementMaster> findAllByOrderByIdAsc();
}
