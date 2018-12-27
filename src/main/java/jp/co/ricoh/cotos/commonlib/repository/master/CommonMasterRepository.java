package jp.co.ricoh.cotos.commonlib.repository.master;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jp.co.ricoh.cotos.commonlib.entity.master.CommonMaster;

@Repository
public interface CommonMasterRepository extends CrudRepository<CommonMaster, Long> {
	@Query(value = "SELECT * FROM common_master WHERE (service_category = :SERVICE_CATEGORY OR (service_category = '0' AND column_name NOT IN (SELECT column_name FROM common_master WHERE service_category = :SERVICE_CATEGORY))) AND delete_flg = 0 order by id", nativeQuery = true)
	public List<CommonMaster> findByServiceCategory(@Param("SERVICE_CATEGORY") String serviceCategory);

	// 受注完了時のメール送信でのみ使用可能
	@Query(value = "SELECT * FROM common_master WHERE SERVICE_CATEGORY = :SERVICE_CATEGORY AND COLUMN_NAME = :COLUMN_NAME AND DELETE_FLG = 0", nativeQuery = true)
	public CommonMaster findByServiceCategoryAndColumnName(@Param("SERVICE_CATEGORY") String serviceCategory, @Param("COLUMN_NAME") String columnName);
}
