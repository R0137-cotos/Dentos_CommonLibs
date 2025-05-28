package jp.co.ricoh.cotos.commonlib.repository.master;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.ArrangementChecklistCompMaster;

@Repository
public interface ArrangementChecklistCompMasterRepository extends CrudRepository<ArrangementChecklistCompMaster, Long> {

	@Query(value = "FROM ArrangementChecklistCompMaster WHERE arrangement_work_type_master_id = :ARRANGEMENT_WORK_TYPE_MASTER_ID order by id", nativeQuery = true)
	public List<ArrangementChecklistCompMaster> findByArrangementWorkTypeMasterId(@Param("ARRANGEMENT_WORK_TYPE_MASTER_ID") Long arrangementWorkTypeMasterId);

}
