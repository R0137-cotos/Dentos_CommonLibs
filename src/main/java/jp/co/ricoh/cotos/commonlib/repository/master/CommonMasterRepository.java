package jp.co.ricoh.cotos.commonlib.repository.master;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.CommonMaster;

@Repository
public interface CommonMasterRepository extends CrudRepository<CommonMaster, Long> {
	@Query(value = "FROM CommonMaster WHERE (service_category = :SERVICE_CATEGORY OR service_category = '0') AND delete_flg = '0' order by id")
	public List<CommonMaster> findByServiceCategory(@Param("SERVICE_CATEGORY") String serviceCategory);
}
